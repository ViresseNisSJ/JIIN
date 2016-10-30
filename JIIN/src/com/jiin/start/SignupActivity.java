package com.jiin.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.jiin.JiinConstant;
import com.jiin.KeepData;
import com.jiin.NetworkManager;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.R;
import com.jiin.myprofile.BasicActivity;


public class SignupActivity extends ActionBarActivity{
	
	TextView messageView;
	EditText idView, pwView;
	String gender = "";
	String userId = "";
	String pw = "";
	SwitchCompat switchAgree;
	Button btnGenderM, btnGenderF, facebookSignup;
	CallbackManager callback;
	
	String code;
	
	StartActivity aActivity = (StartActivity)StartActivity.StartActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		
		code = getIntent().getStringExtra("code");
		
		Button btnRequestSignup = (Button)findViewById(R.id.btn_requestSignup);
		btnRequestSignup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				userId = idView.getText().toString();
				pw = pwView.getText().toString();
				if(pw.length()<6){
					Toast.makeText(SignupActivity.this, "비밀번호는 6자리 이상 입력해 주세요", Toast.LENGTH_SHORT).show();
				}else{
					if(switchAgree.isChecked()==false){
						Toast.makeText(SignupActivity.this, "이용약관 및 개인정보 취급 방침에 동의해주세요", Toast.LENGTH_SHORT).show();
					}else if(switchAgree.isChecked()){
						if(userId.equals("")==false && gender.equals("")==false){
							
							NetworkManager.getInstnace().putSignup(SignupActivity.this, userId, pw, gender, code, new OnResultListener<SignupData>() {

								@Override
								public void onSuccess(SignupData result) {
									if(result.result.equals("success")){
										PropertyManager.getInstance().setUserEmail(userId);
										PropertyManager.getInstance().setJoinPath(JiinConstant.JOIN_JI);
										PropertyManager.getInstance().setPassword(pw);
										PropertyManager.getInstance().setMyGender(gender);
										startActivity(new Intent(SignupActivity.this, BasicActivity.class));
										aActivity.finish();
										finish();
									}else if(result.result.equals("fail")){
										Toast.makeText(SignupActivity.this, result.message, Toast.LENGTH_SHORT).show();
									}
									
								}

								@Override
								public void onFail(int code) {
									Toast.makeText(SignupActivity.this, code+"", Toast.LENGTH_SHORT).show();							
								}
								
							} );
							
						}
						else{
							Toast.makeText(SignupActivity.this, "필수항목을 모두 기입해 주세요", Toast.LENGTH_SHORT).show();
						}
					}
				}
				
			}
		});
		
		btnGenderM = (Button)findViewById(R.id.btn_male);
		btnGenderM.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gender = "남자";	
				checkColor();
				//btnGenderM.setBackgroundResource(R.drawable.btn_sex_on);
			}

			
		});
		
		btnGenderF = (Button)findViewById(R.id.btn_female);
		btnGenderF.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gender = "여자";	
				checkColor();
			}
		});
		
		callback = CallbackManager.Factory.create();
		facebookSignup = (Button)findViewById(R.id.btn_fbsignup);
		facebookSignup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AccessToken token = AccessToken.getCurrentAccessToken();
				if(token==null){
					login();
				}else{
					logout();
					login();
				}
								
			}
		});
		
		mLoginManager = LoginManager.getInstance();
		tracker = new AccessTokenTracker() {
			
			@Override
			protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
					AccessToken currentAccessToken) {
			}
		};
		
		
		switchAgree = (SwitchCompat)findViewById(R.id.switch_agree);
		
		idView = (EditText)findViewById(R.id.edit_emails);
		pwView = (EditText)findViewById(R.id.edit_PWs);
		
		messageView = (TextView)findViewById(R.id.text_link);
		messageView.setMovementMethod(LinkMovementMethod.getInstance());
		String source = getResources().getString(R.string.html_text);
		messageView.setText(Html.fromHtml(source));
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		tracker.stopTracking();
	}
	
	AccessTokenTracker tracker;
	

	LoginManager mLoginManager;
	
	private void logout() {
		mLoginManager.logOut();
		PropertyManager.getInstance().setFacebookId("");
	}
	
	

	private void login() {
		mLoginManager.registerCallback(callback, new FacebookCallback<LoginResult>() {

			@Override
			public void onSuccess(LoginResult result) {
				Log.i("token",result.getAccessToken().getToken());
				signup(result.getAccessToken().getToken());
			}

			
			@Override
			public void onCancel() {
				Toast.makeText(SignupActivity.this, "button cancel...", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(FacebookException error) {
				
			}
		});
		mLoginManager.logInWithReadPermissions(this, null);
	}
	
	private void signup(String token) {
		//Log.i("token", token);
		NetworkManager.getInstnace().putSignupFacebook(SignupActivity.this, token, code, new OnResultListener<SignupData>() {

			@Override
			public void onSuccess(SignupData result) {
				if(result.result.equals("success")){
					PropertyManager.getInstance().setJoinPath(JiinConstant.JOIN_FB);
					startActivity(new Intent(SignupActivity.this, BasicActivity.class));
					aActivity.finish();
					finish();
				}else if(result.result.equals("fail")){
					Toast.makeText(SignupActivity.this, result.message, Toast.LENGTH_SHORT).show();
				}
				
			}

			@Override
			public void onFail(int code) {
				Toast.makeText(SignupActivity.this, code+"", Toast.LENGTH_SHORT).show();							
			}
			
		} );
	}

	
	private void checkColor() {
		if(gender.equals("남자")){
			btnGenderM.setBackgroundResource(R.drawable.btn_male2);
			btnGenderF.setBackgroundResource(R.drawable.btn_female);
		}else if(gender.equals("여자")){
			btnGenderM.setBackgroundResource(R.drawable.btn_male);
			btnGenderF.setBackgroundResource(R.drawable.btn_female2);
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callback.onActivityResult(requestCode, resultCode, data);
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
