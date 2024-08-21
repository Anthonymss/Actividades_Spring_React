import axios from "axios";
const urlbaseLocal="http://localhost:8080/api/v1";
const urlHosting="https://actividades-spring-react-1.onrender.com/api/v1";
const URL=urlHosting+"/actividades";
class ActividadesService{

    generarActividad(id){
        return axios.get(`${URL}/generar/${id}`);
    }

    guardarActividad(actividad){
        return axios.post(URL,actividad);
    }
    guardarListaActividades(listaActividades){
        return axios.post(`${URL}/saveAll`,listaActividades);
    }
    //http://localhost:8080/api/v1/actividades/generar/1

}
export default new ActividadesService(); 