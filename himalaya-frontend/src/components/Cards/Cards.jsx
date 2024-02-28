import React, { useContext, useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import '../../styles/Card.css';
import { Col } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { MyContext } from '../Custom/MyContext';
import { ToastContainer, toast } from 'react-toastify';
const staticData = window.staticData;
const errorData = window.errorData;

const Cards = () => {
  const { addCart, userData, fetchProducts } = useContext(MyContext);
  const [data, setData] = useState([])

  useEffect(() => {
    fetchProducts('/products/GetAllProducts')
      .then((response) => {
        setData(response)
      })
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  const handleAddToCart = (e, productId) => {
    e.preventDefault();
    const quantity = 1;

    if (userData && userData.cart.cartId) {
      addCart(userData, productId, quantity)
    } else {
      toast.error(errorData.cart, { autoClose: 1000 })
    }
  }

  return (
    <div className='cont'>
      {data.slice(0, 5).map((item,index) => (
        <Col key={index}>
          <Link to={`/product/${item.productId}`} >
            <Card key={item.productId} >
              <Card.Img variant="top" src={item.imageURL} />
              <Card.Body>
                <Card.Title>{item.productName}</Card.Title>
                <div className='price'>
                  <Card.Text>from Rs.{item.price}</Card.Text>
                  <Button className='card-btn' variant="primary" onClick={(e) => handleAddToCart(e, item.productId)}>
                    {staticData.addCart}
                  </Button>
                </div>
              </Card.Body>
            </Card>
          </Link>
        </Col>
      ))
      }
      <ToastContainer />
    </div>
  )
}

export default Cards
