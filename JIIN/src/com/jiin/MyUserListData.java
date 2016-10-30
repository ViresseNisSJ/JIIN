package com.jiin;

import java.util.ArrayList;


public class MyUserListData {
	
	private static MyUserListData instance;
	public static MyUserListData getInstance(){
		if (instance == null) {
			instance = new MyUserListData();
		}
		return instance;
	}
	
	private MyUserListData(){
		
	}
	private ArrayList<User> fullList = new ArrayList<User>();
	private User myProfile;
	
	
	public void setUserList(ArrayList<User> userList){
		
		fullList.clear();
		fullList.addAll(userList);
		
	}
	
	public ArrayList<User> getUserList(User other, int relationCount){
		
		ArrayList<User> relation = new ArrayList<User>();
		
		for(int i=0; i<relationCount; i++){
			User user = fullList.get(other.position);
			relation.add(user);
			other = user.parent;
			
		}
		return relation;
	}
	
	public User getKeepUser(User other){
		
		ArrayList<User> relation = new ArrayList<>();
		for(int i=0; i<fullList.size(); i++){
			User user = fullList.get(i);
			if(user._id.equals(other._id)){
				return user;
			}
		}
		return null;
	}
	
	
	public void setMe(User user){
		myProfile=user;
	}
	
	public User getMe(){
		return myProfile;
	}
}
