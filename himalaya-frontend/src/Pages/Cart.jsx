/* eslint-disable react-hooks/exhaustive-deps */
import axios from 'axios';
import React, { useContext, useEffect, useState } from 'react'
import { ToastContainer, toast } from 'react-toastify';
import '../styles/Cart.css';
import { Button } from 'react-bootstrap';
import { RiDeleteBinLine } from "react-icons/ri";
import { MyContext } from '../components/Custom/MyContext';
import { Link, useNavigate } from 'react-router-dom';
const staticData = window.staticData;


const Cart = () => {
    const [data, setData] = useState([]);
    const userData = JSON.parse(localStorage.getItem('userData'))
    const { fetchData } = useContext(MyContext);
    const navigate = useNavigate();

    const token = localStorage.getItem('user');

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


    const removeCartItem = (cartItemId) => {
        let url = staticData.endPoint.host + `/cart/remove/${cartItemId}`
        axios.delete(url, {
            headers: {
                "Content-Type": "application/json",
                "Access-Contol-Allow-Headers": "Content-Type",
                "Access-Contol-Allow-Origin": "*",
                Authorization: `Bearer ${token}`
            }
        })
            .then((response) => {
                toast.success(response.data, {
                    position: toast.POSITION.TOP_RIGHT, autoClose: 1000
                });
                const updatedCart = data.filter((item) => item.id !== cartItemId);
                setData(updatedCart);
            }).catch((error) => {
                toast.error(error, { autoClose: 1000 })
            })
    }

    const updateCart = (cartItemId, quantity) => {
        let url = staticData.endPoint.host + `/cart/${cartItemId}`
        try {
            axios.put(url, null, {
                params: {
                    quantity: quantity
                }, headers: {
                    "Content-Type": "application/json",
                    "Access-Contol-Allow-Headers": "Content-Type",
                    "Access-Contol-Allow-Origin": "*",
                    Authorization: `Bearer ${token}`
                }
            }).then((response) => {
                toast.success(response.data, {
                    position: toast.POSITION.TOP_RIGHT, autoClose: 1000
                });
                const updatedData = data.map(item =>
                    item.id === cartItemId ? { ...item, quantity: quantity } : item);
                setData(updatedData);
            }).catch((error) => {
                // console.error(error.response.data);
                toast.error(error.response.data, { autoClose: 1000 });
            });
        } catch (error) {
            console.log("Network Error")
        }
    }


    const handleIncrease = (cartItemId) => {
        const itemupdate = data.find(item => item.id === cartItemId);
        if (itemupdate.quantity >= 5) {
            toast("Maximum Quantity Reached", { autoClose: 1000 })
        }
        if (itemupdate && itemupdate.quantity < 5) {
            const newquantity = itemupdate.quantity + 1;
            updateCart(cartItemId, newquantity);
        }
    }

    const handleDecrease = (cartItemId) => {
        const itemupdate = data.find(item => item.id === cartItemId);
        if (itemupdate && itemupdate.quantity === 1) {
            removeCartItem(cartItemId)
        }
        if (itemupdate && itemupdate.quantity > 1) {
            const newquantity = itemupdate.quantity - 1;
            updateCart(cartItemId, newquantity);
        }
    };


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


    const checkout = () => {
        navigate("/checkout")
    }

    return (
        <div className='container-fluid'>
            {
                data.map((item) => (
                    <div key={item.product.productId} className='cartItem mb-3'>
                        <div className='cartImg'>
                            <img src={item.product.imageURL} alt=''></img>
                        </div>
                        <div className='cartItemDescription'>
                            <div className='cartDescription'>
                                <p>{item.product.productName}</p>
                                <RiDeleteBinLine className='cartIcon' onClick={() => removeCartItem(item.id)} />
                            </div>
                            <div className='cartDescriptionBelow'>
                                <div className='cartQuantity'>
                                    <Button onClick={() => handleDecrease(item.id)}>-</Button>
                                    <input type='number' value={item.quantity} disabled />
                                    <Button onClick={() => handleIncrease(item.id)}>+</Button>
                                </div>
                                <p>₹{calulateItemPrice(item)}</p>
                            </div>
                        </div>

                    </div>
                ))}
            <p className='total'><span>SUBTOTAL</span> <span>₹{calculateTotal()}</span></p>
            {data.length > 0 ? <Button onClick={() => checkout()}>CHECK OUT</Button> : <Link to={'/allProducts'}><Button> Continue Shopping</Button></Link>}

            <ToastContainer />
        </div>
    );
};

export default Cart;
