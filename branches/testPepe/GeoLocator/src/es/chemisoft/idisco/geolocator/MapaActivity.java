package es.chemisoft.idisco.geolocator;

import android.os.Bundle;

import com.google.android.maps.MapActivity;

public class MapaActivity extends MapActivity {

	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
    }	
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
