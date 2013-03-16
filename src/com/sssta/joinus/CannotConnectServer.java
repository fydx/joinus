package com.sssta.joinus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class CannotConnectServer extends Activity {
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.cannotconnectcerver);
	       
	        
	        
	    }
	 @Override
		public void onBackPressed() {
			System.exit(0);
		}
	 
}
