package com.sssta.joinus;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class activity_login extends Activity {
	private String username;
	private String password;
	private EditText user;
	private EditText pass;
	private Button login, cancel;
	private Boolean enter;
	private SharedPreferences use;
	private joinus_init init;
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_for_account);
	/*	user = (EditText) findViewById(R.id.account);
		pass = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login);
		cancel = (Button) findViewById(R.id.cancel);
		use=getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
		//pas=getApplicationContext().getSharedPreferences("pass", MODE_PRIVATE);
		username=use.getString("user1", "");
		password=use.getString("pass1", "");
		user.setText(username);
		pass.setText(password);
		init = (joinus_init) getApplication();
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				username = user.getText().toString();
				Log.i("username",username);
				password = pass.getText().toString();
				Log.i("password", password);
			
				enter = Club.AccountValid(username, password);
				Log.i("enter", String.valueOf(enter));
				if (enter == true) {
					Intent intent = new Intent();
				
					intent.setClass(activity_login.this, myActivity.class);
					intent.putExtra("club",username);
					Editor editor_user=use.edit();
				//	Editor editor_pass=pas.edit();
					editor_user.putString("user1", username);
					editor_user.putString("pass1", password);
					editor_user.commit();
					init.setLogin(true);
					init.setClub(username);
				//	editor_pass.commit();
				
					startActivity(intent);
					finish(); 
				} 
				else {
					Toast.makeText(getApplicationContext(), "账号或密码不正确",
							Toast.LENGTH_SHORT).show();
				}
			}
		});*/
    
	}
}
