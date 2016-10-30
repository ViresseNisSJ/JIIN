package com.jiin.menu.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.jiin.NetworkManager;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.R;



public class MessageActivity extends ActionBarActivity{
	
	ListView listView;
	MessageAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		
		/*액션바*/
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowCustomEnabled(true);//액션바에 있는 백키 onitemselected에서 조절
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(R.layout.actionbar);
		
		mAdapter = new MessageAdapter();
		listView = (ListView)findViewById(R.id.list_message);
		listView.setEmptyView(findViewById(R.id.text_empty));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String requestId = mAdapter.getItem(position).receiver._id;
				String nickName = mAdapter.getItem(position).receiver.nickName;
				Intent intent = new Intent(MessageActivity.this, MessageContentActivity.class);
				intent.putExtra("requestId", requestId);
				intent.putExtra("nickName", nickName);
				startActivity(intent);
			}
		});
    	
		listView.setAdapter(mAdapter);
		
		initData();
	}
	
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
	}
	
	
	
	private void initData() {
		NetworkManager.getInstnace().getMessageList(this, new OnResultListener<MessageData>() {
			
			@Override
			public void onSuccess(MessageData result) {
				if(result.result.equals("success")) {
					mAdapter.addAll(result.datas);
					
				} else {
					Toast.makeText(MessageActivity.this, result.message, Toast.LENGTH_SHORT).show();
				}
			}
			
			@Override
			public void onFail(int code) {
				Toast.makeText(MessageActivity.this, code +"", Toast.LENGTH_SHORT).show();
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
