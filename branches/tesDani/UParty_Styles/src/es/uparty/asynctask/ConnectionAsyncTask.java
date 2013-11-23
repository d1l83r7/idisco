package es.uparty.asynctask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.maps.GeoPoint;

public class ConnectionAsyncTask extends AsyncTask<String, Integer, List<GeoPoint>> {

	private static final String HTTPCONNECTION_TAG = "HTTPCONNECTION_TAG";
	
	@Override
	protected List<GeoPoint> doInBackground(String... params) {
		try{
			HttpPost httppost = new HttpPost(params[0]);
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
			JSONArray routeArray = jsonObject.getJSONArray("routes");
			JSONObject routes = routeArray.getJSONObject(0);
			JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
			String encodedString = overviewPolylines.getString("points");
			List<GeoPoint> pointToDraw = decodePoly(encodedString);
			return pointToDraw;
		}catch(Exception e){
			Log.d(HTTPCONNECTION_TAG, "Error: "+e.getMessage());
			return null;
		}
	}
	
	private List<GeoPoint> decodePoly(String encoded) {

	    List<GeoPoint> poly = new ArrayList<GeoPoint>();
	    int index = 0, len = encoded.length();
	    int lat = 0, lng = 0;

	    while (index < len) {
	        int b, shift = 0, result = 0;
	        do {
	            b = encoded.charAt(index++) - 63;
	            result |= (b & 0x1f) << shift;
	            shift += 5;
	        } while (b >= 0x20);
	        int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	        lat += dlat;

	        shift = 0;
	        result = 0;
	        do {
	            b = encoded.charAt(index++) - 63;
	            result |= (b & 0x1f) << shift;
	            shift += 5;
	        } while (b >= 0x20);
	        int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	        lng += dlng;

	        GeoPoint p = new GeoPoint((int) (((double) lat / 1E5) * 1E6), (int) (((double) lng / 1E5) * 1E6));
	        poly.add(p);
	    }

	    return poly;
	}


}
