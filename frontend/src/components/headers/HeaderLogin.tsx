import React from 'react';
import { Link } from 'react-router-dom';
import '../../styles/common.css';

const HeaderLogin: React.FC = () => {
  return (
    <header>
      <div className="logo">
        <Link to="#">
          <img src="/files/Logo_black.png" alt="Logo" />
        </Link>
      </div>
      <nav>
        <Link to="/register" className="signup-link">SIGN UP</Link>
      </nav>
    </header>
  );
};

export default HeaderLogin;

