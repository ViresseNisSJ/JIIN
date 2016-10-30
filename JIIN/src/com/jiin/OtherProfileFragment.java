package com.jiin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiin.otherprofile.OtherProfileActivity;
import com.jiin.start.PropertyManager;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OtherProfileFragment extends Fragment {
	
	ImageView imageView;
	TextView ageView,areaView, jobView;
	User item;
	String requestId;
	//OtherlistAdapter olAdapter;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getArguments();
		item = (User)b.getSerializable("profile");
		
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view= inflater.inflate(R.layout.item_otherlist, container, false);
		
		
		imageView = (ImageView)view.findViewById(R.id.image_otherprofile);
		ImageLoader.getInstance().displayImage("http://52.68.174.78"+item.mainPicture, imageView);
		imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getActivity(), OtherProfileActivity.class);
				intent.putExtra("user", item);
				intent.putExtra("relation", MyUserListData.getInstance().getUserList(item, item.relationCount));
				getParentFragment().startActivityForResult(intent, MainFragment.MAIN_PROFILE_REQUEST_CODE);
				getActivity().overridePendingTransition(R.anim.slide_bottom_in, R.anim.fade_out);
						
			}
		});
		
		ageView = (TextView)view.findViewById(R.id.text_otherage);
		ageView.setText(""+item.age);
		areaView = (TextView)view.findViewById(R.id.text_otherarea);
		areaView.setText(item.area);
		jobView = (TextView)view.findViewById(R.id.text_otherjob);
		jobView.setText(item.job);
		
		
		return view;
	}

}
