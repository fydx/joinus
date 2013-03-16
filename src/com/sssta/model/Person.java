package com.sssta.model;

import java.util.List;


import java.util.List;


public class Person {
	String id;	//个人用户名
	String name;//名称
	String pwd;//密码
	String sex; //为"男"或"女"
	String tel=null;//电话
	String qq=null; //qq号码
	String email=null;//电子邮箱
	
	public Person(){}
	
	public Person(String idString,String nameString,String pwdString,String sexString,String telString,String qqsString,String emailString)
	{
		id=idString;
		name=nameString;
		pwd=pwdString;
		sex=sexString;
		tel=telString;
		qq=qqsString;
		email=emailString;
	}
	
	public Person(String [] propertiesStrings)
	{
		this(propertiesStrings[0],propertiesStrings[1],propertiesStrings[2],propertiesStrings[3],propertiesStrings[4],propertiesStrings[5],propertiesStrings[6]);
	}
}
