package controllers;

import java.sql.SQLException;
import java.util.List;

import models.DiscotecaDTO;


public class AltaDiscoteca extends DiscotecaGenericController {
	public static void verAltaDiscoteca(){
		
		render();
	}
	
	public static void editaDiscoteca(String idDiscoteca){
		String sql = "select * from public.\"discotecas\" where discotecas.\"idDiscoteca\"="+idDiscoteca;
		List<DiscotecaDTO> l = seleccionarDiscotecas(sql);
		if(l.size()>0){
			DiscotecaDTO discotecaDTO = l.get(0);
			render(discotecaDTO);
		}else{
			errorEditaDiscoteca();
		}
	}
	
	public static void modificaDiscoteca(String idDiscoteca, String nombre,
			String descripcion, String latitud, String longitud){
		
		DiscotecaDTO dto = new DiscotecaDTO();
		dto.setNombre(nombre);
		dto.setDescripcion(descripcion);
		dto.setLatitud(Double.parseDouble(latitud));
		dto.setLongitud(Double.parseDouble(longitud));
		dto.setIdDiscoteca(Long.parseLong(idDiscoteca));
		
		boolean res = modificarDiscoteca(dto);
		if(res)
			render();
		else
			errorModificaDiscoteca();
		
	}
	
	public static void errorModificaDiscoteca(){
		render();
	}
	
	public static void errorEditaDiscoteca(){
		render();
	}
	
	public static void insertarDiscoteca(String nombre, String descripcion,
			String latitud, String longitud){
		
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
		render();
	}
}
