import React from 'react';
import { Link } from 'react-router-dom';
import '../../styles/common.css'; 


const HeaderRegister: React.FC = () => {
  return (
    <header>
      <div className="logo">
        <Link to="/login">
          <img src="/files/Logo_black.png" alt="Logo" />
        </Link>
      </div>
      <nav>
        <Link to="/login" className="signup-link">SIGN IN</Link>
      </nav>
    </header>
  );
};

export default HeaderRegister;
