package com.jiin.menu;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jiin.NetworkManager;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.R;

public class ChangePwActivity extends ActionBarActivity{

	EditText nowPwView, newPwView, newPwView2;
	String currentPassword, editedPassword, editedPassword2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changepw);
		
		/*�׼ǹ�*/
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowCustomEnabled(true);//�׼ǹٿ� �ִ� ��Ű onitemselected���� ����
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(R.layout.actionbar);
	
		nowPwView = (EditText)findViewById(R.id.edit_nowPw);
		newPwView = (EditText)findViewById(R.id.edit_newPw);
		newPwView2 = (EditText)findViewById(R.id.edit_newPw2);
		
		Button btnChange = (Button)findViewById(R.id.btn_change);
		btnChange.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				currentPassword = nowPwView.getText().toString();
				editedPassword = newPwView.getText().toString();
				editedPassword2 = newPwView2.getText().toString();
				
				if(editedPassword.length()<6){
					Toast.makeText(ChangePwActivity.this, "��й�ȣ�� 6�ڸ� �̻� �Է����ּ���", Toast.LENGTH_SHORT).show();
				}else{
					if(editedPassword.equals(editedPassword2)){
						NetworkManager.getInstnace().putEditPassword(ChangePwActivity.this, currentPassword, editedPassword, new OnResultListener<ChangePwData>() {

							@Override
							public void onSuccess(ChangePwData result) {
								if(result.result.equals("success")){
									Toast.makeText(ChangePwActivity.this, "��й�ȣ�� ����Ǿ����ϴ�", Toast.LENGTH_SHORT).show();
									finish();
								}else if(result.result.equals("fail")){
									Toast.makeText(ChangePwActivity.this, result.message, Toast.LENGTH_SHORT).show();
								}
								
							}

							@Override
							public void onFail(int code) {
								Toast.makeText(ChangePwActivity.this, code +"", Toast.LENGTH_SHORT).show();
								
							}
							
						} );
					}else{
						Toast.makeText(ChangePwActivity.this, "��й�ȣ�� ��ġ���� �ʽ��ϴ�", Toast.LENGTH_SHORT).show();
					}
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
