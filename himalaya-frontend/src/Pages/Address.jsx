/* eslint-disable react-hooks/exhaustive-deps */
import React, { useState, useEffect, useContext } from 'react';
import { useForm } from 'react-hook-form';
import axios from 'axios';
import HeadNavbar from '../components/HeadNavbar';
import { Button, Col, Row } from 'react-bootstrap';
import '../styles/Address.css'
import { MyContext } from '../components/Custom/MyContext';
import { ToastContainer, toast } from 'react-toastify';
import BreadCrumbs from '../components/BreadCrumbs';
const staticData = window.staticData;
const labels = window.labels;

export const statesInIndia = ['Andhra Pradesh',
    'Arunachal Pradesh', 'Assam', 'Bihar', 'Chhattisgarh', 'Goa',
    'Gujarat', 'Haryana', 'Himachal Pradesh','Hyderabad', 'Jharkhand', 'Karnataka',
    'Kerala', 'Madhya Pradesh', 'Maharashtra', 'Manipur',
    'Meghalaya', 'Mizoram', 'Nagaland', 'Odisha', 'Punjab',
    'Rajasthan', 'Sikkim', 'Tamil Nadu', 'Telangana',
    'Tripura', 'Uttar Pradesh', 'Uttarakhand', 'West Bengal'];

const Address = () => {

    const [form, setForm] = useState(false);
    const userData = JSON.parse(localStorage.getItem('userData'))
    const { fetchData } = useContext(MyContext);
    const { handleSubmit, register , reset } = useForm();
    const [address, setAddress] = useState([])
    const token = localStorage.getItem('user');


    const onSubmit = async (data) => {
        try {
            const response = await axios.post(staticData.endPoint.host + `/users/saveAddress/${userData.id}`, data,{
                headers:{
                    Authorization: `Bearer ${token}`
                }
            });
            toast.success("Address Saved", { autoClose: 1000 })
            setForm(false)
            reset();
            fetchDataAndUpdate();
            console.log('Data successfully submitted:', response.data);
        } catch (error) {
            console.error('Error submitting data:', error);
        }
    };

    const deleteAddress = async (addressId)=>{
        try{
            const response = await axios.delete(staticData.endPoint.host + `/users/delete/${userData.id}/${addressId}`,{
                headers:{
                    Authorization: `Bearer ${token}`
                }
            })
            fetchDataAndUpdate();
            toast.success(response.data,{autoClose:1000});
        }catch(error){
            console.log(error)
        }
    }

    const fetchDataAndUpdate = async ()=>{
        try{
            const response = await fetchData(`/users/getAddress/${userData.id}`)
            setAddress(response);
        }catch(error){
            console.log(error.response.data)
        }
    }

    useEffect(() => {
       fetchDataAndUpdate();
    }, [])


    return (
        <div>
            <HeadNavbar />
            <BreadCrumbs/>
            <div className='container mt-5'>
                <h5>{labels.address.address}</h5>
                <Button className='formDisplay' onClick={() => { setForm(!form);}} >{labels.address.add}</Button>
                {form &&
                    <form onSubmit={handleSubmit(onSubmit)} className='addressForm container mt-5 mb-5'>
                        <Row >
                            <Col className='names'>
                                <label>{labels.registration.fName}</label>
                                <input {...register('firstName',
                                    { required: 'First name is required' })}
                                />
                            </Col>
                            <Col className='names'>
                                <label>{labels.registration.lName}</label>
                                <input {...register('lastName')} />
                            </Col>
                        </Row>
                        <Row><label>{labels.address.addLine1}</label>
                            <input {...register('addressLine1',
                                { required: 'Address Line 1 is required' })}
                            />
                        </Row>
                        <Row>
                            <label>{labels.address.addLine2}</label>
                            <input {...register('addressLine2')} />

                            <label>{labels.address.city}</label>
                            <input {...register('city',
                                { required: 'City is required' })}
                            />
                        </Row>
                        <Row>
                            <label>{labels.address.state}</label>
                            <select {...register('state',
                                { required: 'Please select a state' })}>
                                {statesInIndia.map((state, index) => (
                                    <option key={index} value={state}>{state}</option>
                                ))}
                            </select>
                        </Row>
                        <Row>
                            <Col className='names'>
                                <label>{labels.address.pincode}</label>
                                <input
                                    {...register('pinCode', {
                                        required: 'Pincode is required',
                                        pattern: {
                                            value: /^[1-9][0-9]{5}$/,
                                            message: 'Invalid pincode'
                                        }
                                    })}
                                />
                            </Col>
                            <Col className='names'>
                                <label>{labels.registration.phone}</label>
                                <input
                                    {...register('phone', {
                                        required: 'Phone is required',
                                        pattern: {
                                            value: /^[6-9][0-9]{9}$/,
                                            message: 'Invalid phone number'
                                        }
                                    })}
                                />
                            </Col>
                        </Row>
                        <Button type="submit">{labels.address.add}</Button>
                    </form>
                }
                {address.map((add) => (
                    <div className='displayAddress'>
                        <p>{add.firstName + " " + add.lastName}</p>
                        <p>{add.addressLine1}</p>
                        <p>{add.addressLine2}</p>
                        <p>{add.city + " " + add.state}</p>
                        <p>{add.pinCode}</p>
                        <Button onClick={()=> (deleteAddress(add.addressID))}>{labels.delete}</Button>
                    </div>
                ))}
            </div>
            <ToastContainer />
        </div>
    );
};

export default Address;
