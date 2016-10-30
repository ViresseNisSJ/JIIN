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
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiin.NetworkManager;
import com.jiin.R;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.myprofile.OptionData;

public class QnaDetailActivity extends ActionBarActivity{
	
	public static final int QNA_DETAIL_ACTIVITY_RESULT_OK = 100;
	
	DrawerLayout myDrawer;
	ActionBarDrawerToggle myToggle;
	ArrayList<QnaListItem> qnaList = new ArrayList<QnaListItem>();
	
	TextView numView, titleView;
	ListView listView;
	QnaDetailAdapter mAdapter;
	int position;
	String question_id; 
	int myAnswer = -1;
	ArrayList<Integer> wishAnswer = new ArrayList<Integer>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qna_detail);
		
		/*액션바*/
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowCustomEnabled(true);//액션바에 있는 백키 onitemselected에서 조절
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(R.layout.actionbar);
		
		
		Intent intent = getIntent();
    	qnaList = (ArrayList<QnaListItem>)intent.getSerializableExtra("qnaList");
    	position = intent.getIntExtra("position", -1);
    	
    	mAdapter = new QnaDetailAdapter(this, qnaList.get(position).myAnswer, qnaList.get(position).wishAnswer);
    	numView = (TextView)findViewById(R.id.text_Qnum);
    	titleView = (TextView)findViewById(R.id.text_Q);
    	listView = (ListView)findViewById(R.id.list_detail_qna);
    	listView.setAdapter(mAdapter);
		
    	initData();
	}
	
	
	private void initData() {
		numView.setText(position+1 +"번째 질문을 답하고 있습니다");
    	titleView.setText("Q "+qnaList.get(position).question);
    	myAnswer = qnaList.get(position).myAnswer;
    	wishAnswer = qnaList.get(position).wishAnswer;
    	mAdapter.setChooseAnswers(myAnswer, wishAnswer);
    	
    	mAdapter.addAll(qnaList.get(position).answers);
    	
    	
    	Button btnNext = (Button)findViewById(R.id.btn_right);
    	if(position == qnaList.size()-1){
    		btnNext.setVisibility(View.INVISIBLE);
    	}else{
    		btnNext.setVisibility(View.VISIBLE);
    	}
    	btnNext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				question_id = qnaList.get(position).question_id;
				myAnswer = mAdapter.getMyAnswer();
				wishAnswer.clear();
				SparseBooleanArray array = mAdapter.getWishAnswer();
				for (int i = 0; i <array.size(); i++) {
					int position = array.keyAt(i);
					if (array.get(position)) {
						wishAnswer.add(position);
					}
				}
				
				if(myAnswer == -1) {
					if(wishAnswer.size() > 0){
						Toast.makeText(QnaDetailActivity.this, "질문에 대한 나의 대답을 작성해주세요.", Toast.LENGTH_SHORT).show();
						
					}else{
						mAdapter.clear();
						position++;
						initData();
					}
					return;
					
				}else if(wishAnswer.size() <1){
					if(myAnswer != -1){
						Toast.makeText(QnaDetailActivity.this, "질문에 대한 상대방의 대답을 작성해주세요.", Toast.LENGTH_SHORT).show();
						
					}else{
						mAdapter.clear();
						position++;
						initData();
					}
					return;
				}
				
				NetworkManager.getInstnace().putResponseQna(QnaDetailActivity.this, question_id, myAnswer, wishAnswer, new OnResultListener<OptionData>() {
					
					@Override
					public void onSuccess(OptionData result) {
						if(result.result.equals("success")) {
							qnaList.get(position).setQnA(myAnswer, wishAnswer);
							mAdapter.clear();
							position++;
							initData();
						} else {
							Toast.makeText(QnaDetailActivity.this, result.message, Toast.LENGTH_SHORT).show();
						}
					}
					
					@Override
					public void onFail(int code) {
						Toast.makeText(QnaDetailActivity.this, code +"", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
    	
    	
    	Button btnPre = (Button)findViewById(R.id.btn_left);
    	if(position == 0){
    		btnPre.setVisibility(View.INVISIBLE);
    	}else{
    		btnPre.setVisibility(View.VISIBLE);
    	}
    	btnPre.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				question_id = qnaList.get(position).question_id;
				myAnswer = mAdapter.getMyAnswer();
				wishAnswer.clear();
				SparseBooleanArray array = mAdapter.getWishAnswer();
				for (int i = 0; i <array.size(); i++) {
					int position = array.keyAt(i);
					if (array.get(position)) {
						wishAnswer.add(position);
					}
				}
				
				if(myAnswer == -1) {
					if(wishAnswer.size() > 0){
						Toast.makeText(QnaDetailActivity.this, "질문에 대한 나의 대답을 작성해주세요.", Toast.LENGTH_SHORT).show();
						
					}else{
						mAdapter.clear();
						position--;
						initData();
					}
					return;
					
				}else if(wishAnswer.size() <1){
					if(myAnswer != -1){
						Toast.makeText(QnaDetailActivity.this, "질문에 대한 상대방의 대답을 작성해주세요.", Toast.LENGTH_SHORT).show();
						
					}else{
						mAdapter.clear();
						position--;
						initData();
					}
					return;
				}
				
				NetworkManager.getInstnace().putResponseQna(QnaDetailActivity.this, question_id, myAnswer, wishAnswer, new OnResultListener<OptionData>() {
					
					@Override
					public void onSuccess(OptionData result) {
						if(result.result.equals("success")) {
							qnaList.get(position).setQnA(myAnswer, wishAnswer);
							mAdapter.clear();
							position--;
							initData();
						} else {
							Toast.makeText(QnaDetailActivity.this, result.message, Toast.LENGTH_SHORT).show();
						}
					}
					
					@Override
					public void onFail(int code) {
						Toast.makeText(QnaDetailActivity.this, code +"", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
    	
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int id = item.getItemId();
		if(id == android.R.id.home){
			super.onBackPressed();
			return true;
		}else if(id == R.id.menu_complete){
			question_id = qnaList.get(position).question_id;
			myAnswer = mAdapter.getMyAnswer();
			wishAnswer.clear();
			SparseBooleanArray array = mAdapter.getWishAnswer();
			for (int i = 0; i <array.size(); i++) {
				int position = array.keyAt(i);
				if (array.get(position)) {
					wishAnswer.add(position);
				}
			}
			
			if(myAnswer == -1) {
				if(wishAnswer.size() > 0){
					Toast.makeText(QnaDetailActivity.this, "질문에 대한 나의 대답을 작성해주세요.", Toast.LENGTH_SHORT).show();
					
				}else{
					Toast.makeText(QnaDetailActivity.this, "질문에 답해주세요.", Toast.LENGTH_SHORT).show();
					
				}
				return false;
				
			}else if(wishAnswer.size() <1){
				if(myAnswer != -1){
					Toast.makeText(QnaDetailActivity.this, "질문에 대한 상대방의 대답을 작성해주세요.", Toast.LENGTH_SHORT).show();
					
				}else{
					Toast.makeText(QnaDetailActivity.this, "질문에 답해주세요.", Toast.LENGTH_SHORT).show();
					
				}
				return false;
			}
			
			NetworkManager.getInstnace().putResponseQna(QnaDetailActivity.this, question_id, myAnswer, wishAnswer, new OnResultListener<OptionData>() {
				
				@Override
				public void onSuccess(OptionData result) {
					if(result.result.equals("success")) {
						qnaList.get(position).setQnA(myAnswer, wishAnswer);
						Intent data = new Intent();
						data.putExtra("qnaList", qnaList);
						setResult(Activity.RESULT_OK, data);
						finish();
						Log.i("percent",result.data);
					} else {
						Toast.makeText(QnaDetailActivity.this, result.message, Toast.LENGTH_SHORT).show();
					}
				}
				
				@Override
				public void onFail(int code) {
					Toast.makeText(QnaDetailActivity.this, code +"", Toast.LENGTH_SHORT).show();
				}
			});
			

			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.qna_menu, menu);
		return true;
	}
	
}
