package es.uparty.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import es.uparty.R;
import es.uparty.asynctask.AltaEnListaVipAsyncTask;
import es.uparty.comunes.Constants;
import es.uparty.comunes.Utils;
import es.uparty.dto.DiscotecaDTO;

public class DetallDiscotecaActivity extends GPSGenericActivity {
	private TextView tvNombre = null;
	private TextView tvDescripcion = null;
	private Button btAtras;
	private Button btRuta;
	private Button btMuro;
	private Button btListaVip;
	private DiscotecaDTO dto = null;
	private RadioButton rbCoche = null;
	private RadioButton rbPie = null;
	private RadioButton rbTranPublico = null;
	private String origen = null;
	final Context context = this;
	private int acompanyantes = 0;
	
	private final String DetallDiscotecaActivity_TAG = "DetallDiscotecaActivity_TAG";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detallediscoteca);
		
		dto = (DiscotecaDTO)getIntent().getSerializableExtra(Constants.DISCOTECADTO);
		origen = getIntent().getExtras().getString(Constants.ORIGEN);
		String nombre = dto.getNombre();
		String descripcion = dto.getDescripcio();
		
		tvNombre = (TextView)findViewById(R.id.detallediscoteca_tv_nombre);
		tvNombre.setText(nombre);
		
		tvDescripcion = (TextView)findViewById(R.id.detallediscoteca_tv_descripcion);
		tvDescripcion.setText(descripcion);
		
		btAtras = (Button)findViewById(R.id.detallediscoteca_bt_atras);
		btAtras.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i = null;
				
				if(origen.equals(Constants.ORIGEN_BUSQUEDA)){
					i = new Intent(getBaseContext(),BuscarDiscotecaActivity.class);
					startActivity(i);
					overridePendingTransition(R.anim.right_in, R.anim.right_out);
				}else if(origen.equals(Constants.ORIGEN_MAPA)){
					buscaGPS(0,null,0,origen);
				}
			}
		});
		
		btRuta = (Button)findViewById(R.id.detallediscoteca_bt_ruta);
		btRuta.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater layoutInflater = LayoutInflater.from(context);
                View promptView = layoutInflater.inflate(R.layout.detallediscoteca_dialog_ruta, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                // set prompts.xml to be the layout file of the alertdialog builder
                alertDialogBuilder.setView(promptView);
                rbPie = (RadioButton)promptView.findViewById(R.id.detallediscoteca_rb_caminando);
        		rbPie.setChecked(true);
        		rbCoche = (RadioButton)promptView.findViewById(R.id.detallediscoteca_rb_coche);
        		rbCoche.setChecked(false);
        		rbTranPublico = (RadioButton)promptView.findViewById(R.id.detallediscoteca_rb_transp_publico);
        		rbTranPublico.setChecked(false);
                // setup a dialog window
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    	int modeRuta = 0;
                        				if(rbCoche.isChecked())
                        					modeRuta = 0;
                        				else if(rbPie.isChecked())
                        					modeRuta = 1;
                        				else if(rbTranPublico.isChecked())
                        					modeRuta = 2;
                        				buscaGPS(1, dto,modeRuta,origen);
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
		
		btMuro = (Button)findViewById(R.id.detallediscoteca_bt_muro);
		btMuro.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getBaseContext(),MuroActivity.class);
				i.putExtra(Constants.DISCOTECADTO, dto);
				i.putExtra(Constants.ORIGEN, origen);
				startActivity(i);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);
			}
		});
		
		btListaVip = (Button)findViewById(R.id.detallediscoteca_bt_listaVIP);
		btListaVip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater layoutInflater = LayoutInflater.from(context);
                View promptView = layoutInflater.inflate(R.layout.detallediscoteca_dialog_lista_vip, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptView);
                Spinner sp = (Spinner)promptView.findViewById(R.id.acompanyantes_lista_vip);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getBaseContext(),
                        R.array.acompanyantes_lista_vip, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp.setAdapter(adapter);
                
                sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						acompanyantes = arg2;
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
                
                alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            	AltaEnListaVipAsyncTask aelv = new AltaEnListaVipAsyncTask();
                            	String[] params = new String[4];
                            	
                            	String nombreFichero = Constants.NOMBRE_FICHERO_PREFERENCIAS;
                        		SharedPreferences sp = getBaseContext().getSharedPreferences(nombreFichero, Context.MODE_PRIVATE);
                        		String valorUsuario = sp.getString(Constants.PREF_USUARIO, "");
                        		String valorPassword = sp.getString(Constants.PREF_PASSWORD, "");
                        		String user = valorUsuario;
                        		String pass = valorPassword;
                            	params[0] = user;
                            	params[1] = pass;
                            	params[2] = String.valueOf(dto.getId());
                            	params[3] = String.valueOf(acompanyantes);
                            	aelv.execute(params);
                            	try{
                            	aelv.get();
                            	}catch(Exception e){e.printStackTrace();}
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
		
		String nombreFichero = Constants.NOMBRE_FICHERO_PREFERENCIAS;
		SharedPreferences sp = getBaseContext().getSharedPreferences(nombreFichero, Context.MODE_PRIVATE);
		String valorUsuario = sp.getString(Constants.PREF_USUARIO, "");
		String valorPassword = sp.getString(Constants.PREF_PASSWORD, "");
		
		if(valorUsuario.equals("")||valorPassword.equals("")){
			btMuro.setEnabled(false);
			btMuro.setBackgroundColor(Color.GRAY);
			btListaVip.setEnabled(false);
			btListaVip.setBackgroundColor(Color.GRAY);
		}else if(dto.getListaVipActiva().equals("N")){
			btListaVip.setEnabled(false);
			btListaVip.setBackgroundColor(Color.GRAY);
		}
	}
	
	@Override
	public void onBackPressed() {
		Intent i = null;
		if(origen.equals(Constants.ORIGEN_BUSQUEDA)){
			i = new Intent(getBaseContext(),BuscarDiscotecaActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.right_out);
		}else if(origen.equals(Constants.ORIGEN_MAPA)){
			buscaGPS(0,null,0,null);
		}
		super.onBackPressed();
	}
}
