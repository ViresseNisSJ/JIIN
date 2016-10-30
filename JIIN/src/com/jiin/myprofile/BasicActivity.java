package com.jiin.myprofile;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;

import com.jiin.R;

public class BasicActivity extends ActionBarActivity {

	DrawerLayout myDrawer;

	ActionBarDrawerToggle myToggle;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myprofile);
		
		/*액션바*/
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);//액션바에 있는 백키 onitemselected에서 조절
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(R.layout.actionbar);
		
		myDrawer = (DrawerLayout)findViewById(R.id.drawer2);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.contentcontainer, new BasicFragment()).commit();
//			LeftMenuFragment menuFragment = new LeftMenuFragment();
//			menuFragment.setDrawerLayout(myDrawer);
//			getSupportFragmentManager().beginTransaction()
//					.add(R.id.leftmenu, menuFragment).commit();
			
		}
//		myDrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
//
//		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		myToggle = new ActionBarDrawerToggle(this, myDrawer, R.string.open_desc, R.string.close_desc){
//			@Override
//			public void onDrawerClosed(View drawerView) {
//				super.onDrawerClosed(drawerView);
//			}
//			
//			@Override
//			public void onDrawerOpened(View drawerView) {
//				super.onDrawerOpened(drawerView);
//			}
//		};
//		
//		
//		myToggle.setHomeAsUpIndicator(R.drawable.ic_drawer);
		myDrawer.setDrawerListener(myToggle);
	}
	
	Drawable icon;

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.myprofile, menu);
//		return true;
//	}
	
//	@Override
//	public void onConfigurationChanged(Configuration newConfig) {
//		super.onConfigurationChanged(newConfig);
//		myToggle.onConfigurationChanged(newConfig);
//	}
//
//	@Override
//	protected void onPostCreate(Bundle savedInstanceState) {
//		super.onPostCreate(savedInstanceState);
//		myToggle.syncState();
//	}
	



}
