package com.example.my.twin;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Principal extends Activity{
	Intent Cambio;
	Button entrada;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        
        entrada=(Button)findViewById(R.id.button1);
        entrada.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inicio();
				finish();
			}
		});
       
    }
    public void inicio(){
    	Cambio = new Intent(this,Create1.class);
    	startActivity(Cambio);
    }

}
