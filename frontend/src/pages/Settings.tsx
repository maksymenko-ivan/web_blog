import '../styles/common.css'; // Import common styles
import '../styles/settings.css'; // Import home-specific styles
import Header from '../components/headers/Header';
import { useEffect, useState } from 'react';
import axios from 'axios';

const Settings: React.FC = () => {
  const [username, setUsername] = useState<string>('');
  const [newUsername, setNewUsername] = useState<string>('');
  const [firstName, setFirstName] = useState('');
  const [newFirstName, setNewFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [newLastName, setNewLastName] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const token = localStorage.getItem('token');
  const [showSuccessfullBanner, setShowSuccessfullBanner] = useState(false);
  const [showUnsuccessfullBanner, setShowUnsuccessfullBanner] = useState(false);



  const handleFirstNameInput = (event: React.ChangeEvent<HTMLInputElement>) => {
    setNewFirstName(event.target.value);
  };  

  const handleChangeFirstName = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if(newFirstName !== firstName && newFirstName !== ''){
        const response = await axios.post(
            'http://localhost:8080/api/v1/change-first-name',
            {}, // No body content
            {
                params: {
                    firstName: newFirstName 
                },
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }
        );
        setFirstName(newFirstName);
        setShowSuccessfullBanner(true);
        setTimeout(() => {
          setShowSuccessfullBanner(false);
        }, 3000); // Hide banner after 3 seconds
      }else{
        setShowUnsuccessfullBanner(true);
        setTimeout(() => {
          setShowUnsuccessfullBanner(false);
        }, 3000); // Hide banner after 3 seconds
      }
    } catch (error) {
        if (axios.isAxiosError(error)) {
          setError(error.response?.data.message || 'Error');
        } else {
          setError('An unexpected error occurred');
        }
    }
  };

  const handleLastNameInput = (event: React.ChangeEvent<HTMLInputElement>) => {
    setNewLastName(event.target.value); 
  };  

  const handleChangeLastName = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if(newLastName !== lastName && newLastName !== ''){
        const response = await axios.post(
            'http://localhost:8080/api/v1/change-last-name',
            {}, // No body content
            {
                params: {
                    lastName: newLastName 
                },
                headers: {
                    Authorization: `Bearer ${token}` 
                }
            }
        );
        setShowSuccessfullBanner(true);
        setTimeout(() => {
          setShowSuccessfullBanner(false);
        }, 3000); // Hide banner after 3 seconds
      }else{
        setShowUnsuccessfullBanner(true);
        setTimeout(() => {
          setShowUnsuccessfullBanner(false);
        }, 3000); // Hide banner after 3 seconds
      }
    } catch (error) {
        if (axios.isAxiosError(error)) {
          setError(error.response?.data.message || 'Error');
        } else {
          setError('An unexpected error occurred');
        }
    }
  };

  const handlePasswordInput = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };  

  const handleChangePassword = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if(password !== ''){
        const response = await axios.post(
            'http://localhost:8080/api/v1/change-password',
            {}, // No body content
            {
                params: {
                    password: password
                },
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }
        );
        setShowSuccessfullBanner(true);
        setTimeout(() => {
          setShowSuccessfullBanner(false);
        }, 3000); // Hide banner after 3 seconds
      }else{
        setShowUnsuccessfullBanner(true);
        setTimeout(() => {
          setShowUnsuccessfullBanner(false);
        }, 3000); // Hide banner after 3 seconds
      }
    } catch (error) {
        if (axios.isAxiosError(error)) {
          setError(error.response?.data.message || 'Error');
        } else {
          setError('An unexpected error occurred');
        }
    }
  };

  const handleUsernameInput = (event: React.ChangeEvent<HTMLInputElement>) => {
    setNewUsername(event.target.value);
  };  

  const handleChangeUserame = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if(newUsername !== '' && username != newUsername){
        const response = await axios.post(
            'http://localhost:8080/api/v1/change-username',
            {}, // No body content
            {
                params: {
                    username: newUsername 
                },
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }
        );
        setShowSuccessfullBanner(true);
        setTimeout(() => {
          setShowSuccessfullBanner(false);
        }, 3000); // Hide banner after 3 seconds
      }else{
        setShowUnsuccessfullBanner(true);
        setTimeout(() => {
          setShowUnsuccessfullBanner(false);
        }, 3000); // Hide banner after 3 seconds
      }
    } catch (error) {
        if (axios.isAxiosError(error)) {
          setError(error.response?.data.message || 'Error');
        } else {
          setError('An unexpected error occurred');
        }
    }
  };

  useEffect(() => {
    const fetchUserData = async () => {
      const token = localStorage.getItem('token');
      if (token) {
        const response = await axios.get('http://localhost:8080/api/v1/me', {
          headers: { Authorization: `Bearer ${token}` }
        });
        setUsername(response.data.username);
        setFirstName(response.data.firstName);
        setLastName(response.data.lastName);
      }
    };
    fetchUserData();
  }, []);

  return (
    <div>
      {showSuccessfullBanner && <div className="success-banner">Successfull</div>}
      {showUnsuccessfullBanner && <div className="unsuccess-banner">Data hasn't been changed!</div>}
      <Header />
      <main>
        <div className='settings-data'>
            <h1>Account Settings</h1>
            <p>Manage your account information and settings here.</p>
            <div className='settings-change-data'>
              <label className='info-label'>Username:</label>
              <input className='table-username' type="text" defaultValue={username} onChange={handleUsernameInput}/>
              <button className='changer' onClick={handleChangeUserame}>Change username</button><br/>
              <label className='info-label'>First Name:</label>
              <input className='info-input' type="text" defaultValue={firstName} onChange={handleFirstNameInput}/>
              <button className='changer' onClick={handleChangeFirstName}>Change first name</button><br/>
              <label className='info-label'>Last Name:</label>
              <input className='info-input' type="text" defaultValue={lastName} onChange={handleLastNameInput}/>
              <button className='changer'  onClick={handleChangeLastName}>Change last name</button><br/>
              <label className='info-label'>Password:</label>
              <input className='info-input input-password' type="password" onChange={handlePasswordInput} placeholder='**********'/>
              <button className='changer' onClick={handleChangePassword}>Change password</button>
            </div>
        </div>
      </main>
    </div>
  );
};

export default Settings;
