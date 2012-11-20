package com.example.my.twin;


import java.io.File;
import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class Create1 extends Activity implements OnClickListener{
	private Uri mImageCaptureUri;
	Button btSmile,btAngry,btSad,regresar,sgte;
	ImageView imgSmile,imgAngry,imgSad;
	Bitmap photo;
	Intent itRegresar,itSiguiente;
	
	private static final int PICK_FROM_CAMERA = 1;
	private static final int CROP_FROM_CAMERA = 2;
	private static final int PICK_FROM_FILE = 3;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create1);
        
        final String [] items			= new String [] {"Camara", "Galeria"};				
		ArrayAdapter<String> adapter	= new ArrayAdapter<String> (this, android.R.layout.select_dialog_item,items);
		AlertDialog.Builder builder		= new AlertDialog.Builder(this);
		
		builder.setTitle("Seleccion de Imagen");
		builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
			public void onClick( DialogInterface dialog, int item ) { 
				if (item == 0) {
					Intent intent 	 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					
					mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
									   "Avatar" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

					intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

					try {
						
						intent.putExtra("return-data", true);
						startActivityForResult(intent, PICK_FROM_CAMERA);
						
					} catch (ActivityNotFoundException e) {
						e.printStackTrace();
					}
				} else {
					Intent intent = new Intent();
					
	                intent.setType("image/*");
	                intent.setAction(Intent.ACTION_GET_CONTENT);
	                
	                startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
				}
			}
		} );
		
		final AlertDialog dialog = builder.create();
        
        btSmile=(Button) findViewById(R.id.btn_smile);
        btAngry=(Button) findViewById(R.id.btn_angry);
        btSad=(Button) findViewById(R.id.btn_sad);
        regresar=(Button) findViewById(R.id.btn_regresar);
        sgte=(Button) findViewById(R.id.btn_sgte);
        
        
        imgSmile=(ImageView) findViewById(R.id.img_smile);
        imgAngry=(ImageView) findViewById(R.id.img_angry);
        imgSad=(ImageView) findViewById(R.id.img_sad);
        
        btSmile.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				dialog.show();
				imgSmile.setImageBitmap(photo);
			}
		});
        
        btAngry.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				dialog.show();
				imgAngry.setImageBitmap(photo);
			}
		});
        btSad.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				dialog.show();
				imgSad.setImageBitmap(photo);
			}
		});
        
        
        
        regresar.setOnClickListener(this);
        sgte.setOnClickListener(this);
        
        
    }

	public void onClick(View v) {
		switch(v.getId()){
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
	    if (resultCode != RESULT_OK) return;
	   
	    switch (requestCode) {
		    case PICK_FROM_CAMERA:
		    	doCrop();
		    	
		    	break;
		    	
		    case PICK_FROM_FILE: 
		    	mImageCaptureUri = data.getData();
		    	
		    	doCrop();
	    
		    	break;	    	
	    
		    case CROP_FROM_CAMERA:	    	
		        Bundle extras = data.getExtras();
	
		        if (extras != null) {	        	
		            photo = extras.getParcelable("data");
		            //AQUIII
		        }
		        
		        File f = new File(mImageCaptureUri.getPath());            
		        
		        if (f.exists()) f.delete();
		        
		        break;

	    }
	}
    
    private void doCrop() {
		final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
    	
    	Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        
        List<ResolveInfo> list = getPackageManager().queryIntentActivities( intent, 0 );
        
        int size = list.size();
        
        if (size == 0) {	        
        	Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();
        	
            return;
        } else {
        	intent.setData(mImageCaptureUri);
            
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            
        	if (size == 1) {
        		Intent i 		= new Intent(intent);
	        	ResolveInfo res	= list.get(0);
	        	
	        	i.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
	        	
	        	startActivityForResult(i, CROP_FROM_CAMERA);
        	} else {
		        for (ResolveInfo res : list) {
		        	final CropOption co = new CropOption();
		        	
		        	co.title 	= getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
		        	co.icon		= getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
		        	co.appIntent= new Intent(intent);
		        	
		        	co.appIntent.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
		        	
		            cropOptions.add(co);
		        }
	        
		        CropOptionAdapter adapter = new CropOptionAdapter(getApplicationContext(), cropOptions);
		        
		        AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        builder.setTitle("Choose Crop App");
		        builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
		            public void onClick( DialogInterface dialog, int item ) {
		                startActivityForResult( cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
		            }
		        });
	        
		        builder.setOnCancelListener( new DialogInterface.OnCancelListener() {
		            public void onCancel( DialogInterface dialog ) {
		               
		                if (mImageCaptureUri != null ) {
		                    getContentResolver().delete(mImageCaptureUri, null, null );
		                    mImageCaptureUri = null;
		                }
		            }
		        } );
		        
		        AlertDialog alert = builder.create();
		        alert.show();
        	}
        }
	}
    
}