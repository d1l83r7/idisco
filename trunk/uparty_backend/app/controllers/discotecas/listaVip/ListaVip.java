package controllers.discotecas.listaVip;

import java.util.Date;
import java.util.List;

import controllers.discotecas.DiscotecaGenericController;

import models.ListaVipItem;
import models.User;
import es.uparty.utils.Utils;

public class ListaVip extends ListaVipGenericController {
	public static void verListaVip(String idDiscoteca, String name, String fechaListaVip){
		usuarioLogado();
		long idDisco = Long.parseLong(idDiscoteca);
		Date d = null;
		try{
			d = Utils.dateToString(fechaListaVip);
		}catch(Exception e){
			e.printStackTrace();
			d = new Date();
		}
		List<ListaVipItem> l = obtenerListaVip(idDisco, d);
		render(idDiscoteca,name,l);
	}
	
	public static void darAltaListaVIP(String usuario, String password, String idDiscoteca, String acompanyantes){
		String user = Utils.decrypt(usuario);
		String pass = Utils.decrypt(password);
		User u = DiscotecaGenericController.getUsuario(user, pass,false);
		ListaVipItem lvi = new ListaVipItem();
		lvi.setAcompanyantes(Integer.parseInt(acompanyantes));
		lvi.setFecha(new Date());
		lvi.setIdDiscoteca(Long.parseLong(idDiscoteca));
		lvi.setIdUsuario(u.getId());
		insertarEnListaVip(lvi);
	}
}
