import React, { useState, useEffect } from 'react';
import { Routes, Route, BrowserRouter as Router, Navigate } from 'react-router-dom';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import EquipmentPage from './pages/EquipmentPage';
import Header from './components/Header';
import ProfilePage from "./pages/ProfilePage";

function App() {
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        const userId = localStorage.getItem('userId');
        if (userId) {
            setIsAuthenticated(true);
        }
    }, []);

    return (
        <Router>
            <Header isAuthenticated={isAuthenticated} setIsAuthenticated={setIsAuthenticated} />
            <div style={{ marginTop: '80px' }}> {/* Отступ для фиксированного header */}
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/login" element={isAuthenticated ? <Navigate to="/" /> : <LoginPage setIsAuthenticated={setIsAuthenticated} />} />
                    <Route path="/register" element={isAuthenticated ? <Navigate to="/" /> : <RegisterPage setIsAuthenticated={setIsAuthenticated} />} />
                    <Route path="/equipment" element={<EquipmentPage />} />
                    <Route path="/profile" element={<ProfilePage />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;