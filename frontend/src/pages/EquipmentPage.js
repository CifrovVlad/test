import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../Equipment.css';
import { jwtDecode } from 'jwt-decode';

const EquipmentPage = () => {
    const [equipment, setEquipment] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [showModal, setShowModal] = useState(false);
    const [isLogin, setIsLogin] = useState(true);
    const [rentalDate, setRentalDate] = useState(new Date().toISOString().split('T')[0]);
    const [returnDate, setReturnDate] = useState('');
    const [selectedEquipmentId, setSelectedEquipmentId] = useState(null);
    const [authRequired, setAuthRequired] = useState(false);

    useEffect(() => {
        axios.get('http://localhost:8080/api/equipment')
            .then(response => {
                const availableEquipment = response.data.filter(item => item.active);
                setEquipment(availableEquipment);
                setLoading(false);
            })
            .catch(() => {
                setError('Не удалось загрузить данные');
                setLoading(false);
            });
    }, []);

    const handleRentClick = (equipmentId) => {
        const token = localStorage.getItem('token');
        setSelectedEquipmentId(equipmentId);

        if (!token) {
            setAuthRequired(true);
            setIsLogin(true);
            setShowModal(true);
        } else {
            setAuthRequired(false);
            setShowModal(true);
        }
    };

    const handleRentConfirm = () => {
        const token = localStorage.getItem('token');

        if (!returnDate) {
            alert('Пожалуйста, выберите дату возврата');
            return;
        }

        try {
            const decodedToken = jwtDecode(token);
            console.log(decodedToken);
            const clientId = decodedToken.userId;

            axios.post('http://localhost:8080/api/rentals', {
                clientId,
                equipmentId: selectedEquipmentId,
                rentalDate,
                returnDate,
                status: 'ACTIVE'
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }).then(() => {
                alert('Техника успешно арендована!');
                setShowModal(false);
                setReturnDate('');
                setSelectedEquipmentId(null);
            }).catch(() => {
                alert('Ошибка при аренде техники.');
            });
        } catch {
            alert('Ошибка авторизации. Пожалуйста, войдите заново.');
        }
    };

    const handleRegisterLogin = (userData) => {
        const url = isLogin
            ? 'http://localhost:8080/api/auth/login'
            : 'http://localhost:8080/api/auth/register';

        axios.post(url, userData)
            .then(response => {
                const { token } = response.data;
                localStorage.setItem('token', token);
                alert('Успешный вход/регистрация!');
                setAuthRequired(false); // Больше не нужно подтверждать авторизацию
                setIsLogin(true);
                // Переход к форме аренды
                setTimeout(() => setShowModal(true), 0);
            })
            .catch(() => {
                alert('Ошибка при регистрации/входе.');
            });
    };

    const handleModalClose = () => {
        setShowModal(false);
        setAuthRequired(false);
        setReturnDate('');
        setSelectedEquipmentId(null);
    };

    const handleLogout = () => {
        localStorage.removeItem('token');
        alert('Вы вышли из системы.');
    };

    if (loading) return <div>Загрузка...</div>;
    if (error) return <div>{error}</div>;

    return (
        <div className="equipment-page">
            <h2>Добро пожаловать в систему аренды спецтехники!</h2>
            <h3>Выберите технику для аренды:</h3>

            <div className="equipment-list">
                {equipment.length === 0 ? (
                    <p>Нет доступной техники.</p>
                ) : (
                    equipment.map(item => (
                        <div key={item.id} className="equipment-card">
                            <img src={item.imageUrl} alt={item.model} className="equipment-image" />
                            <h3>{item.model} ({item.brand})</h3>
                            <p>Цена аренды: {item.rentalPricePerDay ? `${item.rentalPricePerDay} руб/день` : 'Цена не установлена'}</p>
                            <button className="rent-button" onClick={() => handleRentClick(item.id)}>
                                Арендовать
                            </button>
                        </div>
                    ))
                )}
            </div>

            {showModal && (
                <div className="modal-overlay">
                    <div className="modal">
                        <button className="close-modal" onClick={handleModalClose}>×</button>

                        {authRequired ? (
                            <>
                                <h3>{isLogin ? 'Вход' : 'Регистрация'}</h3>
                                <form onSubmit={(e) => {
                                    e.preventDefault();
                                    handleRegisterLogin({
                                        email: e.target.email.value,
                                        password: e.target.password.value
                                    });
                                }}>
                                    <input type="email" name="email" placeholder="Email" required />
                                    <input type="password" name="password" placeholder="Пароль" required />
                                    <button type="submit">{isLogin ? 'Войти' : 'Зарегистрироваться'}</button>
                                </form>
                                <p>
                                    {isLogin ? 'Нет аккаунта? ' : 'Уже есть аккаунт? '}
                                    <button onClick={() => setIsLogin(!isLogin)}>
                                        {isLogin ? 'Зарегистрироваться' : 'Войти'}
                                    </button>
                                </p>
                            </>
                        ) : (
                            <>
                                <h3>Подтвердите аренду</h3>
                                <form onSubmit={(e) => { e.preventDefault(); handleRentConfirm(); }}>
                                    <label htmlFor="rentalDate">Дата аренды:</label>
                                    <input
                                        type="date"
                                        id="rentalDate"
                                        value={rentalDate}
                                        onChange={(e) => setRentalDate(e.target.value)}
                                        required
                                    />
                                    <label htmlFor="returnDate">Дата возврата:</label>
                                    <input
                                        type="date"
                                        id="returnDate"
                                        value={returnDate}
                                        onChange={(e) => setReturnDate(e.target.value)}
                                        required
                                    />
                                    <button type="submit">Арендовать</button>
                                </form>
                            </>
                        )}
                    </div>
                </div>
            )}

            {localStorage.getItem('token') && (
                <div className="logout-section">
                    <button onClick={handleLogout}>Выход</button>
                    <a href="/profile">Личный кабинет</a>
                </div>
            )}
        </div>
    );
};

export default EquipmentPage;
