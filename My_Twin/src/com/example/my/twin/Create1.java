package com.example.my.twin;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


public class Create1 extends Activity implements OnClickListener{
	Button btSmile,btAngry,btSad,regresar,sgte;
	ImageView imgSmile,imgAngry,imgSad;
	Bitmap img,img2,img3;
	Intent itRegresar,itSiguiente;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create1);
        btSmile=(Button) findViewById(R.id.btn_smile);
        btAngry=(Button) findViewById(R.id.btn_angry);
        btSad=(Button) findViewById(R.id.btn_sad);
        regresar=(Button) findViewById(R.id.btn_regresar);
        sgte=(Button) findViewById(R.id.btn_sgte);
        
        
        imgSmile=(ImageView) findViewById(R.id.img_smile);
        imgAngry=(ImageView) findViewById(R.id.img_angry);
        imgSad=(ImageView) findViewById(R.id.img_sad);
        btSmile.setOnClickListener(this);
        btAngry.setOnClickListener(this);
        btSad.setOnClickListener(this);
        regresar.setOnClickListener(this);
        sgte.setOnClickListener(this);
        
        
    }

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_smile:
			Intent itsmile=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(itsmile,0);
			break;
		case R.id.btn_angry:
			Intent itangry=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(itangry,1);
			break;
		case R.id.btn_sad:
			Intent itsad=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(itsad,2);
			break;
		case R.id.btn_regresar:
			Regresar();
			break;
		case R.id.btn_sgte:
			Siguiente();
			break;
		}
		
	}

	private void Siguiente() {
		itSiguiente = new Intent(this,Create2.class);
		startActivity(itSiguiente);
	}

	private void Regresar() {
		itRegresar = new Intent(this,Principal.class);
		startActivity(itRegresar);		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==0){
			img=(Bitmap) data.getExtras().get("data");
			imgSmile.setImageBitmap(img);
		}
		else if(requestCode==1){
			img2=(Bitmap) data.getExtras().get("data");
			imgAngry.setImageBitmap(img2);
		}
		else{
			img3=(Bitmap) data.getExtras().get("data");
			imgSad.setImageBitmap(img3);
		}

	}
    
}