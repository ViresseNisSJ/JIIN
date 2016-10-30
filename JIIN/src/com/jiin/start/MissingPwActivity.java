package com.jiin.start;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jiin.KeepData;
import com.jiin.NetworkManager;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.R;


public class MissingPwActivity extends ActionBarActivity{
	
	EditText emailView;
	String userEmail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_missing_pw);
		
		emailView= (EditText)findViewById(R.id.edit_emailforpw);
		
		Button btnSend = (Button)findViewById(R.id.btn_sendemail);
		btnSend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				userEmail = emailView.getText().toString();
				NetworkManager.getInstnace().putFindPassword(MissingPwActivity.this, userEmail, new OnResultListener<KeepData>() {

					@Override
					public void onSuccess(KeepData result) {
						if(result.result.equals("success")){
							Toast.makeText(MissingPwActivity.this, "해당 이메일로 임시 비밀번호를 전송했습니다", Toast.LENGTH_SHORT).show();
							finish();
						}else if(result.result.equals("fail")){
							Toast.makeText(MissingPwActivity.this, result.message, Toast.LENGTH_SHORT).show();
						}
						
					}

					@Override
					public void onFail(int code) {
						Toast.makeText(MissingPwActivity.this, code +"", Toast.LENGTH_SHORT).show();
						
					}
					
				} );
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