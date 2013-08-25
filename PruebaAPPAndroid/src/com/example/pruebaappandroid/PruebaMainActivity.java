package com.example.pruebaappandroid;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PruebaMainActivity extends Activity {

	public int contador=0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pruebadoslayout);
        try {
            Button boton = (Button) findViewById(R.id.botonprueba);
            boton.setOnClickListener(new View.OnClickListener() {
     		
     		@Override
     		public void onClick(View v) {
     			contador++;
     			TextView vista = (TextView) findViewById(R.id.textView1);
     			vista.setText(String.valueOf(contador));			
     		}
     	});
			
		} catch (Exception e) {
System.out.println();
		}
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.prueba_main, menu);
        return true;
    }
    
}
