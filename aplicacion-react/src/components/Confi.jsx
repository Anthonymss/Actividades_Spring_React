import React, { useState } from 'react';
import ActividadesService from '../service/ActividadesService';

const Confi = () => {
    const username = sessionStorage.getItem('username');
    const [nameActiviconfi, setNameActiviconfi] = useState("");
    const [nameCateconfi, setNameCateconfi] = useState("");

    const saveActividad = (e) => {
        e.preventDefault();
        if (nameActiviconfi !== "" && nameCateconfi !== "") {
            const objetoActividad = {
                nombre: nameActiviconfi,
                categoria: nameCateconfi,
            };
            ActividadesService.guardarActividad(objetoActividad)
                .then((response) => {
                    console.log(response.data);
                    alert("Actividad guardada con éxito");
                })
                .catch((error) => {
                    console.log(error);
                });
        }
    };

    return (
        <div className='Confi'>
            <br/><br/><br/><br/><br/><br/><br/><br/>
            {username === "gian_anthony" ? <h1>Bienvenido</h1> : <h2>Acceso Restringido 🏴‍☠️🏴‍☠️🏴‍☠️</h2>}
            <form onSubmit={saveActividad}>
                <label>
                    Nombre Actividad:
                    <input
                        type="text"
                        placeholder="Nombre actividad"
                        value={nameActiviconfi}
                        onChange={(e) => setNameActiviconfi(e.target.value)}
                    />
                </label>
                <label>
                    Categoría:
                    <input
                        type="text"
                        placeholder="Nombre categoría"
                        value={nameCateconfi}
                        onChange={(e) => setNameCateconfi(e.target.value)}
                    />
                </label>
                <button type="submit">Save</button>
            </form>
        </div>
    );
};

export default Confi;
