package controllers.discotecas.listaVip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.ListaVipItem;
import play.db.DB;
import controllers.usuarios.SecurityController;

public class ListaVipGenericController extends SecurityController {
	public static List<ListaVipItem> obtenerListaVip(long idDiscoteca, Date d){
		List<ListaVipItem> l = new ArrayList<ListaVipItem>();
		Connection conn = DB.getConnection();
		String sql = 	"SELECT lv.*, u.usuario "+
						"FROM \"listas_VIP\" as lv "+
						"inner join \"usuarios\" as u on u.\"idUsuario\" = lv.\"idUsuario\" " +
						"where lv.\"idDiscoteca\" = ? and fecha = ?";
		
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, idDiscoteca);
			ps.setDate(2, new java.sql.Date(d.getTime()));
			
			ResultSet rs =ps.executeQuery();
			
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
			ps.close();
			conn.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}
		
		return l;
	}
}
