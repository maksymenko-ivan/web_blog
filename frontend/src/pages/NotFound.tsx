import React from 'react';
import '../styles/common.css'; // Import common styles
import Header from '../components/headers/Header';

const NotFound: React.FC = () => {
  return (
    <><Header /><div className="not-found">
          <h1>404 - Page Not Found</h1>
          <p>The page you are looking for does not exist.</p>
          <a href="/">Go to Home</a>
      </div></>
  );
};

export default NotFound;
