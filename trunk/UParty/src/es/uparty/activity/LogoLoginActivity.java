package es.uparty.activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import es.uparty.R;
import es.uparty.asynctask.ValidarUsuarioAsyncTask;
import es.uparty.comunes.Constants;

public class LogoLoginActivity extends Activity {
	private static final String LOGOLOGIN_TAG = "LogoLoginActivity_tag";
	final Context context = this;
	private EditText usuario;
	private EditText password;
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
				// get prompts.xml view
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View promptView = layoutInflater.inflate(R.layout.prompts, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                // set prompts.xml to be the layout file of the alertdialog builder
                alertDialogBuilder.setView(promptView);
                usuario = (EditText) promptView.findViewById(R.id.userInput);
                password = (EditText) promptView.findViewById(R.id.logo_login_et_password);
                // setup a dialog window
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        
                                        StringBuffer sb = new StringBuffer();
                        				sb.append("http://radiant-ravine-3483.herokuapp.com/validaUsuario?");
                        				sb.append("usuario="+usuario.getText().toString());
                        				sb.append("&password="+password.getText().toString());
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
                        					editor.putString(Constants.PREF_USUARIO, usuario.getText().toString());
                        					editor.putString(Constants.PREF_PASSWORD, password.getText().toString());
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
                        				}else{
                        					String text = getResources().getString(R.string.logologin_dialog_msg_error);
                        					Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                        				}
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                // create an alert dialog
                AlertDialog alertD = alertDialogBuilder.create();
                alertD.show();
			}
		});
	}
}
