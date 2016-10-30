package com.jiin.myprofile.qna;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jiin.R;

public class QnaView extends FrameLayout{

	TextView question;
	QnaListItem mData;
	
	public QnaView(Context context) {
		super(context);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_qna, this);
		
		question = (TextView)findViewById(R.id.text_question);
		
	}
	
	public void setItemData(QnaListItem qna) {
		mData = qna;
		
		question.setText(qna.question);
	}
	

}
