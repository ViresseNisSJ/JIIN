package com.jiin.mypage;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jiin.KeepAdapter.OnAdapterClickListener;
import com.jiin.myprofile.qna.QnaListItem;
import com.jiin.otherprofile.OtherQNAFragment;

public class MyQnaAdapter extends BaseAdapter {

	ArrayList<QnaListItem> items = new ArrayList<QnaListItem>();
	Context mContext;
	
	public void add(QnaListItem item) {
		items.add(item);
		notifyDataSetChanged();
		
	}
	
	public void addAll(ArrayList<QnaListItem> qnaDatas){
		items.addAll(qnaDatas);
		notifyDataSetChanged();
	}
	
	
	public MyQnaAdapter(FragmentActivity fragmentActivity){
		mContext = fragmentActivity;
	}

	@Override
	public int getCount() {
		return items.size();
	}
	@Override
	public Object getItem(int position) {
		
		Fragment f = null;
		f= new OtherQNAFragment();
		Bundle b = new Bundle();
		b.putSerializable("qna", items.get(position));
		f.setArguments(b);
		
		return f;
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyQnaView v = new MyQnaView(parent.getContext());
		v.setItemData(items.get(position));
        return v;
	}
	
	OnAdapterClickListener mListener;	
	public void setOnAdapterClickListener(OnAdapterClickListener listener) {
		mListener = listener;
	}

	


	
}
