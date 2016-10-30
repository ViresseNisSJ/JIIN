package com.jiin.menu;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jiin.KeepData;
import com.jiin.NetworkManager;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.R;

public class SetupActivity extends ActionBarActivity {
	
	LinearLayout changePw, withdraw;
	SwitchCompat systemAlarm, matchingAlarm, checkPublic;
	String i, j;
	
	Spinner relation;
	ArrayList<String> items;
	
	int relation2;
	
	TextView version;
	
	boolean isFirst = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup);
		
		/*¾×¼Ç¹Ù*/
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowCustomEnabled(true);//¾×¼Ç¹Ù¿¡ ÀÖ´Â ¹éÅ° onitemselected¿¡¼­ Á¶Àý
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(R.layout.actionbar);
		
		systemAlarm = (SwitchCompat)findViewById(R.id.switct_system);
		systemAlarm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(systemAlarm.isChecked()){
					i="Y";
					NetworkManager.getInstnace().putSystemAlarm(SetupActivity.this, i, new OnResultListener<KeepData>() {

						@Override
						public void onSuccess(KeepData result) {
							if(result.result.equals("success")){
								
							}else if(result.result.equals("fail")){
								Toast.makeText(SetupActivity.this, result.message, Toast.LENGTH_SHORT).show();
								systemAlarm.toggle();
							}
						}

						@Override
						public void onFail(int code) {
							Toast.makeText(SetupActivity.this, code+"", Toast.LENGTH_SHORT).show();
							systemAlarm.toggle();
						}
						
					} );
				}else{
					i="N";
					NetworkManager.getInstnace().putSystemAlarm(SetupActivity.this, i, new OnResultListener<KeepData>() {

						@Override
						public void onSuccess(KeepData result) {
							if(result.result.equals("success")){
								
							}else if(result.result.equals("fail")){
								Toast.makeText(SetupActivity.this, result.message, Toast.LENGTH_SHORT).show();
								systemAlarm.toggle();
							}
						}

						@Override
						public void onFail(int code) {
							Toast.makeText(SetupActivity.this, code+"", Toast.LENGTH_SHORT).show();
							systemAlarm.toggle();
						}
						
					} );
				}
				
			}
		});
		
		matchingAlarm = (SwitchCompat)findViewById(R.id.switct_push);
		matchingAlarm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(matchingAlarm.isChecked()){
					j="Y";
					NetworkManager.getInstnace().putMatchingAlarm(SetupActivity.this, j, new OnResultListener<KeepData>() {

						@Override
						public void onSuccess(KeepData result) {
							if(result.result.equals("success")){
								
							}else if(result.result.equals("fail")){
								Toast.makeText(SetupActivity.this, result.message, Toast.LENGTH_SHORT).show();
								matchingAlarm.toggle();
							}
						}

						@Override
						public void onFail(int code) {
							Toast.makeText(SetupActivity.this, code+"", Toast.LENGTH_SHORT).show();
							matchingAlarm.toggle();
						}
						
					} );
				}else{
					j="N";
					NetworkManager.getInstnace().putMatchingAlarm(SetupActivity.this, j, new OnResultListener<KeepData>() {

						@Override
						public void onSuccess(KeepData result) {
							if(result.result.equals("success")){
							}else if(result.result.equals("fail")){
								Toast.makeText(SetupActivity.this, result.message, Toast.LENGTH_SHORT).show();
								matchingAlarm.toggle();
							}
						}

						@Override
						public void onFail(int code) {
							Toast.makeText(SetupActivity.this, code+"", Toast.LENGTH_SHORT).show();
							matchingAlarm.toggle();
						}
						
					} );
				}
			}
		});
		
		checkPublic = (SwitchCompat)findViewById(R.id.switch_expose);
		checkPublic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(checkPublic.isChecked()){
					j="Y";
					NetworkManager.getInstnace().putCheckPublic(SetupActivity.this, j, new OnResultListener<KeepData>() {

						@Override
						public void onSuccess(KeepData result) {
							if(result.result.equals("success")){
								
							}else if(result.result.equals("fail")){
								Toast.makeText(SetupActivity.this, result.message, Toast.LENGTH_SHORT).show();
								checkPublic.toggle();
							}
						}

						@Override
						public void onFail(int code) {
							Toast.makeText(SetupActivity.this, code+"", Toast.LENGTH_SHORT).show();
							checkPublic.toggle();
						}
						
					} );
				}else{
					j="N";
					NetworkManager.getInstnace().putCheckPublic(SetupActivity.this, j, new OnResultListener<KeepData>() {

						@Override
						public void onSuccess(KeepData result) {
							if(result.result.equals("success")){
							}else if(result.result.equals("fail")){
								Toast.makeText(SetupActivity.this, result.message, Toast.LENGTH_SHORT).show();
								checkPublic.toggle();
							}
						}

						@Override
						public void onFail(int code) {
							Toast.makeText(SetupActivity.this, code+"", Toast.LENGTH_SHORT).show();
							checkPublic.toggle();
						}
						
					} );
				}
			}
		});
		
		relation = (Spinner)findViewById(R.id.spinner1);
		items = new ArrayList<String>();
		items.add("2ÃÌ");
		items.add("3ÃÌ");
		items.add("4ÃÌ");
		items.add("5ÃÌ");
		items.add("6ÃÌ");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
		relation.setAdapter(adapter);
		
		
		changePw = (LinearLayout)findViewById(R.id.layout_repw);
		changePw.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SetupActivity.this, ChangePwActivity.class));
			}
		});
		
		withdraw = (LinearLayout)findViewById(R.id.layout_withdraw);
		withdraw.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SetupActivity.this, WithdrawActivity.class));
			}
		});
		
		version = (TextView)findViewById(R.id.text_ver);
	
		initData();
		
	}
	
	OnItemSelectedListener selectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			if(isFirst){
				isFirst=false;
				return;
			}
			relation2 = position+2;
			NetworkManager.getInstnace().putRelation(SetupActivity.this, relation2, new OnResultListener<KeepData>() {

				@Override
				public void onSuccess(KeepData result) {
					if(result.result.equals("success")){
						
					}else if(result.result.equals("fail")){
						Toast.makeText(SetupActivity.this, result.message, Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onFail(int code) {
					Toast.makeText(SetupActivity.this, code+"", Toast.LENGTH_SHORT).show();
				}
				
			});
		}



		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	};
	
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
	}
	
	private void initData() {
		
		NetworkManager.getInstnace().getMySetting(SetupActivity.this, new OnResultListener<SetupData>() {

			@Override
			public void onSuccess(SetupData result) {
				if(result.result.equals("success")){
					if(result.data.systemAlarm.equals("Y")){
						systemAlarm.setChecked(true);
					}else{
						systemAlarm.setChecked(false);
					}
					
					if(result.data.matchingAlarm.equals("Y")){
						matchingAlarm.setChecked(true);
					}else{
						matchingAlarm.setChecked(false);
					}
					
					if(result.data.checkPublic.equals("Y")){
						checkPublic.setChecked(true);
					}else{
						checkPublic.setChecked(false);
					}
					
					relation.setSelection(result.data.introduceRange-2);
					relation.setOnItemSelectedListener(selectedListener);
					
					version.setText(result.data.version);
				}else if(result.result.equals("fail")){
					Toast.makeText(SetupActivity.this, result.message, Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFail(int code) {
				Toast.makeText(SetupActivity.this, code+"", Toast.LENGTH_SHORT).show();
			}
			
		} );
		
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



//	@Override
//	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//		
//		relation2 = position+2;
//		NetworkManager.getInstnace().putRelation(SetupActivity.this, relation2, new OnResultListener<KeepData>() {
//
//			@Override
//			public void onSuccess(KeepData result) {
//				if(result.result.equals("success")){
//					
//				}else if(result.result.equals("fail")){
//					Toast.makeText(SetupActivity.this, result.message, Toast.LENGTH_SHORT).show();
//				}
//			}
//
//			@Override
//			public void onFail(int code) {
//				Toast.makeText(SetupActivity.this, code+"", Toast.LENGTH_SHORT).show();
//			}
//			
//		});
//	}
//
//
//
//	@Override
//	public void onNothingSelected(AdapterView<?> parent) {
//		// TODO Auto-generated method stub
//		
//	}

}
