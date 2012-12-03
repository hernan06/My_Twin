package com.example.my.twin;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Tarea extends Activity implements OnClickListener,OnCompletionListener {
	Button btnStar,btnFinish,btnPlay;
	EditText ed_tarea;
	MediaRecorder recorder;
	File archivo;
	MediaPlayer player;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tarea);
		btnStar=(Button) findViewById(R.id.btn_starRecord);
		btnFinish=(Button) findViewById(R.id.btn_finishRecord);
		btnPlay=(Button) findViewById(R.id.btn_playRecord);
		
		btnStar.setOnClickListener(this);
		btnFinish.setOnClickListener(this);
		btnPlay.setOnClickListener(this);
	
		
	}

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_starRecord:
			try{
    			grabar(v);	
    			Toast.makeText(getBaseContext(),"Grabando", Toast.LENGTH_SHORT).show();
    		}catch(Exception e){
    			e.printStackTrace();
    		}
			break;
		case R.id.btn_finishRecord:
			try{
    			detener(v);
    			Toast.makeText(getBaseContext(),"Pausado", Toast.LENGTH_SHORT).show();
    		}catch(Exception e){
    			e.printStackTrace();
    		}
			break;
		case R.id.btn_playRecord:
			try{
    			reproducir(v);
    			Toast.makeText(getBaseContext(),"Reproduciendo", Toast.LENGTH_SHORT).show();
    		}catch(Exception e){
    			e.printStackTrace();
    			
    		}
			break;
		}

	}

	public void grabar(View v) {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        File path = new File("/sdcard/data");
        try {
            archivo = File.createTempFile("temporal", ".3gp", path);
        } catch (IOException e) {
        }
        recorder.setOutputFile(archivo.getAbsolutePath());
        try {
            recorder.prepare();
        } catch (IOException e) {
        }
        recorder.start();
        btnPlay.setEnabled(false);        
        btnFinish.setEnabled(true);    
    }

    
    public void detener(View v) {
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
    
    public void reproducir(View v) {
        player.start();
        btnPlay.setEnabled(false);
        btnFinish.setEnabled(false);        
        btnStar.setEnabled(false);     
    }
            

    public void onCompletion(MediaPlayer mp) {
        btnPlay.setEnabled(true);        
        btnFinish.setEnabled(true);
        btnStar.setEnabled(true);
    }

	
}