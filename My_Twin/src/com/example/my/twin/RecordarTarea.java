package com.example.my.twin;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.util.Log;
import android.widget.ImageView;





public class RecordarTarea extends Activity{
	Thread alarma;
	MediaPlayer mp=new MediaPlayer();
	Vibrator vibrate;
	String sound;
	ImageView img_fond;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recordartarea);
		
		img_fond=(ImageView)findViewById(R.id.img_fondo);
		Bitmap tmp=BitmapFactory.decodeFile("/sdcard/Mis archivos/twin_enojado.jpg");
		img_fond.setImageBitmap(tmp);
		
		//Vibrator vibrate=(Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		//vibrate.vibrate(2000);
		Bundle bundle=getIntent().getExtras();
	    sound=bundle.getString("sonido");
		
		try{
			mp.reset();
			
			mp.setDataSource("/sdcard/Mis archivos/"+sound);
			mp.prepare();
			mp.start();
			alarma=new Thread(){
				@Override
				public void run() {
					super.run();
					long control=System.currentTimeMillis()+(long)mp.getDuration();
					int a=0;
					while(a<2){
						if(control==System.currentTimeMillis()){
							mp.start();
							control=System.currentTimeMillis()+(long)mp.getDuration();
							a++;
						}	
					}
				}
			};
			alarma.start();
			//vibrate=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			//vibrate.vibrate(2000);


		}catch(IOException e){
			e.printStackTrace();
		}
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//vibrate.cancel();
		alarma.interrupt();
		File f=new File("/sdcard/Mis archivos/"+sound);
		if(f.exists())
			f.delete();
	}
	
	
}