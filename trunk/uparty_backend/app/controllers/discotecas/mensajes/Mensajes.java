package controllers.discotecas.mensajes;

import java.util.List;

import models.Mensaje;
import models.User;

import com.google.gson.JsonArray;

import controllers.discotecas.DiscotecaGenericController;
import es.uparty.utils.Utils;

public class Mensajes extends MensajesGenericController {
	public static void obtenerMensajes(String idDiscoteca, String idMuro, String name){
		usuarioLogado();
		List<Mensaje> l = seleccionarMensajesPorMuro(Long.parseLong(idMuro));
		render(l,name,idDiscoteca);
	}
	
	public static void obtenerMensajesJSON(String idDiscoteca, String idMuro){
		List<Mensaje> l = seleccionarMensajesPorMuro(Long.parseLong(idMuro));
		JsonArray array = listMensajesToJSonArray(l);
		renderJSON(array);
	}
	
	public static void insertarMensaje(String usuario, String contrasenya, String texto, String idDiscoteca, String idMuro){
		String user = Utils.decrypt(usuario);
		String pass = Utils.decrypt(contrasenya);
		User u = DiscotecaGenericController.getUsuario(user, pass,false);
		Mensaje m = new Mensaje();
		m.setIdDiscoteca(Long.parseLong(idDiscoteca));
		m.setIdMuro(Long.parseLong(idMuro));
		m.setMensaje(texto);
		m.setUsuario(usuario);
		m.setIdUsuario(u.getId());
		insertarMensaje(m);
	}
	
	public static void eliminarMensaje(String idMensaje){
		boolean b = deleteMensaje(Long.parseLong(idMensaje));
		if(b){
			render();
		}else{
			errorEliminaMensaje();
		}
	}
	
	public static void errorEliminaMensaje(){
		render();
	}
}
