package com.example.my.twin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

public class hilo extends Thread{

	
	Context context = null;
	
	
	
	public hilo(Context context) {
		super();
		this.context = context;
	}



	public Context getContext() {
		return context;
	}



	public void setContext(Context context) {
		this.context = context;
	}
	
}