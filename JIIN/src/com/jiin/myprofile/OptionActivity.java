package com.jiin.myprofile;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;

import com.jiin.R;

public class OptionActivity extends ActionBarActivity{

	DrawerLayout myDrawer;

	ActionBarDrawerToggle myToggle;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);
		
		/*�׼ǹ�*/
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);//�׼ǹٿ� �ִ� ��Ű onitemselected���� ����
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(R.layout.actionbar);
		
		myDrawer = (DrawerLayout)findViewById(R.id.drawer66);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.contentcontainer, new OptionFragment()).commit();

			
		}

		myDrawer.setDrawerListener(myToggle);
	}
	
	Drawable icon;

	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		myToggle.onConfigurationChanged(newConfig);
	}


}
