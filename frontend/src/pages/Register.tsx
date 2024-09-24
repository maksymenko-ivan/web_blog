import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../styles/common.css'; 
import '../styles/register.css';
import HeaderRegister from '../components/headers/HeaderRegister';

const Register: React.FC = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [rePassword, setRePassword] = useState('');
  const [error, setError] = useState('');
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
          navigate('/register');
        }
      }
    };

    validateToken();
  }, [navigate]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (password !== rePassword) {
      setError('Passwords do not match');
      return;
    }
    try {
      const response = await axios.post('http://localhost:8080/api/v1/register', {
        firstName,
        lastName,
        username,
        password
      }, {
        headers: {
          'Content-Type': 'application/json'
        }
      });
      localStorage.setItem('token', response.data.token);
      localStorage.setItem('registerSuccess', 'true');
      window.location.href = '/';
      console.log('Registration successful:', response.data);
    } catch (error) {
      if (axios.isAxiosError(error)) {
        if (error.response?.status === 409) {
          setError(`Username \'${username}\' already exists`);
        } else {
          setError(error.response?.data.message || 'Registration failed');
        }
        console.error('Registration failed:', error.response?.data || error.message);
      } else {
        console.error('An unexpected error occurred:', error);
        setError('An unexpected error occurred');
      }
    }
  };

  return (
    <div>
      <HeaderRegister />
      <main>
        <div className="container">
          <div className="left-column">
            <h1>W&P BLOG</h1>
            <p>Welcome to Write & Post – your ultimate platform for creating and sharing engaging blog content. Write & Post offers an intuitive and user-friendly experience for bloggers at any stage.
              Whether you’re an experienced writer or just starting out, our app provides a powerful set of tools to help you craft beautiful posts, connect with your audience, and grow your online presence.
              With customizable templates, advanced editing features, and a vibrant community, Write & Post is designed to inspire creativity and encourage meaningful interactions. Begin your blogging journey with us and make your voice heard with Write & Post!</p>
          </div>
          <div className="right-column">
            <h2>Sign Up to W&P Blog</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <form onSubmit={handleSubmit}>
              <label htmlFor="firstName">First Name</label>
              <input
                type="text"
                id="firstName"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
                placeholder="First Name"
                required
                autoComplete="off"
              />
              <label htmlFor="lastName">Last Name</label>
              <input
                type="text"
                id="lastName"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
                placeholder="Last Name"
                required
                autoComplete="off"
              />
              <label htmlFor="username">Username</label>
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
              <label htmlFor="rePassword">Re-enter Password</label>
              <input
                type="password"
                id="rePassword"
                value={rePassword}
                onChange={(e) => setRePassword(e.target.value)}
                placeholder="Re-enter Password"
                required
                autoComplete="off"
              />
              <button type="submit">Sign Up</button>
            </form>
          </div>
        </div>
      </main>
    </div>
  );
};

export default Register;