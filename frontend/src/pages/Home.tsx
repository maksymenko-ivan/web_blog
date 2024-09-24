import React, { useEffect, useState } from 'react';
import '../styles/common.css';
import '../styles/home.css';
import Header from '../components/headers/Header';

const Home: React.FC = () => {
  const [showLoginBanner, setShowLoginBanner] = useState(false);
  const [showRegisterBanner, setShowRegisterBanner] = useState(false);

  useEffect(() => {
    if (localStorage.getItem('loginSuccess') === 'true') {
      setShowLoginBanner(true);
      localStorage.removeItem('loginSuccess');
      setTimeout(() => {
        setShowLoginBanner(false);
      }, 3000); // Hide banner after 3 seconds
    }
    if (localStorage.getItem('registerSuccess') === 'true') {
      setShowRegisterBanner(true);
      localStorage.removeItem('registerSuccess');
      setTimeout(() => {
        setShowRegisterBanner(false);
      }, 3000); // Hide banner after 3 seconds
    }
  }, []);


  return (
    <div>
      <Header />
      {showLoginBanner && <div className="success-banner">You successfully logged in</div>}
      {showRegisterBanner && <div className="success-banner">You successfully registered</div>}
      <main>
        
      </main>
    </div>
  );
};

export default Home;