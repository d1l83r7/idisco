package es.uparty.activity;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import es.uparty.R;
import es.uparty.asynctask.ObtenerDisctecasAsyncTask;
import es.uparty.comunes.Constants;
import es.uparty.dto.DiscotecaDTO;

public class MapaActivity extends FragmentActivity {

	private final static String TAG_MAPA = "TAG_MAPA";
	List<DiscotecaDTO> lDisco = null;
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        
        double dLat = getIntent().getExtras().getDouble(Constants.LATITUDE);
        double dLong = getIntent().getExtras().getDouble(Constants.LONGITUDE);
        Log.d(TAG_MAPA, "latitud: "+String.valueOf(dLat));
        Log.d(TAG_MAPA, "longitud: "+String.valueOf(dLong));
        android.support.v4.app.FragmentManager myFragmentManager = getSupportFragmentManager();
        SupportMapFragment mySupportMapFragment = (SupportMapFragment)myFragmentManager.findFragmentById(R.id.mapa);

        LatLng point = new LatLng(dLat, dLong);
        
        ObtenerDisctecasAsyncTask obtenerDiscotecas = new ObtenerDisctecasAsyncTask();
//        String url = "http://radiant-ravine-3483.herokuapp.com/getDiscotecas";
//        String url  = "http://192.168.1.14:9000/getDiscotecas";
        
        SharedPreferences sp = getSharedPreferences(Constants.NOMBRE_FICHERO_PREFERENCIAS, Context.MODE_PRIVATE);
        String dist = sp.getString(Constants.PREF_DISTANCIA, "10000");
        
        
        
        StringBuilder sb = new StringBuilder();
        sb.append("http://radiant-ravine-3483.herokuapp.com/");
        sb.append("getDiscotecasCercanas?");
        sb.append("distancia="+dist+"&");
        sb.append("latitud="+String.valueOf(dLat)+"&");
        sb.append("longitud="+String.valueOf(dLong));
        
        obtenerDiscotecas.execute(sb.toString());
        
        try{
        	lDisco = obtenerDiscotecas.get();
        }catch(ExecutionException e){
        	Log.e(TAG_MAPA, e.getMessage());
        }catch(InterruptedException ie){
        	Log.e(TAG_MAPA, ie.getMessage());
        }

        GoogleMap myMap = mySupportMapFragment.getMap();
        if(myMap!=null){
        	myMap.setMyLocationEnabled(true);
        	myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
        	myMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        }
        
        if(lDisco!=null){
        	for(DiscotecaDTO dto: lDisco){
        		Double longitud = Double.parseDouble(dto.getLongitud());
        		Double latitud = Double.parseDouble(dto.getLatitud());
        		LatLng latLong = new LatLng(latitud, longitud);
        		MarkerOptions moptions = new MarkerOptions();
        		moptions.position(latLong);
        		moptions.title(dto.getNombre());
        		moptions.snippet(dto.getDescripcio());
        		moptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        		myMap.addMarker(moptions);
        	}
        }
        myMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			
			@Override
			public void onInfoWindowClick(Marker marker) {
				String text = marker.getTitle();
				if(lDisco!=null){
					Intent i = new Intent(getBaseContext(),DetallDiscotecaActivity.class);
					String nombreDiscotecaMinusculas = text.toLowerCase();
					for(DiscotecaDTO dto: lDisco){
						if(dto.getNombre().toLowerCase().equals(nombreDiscotecaMinusculas)){
							i.putExtra(Constants.DISCOTECADTO, dto);
							i.putExtra(Constants.ORIGEN, Constants.ORIGEN_MAPA);
							startActivity(i);
							overridePendingTransition(R.anim.left_in, R.anim.left_out);
						}
					}
				}
				
			}
		});
    }
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(getBaseContext(),MenuActivity.class);
		startActivity(i);
		super.onBackPressed();
	}
}
