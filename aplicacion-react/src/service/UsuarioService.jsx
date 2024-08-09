import axios from "axios";

const URL="http://localhost:8080/api/v1/usuarios"
class UsuarioService{
    validacion(username, password) {
        return axios.post(`${URL}/login?username=${username}&password=${password}`);
    }
    //++++++++http://localhost:8080/api/v1/usuarios/login?username=gian_anthony&password=1234
    crearUsuario(usuario){
        return axios.post(URL,usuario);
    }
    getAllUsuarios(){
        return axios.get(URL);
    }
    getInfoById(id){
        return axios.get(`${URL}/search/${id}`);
    }
}
export default new UsuarioService(); 