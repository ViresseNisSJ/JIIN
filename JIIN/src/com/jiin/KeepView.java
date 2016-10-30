package com.jiin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class KeepView extends FrameLayout{

	public KeepView(Context context) {
		super(context);
		init();
	}
	
	public KeepView(Context context, AttributeSet attrs) {
		super(context,attrs);
		init();
	}
	
	public interface OnKeepClickListener {
		public void onProfileClicked(KeepView keepView, User mData);
		public void onLikeClicked(KeepView keepView, User mData);
		public void onPassClicked(KeepView keepView, User mData);
	}
	OnKeepClickListener mListener;	
	public void setOnKeepClickListener(OnKeepClickListener listener) {
		mListener = listener;
	}
	

	TextView nameView, areaView, ageView, jobView;
	ImageView profileView;
	User mData;

	private void init() {
		
		LayoutInflater.from(getContext()).inflate(R.layout.item_keep, this);
		nameView = (TextView)findViewById(R.id.text_name);
		areaView = (TextView)findViewById(R.id.text_area);
		ageView = (TextView)findViewById(R.id.text_age);
		jobView = (TextView)findViewById(R.id.text_job);
		profileView = (ImageView)findViewById(R.id.image_profile);
		profileView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mListener != null){
					mListener.onProfileClicked(KeepView.this, mData);
				}
			}
		});
		Button btn_like = (Button)findViewById(R.id.btn_like);
		btn_like.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mListener != null){
					mListener.onLikeClicked(KeepView.this, mData);
				}
			}
		});
		Button btn_pass = (Button)findViewById(R.id.btn_pass);
		btn_pass.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mListener != null){
					mListener.onPassClicked(KeepView.this, mData);
				}
			}
		});		
	}
	
	public void setItemData(User user) {
		mData = user;
		ImageLoader.getInstance().displayImage("http://52.68.174.78"+user.mainPicture, profileView);
		nameView.setText(user.nickName);
		areaView.setText(user.area);
		ageView.setText(user.age+"");
		jobView.setText(user.job);
	}
	
	
}
