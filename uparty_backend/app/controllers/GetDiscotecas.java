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
		String sql = "SELECT * FROM DISCOTECAS";
		List<DiscotecaDTO> l = seleccionarDiscotecas(sql);
		JsonArray array = listDicotecaDTOToJSonArray(l);
		renderJSON(array);
	}

}
