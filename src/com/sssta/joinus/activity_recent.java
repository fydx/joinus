package com.sssta.joinus;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class activity_recent extends Activity{
	private TextView textView;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        textView= (TextView)findViewById(R.id.textView1);
        
        
    }
}
