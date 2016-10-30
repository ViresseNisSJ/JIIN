package com.jiin.menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiin.NetworkManager;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.R;
import com.jiin.start.StartActivity;

public class WithdrawActivity extends ActionBarActivity{

	ListView listView;
	String reason;
	int position;
	LinearLayout reason1, reason2,reason3, reason4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdraw);
		
		/*액션바*/
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowCustomEnabled(true);//액션바에 있는 백키 onitemselected에서 조절
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(R.layout.actionbar);
		
		reason1 = (LinearLayout)findViewById(R.id.layout_1);
		reason1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				reason = reason1.toString();
				reason1.setBackgroundResource(R.color.grey);
				reason2.setBackgroundResource(R.color.progress_bg);
				reason3.setBackgroundResource(R.color.progress_bg);
				reason4.setBackgroundResource(R.color.progress_bg);
			}
		});
		reason2 = (LinearLayout)findViewById(R.id.layout_2);
		reason2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				reason = reason2.toString();
				reason1.setBackgroundResource(R.color.progress_bg);
				reason2.setBackgroundResource(R.color.grey);
				reason3.setBackgroundResource(R.color.progress_bg);
				reason4.setBackgroundResource(R.color.progress_bg);
			}
		});
		reason3 = (LinearLayout)findViewById(R.id.layout_3);
		reason3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				reason = reason3.toString();
				reason1.setBackgroundResource(R.color.progress_bg);
				reason2.setBackgroundResource(R.color.progress_bg);
				reason3.setBackgroundResource(R.color.grey);
				reason4.setBackgroundResource(R.color.progress_bg);
			}
		});
		reason4 = (LinearLayout)findViewById(R.id.layout_4);
		reason4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final LinearLayout layout = (LinearLayout)View.inflate(WithdrawActivity.this, R.layout.dialog_withdraw, null);
	    		final EditText editPopup = (EditText)layout.findViewById(R.id.edit_reason);
				
				new AlertDialog.Builder(WithdrawActivity.this)
	    		.setTitle("탈퇴이유 직접입력")
	    		.setView(layout)
	    		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String strText = editPopup.getText().toString();
						reason=strText;
					}
				})
	    		.show();
				reason1.setBackgroundResource(R.color.progress_bg);
				reason2.setBackgroundResource(R.color.progress_bg);
				reason3.setBackgroundResource(R.color.progress_bg);
				reason4.setBackgroundResource(R.color.grey);
			}
		});
		
		
		
		Button btnWithdraw = (Button)findViewById(R.id.btn_withdraw);
		btnWithdraw.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NetworkManager.getInstnace().putWithdraw(WithdrawActivity.this, reason, new OnResultListener<WithdrawData>() {

					@Override
					public void onSuccess(WithdrawData result) {
						if(result.result.equals("success")){
							startActivity(new Intent(WithdrawActivity.this, StartActivity.class));
							finish();
						}
						else if(result.result.equals("fail")){
							Toast.makeText(WithdrawActivity.this, result.message, Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onFail(int code) {
						Toast.makeText(WithdrawActivity.this, code+"", Toast.LENGTH_SHORT).show();
					}
					
				} );
				
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
