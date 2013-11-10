package controllers.discotecas;

import java.util.ArrayList;
import java.util.List;

import models.Discoteca;
import play.mvc.Controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GetDiscotecasByName extends DiscotecaGenericController {
	public static void getDiscotecasByName(String name){
		String sql = "SELECT * FROM DISCOTECAS WHERE LOWER(NOMBRE) LIKE '%"+name.toLowerCase()+"%'";
		List<Discoteca> l = seleccionarDiscotecas(sql);
		JsonArray array = listDicotecaDTOToJSonArray(l);
		renderJSON(array);
	}
}
