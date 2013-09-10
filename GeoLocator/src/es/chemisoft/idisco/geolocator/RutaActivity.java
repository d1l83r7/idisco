package es.chemisoft.idisco.geolocator;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import es.chemisoft.idisco.geolocator.connection.HttpConnection;
import es.chemisoft.idisco.geolocator.overlay.CustomItemizedOverlay;
import es.chemisoft.idisco.geolocator.overlay.RouteOverlay;

public class RutaActivity extends FragmentActivity {
	private GoogleMap mapView;
	
	private static final String MAPAACTIVITY_TAG = "MAPAACTIVITY_TAG";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.route);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			double defaultLatitude = extras.getDouble("defaultLatitude");
			double defaultLongitude = extras.getDouble("defaultLongitude");
			double currentLongitude = extras.getDouble("currentLongitude");
			double currentLatitude = extras.getDouble("currentLatitude");
			String driving = extras.getString("driving");
			Log.d(MAPAACTIVITY_TAG, "latitud por defecto: "+String.valueOf(defaultLatitude));
			Log.d(MAPAACTIVITY_TAG, "longitud por defecto: "+String.valueOf(defaultLongitude));
			Log.d(MAPAACTIVITY_TAG, "latitud actual: "+String.valueOf(currentLatitude));
			Log.d(MAPAACTIVITY_TAG, "longitud actual: "+String.valueOf(currentLongitude));
			
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

//			putPointsOnMap(l);
			try{
			traceRoute(currentLatitude, currentLongitude, defaultLatitude,
					defaultLongitude,driving);
			}catch(Exception e){
				Log.d(MAPAACTIVITY_TAG, e.getMessage());
			}
		}

	}

	private void traceRoute(Double sourceLat, Double sourceLong,
			Double destinationLat, Double destinationLong,String driving)throws Exception {
		List<GeoPoint> l = getDirectionData(String.valueOf(sourceLat),
				String.valueOf(sourceLong), String.valueOf(destinationLat),
				String.valueOf(destinationLong),driving);
		
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
			String destinationLat, String destinationLong, String driving)throws Exception {
//		String urlString = "http://maps.googleapis.com/maps/api/directions/json?origin="+sourceLat+","+sourceLong+"&destination="+destinationLat+","+destinationLong+"&sensor=false";
		StringBuilder urlString = new StringBuilder();
	    urlString.append("http://maps.googleapis.com/maps/api/directions/json?");
	    urlString.append("origin=");// from
	    urlString.append(sourceLat+","+sourceLong);
	    urlString.append("&destination=");// to
	    urlString.append(destinationLat+","+destinationLong);
	    if(driving.equals("S"))
	    	urlString.append("&mode=driving");
	    else
	    	urlString.append("&mode=walking");	    	
	    urlString.append("&sensor=true");
	    urlString.append("&units=imperial");
		HttpConnection con = new HttpConnection();
		con.execute(urlString.toString());
		List<GeoPoint> pointToDraw = con.get();
		return pointToDraw;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}