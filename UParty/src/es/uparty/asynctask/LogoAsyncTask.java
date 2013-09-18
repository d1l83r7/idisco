package es.uparty.asynctask;

import es.uparty.activity.MenuActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

public class LogoAsyncTask extends AsyncTask<ProgressBar, Integer, Integer> {

	private ProgressBar progBar = null;
	private Context context = null;
	
	public LogoAsyncTask(Context context){
		this.context = context;
	}
	
    protected Integer doInBackground(ProgressBar... progBar) {
    	this.progBar = progBar[0];
    	for(int i=0;i<100000;i++){
    		publishProgress(i);
    	}
        return 1;
    }

    protected void onProgressUpdate(Integer... progress) {
        progBar.setProgress(progress[0]);
    }

    protected void onPostExecute(Integer result) {
        progBar.setVisibility(View.GONE);
        context.startActivity(new Intent(context, MenuActivity.class));

    }
}