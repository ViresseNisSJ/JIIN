package com.jiin.otherprofile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jiin.JiinConstant;
import com.jiin.R;
import com.jiin.User;
import com.nostra13.universalimageloader.core.ImageLoader;



public class OtherBasicinfoFragment extends Fragment{
	
	ImageView profileView, backView;
	TextView nickView, ageView, areaView, jobView, univView, nickView2;
	GridView layoutPic;
	ScrollView scroll;
	User user;
	OtherBasicinfoAdapter mAdpater;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_other_basicinfo, container, false);
		
		mAdpater = new OtherBasicinfoAdapter();
		
		profileView = (ImageView)view.findViewById(R.id.image_profile);
		profileView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), BigPictureActivity.class);
				intent.putExtra("pic", user.mainPicture);
				startActivity(intent);
			}
		});
		backView = (ImageView)view.findViewById(R.id.image_other_background);
		backView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), BigPictureActivity.class);
				intent.putExtra("pic", user.background);
				startActivity(intent);
			}
		});
		nickView = (TextView)view.findViewById(R.id.text_otherinfo_nickname);
		nickView2 = (TextView)view.findViewById(R.id.text_nickName);
		ageView = (TextView)view.findViewById(R.id.text_otherinfo_age);
		areaView = (TextView)view.findViewById(R.id.text_otherinfo_area);
		jobView = (TextView)view.findViewById(R.id.text_otherinfo_job);
		univView = (TextView)view.findViewById(R.id.text_otherinfo_univ);
		
		scroll = (ScrollView)view.findViewById(R.id.scroll_other_basic);
		
		layoutPic = (GridView)view.findViewById(R.id.gridView1);
		layoutPic.setAdapter(mAdpater);
		layoutPic.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(), BigPictureActivity.class);
				intent.putExtra("pic", user.pictures.get(position));
				startActivity(intent);
			}
		});
		
		Intent intent = getActivity().getIntent();
		user = (User)intent.getSerializableExtra("user");
		
		initData();
		
		scroll.post(new Runnable() {
		      public void run() {
		    	  scroll.scrollTo(0, 0);
		      }
		  });
		return view;
 
    }
    
    
    private void initData() {

		ImageLoader.getInstance().displayImage(JiinConstant.SERVER_URL+user.mainPicture, profileView);
		ImageLoader.getInstance().displayImage(JiinConstant.SERVER_URL+user.background, backView);
		nickView.setText(user.nickName);
		ageView.setText(""+user.age);
		areaView.setText(user.area);
		jobView.setText(user.job);
		univView.setText(user.university);
		nickView2.setText(user.nickName);
		
		mAdpater.addAll(user.pictures);
		
		
	}


	@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      

    }


	
}
