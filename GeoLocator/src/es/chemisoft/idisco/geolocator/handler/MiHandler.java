package es.chemisoft.idisco.geolocator.handler;

import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;

public class MiHandler extends Handler {
	
		private ProgressDialog dialogoDuranteBusquedaGPS = null;
		private LocationManager mLocationManager = null;
		private LocationListener mLocationListener = null;
		private Location currentLocation = null;
		private double currentLatitude;
		private double currentLongitude;
	
		public MiHandler(ProgressDialog dialogoDuranteBusquedaGPS, 
				LocationManager mLocationManager, LocationListener mLocationListener, Location currentLocation){
			this.dialogoDuranteBusquedaGPS = dialogoDuranteBusquedaGPS;
			this.mLocationManager = mLocationManager;
			this.mLocationListener = mLocationListener;
			this.currentLocation = currentLocation;
		}
	
		@Override
		public void handleMessage(Message msg) {
			dialogoDuranteBusquedaGPS.dismiss();
			mLocationManager.removeUpdates(mLocationListener);
	    	if (currentLocation!=null) {
	    		currentLatitude = currentLocation.getLatitude();
	    		currentLongitude = currentLocation.getLongitude();
//	    		viewMap();
	    	}
		}
}
