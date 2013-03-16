package com.sssta.model;

public class ConstValue {
	public static String DivFieldString="%field%";
	public static String DivRowString="%row%";
	public static String EndString="%end%";
	public static int BufferSIze=1024;
	public static String Sure="sure";
	public static byte[] Surebytes=Sure.getBytes();
	public static int Suresize=Sure.length();
	public static String MakeString(String s)
	{
		return "'"+s+"'";
	}
	public static Boolean IsQuery(String cmd)
	{
		if(cmd.contains("select"))
			return true;
		else return false;
	}
}
