package es.chemisoft.idisco.geolocator.listener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import es.chemisoft.idisco.geolocator.R;

public class MiLocalizadorListener implements LocationListener{
		
	private Context context;
	private Resources resources;
	private TextView tvLatitud;
	private TextView tvLongitud;
	private Activity activity;
	private String latitud;
	private String longitud;
	private ProgressDialog dialogoDuranteBusquedaGPS;
	
	public MiLocalizadorListener(Activity activity,ProgressDialog dialogoDuranteBusquedaGPS, TextView tvLongitud, TextView tvLatitud){
		this.context = activity.getBaseContext();
		this.resources = activity.getResources();
		this.activity = activity;
		this.dialogoDuranteBusquedaGPS = dialogoDuranteBusquedaGPS;
		this.tvLatitud = tvLatitud;
		this.tvLongitud = tvLongitud;
	}
	
    @Override
    public void onLocationChanged(Location loc) {
        if (loc != null) {
            Toast.makeText(context, 
                resources.getString(R.string.gps_signal_found), 
                Toast.LENGTH_LONG).show();
        	latitud = String.valueOf(loc.getLatitude());
    		longitud = String.valueOf(loc.getLongitude());
    		
           
            activity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					dialogoDuranteBusquedaGPS.hide();
					tvLatitud.setText("Latitud: "+latitud);
			        tvLongitud.setText("Longitud: "+longitud);
				}
			});
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, 
        Bundle extras) {
    }
}