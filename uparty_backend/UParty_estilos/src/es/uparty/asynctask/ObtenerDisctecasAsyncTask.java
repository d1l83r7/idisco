package es.uparty.asynctask;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import es.uparty.dto.DiscotecaDTO;

public class ObtenerDisctecasAsyncTask extends
		AsyncTask<Double, Integer, List<DiscotecaDTO>> {

	private List<DiscotecaDTO> listaDiscotecas = null;
	
	@Override
	protected List<DiscotecaDTO> doInBackground(Double... arg0) {
		// TODO Auto-generated method stub
		
		DiscotecaDTO discotecaDTO = new DiscotecaDTO();
		discotecaDTO.setDescripcio("Descripción del Apolo");
		discotecaDTO.setNombre("Apolo");
		discotecaDTO.setLatitud("41.3744");
		discotecaDTO.setLongitud("2.16772");
		listaDiscotecas.add(discotecaDTO);
		
		discotecaDTO = new DiscotecaDTO();
		discotecaDTO.setDescripcio("Descripción del City Hall");
		discotecaDTO.setNombre("City Hall");
		discotecaDTO.setLatitud("41.38770024");
		discotecaDTO.setLongitud("2.168136835");
		listaDiscotecas.add(discotecaDTO);
		
		return listaDiscotecas;
	}

	@Override
	protected void onPostExecute(List<DiscotecaDTO> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		listaDiscotecas = new ArrayList<DiscotecaDTO>();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

}
