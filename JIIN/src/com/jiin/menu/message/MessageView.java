package com.jiin.menu.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiin.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MessageView extends FrameLayout {
	
	public MessageView(Context context) {
		super(context);
		init();
	}
	
	ImageView profileView;
	TextView ageView, areaView, jobView, nickView, contentView, dateView;
	MessageResponse mData;
	LinearLayout layout;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_message, this);
		
		profileView = (ImageView)findViewById(R.id.image_profile);
		ageView = (TextView)findViewById(R.id.text_age);
		areaView = (TextView)findViewById(R.id.text_area);
		jobView = (TextView)findViewById(R.id.text_job);
		nickView = (TextView)findViewById(R.id.text_nickName);
		contentView = (TextView)findViewById(R.id.text_message_content);
		dateView = (TextView)findViewById(R.id.text_date);
		
		
	}
	
	public void setItemData(MessageResponse data) {
		
		mData = data;
		ImageLoader.getInstance().displayImage("http://52.68.174.78"+data.receiver.mainPicture, profileView);
		ageView.setText(data.receiver.age+"");
		areaView.setText(data.receiver.area);
		jobView.setText(data.receiver.job);
		nickView.setText(data.receiver.nickName);
		contentView.setText(data.message.messages.get(data.message.messages.size()-1).content);
		String date = data.message.messages.get(data.message.messages.size()-1).sendDate;
		String date2 = date.substring(5, 10).replace("-", "¿ù");
		String date3 = date.substring(11, 16);
		String sendDate = date2.concat("ÀÏ\n").concat(date3);
		dateView.setText(sendDate);
		
	}


}
