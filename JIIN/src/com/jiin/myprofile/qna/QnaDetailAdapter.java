package com.jiin.myprofile.qna;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class QnaDetailAdapter extends BaseAdapter implements QnaDetailView.OnCheckListener {

	ArrayList<String> items = new ArrayList<String>();
	Context mContext;
	
	int myAnswer;
	SparseBooleanArray wishAnswer = new SparseBooleanArray();
//	ArrayList<Integer> wishAnswer = new ArrayList<Integer>();

	
	public QnaDetailAdapter(FragmentActivity fragmentActivity, int myAnswer, ArrayList<Integer> wishAns){
		mContext = fragmentActivity;
		setChooseAnswers(myAnswer, wishAns);
		
	}
	
	public void setChooseAnswers(int myAnswer, ArrayList<Integer> wishAns) {
		this.myAnswer = myAnswer;
		for(Integer i : wishAns) {
			wishAnswer.append(i, true);
		}
	}

	public void addAll(ArrayList<String> qnaList) {
		items.addAll(qnaList);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return items.size();
	}
	@Override
	public Object getItem(int position) {
		return items.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		QnaDetailView v = (QnaDetailView)convertView;
		if (v == null) {
			v = new QnaDetailView(mContext);
			v.setOnCheckListener(this);
		}
		boolean my = (position == myAnswer)?true:false;
		v.setItemData(items.get(position), my, wishAnswer.get(position), position);
		return v;
	}
	
	
	public void clear() {
		items.clear();
		wishAnswer.clear();
		myAnswer = -1;
		
		notifyDataSetChanged();
	}

	@Override
	public void onMyChecked(View view, int position, boolean isCheck) {

		if (isCheck) {
			if (position != myAnswer) {
				myAnswer = position;
			} 
		} else {
			if (position == myAnswer) {
				myAnswer = -1;
			}
		}
		notifyDataSetChanged();
	}

	@Override
	public void onWishChecked(View view, int position, boolean isCheck) {
		if(isCheck) {
			wishAnswer.put(position, isCheck);
		} else {
			wishAnswer.delete(position);
		}
		notifyDataSetChanged();
	}


	public int getMyAnswer() {
		return myAnswer;
	}
	
	public SparseBooleanArray getWishAnswer() {
		return wishAnswer;
	}
	
}
