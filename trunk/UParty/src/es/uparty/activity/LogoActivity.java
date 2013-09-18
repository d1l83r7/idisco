package es.uparty.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import es.uparty.R;
import es.uparty.asynctask.LogoAsyncTask;

public class LogoActivity extends Activity {

	private static final String LOGO_TAG = "logotag";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logo);
		
		RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.layout_logo);
		Resources res = getResources(); //resource handle
		Drawable drawable = res.getDrawable(R.drawable.logo); //new Image that was added to the res folder

		rLayout.setBackgroundDrawable(drawable);
		
		ProgressBar progBar = (ProgressBar)findViewById(R.id.logo_progressbar);
		progBar.setVisibility(View.VISIBLE);
		LogoAsyncTask logoAsyncTask = new LogoAsyncTask(this);
		logoAsyncTask.execute(progBar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logo, menu);
		return true;
	}

}
