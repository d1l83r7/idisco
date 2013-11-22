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
	private Button btConfiguracion = null;
	private Button btManual = null;
	private Button btOffline = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		btBuscaDiscotecaCerca = (Button)findViewById(R.id.menu_bt_buscarCerca);
		btBuscaDiscotecaCerca.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Codigo que se ejecuta al clickar el botón
				buscaGPS(0,null,0,null);
				
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
		
		btConfiguracion = (Button) findViewById(R.id.menu_bt_configuracion);
		btConfiguracion.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(),ConfigurationActivity.class);
				startActivity(i);
			}
		});
		
		btManual = (Button) findViewById(R.id.menu_bt_manual);
		btManual.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				crearToast("En construcción");
			}
		});
		btOffline = (Button)findViewById(R.id.menu_bt_offline);
		btOffline.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				crearToast("En construcción");
			}
		});
		
		
	}
}
