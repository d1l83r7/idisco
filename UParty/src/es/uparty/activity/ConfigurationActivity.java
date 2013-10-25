package es.uparty.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import es.uparty.R;
import es.uparty.comunes.Constants;

public class ConfigurationActivity extends Activity {
	private Button btGuardar = null;
	private Button btVolver = null;
	private SeekBar distancia = null;
	private SharedPreferences sp = null;
	private int valor = 0;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuracion);
		
		String nombreFichero = Constants.NOMBRE_FICHERO_PREFERENCIAS;
		sp = getBaseContext().getSharedPreferences(nombreFichero, 0);
		String valorDistancia = sp.getString(Constants.PREF_DISTANCIA, "0");
		
		
		btGuardar = (Button)findViewById(R.id.configuracion_bt_guardar);
		btGuardar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sp.edit().putString(Constants.PREF_DISTANCIA, String.valueOf(valor));
				sp.edit().commit();
				Intent i = new Intent(getBaseContext(), MenuActivity.class);
				startActivity(i);
			}
		});
		
		btVolver = (Button)findViewById(R.id.configuracion_bt_volver);
		btVolver.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), MenuActivity.class);
				startActivity(i);
			}
		});
		
		distancia = (SeekBar)findViewById(R.id.configuracion_sb_distancia);
		distancia.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				valor = seekBar.getProgress();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}
		});
		distancia.setProgress(Integer.parseInt(valorDistancia));
	}
}
