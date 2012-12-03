package com.example.my.twin;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class Nueva_Tarea extends Activity implements OnClickListener,OnCompletionListener {
	protected static final int WIFI_STATE_ENABLED = 2;
	private static final int READ_BLOCK_SIZE=100;
	Button btnStar,btnFinish,btnPlay;
	MediaRecorder recorder;
	File archivo;
	MediaPlayer player;
	
	private static final int Alarm_Request_Code=1;
	Time time = new Time();

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_tarea);
        
        
        
        Button cancel1=(Button) findViewById(R.id.back2);
        Button ok1=(Button) findViewById(R.id.ok1);
        
        btnStar=(Button) findViewById(R.id.btn_startRecord);
		btnFinish=(Button) findViewById(R.id.btn_StopRecord);
		btnPlay=(Button) findViewById(R.id.btn_PlayRecord);
		
		btnStar.setOnClickListener(this);
		btnFinish.setOnClickListener(this);
		btnPlay.setOnClickListener(this);
		
		Calendar calendario = new GregorianCalendar();
	     TimePicker tp=(TimePicker) findViewById(R.id.horaTarea);
	     
	     tp.clearFocus();
	     time.hour=(int)tp.getCurrentHour();
	     time.minute=(int)tp.getCurrentMinute();
	     time.second=(int)calendario.get(Calendar.SECOND);
	     
	     
		
        
        cancel1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent=new Intent(Nueva_Tarea.this,MenuTareas.class);
				startActivity(intent);
				finish();				
			}
		});        
        
        ok1.setOnClickListener(new OnClickListener() {
			

			public void onClick(View v) {
				Bundle bundle=getIntent().getExtras();
				int i=1;   int numeroTareas;
				Intent intent=new Intent(Nueva_Tarea.this,ListaTareas.class);
				TimePicker tp=(TimePicker) findViewById(R.id.horaTarea);
				Time time2 = new Time();
				
				intent.putExtra("agregar",i);
				Toast.makeText(getBaseContext(),"agregar", Toast.LENGTH_SHORT).show();
				numeroTareas=bundle.getInt("identificador");
		        tp.clearFocus();
		        time2.hour=(int)tp.getCurrentHour();
		        time2.minute=(int)tp.getCurrentMinute();
				startActivity(intent);
				finish();
				String newTarea=numeroTareas+",Tarea"+numeroTareas+".3gp,"+time2.hour+":"+time2.minute;
			
				AgregarTarea(newTarea);

			}
		});
        
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
	

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void onCompletion(MediaPlayer mp) {
		btnPlay.setEnabled(true);        
        btnFinish.setEnabled(true);
        btnStar.setEnabled(true);
		
	}


	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_startRecord:
			try{
    			grabar(v);	
    			Toast.makeText(getBaseContext(),"Grabando", Toast.LENGTH_SHORT).show();
    		}catch(Exception e){
    			e.printStackTrace();
    			
    		}
			break;
		case R.id.btn_StopRecord:
			try{
    			detener(v);
    			Toast.makeText(getBaseContext(),"Pausado", Toast.LENGTH_SHORT).show();
    		}catch(Exception e){
    			e.printStackTrace();
    			
    		}
			break;
		case R.id.btn_PlayRecord:
			try{
    			reproducir(v);
    			Toast.makeText(getBaseContext(),"Reproduciendo", Toast.LENGTH_SHORT).show();
    		}catch(Exception e){
    			e.printStackTrace();
    			
    		}
			break;
		}
		
	}


	private void reproducir(View v) {
		player.start();
        btnPlay.setEnabled(false);
        btnFinish.setEnabled(false);        
        btnStar.setEnabled(false);     
	}


	private void detener(View v) {
		recorder.stop();
        recorder.release();   
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        try {
            player.setDataSource(archivo.getAbsolutePath());
        } catch (IOException e) {
        }
        try {
            player.prepare();
        } catch (IOException e) {
        }            
        btnPlay.setEnabled(true);        
        btnFinish.setEnabled(false);
        btnStar.setEnabled(true); 
	}


	private void grabar(View v) {
		 	recorder = new MediaRecorder();
		 	
	        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
	        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
	       
	        File path = new File("/sdcard/Mis archivos/");
	        Bundle bundle=getIntent().getExtras();
	        String name="Tarea"+bundle.getInt("identificador")+".3gp";
	        Toast.makeText(getBaseContext(),name, Toast.LENGTH_SHORT).show();
	        try {
	        	archivo =new File(path,name);
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	        recorder.setOutputFile(archivo.getAbsolutePath());
	        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	        try {
	            recorder.prepare();
	        } catch (IOException e) {
	        }
	        recorder.start();
	        btnPlay.setEnabled(false);        
	        btnFinish.setEnabled(true);    
	}    
}
