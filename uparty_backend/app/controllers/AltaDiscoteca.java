package controllers;

import java.sql.SQLException;
import java.util.List;

import models.DiscotecaDTO;


public class AltaDiscoteca extends DiscotecaGenericController {
	public static void verAltaDiscoteca(){
		usuarioLogado();
		render();
	}
	
	public static void insertarDiscoteca(String nombre, String descripcion,
			String latitud, String longitud){
		usuarioLogado();
		DiscotecaDTO dto = new DiscotecaDTO();
		dto.setDescripcion(descripcion);
		dto.setNombre(nombre);
		dto.setLatitud(Double.parseDouble(latitud));
		dto.setLongitud(Double.parseDouble(longitud));
		boolean res = false;
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
