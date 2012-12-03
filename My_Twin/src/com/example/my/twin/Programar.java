package com.example.my.twin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;



public class Programar extends Activity{
	Button programar,regresar;
	TimePicker tp_program;
	CheckBox wifi,gps;
	Spinner sp;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.programar);
		sp=(Spinner)findViewById(R.id.sp_modo);
		wifi=(CheckBox) findViewById(R.id.ckb_wifi);
		gps=(CheckBox) findViewById(R.id.ckb_gps);
		tp_program=(TimePicker)findViewById(R.id.tp_alarm_program);
		regresar=(Button)findViewById(R.id.btn_regresar);
		programar=(Button)findViewById(R.id.btn_programar);
		
		regresar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Regresar();
			}
		});
		programar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Programar();
			}
		});
	}


	protected void Regresar() {
		Intent itregresar=new Intent(this,MenuTareas.class);
		startActivity(itregresar);
		finish();
	}


	protected void Programar() {
		int numwifi=0,numgps=0;
		Time time2 = new Time();
		tp_program.clearFocus();
        time2.hour=(int)tp_program.getCurrentHour();
        time2.minute=(int)tp_program.getCurrentMinute();
        
		String modo=sp.getSelectedItem().toString();
		if(wifi.isChecked())
			numwifi=1;
		if(gps.isChecked())
			numgps=1;
		
		AgregarTarea("0,"+"Configuracion"+","
								+time2.hour+":"+time2.minute+","+
								modo+","+numwifi+","+numgps);
		
		
		Intent itregresar=new Intent(this,MenuTareas.class);
		startActivity(itregresar);
		finish();
	}
	protected void AgregarTarea(String newTarea) {
		String texto="";
		ArrayList<String> tareas=new ArrayList<String>();
		try
		{
		    File ruta_sd = Environment.getExternalStorageDirectory();
		 
		    File f = new File(ruta_sd.getAbsolutePath(), "/Mis archivos/BaseDeDatosTW.txt");
		 
		    BufferedReader fin =
		        new BufferedReader(
		            new InputStreamReader(
		                new FileInputStream(f)));
		    while((texto=fin.readLine())!=null)
			    tareas.add(texto);
			fin.close();
		}
		catch (Exception ex)
		{
		    Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");
		}
		tareas.add(newTarea);
		SobrescribirArchivo(tareas);
		
	}


	private void SobrescribirArchivo(ArrayList<String> tareas) {
		File sdCard, directory, file = null;
		String str="";
        sdCard = Environment.getExternalStorageDirectory();
		FileOutputStream fout = null;
		
		try {
			directory = new File(sdCard.getAbsolutePath()
					+ "/Mis archivos");
			directory.mkdirs();

			file = new File(directory, "BaseDeDatosTW.txt");
				
				fout = new FileOutputStream(file);
				OutputStreamWriter ows = new OutputStreamWriter(fout);
				for(String s:tareas){
					ows.write(s);
					ows.flush(); 
					ows.write("\n");
				}
				
				ows.close(); 
	
				Toast.makeText(getBaseContext(),
						"El archivo se ha modificado!!!",
						Toast.LENGTH_SHORT).show();
	

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


}