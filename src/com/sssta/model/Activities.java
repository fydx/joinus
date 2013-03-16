package com.sssta.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

import com.sssta.joinus.SocketClient;
public class Activities implements Serializable {
	public int id; //���id(��Ϊ����,��ʵ������)
	public String name; //�����
	public String content; //�����
	public String publisher_id; //�������id ��ͨ��������id�ҵ�������
	public String position; //����еص�
	public int love_count; //ϲ���������
	public int join_count; //�μӻ������
	public String time; //��ľٰ�ʱ��
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPublisher_id() {
		return publisher_id;
	}
	public void setPublisher_id(String publisher_id) {
		this.publisher_id = publisher_id;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getLove_count() {
		return love_count;
	}
	public void setLove_count(int love_count) {
		this.love_count = love_count;
	}
	public int getJoin_count() {
		return join_count;
	}
	public void setJoin_count(int join_count) {
		this.join_count = join_count;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	static private List<Activities> GetActivitiesList(String cmd)
	{
		String AllActivities=SocketClient.Execute(cmd);
		String[] ActivitiesListStrings=AllActivities.split(ConstValue.DivRowString);
		List<Activities> ActivitiesList=new ArrayList<Activities>();
		for (String string : ActivitiesListStrings) {
			String[] PropertyList=string.split(ConstValue.DivFieldString);
			Activities activities=new Activities(PropertyList);
			ActivitiesList.add(activities);
		}
		return ActivitiesList;
	}

	

	public Activities(){}
	public Activities(String idString,String nameString,String contentString,String publisher_idString,String positionString,String love_countString,String join_countString,String timeString)
	{
		id=Integer.parseInt(idString);
		name=nameString;
		content=contentString;
		publisher_id=publisher_idString;
		position=positionString;
		love_count=Integer.parseInt(love_countString);
		join_count=Integer.parseInt(join_countString);
		time=timeString;
	}
	
	@Override
	public String toString() {
		String string= "values("
				+id+","
				+"'"+name+"'"+","
				+"'"+content+"'"+","
				+"'"+publisher_id +"'"+","
				+"'"+position+"'"+","
				+love_count+","
				+join_count+",";
		if(time!=null)
			string+="'"+time+"'"
				+")";
		else 
			string+="NULL"+")";
		return string;
	}
	
	public Activities(String [] propertiesStrings)
	{
		this(propertiesStrings[0],propertiesStrings[1],propertiesStrings[2],propertiesStrings[3],propertiesStrings[4],propertiesStrings[5],propertiesStrings[6],propertiesStrings[7]);
	}
	
	//��ȡ���л
	static public List<Activities> GetAllActivities()
	{
		/*String AllActivities=SocketClient.Execute("select * from activities");
		 Log.i("execute", AllActivities);
		String[] ActivitiesListStrings=AllActivities.split(ConstValue.DivRowString);
		List<Activities> ActivitiesList=new ArrayList<Activities>();
		for (String string : ActivitiesListStrings) {
			String[] PropertyList=string.split(ConstValue.DivFieldString);
			Activities activities=new Activities(PropertyList);
			ActivitiesList.add(activities);
		}
		return ActivitiesList;*/
		return GetActivitiesList("select * from activities");
	}
	
	//��ȡ�ӵ�index��ʼ��count���
	static public List<Activities> GetAllActivities(int index,int count)
	{
		/*String AllActivities=SocketClient.Execute("select * from activities limit "+index+","+num);

		String[] ActivitiesListStrings=AllActivities.split(ConstValue.DivRowString);
		List<Activities> ActivitiesList=new ArrayList<Activities>();
		for (String string : ActivitiesListStrings) {
			String[] PropertyList=string.split(ConstValue.DivFieldString);
			Activities activities=new Activities(PropertyList);
			ActivitiesList.add(activities);
		}
		return ActivitiesList;*/
		index--;
		return GetActivitiesList("select * from activities limit "+index+","+count);
	}
	
	//ͨ���id�ҵ��
	static public Activities FindActivities(int id)
	{
		String activitiesString=SocketClient.Execute("select * from activities where activities_id = "+id);
		String[] propertiesStrings=activitiesString.split(ConstValue.DivFieldString);
		Activities activities=new Activities(propertiesStrings);
		return activities;
	}
	
	//ͨ��������ҵ��
	static public List<Activities> FindActivitiesByName(String name)
	{
		/*String AllActivitiesString=SocketClient.Execute("select * from activities where activities_name = "+ConstValue.MakeString(name));
		String[] activitiesStrings=AllActivitiesString.split(ConstValue.DivRowString);
		List<Activities> activitiesList=new ArrayList<Activities>();
		for (String string : activitiesStrings) {
			String[] propertiesString=string.split(ConstValue.DivFieldString);
			Activities activities=new Activities(propertiesString);
			activitiesList.add(activities);
		}
		return activitiesList;*/
		return GetActivitiesList("select * from activities where activities_name like \"%"+name+"%\"");
	}
	
	//��ӵ����
	//�ɹ����ػid ʧ�ܷ���-1
	static public int AddActivities(Activities activities)
	{
		try{
		SocketClient.Execute("insert into activities "+activities.toString());
		String idString=SocketClient.Execute("select activities_id from activities where activities_publisher_id = "
				+ConstValue.MakeString(activities.publisher_id)
				+" AND "
				+"activities_name = "
				+ConstValue.MakeString(activities.name));
		int index=idString.indexOf(ConstValue.DivFieldString);
		idString=idString.substring(0, index);
		return Integer.parseInt(idString);
		}
		
		catch (Exception e) {
			return -1;
		}
	}
	
	//�ϴ�ͼƬ
	//�ɹ��򷵻�true
	static public Boolean UploadPic(int id,String ImagePath)
	{
		return SocketClient.UploadImage(id,ImagePath);
	}
	
	//��ȡͼƬ
	//����ͼƬ��·��
	static public String GetImage(int ActivitiesId) 
	{
		try {
			String imagePathString;
			imagePathString = "/"+ActivitiesId+ ".jpg";
			File file = new File(Environment.getExternalStorageDirectory()+imagePathString);
			if(file.exists())
				 return file.getAbsolutePath();
			else return SocketClient.DownloadImage(ActivitiesId);
		} catch (Exception e) {
			return null;
		}
	}
	
	//ɾ���
	static public Boolean DelActivities(int id)
	{
		try {
			SocketClient
					.Execute("delete from activities where activities_id = "
							+ id);
			String imagePathString;
			imagePathString = "/"+id+ ".jpg";
			File file = new File(Environment.getExternalStorageDirectory()+imagePathString);
			file.delete();
			return true;
		} catch (Exception e) {
			return false;
		}
	
	}
}