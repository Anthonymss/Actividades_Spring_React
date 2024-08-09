import React from 'react'
import "../css/perfil.css"
const Perfil = () => {
    const nombre=sessionStorage.getItem('nombre')
    const apellido=sessionStorage.getItem('apellido')
    const username=sessionStorage.getItem('username')
    const puntos=sessionStorage.getItem("puntos");
    const image=sessionStorage.getItem("image");
    return (
    <div className='profile-Principal'>
        <div className="xdxd"></div>
        <div className='profile'>
            <div className="profile-imagen">
                <img src={image} alt={username} />
            </div>
            <p className='profile-name'>{nombre}, {apellido}</p>
            <p className='profile-username'>Username:</p>
            <p className='profile-username-u'>{username}</p>
            <div className='profile-puntos'>
                <p>Puntos:</p>
                <p className='profile-puntosT'>{puntos}</p>
            </div>
        
        </div>
        <div className="xdxd"></div>
    </div>
  )
}

export default Perfil
