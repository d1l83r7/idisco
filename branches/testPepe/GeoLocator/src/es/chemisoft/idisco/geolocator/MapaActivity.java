package es.chemisoft.idisco.geolocator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapaActivity extends FragmentActivity {

	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        
        double dLong = getIntent().getExtras().getDouble("currentLongitude");
        double dLat = getIntent().getExtras().getDouble("currentLatitude");
        
        android.support.v4.app.FragmentManager myFragmentManager = getSupportFragmentManager();
        SupportMapFragment mySupportMapFragment = (SupportMapFragment)myFragmentManager.findFragmentById(R.id.mapa);

        LatLng point = new LatLng(dLong, dLat);

        GoogleMap myMap = mySupportMapFragment.getMap();
        if(myMap!=null){
        	myMap.setMyLocationEnabled(true);
        	myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
        	myMap.animateCamera(CameraUpdateFactory.zoomTo(20), 2000, null);
        }
    }	
}
