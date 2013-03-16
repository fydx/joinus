package com.sssta.model;

import java.util.ArrayList;
import java.util.List;

import com.sssta.joinus.SocketClient;

public class Club {
	String id;// 社团的用户名
	String name;// 社团的名称
	String pwd;// 社团账户密码
	String description;// 社团的介绍

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Club() {

	}

	public Club(String idString, String nameString, String pwdString,
			String descriptionString) {
		id = idString;
		name = nameString;
		pwd = pwdString;
		description = descriptionString;
	}

	public Club(String[] propertiesStrings) {
		this(propertiesStrings[0], propertiesStrings[1], propertiesStrings[2],
				propertiesStrings[3]);
	}

	// 获取所有社团
	public static List<Club> GetAllClub() {
		String AllClubString = SocketClient.Execute("select * from club");
		String[] ClubStrings = AllClubString.split(ConstValue.DivRowString);
		List<Club> clubList = new ArrayList<Club>();
		for (String string : ClubStrings) {
			String[] fieldsstrings = string.split(ConstValue.DivFieldString);
			Club club = new Club(fieldsstrings);
			clubList.add(club);
		}
		return clubList;
	}

	// 根据社团ID获取社团
	public static Club GetClub(String idString) {
		String clubString = SocketClient
				.Execute("select * from club where club_id = "
						+ ConstValue.MakeString(idString));
		String[] fieldStrings = clubString.split(ConstValue.DivFieldString);
		return new Club(fieldStrings);
	}

	// 根据社团名称获取社团
	public static Club GetClubByName(String clubnameString) {
		String clubString = SocketClient
				.Execute("select * from club where club_name = "
						+ ConstValue.MakeString(clubnameString));
		String[] propertiesStrings = clubnameString
				.split(ConstValue.DivFieldString);
		return new Club(propertiesStrings);
	}

	// 验证账号信息是否正确
	public static Boolean AccountValid(Club club) {
		return AccountValid(club.id, club.pwd);
	}

	// 验证账号信息是否正确
	public static Boolean AccountValid(String id, String pwd) {
		try {
			String Accountstring = SocketClient
					.Execute("select * from club where club_id = "
							+ ConstValue.MakeString(id));
			if (Accountstring.isEmpty())
				return false;
			String[] propertiesStrings = Accountstring
					.split(ConstValue.DivFieldString);
			Club tempClub = new Club(propertiesStrings);
			if (pwd.compareTo(tempClub.pwd) == 0)
				return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	// 获得该club发布的所有活动
	public static List<Activities> GetActivitiesByClub(String clubIdString) {
		String activitiesString = SocketClient
				.Execute("select * from Activities where activities_publisher_id = "
						+ ConstValue.MakeString(clubIdString));
		String[] activitiesliststring = activitiesString
				.split(ConstValue.DivRowString);
		List<Activities> activitieslist = new ArrayList<Activities>();
		for (String string : activitiesliststring) {
			Activities activities = new Activities(
					string.split(ConstValue.DivFieldString));
			activitieslist.add(activities);
		}
		return activitieslist;
	}

	// 获得该club发布的从第index开始的count个活动,index从0开始
	public static List<Activities> GetActivitiesByClub(String clubIdString,
			int index, int count) {
		index -= 1;
		String activitiesString = SocketClient
				.Execute("select * from activities where activities_publisher_id = "
						+ ConstValue.MakeString(clubIdString)
						+ " limit "
						+ index + "," + count);
		String[] activitiesliststring = activitiesString
				.split(ConstValue.DivRowString);
		List<Activities> activitieslist = new ArrayList<Activities>();
		for (String string : activitiesliststring) {
			Activities activities = new Activities(
					string.split(ConstValue.DivFieldString));
			activitieslist.add(activities);
		}
		return activitieslist;
	}

	// 获取所有活动根据参与人数从大到小排序
	public static List<Activities> GetActivitiesOrderByJoin() {
		String activitiesString = SocketClient
				.Execute("select * from activities order by activities_join_count DESC");
		String[] activitiesliststring = activitiesString
				.split(ConstValue.DivRowString);
		List<Activities> activitieslist = new ArrayList<Activities>();
		for (String string : activitiesliststring) {
			Activities activities = new Activities(
					string.split(ConstValue.DivFieldString));
			activitieslist.add(activities);
		}
		return activitieslist;
	}

	// 根据社团名字获取所属活动并根据参与人数从大到小排序
	public static List<Activities> GetActivitiesOrderByJoin(String clubIdString) {
		String activitiesString = SocketClient
				.Execute("select * from activities order by activities_join_count DESC where activities_id = "
						+ ConstValue.MakeString(clubIdString));
		String[] activitiesliststring = activitiesString
				.split(ConstValue.DivRowString);
		List<Activities> activitieslist = new ArrayList<Activities>();
		for (String string : activitiesliststring) {
			Activities activities = new Activities(
					string.split(ConstValue.DivFieldString));
			activitieslist.add(activities);
		}
		return activitieslist;
	}

	public static boolean ModifyClub(Club club)
	{
		try {
			String activitiesString = SocketClient
					.Execute("update club set club_description = "
							+ConstValue.MakeString(club.description)
							+" where club_id = "+ConstValue.MakeString(club.id));
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
}
