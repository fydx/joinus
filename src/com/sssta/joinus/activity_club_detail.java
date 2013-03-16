package com.sssta.joinus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sssta.model.Club;

public class activity_club_detail extends Activity{
	private joinus_init ini;
	private Club club;
	private String detail_new;
	private Button submit;
	private EditText club_detail;
	private TextView clubname;
	private boolean submit_2;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_club_detail);
		clubname=(TextView)findViewById(R.id.name_clubdetail);
		club_detail=(EditText)findViewById(R.id.editText_detail);
		submit=(Button)findViewById(R.id.submit_club);
		ini=(joinus_init)getApplication();
		club= Club.GetClub(ini.getClub());
		clubname.setText(club.getName());
		club_detail.setText(club.getDescription());
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				detail_new = club_detail.getText().toString();
				//Ã·Ωª
				club.setDescription(detail_new);
				submit_2=Club.ModifyClub(club);
				Log.i("submit club",String.valueOf(submit_2));
				finish();
			}
		});
		
	}
}
