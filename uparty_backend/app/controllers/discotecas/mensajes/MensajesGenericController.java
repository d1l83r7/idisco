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
}
