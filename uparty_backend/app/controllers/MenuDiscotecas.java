package controllers;

import controllers.discotecas.DiscotecaGenericController;
import es.uparty.utils.Utils;
import models.User;
import play.cache.Cache;
import play.mvc.Controller;

public class MenuDiscotecas extends DiscotecaGenericController {
	
	public static void verMenuDiscotecas(){
		usuarioLogado();
		User user = (User)Cache.get("user");
		render(user);
	}
	
	public static void login(String usuario, String password){
		User u = getUsuario(usuario, password,true);
		if(u!=null){
			Cache.set("user", u);
			verMenuDiscotecas();
		}
		errorLogin();
	}
	
	public static void errorLogin(){
		render();
	}
	
	public static void usuarioValido(String usuario, String password){
		String user = Utils.decrypt(usuario);
		String pass = Utils.decrypt(password);
		User u = getUsuario(user, pass,true);
		if(u!=null){
			renderJSON("S");
		}
		renderJSON("N");
	}
}
 