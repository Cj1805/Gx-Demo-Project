import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../styles/RegistrationForm.css';
import HeadNavbar from '../components/HeadNavbar';
import Footer from '../components/Footer';
import { Button } from 'react-bootstrap';
import { Eye, EyeSlash } from 'react-bootstrap-icons';
import { useNavigate } from 'react-router-dom';
const staticData = window.staticData;
const errorData = window.errorData;
const labels = window.labels;

const RegistrationForm = () => {
    const { register, handleSubmit, formState: { errors }, reset, trigger } = useForm();
    const [showPassword, setShowPassword] = useState(false);
    const navigate = useNavigate();

    const passwordVisibility = () => {
        setShowPassword(!showPassword);
    };


    const onSubmit = async (data) => {

        const isFormValid = await trigger();

        if (isFormValid) {
            const url = staticData.endPoint.host + "/users/save"
            try {
                const response = await axios.post(url, data);
                console.log('Data sent successfully:', response.data);
                toast.success("Account Created Successfully!!!", {
                    position: toast.POSITION.TOP_RIGHT, autoClose: 2000
                });
                setTimeout(() => {
                    navigate('/login')
                }, 2000)
                reset();
            } catch (error) {
                if (error.response && error.response.data) {
                    const errorMsg = error.response.data;
                    if (errorMsg.includes(errorData.emailExist)) {
                        toast.error(errorData.emailExist, { autoClose: 1000 })
                    }
                    else if (errorMsg.includes(errorData.phoneExist)) {
                        toast.error(errorData.phoneExist, { autoClose: 1000 })
                    } else {
                        alert(errorData.unexpected);
                    }
                } else {
                    alert(errorData.network);
                }
            }
        }
    };


    const phoneRegex = /^[6-9][0-9]{9}$/;
    const nameRegex = /^[A-Za-z]+$/;

    return (
        <div>
            <HeadNavbar />
            <div className="container mt-5 mb-5 p-2 registeration">
                <h3>{labels.login.register}</h3>
                <form className='mt-5' onSubmit={handleSubmit(onSubmit)}>

                    <ul className='errors'>
                        {errors.firstName && (
                            <li className="text-danger">{errors.firstName.message}</li>
                        )}
                        {errors.lastName && (
                            <li className="text-danger">{errors.lastName.message}</li>
                        )}
                        {errors.phone && (
                            <li className="text-danger">{errors.phone.message}</li>
                        )}
                        {errors.email && (
                            <li className="text-danger">{errors.email.message}</li>
                        )}
                        {errors.password && (
                            <li className="text-danger">{errors.password.message}</li>
                        )}
                        {window.scrollTo({ top: 170, behavior: 'smooth' })}
                    </ul>
                    <div className="mb-3">
                        <label htmlFor="firstName" className="form-label">
                            {labels.registration.fName}
                        </label>
                        <input type="text" className="form-control" id="firstName"
                            style={errors.firstName ? { borderColor: 'red' } : { boxShadow: 'none' }}
                            {...register('firstName', {
                                required: errorData.required.firstName,
                                pattern: {
                                    value: nameRegex,
                                    message: errorData.validation.name,
                                },
                            })}
                        />

                    </div>
                    <div className="mb-3">
                        <label htmlFor="lastName" className="form-label">
                            {labels.registration.lName}
                        </label>
                        <input
                            type="text" className="form-control" id="lastName"
                            style={errors.lastName ? { borderColor: 'red' } : { boxShadow: 'none' }}
                            {...register('lastName', {
                                required: errorData.required.lastName,
                                pattern: {
                                    value: nameRegex,
                                    message: errorData.validation.name,
                                },
                            })}
                        />

                    </div>
                    <div className="mb-3">
                        <label htmlFor="phone" className="form-label">
                            {labels.registration.phone}
                        </label>
                        <input
                            type="text" className="form-control" id="phone"
                            style={errors.phone ? { borderColor: 'red' } : { boxShadow: 'none' }}
                            {...register('phone', {
                                required: errorData.required.phone,
                                pattern: {
                                    value: phoneRegex,
                                    message: errorData.validation.phone,
                                },
                            })}
                        />

                    </div>
                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">
                            {labels.login.email}
                        </label>
                        <input
                            type="email" className="form-control" id="email"
                            style={errors.email ? { borderColor: 'red' } : { boxShadow: 'none' }}
                            {...register('email', {
                                required: errorData.required.email,
                                pattern: {
                                    value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
                                    message: errorData.validation.email,
                                },
                            })}
                        />

                    </div>
                    <div className="mb-3">
                        <label htmlFor="password" className="form-label">
                            {labels.login.password}
                        </label>
                        <div className="pass">
                            <input
                                type={showPassword ? "text" : "password"}
                                className="form-control"
                                style={errors.password ? { borderColor: 'red' } : { boxShadow: 'none' }}
                                id="password"
                                {...register('password', {
                                    required: errorData.required.password,
                                    minLength: {
                                        value: 8,
                                        message: errorData.validation.password.length
                                    },
                                    pattern: {
                                        value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,16}$/,
                                        message: errorData.validation.password.pattern
                                    },
                                })}
                            />
                            <Button variant="outline-secondary"
                                className='passVisible'
                                onClick={passwordVisibility}>
                                {showPassword ? <EyeSlash /> : <Eye />}
                            </Button>
                        </div>
                        <button type="submit" className="btn btn-primary">
                            {labels.login.register}
                        </button>
                    </div>
                </form>
            </div>
            <Footer />
            <ToastContainer />
        </div>
    );
};

export default RegistrationForm;

