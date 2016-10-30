package com.jiin.otherprofile;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

import com.jiin.KeepData;
import com.jiin.LikeMessageActivity;
import com.jiin.MainFragment;
import com.jiin.MyUserListData;
import com.jiin.NetworkManager;
import com.jiin.R;
import com.jiin.User;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.otherprofile.relationship.RelationShipActivity;

public class OtherProfileActivity extends ActionBarActivity {

	FragmentTabHost otabHost;
	User user;
	ArrayList<User> relation = new ArrayList<User>();
	int relationCount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_otherprofile);
		
		/*액션바*/
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowCustomEnabled(true);//액션바에 있는 백키 onitemselected에서 조절
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(R.layout.actionbar);
		
		otabHost = (FragmentTabHost)findViewById(R.id.tabhost);
		otabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		
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
        
        Intent intent = getIntent();
		user = (User)intent.getSerializableExtra("user");
		
		 /*하단 4개 버튼*/
		Button btnLike = (Button)findViewById(R.id.btn_like);
        btnLike.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(OtherProfileActivity.this, LikeMessageActivity.class);
				intent.putExtra("requestId", user._id);
				intent.putExtra("nickName", user.nickName);
				intent.putExtra("where", 1);
				startActivityForResult(intent, MainFragment.MAIN_PROFILE_REQUEST_CODE);
				overridePendingTransition(R.anim.slide_right_in, R.anim.fade_out);
				
			}

		});
        
        Button btnPass = (Button)findViewById(R.id.btn_pass);
        btnPass.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NetworkManager.getInstnace().putPass(OtherProfileActivity.this, user._id, new OnResultListener<KeepData>() {
					
					@Override
					public void onSuccess(KeepData result) {
						if(result.result.equals("success")) {
							Toast.makeText(OtherProfileActivity.this, "차단되었습니다",Toast.LENGTH_SHORT).show();
							setResult(Activity.RESULT_OK);
							finish();
						} else {
							Toast.makeText(OtherProfileActivity.this, result.message,Toast.LENGTH_SHORT).show();
						}
					}
					
					@Override
					public void onFail(int code) {
						Toast.makeText(OtherProfileActivity.this, "code - " + code, Toast.LENGTH_SHORT).show();
					}
				});
			}

		});
        
        Button btnKeep = (Button)findViewById(R.id.btn_keep);
        btnKeep.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NetworkManager.getInstnace().putKeepbtn(OtherProfileActivity.this, user._id, user.relationCount, new OnResultListener<KeepData>() {
					
					@Override
					public void onSuccess(KeepData result) {
						if(result.result.equals("success")) {
							Toast.makeText(OtherProfileActivity.this, "찜 리스트에 추가되었습니다",Toast.LENGTH_SHORT).show();
							setResult(Activity.RESULT_OK);
							finish();
						} else {
							Toast.makeText(OtherProfileActivity.this, result.message,Toast.LENGTH_SHORT).show();
						}
					}
					
					@Override
					public void onFail(int code) {
						Toast.makeText(OtherProfileActivity.this, "code - " + code, Toast.LENGTH_SHORT).show();
					}
				});
			}

		});
        
        Button btnJiin = (Button)findViewById(R.id.btn_jiin);
        btnJiin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(OtherProfileActivity.this, RelationShipActivity.class);
				intent.putExtra("relation", MyUserListData.getInstance().getUserList(user, user.relationCount));
				intent.putExtra("relationCount", user.relationCount);
				startActivity(intent);
				
			}

		});

	}
	
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.fade_in, R.anim.slide_bottom_out);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode==MainFragment.MAIN_PROFILE_REQUEST_CODE && resultCode==Activity.RESULT_OK){
			setResult(Activity.RESULT_OK);
			finish();
		}
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int id = item.getItemId();
		if(id == android.R.id.home){
			super.onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
}
