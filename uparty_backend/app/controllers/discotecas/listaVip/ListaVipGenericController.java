package controllers.discotecas.listaVip;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import play.db.DB;

import models.ListaVipItem;
import models.Mensaje;
import controllers.usuarios.SecurityController;

public class ListaVipGenericController extends SecurityController {
	public static List<ListaVipItem> obtenerInscritosPorIdDiscoteca(long idDiscoteca){
		List<ListaVipItem> l = new ArrayList<ListaVipItem>();
		Connection conn = DB.getConnection();
		String sql = 	"SELECT lv.*, u.usuario "+
						"FROM \"listas_VIP\" as lv "+
						"inner join \"usuarios\" as u on u.\"idUsuario\" = lv.\"idUsuario\" " +
						"where lv.\"idDiscoteca\" = " + String.valueOf(idDiscoteca);
		
		try{
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				ListaVipItem dto = new ListaVipItem();
				dto.setIdListaVip(rs.getLong(1));
				dto.setIdUsuario(rs.getLong(2));
				dto.setIdDiscoteca(rs.getLong(3));
				dto.setAcompanyantes(rs.getInt(4));
				dto.setFecha(rs.getDate(5));
				dto.setNombreUsuario(rs.getString(6));
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
