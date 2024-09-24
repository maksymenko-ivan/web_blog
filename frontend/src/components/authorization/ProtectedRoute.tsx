import React, { useEffect, useState, FC } from 'react';
import { Navigate } from 'react-router-dom';
import axios from 'axios';

interface ProtectedRouteProps {
    children: React.ReactNode;
}

const ProtectedRoute: FC<ProtectedRouteProps> = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState<boolean | null>(null);

    useEffect(() => {
        const checkTokenValidity = async () => {
            const token = localStorage.getItem('token');

            if (!token) {
                console.error('No token found in localStorage');
                setIsAuthenticated(false);
                return;
            }

            try {
                const response = await axios.get('http://localhost:8080/api/v1/auth', {
                    headers: { Authorization: `Bearer ${token}` }   
                });
                console.log('Token validation response:', response);
                setIsAuthenticated(response.status === 200);
            } catch (error) {
                console.error('Error validating token:', error);
                setIsAuthenticated(false);
            }
        };

        checkTokenValidity();
    }, []);

    if (isAuthenticated === null) {
        return <div>Loading...</div>;
    }

    return isAuthenticated ? <>{children}</> : <Navigate to="/login" />;
};

export default ProtectedRoute;