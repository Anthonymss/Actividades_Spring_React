import React from 'react';
const Header = () => {
  const destruir=()=>{
    sessionStorage.clear();

  }
  return (
      <header className="header">
        <nav>
          <ul>
              <li><a href="/clasi" className="menu">Clasificación</a> </li>
              <li><a href="/home" className="menu">Home</a></li>
              <li><a href="/perfil" className="menu">Ver Perfil</a></li>
              <li className="logo"> 
                  <a className="menu" onClick={destruir} href="/">Salir</a>
              </li>
          </ul>
      </nav>
     
      </header>
  );
};

export default Header;
/*
<nav className="nav-bar">
<a href="/clasi" className="nav-link">Clasificacion</a>
<a href="/home" className="nav-link">Home</a>
<a href="/perfil" className="nav-link">Ver Perfil</a>
</nav>
*/