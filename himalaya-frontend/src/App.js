import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from './Pages/Login';
import Dashboard from './Pages/Dashboard';
import RegistrationForm from './Pages/RegistrationForm';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Profile from './Pages/Profile';
import ProductsDisplay from './Pages/ProductsDisplay';
import { ContextProvider } from './components/Custom/MyContext';
import Product from './Pages/Product';
import Cart from './Pages/Cart';
import Address from './Pages/Address';
import Checkout from './Pages/Checkout';
import Shipping from './Pages/Shipping';
import Payment from './Pages/Payment';
import React from 'react';
import { FormDataProvider } from './components/Custom/FormDataContext';

function App() {


  return (
    <div className="App">

      <BrowserRouter>
        <ContextProvider>
          <FormDataProvider>
            <Routes>
              <Route path='/' element={<Dashboard />} />
              <Route path='/login' element={<Login />} />
              <Route path='/register' element={<RegistrationForm />} />
              <Route path='/profile' element={<Profile />} />
              <Route path='/allProducts' element={<ProductsDisplay />} />
              <Route path='/product/:id' element={<Product />}></Route>
              <Route path='/cart' element={<Cart />}></Route>
              <Route path='/address' element={<Address />}></Route>
              <Route path='/checkout' element={<Checkout />}></Route>
              <Route path='/shipping' element={<Shipping />}></Route>
              <Route path='/payment' element={<Payment />}></Route>
            </Routes>
          </FormDataProvider>
        </ContextProvider>
      </BrowserRouter>
    </div>
  );
}

export default App