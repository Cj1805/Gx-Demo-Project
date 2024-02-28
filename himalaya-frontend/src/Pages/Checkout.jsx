/* eslint-disable no-unused-vars */
/* eslint-disable react-hooks/exhaustive-deps */
import React, { useContext, useEffect, useState } from 'react'
import { MyContext } from '../components/Custom/MyContext'
import { ToastContainer } from 'react-toastify'
import '../styles/Checkout.css'
import { Link, useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { Button, Col, Row, FloatingLabel } from 'react-bootstrap';
import { statesInIndia } from './Address';
import logo from '../images/Himalaya-wellness-logo.jpeg';
import OrderData from '../components/OrderData';
import { useFormData } from '../components/Custom/FormDataContext';
import BreadCrumbs from '../components/BreadCrumbs';
const staticData = window.staticData;
const errorData = window.errorData;

const Checkout = () => {

    const userData = JSON.parse(localStorage.getItem('userData'))
    const [address, setAddress] = useState([]);
    const { fetchData } = useContext(MyContext);
    const { handleSubmit, register, reset, clearErrors } = useForm();
    const [selectAddress, setSelectAddress] = useState('');
    const {formData, dispatch} = useFormData();
    const navigate = useNavigate();

    useEffect(() => {
        fetchData(`/users/getAddress/${userData.id}`)
            .then((response) => {
                setAddress(response)
            }).catch((error) => {
                console.error("Error fetching address " + error);
            });
    }, []);

    const handleAddressChange = (event) => {
        const selectedAddressId = parseInt(event.target.value);
        setSelectAddress(selectedAddressId);
        clearErrors();

        if (selectedAddressId > 0) {
            const selectedAddressData = address.find(add => add.addressID === selectedAddressId);
            if (selectedAddressData) {
                reset({
                    firstName: selectedAddressData.firstName,
                    lastName: selectedAddressData.lastName,
                    addressLine: selectedAddressData.addressLine2,
                    city: selectedAddressData.city,
                    state: selectedAddressData.state,
                    pinCode: selectedAddressData.pinCode,
                    phone: selectedAddressData.phone
                });
            }
        } else if (selectedAddressId === 0) {
            reset({
                firstName: "",
                lastName: "",
                addressLine: "",
                city: "",
                state: "",
                pinCode: null,
                phone: ""
            });
        }
    };


    const handleFormSubmit = (formData) => {
        dispatch({type:'SET', payLoad: formData})
        navigate('/shipping')
    };


    return (
        <div className='container'>
            <div className='mb-3' style={{background:'whitesmoke'}} ><Link to={'/'}><img style={{width:'12%',marginLeft:'45%', paddingBottom:'2%'}} src={logo} alt='' /></Link></div>
            <BreadCrumbs/>
            <div className='checkoutInfo'>
                <div className='shippingAddress'>
                    {staticData.checkout.ship}
                    <form onSubmit={handleSubmit(handleFormSubmit)} className=' container mt-5 mb-5'>
                        <Row>
                            <FloatingLabel controlId="savedAddressSelect" label="Saved Address">
                                <select
                                    className="form-select"
                                    value={selectAddress}
                                    onChange={handleAddressChange}
                                >
                                    <option value='0'>{staticData.checkout.newAddress}</option>
                                    {address.map((add) => (
                                        <option key={add.addressId} value={add.addressID}>
                                            {add.addressLine2 + "," + add.city + "," + add.state + "," + add.firstName + " " + add.lastName}
                                        </option>
                                    ))}
                                </select>
                            </FloatingLabel>
                        </Row>
                        <Row>
                            <Col className='names' style={{ paddingLeft: '2%' }}>
                                <FloatingLabel controlId="firstName" label="First Name">
                                    <input
                                        {...register('firstName', { required: errorData.required.firstName })}
                                        className="form-control"
                                    />
                                </FloatingLabel>
                            </Col>
                            <Col className='names'>
                                <FloatingLabel controlId="lastName" label="Last Name">
                                    <input
                                        {...register('lastName')}
                                        className="form-control"
                                    />
                                </FloatingLabel>
                            </Col>
                        </Row>
                        <Row>
                            <Col>
                                <FloatingLabel controlId="addressLine" label="Address">
                                    <input
                                        {...register('addressLine', { required: errorData.required.address })}
                                        className="form-control"
                                    />
                                </FloatingLabel>
                            </Col>
                        </Row>
                        <Row className='checkoutCity'>
                            <div>
                                <FloatingLabel controlId="city" label="City">
                                    <input
                                        {...register('city', { required: errorData.required.city })}
                                        className="form-control"
                                    />
                                </FloatingLabel>
                            </div>
                            <div className='select'>
                                <FloatingLabel controlId="state" label="State">
                                    <select
                                        {...register('state', { required: errorData.required.state })}
                                        className="form-select"
                                    >
                                        {statesInIndia.map((state, index) => (
                                            <option key={index} value={state}>{state}</option>
                                        ))}
                                    </select>
                                </FloatingLabel>
                            </div>
                            <div>
                                <FloatingLabel controlId="pinCode" label="Pincode">
                                    <input
                                        {...register('pinCode', {
                                            required: errorData.required.pincode,
                                            pattern: {
                                                value: /^[1-9][0-9]{5}$/,
                                                message: 'Invalid pincode'
                                            }
                                        })}
                                        className="form-control"
                                    />
                                </FloatingLabel>
                            </div>
                        </Row>
                        <Row>
                            <FloatingLabel controlId="phone" label="Phone">
                                <input
                                    {...register('phone', {
                                        required: errorData.required.phone,
                                        pattern: {
                                            value: /^[6-9][0-9]{9}$/,
                                            message:errorData.validation.phone
                                        }
                                    })}
                                    className="form-control"
                                />
                            </FloatingLabel>
                        </Row>
                        <Row style={{flexWrap:'nowrap', justifyContent:'space-between' ,alignItems:'center'}}>
                            <Link to={'/'} style={{width:'fit-content', textDecoration:'none', color:'orange'}}>{staticData.checkout.return}</Link>
                            <Button style={{width:'fit-content',backgroundColor:'orange'}} type="submit">{staticData.checkout.continue}</Button>
                        </Row>
                    </form>
                </div>
                <OrderData charge ={0}/>
                <ToastContainer />
            </div>
        </div>
    )
}

export default Checkout