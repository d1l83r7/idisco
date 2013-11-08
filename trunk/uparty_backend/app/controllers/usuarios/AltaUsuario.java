package controllers.usuarios;

import java.sql.SQLException;

import models.User;

public class AltaUsuario extends UsuarioGenericController {
	public static void insertarUsuario(String usuario, String password,
			String perfil){
		usuarioLogado();
		User u = new User();
		u.setUsuario(usuario);
		u.setPassword(password);
		u.setPerfil(perfil);
		boolean res = false;
		try{
			res = darAltaUsuario(u);
		}catch(SQLException sqle){
			sqle.printStackTrace();
			res = false;
		}
		
		if(res)
			render();
		else
			errorAltaUsuario();
	}
	
	public static void verAltaUsuario(){
		render();
	}
	
	public static void errorAltaUsuario(){
		render();
	}
}
