package es.uparty.activity;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.ActionBar.LayoutParams;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import es.uparty.R;
import es.uparty.adapter.MyAdapter;
import es.uparty.asynctask.ObtenerDisctecasAsyncTask;
import es.uparty.comunes.Constants;
import es.uparty.dto.DiscotecaDTO;

public class BuscarDiscotecaActivity extends ListActivity{

	private Button botonBuscar = null;
	private Button botonAtras = null;
	private List<DiscotecaDTO> listaDiscotecas = null;
	private final static String TAG_BUSCAR_DISCOTECA = "TAG_BUSCAR_DISCOTECA";
	private ListView lv = null;
	private MyAdapter myAdapter = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buscardiscoteca);
		
		botonBuscar = (Button)findViewById(R.id.buscardiscoteca_bt_buscar);
		botonBuscar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText editText = (EditText)findViewById(R.id.buscardiscoteca_et_nombreDiscoteca);
				String nombreDiscoteca = editText.getText().toString();
				Log.d(TAG_BUSCAR_DISCOTECA, "Nombre discoteca: "+nombreDiscoteca);
				
				ObtenerDisctecasAsyncTask obtenerDisctecasAsyncTask = new ObtenerDisctecasAsyncTask();
				String url = "http://radiant-ravine-3483.herokuapp.com/getDiscotecasByName?name="+nombreDiscoteca;
//				String url = "http://192.168.1.14:9000/getDiscotecasByName?name="+nombreDiscoteca;
				obtenerDisctecasAsyncTask.execute(url);
				try{
					listaDiscotecas = obtenerDisctecasAsyncTask.get();
				}catch(InterruptedException ie){
					Log.e(TAG_BUSCAR_DISCOTECA, ie.getMessage());
				}catch(ExecutionException e){
					Log.e(TAG_BUSCAR_DISCOTECA, e.getMessage());
				}
				if(listaDiscotecas!=null){
					myAdapter.setlDTO(listaDiscotecas);
					lv.setAdapter(myAdapter);
					if(myAdapter.getCount() > 5){
				        View item = myAdapter.getView(0, null, lv);
				        item.measure(0, 0);         
				        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) (5.5 * item.getMeasuredHeight()));
				        lv.setLayoutParams(params);
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
//		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
		myAdapter = new MyAdapter(this);
		lv.setAdapter(myAdapter);
		View item = myAdapter.getView(0, null, lv);
        item.measure(0, 0);         
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) (5.5 * item.getMeasuredHeight()));
        lv.setLayoutParams(params);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
	                  int position, long id) {
						Log.d(TAG_BUSCAR_DISCOTECA, "Posición: "+String.valueOf(position));
						Log.d(TAG_BUSCAR_DISCOTECA, "id: "+String.valueOf(id));					
						if(listaDiscotecas!=null){
							Intent i = new Intent(getBaseContext(),DetallDiscotecaActivity.class);
							i.putExtra(Constants.DISCOTECADTO, listaDiscotecas.get(position));
							i.putExtra(Constants.ORIGEN, Constants.ORIGEN_BUSQUEDA);
							startActivity(i);
						}
						
	              }
		});
	}
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(getBaseContext(),MenuActivity.class);
		startActivity(i);
		super.onBackPressed();
	}

}
