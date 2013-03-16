package com.sssta.joinus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.sssta.model.Activities;
import com.sssta.model.Club;

public class activity_detail extends Activity {
	private TextView nameTextView;
	private TextView timeTextView;
	private TextView placeTextView;
	private TextView detailTextView;
	private TextView clubTextView;
	private TextView aboutclubTextView;
	private TextView aboutclub_nameTextView;

	private String name,place,time,detail,clubname,clubname_1,about_club,about_club_name;
	private Bitmap posterBitmap;
	private Activities activity_1;
	private ImageView posterImageView;
	private Club club;
	 public void onCreate(Bundle savedInstanceState) {
		 	
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_detail); 
	        Intent intent= getIntent();
	       
	        activity_1=(Activities) intent.getSerializableExtra("fromMain");
	        posterBitmap=BitmapFactory.decodeFile("/mnt/sdcard/"+String.valueOf(activity_1.getId())+".jpg");
	        posterImageView=(ImageView)findViewById(R.id.poster_for_detail);
	        posterImageView.setImageBitmap(posterBitmap);
	        nameTextView=(TextView)findViewById(R.id.name_for_detail);
	        timeTextView=(TextView)findViewById(R.id.time_for_detail);
	        placeTextView=(TextView)findViewById(R.id.place_for_detail);
	        detailTextView=(TextView)findViewById(R.id.detail);
	        clubTextView=(TextView)findViewById(R.id.shetuan_for_detail);
	        aboutclubTextView=(TextView)findViewById(R.id.text_about_club);
	        aboutclub_nameTextView=(TextView)findViewById(R.id.about_club);
	
	        name=activity_1.getName();
	        place=activity_1.getPosition();
	        time=activity_1.getTime();
	        clubname_1=activity_1.getPublisher_id();
	        detail=activity_1.getContent();
	        club=Club.GetClub(clubname_1);
	        clubname = club.getName();
	        about_club_name = "¹ØÓÚ"+ " " + clubname;
	        Log.i("club_detail", club.getId());
	        about_club = club.getDescription();
	        Log.i("club_des",about_club);
	        nameTextView.setText(name);
	        timeTextView.setText(time);
	        placeTextView.setText(place);
	        detailTextView.setText(detail);
	        clubTextView.setText(clubname);
	        aboutclubTextView.setText(about_club);
	        aboutclub_nameTextView.setText(about_club_name);
	        posterImageView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent= new Intent();
					intent.setClass(activity_detail.this, activity_bigpicture.class);
					intent.putExtra("activity_id", activity_1.getId());
					startActivity(intent);
				}
			});
	        
	     
	    //    textView=(TextView)findViewById(R.id.test_detail);
	 }
	
}
