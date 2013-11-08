package controllers.usuarios;

import models.User;

import org.junit.Before;

import play.cache.Cache;
import play.mvc.Controller;

public class SecurityController extends Controller {
	
	protected static void usuarioLogado(){
		User user = (User)Cache.get("user");
		if(user==null){
			pantallaLogin();
		}
	}
	
	public static void pantallaLogin(){
		render();
	}
	
	public static void logout(){
		Cache.set("user", null);
		pantallaLogin();
	}
}
