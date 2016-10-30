package com.jiin.menu.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiin.CheckMessageActivity;
import com.jiin.KeepData;
import com.jiin.LikeMessageActivity;
import com.jiin.NetworkManager;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.R;

public class MessageContentActivity extends ActionBarActivity{
	
	ListView listView;
	MessageContentAdapter mAdapter;
	String requestId, nickName;
	int count;
	String lastId , message;
	EditText messageContent;
	LinearLayout layout_message;
	Button btnSend;
	TextView nick1, nick2 , textview;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_message_content);
	        
	        /*�׼ǹ�*/
			android.support.v7.app.ActionBar actionBar = getSupportActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowCustomEnabled(true);//�׼ǹٿ� �ִ� ��Ű onitemselected���� ����
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setCustomView(R.layout.actionbar);
			
	        Intent intent = getIntent();
	        requestId = intent.getStringExtra("requestId");
	        nickName = intent.getStringExtra("nickName");
	        
	        listView = (ListView)findViewById(R.id.list_message);
	        mAdapter = new MessageContentAdapter(MessageContentActivity.this, requestId);
	        listView.setAdapter(mAdapter);
	        
	        messageContent = (EditText)findViewById(R.id.text_message);
	        layout_message = (LinearLayout)findViewById(R.id.layout_message);
	        btnSend = (Button)findViewById(R.id.btn_sendMessage);
	        
	        nick1 = (TextView)findViewById(R.id.text_nick);
	        nick1.setText(nickName+"�����κ��� ������ �����߽��ϴ�!");
	        
	        textview = (TextView)findViewById(R.id.text_message_html);
	        textview.setText(Html.fromHtml("�߰� ������ ���� <b>īī���� ID</b>�� <b>����ó</b>��<br>���濡�� �����ּ���~!"));
	        
	        initData();
	        
	        
	 }

	 private void initData() {
			NetworkManager.getInstnace().getMessage(this, requestId, new OnResultListener<MessageContentData>() {
				
				@Override
				public void onSuccess(MessageContentData result) {
					if(result.result.equals("success")) {
						mAdapter.addAll(result.datas);
						count = result.datas.size();
						lastId = result.datas.get(result.datas.size()-1).sender._id;
						Log.i("yourID",requestId);
						Log.i("MyId",lastId);
						if(requestId.equals(lastId)){
				        	if(count<3){
				        		layout_message.setVisibility(View.VISIBLE);
				        		btnSend.setVisibility(View.VISIBLE);
				        		btnSend.setOnClickListener(new View.OnClickListener() {
									
									@Override
									public void onClick(View v) {
										message = messageContent.getText().toString();
										
										if(message.length()<6){
											Toast.makeText(MessageContentActivity.this, "������ 6���� �̻� ���������� �ۼ����ּ���", Toast.LENGTH_SHORT).show();
											return;
										}else{
											NetworkManager.getInstnace().putSendMessage(MessageContentActivity.this, requestId, message, new OnResultListener<KeepData>() {
												
												@Override
												public void onSuccess(KeepData result) {
													if(result.result.equals("success")) {
														Toast.makeText(MessageContentActivity.this, "������ ���޵Ǿ����ϴ�",Toast.LENGTH_SHORT).show();
														Intent intent = new Intent(MessageContentActivity.this, CheckMessageActivity.class);
														intent.putExtra("nickname", nickName);
														intent.putExtra("where", 2);
														startActivity(intent);
														finish();
													} else {
														Toast.makeText(MessageContentActivity.this, result.message,Toast.LENGTH_SHORT).show();
													}
												}
												
												@Override
												public void onFail(int code) {
													Toast.makeText(MessageContentActivity.this, "code - " + code, Toast.LENGTH_SHORT).show();
												}
											});
										}			
									}
								});
				        	}else{
				        		btnSend.setVisibility(View.GONE);
				        	}
				        }else{
				        	btnSend.setVisibility(View.GONE);
				        }
												
					} else {
						Toast.makeText(MessageContentActivity.this, result.message, Toast.LENGTH_SHORT).show();
					}
				}
				
				@Override
				public void onFail(int code) {
					Toast.makeText(MessageContentActivity.this, code +"", Toast.LENGTH_SHORT).show();
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