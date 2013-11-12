package es.uparty.activity;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import es.uparty.R;
import es.uparty.adapter.MuroAdapter;
import es.uparty.asynctask.InsertarMensajeAsyncTask;
import es.uparty.asynctask.MuroAsyncTask;
import es.uparty.comunes.Constants;
import es.uparty.dto.DiscotecaDTO;
import es.uparty.dto.MensajeDTO;

public class MuroActivity extends ListActivity {
	
	private DiscotecaDTO dto = null;
	private Button btVolver = null;
	private String origen = null;
	private final static String MuroActivity_TAG = "MuroActivity_TAG";
	private MuroAdapter muroAdapter = null;
	private ListView lv = null;
	private Button btEnviar = null;
	private String usuario = null;
	private String password = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.muro);
		String nombreFichero = Constants.NOMBRE_FICHERO_PREFERENCIAS;
		SharedPreferences sp = getBaseContext().getSharedPreferences(nombreFichero, Context.MODE_PRIVATE);
		usuario = sp.getString(Constants.PREF_USUARIO, "");
		password = sp.getString(Constants.PREF_PASSWORD, "");
		dto = (DiscotecaDTO)getIntent().getSerializableExtra(Constants.DISCOTECADTO);
		origen = getIntent().getStringExtra(Constants.ORIGEN);
		btVolver = (Button)findViewById(R.id.murodiscoteca_bt_atras);
		btVolver.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(),DetallDiscotecaActivity.class);
				i.putExtra(Constants.DISCOTECADTO, dto);
				i.putExtra(Constants.ORIGEN, origen);
				startActivity(i);
			}
		});
		
		TextView tv = (TextView)findViewById(R.id.murodiscoteca_tv_nombre);
		tv.setText("Muro de "+dto.getNombre());
		
		MuroAsyncTask m = new MuroAsyncTask();
		List<MensajeDTO> l = null;
		try{
			StringBuilder sb = new StringBuilder();
			sb.append("http://radiant-ravine-3483.herokuapp.com/getMensajes");
			sb.append("?idMuro="+dto.getId());
			sb.append("&idDiscoteca="+dto.getId());
			m.execute(sb.toString());
			l = m.get();
		}catch(Exception e){
			e.printStackTrace();
			Log.e(MuroActivity_TAG, e.getMessage());
		}
		lv = getListView();
		muroAdapter = new MuroAdapter(this);
		muroAdapter.setlDTO(l);
		lv.setAdapter(muroAdapter);
		btEnviar = (Button)findViewById(R.id.murodiscoteca_bt_enviar);
		btEnviar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText et_mensaje = (EditText)findViewById(R.id.murodiscoteca_et_mensaje);
				String text = et_mensaje.getText().toString();
				String idDiscoteca = String.valueOf(dto.getId());
				
				InsertarMensajeAsyncTask i = new InsertarMensajeAsyncTask();
				String[] params = new String[5];
				
				params[0] = usuario;
				params[1] = password;
				params[2] = text.replace(" ", "%20");
				params[3] = idDiscoteca;
				params[4] = idDiscoteca;
				
				i.execute(params);
				try{
				String res = i.get();
				}catch(Exception e){
					e.printStackTrace();
				}
				MuroAsyncTask m = new MuroAsyncTask();
				List<MensajeDTO> l = null;
				try{
					StringBuilder sb = new StringBuilder();
					sb.append("http://radiant-ravine-3483.herokuapp.com/getMensajes");
					sb.append("?idMuro="+dto.getId());
					sb.append("&idDiscoteca="+dto.getId());
					m.execute(sb.toString());
					l = m.get();
				}catch(Exception e){
					e.printStackTrace();
					Log.e(MuroActivity_TAG, e.getMessage());
				}
				muroAdapter.setlDTO(l);
				lv.setAdapter(muroAdapter);
				et_mensaje.setText("");
			}
		});
	}
}
