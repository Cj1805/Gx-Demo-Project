import React, { useContext, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import HeadNavbar from '../components/HeadNavbar';
import Footer from '../components/Footer';
import '../styles/Product.css'
import { Button } from 'react-bootstrap';
import { MyContext } from '../components/Custom/MyContext';
import { ToastContainer, toast } from 'react-toastify';
import Stars from '../components/Stars';

const staticData = window.staticData;
const errorData = window.errorData;

const Product = () => {
    const { id } = useParams();
    const [data, setData] = useState({});
    const [quant, setQuant] = useState(1);
    const [review, setReview] = useState([]);

    const { addCart, fetchProducts } = useContext(MyContext);

    const handleAddToCart = (e, productId) => {
        e.preventDefault();
        const userData = JSON.parse(localStorage.getItem('userData'));
        const quantity = quant;

        if (userData && userData.id) {
            addCart(userData, productId, quantity)
        } else {
            toast.error(errorData.cart, { autoClose: 1000 })
        }
    }


    useEffect(() => {
        viewData();
        viewReview();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [id])
    const viewData = async () => {
        try {
            const data = await fetchProducts(`/products/getById/${id}`)
            setData(data)
        } catch (error) {
            toast("Network Error.", { autoClose: 1000 })
        }
    };

    const viewReview = async () => {
        try {
            const response = await fetchProducts(`/products/getReview/${id}`)
            setReview(response)
        } catch (error) {
            console.log(error)
        }
    };

    const quantDecrement = () => {
        if (quant > 1) {
            setQuant(quant - 1);
        }
    }

    const quantIncrement = () => {
        if (quant >= 5) {
            toast("Maximum Quantity Reached", { autoClose: 1000 })
            setQuant(5)
        } else {
            setQuant(quant + 1);
        }
    }

    return (
        <div>
            <HeadNavbar />
            <div className='productPage m-5'>
                <img src={data.imageURL} alt='' />
                <div className='productDetails'>
                    {window.scrollTo({ top: 10, behavior: 'smooth' })}
                    <h3 className='name'>{data.productName}</h3>
                    {data.stock > 0 ? null : <p style={{ color: 'red', fontSize: 'large' }}>(Product Out of Stock)</p>}
                    <p className='proDescription'> {data.description}</p>
                    <p >MRP: &emsp;<span className='price'>₹{data.price}</span></p>
                    <h6>QUANTITY</h6>
                    <div className='quantity mt-3'>
                        <Button onClick={quantDecrement}>-</Button>
                        <input type='number' value={quant}
                            onChange={(e) => { setQuant(e.target.value) }} disabled />

                        <Button onClick={quantIncrement}>+</Button>
                    </div>
                    <Button onClick={(e) => { handleAddToCart(e, data.productId) }}>
                        {staticData.addCart}
                    </Button>
                </div>
            </div>
            <div className='reviews m-5'>
                {review.map((review, index) =>
                    <div className='review' key={index} style={{border:'1px solid black'}} >
                        <div>{review.name}</div>
                        <div>
                            <p><Stars star={review.rating}/>
                             {review.rating}</p>
                            <p> {review.summary}</p>
                            <p> {review.info}</p>
                        </div>
                        <div>{review.reviewDate}</div>
                    </div>
                )}
            </div>
            <Footer />
            <ToastContainer />
        </div>
    )
}

export default Product