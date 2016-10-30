package com.jiin.menu.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiin.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MessageReceiveView extends FrameLayout{

	public MessageReceiveView(Context context) {
		super(context);
		init();
	}

	TextView contentView;
	LinearLayout layout;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_receive, this);
		contentView = (TextView)findViewById(R.id.text_content);
		layout = (LinearLayout)findViewById(R.id.layout_receive);
		layout.setClickable(false);
	}

	public void setData(MessageContentItem data) {
		contentView.setText(data.content);
	}
	
	
}
