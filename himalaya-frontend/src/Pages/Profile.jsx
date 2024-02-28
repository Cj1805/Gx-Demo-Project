/* eslint-disable react-hooks/exhaustive-deps */
import React, { useContext, useEffect, useState } from 'react'
import HeadNavbar from '../components/HeadNavbar'
import Footer from '../components/Footer'
import { Button } from 'react-bootstrap'
import { Link, useNavigate } from 'react-router-dom'
import { ToastContainer, toast } from 'react-toastify'
import '../styles/Profile.css'
import { MyContext } from '../components/Custom/MyContext'
import BreadCrumbs from '../components/BreadCrumbs'


const Profile = () => {
    const navigate = useNavigate();
    const userData = JSON.parse(localStorage.getItem('userData'))
    const { fetchData } = useContext(MyContext);
    const [address, setAddress] = useState([]);
    const [order, setOrder] = useState([]);
    const [addressError, setAddressError] = useState('');
    const [orderError, setOrderError] = useState('');



    const handleCLick = () => {
        toast.success("LogOut successfull!!", { autoClose: 1000 })
        setTimeout(() =>
            navigate('/'), 2000);
        localStorage.clear();
    }

    const defaultAddress = () => {
        fetchData(`/users/getAddress/${userData.id}`)
            .then((response) => {
                setAddress(response);
            })
            .catch((error) => {
                setAddressError(error.response.data)
            })
    }

    const orderHistory = () => {
        fetchData(`/checkout/getOrders/${userData.id}`)
            .then((response) => {
                setOrder(response)
            })
            .catch((error) => {
                setOrderError(error.response.data);
            })
    }

    useEffect(() => {
        defaultAddress();
        orderHistory();
    }, [])

    return (
        <div>
            <HeadNavbar />
            <BreadCrumbs />
            <h2>Profile</h2>
            <div className='container'>
                <div className='profile'>
                    <p>{userData.firstName + " " + userData.lastName}</p>

                    <Button className='logout' onClick={handleCLick} >Log Out</Button>
                </div>
                <div className='order' style={{ display: 'flex', justifyContent: 'space-between' }}>
                    <div className='profileDetails mt-5'>
                        <h5> Default Address</h5>
                        {addressError ? <p>{addressError}</p> :
                            address.length > 0 ? <div className='address'>
                                <p>{address[0].firstName + " " + address[0].lastName}</p>
                                <p>{address[0].addressLine1}</p>
                                <p>{address[0].addressLine2}</p>
                                <p>{address[0].city}</p>
                                <p>{address[0].state}</p>
                                <p>{address[0].pinCode}</p>
                            </div> : null}

                        <Link to={'/address'}>View Addresses</Link>
                    </div>
                    <div className='profileDetails order mt-5 p-2'>
                        <h4 className='orderLabel'> Order History</h4>
                        <div className='orderHistory'>
                            {orderError ? <p>{orderError}</p> :
                                order.map((orderItem, index) => (
                                    <div key={index} className='orderItem m-4'>
                                        <p><b>Order Date:</b> {orderItem.orderDate}</p>
                                        <div style={{ border: '2px solid #98cfb8b5', padding: '2%' }}>
                                            {orderItem.orderItem.map((item, itemIndex) => (
                                                <div key={itemIndex} className='orderDetails'>
                                                    <img src={item.product.imageURL} alt='' style={{ width: '12%' }}></img>
                                                    <p>{item.product.productName}</p>
                                                    <p> Quantity:- {item.quantity}</p>
                                                </div>
                                            ))}
                                        </div>
                                        <p><b>Total Price:</b><span style={{ fontSize: '1em', marginLeft: '10px' }}>₹{orderItem.totalPrice}</span> </p>
                                        <p><b>Shipping Address:</b> {orderItem.shipAddress.addressLine}, {orderItem.shipAddress.city}, {orderItem.shipAddress.state}</p>
                                        <hr></hr>
                                    </div>
                                ))}
                        </div>
                    </div>
                </div>
            </div>
            <Footer />
            <ToastContainer />
        </div>
    )
}

export default Profile