package es.chemisoft.idisco.geolocator;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import es.chemisoft.idisco.geolocator.handler.MiHandler;
import es.chemisoft.idisco.geolocator.listener.MiLocalizadorListener;

public class MainActivity extends Activity implements Runnable {
	
	private ProgressDialog dialogoDuranteBusquedaGPS;
	private LocationManager locationManager;
	private MiLocalizadorListener mLocationListener;
	private TextView tvLatitud = null;
	private TextView tvLongitud = null;
	private MiHandler handler = null;
	private Double currentLongitude = null;
	private Double currentLatitude = null;
	private final double defaultLongitude = 2.173662;
	private final double defaultLatitude = 41.401536;
	private Button btVerMapa;
	private Button btVerRuta;
	private RadioButton rbCoche;
	private RadioButton rbCaminando;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button bt = (Button)findViewById(R.id.bt_buscarCoordenadas);
        bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tvLatitud = (TextView)findViewById(R.id.tv_latitud);				
				tvLongitud = (TextView)findViewById(R.id.tv_longitud);
				buscaGPS();
			}
		});
        
        btVerMapa = (Button)findViewById(R.id.bt_verMapa);
        btVerMapa.setEnabled(false);
        btVerMapa.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				verMapa();
				
			}
		});
        
        btVerRuta = (Button)findViewById(R.id.bt_verRuta);
        btVerRuta.setEnabled(false);
        btVerRuta.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				verRuta();
				
			}
		});
        rbCoche = (RadioButton)findViewById(R.id.rb_coche);
        rbCoche.setChecked(true);
        rbCoche.setEnabled(false);
        
        rbCaminando = (RadioButton)findViewById(R.id.rb_caminando);
        rbCaminando.setEnabled(false);
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
//	        	handler.sendEmptyMessage(0);
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
		//si est� activado el GPS
		if (gpsEnabled) {
			Looper.prepare();
			mLocationListener = new MiLocalizadorListener(this,dialogoDuranteBusquedaGPS,tvLatitud,tvLongitud,handler,btVerMapa,btVerRuta,currentLongitude,currentLatitude,rbCaminando,rbCoche);
			locationManager.requestLocationUpdates(
	                LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
			Looper.loop(); 
			Looper.myLooper().quit(); 
		//Si est� activada la RED 3G
		} else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			mLocationListener = new MiLocalizadorListener(this,dialogoDuranteBusquedaGPS,tvLatitud,tvLongitud,handler,btVerMapa,btVerRuta,currentLongitude,currentLatitude,rbCoche,rbCaminando);
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0,mLocationListener);
			Looper.loop();
			Looper.myLooper().quit();
		//Sino error, somos ilocalizables
		} else{
			crearToast(getResources().getString(R.string.gps_signal_not_found));
		}
	}
	
	public void verRuta(){
		List<Double> l = (List<Double>)btVerMapa.getTag();
		Double dLong = l.get(0);
		Double dLat = l.get(1);
		 Intent i = new Intent(getBaseContext(),RutaActivity.class);
		 i.putExtra("defaultLatitude", defaultLatitude);
		 i.putExtra("defaultLongitude", defaultLongitude);
		 i.putExtra("currentLongitude", dLat);
		 i.putExtra("currentLatitude", dLong);
		 if(rbCoche.isChecked())
			i.putExtra("driving", "S");
		 else
			i.putExtra("driving", "N");
		 startActivity(i);
	 }
	
	public void verMapa(){
		List<Double> l = (List<Double>)btVerMapa.getTag();
		Double dLong = l.get(0);
		Double dLat = l.get(1);
		
		Intent i = new Intent(getBaseContext(),MapaActivity.class);
		i.putExtra("defaultLatitude", defaultLatitude);
		i.putExtra("defaultLongitude", defaultLongitude);
		i.putExtra("currentLongitude", dLong);
		i.putExtra("currentLatitude", dLat);
		startActivity(i);
	}
	
	private void crearToast(String msg){
		Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	 }
	    
    
}