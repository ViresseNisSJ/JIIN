package com.jiin.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
import com.jiin.MainActivity;
import com.jiin.NetworkManager;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.R;
import com.jiin.myprofile.BasicActivity;
import com.jiin.myprofile.OptionActivity;
import com.jiin.myprofile.qna.QNAActivity;


public class LoginActivity extends ActionBarActivity{
	
	TextView misstext;
	EditText idView, pwView;
	String userId = "";
	String pw = "";
	CallbackManager callback;
	
	StartActivity aActivity = (StartActivity)StartActivity.StartActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		
		idView = (EditText)findViewById(R.id.edit_emaill);
		pwView = (EditText)findViewById(R.id.edit_PWl);
		
		Button btnrequestlogin = (Button)findViewById(R.id.btn_requestLogin);
		btnrequestlogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				userId = idView.getText().toString();
				pw = pwView.getText().toString();
				
				if(userId.equals("")==false && pw.equals("")==false){
					
					NetworkManager.getInstnace().putLogin(LoginActivity.this, userId, pw, new OnResultListener<LoginData>() {

						@Override
						public void onSuccess(LoginData result) {
							if(result.result.equals("success")){
								PropertyManager.getInstance().setUserEmail(userId);
								PropertyManager.getInstance().setJoinPath(JiinConstant.JOIN_JI);
								PropertyManager.getInstance().setPassword(pw);
								PropertyManager.getInstance().setMyGender(result.data.gender);
								
								if(result.data.nickName == null){
									startActivity(new Intent(LoginActivity.this, BasicActivity.class));
									aActivity.finish();
									finish();
								}else if(result.data.height == null){
									startActivity(new Intent(LoginActivity.this, OptionActivity.class));
									//startActivity(new Intent(LoginActivity.this, QNAActivity.class));
									aActivity.finish();
									finish();
								}else{
									startActivity(new Intent(LoginActivity.this, MainActivity.class));
									aActivity.finish();
									finish();
								}
							}else if(result.result.equals("fail")){
								Toast.makeText(LoginActivity.this, result.message , Toast.LENGTH_SHORT).show();
							}
							
						}

						@Override
						public void onFail(int code) {
							Toast.makeText(LoginActivity.this, code+"", Toast.LENGTH_SHORT).show();
							
						}
						
					} );
				}
				else{
					Toast.makeText(LoginActivity.this, "필수항목을 모두 기입해 주세요", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		misstext = (TextView)findViewById(R.id.text_link2);
		misstext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//startActivity(new Intent(LoginActivity.this, MissingPwActivity.class));
				
				MissDialogFragment dialog =  new MissDialogFragment();
				dialog.show(getSupportFragmentManager(), "loading");
			}
		});
		
		mLoginManager = LoginManager.getInstance();
		tracker = new AccessTokenTracker() {
			
			@Override
			protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
					AccessToken currentAccessToken) {
			}
		};
		
		callback = CallbackManager.Factory.create();
		Button btnFblogin = (Button)findViewById(R.id.btn_fblogin);
		btnFblogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AccessToken token = AccessToken.getCurrentAccessToken();
				if(token==null){
					login();
				}else{
//					logout();
					signup(token.getToken());
				}
			}
		});
	
		
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
				Toast.makeText(LoginActivity.this, "button cancel...", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(FacebookException error) {
				
			}
		});
		mLoginManager.logInWithReadPermissions(this, null);
	}
	
	private void signup(String token) {
		//Log.i("token", token);
		NetworkManager.getInstnace().putLoginFacebook(LoginActivity.this, token, new OnResultListener<LoginData>() {

			@Override
			public void onSuccess(LoginData result) {
				if(result.result.equals("success")){
					PropertyManager.getInstance().setJoinPath(JiinConstant.JOIN_FB);
					if(result.data.nickName == null){
						startActivity(new Intent(LoginActivity.this, BasicActivity.class));
						aActivity.finish();
						finish();
					}else if(result.data.height != null){
//						startActivity(new Intent(LoginActivity.this, OptionActivity.class));
						startActivity(new Intent(LoginActivity.this, QNAActivity.class));
						aActivity.finish();
						finish();
					}else{
						startActivity(new Intent(LoginActivity.this, MainActivity.class));
						aActivity.finish();
						finish();
					}
				}else if(result.result.equals("fail")){
					Toast.makeText(LoginActivity.this, result.message, Toast.LENGTH_SHORT).show();
				}
				
			}

			@Override
			public void onFail(int code) {
				Toast.makeText(LoginActivity.this, code+"", Toast.LENGTH_SHORT).show();							
			}
			
		} );
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
