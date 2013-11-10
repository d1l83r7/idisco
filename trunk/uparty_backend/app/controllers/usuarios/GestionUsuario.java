package controllers.usuarios;

import java.util.List;

import models.Discoteca;
import models.User;

public class GestionUsuario extends UsuarioGenericController {
	public static void gestionUsuario(){
		usuarioLogado();
		render();
	}
	
	public static void verGestionUsuario(String usuario){
		usuarioLogado();
		String sql = "SELECT * FROM USUARIOS WHERE LOWER(USUARIO) LIKE '%"+usuario.toLowerCase()+"%'";
		List<User> l = seleccionarUsuarios(sql);
		render(l,usuario);
	}
	
	public static void eliminaUsuario(String idUsuario){
		usuarioLogado();
		boolean res = borrarUsuario(idUsuario);
		if(res)
			render();
		else
			errorEliminaUsuario();
	}
	
	public static void errorEliminaUsuario(){
		usuarioLogado();
		render();
	}
	
	public static void editaUsuario(String idUsuario, String usuario){
		usuarioLogado();
		String sql = "select * from public.\"usuarios\" where usuarios.\"idUsuario\"="+idUsuario;
		List<User> l = seleccionarUsuarios(sql);
		if(l.size()>0){
			User user = l.get(0);
			render(user,usuario);
		}else{
			errorEditaUsuario();
		}
	}
	
	public static void modificaUsuario(String usuario, String password,
			String perfil, String idUsuario){
		usuarioLogado();
		User dto = new User();
		dto.setUsuario(usuario);
		dto.setPassword(password);
		dto.setPerfil(perfil);
		dto.setId(Long.parseLong(idUsuario));
		
		boolean res = modificarUsuario(dto);
		if(res)
			render();
		else
			errorModificaUsuario();
		
	}
	
	public static void errorModificaUsuario(){
		usuarioLogado();
		render();
	}
	
	public static void errorEditaUsuario(){
		usuarioLogado();
		render();
	}
}
