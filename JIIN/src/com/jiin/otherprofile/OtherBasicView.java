package com.jiin.otherprofile;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jiin.JiinConstant;
import com.jiin.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OtherBasicView extends FrameLayout{
	
	String mData;

	public OtherBasicView(Context context) {
		super(context);
		init();
	}

	ImageView pictureView;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_other_basicpictures, this);
		
		pictureView = (ImageView)findViewById(R.id.image_pic_other);
		
	}
	
	public void setItemData(String data) {
		mData = data;
		
		ImageLoader.getInstance().displayImage(JiinConstant.SERVER_URL+data, pictureView);
		
		
	}
}
