package com.sssta.model;

import java.util.List;


import java.util.List;


public class Person {
	String id;	//�����û���
	String name;//����
	String pwd;//����
	String sex; //Ϊ"��"��"Ů"
	String tel=null;//�绰
	String qq=null; //qq����
	String email=null;//��������
	
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
