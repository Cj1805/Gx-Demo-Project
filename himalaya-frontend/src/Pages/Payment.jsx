import React, { useEffect, useState } from 'react'
import { Link, useLocation, useNavigate } from 'react-router-dom'
import logo from '../images/Himalaya-wellness-logo.jpeg';
import '../styles/Checkout.css'
import { Button, Row } from 'react-bootstrap';
import OrderData from '../components/OrderData';
import { useFormData } from '../components/Custom/FormDataContext';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import BreadCrumbs from '../components/BreadCrumbs';
const staticData = window.staticData;

const Payment = () => {
    const location = useLocation();
    const data = location.state
    const userData = JSON.parse(localStorage.getItem('userData'))
    const { formData } = useFormData();
    const [paymentStatus, setPaymentStatus] = useState("");
    const navigate = useNavigate();
    const token = localStorage.getItem('user');

    // console.log(formData)
    // console.log(data)

    useEffect(() => {
        setPaymentStatus(data.shippingMethod[0].ship.value === '50' ? "no" : "yes")

    }, [data.shippingMethod]);
    // console.log(paymentStatus)

    const completeOrder = async () => {
        try {

            const response = await axios.post(staticData.endPoint.host + `/checkout/shipAddress/${userData.id}`, formData,{
                headers:{
                    Authorization: `Bearer ${token}`
                }
            })
            // console.log("ship", response.data.addressID)
            const res = await axios.post(staticData.endPoint.host + `/checkout/saveOrder/${userData.id}/${response.data.addressID}/${paymentStatus}`,null,{
                headers:{
                    Authorization: `Bearer ${token}`
                }
            })
            console.log("Order", res.data)
            const respond = await axios.delete(staticData.endPoint.host + `/cart/clearCart/${userData.cart.cartId}`,{
                headers:{
                    Authorization: `Bearer ${token}`
                }
            })
            console.log("Cart", respond.data)
            toast.success("Order Placed!!!", { autoClose: 1000 })
            setTimeout(() => {
                navigate("/")
            }, 2000)
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <div className='container'>
            <div className='mb-3' style={{ background: 'whitesmoke' }} ><Link to={'/'}><img style={{ width: '12%', marginLeft: '45%', paddingBottom: '2%' }} src={logo} alt='' /></Link></div>
            <BreadCrumbs/>
            <div className='checkoutInfo'>
                <div className='shipping'>
                    <div className='shippingInfo mb-5'>
                        <div style={{ borderBottom: '1px solid gray' }}>
                            <label>Contact</label>
                            <p>{data.shippingMethod[1].contact}</p>
                        </div>
                        <div style={{ borderBottom: '1px solid gray' }}>
                            <label>Ship To</label>
                            <p>{formData.addressLine + "," + formData.city + " " + formData.state + " " + formData.pinCode}</p>
                            <Link to={'/checkout'}>Change</Link>
                        </div>
                        <div >
                            <label>Shipping Method</label>
                            <p>{data.shippingMethod[0].ship.name}</p>
                            <Link to={'/shipping'}>Change</Link>
                        </div>
                    </div>
                    <Row style={{ flexWrap: 'nowrap', justifyContent: 'space-between', alignItems: 'center' }}>
                        <Link to={'/shipping'} style={{ width: 'fit-content', textDecoration: 'none', color: 'orange' }}>Return to Shipping</Link>
                        <Button style={{ width: 'fit-content', backgroundColor: 'orange' }} onClick={completeOrder} >Complete Order</Button>
                    </Row>
                </div>
                <OrderData charge={data.shippingMethod[0].ship.value} />
            </div>
            <ToastContainer />
        </div>
    )
}

export default Payment