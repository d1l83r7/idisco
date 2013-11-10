package controllers.discotecas.mensajes;

import java.util.List;

import com.google.gson.JsonArray;

import models.Mensaje;

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
}
