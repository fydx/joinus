package com.sssta.joinus;

import android.app.Application;

public class joinus_init extends Application {
	private boolean login;
	private String club;
	private boolean firstboot_my;
	public boolean isFirstboot_my() {
		return firstboot_my;
	}

	public void setFirstboot_my(boolean firstboot_my) {
		this.firstboot_my = firstboot_my;
	}

	public boolean isLogin() {
		return login;
	}
	
	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}
	@Override 
    public void onCreate() { 
        // TODO Auto-generated method stub 
        super.onCreate(); 
        setLogin(false); //初始化全局变量 
        setClub(null);
        setFirstboot_my(true);
    }    
}
