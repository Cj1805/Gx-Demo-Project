/* eslint-disable react-hooks/exhaustive-deps */
import axios from 'axios';
import React, { createContext, useState } from 'react';
import { toast } from 'react-toastify';

const staticData = window.staticData;

export const MyContext = createContext();

export const ContextProvider = ({ children }) => {
    const [cart, setCart] = useState([]);

    const userData = JSON.parse(localStorage.getItem('userData'));
    const token = localStorage.getItem('user');


    const addCart = (userdata, productId, quantity) => {
        if (userdata && userdata.cart.cartId) {
            const cartId = userdata.cart.cartId;
            axios.post(staticData.endPoint.host + `/cart/add`,null, {
                params: {
                    cartId: cartId,
                    productId: productId,
                    quantity: quantity
                }, headers:{
                    "Content-Type": "application/json",
                    "Access-Contol-Allow-Headers": "Content-Type",
                    "Access-Contol-Allow-Origin": "*",
                    Authorization: `Bearer ${token}`
                }
            }).then((response) => {
                console.log('Product added', response.data);
                setCart([...cart, productId]);
                toast.success("Product Added in Cart", {
                    position: toast.POSITION.TOP_RIGHT, autoClose: 1000
                });
            }).catch((error) => {
                toast.error(error.response.data, { autoClose: 1000 })
            })
        }
        else {
            console.log("User not logged in")
        }
    }

    const fetchData = async (endpoint) => {
        const url = staticData.endPoint.host + endpoint
        try {
            const response = await axios.get(url, {
                headers: {
                    "Content-Type": "application/json",
                    "Access-Contol-Allow-Headers": "Content-Type",
                    "Access-Contol-Allow-Origin": "*",
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            throw error;
        }
    };

    const fetchProducts = async (endpoint) => {
        const url = staticData.endPoint.host + endpoint
        try {
            const response = await axios.get(url, {
                headers: {
                    "Content-Type": "application/json",
                    "Access-Contol-Allow-Headers": "Content-Type",
                    "Access-Contol-Allow-Origin": "*"
                }
            });
            return response.data;
        } catch (error) {
            throw error;
        }
    };

    return (
        <MyContext.Provider value={{ addCart, cart, userData, fetchData, fetchProducts }}>
            {children}
        </MyContext.Provider>
    );
};