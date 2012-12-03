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

import javax.net.ssl.ManagerFactoryParameters;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.Vibrator;
import android.provider.Settings;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MenuTareas extends Activity {	
	static int cont=3;
	Thread RecTarea;
	ImageView img_avatar;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tareas);
        
        img_avatar=(ImageView)findViewById(R.id.img_avatar);
        Bitmap tmp=BitmapFactory.decodeFile("/sdcard/Mis archivos/twin_feliz.jpg");
        img_avatar.setImageBitmap(tmp);
        
        RecTarea=new Thread(){
			@Override
			public void run() {
				super.run();
				String texto="";
		        while(true){
		        	Calendar ndario = new GregorianCalendar();		
		    		int horas=(int)ndario.get(Calendar.HOUR_OF_DAY);
		    		int minutos=(int)ndario.get(Calendar.MINUTE);
		        	String hora=""+horas+":"+minutos+"";
		        	Log.d("*********************************", hora);
		        	try
					{
					    File ruta_sd = Environment.getExternalStorageDirectory();
					    File f = new File(ruta_sd.getAbsolutePath(),"/Mis archivos/BaseDeDatosTW.txt");
					    String str="Configuracion";
					    BufferedReader fin =       new BufferedReader(new InputStreamReader(new FileInputStream(f)));
					    while((texto=fin.readLine())!=null){
					    	String[] linea; 
					    	linea=texto.split(",");
					    	Log.d("*********************************", linea[2]);
					    	if(linea[2].equals(hora)){
					    		Log.d("*********************************", linea[1]);
					    		Log.d("*********************************", linea[2]);
					    		if(linea[1].equals(str)){
					    			Log.d("*********************************","OKKCONFIGURAAA");
					    			LanzarConfiguracion(linea[3],linea[4],linea[5]);
						    		BorrarRegistro(hora);
					    		}
					    		else{
					    			Log.d("********************************","OKK");
					    			LanzarAlarma(linea[1]);
						    		BorrarRegistro(hora);
						    	}
					    	}
					    	
					    }
					    	
						fin.close();
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					    Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");
					}
		        }
			}
        };
		RecTarea.start();
    }

	protected void LanzarConfiguracion(String modo,String wifi,String gps) {
		
		WifiManager wifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE);
		//Vibrator vibrate=(Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		//vibrate.vibrate(100);
		if(modo.equals("Vibrador"))
			((AudioManager) getSystemService(AUDIO_SERVICE)) .setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
		else if(modo.equals("Silencio"))
			((AudioManager) getSystemService(AUDIO_SERVICE)) .setRingerMode(AudioManager.RINGER_MODE_SILENT);
		else if(modo.equals("Normal"))
			((AudioManager) getSystemService(AUDIO_SERVICE)) .setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		
		if(wifi.equals("1")){
			Log.d("*********************************","AQUIIIII");
			wifiManager.setWifiEnabled(true);
		}
		else{
			wifiManager.setWifiEnabled(false);
		}
		
		if(gps.equals("1"))
			turnGPSOnOff();
				
			
		
	}
	private void turnGPSOnOff(){ 
		String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED); 
		if(!provider.contains("gps")){ 
			final Intent poke = new Intent(); 
			poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider"); 
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE); 
			poke.setData(Uri.parse("3")); 
			sendBroadcast(poke);
		}
	}

	protected void BorrarRegistro(String hora) {
		String texto="";
		ArrayList<String> tareas=new ArrayList<String>();
		try
		{
		    File ruta_sd = Environment.getExternalStorageDirectory();
		 
		    File f = new File(ruta_sd.getAbsolutePath(),"/Mis archivos/BaseDeDatosTW.txt");
		 
		    BufferedReader fin =
		        new BufferedReader(
		            new InputStreamReader(
		                new FileInputStream(f)));
		    while((texto=fin.readLine())!=null){
			    String []tmp=texto.split(",");
			    if(!tmp[2].equals(hora))
			    	tareas.add(texto);
		    }
			fin.close();
		}
		catch (Exception ex)
		{
		    Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");
		}
		SobrescribirArchivo(tareas);
	}

	private void SobrescribirArchivo(ArrayList<String> tareas) {
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

	protected void LanzarAlarma(String nombre) {
		AlarmManager alarm=(AlarmManager) this.getSystemService(ALARM_SERVICE);
		Intent itAlarma=new Intent(MenuTareas.this,RecordarTarea.class);
		itAlarma.putExtra("sonido",nombre);
		PendingIntent pIntent=PendingIntent.getActivity(getApplicationContext(), 3333, itAlarma, PendingIntent.FLAG_CANCEL_CURRENT);
		alarm.set(AlarmManager.RTC_WAKEUP,1,pIntent);
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
			Modificar();
			break;
		case R.id.Programar:
			Programar();
			break;
		}
		return false;
	}

	private void Modificar() {
		Intent itModify=new Intent(MenuTareas.this,Create1.class);
		startActivity(itModify);
		
	}

	private void Programar() {
		Intent intent=new Intent(MenuTareas.this,Programar.class);
		startActivity(intent);
		
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
		}
		startActivity(intent);
		
	}
	@Override
	protected void onDestroy() {
		super.onPause();
		
		RecTarea.interrupt();
	}
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		
	}  
	
}
