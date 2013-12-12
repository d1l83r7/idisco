package es.uparty.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import es.uparty.R;
import es.uparty.comunes.Constants;
import es.uparty.dto.DiscotecaDTO;

public class DetallDiscotecaActivity extends GPSGenericActivity {
	private TextView tvNombre = null;
	private TextView tvDescripcion = null;
	private Button btAtras;
	private Button btRuta;
	private Button btMuro;
	private DiscotecaDTO dto = null;
	private RadioButton rbCoche = null;
	private RadioButton rbPie = null;
	private RadioButton rbTranPublico = null;
	private String origen = null;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detallediscoteca);
		
		dto = (DiscotecaDTO)getIntent().getSerializableExtra(Constants.DISCOTECADTO);
		origen = getIntent().getExtras().getString(Constants.ORIGEN);
		String nombre = dto.getNombre();
		String descripcion = dto.getDescripcio();
		
		tvNombre = (TextView)findViewById(R.id.detallediscoteca_tv_nombre);
		tvNombre.setText(nombre);
		
		tvDescripcion = (TextView)findViewById(R.id.detallediscoteca_tv_descripcion);
		tvDescripcion.setText(descripcion);
		
		btAtras = (Button)findViewById(R.id.detallediscoteca_bt_atras);
		btAtras.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i = null;
				
				if(origen.equals(Constants.ORIGEN_BUSQUEDA)){
					i = new Intent(getBaseContext(),BuscarDiscotecaActivity.class);
					startActivity(i);
					overridePendingTransition(R.anim.right_in, R.anim.right_out);
				}else if(origen.equals(Constants.ORIGEN_MAPA)){
					buscaGPS(0,null,0,origen);
				}
			}
		});
		
		btRuta = (Button)findViewById(R.id.detallediscoteca_bt_ruta);
		btRuta.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int modeRuta = 0;
				if(rbCoche.isChecked())
					modeRuta = 0;
				else if(rbPie.isChecked())
					modeRuta = 1;
				else if(rbTranPublico.isChecked())
					modeRuta = 2;
				buscaGPS(1, dto,modeRuta,origen);
			}
		});
		
		rbPie = (RadioButton)findViewById(R.id.detallediscoteca_rb_caminando);
		rbPie.setChecked(true);
		rbCoche = (RadioButton)findViewById(R.id.detallediscoteca_rb_coche);
		rbCoche.setChecked(false);
		rbTranPublico = (RadioButton)findViewById(R.id.detallediscoteca_rb_transp_publico);
		rbTranPublico.setChecked(false);
		
		btMuro = (Button)findViewById(R.id.detallediscoteca_bt_muro);
		btMuro.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getBaseContext(),MuroActivity.class);
				i.putExtra(Constants.DISCOTECADTO, dto);
				i.putExtra(Constants.ORIGEN, origen);
				startActivity(i);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);
			}
		});
		
		String nombreFichero = Constants.NOMBRE_FICHERO_PREFERENCIAS;
		SharedPreferences sp = getBaseContext().getSharedPreferences(nombreFichero, Context.MODE_PRIVATE);
		String valorUsuario = sp.getString(Constants.PREF_USUARIO, "");
		String valorPassword = sp.getString(Constants.PREF_PASSWORD, "");
		
		if(valorUsuario.equals("")||valorPassword.equals("")){
			btMuro.setEnabled(false);
			btMuro.setBackgroundColor(Color.GRAY);
		}
	}
	
	@Override
	public void onBackPressed() {
		Intent i = null;
		if(origen.equals(Constants.ORIGEN_BUSQUEDA)){
			i = new Intent(getBaseContext(),BuscarDiscotecaActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.right_out);
		}else if(origen.equals(Constants.ORIGEN_MAPA)){
			buscaGPS(0,null,0,null);
		}
		super.onBackPressed();
	}
}
