package com.jiin;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


public class OtherlistView extends FrameLayout{

	public OtherlistView(Context context) {
		super(context);
		init();
	}
	
	ImageView profileView;
	TextView ageView, areaView, jobView;
	User mItem;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_otherlist, this);
		profileView = (ImageView)findViewById(R.id.image_otherprofile);
		ageView = (TextView)findViewById(R.id.text_otherage);
		areaView = (TextView)findViewById(R.id.text_otherarea);
		jobView = (TextView)findViewById(R.id.text_otherjob);
		
	}
//	public void setItemData(OtherProfileItem otherProfileItem) {
//		// TODO Auto-generated method stub
//		
//	}
	
}
