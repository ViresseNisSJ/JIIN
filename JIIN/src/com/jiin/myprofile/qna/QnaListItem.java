package com.jiin.myprofile.qna;

import java.io.Serializable;
import java.util.ArrayList;

public class QnaListItem implements Serializable{

	
	private static final long serialVersionUID = 907811729072354985L;
	public String question_id;
	public String question;
	public ArrayList<String> answers;
	public int myAnswer;
	public ArrayList<Integer> wishAnswer;
	
	
	public void setQnA(int myAnswer, ArrayList<Integer> wishAnswer) {
		
		this.myAnswer = myAnswer;
		this.wishAnswer.clear();
		this.wishAnswer.addAll(wishAnswer);
	}
	
}
