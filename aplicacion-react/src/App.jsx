import { useState } from 'react'
import './App.css'
import Header from './components/Header'
import { BrowserRouter, Routes, Route } from'react-router-dom'
import Home from './components/Home'
import Login from './components/Login'
import Clasi from './components/Clasi'
import Perfil from './components/Perfil'
import Footer from './components/Footer'
function App() {

  return (
    <>
      <div className='G'>
        <BrowserRouter>
          <Header/>
          <div className='contenedorGeneral'>
            
            <Routes>
              <Route exact path="/" element={<Login/>}></Route>
              <Route exact path="/home" element={<Home/>}></Route>
              <Route exact path="/clasi" element={<Clasi/>}></Route>
              <Route exact path="/perfil" element={<Perfil/>}></Route>
             {
              
              /*<Route exact path="/clientes" element={<ListClientesComponent/>}></Route>
              <Route exact path="/add-cliente" element={<AddClienteComponent/>}></Route>
              <Route exact path="/edit-cliente/:id" element={<AddClienteComponent/>}></Route>
             */}
              

            </Routes>
          </div>
          <Footer/>
        </BrowserRouter>
        

      </div>
    </>
  )
}

export default App
