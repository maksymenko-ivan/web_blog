import { Link, useNavigate } from 'react-router-dom';
import '../../styles/common.css';
import axios from 'axios';

const Header: React.FC = () => {
  const navigate = useNavigate();

  const handleLogout = async () => {
    const token = localStorage.getItem('token');

    if (token) {
      await axios.post('http://localhost:8080/api/v1/logout', {}, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      localStorage.removeItem('token');
      localStorage.setItem('logoutSuccess', 'true');
    }

    navigate('/login');
  };

  return (
    <header>
      <div className="logo">
        <Link to="/">
          <img src="/files/Logo_black.png" alt="Logo" />
        </Link>
      </div>
      <nav>
        <Link to="/settings" className="signup-link settings">Settings</Link>
        <button onClick={handleLogout} className="signup-link logout">LOGOUT</button>
      </nav>
    </header>
  );
};

export default Header;