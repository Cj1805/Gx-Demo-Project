import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import logo from '../images/Himalaya-wellness-logo.jpeg';
import OrderData from '../components/OrderData';
import '../styles/Checkout.css'
import { Button, Form, Row } from 'react-bootstrap';
import { useFormData } from '../components/Custom/FormDataContext';
import BreadCrumbs from '../components/BreadCrumbs';

const Shipping = () => {

    const userData = JSON.parse(localStorage.getItem('userData'));
    const navigate = useNavigate()
    const [shipMethod, setShipMethod] = useState({
        name: 'Standard (3-5 Business Days)',
        value: 0
    })

    const {formData } = useFormData();
    const addressData = formData

    const [delivery, setDelivery] = useState(0);

    const handleDelivery = (e) => {
        setShipMethod(...[{ name: e.target.id, value: e.target.value }])
        setDelivery(e.target.value);
    }

    const paymentData = {
        shippingMethod: [
            { ship: shipMethod },
            { contact: userData.email }
        ]
    }

    const handlePayment = () => {
        navigate("/payment", { state: paymentData })
    }

    return (
        <div className='container'>
            <div className='mb-3' style={{ background: 'whitesmoke' }} ><Link to={'/'}><img style={{ width: '12%', marginLeft: '45%', paddingBottom: '2%' }} src={logo} alt='' /></Link></div>
            <BreadCrumbs/>
            <div className='checkoutInfo'>
                <div className='shipping'>
                    <div className='shippingInfo'>
                        <div style={{ borderBottom: '1px solid gray' }}>
                            <label>Contact</label>
                            <p>{userData.email}</p>
                         
                        </div>
                        <div>
                            <label>Ship To</label>
                            <p>{addressData.addressLine + "," + addressData.city + " " + addressData.state + " " + addressData.pinCode}</p>
                            <Link to={'/checkout'}>Change</Link>
                        </div>
                    </div>
                    <div className='shippingType mt-5 mb-5'>
                        <h5>Shipping method</h5>
                        <div className='shippingMethod'>
                            <div className='shipMethod' style={{ borderBottom: '1px solid gray' }}>
                                <Form.Check
                                    defaultChecked
                                    label="Standard (3-5 Business Days)"
                                    name="group1"
                                    type="radio"
                                    value={0}
                                    id={`Standard (3-5 Business Days)`}
                                    onChange={handleDelivery}
                                />
                                <p>Free</p>
                            </div>
                            <div className='shipMethod'>
                                <Form.Check
                                    label="Cash on Delivery"
                                    name="group1"
                                    type="radio"
                                    value={50}
                                    id={`Cash on Delivery`}
                                    onChange={handleDelivery}
                                />
                                <p>Rs. 50.00</p>
                            </div>
                        </div>
                    </div>
                    <Row style={{ flexWrap: 'nowrap', justifyContent: 'space-between', alignItems: 'center' }}>
                        <Link to={'/checkout'} style={{ width: 'fit-content', textDecoration: 'none', color: 'orange' }}>Return to Information</Link>
                        <Button style={{ width: 'fit-content', backgroundColor: 'orange' }} onClick={handlePayment}>Continue to Payment</Button>
                    </Row>
                </div>
                <OrderData charge={delivery} />
            </div>
        </div>
    )
}

export default Shipping