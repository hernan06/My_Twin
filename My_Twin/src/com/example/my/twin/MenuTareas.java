package com.example.my.twin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MenuTareas extends Activity {
	CharSequence items[]={"Lavar Ropa","Pasar por Fiec","Juega Barza","Recuperacion Redes"};
	boolean[] itemsChecked=new boolean[items.length];
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tareas);        
        Button back1=(Button) findViewById(R.id.back1);
        Button add1=(Button) findViewById(R.id.add1);
        Button tareasList=(Button) findViewById(R.id.tareas);
        
        
        
        tareasList.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				showDialog(0);
			}
		});
        
        add1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent=new Intent(MenuTareas.this,Tarea.class);				
				startActivity(intent);				
			}
		});
        
        back1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Regresar();			
				finish();				
			}

		});
    }
    

	private void Regresar() {
		Intent itRegresar=new Intent(this,Create2.class);
		startActivity(itRegresar);
		
	}
    @Override
    protected Dialog onCreateDialog(int id){
    	switch(id){
    	case 0:
    		return new AlertDialog.Builder(this)
    		.setIcon(R.drawable.ic_tareas)
    		.setTitle("Lista de Tareas")
    		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(getBaseContext(),"Ok", Toast.LENGTH_SHORT).show();
					
				}
			})
			.setMultiChoiceItems(items, itemsChecked, new DialogInterface.OnMultiChoiceClickListener() {
				
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					Toast.makeText(getBaseContext(),items[which] + (isChecked ? " Realizado":""),Toast.LENGTH_SHORT).show();
					
				}
			})
			.create();
    	}
    	return null;
    }    
}
