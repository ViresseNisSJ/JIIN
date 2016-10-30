package com.jiin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiin.NetworkManager.OnResultListener;
import com.jiin.menu.LogoutData;
import com.jiin.mypage.MypageActivity;
import com.jiin.start.PropertyManager;
import com.jiin.start.StartActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

public class LeftMenuFragment extends Fragment {
	ListView listView;
	LeftMenuAdapter mAdapter;

	public static final int MENU_ID_ONE = 0;
	public static final int MENU_ID_TWO = 1;
	public static final int MENU_ID_THREE = 2;
	public static final int MENU_ID_FOUR = 3;
	public static final int MENU_ID_FIVE = 4;
	public static final int MENU_ID_SIX = 5;
	public static final int MENU_ID_SEVEN = 6;

	public interface MenuCallback {
		public void selectedItem(int menuId);
	}

	DrawerLayout mDrawer;
	MenuCallback callback = null;
	LinearLayout logout;
	ImageView profileView;
	TextView nickView, pointView;
	FrameLayout profileLayout;

	public void setDrawerLayout(DrawerLayout drawer) {
		mDrawer = drawer;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_leftmenu, container, false);
		logout = (LinearLayout)view.findViewById(R.id.layout_logout);
		logout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NetworkManager.getInstnace().getLogout(getActivity(), new OnResultListener<LogoutData>() {
					
					@Override
					public void onSuccess(LogoutData result) {
						if(result.result.equals("success")) {
							PropertyManager.getInstance().setJoinPath("");
							startActivity(new Intent(getActivity(), StartActivity.class));
							getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
							getActivity().finish();
							
						} else if(result.result.equals("fail")){
							Toast.makeText(getActivity(), result.message, Toast.LENGTH_SHORT).show();
						}
					}
					
					@Override
					public void onFail(int code) {
						Toast.makeText(getActivity(), code + "", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		
		listView = (ListView) view.findViewById(R.id.list_menu);
		mAdapter = new LeftMenuAdapter();
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LeftMenuItem item = (LeftMenuItem) listView.getItemAtPosition(position);
				if (callback != null) {
					callback.selectedItem(item.menuId);
				}
				if (mDrawer != null) {
					mDrawer.closeDrawer(GravityCompat.START);
				}
			}
		});
		
		profileView = (ImageView)view.findViewById(R.id.image_profile);
		nickView = (TextView)view.findViewById(R.id.text_nickname);
		pointView = (TextView)view.findViewById(R.id.text_point);
		
		profileLayout = (FrameLayout)view.findViewById(R.id.layout_myProfile);
		profileLayout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), MypageActivity.class));
				getActivity().overridePendingTransition(R.anim.slide_bottom_in, R.anim.fade_out);
			}
		});

		initData();
		initProfile();
		return view;
	}

	private void initProfile() {
		NetworkManager.getInstnace().getMyInform(getActivity(), new OnResultListener<MyData>(){
			@Override
			public void onSuccess(MyData result) {
				if(result.result.equals("success")){
					ImageLoader.getInstance().displayImage(JiinConstant.SERVER_URL+result.data.mainPicture, profileView);
					nickView.setText(result.data.nickName);
					pointView.setText(result.data.point+"p 보유");
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

	private void initData() {
		
		LeftMenuItem mi = new LeftMenuItem();
		
		mi.image = R.drawable.ic_friend;
		mi.name = "친구초대";
		mi.menuId = MENU_ID_ONE;
		mAdapter.add(mi);
		
		LeftMenuItem mi2 = new LeftMenuItem();
		mi2.image = R.drawable.ic_message;
		mi2.name = "쪽지함";
		mi2.menuId = MENU_ID_TWO;
		mAdapter.add(mi2);
		
		LeftMenuItem mi3 = new LeftMenuItem();
		mi3.image = R.drawable.ic_board;
		mi3.name = "공지사항";
		mi3.menuId = MENU_ID_THREE;
		mAdapter.add(mi3);
		
		LeftMenuItem mi4 = new LeftMenuItem();
		mi4.image = R.drawable.ic_question;
		mi4.name = "문의하기";
		mi4.menuId = MENU_ID_FOUR;
		mAdapter.add(mi4);
		
		LeftMenuItem mi5 = new LeftMenuItem();
		mi5.image = R.drawable.ic_pay_icon;
		mi5.name = "결제";
		mi5.menuId = MENU_ID_FIVE;
		mAdapter.add(mi5);
		
		LeftMenuItem mi6 = new LeftMenuItem();
		mi6.image = R.drawable.ic_setting;
		mi6.name = "설정";
		mi6.menuId = MENU_ID_SIX;
		mAdapter.add(mi6);      
        
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof MenuCallback) {
			callback = (MenuCallback) activity;
		}
	}
}
