package com.jiin.myprofile.qna;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jiin.NetworkManager;
import com.jiin.R;
import com.jiin.NetworkManager.OnResultListener;

public class QNAActivity extends ActionBarActivity {

	public static final int QNA_ACTIVITY_REQUEST_CODE = 1234;
	
	DrawerLayout myDrawer;
	//QNAFragment fragment = new QNAFragment();
	
	ListView listView;
	QnaAdapter mAdapter;
	ArrayList<QnaListItem> qnaList = new ArrayList<QnaListItem>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qna);
		
		/*액션바*/
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);//액션바에 있는 백키 onitemselected에서 조절
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(R.layout.actionbar);
		
		mAdapter = new QnaAdapter(this);
    	listView = (ListView)findViewById(R.id.list_qna);
    	listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(QNAActivity.this, QnaDetailActivity.class);
				intent.putExtra("qnaList", qnaList);
				intent.putExtra("position", position);
    			
    			startActivityForResult(intent, QNA_ACTIVITY_REQUEST_CODE);
			}
		});
    	
    	listView.setAdapter(mAdapter);
    	initData();
	}
	
	@Override
	protected void onActivityResult(int reqCode, int resCode, Intent data) {
		super.onActivityResult(reqCode, resCode, data);
		
		Log.i("QNAActivity1", reqCode+"");
		Log.i("QNAActivity2", QNA_ACTIVITY_REQUEST_CODE+"");
		Log.i("QNAActivity3", resCode+"");
		Log.i("QNAActivity4", Activity.RESULT_OK+"");
		
		if(reqCode==QNA_ACTIVITY_REQUEST_CODE && resCode==Activity.RESULT_OK){
//			ArrayList<QnaListItem> list = (ArrayList<QnaListItem>) data.getSerializableExtra("qnaList");
//			if(list != null){
//				qnaList.clear();
//				qnaList.addAll(list);
//				Log.i("qnaList", qnaList.size()+"");
//			}
			initData();
		}
	}
	
	private void initData() {
		NetworkManager.getInstnace().getQnaList(QNAActivity.this, null, new OnResultListener<QnaData>() {
			
			@Override
			public void onSuccess(QnaData result) {
				if(result.result.equals("success")) {
					mAdapter.clear();
					mAdapter.addAll(result.datas);
					qnaList = result.datas;
					
				} else {
					Toast.makeText(QNAActivity.this, result.message, Toast.LENGTH_SHORT).show();
				}
			}
			
			@Override
			public void onFail(int code) {
				Toast.makeText(QNAActivity.this, code +"", Toast.LENGTH_SHORT).show();
			}
		});
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.qna_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		
		switch(item.getItemId()){		
		case R.id.menu_complete:
			startActivity(new Intent(this, com.jiin.MainActivity.class));
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	

}

