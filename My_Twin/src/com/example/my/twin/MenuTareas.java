package com.example.my.twin;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MenuTareas extends Activity {	
	static int cont=3;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tareas);
        String str ="";
        File sdCard, directory, file = null;
		// Clase que permite grabar texto en un archivo
        sdCard = Environment.getExternalStorageDirectory();
		FileOutputStream fout = null;
		try {
			// instanciamos un onjeto File para crear un nuevo
			// directorio
			// la memoria externa
			directory = new File(sdCard.getAbsolutePath()
					+ "/Mis archivos");
			// se crea el nuevo directorio donde se cerara el
			// archivo
			directory.mkdirs();

			// creamos el archivo en el nuevo directorio creado
			file = new File(directory, "BaseDeDatosTW.txt");

			if(!file.exists()){
				fout = new FileOutputStream(file);
				// Convierte un stream de caracteres en un stream de
				// bytes
				OutputStreamWriter ows = new OutputStreamWriter(fout);
				ows.write(str); // Escribe en el buffer la cadena de texto
				ows.flush(); // Volca lo que hay en el buffer al archivo
				ows.close(); // Cierra el archivo de texto
	
				Toast.makeText(getBaseContext(),
						"El archivo se ha almacenado!!!",
						Toast.LENGTH_SHORT).show();
	
				//txtTexto.setText("");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.exit:
			this.finish();
			break;
		case R.id.Tarea:
			Tareas();
			break;
		case R.id.Modificar:
			break;
		case R.id.Programar:
			break;
		}
		return false;
	}

	private void Tareas() {
		int j=0;
		Intent intent=new Intent(MenuTareas.this,ListaTareas.class);
		if(getIntent().getExtras()!=null){					
			Bundle bundle=getIntent().getExtras();
			int i=bundle.getInt("agregar");
			intent.putExtra("agregar",i);				    
		}else{
			intent.putExtra("agregar",j);
			Toast.makeText(getBaseContext(), "null", Toast.LENGTH_SHORT).show();
		}
		startActivity(intent);
		
	}  
	
}
