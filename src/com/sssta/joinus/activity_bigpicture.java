package com.sssta.joinus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class activity_bigpicture extends Activity{
	private ImageView poster_big;
	private int activity_id;
	private Bitmap posterBitmap;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bigpicture);
		 Intent intent= getIntent();
		 activity_id = intent.getIntExtra("activity_id", 1);
		 poster_big = (ImageView)findViewById(R.id.poster_big);
		 posterBitmap=BitmapFactory.decodeFile("/mnt/sdcard/"+String.valueOf(activity_id)+".jpg");
		 poster_big.setImageBitmap(posterBitmap);
		 poster_big.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
