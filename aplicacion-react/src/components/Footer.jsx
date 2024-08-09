import React from 'react'
import "../css/footer.css"
const Footer = () => {
  return (
    <footer>
      <ul className='footer-list'>
        <li>
          <p>Sitio diseñado por: <a className='footer-autor' href="https://github.com/AnthonyMss">Anthony Mss</a></p>
        </li>
        <li>
          <p> Colaboradores:  Joel Alexander y  Marcelo</p>
        </li>
        <li>
          <p>Desarrollado con <span className='footer-react'>React</span> y <span className='footer-spring'>Spring Boot</span></p>
        </li>
        <li>
          <p>Version 1.0.0</p>
        </li>
      </ul>
    </footer>

  )
}

export default Footer
