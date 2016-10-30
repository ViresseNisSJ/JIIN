package com.jiin.myprofile.qna;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jiin.KeepAdapter.OnAdapterClickListener;

public class QnaAdapter extends BaseAdapter {

	ArrayList<QnaListItem> items = new ArrayList<QnaListItem>();
	Context mContext;
	
	public void add(QnaListItem item) {
		items.add(item);
		notifyDataSetChanged();
		
	}
	
	public void clear() {
		items.clear();
		notifyDataSetChanged();
	}
	
	public void addAll(ArrayList<QnaListItem> datas){
		items.addAll(datas);
		notifyDataSetChanged();
	}
	
	public QnaAdapter(FragmentActivity fragmentActivity){
		mContext = fragmentActivity;
	}

	@Override
	public int getCount() {
		return items.size();
	}
	@Override
	public Object getItem(int position) {
		
		return items.get(position);
		
//		Fragment f = null;
//		f= new QNAFragment();
//		Bundle b = new Bundle();
//		b.putSerializable("keep", items.get(position));
//		f.setArguments(b);
//		
//		return f;
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		QnaView v = new QnaView(mContext);
		v.setItemData(items.get(position));
		return v;
	}
	
	OnAdapterClickListener mListener;	
	public void setOnAdapterClickListener(OnAdapterClickListener listener) {
		mListener = listener;
	}
	


	
}
