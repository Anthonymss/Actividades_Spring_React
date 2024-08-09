import React, { useEffect, useState, useCallback } from 'react';
import ActividadesService from '../service/ActividadesService';
import "../css/home.css";
import ActividadUsuario from '../service/ActividadUsuario';
import UsuarioService from '../service/UsuarioService';

const formatDateTime = (date) => {
  const d = new Date(date);
  const day = String(d.getDate()).padStart(2, '0');
  const month = String(d.getMonth() + 1).padStart(2, '0');
  const year = d.getFullYear();
  const hours = String(d.getHours()).padStart(2, '0');
  const minutes = String(d.getMinutes()).padStart(2, '0');
  const seconds = String(d.getSeconds()).padStart(2, '0');
  return `${day}/${month}/${year} ${hours}:${minutes}:${seconds}`;
};

const Home = () => {
  const id = sessionStorage.getItem("id");
  const [nuevaActi, setNuevaActi] = useState("");
  const [nuevaCate, setNuevaCate] = useState("");
  const [lista, setLista] = useState([]);

  const recuperarActividades = useCallback(() => {
    if (!id) return; 
    ActividadUsuario.listActivityById(id)
      .then(response => {
        setLista(response.data.reverse() || []);
      })
      .catch(error => {
        console.error("Error al recuperar actividades:", error);
      });
  }, [id]);

  useEffect(() => {
    recuperarActividades();
  }, [recuperarActividades]);

  const generacionActividades = async () => {
    try {
      const response = await ActividadesService.generarActividad(id);
      const idActivi=response.data.id;
      const nombre=response.data.nombre;
      const categoria=response.data.categoria;

      setNuevaActi(nombre || "");
      setNuevaCate(categoria || "");
      if (idActivi) {
        await guardar(idActivi);
        recuperarActividades();
      }
    } catch (error) {
      console.error("Error al generar actividad:", error);
      alert("Error al generar actividad - COMPLETA LAS PENDIENTES");
    }
  };
  

  const guardar = async (idActivi) => {
    try {
      const response = await ActividadUsuario.guardarActividad(id, idActivi);
      console.log(response);
    } catch (error) {
      console.error("Error al guardar actividad:", error);
    }
  };
  const infoByUser = async (idUser) => {
    try {
      const response = await UsuarioService.getInfoById(idUser);
      const nuevosPuntos = response.data.puntos;
      sessionStorage.setItem("puntos", nuevosPuntos);
      
    } catch (error) {
      console.error("Error al recuperar info del usuario:", error);
    }
  };
  const completar = async (e) => {
    e.preventDefault();
    const idActividad = e.target.value;
    try {
      await ActividadUsuario.completar(idActividad);
      infoByUser(id)
      recuperarActividades();
    } catch (error) {
      console.error("Error al completar actividad:", error);
    }
  };

  return (
    <div className='home'>
      <div className="home-tablaP">
        <h1 className='home-tabla-title'>
          Lista de Actividades de {sessionStorage.getItem("username")}
        </h1>
        <table className='home-table'>
          <thead>
            <tr>
              <th>#</th>
              <th>Nombre</th>
              <th>Categoria</th>
              <th>Fecha Vencimiento</th>
              <th>Estado</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {lista.map((actividad, index) => (
              <tr key={actividad.id}>
                <td>{index + 1}</td>
                <td>{actividad.actividadNombre}</td>
                <td>{actividad.categoria}</td>
                <td>{formatDateTime(actividad.tiempoAsignado)}</td>
                <td>{actividad.estado}</td>
                <td>
                  {
                    new Date(actividad.tiempoAsignado) > new Date()
                      ? (actividad.estado === "COMPLETADA")
                        ? <button value={actividad.id} disabled={true} className='home-botonCompletar'>Completar</button>
                        : <button value={actividad.id} onClick={completar} className='home-botonCompletar'>Completar</button>
                      : <p className='home-vencido'>Vencido</p>
                  }
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <div className="home-generar">
        <h1>Generar nueva Actividad</h1>
        <button onClick={generacionActividades}>Generar</button>
        {nuevaActi && <p>Tu nueva Actividad es: <span className='home-generar-activity'>{nuevaActi}</span></p>}
        {nuevaCate && <p>Categoría: <span className='home-generar-activity' >{nuevaCate}</span></p>}
      </div>
    </div>
  );
}

export default Home;
