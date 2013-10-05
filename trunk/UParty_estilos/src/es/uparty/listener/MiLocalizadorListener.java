package es.uparty.listener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import es.uparty.R;
import es.uparty.activity.MapaActivity;
import es.uparty.activity.RutaActivity;
import es.uparty.comunes.Constants;
import es.uparty.dto.DiscotecaDTO;

public class MiLocalizadorListener implements LocationListener{
		
	private Context context;
	private Resources resources;
	private Activity activity;
	private ProgressDialog dialogoDuranteBusquedaGPS;
	private Double currentLongitude =null;
	private Double currentLatitude = null;
	private int option = 0;
	private DiscotecaDTO dto=null;
	private static final String TAG_LOGATION_LISTENER = "TAG_LOGATION_LISTENER";
	private boolean coche = false;
	public MiLocalizadorListener(Activity activity,
			ProgressDialog dialogoDuranteBusquedaGPS,
			Context context,int option,
			DiscotecaDTO dto,
			boolean coche){
		this.context = activity.getBaseContext();
		this.resources = activity.getResources();
		this.activity = activity;
		this.dialogoDuranteBusquedaGPS = dialogoDuranteBusquedaGPS;
		this.context = context;
		this.option = option;
		this.dto = dto;
		this.coche = coche;
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
            Log.d(TAG_LOGATION_LISTENER, "latitud: "+currentLatitude.toString());
            Log.d(TAG_LOGATION_LISTENER, "longitud: "+currentLongitude.toString());
            Looper.myLooper().quit();
            activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					dialogoDuranteBusquedaGPS.hide();
					if(option==0){
				        Intent i = new Intent(context,MapaActivity.class);
				        i.putExtra(Constants.LATITUDE, currentLatitude);
				        i.putExtra(Constants.LONGITUDE, currentLongitude);
				        context.startActivity(i);
					}else if(option==1){
						 Intent i = new Intent(context,RutaActivity.class);
						 i.putExtra(Constants.LATITUD_DESTINO, Double.parseDouble(dto.getLatitud()));
						 i.putExtra(Constants.LONGITUD_DESTINO, Double.parseDouble(dto.getLongitud()));
						 i.putExtra(Constants.LATITUD_ORIGEN, currentLatitude);
						 i.putExtra(Constants.LONGITUD_ORIGEN, currentLongitude);
						 if(coche)
							i.putExtra("driving", "S");
						 else
							i.putExtra("driving", "N");
						 context.startActivity(i);
					}
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