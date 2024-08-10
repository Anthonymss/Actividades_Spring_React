import axios from "axios";
const urlbaseLocal="http://localhost:8080/api/v1";
const urlHosting="https://actividades-spring-react.onrender.com/api/v1";
const URL=urlHosting+"/actividades";
class ActividadesService{

    generarActividad(id){
        return axios.get(`${URL}/generar/${id}`);
    }
    //http://localhost:8080/api/v1/actividades/generar/1

}
export default new ActividadesService(); 