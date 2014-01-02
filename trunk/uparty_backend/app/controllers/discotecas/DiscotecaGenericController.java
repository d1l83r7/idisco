package controllers.discotecas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Discoteca;
import models.User;
import play.Play;
import play.db.DB;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import controllers.usuarios.SecurityController;

public class DiscotecaGenericController extends SecurityController {

	protected static List<Discoteca> seleccionarDiscotecas(String sql){
		List<Discoteca> l = new ArrayList<Discoteca>();
		Connection conn = DB.getConnection();
		try{
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				Discoteca dto = new Discoteca();
				dto.setIdDiscoteca(rs.getBigDecimal(1).longValue());
				dto.setNombre(rs.getString(2));
				dto.setDescripcion(rs.getString(3));
				dto.setLatitud(rs.getDouble(4));
				dto.setLongitud(rs.getDouble(5));
				dto.setNombreImg(rs.getString(6));
				dto.setAirbopAppKey(rs.getString(7));
				dto.setAirbopAppSecret(rs.getString(8));
				dto.setGoogleProjectNumber(rs.getString(9));
				dto.setDescripcion_ca(rs.getString(10));
				dto.setDescripcion_en(rs.getString(11));
				boolean listaVipActiva = rs.getBoolean(12);
				if(listaVipActiva)
					dto.setListaVipActiva("S");
				else
					dto.setListaVipActiva("N");
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
	
	protected static boolean modificarDiscoteca(Discoteca dto){
		String sql = "UPDATE discotecas "+
				   		"SET nombre=?," +
				   		"descripcion=?, " +
				   		"descripcion_ca=?, " +
				   		"descripcion_en=?, " +
				   		"latitud=?, " +
				   		"longitud=?, " +
				   		"nombre_img=?, "+
				   		"airbopappkey=?, "+
				   		"airbopappsecret=?, "+
				   		"googleprojectnumber=?, "+
				   		"lista_vip=? "+
				   		"WHERE \"idDiscoteca\"=?";
		Connection conn = DB.getConnection();
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			
			
			ps.setString(1, dto.getNombre());
			ps.setString(2, dto.getDescripcion());
			ps.setString(3, dto.getDescripcion_ca());
			ps.setString(4, dto.getDescripcion_en());
			ps.setDouble(5, dto.getLatitud());
			ps.setDouble(6, dto.getLongitud());
			ps.setString(7, dto.getNombreImg());
			ps.setString(8, dto.getAirbopAppKey());
			ps.setString(9, dto.getAirbopAppSecret());
			ps.setString(10, dto.getGoogleProjectNumber());
			if(dto.getListaVipActiva().equals("S"))
				ps.setBoolean(11, true);
			else
				ps.setBoolean(11, false);
			ps.setLong(12, dto.getIdDiscoteca());
			
			ps.executeUpdate();
			ps.close();
			
			return true;
		}catch(SQLException sqle){
			sqle.printStackTrace();
			return false;
		}
	}
	
	protected static boolean darAltaDiscoteca(Discoteca dto)throws SQLException{
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
	
	private static boolean insertarDiscoteca(Discoteca dto, Connection conn){
		String sql = "INSERT INTO " +
				"discotecas(" +
					"\"idDiscoteca\", " +
					"nombre, " +
					"descripcion, " +
					"descripcion_ca, " +
					"descripcion_en, " +
					"latitud, " +
					"longitud," +
					"nombre_img," +
					"airbopappkey," +
					"airbopappsecret," +
					"googleprojectnumber," +
					"lista_vip) " +
				"VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
					
		
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setLong(1, dto.getIdDiscoteca());
			ps.setString(2, dto.getNombre());
			ps.setString(3, dto.getDescripcion());
			ps.setString(4, dto.getDescripcion_ca());
			ps.setString(5, dto.getDescripcion_en());
			ps.setDouble(6, dto.getLatitud());
			ps.setDouble(7, dto.getLongitud());
			ps.setString(8, dto.getNombreImg());
			ps.setString(9, dto.getAirbopAppKey());
			ps.setString(10, dto.getAirbopAppSecret());
			ps.setString(11, dto.getGoogleProjectNumber());
			if(dto.getListaVipActiva().equals("S"))
				ps.setBoolean(12, true);
			else
				ps.setBoolean(12, false);
			ps.executeUpdate();
			ps.close();
			
			return true;
		}catch(SQLException sqle){
			sqle.printStackTrace();
			return false;
		}
	}
	
//	private static boolean executeQuery(Connection conn, String sql){
//		try{
//			Statement statement = conn.createStatement();
//			statement.execute(sql);
//			statement.close();
//			
//			return true;
//		}catch(SQLException sqle){
//			sqle.printStackTrace();
//			return false;
//		}
//	}
	
	protected static JsonArray listDicotecaDTOToJSonArray(List<Discoteca> l, String idioma){
		JsonArray array = new JsonArray();
		for(Discoteca dto: l){
			JsonObject ob = new JsonObject();
			ob.addProperty("idDiscoteca", dto.getIdDiscoteca());
			ob.addProperty("nombre", dto.getNombre());
			ob.addProperty("latitud", dto.getLatitud());
			ob.addProperty("longitud", dto.getLongitud());
			if(idioma.equals("es"))
				ob.addProperty("descripcion", dto.getDescripcion());
			else if(idioma.equals("ca"))
				ob.addProperty("descripcion", dto.getDescripcion_ca());
			else if(idioma.equals("en"))
				ob.addProperty("descripcion", dto.getDescripcion_en());
			ob.addProperty("nombreImg", dto.getNombreImg());
			ob.addProperty("airbopAppKey", dto.getAirbopAppKey());
			ob.addProperty("airbopAppSecret", dto.getAirbopAppSecret());
			ob.addProperty("googleProjectNumber", dto.getGoogleProjectNumber());
			array.add(ob);	
		}
		return array;
	}
	
	public static User getUsuario(String usuario, String password, boolean closeConnection){
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
			if(closeConnection)
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
