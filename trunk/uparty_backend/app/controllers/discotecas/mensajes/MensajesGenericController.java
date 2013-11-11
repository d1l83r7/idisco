package controllers.discotecas.mensajes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Discoteca;
import models.Mensaje;
import play.db.DB;
import play.mvc.Controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import controllers.usuarios.SecurityController;

public class MensajesGenericController extends SecurityController {
	protected static List<Mensaje> seleccionarMensajesPorMuro(long idMuro){
		List<Mensaje> l = new ArrayList<Mensaje>();
		Connection conn = DB.getConnection();
		String sql = 	"select m.\"idMensaje\", m.\"mensaje\", mu.\"idDiscoteca\" , u.\"idUsuario\", u.\"usuario\" "+
						"from mensajes as m " + 
						"inner join usuarios as u on m.\"idUsuario\" = u.\"idUsuario\" "+
						"inner join muros as mu on mu.\"idMuro\" = m.\"idMuro\" "+ 
						"inner join discotecas as d on d.\"idDiscoteca\" = mu.\"idDiscoteca\" " +
						"where m.\"idMuro\" = " + String.valueOf(idMuro);
		try{
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				Mensaje dto = new Mensaje();
				dto.setIdMensaje(rs.getBigDecimal(1).longValue());
				dto.setMensaje(rs.getString(2));
				dto.setIdDiscoteca(rs.getBigDecimal(3).longValue());
				dto.setIdUsuario(rs.getBigDecimal(4).longValue());
				dto.setUsuario(rs.getString(5));
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
		long idMensaje = 0;
		String sql = "SELECT "
				+ "mensajes.\"idMensaje\" " 	
				+ "FROM " 
				+ "public.mensajes " 
				+ "order by mensajes.\"idMensaje\" desc;";
		
		try{
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			rs.next();
			idMensaje = rs.getLong("idMensaje");
			// close all the connections.
			rs.close();
			statement.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}
		
		return idMensaje;
	}
	
	protected static boolean insertarMensaje(Mensaje m){
		Connection conn = DB.getConnection();
		long id = obtenerUltimoId(conn);
		id++;
		m.setIdMensaje(id);
		String sql = "INSERT INTO mensajes("+ 
            " \"idMensaje\", mensaje, \"idUsuario\", \"idMuro\") " +
            "VALUES ("+String.valueOf(m.getIdMensaje())+", '"+m.getMensaje()+"', "+m.getIdUsuario()+", "+m.getIdMuro()+");";
		System.out.println(sql);
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
	
	protected static JsonArray listMensajesToJSonArray(List<Mensaje> l){
		JsonArray array = new JsonArray();
		for(Mensaje dto: l){
			JsonObject ob = new JsonObject();
			ob.addProperty("idMensaje", dto.getIdMensaje());
			ob.addProperty("usuario", dto.getUsuario());
			ob.addProperty("mensaje", dto.getMensaje());
			array.add(ob);	
		}
		return array;
	}
	
	protected static boolean deleteMensaje(long idMensaje){
		String sql = "Delete from mensajes where mensajes.\"idMensaje\"="+String.valueOf(idMensaje);
		
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
}
