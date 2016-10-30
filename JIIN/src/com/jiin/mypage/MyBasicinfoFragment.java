package com.jiin.mypage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiin.JiinConstant;
import com.jiin.MyData;
import com.jiin.NetworkManager;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyBasicinfoFragment extends Fragment{
	
	ImageView profileView, backView;
	TextView nickView, ageView, areaView, jobView, univView;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_basicinfo, container, false);
		
		profileView = (ImageView)view.findViewById(R.id.image_profile);
		backView = (ImageView)view.findViewById(R.id.image_other_background);
		nickView = (TextView)view.findViewById(R.id.text_otherinfo_nickname);
		ageView = (TextView)view.findViewById(R.id.text_otherinfo_age);
		areaView = (TextView)view.findViewById(R.id.text_otherinfo_area);
		jobView = (TextView)view.findViewById(R.id.text_otherinfo_job);
		univView = (TextView)view.findViewById(R.id.text_otherinfo_univ);
		
		
		initData();
		return view;
 
    }
    
    
    private void initData() {
    	
    	NetworkManager.getInstnace().getMyInform(getActivity(), new OnResultListener<MyData>(){
			@Override
			public void onSuccess(MyData result) {
				if(result.result.equals("success")){
					ImageLoader.getInstance().displayImage(JiinConstant.SERVER_URL+result.data.mainPicture, profileView);
					ImageLoader.getInstance().displayImage(JiinConstant.SERVER_URL+result.data.background, backView);
					nickView.setText(result.data.nickName);
					ageView.setText(""+result.data.age);
					areaView.setText(result.data.area);
					jobView.setText(result.data.job);
					univView.setText(result.data.university);
				}else if(result.result.equals("fail")){
					Toast.makeText(getActivity(), result.message, Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFail(int code) {
				Toast.makeText(getActivity(), code+"", Toast.LENGTH_SHORT).show();
			}
		});

		
		
		
		
	}


	@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      

    }


	
}
