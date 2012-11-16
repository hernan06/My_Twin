package com.example.my.twin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


public class Create2 extends Activity implements OnClickListener {
ImageView img,cuerpo1,cuerpo2,cuerpo3;
Button btSigte;
Intent itSgte;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create2);
		img=(ImageView)findViewById(R.id.principal);
        cuerpo1=(ImageView)findViewById(R.id.cp1);
        cuerpo2=(ImageView)findViewById(R.id.cp2);
        cuerpo3=(ImageView)findViewById(R.id.cp3);
        btSigte=(Button) findViewById(R.id.btn_sgte);
        btSigte.setOnClickListener(this);
        cuerpo1.setOnClickListener(this);
        cuerpo2.setOnClickListener(this);
        cuerpo3.setOnClickListener(this);
	}

	public void onClick(View v) {
		
		switch (v.getId()){
		case R.id.cp1:
			img.setImageResource(R.drawable.cuerpo1);
			break;
		case R.id.cp2:
			img.setImageResource(R.drawable.cuerpo2);
			break;
		case R.id.cp3:
			img.setImageResource(R.drawable.cuerpo3);
			break;
		case R.id.btn_sgte:
			Siguiente();
			break;
		}
		
		
	}

	private void Siguiente() {
		itSgte=new Intent(this,MenuTareas.class);
		startActivity(itSgte);
	}
	
}