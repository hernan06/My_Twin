package com.example.my.twin;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class NlistaTareas extends Activity {

	static int cont=1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nlista_tareas);
        
        if(getIntent().getExtras()!=null){
        	Bundle bundle=getIntent().getExtras();        	
        if(bundle.getInt("agregar")!=0){
        	cont++;        	
        }
        
        RadioGroup rg=(RadioGroup) findViewById(R.id.rdTareas);        
        RadioButton button;
        for(int i = 1; i < cont; i++) {
            button = new RadioButton(this);
            button.setText("Tarea" + i);
            rg.addView(button);
        }
        
        }
        
        if(getIntent().getExtras()!=null){
        	Bundle bundle=getIntent().getExtras();
        if(bundle.getInt("agregar")!=0){
        	finish();        	
        }
        
        Button back3=(Button) findViewById(R.id.back3);
        Button add2=(Button) findViewById(R.id.add3);
        
        back3.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				finish();				
			}
		});
        add2.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent=new Intent(NlistaTareas.this,Nueva_Tarea.class);
				startActivity(intent);
				finish();
			}
		});
        
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_nlista_tareas, menu);
        return true;
    }
}
