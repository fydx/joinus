package com.sssta.joinus;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class welcome extends Activity {
	public boolean connect=true;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
		setContentView(R.layout.welcome);
		final Intent intent2 = new Intent(welcome.this,
				MainActivity.class);
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				startActivity(intent2);
				finish(); // 执行
			}
		};
		//connect=SocketClient.isconnected();
		if(connect== false)
		{
			Intent intent = new Intent();
			intent.setClass(welcome.this, CannotConnectServer.class);
			startActivity(intent);
			finish();
		}
		else 
		{
		timer.schedule(task, 1000 * 2);
		}// 2秒后

	}
}
