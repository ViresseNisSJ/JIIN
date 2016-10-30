package com.jiin.menu;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jiin.R;

public class ShopActivity extends ActionBarActivity{
	
	TextView pay,pay2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);
		
		/*액션바*/
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowCustomEnabled(true);//액션바에 있는 백키 onitemselected에서 조절
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(R.layout.actionbar);
		
		pay = (TextView)findViewById(R.id.text_pay);
		
		
		Button btn_shop1 = (Button)findViewById(R.id.btn_shop1);
		btn_shop1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button btn_shop2 = (Button)findViewById(R.id.btn_shop2);
		btn_shop2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button btn_shop3 = (Button)findViewById(R.id.btn_shop3);
		btn_shop3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	
	}
	
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
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
