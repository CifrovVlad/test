import React from 'react';
import { Link } from 'react-router-dom';
import '../Header.css';

function Header({ isAuthenticated, setIsAuthenticated }) {
    const handleLogout = () => {
        localStorage.removeItem('userId');
        localStorage.removeItem('token');  // Убираем токен при выходе
        setIsAuthenticated(false);
    };

    return (
        <header className="header">
            <div className="logo">Аренда спецтехники</div>
            <nav className="nav">
                <Link to="/" className="home-link">Главная</Link> {/* Ссылка на главную страницу */}
                {isAuthenticated ? (
                    <div className="auth-links">
                        <Link to="/profile" className="profile-link">Личный кабинет</Link>
                        <button onClick={handleLogout} className="logout-button">Выход</button>
                    </div>
                ) : (
                    <div className="auth-links">
                        <Link to="/login" className="auth-link">Войти</Link>
                        <Link to="/register" className="auth-link">Зарегистрироваться</Link>
                    </div>
                )}
            </nav>
        </header>
    );
}

export default Header;
