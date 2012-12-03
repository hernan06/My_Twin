package com.example.my.twin;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;



import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


public class Create2 extends Activity implements OnClickListener {
ImageView img,cuerpo1,cuerpo2,cuerpo3,cuerpo4,cuerpo5,cuerpo6;
Button btSigte,btAtras;
Intent itSgte,itAtras;
int control=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create2);
		img=(ImageView)findViewById(R.id.principal);
        cuerpo1=(ImageView)findViewById(R.id.cp1);
        cuerpo2=(ImageView)findViewById(R.id.cp2);
        cuerpo3=(ImageView)findViewById(R.id.cp3);
        cuerpo4=(ImageView)findViewById(R.id.cp4);
        cuerpo5=(ImageView)findViewById(R.id.cp5);
        cuerpo6=(ImageView)findViewById(R.id.cp6);
        
        //final Resources res = this.getResources();
        //Bitmap cp = BitmapFactory.decodeResource(res,R.drawable.cuerpo2);
        //cuerpo1.setImageBitmap(cp);
        
        btSigte=(Button) findViewById(R.id.btn_sgte);
        btAtras=(Button) findViewById(R.id.btn_regresar);
        btSigte.setOnClickListener(this);
        btAtras.setOnClickListener(this);
        cuerpo1.setOnClickListener(this);
        cuerpo2.setOnClickListener(this);
        cuerpo3.setOnClickListener(this);
        cuerpo4.setOnClickListener(this);
        cuerpo5.setOnClickListener(this);
        cuerpo6.setOnClickListener(this);
	}

	public void onClick(View v) {
		
		switch (v.getId()){
		case R.id.cp1:
			img.setImageBitmap(muestraMyTwin(R.drawable.cuerpo1));
			control = 0;
			break;
		case R.id.cp2:
			img.setImageBitmap(muestraMyTwin(R.drawable.cuerpo2));
			control = 1;
			break;
		case R.id.cp3:
			img.setImageBitmap(muestraMyTwin(R.drawable.cuerpo3));
			control = 2;
			break;
		case R.id.cp4:
			img.setImageBitmap(muestraMyTwin(R.drawable.cuerpo4));
			control = 3;
			break;
		case R.id.cp5:
			img.setImageBitmap(muestraMyTwin(R.drawable.cuerpo5));
			control = 4;
			break;
		case R.id.cp6:
			img.setImageBitmap(muestraMyTwin(R.drawable.cuerpo6));
			control = 5;
			break;
		case R.id.btn_sgte:
			Siguiente();
			break;
		case R.id.btn_regresar:
			Regresar();
			break;
		}
		
		
	}

	private void Regresar() {
		itAtras=new Intent(this,Create1.class);
		startActivity(itAtras);
	}

	private void Siguiente() {
		switch(control){
		case 0:
		 	combinaImagenes("/sdcard/Mis archivos/smile.jpg", R.drawable.cuerpo1, "twin_feliz.jpg");
		 	combinaImagenes("/sdcard/Mis archivos/angry.jpg", R.drawable.cuerpo1, "twin_enojado.jpg");
		 	combinaImagenes("/sdcard/Mis archivos/sad.jpg", R.drawable.cuerpo1, "twin_triste.jpg");
	        break;
		case 1:
		 	combinaImagenes("/sdcard/Mis archivos/smile.jpg", R.drawable.cuerpo2, "twin_feliz.jpg");
		 	combinaImagenes("/sdcard/Mis archivos/angry.jpg", R.drawable.cuerpo2, "twin_enojado.jpg");
		 	combinaImagenes("/sdcard/Mis archivos/sad.jpg", R.drawable.cuerpo2, "twin_triste.jpg");
	        break;
		case 2:
		 	combinaImagenes("/sdcard/Mis archivos/smile.jpg", R.drawable.cuerpo3, "twin_feliz.jpg");
		 	combinaImagenes("/sdcard/Mis archivos/angry.jpg", R.drawable.cuerpo3, "twin_enojado.jpg");
		 	combinaImagenes("/sdcard/Mis archivos/sad.jpg", R.drawable.cuerpo3, "twin_triste.jpg");
	        break;
		case 3:
		 	combinaImagenes("/sdcard/Mis archivos/smile.jpg", R.drawable.cuerpo4, "twin_feliz.jpg");
		 	combinaImagenes("/sdcard/Mis archivos/angry.jpg", R.drawable.cuerpo4, "twin_enojado.jpg");
		 	combinaImagenes("/sdcard/Mis archivos/sad.jpg", R.drawable.cuerpo4, "twin_triste.jpg");
	        break;
		case 4:
		 	combinaImagenes("/sdcard/Mis archivos/smile.jpg", R.drawable.cuerpo5, "twin_feliz.jpg");
		 	combinaImagenes("/sdcard/Mis archivos/angry.jpg", R.drawable.cuerpo5, "twin_enojado.jpg");
		 	combinaImagenes("/sdcard/Mis archivos/sad.jpg", R.drawable.cuerpo5, "twin_triste.jpg");
	        break;
		case 5:
		 	combinaImagenes("/sdcard/Mis archivos/smile.jpg", R.drawable.cuerpo6, "twin_feliz.jpg");
		 	combinaImagenes("/sdcard/Mis archivos/angry.jpg", R.drawable.cuerpo6, "twin_enojado.jpg");
		 	combinaImagenes("/sdcard/Mis archivos/sad.jpg", R.drawable.cuerpo6, "twin_triste.jpg");
	        break;
		}
		
		itSgte=new Intent(this,MenuTareas.class);
		startActivity(itSgte);
		finish();
	}
	
	private void combinaImagenes(String pathFaceImg, int idDrawab, String nombre){
		final Resources res = this.getResources();
        Bitmap cuerpoImage = BitmapFactory.decodeResource(res, idDrawab);
        Bitmap faceImage = BitmapFactory.decodeFile(pathFaceImg);
        faceImage = Bitmap.createScaledBitmap(faceImage, (cuerpoImage.getWidth()*3)/4, (cuerpoImage.getHeight()*95)/100, true);

        Bitmap bmOverlay = Bitmap.createBitmap(cuerpoImage.getWidth(), faceImage.getHeight() + cuerpoImage.getHeight(), Bitmap.Config.ARGB_8888);
        
        Canvas comboImage = new Canvas(bmOverlay);
        Paint p = new Paint(); p.setColor(Color.WHITE);
        comboImage.drawBitmap(faceImage, cuerpoImage.getWidth()/10, 0, p);
        comboImage.drawBitmap(cuerpoImage, 0,faceImage.getHeight(), p);
        
        OutputStream os = null;
        try {
            os = new FileOutputStream("/sdcard/Mis archivos/" + nombre);
            bmOverlay.compress(CompressFormat.JPEG, 80, os);
        } catch(IOException e) {
            e.printStackTrace();
        }
	}
	
	private Bitmap muestraMyTwin(int idDrawab){
		final Resources res = this.getResources();
        Bitmap cuerpoImage = BitmapFactory.decodeResource(res, idDrawab);
        Bitmap faceImage = BitmapFactory.decodeFile("/sdcard/Mis archivos/smile.jpg");
        faceImage = Bitmap.createScaledBitmap(faceImage, (cuerpoImage.getWidth()*3)/4, (cuerpoImage.getHeight()*95)/100, true);

        Bitmap bmOverlay = Bitmap.createBitmap(cuerpoImage.getWidth(), faceImage.getHeight() + cuerpoImage.getHeight(), Bitmap.Config.ARGB_8888);
        
        Canvas comboImage = new Canvas(bmOverlay);
        comboImage.drawBitmap(faceImage, cuerpoImage.getWidth()/10, 0, null);
        comboImage.drawBitmap(cuerpoImage, 0,faceImage.getHeight(), null);
        return bmOverlay;
	}
	
	
}