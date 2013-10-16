package es.uparty.asynctask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import es.uparty.dto.DiscotecaDTO;

public class ObtenerDisctecasAsyncTask extends
		AsyncTask<String, Integer, List<DiscotecaDTO>> {

	private List<DiscotecaDTO> listaDiscotecas = null;
	private final static String OBTENERDICOTECASASYNCTASK_TAG = "OBTENERDICOTECASASYNCTASK_TAG";
	@Override
	protected List<DiscotecaDTO> doInBackground(String... params) {
		try{
			JSONObject jsonObject = abrirConexion(params[0]);
			listaDiscotecas = JSONObjectToListDiscotecasDTO(jsonObject);	
		}catch(JSONException jsone){
			Log.d(OBTENERDICOTECASASYNCTASK_TAG, "Error: "+jsone.getMessage());
		}
		
		
		return listaDiscotecas;
	}
	
	private List<DiscotecaDTO> JSONObjectToListDiscotecasDTO(JSONObject jsonObject)throws JSONException{
		List<DiscotecaDTO> l = new ArrayList<DiscotecaDTO>();
		JSONArray JArray = jsonObject.getJSONArray("elements");
		for(int i=0;i<JArray.length();i++){
			JSONObject json = JArray.getJSONObject(i);
			DiscotecaDTO discotecaDTO = new DiscotecaDTO();
			discotecaDTO.setDescripcio(json.getString("descripcion"));
			discotecaDTO.setNombre(json.getString("nombre"));
			discotecaDTO.setLatitud(json.getString("latitud"));
			discotecaDTO.setLongitud(json.getString("longitud"));
			l.add(discotecaDTO);
		}
		return l;
	}
	
	private JSONObject abrirConexion(String url){
		try{
			HttpPost httppost = new HttpPost(url);
			HttpClient httpclient = new DefaultHttpClient();
	
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream is = null;
			is = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			sb.append(reader.readLine() + "\n");
			String line = "0";
			while ((line = reader.readLine()) != null) {
			    sb.append(line + "\n");
			}
			is.close();
			reader.close();
			String result = sb.toString();
			JSONObject jsonObject = new JSONObject(result);
			return jsonObject;
		}catch(Exception e){
			Log.d(OBTENERDICOTECASASYNCTASK_TAG, "Error: "+e.getMessage());
			return null;
		}
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
