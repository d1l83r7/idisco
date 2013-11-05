package controllers;

import java.util.List;

import models.DiscotecaDTO;

public class GestionDiscoteca extends DiscotecaGenericController {
	public static void gestionDiscoteca(){
		usuarioLogado();
		render();		
	}
	
	public static void verGestionDiscoteca(String name){
		usuarioLogado();
		String sql = "SELECT * FROM DISCOTECAS WHERE LOWER(NOMBRE) LIKE '%"+name.toLowerCase()+"%'";
		List<DiscotecaDTO> l = seleccionarDiscotecas(sql);
		render(l);
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
	
	public static void editaDiscoteca(String idDiscoteca){
		usuarioLogado();
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
			String descripcion, String latitud, String longitud, String nombreImg){
		usuarioLogado();
		DiscotecaDTO dto = new DiscotecaDTO();
		dto.setNombre(nombre);
		dto.setDescripcion(descripcion);
		dto.setLatitud(Double.parseDouble(latitud));
		dto.setLongitud(Double.parseDouble(longitud));
		dto.setIdDiscoteca(Long.parseLong(idDiscoteca));
		dto.setNombreImg(nombreImg);
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
