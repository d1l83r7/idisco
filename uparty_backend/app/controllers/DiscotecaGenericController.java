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

public class DiscotecaGenericController extends Controller {

	protected static List<DiscotecaDTO> ejecutarSQL(String sql){
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
}
