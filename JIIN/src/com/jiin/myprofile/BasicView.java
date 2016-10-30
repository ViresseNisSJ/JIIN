package com.jiin.myprofile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jiin.R;

public class BasicView extends FrameLayout{

	public BasicView(Context context) {
		super(context);
		init();
	}

	ImageView pictureView;
	Pictures mData;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_basic_pictures, this);
		
		pictureView = (ImageView)findViewById(R.id.imageView1);
		
	}
	
	public void setItemData(Pictures data) {
		mData = data;
		
		if(data.type==0){
			pictureView.setImageResource(R.drawable.ic_plus_item);
		}else if(data.type==1){
			pictureView.setImageResource(R.drawable.ic_photo_guide);
		}else{
			Bitmap bm3 = BitmapFactory.decodeFile(data.pics);
			pictureView.setImageBitmap(bm3);
		}
		
		
	}
}
