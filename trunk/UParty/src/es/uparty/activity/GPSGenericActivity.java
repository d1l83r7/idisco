package es.uparty.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.os.Looper;
import android.widget.Toast;
import es.uparty.R;
import es.uparty.dto.DiscotecaDTO;
import es.uparty.listener.MiLocalizadorListener;

public class GPSGenericActivity extends Activity implements Runnable {
	private ProgressDialog dialogoDuranteBusquedaGPS;
	private LocationManager locationManager;
	private MiLocalizadorListener mLocationListener;
	private int option = 0;
	private DiscotecaDTO dto = null;
	private int modoRuta = 0;
	protected void buscaGPS(int option, DiscotecaDTO dto, int modoRuta) {
		this.dto = dto;
		this.option = option;
		this.modoRuta = modoRuta;
		DialogInterface.OnCancelListener dialogCancel = new DialogInterface.OnCancelListener() {
	        public void onCancel(DialogInterface dialog) {
	        	crearToast(getResources().getString(R.string.gps_signal_not_found));
	        }
	    };
//	    dialogoDuranteBusquedaGPS = ProgressDialog.show(this, this.getResources().getString(R.string.search), 
//				this.getResources().getString(R.string.search_signal_gps), true, true, dialogCancel);
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		//si está activado el GPS
		if (gpsEnabled) {
			Looper.prepare();
			mLocationListener = new MiLocalizadorListener(this,dialogoDuranteBusquedaGPS,this,option,dto,modoRuta);
			locationManager.requestLocationUpdates(
	                LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
			Looper.loop(); 
			Looper.myLooper().quit(); 
		//Si está activada la RED 3G
		} else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			Looper.prepare();
			mLocationListener = new MiLocalizadorListener(this,dialogoDuranteBusquedaGPS,this,option,dto,modoRuta);
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0,mLocationListener);
			Looper.loop();
			Looper.myLooper().quit();
		//Sino error, somos ilocalizables
		} else{
			crearToast(getResources().getString(R.string.gps_signal_not_found));
		}
	}
	protected void crearToast(String msg){
		Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
	}
}
