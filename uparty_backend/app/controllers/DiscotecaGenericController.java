package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.DiscotecaDTO;
import models.User;
import play.Play;
import play.db.DB;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class DiscotecaGenericController extends SecurityController {

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
				dto.setNombreImg(rs.getString(6));
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
				   		"SET nombre='"+dto.getNombre()+"', " +
				   		"descripcion='"+dto.getDescripcion()+"', " +
				   		"latitud="+String.valueOf(dto.getLatitud())+", " +
				   		"longitud="+String.valueOf(dto.getLongitud())+", " +
				   		"nombre_img='"+dto.getNombreImg()+"' "+
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
					"longitud," +
					"nombre_img) " +
				"VALUES (" +
					String.valueOf(dto.getIdDiscoteca())+", " +
					"'"+dto.getNombre()+"', " +
					"'"+dto.getDescripcion()+"', " +
					String.valueOf(dto.getLatitud()) +","+
					String.valueOf(dto.getLongitud())+"," +
					"'"+dto.getNombreImg()+"');";
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
			ob.addProperty("nombreImg", dto.getNombreImg());
			array.add(ob);	
		}
		return array;
	}
	
	protected static User getUsuario(String usuario, String password){
		List<User>lUsers = new ArrayList<User>();
		String sql = "Select * " +
				"from usuarios " +
				"where usuarios.\"usuario\"='"+usuario+
				"' and usuarios.\"clave\"='"+password+"'";
		try{
			Connection conn = DB.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getLong(1));
				user.setUsuario(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setPerfil(rs.getString(4));
				lUsers.add(user);
			}
	
				  // close all the connections.
			rs.close();
			statement.close();
			conn.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			return null;
		}
		
		if(lUsers.size()>0){
			return lUsers.get(0);
		}else
			return null;
	}
	
	protected static void saveFile(File imagen){
		try{
			String path = Play.getFile("public/images/discotecas").getAbsolutePath();
			FileInputStream fis = new FileInputStream(imagen);
			FileOutputStream fos = new FileOutputStream(new File(path+File.separator+imagen.getName()));
			int value = 0;
			do{
				 value = fis.read();
				 fos.write(value);
			}while(value!=-1);
			fis.close();
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected static void deleteFile(String fileName){
		String path = Play.getFile("public/images/discotecas").getAbsolutePath();
		File f = new File(path+File.pathSeparator+fileName);
		f.delete();
	}
}
