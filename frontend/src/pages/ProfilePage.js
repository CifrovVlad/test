import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';

const ProfilePage = () => {
    const [rentals, setRentals] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!token) {
            setError('Вы не авторизованы');
            setLoading(false);
            return;
        }

        const decoded = jwtDecode(token);
        const userId = decoded.userId;

        axios.get(`http://localhost:8080/api/rentals/history/user/${userId}`, {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => {
                const rentalData = res.data;
                const rentalPromises = rentalData.map(rental =>
                    axios.get(`http://localhost:8080/api/equipment/${rental.equipmentId}`, {
                        headers: { Authorization: `Bearer ${token}` }
                    }).then(equipmentResponse => ({
                        ...rental,
                        equipment: equipmentResponse.data
                    }))
                );

                Promise.all(rentalPromises).then(rentalsWithEquipment => {
                    setRentals(rentalsWithEquipment);
                    setLoading(false);
                });
            })
            .catch(err => {
                console.error(err);
                setError('Ошибка при загрузке истории аренд.');
                setLoading(false);
            });
    }, []);

    const handleDeleteRental = (rentalId) => {
        const token = localStorage.getItem('token');
        if (!token) {
            setError('Вы не авторизованы');
            return;
        }

        axios.delete(`http://localhost:8080/api/rentals/${rentalId}`, {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(() => {
                // Обновляем список аренды после удаления
                setRentals(rentals.filter(rental => rental.id !== rentalId));
            })
            .catch(err => {
                console.error(err);
                setError('Ошибка при удалении аренды.');
            });
    };

    if (loading) return <div>Загрузка...</div>;
    if (error) return <div>{error}</div>;

    return (
        <div className="profile-page">
            <h2>Моя история аренд</h2>
            {rentals.length === 0 ? (
                <p>Вы пока ничего не арендовали.</p>
            ) : (
                <ul className="rental-list">
                    {rentals.map(rental => (
                        <li key={rental.id} className="rental-item">
                            <strong>Техника:</strong> {rental.equipment?.model} ({rental.equipment?.brand})<br/>
                            <strong>С:</strong> {rental.rentalDate} <strong>по:</strong> {rental.returnDate}<br/>
                            <strong>Статус:</strong> {rental.status}<br/>
                            <button
                                onClick={() => handleDeleteRental(rental.id)}
                                className="delete-rental-button"
                            >
                                Удалить аренду
                            </button>
                            <hr/>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default ProfilePage;