package controllers;

import models.User;
import play.cache.Cache;
import play.mvc.Controller;

public class MenuDiscotecas extends DiscotecaGenericController {
	
	public static void verMenuDiscotecas(){
		usuarioLogado();
		render();
	}
	
	public static void login(String usuario, String password){
		User u = getUsuario(usuario, password);
		if(u!=null){
			Cache.set("user", u);
			verMenuDiscotecas();
		}
		errorLogin();
	}
	
	public static void errorLogin(){
		render();
	}
}
