package com.jiin.otherprofile;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiin.R;

public class OtherQnaView extends FrameLayout {
	
	public OtherQnaView(Context context) {
		super(context);
		init();
	}
	
	
	TextView questionView, myView, yourView;
	ImageView myprofile, yourprofile;
	LinearLayout layoutAnswer;
	OtherQnaItem mData;
	OtherQNAFragment qnaF = new OtherQNAFragment();
	String color = "#2F9D27";
	String color2 = "#CC3D3D";
	
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_other_qna, this);
		
		questionView = (TextView)findViewById(R.id.text_question);
		myView = (TextView)findViewById(R.id.text_myAnswer);
		yourView = (TextView)findViewById(R.id.text_yourAnswer);
		layoutAnswer = (LinearLayout)findViewById(R.id.layout_answer);
		myprofile = (ImageView)findViewById(R.id.image_me);
		yourprofile = (ImageView)findViewById(R.id.image_you);
		
		
	}
	
	public void setItemData(OtherQnaItem data) {
		mData = data;
		questionView.setText(data.question);
		
		if(data.myAnswer.answer.equals("응답 없음") 
				|| data.yourAnswer.answer.equals("응답 없음")){
			layoutAnswer.setVisibility(View.GONE);
		}else{
			myView.setText(data.myAnswer.answer);
			if(data.myAnswer.matched == 1){
				myView.setTextColor(Color.parseColor(color));
			}else{
				myView.setTextColor(Color.parseColor(color2));
			}
			
			yourView.setText(data.yourAnswer.answer);
			if(data.yourAnswer.matched == 1){
				yourView.setTextColor(Color.parseColor(color));
			}else{
				yourView.setTextColor(Color.parseColor(color2));
			}
		}
		
	}

	


}
