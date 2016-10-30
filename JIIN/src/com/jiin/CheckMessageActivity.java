package com.jiin;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class CheckMessageActivity extends ActionBarActivity{
	
	String nickname;
	TextView nickView, conView;
	int where;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkmessage);
		
		/*액션바*/
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowCustomEnabled(true);//액션바에 있는 백키 onitemselected에서 조절
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(R.layout.actionbar);
	
		Intent intent = getIntent();
		nickname = intent.getStringExtra("nickname");
		where = intent.getIntExtra("where", -1);
		
		nickView = (TextView)findViewById(R.id.text_notice_who);
		nickView.setText(nickname +"님께 쪽지를 보냈습니다~!");
		
		conView = (TextView)findViewById(R.id.text_notice_content);
		if(where==1){
			conView.setText(R.string.notice_message1);
		}else if(where==2){
			conView.setText(R.string.notice_message2);
		}
		
		
		Handler mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				finish();
			}
		}, 2000);
	}
	
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.abc_fade_in, R.anim.slide_right_out);
	}
}
