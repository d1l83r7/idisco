package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.DiscotecaDTO;
import play.db.DB;
import play.mvc.Controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class DiscotecaGenericController extends Controller {

	protected static List<DiscotecaDTO> seleccionarDiscotecas(String sql){
		List<DiscotecaDTO> l = new ArrayList<DiscotecaDTO>();
		Connection conn = DB.getConnection();
		try{
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				DiscotecaDTO dto = new DiscotecaDTO();
				dto.setIdDiscoteca(rs.getBigDecimal(1).longValue());
				dto.setNombre(rs.getString(2));
				dto.setDescripcion(rs.getString(3));
				dto.setLatitud(rs.getDouble(4));
				dto.setLongitud(rs.getDouble(5));
				l.add(dto);
			}

				  // close all the connections.
			rs.close();
			statement.close();
			conn.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}
		
		return l;
	}
	
	protected static boolean modificarDiscoteca(DiscotecaDTO dto){
		String sql = "UPDATE discotecas "+
				   		"SET nombre='"+dto.getNombre()+"', descripcion='"+dto.getDescripcion()+"', latitud="+String.valueOf(dto.getLatitud())+", longitud="+String.valueOf(dto.getLongitud())+" "+
				   		"WHERE \"idDiscoteca\"="+String.valueOf(dto.getIdDiscoteca());
		Connection conn = DB.getConnection();
		return executeQuery(conn, sql);
	}
	
	protected static boolean darAltaDiscoteca(DiscotecaDTO dto)throws SQLException{
		Connection conn = DB.getConnection();
		long idDiscoteca = obtenerUltimoId(conn);
		idDiscoteca++;
		dto.setIdDiscoteca(idDiscoteca);
		boolean res = insertarDiscoteca(dto, conn);
		conn.close();
		return res;
	}
	
	private static long obtenerUltimoId(Connection conn){
		long idDiscoteca = 0;
		String sql = "SELECT "
				+ "discotecas.\"idDiscoteca\" " 	
				+ "FROM " 
				+ "public.discotecas " 
				+ "order by discotecas.\"idDiscoteca\" desc;";
		
		try{
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			rs.next();
			idDiscoteca = rs.getLong("idDiscoteca");
			// close all the connections.
			rs.close();
			statement.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}
		
		return idDiscoteca;
	}
	
	public static boolean borrarDiscoteca(String idDiscoteca){
		String sql = "Delete from discotecas where discotecas.\"idDiscoteca\"="+idDiscoteca;
		
		try{
			Connection conn = DB.getConnection();
			Statement statement = conn.createStatement();
			statement.execute(sql);
			
			statement.close();
			conn.close();
			return true;
		}catch(SQLException sqle){
			sqle.printStackTrace();
			return false;
		}
	}
	
	private static boolean insertarDiscoteca(DiscotecaDTO dto, Connection conn){
		String sql = "INSERT INTO " +
				"discotecas(" +
					"\"idDiscoteca\", " +
					"nombre, " +
					"descripcion, " +
					"latitud, " +
					"longitud) " +
				"VALUES (" +
					String.valueOf(dto.getIdDiscoteca())+", " +
					"'"+dto.getNombre()+"', " +
					"'"+dto.getDescripcion()+"', " +
					String.valueOf(dto.getLatitud()) +","+
					String.valueOf(dto.getLongitud())+");";
		return executeQuery(conn, sql);
	}
	
	private static boolean executeQuery(Connection conn, String sql){
		try{
			Statement statement = conn.createStatement();
			statement.execute(sql);
			statement.close();
			
			return true;
		}catch(SQLException sqle){
			sqle.printStackTrace();
			return false;
		}
	}
	
	protected static JsonArray listDicotecaDTOToJSonArray(List<DiscotecaDTO> l){
		JsonArray array = new JsonArray();
		for(DiscotecaDTO dto: l){
			JsonObject ob = new JsonObject();
			ob.addProperty("idDiscoteca", dto.getIdDiscoteca());
			ob.addProperty("nombre", dto.getNombre());
			ob.addProperty("latitud", dto.getLatitud());
			ob.addProperty("longitud", dto.getLongitud());
			ob.addProperty("descripcion", dto.getDescripcion());
			array.add(ob);	
		}
		return array;
	}
}
