package controllers;

import java.util.ArrayList;
import java.util.List;

import models.DiscotecaDTO;
import play.mvc.Controller;

import com.google.gson.JsonArray;

import es.uparty.utils.Utils;

public class GetDiscotecasCercanas extends DiscotecaGenericController {
	public static void getDiscotecasCercanas(String distancia, String latitud, String longitud){
		String sql = "SELECT * FROM DISCOTECAS";
		List<DiscotecaDTO> l = seleccionarDiscotecas(sql);
		double longOrigen = Double.parseDouble(longitud);
		double latOrigen = Double.parseDouble(latitud);
		
		List<DiscotecaDTO> lDiscotecasCercanas = new ArrayList<DiscotecaDTO>();
		for(DiscotecaDTO dto: l){
			double dist = Utils.calculaDistancia(latOrigen, longOrigen, dto.getLatitud(), dto.getLongitud());
			Double d = Double.valueOf(dist)*1000;
			if(d.intValue()<=Integer.parseInt(distancia)){
				lDiscotecasCercanas.add(dto);
			}
		}
		
		JsonArray array = listDicotecaDTOToJSonArray(lDiscotecasCercanas);
		renderJSON(array);
	}

}
