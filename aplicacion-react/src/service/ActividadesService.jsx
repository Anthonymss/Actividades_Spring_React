import axios from "axios";

const URL="http://localhost:8080/api/v1/actividades"
class ActividadesService{

    generarActividad(id){
        return axios.get(`${URL}/generar/${id}`);
    }
    //http://localhost:8080/api/v1/actividades/generar/1

}
export default new ActividadesService(); 