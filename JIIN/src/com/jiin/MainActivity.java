package com.jiin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jiin.NetworkManager.OnResultListener;
import com.jiin.menu.CustomerServiceActivity;
import com.jiin.menu.LogoutData;
import com.jiin.menu.NoticeActivity;
import com.jiin.menu.SetupActivity;
import com.jiin.menu.ShopActivity;
import com.jiin.menu.message.MessageActivity;
import com.jiin.start.StartActivity;
import com.kakao.AppActionBuilder;
import com.kakao.AppActionInfoBuilder;
import com.kakao.KakaoLink;
import com.kakao.KakaoParameterException;
import com.kakao.KakaoTalkLinkMessageBuilder;

public class MainActivity extends ActionBarActivity implements LeftMenuFragment.MenuCallback {

	private final long	FINSH_INTERVAL_TIME    = 2000;
	private long		backPressedTime        = 0;
	
	DrawerLayout mDrawer;

	ActionBarDrawerToggle mToggle;
	String code;
	
	private KakaoLink kakaoLink;
    private KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*액션바*/
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);//액션바에 있는 백키 onitemselected에서 조절
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(R.layout.actionbar);
		
		
		mDrawer = (DrawerLayout)findViewById(R.id.drawer);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.contentcontainer, new MainFragment()).commit();
			LeftMenuFragment menuFragment = new LeftMenuFragment();
			menuFragment.setDrawerLayout(mDrawer);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.leftmenu, menuFragment).commit();
			
		}
		mDrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.open_desc, R.string.close_desc){
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}
			
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		};
		
		mToggle.setHomeAsUpIndicator(R.drawable.ic_drawer);
		mDrawer.setDrawerListener(mToggle);
		
		
		try {
            kakaoLink = KakaoLink.getKakaoLink(getApplicationContext());
            kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();

        } catch (KakaoParameterException e) {
            alert(e.getMessage());
        }
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("MainActivity","onActivityResult");
		
		Log.i("requestCode",requestCode+"");
		Log.i("MainFragment.MAIN_PROFILE_REQUEST_CODE",MainFragment.MAIN_PROFILE_REQUEST_CODE+"");
		Log.i("resultCode",resultCode+"");
		Log.i("Activity.RESULT_OK",Activity.RESULT_OK+"");
	};
	
	
	Drawable icon;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mToggle.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mToggle.syncState();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (mToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	
	private void sendKakaoTalkLink() {
        try {
        	
           //이미지(URL)
           kakaoTalkLinkMessageBuilder.addImage(JiinConstant.SERVER_URL+"/images/default/kakaolink/kakaolink_image.png", 300, 200);
        	
           //메시지(타이틀?)
           kakaoTalkLinkMessageBuilder.addText(getString(R.string.kakaolink_title));
           
           //앱연결 버튼(이미지는?)
           kakaoTalkLinkMessageBuilder.addAppButton(getString(R.string.kakaolink_appbutton),
                 new AppActionBuilder()
                    .addActionInfo(AppActionInfoBuilder.createAndroidActionInfoBuilder().setExecuteParam("code="+code).setMarketParam("referrer=kakaotalklink").build())
                    .addActionInfo(AppActionInfoBuilder.createiOSActionInfoBuilder(AppActionBuilder.DEVICE_TYPE.PHONE).setExecuteParam("execparamkey1").build()).build()
           );

           //메시지 보내기
           kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder.build(), this);
           

        } catch (KakaoParameterException e) {
            alert(e.getMessage());
        }
    }
	
	private void alert(String message) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .create().show();
    }
	

	@Override
	public void selectedItem(int menuId) {
		
//		Fragment f = null;
//		String tag = null;
//		Fragment old = null;
		
		switch(menuId) {
		case LeftMenuFragment.MENU_ID_ONE :
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("지인 초대");
			final String[] array = getResources().getStringArray(R.array.invite);
			builder.setItems(array, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch(which){
					case 0:
						NetworkManager.getInstnace().getInvite(MainActivity.this, new OnResultListener<InviteData>() {

							@Override
							public void onSuccess(InviteData result) {
								if(result.result.equals("success")){
									code = result.data;
									sendKakaoTalkLink();
									kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
								}else if(result.result.equals("fail")){
									Toast.makeText(MainActivity.this, result.message, Toast.LENGTH_SHORT).show();
								}
							}

							@Override
							public void onFail(int code) {
								Toast.makeText(MainActivity.this, code+"", Toast.LENGTH_SHORT).show();
							}
							
						});
						break;
						
					case 1:
						NetworkManager.getInstnace().getInvite(MainActivity.this, new OnResultListener<InviteData>() {

							@Override
							public void onSuccess(InviteData result) {
								if(result.result.equals("success")){
									code = result.data;
							         Uri uri = Uri.parse("smsto:");   
							         Intent it = new Intent(Intent.ACTION_SENDTO, uri);   
							         it.putExtra("sms_body", getString(R.string.kakaolink_title)+"\n jiin://invite?code="+code);   
							         startActivity(it); 
								         
								}else if(result.result.equals("fail")){
									Toast.makeText(MainActivity.this, result.message, Toast.LENGTH_SHORT).show();
								}
							}

							@Override
							public void onFail(int code) {
								Toast.makeText(MainActivity.this, code+"", Toast.LENGTH_SHORT).show();
							}
							
						});
						break;
					
					case 2:
						NetworkManager.getInstnace().getInvite(MainActivity.this, new OnResultListener<InviteData>() {

							@Override
							public void onSuccess(InviteData result) {
								if(result.result.equals("success")){
									code = result.data;
									
									String linkCode = "<a href=\"jiin://invite?code="+code+"\"" + ">JIIN 시작하기 </a>";
									Intent i = new Intent(Intent.ACTION_SEND);
							         i.setType("message/rfc822");
							         i.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
							         i.putExtra(Intent.EXTRA_SUBJECT, String.format(getString(R.string.invite_string_title_email)));
							         i.putExtra(Intent.EXTRA_TEXT,
							        		 Html.fromHtml(new StringBuilder()
							        		     .append(getString(R.string.kakaolink_title)+"<br>")
							        		     .append(linkCode)
							        		     .toString())
							        		 );
							         
							         try {
							             startActivity(Intent.createChooser(i, "Email"));
							             
							         } catch (android.content.ActivityNotFoundException ex) {
							             Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
							             
							         }
								}else if(result.result.equals("fail")){
									Toast.makeText(MainActivity.this, result.message, Toast.LENGTH_SHORT).show();
								}
							}

							@Override
							public void onFail(int code) {
								Toast.makeText(MainActivity.this, code+"", Toast.LENGTH_SHORT).show();
							}
							
						});
					}
						
					
				}
			});
			
			builder.create().show();
			break;
		case LeftMenuFragment.MENU_ID_TWO :
			startActivity(new Intent(MainActivity.this, MessageActivity.class));
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
			break;
		case LeftMenuFragment.MENU_ID_THREE :
			startActivity(new Intent(MainActivity.this, NoticeActivity.class));
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
			break;
		case LeftMenuFragment.MENU_ID_FOUR :
			startActivity(new Intent(MainActivity.this, CustomerServiceActivity.class));
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
			break;
		case LeftMenuFragment.MENU_ID_FIVE :
			startActivity(new Intent(MainActivity.this, ShopActivity.class));
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
			break;
		case LeftMenuFragment.MENU_ID_SIX :
			startActivity(new Intent(MainActivity.this, SetupActivity.class));
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
			break;
		case LeftMenuFragment.MENU_ID_SEVEN :
			NetworkManager.getInstnace().getLogout(this, new OnResultListener<LogoutData>() {
				
				@Override
				public void onSuccess(LogoutData result) {
					if(result.result.equals("success")) {
						startActivity(new Intent(MainActivity.this, StartActivity.class));
						overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
						finish();
						
					} else if(result.result.equals("fail")){
						Toast.makeText(MainActivity.this, result.message, Toast.LENGTH_SHORT).show();
					}
				}
				
				@Override
				public void onFail(int code) {
					Toast.makeText(MainActivity.this, code + "", Toast.LENGTH_SHORT).show();
				}
			});
			break;
			
			
		}
	}
	
	
	
	@Override 
	public void onBackPressed() {
		long tempTime        = System.currentTimeMillis();
		long intervalTime    = tempTime - backPressedTime;
		  
		if ( 0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime ) {
		    super.onBackPressed(); 
		} 
		else { 
			backPressedTime = tempTime; 
			Toast.makeText(getApplicationContext(),"'뒤로'버튼을한번더누르시면종료됩니다.",Toast.LENGTH_SHORT).show(); 
		} 
	} 
}