package es.chemisoft.idisco.geolocator.listener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import es.chemisoft.idisco.geolocator.R;
import es.chemisoft.idisco.geolocator.handler.MiHandler;

public class MiLocalizadorListener implements LocationListener{
		
	private Context context;
	private Resources resources;
	private TextView tvLatitud;
	private TextView tvLongitud;
	private Activity activity;
	private ProgressDialog dialogoDuranteBusquedaGPS;
	private Button btVerMapa;
	private Double currentLongitude;
	private Double currentLatitude;
	
	public MiLocalizadorListener(Activity activity,
			ProgressDialog dialogoDuranteBusquedaGPS, 
			TextView tvLongitud, 
			TextView tvLatitud,
			MiHandler handler,
			Button btVerMapa,
			Double currentLongitude,
			Double currentLatitude){
		this.context = activity.getBaseContext();
		this.resources = activity.getResources();
		this.activity = activity;
		this.dialogoDuranteBusquedaGPS = dialogoDuranteBusquedaGPS;
		this.tvLatitud = tvLatitud;
		this.tvLongitud = tvLongitud;
		this.btVerMapa = btVerMapa;
		this.currentLatitude =currentLatitude;
		this.currentLongitude = currentLongitude;
	}
	
    @SuppressLint("UseValueOf") 
    @Override
    public void onLocationChanged(Location loc) {
        if (loc != null) {
            Toast.makeText(context, 
                resources.getString(R.string.gps_signal_found), 
                Toast.LENGTH_LONG).show();
            currentLatitude = new Double(loc.getLatitude());
            currentLongitude = new Double(loc.getLongitude());
            activity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					dialogoDuranteBusquedaGPS.hide();
					tvLatitud.setText("Latitud: "+String.valueOf(currentLatitude.doubleValue()));
			        tvLongitud.setText("Longitud: "+String.valueOf(currentLongitude.doubleValue()));
			        btVerMapa.setEnabled(true);
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