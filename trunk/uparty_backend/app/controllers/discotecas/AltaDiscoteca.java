package controllers.discotecas;

import java.io.File;
import java.sql.SQLException;

import models.Discoteca;



public class AltaDiscoteca extends DiscotecaGenericController {
	public static void verAltaDiscoteca(){
		usuarioLogado();
		render();
	}
	
	public static void insertarDiscoteca(String nombre, String descripcion,
			String latitud, String longitud, File imagen, String airbopAppKey,
			String airbopAppSecret, String googleProjectNumber,
			String descripcion_ca, String descripcion_en){
		usuarioLogado();
		Discoteca dto = new Discoteca();
		dto.setDescripcion(descripcion);
		dto.setNombre(nombre);
		dto.setLatitud(Double.parseDouble(latitud));
		dto.setLongitud(Double.parseDouble(longitud));
		dto.setAirbopAppKey(airbopAppKey);
		dto.setAirbopAppSecret(airbopAppSecret);
		dto.setGoogleProjectNumber(googleProjectNumber);
		dto.setDescripcion_ca(descripcion_ca);
		dto.setDescripcion_en(descripcion_en);
		
		if(imagen!=null){
			dto.setNombreImg(imagen.getName());
			saveFile(imagen);
		}
		
		boolean res = true;
		try{
			res = darAltaDiscoteca(dto);
		}catch(SQLException sqle){
			sqle.printStackTrace();
			res = false;
		}
		
		if(res)
			render();
		else
			errorAltaDiscoteca();
	}
	
	public static void errorAltaDiscoteca(){
		usuarioLogado();
		render();
	}
}
