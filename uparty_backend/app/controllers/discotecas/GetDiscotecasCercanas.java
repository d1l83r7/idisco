package controllers.discotecas;

import java.util.ArrayList;
import java.util.List;

import models.Discoteca;
import play.mvc.Controller;

import com.google.gson.JsonArray;

import es.uparty.utils.Utils;

public class GetDiscotecasCercanas extends DiscotecaGenericController {
	public static void getDiscotecasCercanas(String distancia, String latitud, String longitud, String idioma){
		String sql = "SELECT * FROM DISCOTECAS";
		List<Discoteca> l = seleccionarDiscotecas(sql);
		double longOrigen = Double.parseDouble(longitud);
		double latOrigen = Double.parseDouble(latitud);
		
		List<Discoteca> lDiscotecasCercanas = new ArrayList<Discoteca>();
		for(Discoteca dto: l){
			double dist = Utils.calculaDistancia(latOrigen, longOrigen, dto.getLatitud(), dto.getLongitud());
			Double d = Double.valueOf(dist)*1000;
			if(d.intValue()<=Integer.parseInt(distancia)){
				lDiscotecasCercanas.add(dto);
			}
		}
		
		JsonArray array = listDicotecaDTOToJSonArray(lDiscotecasCercanas, idioma);
		renderJSON(array);
	}

}
