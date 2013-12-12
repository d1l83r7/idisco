package es.uparty.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import es.uparty.R;
import es.uparty.comunes.Constants;

public class ConfigurationActivity extends Activity {
	private Button btGuardar = null;
	private Button btVolver = null;
	private SeekBar distancia = null;
	private EditText et_usuario = null;
	private EditText et_password = null;
	private int valor = 0;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuracion);
		
		String nombreFichero = Constants.NOMBRE_FICHERO_PREFERENCIAS;
		SharedPreferences sp = getBaseContext().getSharedPreferences(nombreFichero, Context.MODE_PRIVATE);
		String valorDistancia = sp.getString(Constants.PREF_DISTANCIA, "10000");
		
		TextView tv = (TextView)findViewById(R.id.configuracion_tv_distancia);
		String text = tv.getText().toString();
		String aux = text.substring(0, 18);
		aux += String.valueOf(valorDistancia)+" m";
		tv.setText(aux);
		btGuardar = (Button)findViewById(R.id.configuracion_bt_guardar);
		btGuardar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String nombreFichero = Constants.NOMBRE_FICHERO_PREFERENCIAS;
				SharedPreferences sp = getBaseContext().getSharedPreferences(nombreFichero, Context.MODE_PRIVATE);
				
				Editor editor = sp.edit();
				valor = distancia.getProgress();
				editor.putString(Constants.PREF_DISTANCIA, String.valueOf(valor));
				editor.putString(Constants.PREF_USUARIO, et_usuario.getText().toString());
				editor.putString(Constants.PREF_PASSWORD, et_password.getText().toString());
				editor.commit();
				Intent i = new Intent(getBaseContext(), MenuActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);
			}
		});
		btVolver = (Button)findViewById(R.id.configuracion_bt_volver);
		btVolver.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), MenuActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.right_in, R.anim.right_out);
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
				TextView tv = (TextView)findViewById(R.id.configuracion_tv_distancia);
				String text = tv.getText().toString();
				String aux = text.substring(0, 18);
				aux += String.valueOf(progress)+" m";
				tv.setText(aux);
			}
		});
		distancia.setProgress(Integer.parseInt(valorDistancia));
		
		String usuario = sp.getString(Constants.PREF_USUARIO, "");
		String password = sp.getString(Constants.PREF_PASSWORD, "");
		
		et_usuario = (EditText)findViewById(R.id.configuracion_et_usuario);
		et_usuario.setText(usuario);
		
		et_password = (EditText)findViewById(R.id.configuracion_et_password);
		et_password.setText(password);
		
	}
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(getBaseContext(),MenuActivity.class);
		startActivity(i);
		super.onBackPressed();
	}
}
