package es.chemisoft.idisco.geolocator;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import es.chemisoft.idisco.geolocator.connection.HttpConnection;
import es.chemisoft.idisco.geolocator.overlay.CustomItemizedOverlay;
import es.chemisoft.idisco.geolocator.overlay.RouteOverlay;

public class RutaActivity extends MapActivity {
	private MapView mapView;
	
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
			Log.d(MAPAACTIVITY_TAG, "latitud por defecto: "+String.valueOf(defaultLatitude));
			Log.d(MAPAACTIVITY_TAG, "longitud por defecto: "+String.valueOf(defaultLongitude));
			Log.d(MAPAACTIVITY_TAG, "latitud actual: "+String.valueOf(currentLatitude));
			Log.d(MAPAACTIVITY_TAG, "longitud actual: "+String.valueOf(currentLongitude));
			operateMap(currentLongitude, currentLatitude);

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
					defaultLongitude);
			}catch(Exception e){
				Log.d(MAPAACTIVITY_TAG, e.getMessage());
			}
		}

	}

	private void traceRoute(Double sourceLat, Double sourceLong,
			Double destinationLat, Double destinationLong)throws Exception {
		List<GeoPoint> l = getDicrectionData(String.valueOf(sourceLat),
				String.valueOf(sourceLong), String.valueOf(destinationLat),
				String.valueOf(destinationLong));
//		String[] lngLat = pairs[0].split(",");
//
//		// STARTING POINT
//		GeoPoint startGP = new GeoPoint(
//				(int) (Double.parseDouble(lngLat[1]) * 1E6),
//				(int) (Double.parseDouble(lngLat[0]) * 1E6));

		MapController myMC = mapView.getController();

		myMC.setCenter(l.get(0));
		myMC.setZoom(10);
//		mapView.getOverlays().add(new RouteOverlay(l.get(0), l.get(0)));

		// NAVIGATE THE PATH


		for(GeoPoint gp: l){

			mapView.getOverlays().add(new RouteOverlay(gp, gp));

			

		}
		
		mapView.getController().animateTo(l.get(0));
		mapView.setBuiltInZoomControls(true);
		mapView.displayZoomControls(true);
	}

	private void operateMap(double longitude, double latitude) {
		mapView = (MapView) findViewById(R.id.map_view);
		mapView.setBuiltInZoomControls(true);
		MapController mc = mapView.getController();
		GeoPoint p = new GeoPoint((int) (latitude * 1E6),
				(int) (longitude * 1E6));

		mc.animateTo(p);
		mc.setZoom(12);
		mapView.invalidate();
	}

	private void putPointsOnMap(List<Location> l) {
		if (l.size() > 0) {
			List<Overlay> mapOverlays = mapView.getOverlays();
			Drawable currentPoint = this.getResources().getDrawable(
					R.drawable.currentpoint);
			CustomItemizedOverlay itemizedOverlayCurrent = new CustomItemizedOverlay(
					currentPoint, this);
			Drawable destinationPoint = this.getResources().getDrawable(
					R.drawable.destinationpoint);
			CustomItemizedOverlay itemizedOverlayDestination = new CustomItemizedOverlay(
					destinationPoint, this);

			Location currentPosition = l.get(0);
			double latitude = currentPosition.getLatitude();
			double longitude = currentPosition.getLongitude();
			GeoPoint point = new GeoPoint((int) (latitude * 1E6),
					(int) (longitude * 1E6));
			OverlayItem overlayitem = new OverlayItem(point,
					"Current position", "Current position");
			Log.w(this.getClass().getName(), "Current position" + " longitude "
					+ currentPosition.getLongitude() + " latitude "
					+ currentPosition.getLatitude());
			itemizedOverlayCurrent.addOverlay(overlayitem);

			Location destination = l.get(1);
			latitude = destination.getLatitude();
			longitude = destination.getLongitude();
			GeoPoint point2 = new GeoPoint((int) (latitude * 1E6),
					(int) (longitude * 1E6));
			OverlayItem overlayitem2 = new OverlayItem(point2, "Destination",
					"Destination");
			Log.w(this.getClass().getName(),
					"Destination" + " longitude " + destination.getLongitude()
							+ " latitude " + destination.getLatitude());
			itemizedOverlayDestination.addOverlay(overlayitem2);

			// RouteOverlay routeOverlay = new RouteOverlay(point,point2);

			mapOverlays.add(itemizedOverlayCurrent);
			mapOverlays.add(itemizedOverlayDestination);
			// mapOverlays.add(routeOverlay);
		}
	}
	
	private List<GeoPoint> getDicrectionData(String sourceLat, String sourceLong,
			String destinationLat, String destinationLong)throws Exception {
		String urlString = "http://maps.googleapis.com/maps/api/directions/json?origin="+sourceLat+","+sourceLong+"&destination="+destinationLat+","+destinationLong+"&sensor=false";
		HttpConnection con = new HttpConnection();
		con.execute(urlString);
		List<GeoPoint> pointToDraw = con.get();
		return pointToDraw;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return true;
	}
}