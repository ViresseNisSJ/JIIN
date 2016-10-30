package com.jiin.menu;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.jiin.NetworkManager;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.R;

public class NoticeActivity extends ActionBarActivity{
	
	ListView noticeList;
	NoticeAdapter nAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice);

		/*액션바*/
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowCustomEnabled(true);//액션바에 있는 백키 onitemselected에서 조절
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(R.layout.actionbar);
		
		nAdapter = new NoticeAdapter();
		noticeList = (ListView)findViewById(R.id.list_notice);
		noticeList.setEmptyView(findViewById(R.id.text_empty));
		noticeList.setAdapter(nAdapter);

		
		initData();
	
	}
	
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
	}

	private void initData() {
		NetworkManager.getInstnace().getNotice(this, new OnResultListener<NoticeData>() {
			
			@Override
			public void onSuccess(NoticeData result) {
				if(result.result.equals("success")) {
	
					nAdapter.addAll(result.datas);
					nAdapter.notifyDataSetChanged();
					
				} else {
					
				}
			}
			
			@Override
			public void onFail(int code) {
				
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
