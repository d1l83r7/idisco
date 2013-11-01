package controllers;

import java.util.ArrayList;
import java.util.List;

import models.DiscotecaDTO;
import play.mvc.Controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GetDiscotecasByName extends DiscotecaGenericController {
	public static void getDiscotecasByName(String name){
		String sql = "SELECT * FROM DISCOTECAS WHERE LOWER(NOMBRE) LIKE '%"+name.toLowerCase()+"%'";
		List<DiscotecaDTO> l = seleccionarDiscotecas(sql);
		JsonArray array = listDicotecaDTOToJSonArray(l);
		renderJSON(array);
	}
}
