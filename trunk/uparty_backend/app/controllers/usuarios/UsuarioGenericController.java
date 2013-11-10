package controllers.usuarios;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Discoteca;
import models.User;
import play.db.DB;

public class UsuarioGenericController extends SecurityController {

	public static List<User> getUsuarios(String usuario){
		String sql = "SELECT * FROM USUARIOS WHERE LOWER(USUARIO) LIKE '%"+usuario.toLowerCase()+"%'";

		List<User> l = new ArrayList<User>();
		Connection conn = DB.getConnection();
		try{
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				User dto = new User();
				dto.setId(rs.getLong(1));
				dto.setUsuario(rs.getString(2));
				dto.setPassword(rs.getString(3));
				dto.setPerfil(rs.getString(4));
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
	private static long obtenerUltimoId(Connection conn){
		long idUsuario = 0;
		String sql = "SELECT "
				+ "usuarios.\"idUsuario\" " 	
				+ "FROM " 
				+ "public.usuarios " 
				+ "order by usuarios.\"idUsuario\" desc;";
		
		try{
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			rs.next();
			idUsuario = rs.getLong("idUsuario");
			// close all the connections.
			rs.close();
			statement.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}
		
		return idUsuario;
	}
	
	public static boolean borrarUsuario(String idUsuario){
		String sql = "Delete from usuarios where usuarios.\"idUsuario\"="+idUsuario;
		
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
	
	protected static boolean darAltaUsuario(User dto)throws SQLException{
		Connection conn = DB.getConnection();
		long idUsuario = obtenerUltimoId(conn);
		idUsuario++;
		dto.setId(idUsuario);
		boolean res = insertarUsuario(dto, conn);
		conn.close();
		return res;
	}
	
	private static boolean insertarUsuario(User u, Connection conn){
		String sql = "INSERT INTO " +
				"usuarios(" +
					"\"idUsuario\", " +
					"usuario, " +
					"clave, " +
					"perfil) " +
				"VALUES (" +
					String.valueOf(u.getId())+", " +
					"'"+u.getUsuario()+"', " +
					"'"+u.getPassword()+"', " +
					"'"+u.getPerfil()+"');";
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
	
	protected static boolean modificarUsuario(User dto){
		String sql = "UPDATE usuarios "+
				   		"SET usuario='"+dto.getUsuario()+"', clave='"+dto.getPassword()+"', perfil='"+dto.getPerfil()+"' "+
				   		"WHERE \"idUsuario\"="+String.valueOf(dto.getId());
		Connection conn = DB.getConnection();
		return executeQuery(conn, sql);
	}
	
	protected static List<User> seleccionarUsuarios(String sql){
		List<User> l = new ArrayList<User>();
		Connection conn = DB.getConnection();
		try{
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				User dto = new User();
				dto.setId(rs.getLong(1));
				dto.setUsuario(rs.getString(2));
				dto.setPassword(rs.getString(3));
				dto.setPerfil(rs.getString(4));
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
}
