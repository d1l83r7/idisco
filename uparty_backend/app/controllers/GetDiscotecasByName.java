package controllers;

import java.util.ArrayList;
import java.util.List;

import models.DiscotecaDTO;
import play.mvc.Controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GetDiscotecasByName extends DiscotecaGenericController {
	public static void getDiscotecasByName(String name){
		JsonArray array = new JsonArray();
		String sql = "SELECT * FROM DISCOTECAS WHERE LOWER(NOMBRE) LIKE '%"+name.toLowerCase()+"%'";
		List<DiscotecaDTO> l = ejecutarSQL(sql);
		
		for(DiscotecaDTO dto: l){
			JsonObject ob = new JsonObject();
			ob.addProperty("nombre", dto.getNombre());
			ob.addProperty("latitud", dto.getLatitud());
			ob.addProperty("longitud", dto.getLongitud());
			ob.addProperty("descripcion", dto.getDescripcion());
			array.add(ob);	
		}
		
		renderJSON(array);
	}
}
