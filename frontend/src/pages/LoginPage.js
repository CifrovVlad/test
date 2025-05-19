import React, { useState } from 'react';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import '../App.css';

const LoginPage = ({ setIsAuthenticated }) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();

        if (!email || !password) {
            alert('Заполните все поля');
            return;
        }

        try {
            const response = await axios.post('http://localhost:8080/api/auth/login', {
                email,
                password
            });

            // Предполагаем, что сервер возвращает JSON с полем token
            const { token } = response.data;

            // Сохраняем токен в localStorage
            localStorage.setItem('token', token);

            // Декодируем токен, чтобы получить userId
            const decodedToken = jwtDecode(token);
            const userId = decodedToken.sub; // Обычно 'sub' содержит userId в JWT

            // Сохраняем userId в localStorage
            localStorage.setItem('userId', userId);

            // Обновляем состояние, чтобы пользователь был аутентифицирован
            setIsAuthenticated(true);
            alert('Успешный вход');
        } catch (error) {
            console.error('Ошибка при входе:', error);
            alert('Неверный email или пароль');
        }
    };

    return (
        <div className="page-container">
            <div className="form-container">
                <h2>Вход</h2>
                <form onSubmit={handleLogin}>
                    <div className="input-group">
                        <input
                            type="email"
                            placeholder="Email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <input
                            type="password"
                            placeholder="Пароль"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit">Войти</button>
                </form>
                <div className="switch-link">
                    Нет аккаунта? <a href="/register">Зарегистрироваться</a>
                </div>
            </div>
        </div>
    );
};

export default LoginPage;