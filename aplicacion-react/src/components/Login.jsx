import React, { useState } from 'react';
import usuarioService from '../service/UsuarioService';
import { useNavigate } from 'react-router-dom';

const Login = () => {
  const [isActive, setIsActive] = useState(false);
  const [nombre, setNombre] = useState("");
  const [apellido, setApellido] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loginUsername, setLoginUsername] = useState("");
  const [loginPassword, setLoginPassword] = useState("");
  const navigate = useNavigate();

  const handleRegisterClick = () => {
    setIsActive(true);
  };

  const handleLoginClick = () => {
    setIsActive(false);
  };

  const registrar = (e) => {
    e.preventDefault();
    const usuario = { nombre, apellido, username, password };
    console.log(usuario);
    console.log("Se esta creando la cuenta");
    
    usuarioService.crearUsuario(usuario)
      .then((response) => {
        alert(`Tu cuenta ha sido creada! Usuario: ${username}`);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const login = (e) => {
    e.preventDefault();
    usuarioService.validacion(loginUsername,loginPassword)
      .then((response) => {
        sessionStorage.setItem("id",response.data.id);
        sessionStorage.setItem('username', response.data.username);
        sessionStorage.setItem("nombre",response.data.nombre);
        sessionStorage.setItem("apellido",response.data.apellido);
        sessionStorage.setItem("puntos",response.data.puntos);
        sessionStorage.setItem("image",response.data.image);
        alert(sessionStorage.getItem("nombre"));
        navigate('/home');
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div className={`container ${isActive ? 'active' : ''}`}>
      <div className={`form-container sign-up ${isActive ? 'active' : ''}`}>
        <form onSubmit={registrar}>
          <h1>Crea una Cuenta</h1>
          <div className="social-icons">
            <a href="#" className="icon"><img className='social-icons-img' src="https://www.wallpaperflare.com/static/388/690/548/rabbit-ears-charming-little-wallpaper.jpg" alt="1" /></a>
            <a href="#" className="icon"><img className='social-icons-img' src="https://www.wallpaperflare.com/static/388/690/548/rabbit-ears-charming-little-wallpaper.jpg" alt="1" /></a>
            <a href="#" className="icon"><img className='social-icons-img' src="https://www.tiendanimal.es/articulos/wp-content/uploads/2018/01/Fibra-en-la-alimentaci%C3%B3n-de-los-conejos.jpg" alt="1" /></a>
          </div>
          <span>Usa tu username y contraseña</span>
          <input
            type="text"
            placeholder="Nombres"
            value={nombre}
            onChange={(e) => setNombre(e.target.value)}
          />
          <input
            type="text"
            placeholder="Apellidos"
            value={apellido}
            onChange={(e) => setApellido(e.target.value)}
          />
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <button type="submit">Registrate</button>
        </form>
      </div>
      <div className={`form-container sign-in ${isActive ? '' : 'active'}`}>
        <form onSubmit={login}>
          <h1>Iniciar Sesión</h1>
          <div className="social-icons">
            <a href="#" className="icon"><img className='social-icons-img' src="https://www.wallpaperflare.com/static/388/690/548/rabbit-ears-charming-little-wallpaper.jpg" alt="1" /></a>
            <a href="#" className="icon"><img className='social-icons-img' src="https://www.wallpaperflare.com/static/388/690/548/rabbit-ears-charming-little-wallpaper.jpg" alt="1" /></a>
            <a href="#" className="icon"><img className='social-icons-img' src="https://www.wallpaperflare.com/static/388/690/548/rabbit-ears-charming-little-wallpaper.jpg" alt="1" /></a>
          </div>
          <span>Usa tu username y contraseña</span>
          <input
            type="text"
            placeholder="Username"
            value={loginUsername}
            onChange={(e) => setLoginUsername(e.target.value)}
          />
          <input
            type="password"
            placeholder="Password"
            value={loginPassword}
            onChange={(e) => setLoginPassword(e.target.value)}
          />
          <button type="submit">Iniciar Sesión</button>
        </form>
      </div>
      <div className="toggle-container">
        <div className="toggle">
          <div className="toggle-panel toggle-left">
            <h1>Bienvenido!</h1>
            <p>Marcelo wonejo</p>
            <button className="hidden" onClick={handleLoginClick}>Iniciar Sesión</button>
          </div>
          <div className="toggle-panel toggle-right">
            <h1>Hola, Brother!!</h1>
            <p>Regístrate con tus datos personales para usar todas las características del sitio</p>
            <button className="hidden" onClick={handleRegisterClick}>Regístrate</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
