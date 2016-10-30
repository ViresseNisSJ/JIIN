package com.jiin.mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiin.R;
import com.jiin.myprofile.qna.QnaListItem;

public class MyQnaView extends FrameLayout {
	
	public MyQnaView(Context context) {
		super(context);
		init();
	}
	
	
	TextView questionView, myView, yourView;
	ImageView myprofile;
	LinearLayout layoutAnswer;
	QnaListItem mData;
	String color = "#FF0000";
	
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_my_qna, this);
		
		questionView = (TextView)findViewById(R.id.text_question);
		myView = (TextView)findViewById(R.id.text_myAnswer);
		layoutAnswer = (LinearLayout)findViewById(R.id.layout_answer);
		myprofile = (ImageView)findViewById(R.id.image_me);
		
	}
	
	public void setItemData(QnaListItem data) {
		mData = data;
		questionView.setText(data.question);
		int a = data.myAnswer;
		
		if(a== -1){
			layoutAnswer.setVisibility(View.GONE);
		}else{
			myView.setText(data.answers.get(a));
			
		}
		
	}

}
