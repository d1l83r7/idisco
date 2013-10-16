package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.DiscotecaDTO;
import play.db.DB;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GetDiscotecas extends DiscotecaGenericController {
	
	public static void getDiscotecas(){
		JsonArray array = new JsonArray();
		String sql = "SELECT * FROM DISCOTECAS";
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
