import React from 'react'
import HeadNavbar from '../components/HeadNavbar'
import Cards from '../components/Cards/Cards'
import Button from 'react-bootstrap/esm/Button'
import promo from '../images/dashboard/promo.jpeg';
import disease1 from '../images/dashboard/Brand-02.jpeg';
import disease2 from '../images/dashboard/jpeg-optimizer_Brand-03.jpeg';
import disease3 from '../images/dashboard/jpeg-optimizer_Brand-04-min.jpeg';
import banner2 from '../images/dashboard/banner2.jpeg'
import banner1 from '../images/dashboard/banner1.png'
import '../styles/Dashboard.css';
import HealthInfoCard from '../components/Cards/HealthInfoCard';
import Footer from '../components/Footer';
import { Link } from 'react-router-dom';
const staticData = window.staticData;

const Dashboard = () => {
    return (
        <div className='Container'>
            <HeadNavbar />

            <div className='products'>
            <h3>{staticData.Seller}</h3>
               <Cards />
            </div>
           <Link to={'/allProducts'}> <Button className='viewAll' >View All</Button></Link>
            <div>
                <img src={promo} alt='Promo' className='promo'></img>
                <div className='disease-link'>
                    <a href='#disease2'><img src={disease2} alt='Sleep & Stress'></img></a>
                    <a href='#disease3'><img src={disease3} alt='Liver & Kidney'></img></a>
                    <a href='#disease1'><img src={disease1} alt='Men & Women'></img></a>
                </div>
                <div className='banner m-20'>
                    <div><img src={banner1} alt='Banner-1' /></div>
                    <div><img src={banner2} alt='Banner-2' /></div>
                </div>
                <HealthInfoCard />
            </div>
            <Footer />
        </div>
    )
}

export default Dashboard