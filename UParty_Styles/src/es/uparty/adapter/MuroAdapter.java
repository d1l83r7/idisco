package es.uparty.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import es.uparty.R;
import es.uparty.dto.MensajeDTO;

public class MuroAdapter extends BaseAdapter {
	Activity mActivity;
	private List<MensajeDTO> lDTO = new ArrayList<MensajeDTO>();
	
	public List<MensajeDTO> getlDTO() {
		return lDTO;
	}

	public void setlDTO(List<MensajeDTO> lDTO) {
		this.lDTO = lDTO;
	}

	public MuroAdapter(Activity act){
		mActivity = act;
	}
	
	@Override
	public int getCount() {
		return lDTO.size();
	}

	@Override
	public Object getItem(int position) {
		return lDTO.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	class DatosTag{
		TextView usuario;
		TextView mensaje;
		int mId;
		
		public DatosTag(TextView usuario, TextView mensaje, int id){
			this.mId = id;
			this.usuario = usuario;
			this.mensaje = mensaje;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null){
			convertView = mActivity.getLayoutInflater().inflate(R.layout.muro_lv,parent,false);
			TextView usuario = (TextView)convertView.findViewById(R.id.muro_lv_usuario);
			TextView mensaje = (TextView)convertView.findViewById(R.id.muro_lv_mensaje);
			DatosTag dt = new DatosTag(usuario, mensaje, 0);
			convertView.setTag(dt);
		}
		
		//convertView esta preparado para usarse
		
		DatosTag dt = (DatosTag)convertView.getTag();
		if(lDTO!=null){
			if(position<lDTO.size()){
				dt.usuario.setText(lDTO.get(position).getUsuario()+": ");
			    dt.mensaje.setText("\""+lDTO.get(position).getTexto()+"\"");
			}
		}
		return convertView;
	}

}
