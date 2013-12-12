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

public class ValidarUsuarioAsyncTask extends
		AsyncTask<String, Integer, String> {

	private final static String VALIDARUSUARIOASYNC_TASK = "OBTENERDICOTECASASYNCTASK_TAG";
	@Override
	protected String doInBackground(String... params) {
		String result = abrirConexion(params[0]);
		result = result.substring(0, 1);
		return result;
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
			return result;
		}catch(Exception e){
			Log.d(VALIDARUSUARIOASYNC_TASK, "Error: "+e.getMessage());
			return null;
		}
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
	
}
