import React, {  useState } from 'react';
import axios from 'axios';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import { Link, useNavigate } from 'react-router-dom';
import { Eye, EyeSlash } from 'react-bootstrap-icons';
import HeadNavbar from '../components/HeadNavbar';
import Footer from '../components/Footer';
import '../styles/Login.css'
import { ToastContainer, toast } from 'react-toastify';
// import { MyContext } from '../components/Custom/MyContext';
const staticData = window.staticData;
const errorData = window.errorData;
const labels = window.labels;

const Login = () => {
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [error, setError] = useState('');
    // const {fetchData} = useContext(MyContext);

    const handleEmailChange = (e) => {
        setEmail(e.target.value);
    };

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    };

    const passwordVisibility = () => {
        setShowPassword(!showPassword);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!email || !password) {
            setError(errorData.loginMandatory);
            return;
        }

        try {
            const url = staticData.endPoint.host + "/users/login"
            const response = await axios.post(url, null, {
                params: {
                    email: email,
                    password: password
                }
            });
            const responseData = response.data;
            localStorage.setItem('user', responseData);

            const token = localStorage.getItem('user');

            axios.get("http://localhost:8080/users/userInfo",{
                headers:{
                    Authorization: `Bearer ${token}`
                }
            }).then((response)=>{
                localStorage.setItem('userData',JSON.stringify(response.data))
            })
            if (response.status === 200) {
                toast.success("Login successful", { autoClose: 1000 })
                setTimeout(() =>
                    navigate('/')
                    , 2000)
            } else {
                setError(errorData.loginInvalid);
            }
        } catch (error) {
            if (error.response) {
                setError(error.response.data);
            } else if (error.request) {
                setError(errorData.network);
            } else {
                setError(errorData.unexpected);
            }
        }
    };

    return (
        <div>
            <HeadNavbar />
            <div className='container mt-5 mb-5 p-2 login'>
                <h3>{labels.login.login}</h3>
                <Form onSubmit={handleSubmit}>
                    <Form.Group className="mb-3" controlId="formBasicEmail">
                        <Form.Label>{labels.login.email}</Form.Label>
                        <Form.Control className='formEmail'
                            type="email"
                            placeholder={labels.login.email}
                            value={email}
                            onChange={handleEmailChange}
                        />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formBasicPassword">
                        <Form.Label className='formPassLabel'><span>{labels.login.password}</span>
                            <a href='/#forget'>{labels.login.forget}</a>
                        </Form.Label>
                        <div className="pass">
                            <Form.Control className='formPassword'
                                type={showPassword ? "text" : "password"}
                                placeholder={labels.login.password}
                                value={password}
                                onChange={handlePasswordChange}
                            />
                            <Button variant="outline-secondary viewPass" onClick={passwordVisibility}>
                                {showPassword ? <EyeSlash /> : <Eye />}
                            </Button>
                        </div>
                    </Form.Group>
                    {error && <p className='error'>{error}</p>}
                    <Button variant="primary" type="submit"> Login </Button>
                </Form>
                <div className='register'>
                    <Link to='/register'>{labels.login.register}</Link>
                </div>
            </div>
            <Footer />
            <ToastContainer />
        </div>
    );
};

export default Login;