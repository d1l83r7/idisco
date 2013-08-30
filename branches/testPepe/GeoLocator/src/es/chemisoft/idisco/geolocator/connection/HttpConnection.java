package es.chemisoft.idisco.geolocator.connection;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import android.os.AsyncTask;
import android.util.Log;

public class HttpConnection extends AsyncTask<String, Integer, Document> {

	private static final String HTTPCONNECTION_TAG = "HTTPCONNECTION_TAG";
	
	@Override
	protected Document doInBackground(String... params) {
		try{
			
//		System.setProperty("http.proxyHost", "10.49.1.1");
//		System.setProperty("http.proxyPort", "8080");
		Log.d(HTTPCONNECTION_TAG, "url_connexion_google: "+params[0]);
		HttpURLConnection urlConnection = null;
		URL url = new URL(params[0]);
		urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod("GET");
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.connect();
		InputStream is = urlConnection.getInputStream();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(is);
		
		return doc;
		}catch(Exception e){
			Log.d(HTTPCONNECTION_TAG, "Error: "+e.getMessage());
			return null;
		}
	}


}
