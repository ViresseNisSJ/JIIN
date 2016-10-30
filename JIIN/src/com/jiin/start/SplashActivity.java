package com.jiin.start;

import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.jiin.JiinConstant;
import com.jiin.MainActivity;
import com.jiin.NetworkManager;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.R;
import com.jiin.User;
import com.jiin.menu.message.MessageActivity;
import com.jiin.menu.message.MessageContentActivity;
import com.jiin.myprofile.BasicActivity;
import com.jiin.myprofile.OptionActivity;
import com.jiin.myprofile.qna.QNAActivity;

public class SplashActivity extends ActionBarActivity {
	
	public static final int DELAY_TIME = 2000;
	
	CallbackManager callback;
	LoginManager mLoginManager;
	AccessTokenTracker tracker;
	
	String data;
	
	LoadingDialogFragment dialog;
	private static final String SENDER_ID = "1060189926693";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		checkAndRegister();
	}

	private void tryLogin() {
		AccessToken token = AccessToken.getCurrentAccessToken();
		if(token==null){
			login();
		}else{
			signup(token.getToken());
		}
	}
	
	private void signup(String token) {
		NetworkManager.getInstnace().putLoginFacebook(SplashActivity.this, token, new OnResultListener<LoginData>() {

			@Override
			public void onSuccess(LoginData result) {
				if(result.result.equals("success")){
					PropertyManager.getInstance().setMyGender(result.data.gender);
					redirectInfom(result.data);
				}else if(result.result.equals("fail")){
					Toast.makeText(SplashActivity.this, result.message, Toast.LENGTH_SHORT).show();
				}
				dialog.dismiss();
				
			}

			@Override
			public void onFail(int code) {
				Toast.makeText(SplashActivity.this, code+"", Toast.LENGTH_SHORT).show();	
				dialog.dismiss();
			}
			
		} );
	}

	private void login() {
		mLoginManager.registerCallback(callback, new FacebookCallback<LoginResult>() {

			@Override
			public void onSuccess(LoginResult result) {
				signup(result.getAccessToken().getToken());
			}

			
			@Override
			public void onCancel() {
				Toast.makeText(SplashActivity.this, "button cancel...", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(FacebookException error) {
				
			}
		});
		mLoginManager.logInWithReadPermissions(this, null);
		
	}

	private void redirectLoginActivity() {
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this, StartActivity.class);
				intent.putExtra("code", data);
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in_none_zoom, R.anim.fade_out_none_zoom);
				finish();
			}
		}, DELAY_TIME);
	}
	
	private void redirectInfom(final User user) {
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
		
				if(user.nickName == null){
					startActivity(new Intent(SplashActivity.this, BasicActivity.class));
					finish();
				}else if(user.height == null){
					//startActivity(new Intent(SplashActivity.this, QNAActivity.class));
					startActivity(new Intent(SplashActivity.this, OptionActivity.class));
					finish();
				}else{
					Intent intent = getIntent();
					int gcmType = (Integer) intent.getIntExtra("gcmType", -1);
					String requestId = intent.getStringExtra("requestId");
					String nickName = intent.getStringExtra("nickName");
					
					Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
					
					if(gcmType==2){
						Intent[] intent2 = new Intent[3];
						intent2[0] = mainIntent;
						intent2[1] = new Intent(SplashActivity.this, MessageActivity.class);
						intent2[2] = new Intent(SplashActivity.this, MessageContentActivity.class);
						intent2[2].putExtra("nickName", nickName);
						intent2[2].putExtra("requestId",requestId);
						startActivities(intent2);
					} else {
						startActivity(mainIntent);
					}
					finish();
				}
			}
		}, DELAY_TIME);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callback.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PLAY_SERVICES_RESOLUTION_REQUEST) {
			if (resultCode == Activity.RESULT_OK) {
				checkAndRegister();
			} else {
				finish();
			}
		}
		
	}

	
	private void checkAndRegister() {
		if (checkPlayServices()) {
			String regid = PropertyManager.getInstance().getRegistrationId();
			if (regid.equals("")) {
				registerInBackground();
			} else {
				doRealStart();
			}
		}
	}

	private void doRealStart() {
		
		
		Uri uri = getIntent().getData();
		if(uri == null) {
			data = null;
		} else {
			data = uri.getQueryParameter("code");
			Log.i("data",data);
		}
		
		
//		//나중에 지우기
//		Boolean check = false;
//		if(check==false){
//			redirectLoginActivity();
//			return;
//		}
//		
		//String data = getIntent().getData().getQueryParameter("code");
		
		
		String path = PropertyManager.getInstance().getJoinPath();
		if(path.equals(JiinConstant.JOIN_JI)){
			dialog = new LoadingDialogFragment();
			dialog.setCancelable(false);
			dialog.show(getSupportFragmentManager(), "loading");
			
			String userId= PropertyManager.getInstance().getUserEmail();
			String pw= PropertyManager.getInstance().getPassword();
			NetworkManager.getInstnace().putLogin(this, userId, pw, new OnResultListener<LoginData>() {
				
				@Override
				public void onSuccess(LoginData result) {
					if(result.result.equals("success")){
						PropertyManager.getInstance().setMyGender(result.data.gender);
						redirectInfom(result.data);
					}else if(result.result.equals("fail")){
						redirectLoginActivity();
					}
					
					dialog.dismiss();
				}
				
				@Override
				public void onFail(int code) {
					Toast.makeText(SplashActivity.this, code+"", Toast.LENGTH_SHORT).show();
					dialog.dismiss();
					
				}
			});
		}else if(path.equals(JiinConstant.JOIN_FB)){
			dialog = new LoadingDialogFragment();
			dialog.setCancelable(false);
			dialog.show(getSupportFragmentManager(), "loading");
			
			callback = CallbackManager.Factory.create();
			mLoginManager = LoginManager.getInstance();
			tracker = new AccessTokenTracker() {
				
				@Override
				protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
						AccessToken currentAccessToken) {
					
				}
			};
			
			tryLogin();
			
		}else{
			redirectLoginActivity();
		}
		
	}
	
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
						resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST);
				dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						finish();
					}
				});
				dialog.show();
			} else {
				finish();
			}
			return false;
		}
		return true;
	}
	

	private void registerInBackground() {
		new AsyncTask<Void, Integer, String>() {
			@Override
			protected String doInBackground(Void... params) {
				try {
					GoogleCloudMessaging gcm = GoogleCloudMessaging
							.getInstance(SplashActivity.this);
					String regid = gcm.register(SENDER_ID);
					PropertyManager.getInstance().setRegistrationId(regid);
					return regid;
				} catch (IOException ex) {
				}
				return null;
			}

			@Override
			protected void onPostExecute(String msg) {
				doRealStart();
			}
		}.execute();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
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
