package com.example.my.twin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Nueva_Tarea extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_tarea);
        Button cancel1=(Button) findViewById(R.id.back2);
        Button ok1=(Button) findViewById(R.id.ok1);
        
        cancel1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();				
			}
		});        
        
        ok1.setOnClickListener(new OnClickListener() {
			

			public void onClick(View v) {
				// TODO Auto-generated method stub				
				Toast.makeText(getBaseContext(),"Tarea Agregada", Toast.LENGTH_SHORT).show();				
				finish();
			}
		});
        
    }

}
