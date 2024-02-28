import React, { useContext, useEffect, useState } from 'react';
import { Form, Row, Col, DropdownButton, Button } from 'react-bootstrap';
import 'rc-slider/assets/index.css';
import Slider from 'rc-slider';
import HeadNavbar from '../components/HeadNavbar';
import Footer from '../components/Footer';
import ProductCard from '../components/Cards/ProductCard';
import { MyContext } from '../components/Custom/MyContext';
import '../styles/ProductDisplay.css';
import BreadCrumbs from '../components/BreadCrumbs';

const ProductsDisplay = () => {
    const [sort, setSort] = useState('');
    const [appliedFilters, setAppliedFilters] = useState({
        category: '',
        minPrice: 0,
        maxPrice: 8100,
        inStock: false,
    });
    const [productData, setProductData] = useState([]);
    const [category, setCategory] = useState([]);
    const { fetchProducts } = useContext(MyContext);

    const handleSort = (e) => {
        setSort(e.target.value);
    };

    const handleCategoryChange = (e) => {
        const selectedCategory = e.target.value;
        setAppliedFilters((prevFilters) => ({ ...prevFilters, category: selectedCategory }));
    };

    const handleMinPriceChange = (e) => {
        const newMinPrice = parseFloat(e.target.value);
        setAppliedFilters((prevFilters) => ({ ...prevFilters, minPrice: newMinPrice }));
    };

    const handleMaxPriceChange = (e) => {
        const newMaxPrice = parseFloat(e.target.value);
        setAppliedFilters((prevFilters) => ({ ...prevFilters, maxPrice: newMaxPrice }));
    };

    const handleSliderChange = (value) => {
        setAppliedFilters((prevFilters) => ({ ...prevFilters, minPrice: value[0], maxPrice: value[1] }));
    };

    const handleInStockChange = (e) => {
        const newInStock = e.target.checked;
        setAppliedFilters((prevFilters) => ({ ...prevFilters, inStock: newInStock }));
    };


    const handleResetFilter = () =>{
        setSort('');
        setAppliedFilters({
            category:'',
            minPrice:0,
            maxPrice:8100,
            inStock:false
        });
    }


    useEffect(() => {
        const fetchDataWithFilters = async () => {
            try {
                const response = await fetchProducts(
                    `/products/filterSort?category=${appliedFilters.category}&minPrice=${appliedFilters.minPrice}&maxPrice=${appliedFilters.maxPrice}&stock=${appliedFilters.inStock}&sortBy=${sort}`
                );
                setProductData(response);
            } catch (error) {
                setProductData([]);
            }
        };

        fetchDataWithFilters();

        fetchProducts('/products/category')
            .then((response) => {
                setCategory(response);
            })
            .catch((error) => {
                console.log(error);
            });
    }, [fetchProducts, appliedFilters, sort]);

    return (
        <div>
            <HeadNavbar />
            <BreadCrumbs/>
            <div>
            <div className='filters mt-5'>
                <h2>View Products</h2>
                <div className="sortFilter mt-5">
                    <div className='filter'>
                        <DropdownButton title="Price">
                            <Row>
                                <Col>
                                    <Form.Control
                                        type='number'
                                        placeholder='Min Price'
                                        value={appliedFilters.minPrice}
                                        onChange={handleMinPriceChange}
                                    />
                                </Col>
                                <Col>
                                    <Form.Control
                                        type='number'
                                        placeholder='Max Price'
                                        value={appliedFilters.maxPrice}
                                        onChange={handleMaxPriceChange}
                                    />
                                </Col>
                            </Row>
                            <Slider
                                range
                                min={0}
                                max={8100}
                                value={[appliedFilters.minPrice, appliedFilters.maxPrice]}
                                onChange={handleSliderChange}
                            />
                        </DropdownButton>
                        <DropdownButton title='Availabilty' drop='down'>
                            <Form.Check type='checkbox' label='In Stock' onChange={handleInStockChange} />
                        </DropdownButton>
                        <Form.Select aria-label='Category' onChange={handleCategoryChange} value={appliedFilters.category}>
                            <option>All</option>
                            {category &&
                                category.map((index) => (
                                    <option key={index.categoryName} value={index.categoryName}>
                                        {index.categoryName}
                                    </option>
                                ))}
                        </Form.Select>
                    </div>
                    <Form.Select aria-label="Sort Type" onChange={handleSort} value={sort}>
                        <option>Sort</option>
                        <option value="PriceAsc">Price : Low to High</option>
                        <option value="PriceDesc">Price : High to Low</option>
                        <option value="NameAsc">Name : A-Z</option>
                        <option value="NameDesc">Name : Z-A</option>
                    </Form.Select>
                    <Button className='resetBtn' onClick={handleResetFilter}>Reset Filters</Button>
                </div>
            </div>
            </div>
            
            {productData.length===0 ? <h5>No Product Found</h5> : <ProductCard productData={productData} /> }
            <Footer />
        </div>
    );
};

export default ProductsDisplay;