package es.uparty.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.maps.GeoPoint;

import es.uparty.R;
import es.uparty.asynctask.ConnectionAsyncTask;
import es.uparty.comunes.Constants;

public class RutaActivity extends FragmentActivity {
	private GoogleMap mapView;
	
	private static final String RUTAACTIVITY_TAG = "RUTAACTIVITY_TAG";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.route);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			double defaultLatitude = extras.getDouble(Constants.LATITUD_DESTINO);
			double defaultLongitude = extras.getDouble(Constants.LONGITUD_DESTINO);
			double currentLatitude = extras.getDouble(Constants.LATITUD_ORIGEN);
			double currentLongitude = extras.getDouble(Constants.LONGITUD_ORIGEN);
			String mode = extras.getString("mode");
			Log.d(RUTAACTIVITY_TAG, "latitud por defecto: "+String.valueOf(defaultLatitude));
			Log.d(RUTAACTIVITY_TAG, "longitud por defecto: "+String.valueOf(defaultLongitude));
			Log.d(RUTAACTIVITY_TAG, "latitud actual: "+String.valueOf(currentLatitude));
			Log.d(RUTAACTIVITY_TAG, "longitud actual: "+String.valueOf(currentLongitude));
			
			android.support.v4.app.FragmentManager myFragmentManager = getSupportFragmentManager();
	        SupportMapFragment mySupportMapFragment = (SupportMapFragment)myFragmentManager.findFragmentById(R.id.mapa_ruta);
			
	        mapView = mySupportMapFragment.getMap();
	        
			Location defaultLocation = new Location(
					LocationManager.GPS_PROVIDER);
			defaultLocation.setLatitude(defaultLatitude);
			defaultLocation.setLongitude(defaultLongitude);

			Location currentLocation = new Location(
					LocationManager.GPS_PROVIDER);
			currentLocation.setLatitude(currentLatitude);
			currentLocation.setLongitude(currentLongitude);

			List<Location> l = new ArrayList<Location>();
			l.add(defaultLocation);
			l.add(currentLocation);

			try{
			traceRoute(currentLatitude, currentLongitude, defaultLatitude,
					defaultLongitude,mode);
			}catch(Exception e){
				Log.d(RUTAACTIVITY_TAG, e.getMessage());
			}
		}

	}

	private void traceRoute(Double sourceLat, Double sourceLong,
			Double destinationLat, Double destinationLong,String mode)throws Exception {
		List<GeoPoint> l = getDirectionData(String.valueOf(sourceLat),
				String.valueOf(sourceLong), String.valueOf(destinationLat),
				String.valueOf(destinationLong),mode);
		
		PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.RED);
		
		GeoPoint geoPoint = l.get(0);
		LatLng init = new LatLng(geoPoint.getLatitudeE6()/1E6, geoPoint.getLongitudeE6()/1E6);
		
		mapView.setMyLocationEnabled(true);
		mapView.moveCamera(CameraUpdateFactory.newLatLngZoom(init, 15));
    	mapView.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
		
		// NAVIGATE THE PATH
		for(GeoPoint gp: l){
			double latitude = gp.getLatitudeE6()/1E6;
			double longitude = gp.getLongitudeE6()/1E6;
			LatLng p = new LatLng(latitude,longitude);
			rectLine.add(p);
		}
		mapView.addPolyline(rectLine);
	}

	private List<GeoPoint> getDirectionData(String sourceLat, String sourceLong,
			String destinationLat, String destinationLong, String mode)throws Exception {
		StringBuilder urlString = new StringBuilder();
	    urlString.append("http://maps.googleapis.com/maps/api/directions/json?");
	    urlString.append("origin=");// from
	    urlString.append(sourceLat+","+sourceLong);
	    urlString.append("&destination=");// to
	    urlString.append(destinationLat+","+destinationLong);
	    if(mode.equals("driving"))
	    	urlString.append("&mode=driving");
	    else if(mode.equals("walking"))
	    	urlString.append("&mode=walking");
	    else if(mode.equals("transit")){
	    	urlString.append("&mode=transit");
	    	Date d = new Date();
	    	String departure_time = String.valueOf(d.getTime());
	    	urlString.append("&departure_time=");
	    	urlString.append(departure_time.substring(0,10));
	    }
	    urlString.append("&sensor=true");
	    urlString.append("&units=imperial");
	    ConnectionAsyncTask con = new ConnectionAsyncTask();
		con.execute(urlString.toString());
		List<GeoPoint> pointToDraw = con.get();
		return pointToDraw;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}