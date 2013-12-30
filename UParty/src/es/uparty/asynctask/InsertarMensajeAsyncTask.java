package es.uparty.asynctask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import es.uparty.comunes.Utils;
import es.uparty.dto.DiscotecaDTO;
import es.uparty.dto.MensajeDTO;

public class InsertarMensajeAsyncTask extends AsyncTask<String, Integer, String> {

	private static final String ObtenerMensajesMuroAsyncTask_TAG = "ObtenerMensajesMuroAsyncTask_TAG";
	
	@Override
	protected String doInBackground(String... params) {
		try{
			StringBuilder sb = new StringBuilder();
			sb.append("http://radiant-ravine-3483.herokuapp.com/insertarMensaje?");
			sb.append("usuario="+Utils.encryptURL(params[0]));
			sb.append("&contrasenya="+Utils.encryptURL(params[1]));
			sb.append("&texto="+params[2]);
			sb.append("&idDiscoteca="+params[3]);
			sb.append("&idMuro="+params[4]);
			String res = abrirConexion(sb.toString());
			return res;
		}catch(Exception e){
			Log.d(ObtenerMensajesMuroAsyncTask_TAG, "Error: "+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	private String abrirConexion(String url){
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
			return "S";
		}catch(Exception e){
			Log.d(ObtenerMensajesMuroAsyncTask_TAG, "Error: "+e.getMessage());
			return null;
		}
	}
}
