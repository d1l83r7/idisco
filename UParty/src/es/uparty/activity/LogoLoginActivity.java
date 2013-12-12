package es.uparty.activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import es.uparty.R;
import es.uparty.asynctask.ValidarUsuarioAsyncTask;
import es.uparty.comunes.Constants;

public class LogoLoginActivity extends Activity {
	private static final String LOGOLOGIN_TAG = "LogoLoginActivity_tag";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logo_login);
		
		RelativeLayout lLayout = (RelativeLayout) findViewById (R.id.layout_logologin);
		Resources res = getResources(); //resource handle
		Drawable drawable = res.getDrawable(R.drawable.logo); //new Image that was added to the res folder

		lLayout.setBackgroundDrawable(drawable);
		
		Button anonimo = (Button)findViewById(R.id.logologin_bt_anonimo);
		anonimo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String nombreFichero = Constants.NOMBRE_FICHERO_PREFERENCIAS;
				SharedPreferences sp = getBaseContext().getSharedPreferences(nombreFichero, Context.MODE_PRIVATE);
				
				Editor editor = sp.edit();
				editor.putString(Constants.PREF_USUARIO, "");
				editor.putString(Constants.PREF_PASSWORD, "");
				editor.commit();
				startActivity(new Intent(getBaseContext(),MenuActivity.class));
			}
		});
		
		Button acceder = (Button)findViewById(R.id.logologin_bt_acceder);
		acceder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText usuario = (EditText)findViewById(R.id.logologin_et_usuario);
				String user = usuario.getText().toString();
				EditText password = (EditText)findViewById(R.id.logologin_et_password);
				String pass = password.getText().toString();
				
				StringBuffer sb = new StringBuffer();
				sb.append("http://radiant-ravine-3483.herokuapp.com/validaUsuario?");
				sb.append("usuario="+user);
				sb.append("&password="+pass);
				ValidarUsuarioAsyncTask vua = new ValidarUsuarioAsyncTask();
				vua.execute(sb.toString());
				String res = null;
				try{
					res = vua.get();
				}catch(Exception e){
					Log.e(LOGOLOGIN_TAG, e.getMessage());
					res = "N";
				}
				if(res.equals("S")){
					String nombreFichero = Constants.NOMBRE_FICHERO_PREFERENCIAS;
					SharedPreferences sp = getBaseContext().getSharedPreferences(nombreFichero, Context.MODE_PRIVATE);
					
					Editor editor = sp.edit();
					editor.putString(Constants.PREF_USUARIO, user);
					editor.putString(Constants.PREF_PASSWORD, pass);
					editor.commit();
					
						final View windowDecor = (View)getWindow().getDecorView();
						
						final ObjectAnimator scaleX = ObjectAnimator.ofFloat(windowDecor, "scaleX", 1f, 0f);
						final ObjectAnimator scaleY = ObjectAnimator.ofFloat(windowDecor, "scaleY", 1f, 0f);
						final ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(windowDecor, "alpha", 1f, 0f);
						
						final AnimatorSet an = new AnimatorSet();
						an.playTogether(scaleX,scaleY,alphaAnim);
						an.setDuration( 3 * 1000);
						an.addListener(new AnimatorListener()
						{
							@Override
							public void onAnimationCancel(Animator animation) {}

							@Override
							public void onAnimationEnd(Animator animation) {
								scaleX.reverse();
								scaleY.reverse();
								alphaAnim.reverse();
								startActivity(new Intent(getBaseContext(),MenuActivity.class));
							}

							@Override
							public void onAnimationRepeat(Animator animation) {}

							@Override
							public void onAnimationStart(Animator animation) {}
						});
						an.start();
				}
			}
		});
	}
}
