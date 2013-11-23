package es.uparty.asynctask;

import java.io.InputStream;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

public class ObtenerImagenAsyncTask extends AsyncTask<String, Integer, Drawable> {

	private static final String ObtenerImagenAsyncTask_TAG = "ObtenerImagenAsyncTask_TAG";
	
	@Override
	protected Drawable doInBackground(String... params) {
		try{
			URL url = new URL(params[0]);
			InputStream is = (InputStream) url.getContent();
			Drawable dw = Drawable.createFromStream(is,"src");
			return dw;
		}catch(Exception e){
			Log.d(ObtenerImagenAsyncTask_TAG, "Error: "+e.getMessage());
			return null;
		}
	}
}
