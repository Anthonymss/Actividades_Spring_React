import React, { useState } from 'react';
import ActividadesService from '../service/ActividadesService';
import "../css/confi.css"

const Confi = () => {
    const username = sessionStorage.getItem('username');
    const [nameActiviconfi, setNameActiviconfi] = useState("");
    const [nameCateconfi, setNameCateconfi] = useState("");
    const [listaAccionesNueva, setListaAccionesNueva] = useState([]);
    const [accionesSeleccionadas, setAccionesSeleccionadas] = useState([]);

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

    async function uploadPDF() {
        const fileInput = document.getElementById('file-input');
        const file = fileInput.files[0];

        if (!file) {
            alert('Por favor, selecciona un archivo PDF.');
            return;
        }

        const formData = new FormData();
        formData.append('file', file);

        try {
            const response = await fetch('https://pyy-woxp.onrender.com/extraer-acciones', {
                method: 'POST',
                body: formData
            });

            if (!response.ok) {
                throw new Error('Error en la respuesta del servidor');
            }

            const result = await response.json();
            setListaAccionesNueva(Array.isArray(result) ? result : []);
        } catch (error) {
            console.error('Error:', error);
            alert('Ocurrió un error al procesar el PDF.');
        }
    }

    async function sendText() {
        const textInput = document.getElementById('text-input').value;
        const idiomaDestino = document.getElementById('idioma').value;

        if (!textInput.trim()) {
            alert('Por favor, escribe algún texto.');
            return;
        }

        const data = {
            texto: textInput,
            idioma: idiomaDestino
        };

        try {
            const response = await fetch('https://pyy-woxp.onrender.com/extraer-acciones-texto', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });

            const result = await response.json();
            setListaAccionesNueva(Array.isArray(result.acciones) ? result.acciones : []);
        } catch (error) {
            console.error('Error:', error);
            alert('Ocurrió un error al procesar el texto.');
        }
    }

    const toggleSeleccion = (index) => {
        const accion = listaAccionesNueva[index];
        setAccionesSeleccionadas((prevList) => {
            if (prevList.includes(accion)) {
                return prevList.filter(item => item !== accion);
            } else {
                return [...prevList, accion];
            }
        });
    };

    const guardarListaGenerada = () => {
        if (accionesSeleccionadas.length > 0) {
            const listaForApi = accionesSeleccionadas.map((element) => ({
                nombre: element,
                categoria: "?"
            }));

            ActividadesService.guardarListaActividades(listaForApi)
                .then(response => alert(response.data))
                .catch(e => console.log(e));
        } else {
            alert("Debes agregar alguna acción para poder guardar");
        }
    };

    return (
        <div className='Confi'>
            {username === "gian_anthony" ? <h1>Bienvenido</h1> : <h2>Acceso Restringido 🏴‍☠️🏴‍☠️🏴‍☠️</h2>}
            <div className="Confi-manual">
                <form className='Confi-form' onSubmit={saveActividad}>
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
            <div className="Confi-py">
                <h1>Enviar Archivo PDF</h1>
                <form id="upload-form">
                    <input name='as' type="file" id="file-input" accept=".pdf" required/>
                    <button type="button" onClick={uploadPDF}>Enviar PDF</button>
                </form>
                <h1>Enviar Texto</h1>
                <form id="text-form">
                    <textarea id="text-input" rows="4" cols="50" placeholder="Escribe el texto aquí..." required></textarea>
                    <label name="idioma">Idioma de destino:</label>
                    <select id="idioma">
                        <option value="es">Español</option>
                        <option value="en">Ingles</option>
                    </select>
                    <button type="button" onClick={sendText}>Enviar Texto</button>
                </form>
            </div>
            <div className="Confi-lista">
                <h1>Acciones Extraidas</h1>
                <table className='Clasi-table'>
                    <thead>
                        <tr>
                            <th>Index</th>
                            <th>Activities</th>
                            <th>Acción</th>
                        </tr>
                    </thead>
                    <tbody>
                    {Array.isArray(listaAccionesNueva) && listaAccionesNueva.map((acti, index) => (
                        <tr key={index}>
                            <td>{index + 1}</td>
                            <td>{acti}</td>
                            <td>
                                <button
                                    onClick={() => toggleSeleccion(index)}
                                    className={accionesSeleccionadas.includes(acti) ? 'btn-eliminar' : 'btn-agregar'}
                                >
                                    {accionesSeleccionadas.includes(acti) ? 'Eliminar' : 'Agregar'}
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                <button onClick={guardarListaGenerada} className='Confi-btn-enviarLista'>Guardar Lista de Acciones</button>
            </div>
        </div>
    );
};

export default Confi;
