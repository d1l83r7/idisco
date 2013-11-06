package es.uparty.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import es.uparty.R;
import es.uparty.asynctask.ObtenerImagenAsyncTask;
import es.uparty.dto.DiscotecaDTO;

public class MyAdapter extends BaseAdapter {
	Activity mActivity;
	private List<DiscotecaDTO> lDTO = null;
	
	public List<DiscotecaDTO> getlDTO() {
		return lDTO;
	}

	public void setlDTO(List<DiscotecaDTO> lDTO) {
		this.lDTO = lDTO;
	}

	public MyAdapter(Activity act){
		mActivity = act;
	}
	
	@Override
	public int getCount() {
		return 100000;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	class DatosTag{
		TextView nombre;
		ImageView img;
		int mId;
		
		public DatosTag(TextView nombre, ImageView img, int id){
			this.mId = id;
			this.nombre = nombre;
			this.img = img;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null){
			convertView = mActivity.getLayoutInflater().inflate(R.layout.discoteca_lv,parent,false);
			TextView tv = (TextView)convertView.findViewById(R.id.discoteca_lv_nombre);
			ImageView img = (ImageView)convertView.findViewById(R.id.discoteca_lv_img);
			DatosTag dt = new DatosTag(tv, img, 0);
			convertView.setTag(dt);
		}
		
		//convertView esta preparado para usarse
		
		DatosTag dt = (DatosTag)convertView.getTag();
		if(lDTO!=null){
			if(position<lDTO.size()){
				dt.nombre.setText(lDTO.get(position).getNombre());
				InputStream is = null;
				Drawable avatar = null;
				String inputurl = "http://radiant-ravine-3483.herokuapp.com/public/images/discotecas/"+lDTO.get(position).getNombreImagen();
				ObtenerImagenAsyncTask o = new ObtenerImagenAsyncTask();
				o.execute(inputurl);
				
				try {
			        Drawable dw = o.get();
			        dt.img.setImageDrawable(dw);
				} catch (ExecutionException e) {
				        e.printStackTrace();
				} catch (InterruptedException e) {
				        e.printStackTrace();
				}
			}
		}
//		dt.img.set
		
		return convertView;
	}

}
