package com.jiin;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.jiin.NetworkManager.OnResultListener;
import com.jiin.otherprofile.relationship.RelationShipActivity;
import com.jiin.start.PropertyManager;

public class OtherFragment extends Fragment {
	
	
	android.app.Fragment keepFragment;
	ViewPager viewPager;
	OtherlistAdapter olAdapter;
	ArrayList<User> userList = new ArrayList<User>();
	
	String message = "abc";
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view= inflater.inflate(R.layout.fragment_otherlist, container, false);
		
		viewPager = (ViewPager)view.findViewById(R.id.pager);
        olAdapter = new OtherlistAdapter(getChildFragmentManager());
        
        viewPager.setPadding(90, 0, 90, 0);
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(30);

        initData();
 
		
		/*하단 4개 버튼*/
		Button btnLike = (Button)view.findViewById(R.id.btn_like);
        btnLike.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				User user = olAdapter.get(viewPager.getCurrentItem());
				Intent intent = new Intent(getActivity(), LikeMessageActivity.class);
				intent.putExtra("requestId", user._id);
				intent.putExtra("nickName", user.nickName);
				intent.putExtra("where", 1);
				startActivityForResult(intent, MainFragment.MAIN_LIKEMESSAGE_REQUEST_CODE);
				getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.fade_out);
			}

		});
        
        Button btnPass = (Button)view.findViewById(R.id.btn_pass);
        btnPass.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				User user = olAdapter.get(viewPager.getCurrentItem()); 
				NetworkManager.getInstnace().putPass(getActivity(), user._id, new OnResultListener<KeepData>() {
					
					@Override
					public void onSuccess(KeepData result) {
						if(result.result.equals("success")) {
							Toast.makeText(getActivity(), "차단되었습니다",Toast.LENGTH_SHORT).show();
							olAdapter.remove(viewPager.getCurrentItem());
						} else {
							Toast.makeText(getActivity(), result.message,Toast.LENGTH_SHORT).show();
						}
					}
					
					@Override
					public void onFail(int code) {
						Toast.makeText(getActivity(), "code - " + code, Toast.LENGTH_SHORT).show();
					}
				});
				
				//onResume();
			}

		});
        
        Button btnKeep = (Button)view.findViewById(R.id.btn_keep);
        btnKeep.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				User user = olAdapter.get(viewPager.getCurrentItem()); 
				NetworkManager.getInstnace().putKeepbtn(getActivity(), user._id, user.relationCount, new OnResultListener<KeepData>() {
					
					@Override
					public void onSuccess(KeepData result) {
						if(result.result.equals("success")) {
							Toast.makeText(getActivity(), "찜 리스트에 추가되었습니다",Toast.LENGTH_SHORT).show();
							olAdapter.remove(viewPager.getCurrentItem());
						} else {
							Toast.makeText(getActivity(), result.message,Toast.LENGTH_SHORT).show();
						}
					}
					
					@Override
					public void onFail(int code) {
						Toast.makeText(getActivity(), "code - " + code, Toast.LENGTH_SHORT).show();
					}
				});
				
				//MainFragment.OnTabChanged();
			}

		});
        
        Button btnJiin = (Button)view.findViewById(R.id.btn_jiin);
        btnJiin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				User user = olAdapter.get(viewPager.getCurrentItem()); 
				Intent intent = new Intent(getActivity(), RelationShipActivity.class);
        		intent.putExtra("relation", MyUserListData.getInstance().getUserList(user, user.relationCount));
        		intent.putExtra("relationCount", user.relationCount);
				startActivity(intent);
			}

		});
		
       
		
		return view;
	}
	
	private void initData() {

		NetworkManager.getInstnace().getOtherlist(getActivity(), new OnResultListener<OtherlistData>() {
			
			@Override
			public void onSuccess(OtherlistData result) {
				if(result.result.equals("success")) {
					String gender=PropertyManager.getInstance().getMyGender();
					olAdapter.addAll(initChildren(result.data.userList),result.data.hideList,gender);
					viewPager.setAdapter(olAdapter);
					olAdapter.notifyDataSetChanged();
				} else {
					
				}
			}
			
			@Override
			public void onFail(int code) {
				Toast.makeText(getActivity(), "code - " + code, Toast.LENGTH_SHORT).show();
			}
		});
	}

	
	private ArrayList<User> initChildren(ArrayList<User> list) {
		userList.clear();
		userList.addAll(list);
		int size = userList.size();
		
		for(int i=0; i<size; size = userList.size(), i++) {
			User parent = userList.get(i);
			parent.position = i;
			ArrayList<User> childList = parent.children;
			
			for(int j=0; j<childList.size(); j++) {
				childList.get(j).parent = parent;
			}
			
			userList.addAll(childList);
		}
		MyUserListData.getInstance().setUserList(userList);
		return userList;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		Log.i("OtherFragment","onActivityResult");
		
		Log.i("requestCode",requestCode+"");
		Log.i("MainFragment.MAIN_PROFILE_REQUEST_CODE",MainFragment.MAIN_PROFILE_REQUEST_CODE+"");
		Log.i("resultCode",resultCode+"");
		Log.i("Activity.RESULT_OK",Activity.RESULT_OK+"");
		
	}
		
	public interface ResultReceiver{
		public void receiveResult();
		
		
	}
	
   
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      

    }
}
