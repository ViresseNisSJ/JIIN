package com.jiin;

import java.io.Serializable;
import java.util.ArrayList;

import com.jiin.myprofile.qna.QnaListItem;

public class User implements Serializable{

	private static final long serialVersionUID = 1108627403015803261L;
	public int relationCount;
	
	public String _id;
	public String userId;
	public String pw;
	public String nickName;
	public String gender;
	public int age;
	public String height;
	public int bodyType;
	public int drinking;
	public int smoke;
	public int religion;
	public ArrayList<Integer> characters;
	public ArrayList<Integer> hobbies;
	public String area;
	public String job;
	public String university;
	public String mainPicture;
	public String background;
	public ArrayList<String> pictures;
	public int point;
	public String parentId;
	public String accDate;
	public ArrayList<User> children = new ArrayList<User>();
	public User parent;
	
	public String univProof;
	public String jobProof;
	public String checkPublic;
	
	public int position;
	
	public ArrayList<QnaListItem> qna;
	
	
	
}
