package es.chemisoft.idisco.geolocator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import es.chemisoft.idisco.geolocator.listener.MiLocalizadorListener;

public class MainActivity extends Activity implements Runnable {
	
	private ProgressDialog dialogoDuranteBusquedaGPS;
	private LocationManager locationManager;
	private MiLocalizadorListener mLocationListener;
	private TextView tvLatitud = null;
	TextView tvLongitud = null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button bt = (Button)findViewById(R.id.bt_buscarCoordenadas);
        bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tvLatitud = (TextView)findViewById(R.id.tv_latitud);				
				tvLongitud = (TextView)findViewById(R.id.tv_longitud);
				buscaGPS();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	     
	private void buscaGPS() {
		DialogInterface.OnCancelListener dialogCancel = new DialogInterface.OnCancelListener() {
	        public void onCancel(DialogInterface dialog) {
	        	crearToast(getResources().getString(R.string.gps_signal_not_found));
	        }
	    };
	    dialogoDuranteBusquedaGPS = ProgressDialog.show(this, this.getResources().getString(R.string.search), 
				this.getResources().getString(R.string.search_signal_gps), true, true, dialogCancel);
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
			mLocationListener = new MiLocalizadorListener(this,dialogoDuranteBusquedaGPS,tvLatitud,tvLongitud);
			locationManager.requestLocationUpdates(
	                LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
			Looper.loop(); 
			Looper.myLooper().quit(); 
		//Si está activada la RED 3G
		} else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			mLocationListener = new MiLocalizadorListener(this,dialogoDuranteBusquedaGPS,tvLatitud,tvLongitud);
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0,mLocationListener);
			Looper.loop();
			Looper.myLooper().quit();
		//Sino error, somos ilocalizables
		} else{
			crearToast(getResources().getString(R.string.gps_signal_not_found));
		}
	}
	
	private void crearToast(String msg){
		Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	 }
	    
    
}
