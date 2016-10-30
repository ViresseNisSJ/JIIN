package com.jiin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jiin.NetworkManager.OnResultListener;


public class LikeMessageActivity extends ActionBarActivity{
	
	EditText messagecontent;
	
	String message, requestId ,nickname; 
	int where;
	//키보드??//
	private InputMethodManager imm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_likemessage);
		
		/*액션바*/
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowCustomEnabled(true);//액션바에 있는 백키 onitemselected에서 조절
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(R.layout.actionbar);
		
		
		imm= (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		
		messagecontent = (EditText)findViewById(R.id.edit_sendlike);
		
		imm.hideSoftInputFromWindow(messagecontent.getWindowToken(), 0);
		Button btnSendlike = (Button)findViewById(R.id.btn_sendlike);
		btnSendlike.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = getIntent();
				requestId = intent.getStringExtra("requestId");
				nickname = intent.getStringExtra("nickName");
				where = intent.getIntExtra("where", -1);
				message = messagecontent.getText().toString();
				
				if(message.length()<6){
					Toast.makeText(LikeMessageActivity.this, "쪽지는 6글자 이상 정성스럽게 작성해주세요", Toast.LENGTH_SHORT).show();
					return;
				}else{
					NetworkManager.getInstnace().putSendMessage(LikeMessageActivity.this, requestId, message, new OnResultListener<KeepData>() {
						
						@Override
						public void onSuccess(KeepData result) {
							if(result.result.equals("success")) {
								Toast.makeText(LikeMessageActivity.this, "쪽지가 전달되었습니다",Toast.LENGTH_SHORT).show();
								Intent intent = new Intent(LikeMessageActivity.this, CheckMessageActivity.class);
								intent.putExtra("nickname", nickname);
								intent.putExtra("where", 1);
								startActivity(intent);
								overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
								setResult(Activity.RESULT_OK);
								finish();
							} else {
								Toast.makeText(LikeMessageActivity.this, result.message,Toast.LENGTH_SHORT).show();
							}
						}
						
						@Override
						public void onFail(int code) {
							Toast.makeText(LikeMessageActivity.this, "code - " + code, Toast.LENGTH_SHORT).show();
						}
					});
				}
				
			}
		});
		
		
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
