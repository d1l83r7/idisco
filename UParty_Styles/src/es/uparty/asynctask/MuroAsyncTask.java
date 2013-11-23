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
import es.uparty.dto.MensajeDTO;

public class MuroAsyncTask extends AsyncTask<String, Integer, List<MensajeDTO>> {

	private static final String ObtenerMensajesMuroAsyncTask_TAG = "ObtenerMensajesMuroAsyncTask_TAG";
	
	@Override
	protected List<MensajeDTO> doInBackground(String... params) {
		try{
			JSONObject json = abrirConexion(params[0]);
			List<MensajeDTO> l = JSONObjectToListMensajesDTO(json);
			return l;
		}catch(Exception e){
			Log.d(ObtenerMensajesMuroAsyncTask_TAG, "Error: "+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	private List<MensajeDTO> JSONObjectToListMensajesDTO(JSONObject jsonObject)throws JSONException{
		List<MensajeDTO> l = new ArrayList<MensajeDTO>();
		JSONArray JArray = jsonObject.getJSONArray("elements");
		for(int i=0;i<JArray.length();i++){
			JSONObject json = JArray.getJSONObject(i);
			MensajeDTO dto = new MensajeDTO();
			dto.setIdMensaje(json.getString("idMensaje"));
			dto.setTexto(json.getString("mensaje"));
			dto.setUsuario(json.getString("usuario"));
			l.add(dto);
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
			Log.d(ObtenerMensajesMuroAsyncTask_TAG, "Error: "+e.getMessage());
			return null;
		}
	}
}
