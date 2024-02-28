import React, { useContext, useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import '../../styles/Card.css';
import 'react-toastify/dist/ReactToastify.css';
import { Col, Row } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import { MyContext } from '../Custom/MyContext';
import ReactPaginate from 'react-paginate';
const staticData = window.staticData;
const errorData = window.errorData;

const ProductCard = ({productData}) => {

    const { addCart, cart} = useContext(MyContext);
    const [currentPage, setCurrentPage] = useState(0);
   
    const itemsPerPage = 6;

    const indexOfLastItem = (currentPage + 1) * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentProducts = productData.slice(indexOfFirstItem, indexOfLastItem);

    const handlePageChange = (newPage) => {
        setCurrentPage(newPage.selected);
    };

    const handleAddToCart = (e, productId) => {
        e.preventDefault();
        const userData = JSON.parse(localStorage.getItem('userData'));
        const quantity = 1;

        if (userData && userData.id) {
            addCart(userData, productId, quantity)
        } else {
            toast.error(errorData.cart, { autoClose: 1000 })
        }
    }
    useEffect(()=>{
        setCurrentPage(0);
    },[productData])

    return (
        <div className='container' >
        {window.scrollTo({ top: 10, behavior: 'smooth' })}
            <Row className='productCard'>
                {currentProducts.map((item) => (
                    <Col key={item.productId}>
                        <Link to={`/product/${item.productId}`}>
                            <Card className='product'>
                                <div className="imageContainer">
                                    <Card.Img className='productImg' variant="top" src={item.imageURL} />
                                    <div className='addCart'>
                                        {cart.includes(item.productId) ? (
                                            <Button className='productButton' variant='primary'>{staticData.viewCart}</Button>

                                        ) : (
                                            <Button className='productButton' variant="primary" onClick={(e) => {
                                                handleAddToCart(e, item.productId)
                                            }}>{staticData.addCart}</Button>
                                        )}
                                    </div>
                                </div>
                                <Card.Body >
                                    <Card.Title className='productText' >{item.productName}</Card.Title>
                                    <Card.Text className='description'>{item.description}</Card.Text>
                                    <Card.Text className='productText'>{staticData.rs}{item.price}</Card.Text>
                                </Card.Body>
                            </Card>
                        </Link>
                    </Col>
                ))
                }
            </Row>
            <ReactPaginate
                previousLabel='< prev'
                nextLabel='next >'
                breakLabel='...'
                pageCount={Math.ceil(productData.length / itemsPerPage)}
                marginPagesDisplayed={2}
                pageRangeDisplayed={5}
                onPageChange={handlePageChange}
                containerClassName='pagination'
                activeClassName='active'
                forcePage={currentPage}
            />

            <ToastContainer />
        </div>
    );
};

export default ProductCard