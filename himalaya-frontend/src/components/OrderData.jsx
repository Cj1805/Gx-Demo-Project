/* eslint-disable react-hooks/exhaustive-deps */
import React, { useContext, useEffect, useState } from 'react'
import { MyContext } from './Custom/MyContext';
import { toast } from 'react-toastify';
import { Badge } from '@mui/material';

const OrderData = (charge) => {

    const userData = JSON.parse(localStorage.getItem('userData'))
    const [data, setData] = useState([]);
    const {fetchData} = useContext(MyContext);

    const delivery = parseFloat(charge.charge)

    useEffect(() => {
        if (userData) {
            fetchData(`/cart/viewCart/${userData.cart.cartId}`)
                .then((response) => {
                    setData(response);
                }).catch((error) => {
                    toast.error("Login First");
                });
        }
    }, []);

    const calulateItemPrice = (item) => {
        return item.product.price * item.quantity;
    }

    const calculateTotal = () => {
        let total = 0;
        data.forEach((item) => {
            total += item.product.price * item.quantity;
        })
        return total;
    }


  return (
    <div className='container checkoutProduct'>
                    <div className='mt-5'>
                        {data.map((item) => (
                            <div key={item.product.productId} className='checkoutItem'>
                                <div className='cartImg'>
                                    <Badge badgeContent={item.quantity} color="primary">
                                        <img src={item.product.imageURL} alt=''></img>
                                    </Badge>
                                </div>
                                <div className='checkoutItemDesc'>
                                    <p>{item.product.productName}</p>
                                    <p>₹{calulateItemPrice(item)}</p>
                                </div>
                            </div>
                        ))}
                    </div>
                    <div className='subTotal'>
                        <p><span>SubTotal</span> <span>₹{calculateTotal()}</span></p>
                        <p><span>Shipping</span><span>{delivery === 0? "Free" : "₹ "+delivery }</span></p>
                    </div>
                    <div>
                        <p className='Total'><span>Total</span><span className='total'>₹{calculateTotal()+delivery}</span></p>
                    </div>
                </div>
  )
}

export default OrderData