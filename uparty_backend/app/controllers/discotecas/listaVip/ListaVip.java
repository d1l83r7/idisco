package controllers.discotecas.listaVip;

import java.util.List;

import models.ListaVipItem;

public class ListaVip extends ListaVipGenericController {
	public static void verListaVip(String idDiscoteca, String name){
		usuarioLogado();
		long idDisco = Long.parseLong(idDiscoteca);
		List<ListaVipItem> l = obtenerInscritosPorIdDiscoteca(idDisco);
		render(idDiscoteca,name,l);
	}
}
