import React, { useContext, useEffect, useState } from 'react';
import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { Row, Col, ListGroup, Offcanvas } from 'react-bootstrap';
import '../styles/HeaderNav.css'
import logo from '../images/Himalaya-wellness-logo.jpeg';
import { Cart2, Person } from 'react-bootstrap-icons';
import { Link, useNavigate } from 'react-router-dom';
import '../styles/Dropdown.css'
import Cart from '../Pages/Cart';
import { MyContext } from './Custom/MyContext';

const staticData = window.staticData;


const HeadNavbar = () => {
    const [search, setsearch] = useState('');
    const [result, setResult] = useState([]);
    const [dropdown, setDropdown] = useState(null);
    const navigate = useNavigate();

    const { fetchProducts } = useContext(MyContext);

    const userData = JSON.parse(localStorage.getItem('userData'))

    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const dropdownPages = [
        '/',
        '/',
        '/allProducts'
    ]

    const dropdownClick = (index, title) => {
        navigate(dropdownPages[index] || dropdownPages[title])
    }
    const handleDropdown = (index) => {
        setDropdown(index === dropdown ? null : index);
    };

    const renderValues = (values) => {
        return values.map((value, index) => (
            <NavDropdown.Item href='#/action-1'>{value.name}</NavDropdown.Item>
        ));
    }

    const renderCategory = (category) => {
        return (
            category.map((element, index) => (
                <Col className='categoryNav' key={index} md={3} >
                    <NavDropdown key={index} title={element.title} id={`navbarScrollingDropdown-${index}`}
                        show={dropdown === index}
                        onMouseEnter={() => handleDropdown(index)}
                        onMouseLeave={() => handleDropdown(index)}
                        onClick={() => dropdownClick(index, element.title)}
                    >
                        {element.category.map((subElement, subIndex) => (
                            <div key={subIndex} className='dropdown-sub'>
                                <NavDropdown.Item key={subIndex} className='fw-bold' disabled >
                                    {subElement.name}
                                </NavDropdown.Item>

                                {subElement.values ? (
                                    <Row >  {renderValues(subElement.values)} </Row>) : null}
                            </div>
                        ))}
                    </NavDropdown>
                </Col>
            ))
        );
    };

    useEffect(() => {
        const handleSearch = async () => {
            try {
                const response = await fetchProducts(`/products/getByName/${search}`)
                setResult(response)
                console.log(result)
            } catch (error) {
                setResult([{ error: error.response.data }]);
            }
        };
        if (search) {
            handleSearch();
        } else {
            setResult([]);
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [search])
    return (

        <div>
            <div className='welcome p-2'>{staticData.welcome}</div>
            <Navbar expand="lg" className="bg-body-tertiary">
                <Container fluid>
                    <Navbar.Brand ><Link to='/'><img id='nav-logo' src={logo} alt='LOGO'></img> </Link> </Navbar.Brand>
                    <Navbar.Toggle aria-controls="navbarScroll" />
                    <Navbar.Collapse id="navbarScroll">
                        <Nav className="me-auto my-2 my-lg-0 nav2" navbarScroll >
                            {renderCategory(staticData.navbar.dropdown)}
                            <Nav.Link href="#action1">{staticData.navbar.store}</Nav.Link>
                            <Nav.Link href="#action2">{staticData.navbar.csr}</Nav.Link>

                            {staticData.navbar.dropdown.forEach(element => {
                                <NavDropdown title={element.title} id="navbarScrollingDropdown">
                                    <Row>
                                        <Col>{element.category.forEach(subElement => {
                                            <NavDropdown.Item href="#/action-1">{subElement.name}</NavDropdown.Item>
                                        })}</Col>
                                    </Row>
                                </NavDropdown>
                            })}
                        </Nav>
                        <div className='nav-search w-25'>
                            <Form className="d-flex">
                                <div>
                                    <Form.Control
                                        type="search"
                                        placeholder="Search"
                                        className="me-2"
                                        aria-label="Search"
                                        value={search}
                                        onChange={(e) => setsearch(e.target.value)}
                                    />
                                    {search && (
                                        <div className='custom-dropdown'>
                                            {result.map((product) => (
                                                'error' in product ? (
                                                    <h6>{product.error}</h6>
                                                ) : (
                                                    <Link to={`/product/${product.productId}`}>
                                                        <ListGroup>
                                                            <ListGroup.Item action href="" key={product.productID} onClick={() => { setsearch('') }} >
                                                                <img src={product.imageURL} alt=''></img>
                                                                <div  className='productDetails'>
                                                                    <p>{product.productName}</p>
                                                                    <b>₹{product.price}</b>
                                                                </div>
                                                            </ListGroup.Item>
                                                        </ListGroup>
                                                    </Link>
                                                )
                                            ))}</div>
                                    )}
                                </div>
                            </Form>
                            {
                                userData === null ?
                                    (<Link to='/login'> <Person className='icon' /></Link>)
                                    : (<Link to='/profile'> <Person className='icon' /></Link>
                                    )}
                            <Cart2 className='icon' onClick={handleShow} />
                            <Offcanvas show={show} onHide={handleClose} placement='end'>
                                <Offcanvas.Header closeButton>
                                    <Offcanvas.Title>CART</Offcanvas.Title>
                                </Offcanvas.Header>
                                <Offcanvas.Body>
                                    <Cart />
                                </Offcanvas.Body>
                            </Offcanvas>
                        </div>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </div>
    );
}

export default HeadNavbar;