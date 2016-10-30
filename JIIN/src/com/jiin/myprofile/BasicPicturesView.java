package com.jiin.myprofile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jiin.R;

public class BasicPicturesView extends FrameLayout{

	ImageView image;
	BasicPicturesItem mData;
	
	public BasicPicturesView(Context context) {
		super(context);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_basic, this);
		
		image = (ImageView)findViewById(R.id.image_pictures);
		image.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
	}
	

}
