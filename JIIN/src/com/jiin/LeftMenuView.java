package com.jiin;

import java.text.AttributedCharacterIterator.Attribute;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class LeftMenuView extends FrameLayout{

	public LeftMenuView(Context context) {
		super(context);
		init();
	}
	
	public LeftMenuView(Context context, Attribute attrs) {
		super(context);
		init();
	}
	
	TextView menuNameView;
	ImageView menuIconView;
	LeftMenuItem mData;
	
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_leftmenu, this);
		
		menuNameView = (TextView)findViewById(R.id.text_menu);
		menuIconView = (ImageView)findViewById(R.id.image_menu);
		
	}
	public void setItemData(LeftMenuItem item) {
		mData = item;
		menuIconView.setImageResource(item.image);
		menuNameView.setText(item.name);
	}

}
