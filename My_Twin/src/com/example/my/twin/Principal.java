package com.example.my.twin;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Principal extends Activity{
	Intent Cambio;
	Button entrada;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        
        String str ="";
        File sdCard, directory, file = null;
        sdCard = Environment.getExternalStorageDirectory();
		FileOutputStream fout = null;
		try {
			directory = new File(sdCard.getAbsolutePath()
					+ "/Mis archivos");
			directory.mkdirs();

			file = new File(directory, "BaseDeDatosTW.txt");

			fout = new FileOutputStream(file);
			OutputStreamWriter ows = new OutputStreamWriter(fout);
			ows.write(str);
			ows.flush(); 
			ows.close(); 
			Toast.makeText(getBaseContext(),
					"El archivo se ha creado!!!",
					Toast.LENGTH_SHORT).show();
	

		} catch (IOException e) {
			e.printStackTrace();
		}

        entrada=(Button)findViewById(R.id.button1);
        entrada.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				inicio();
				finish();
			}
		});
       
    }
    public void inicio(){
    	File f=new File("/sdcard/Mis archivos/twin_feliz.jpg");
    	if(!f.exists()){
	    	Cambio = new Intent(this,Create1.class);
	    	startActivity(Cambio);
    	}
    	else{
    		Cambio = new Intent(this,MenuTareas.class);
	    	startActivity(Cambio);
    	}	
    }

}
