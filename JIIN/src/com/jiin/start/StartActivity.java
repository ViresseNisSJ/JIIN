package com.jiin.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jiin.KeepData;
import com.jiin.NetworkManager;
import com.jiin.R;
import com.jiin.NetworkManager.OnResultListener;


public class StartActivity extends ActionBarActivity{
	
	public static Activity StartActivity;
	
	String code;
	View buttonView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		StartActivity = StartActivity.this;
		
		Animation anim = AnimationUtils.loadAnimation(StartActivity.this, R.anim.loginbox_apear);
		
		buttonView = findViewById(R.id.layout_button);
		buttonView.setAnimation(anim);
		
		code = getIntent().getStringExtra("code");
		
		Button btnsignup = (Button)findViewById(R.id.btn_signup);
		btnsignup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(code==null){
					Toast.makeText(StartActivity.this, "초대된 분들만 사용할 수 있습니다", Toast.LENGTH_SHORT).show();
					return;
				}
				
				NetworkManager.getInstnace().putCheckCode(StartActivity.this, code, new OnResultListener<KeepData>() {
					
					@Override
					public void onSuccess(KeepData result) {
						if(result.result.equals("success")){
							Intent intent = new Intent(StartActivity.this, SignupActivity.class);
							intent.putExtra("code", code);
							startActivity(new Intent(intent));
						}else if(result.result.equals("fail")){
							Toast.makeText(StartActivity.this, result.message, Toast.LENGTH_SHORT).show();
						}
					}
					
					@Override
					public void onFail(int code) {
						Toast.makeText(StartActivity.this, code+"", Toast.LENGTH_SHORT).show();
					}
				});
				
			}
		});
		
		Button btnlogin = (Button)findViewById(R.id.btn_login);
		btnlogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(StartActivity.this, LoginActivity.class));
				//finish();
			}
		});
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
