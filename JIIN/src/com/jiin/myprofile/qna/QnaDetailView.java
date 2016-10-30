package com.jiin.myprofile.qna;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jiin.R;

public class QnaDetailView extends FrameLayout{

	TextView answer;
	CheckBox me, you;
	QnaDetailData mData;
	int position;
	
	public QnaDetailView(Context context) {
		super(context);
		init();
	}
	
	public interface OnCheckListener {
		public void onMyChecked(View view, int position, boolean isCheck);
		public void onWishChecked(View view, int position, boolean isCheck);
	}
	OnCheckListener mListener;
	public void setOnCheckListener(OnCheckListener listener) {
		mListener = listener;
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_qna_detail, this);
		
		answer = (TextView)findViewById(R.id.text_ex);
		me = (CheckBox)findViewById(R.id.check_me);
		you = (CheckBox)findViewById(R.id.check_you);
		
		me.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (mListener != null && !isDataSet) {
					mListener.onMyChecked(QnaDetailView.this, position, isChecked);
				}
			}
		});
		
		you.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (mListener != null && !isDataSet) {
					mListener.onWishChecked(QnaDetailView.this, position, isChecked);
				}
			}
		});
		
	}
	
	boolean isDataSet = false;
	public void setItemData(String ans, boolean my, boolean wish, int position) {
//		mData = data;
		answer.setText(ans);
		isDataSet = true;
		me.setChecked(my);
		if(my==true){
			Log.i("my.position",position+"");
		}
		you.setChecked(wish);
		if(wish==true){
			Log.i("you.position",position+"");
		}
		this.position = position;
		isDataSet = false;
	}
	

}
