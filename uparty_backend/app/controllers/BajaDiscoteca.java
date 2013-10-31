package controllers;

public class BajaDiscoteca extends DiscotecaGenericController {

	public static void eliminaDiscoteca(String idDiscoteca){
		usuarioLogado();
		boolean res = borrarDiscoteca(idDiscoteca);
		if(res)
			render();
		else
			errorEliminaDiscoteca();
	}
	
	public static void errorEliminaDiscoteca(){
		usuarioLogado();
		render();
	}
}
