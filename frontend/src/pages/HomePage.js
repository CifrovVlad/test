import React from 'react';
import { Link } from 'react-router-dom';
import '../HomePage.css'; // Подключаем CSS

const HomePage = () => {
    return (
        <div className="home-page">
            <div className="hero">
                <h1>Добро пожаловать в систему аренды спецтехники!</h1>
            </div>
            <div className="center-button-wrapper">
                <Link to="/equipment" className="main-button">Посмотреть технику</Link>
            </div>
        </div>
    );
};

export default HomePage;