package com.jiin.otherprofile;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

import com.jiin.KeepData;
import com.jiin.LikeMessageActivity;
import com.jiin.MainFragment;
import com.jiin.MyUserListData;
import com.jiin.NetworkManager;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.R;
import com.jiin.User;
import com.jiin.otherprofile.relationship.RelationShipActivity;


public class OtherprofileFragment extends Fragment {
	
	FragmentTabHost otabHost;
	User user;
	ArrayList<User> relation = new ArrayList<User>();
	int relationCount;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view= inflater.inflate(R.layout.fragment_otherprofile, container, false);
		
		otabHost = (FragmentTabHost)view.findViewById(R.id.tabhost);
		otabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
		
		otabHost.addTab(otabHost.newTabSpec("기본정보").setIndicator(""), OtherBasicinfoFragment.class ,null);
        otabHost.addTab(otabHost.newTabSpec("부가정보").setIndicator(""), OtherOptioninfoFragment.class ,null);
        otabHost.addTab(otabHost.newTabSpec("Q&A").setIndicator(""), OtherQNAFragment.class ,null);

        otabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.btn_left_on);
        otabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.btn_m_off);
        otabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.btn_qa_off);
        
        otabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				if(tabId.equals("기본정보")){
					otabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.btn_left_on);
			        otabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.btn_m_off);
			        otabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.btn_qa_off);
				}else if(tabId.equals("부가정보")){
					otabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.btn_left_off);
			        otabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.btn_m_on);
			        otabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.btn_qa_off);
				}else if(tabId.equals("Q&A")){
					otabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.btn_left_off);
			        otabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.btn_m_off);
			        otabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.btn_qa_on);
				}
			}
		});
        
        Intent intent = getActivity().getIntent();
		user = (User)intent.getSerializableExtra("user");
		
		
        
        /*하단 4개 버튼*/
		Button btnLike = (Button)view.findViewById(R.id.btn_like);
        btnLike.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LikeMessageActivity.class);
				intent.putExtra("requestId", user._id);
				intent.putExtra("nickName", user.nickName);
				intent.putExtra("where", 1);
				startActivityForResult(intent, MainFragment.MAIN_PROFILE_REQUEST_CODE);
				getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.fade_out);
			}

		});
        
        Button btnPass = (Button)view.findViewById(R.id.btn_pass);
        btnPass.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NetworkManager.getInstnace().putPass(getActivity(), user._id, new OnResultListener<KeepData>() {
					
					@Override
					public void onSuccess(KeepData result) {
						if(result.result.equals("success")) {
							((OtherProfileActivity)getActivity()).setResult(Activity.RESULT_OK);
							Toast.makeText(getActivity(), "차단되었습니다",Toast.LENGTH_SHORT).show();
							getActivity().finish();
						} else {
							Toast.makeText(getActivity(), result.message,Toast.LENGTH_SHORT).show();
						}
					}
					
					@Override
					public void onFail(int code) {
						Toast.makeText(getActivity(), "code - " + code, Toast.LENGTH_SHORT).show();
					}
				});
			}

		});
        
        Button btnKeep = (Button)view.findViewById(R.id.btn_keep);
        btnKeep.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NetworkManager.getInstnace().putKeepbtn(getActivity(), user._id, user.relationCount, new OnResultListener<KeepData>() {
					
					@Override
					public void onSuccess(KeepData result) {
						if(result.result.equals("success")) {
							Toast.makeText(getActivity(), "찜 리스트에 추가되었습니다",Toast.LENGTH_SHORT).show();
							((OtherProfileActivity)getActivity()).setResult(Activity.RESULT_OK);
							getActivity().finish();
						} else {
							Toast.makeText(getActivity(), result.message,Toast.LENGTH_SHORT).show();
						}
					}
					
					@Override
					public void onFail(int code) {
						Toast.makeText(getActivity(), "code - " + code, Toast.LENGTH_SHORT).show();
					}
				});
			}

		});
        
        Button btnJiin = (Button)view.findViewById(R.id.btn_jiin);
        btnJiin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), RelationShipActivity.class);
				intent.putExtra("relation", MyUserListData.getInstance().getUserList(user, user.relationCount));
				intent.putExtra("relationCount", user.relationCount);
				startActivity(intent);
				
			}

		});
        
		return view;
    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		Log.i("OtherprofileFragment","onActivityResult");
		
		Log.i("requestCode",requestCode+"");
		Log.i("MainFragment.MAIN_PROFILE_REQUEST_CODE",MainFragment.MAIN_PROFILE_REQUEST_CODE+"");
		Log.i("resultCode",resultCode+"");
		Log.i("Activity.RESULT_OK",Activity.RESULT_OK+"");
		
		
		if(requestCode==MainFragment.MAIN_PROFILE_REQUEST_CODE && resultCode==Activity.RESULT_OK){
			((OtherProfileActivity)getActivity()).setResult(Activity.RESULT_OK);
			getActivity().finish();
		}
	}
   
	
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      

    }
}