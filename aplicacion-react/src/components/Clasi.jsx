import React, { useState, useEffect } from 'react';
import UsuarioService from '../service/UsuarioService';
import "../css/clasi.css"
const Clasi = () => {
    const [usuarios, setUsuarios] = useState([]);
    const getOrder = (index) => index + 1;
    useEffect(() => {
        const fetchUsuarios = async () => {
            UsuarioService.getAllUsuarios()
            .then(response => {
                response.data. sort((a, b) => b.puntos - a.puntos);
                setUsuarios(response.data); 
            }).catch(error=>{console.log(error)}) 
        };

        fetchUsuarios();
    }, []);

    return (
        <div className='Clasi-General'>
            <h1>Clasificación 😎🐰⏫</h1>
            <table className='Clasi-table'>
                <thead>
                    <tr>
                        <th>Orden</th>
                        <th>Nombre</th>
                        <th>Username</th>
                        <th>Puntos</th>
                    </tr>
                </thead>
                <tbody>
                {usuarios.map((user, index) => (
                <tr key={user.id} className={index === 0 ? 'first-place' : ''}>
                    <td className='orden-cell'>{getOrder(index)}</td>
                    <td>{user.nombre}</td>
                    <td>{user.username}</td>
                    <td className='puntos-cell'>{user.puntos}</td>
                </tr>
            ))
            }
                </tbody>
            </table>
        </div>
    );
};

export default Clasi;