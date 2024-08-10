import axios from "axios";
const urlbaseLocal="http://localhost:8080/api/v1";
const urlHosting="https://actividades-spring-react.onrender.com/";
const URL=urlHosting+"/asignacion"
class ActividadUsuarioService{

    listActivityById(id){
        return axios.get(`${URL}/usuario/${id}`);
    }
    completar(id){
        return axios.put(`${URL}/completar/${id}`);
    }
    guardarActividad(idUser,idActividad){
        return axios.post(`${URL}/${idUser}/${idActividad}`);
    }
 /*
    validacion(username, password) {
        return axios.post(`${URL}/login?username=${username}&password=${password}`);
    }
    crearUsuario(usuario){
        return axios.post(URL,usuario);
    }
    getAllUsuarios(){
        return axios.get(URL);
    }
*/
}
export default new ActividadUsuarioService(); 