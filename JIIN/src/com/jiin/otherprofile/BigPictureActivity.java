package com.jiin.otherprofile;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;

import com.jiin.JiinConstant;
import com.jiin.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class BigPictureActivity extends ActionBarActivity{
	
	ImageView back;
	PhotoView picture;
	String pic;
	
	PhotoViewAttacher mAttacher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bigpicture);
		
		
		
		pic = getIntent().getStringExtra("pic");
		
		picture = (PhotoView)findViewById(R.id.image_picture);
		back = (ImageView)findViewById(R.id.image_x);
		
		ImageLoader.getInstance().displayImage(JiinConstant.SERVER_URL+pic, picture);
		
		mAttacher = new PhotoViewAttacher(picture);
//		mAttacher.setScaleType(ScaleType.FIT_XY);
		
		
		
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
