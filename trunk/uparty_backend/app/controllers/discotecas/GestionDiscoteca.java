package controllers.discotecas;

import java.io.File;
import java.util.List;

import models.Discoteca;

public class GestionDiscoteca extends DiscotecaGenericController {
	public static void gestionDiscoteca(){
		usuarioLogado();
		render();		
	}
	
	public static void verGestionDiscoteca(String name){
		usuarioLogado();
		String sql = "SELECT * FROM DISCOTECAS WHERE LOWER(NOMBRE) LIKE '%"+name.toLowerCase()+"%'";
		List<Discoteca> l = seleccionarDiscotecas(sql);
		render(l,name);
	}
	
	public static void eliminaDiscoteca(String idDiscoteca){
		usuarioLogado();
		boolean res = borrarDiscoteca(idDiscoteca);
		if(res)
			render();
		else
			errorEliminaDiscoteca();
	}
	
	public static void errorEliminaDiscoteca(){
		usuarioLogado();
		render();
	}
	
	public static void editaDiscoteca(String idDiscoteca, String name){
		usuarioLogado();
		String sql = "select * from public.\"discotecas\" where discotecas.\"idDiscoteca\"="+idDiscoteca;
		List<Discoteca> l = seleccionarDiscotecas(sql);
		if(l.size()>0){
			Discoteca discoteca = l.get(0);
			render(discoteca,name);
		}else{
			errorEditaDiscoteca();
		}
	}
	
	public static void modificaDiscoteca(String idDiscoteca, String nombre,
			String descripcion, String latitud, String longitud, String nombreImg, File imagen){
		usuarioLogado();
		Discoteca dto = new Discoteca();
		dto.setNombre(nombre);
		dto.setDescripcion(descripcion);
		dto.setLatitud(Double.parseDouble(latitud));
		dto.setLongitud(Double.parseDouble(longitud));
		dto.setIdDiscoteca(Long.parseLong(idDiscoteca));
		if(imagen!=null){
			deleteFile(nombreImg);
			saveFile(imagen);
			dto.setNombreImg(imagen.getName());
		}else{
			dto.setNombreImg(nombreImg);
		}
		boolean res = modificarDiscoteca(dto);
		if(res)
			render();
		else
			errorModificaDiscoteca();
		
	}
	
	public static void errorModificaDiscoteca(){
		usuarioLogado();
		render();
	}
	
	public static void errorEditaDiscoteca(){
		usuarioLogado();
		render();
	}
}
