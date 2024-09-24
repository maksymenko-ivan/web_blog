import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../styles/common.css'; 
import '../styles/login.css';
import HeaderLogin from '../components/headers/HeaderLogin';

const Login: React.FC = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [showBanner, setShowBanner] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const validateToken = async () => {
      const token = localStorage.getItem('token');
      if (token) {
        try {
          const response = await axios.get('http://localhost:8080/api/v1/auth', {
            headers: {
              Authorization: `Bearer ${token}`
            }
          });
          console.log('Token validation response:', response);
          navigate('/');
        } catch (error) {
          console.error('Token validation failed:', error);
          navigate('/login');
        }
      }
    };

    validateToken();

    // Check for logout success flag
    if (localStorage.getItem('logoutSuccess') === 'true') {
      setShowBanner(true);
      localStorage.removeItem('logoutSuccess');
      setTimeout(() => {
        setShowBanner(false);
      }, 3000); // Hide banner after 3 seconds
    }
  }, [navigate]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/api/v1/login', {
        username,
        password
      }, {
        headers: {
          'Content-Type': 'application/json'
        }
      });

      localStorage.setItem('token', response.data.token);
      localStorage.setItem('loginSuccess', 'true');
      navigate('/');
    } catch (error) {
      if (axios.isAxiosError(error)) {
        setError(error.response?.data.message || 'Incorrect login or password');
      } else {
        setError('An unexpected error occurred');
      }
    }
  };

  return (
    <div>
      <HeaderLogin />
      {showBanner && <div className="logout-banner">You successfully logged out</div>}
      <main>
        <div className="container">
          <div className="left-column">
            <h1>W&P BLOG</h1>
            <p>Welcome to Write & Post – your ultimate platform for creating and sharing engaging blog content. Write & Post offers an intuitive and user-friendly experience for bloggers at any stage.
              Whether you’re an experienced writer or just starting out, our app provides a powerful set of tools to help you craft beautiful posts, connect with your audience, and grow your online presence.
              With customizable templates, advanced editing features, and a vibrant community, Write & Post is designed to inspire creativity and encourage meaningful interactions. Begin your blogging journey with us and make your voice heard with Write & Post!</p>
          </div>
          <div className="right-column">
            <h2>Sign In to W&P Blog</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <form onSubmit={handleSubmit}>
              <label htmlFor="username">Username or Email</label>
              <input
                type="text"
                id="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                placeholder="Username"
                required
                autoComplete="off"
              />
              <label htmlFor="password">Password</label>
              <input
                type="password"
                id="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Password"
                required
                autoComplete="off"
              />
              <button type="submit">Sign In</button>
            </form>
          </div>
        </div>
      </main>
    </div>
  );
};

export default Login;