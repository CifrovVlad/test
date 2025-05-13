import React, { useEffect, useState } from 'react';
import axios from 'axios';

const RentalPage = () => {
    const [rentals, setRentals] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const userId = localStorage.getItem('userId');

    useEffect(() => {
        if (userId) {
            axios.get(`http://localhost:8080/api/rentals/history/${userId}`)
                .then(response => {
                    setRentals(response.data);
                    setLoading(false);
                })
                .catch(err => {
                    setError('Не удалось загрузить аренды');
                    setLoading(false);
                });
        } else {
            setLoading(false);
            setError('Пожалуйста, войдите для просмотра аренды.');
        }
    }, [userId]);

    if (loading) {
        return <div>Загрузка...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    return (
        <div className="rental-page">
            <h2>Ваши аренды</h2>
            <div className="rental-list">
                {rentals.length === 0 ? (
                    <p>У вас нет активных арендов.</p>
                ) : (
                    rentals.map((rental) => (
                        <div key={rental.id} className="rental-card">
                            <h3>{rental.equipment.model} ({rental.equipment.brand})</h3>
                            <p>Дата аренды: {new Date(rental.rentalDate).toLocaleDateString()}</p>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
};

export default RentalPage;