package com.jiin.otherprofile;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jiin.KeepAdapter.OnAdapterClickListener;

public class OtherQnaAdapter extends BaseAdapter {

	ArrayList<OtherQnaItem> items = new ArrayList<OtherQnaItem>();
	Context mContext;
	
	public void add(OtherQnaItem item) {
		items.add(item);
		notifyDataSetChanged();
		
	}
	
	public void addAll(ArrayList<OtherQnaItem> qnaDatas){
		items.addAll(qnaDatas);
		notifyDataSetChanged();
	}
	
	
	public OtherQnaAdapter(FragmentActivity fragmentActivity){
		mContext = fragmentActivity;
	}

	@Override
	public int getCount() {
		return items.size();
	}
	@Override
	public Object getItem(int position) {
		
		//return items.get(position);
		
		Fragment f = null;
		f= new OtherQNAFragment();
		Bundle b = new Bundle();
		b.putSerializable("message", items.get(position));
		f.setArguments(b);
		
		return f;
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		OtherQnaView v = new OtherQnaView(mContext);
		v.setItemData(items.get(position));
		return v;
	}
	
	OnAdapterClickListener mListener;	
	public void setOnAdapterClickListener(OnAdapterClickListener listener) {
		mListener = listener;
	}

	


	
}
