package es.uparty.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import es.uparty.R;
import es.uparty.comunes.Constants;

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
				overridePendingTransition(R.anim.left_in, R.anim.left_out);
			}
		});
		
		btConfiguracion = (Button) findViewById(R.id.menu_bt_configuracion);
		btConfiguracion.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(),ConfigurationActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);
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
		btOffline = (Button)findViewById(R.id.menu_bt_salir);
		btOffline.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String nombreFichero = Constants.NOMBRE_FICHERO_PREFERENCIAS;
				SharedPreferences sp = getBaseContext().getSharedPreferences(nombreFichero, Context.MODE_PRIVATE);
				
				Editor editor = sp.edit();
				editor.putString(Constants.PREF_USUARIO, "");
				editor.putString(Constants.PREF_PASSWORD, "");
				editor.commit();
				
				System.exit(0);
			}
		});
		
		
	}
}
