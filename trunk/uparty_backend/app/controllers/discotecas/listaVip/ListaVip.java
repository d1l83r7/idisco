package controllers.discotecas.listaVip;

import java.util.Date;
import java.util.List;

import models.ListaVipItem;
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
}
