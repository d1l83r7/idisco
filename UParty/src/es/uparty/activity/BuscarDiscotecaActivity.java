package es.uparty.activity;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import es.uparty.R;
import es.uparty.asynctask.ObtenerDisctecasAsyncTask;
import es.uparty.comunes.Constants;
import es.uparty.dto.DiscotecaDTO;

public class BuscarDiscotecaActivity extends ListActivity{

	private Button botonBuscar = null;
	private Button botonAtras = null;
	private List<DiscotecaDTO> listaDiscotecas = null;
	private final static String TAG_BUSCAR_DISCOTECA = "TAG_BUSCAR_DISCOTECA";
	private ListView lv = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buscardiscoteca);
		ObtenerDisctecasAsyncTask obtenerDisctecasAsyncTask = new ObtenerDisctecasAsyncTask();
		String url = "http://192.168.1.8:9000/getDiscotecas";
		obtenerDisctecasAsyncTask.execute(url);
		try{
			listaDiscotecas = obtenerDisctecasAsyncTask.get();
		}catch(InterruptedException ie){
			Log.e(TAG_BUSCAR_DISCOTECA, ie.getMessage());
		}catch(ExecutionException e){
			Log.e(TAG_BUSCAR_DISCOTECA, e.getMessage());
		}
		
		botonBuscar = (Button)findViewById(R.id.buscardiscoteca_bt_buscar);
		botonBuscar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((ArrayAdapter)lv.getAdapter()).clear();
				EditText editText = (EditText)findViewById(R.id.buscardiscoteca_et_nombreDiscoteca);
				String nombreDiscoteca = editText.getText().toString();
				Log.d(TAG_BUSCAR_DISCOTECA, "Nombre discoteca: "+nombreDiscoteca);
				if(listaDiscotecas!=null){
					for(DiscotecaDTO dto: listaDiscotecas){
						String nombreDiscotecaMinusculas = nombreDiscoteca.toLowerCase();
						if(dto.getNombre().toLowerCase().contains(nombreDiscotecaMinusculas)){
							ArrayAdapter<String> adapter = (ArrayAdapter<String>)lv.getAdapter();
							adapter.add(dto.getNombre());
						}
					}
				}
			}
		});
		botonAtras = (Button)findViewById(R.id.buscardiscoteca_bt_atras);
		botonAtras.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(),MenuActivity.class);
				startActivity(i);
			}
		});
		lv = getListView();
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
		lv.setAdapter(arrayAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
	                  int position, long id) {
						TextView tv = (TextView)view;
						Log.d(TAG_BUSCAR_DISCOTECA, "Selección de ListView: "+tv.getText());
						Log.d(TAG_BUSCAR_DISCOTECA, "Posición: "+String.valueOf(position));
						Log.d(TAG_BUSCAR_DISCOTECA, "id: "+String.valueOf(id));					
						if(listaDiscotecas!=null){
							Intent i = new Intent(getBaseContext(),DetallDiscotecaActivity.class);
							
							for(DiscotecaDTO dto: listaDiscotecas){
								String nombreDiscotecaMinusculas = tv.getText().toString().toLowerCase();
								if(dto.getNombre().toLowerCase().equals(nombreDiscotecaMinusculas)){
									i.putExtra(Constants.DISCOTECADTO, dto);
									startActivity(i);
								}
							}
						}
						
	              }
		});
	}

}
