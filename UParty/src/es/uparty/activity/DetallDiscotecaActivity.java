package es.uparty.activity;

import android.content.Intent;
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
	private DiscotecaDTO dto = null;
	private RadioButton rbCoche = null;
	private RadioButton rbPie = null;
	private RadioButton rbTranPublico = null;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detallediscoteca);
		
		dto = (DiscotecaDTO)getIntent().getSerializableExtra(Constants.DISCOTECADTO);
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
				String origen = getIntent().getExtras().getString(Constants.ORIGEN);
				if(origen.equals(Constants.ORIGEN_BUSQUEDA)){
					i = new Intent(getBaseContext(),BuscarDiscotecaActivity.class);
					startActivity(i);	
				}else if(origen.equals(Constants.ORIGEN_MAPA)){
					buscaGPS(0,null,0);
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
				buscaGPS(1, dto,modeRuta);
			}
		});
		
		rbPie = (RadioButton)findViewById(R.id.detallediscoteca_rb_caminando);
		rbPie.setChecked(true);
		rbCoche = (RadioButton)findViewById(R.id.detallediscoteca_rb_coche);
		rbCoche.setChecked(false);
		rbTranPublico = (RadioButton)findViewById(R.id.detallediscoteca_rb_transp_publico);
		rbTranPublico.setChecked(false);
	}
}