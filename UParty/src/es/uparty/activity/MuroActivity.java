package es.uparty.activity;

import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import es.uparty.R;
import es.uparty.adapter.MuroAdapter;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.muro);
		
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
//		View item = muroAdapter.getView(0, null, lv);
//        item.measure(0, 0);         
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) (5.5 * item.getMeasuredHeight()));
//        lv.setLayoutParams(params);
	}
}
