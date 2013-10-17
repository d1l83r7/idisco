package controllers;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import es.uparty.utils.Utils;

import models.DiscotecaDTO;

public class GetDistanciaToDisco extends DiscotecaGenericController {
	public static void getDistanciasToDisco(String latitud, String longitud){
		String sql = "SELECT * FROM DISCOTECAS";
		JsonArray array = new JsonArray();
		double latOrigen = Double.parseDouble(latitud);
		double longOrigen = Double.parseDouble(longitud);
		
		List<DiscotecaDTO> l = ejecutarSQL(sql);
		for(DiscotecaDTO dto:l){
			
			JsonObject ob = new JsonObject();
			String nombre = dto.getNombre();
			double latitudDisco = dto.getLatitud();
			double longitudDisco =dto.getLongitud();
			double distancia = Utils.calculaDistancia(latOrigen, longOrigen, latitudDisco, longitudDisco);
			ob.addProperty("nombreDiscoteca", nombre);
			ob.addProperty("distancia", Double.toString(distancia*1000));
			array.add(ob);
		}
		
		renderJSON(array);
	
	}
}
