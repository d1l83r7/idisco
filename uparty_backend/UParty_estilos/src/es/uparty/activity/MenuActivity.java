package es.uparty.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import es.uparty.R;

public class MenuActivity extends GPSGenericActivity {
	private Button btBuscaDiscotecaCerca = null;
	private Button btBuscaDiscoteca = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		btBuscaDiscotecaCerca = (Button)findViewById(R.id.menu_bt_buscarCerca);
		btBuscaDiscotecaCerca.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Codigo que se ejecuta al clickar el botón
				buscaGPS(0,null,false);
				
			}
		});
		btBuscaDiscoteca = (Button)findViewById(R.id.menu_bt_buscarDisco);
		btBuscaDiscoteca.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Código que se ejecuta al clickar el botón
				Intent intent = new Intent(v.getContext(),BuscarDiscotecaActivity.class);
				startActivity(intent);
			}
		});
		
	}
}
