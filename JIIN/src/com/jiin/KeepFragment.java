package com.jiin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.jiin.KeepAdapter.OnAdapterClickListener;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.otherprofile.OtherProfileActivity;

public class KeepFragment extends Fragment{

	GridView listView;
	KeepAdapter mAdapter;
	User item;
	String requestId;
	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		Bundle b = getArguments();
//		item = (User)b.getSerializable("keep");
//		
//	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_keep, container, false);
    	
    	mAdapter = new KeepAdapter(getActivity());
    	listView = (GridView)view.findViewById(R.id.gridView1);
		mAdapter.setOnAdapterClickListener(new OnAdapterClickListener() {
			
			@Override
			public void onProfileClicked(KeepAdapter keepAdapter, KeepView view, User data) {
				User user = MyUserListData.getInstance().getKeepUser(data);
				if(user==null){
					Toast.makeText(getActivity(), "오류가 발생했습니다. 다시 실행해 주세요", Toast.LENGTH_SHORT).show();
				}
				Intent intent = new Intent(getActivity(), OtherProfileActivity.class);
				intent.putExtra("user", user);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.slide_bottom_in, R.anim.fade_out);
			}
			
			@Override
			public void onPassClicked(KeepAdapter keepAdapter, KeepView view, User data) {
				NetworkManager.getInstnace().putPass(getActivity(), data._id, new OnResultListener<KeepData>() {
					
					@Override
					public void onSuccess(KeepData result) {
						if(result.result.equals("success")) {
							Toast.makeText(getActivity(), "차단되었습니다",Toast.LENGTH_SHORT).show();
							mAdapter.clear();
							initData();
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
			
			@Override
			public void onLikeClicked(KeepAdapter keepAdapter, KeepView view, User data) {
				Intent intent = new Intent(getActivity(), LikeMessageActivity.class);
				intent.putExtra("nickName", data.nickName);
				intent.putExtra("where", 1);
				intent.putExtra("requestId", data._id);
				startActivity(intent);
			}
		});
		listView.setAdapter(mAdapter);
		
		
    	return view;
    }
	
	@Override
	public void onResume() {
		super.onResume();
		initData();
	}
	
	private void initData() {
		NetworkManager.getInstnace().getKeep(getActivity(), new OnResultListener<KeepData>() {
			
			@Override
			public void onSuccess(KeepData result) {
				if(result.result.equals("success")) {
					mAdapter.clear();
					mAdapter.addAll(result.datas);
					mAdapter.notifyDataSetChanged();
					
				} else {
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
