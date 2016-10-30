package com.jiin.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiin.R;

public class NoticeGroupView extends FrameLayout {
	
	public NoticeGroupView(Context context) {
		super(context);
		init();
	}
	
	ImageView arrow;
	TextView titleView, dateView, contentView;
	LinearLayout noticeGroup;
	NoticeGroupData mData;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_notice, this);
		
		arrow = (ImageView)findViewById(R.id.image_arrow);
		titleView = (TextView)findViewById(R.id.text_title);
		dateView = (TextView)findViewById(R.id.text_date);
		contentView = (TextView)findViewById(R.id.text_content);
		noticeGroup = (LinearLayout)findViewById(R.id.notice_group);
		noticeGroup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(contentView.getVisibility() == View.GONE){
					contentView.setVisibility(View.VISIBLE);
					arrow.setImageResource(R.drawable.btn_arrow_down);
				}else{
					contentView.setVisibility(View.GONE);
					arrow.setImageResource(R.drawable.btn_arrow6_3);
				}
				
				
			}
		});
		
	}
	
	public void setItemData(NoticeGroupData data) {
		mData = data;
		titleView.setText(data.title);
		dateView.setText(data.regdate);
		contentView.setText(data.content);
	}

	


}
