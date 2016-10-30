package com.jiin;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class KeepAdapter extends BaseAdapter implements KeepView.OnKeepClickListener{

	ArrayList<User> items = new ArrayList<User>();
	Context mContext;
	
	public void add(User item) {
		items.add(item);
		notifyDataSetChanged();
		
	}
	
	public void addAll(ArrayList<User> datas){
		items.addAll(datas);
		notifyDataSetChanged();
	}
	
	public void clear() {
		items.clear();
		notifyDataSetChanged();
	}
	
	public KeepAdapter(FragmentActivity fragmentActivity){
		mContext = fragmentActivity;
	}

	@Override
	public int getCount() {
		return items.size();
	}
	@Override
	public Object getItem(int position) {
		
		Fragment f = null;
		f= new KeepFragment();
		Bundle b = new Bundle();
		b.putSerializable("keep", items.get(position));
		f.setArguments(b);
		
		return f;
		//return items.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		KeepView v = new KeepView(mContext);
		v.setOnKeepClickListener(this);
		v.setItemData(items.get(position));
		return v;
	}
	
	public interface OnAdapterClickListener {
		public void onProfileClicked(KeepAdapter keepAdapter, KeepView view, User mData);
		public void onLikeClicked(KeepAdapter keepAdapter, KeepView view, User mData);
		public void onPassClicked(KeepAdapter keepAdapter, KeepView view, User mData);
	}
	OnAdapterClickListener mListener;	
	public void setOnAdapterClickListener(OnAdapterClickListener listener) {
		mListener = listener;
	}
	
	@Override
	public void onProfileClicked(KeepView view, User mData) {
		if(mListener != null){
			mListener.onProfileClicked(this, view, mData);
		}
		
	}

	@Override
	public void onLikeClicked(KeepView view, User mData) {
		if(mListener != null){
			mListener.onLikeClicked(this, view, mData);
		}
	}

	@Override
	public void onPassClicked(KeepView view, User mData) {
		if(mListener != null){
			mListener.onPassClicked(this, view, mData);
		}
	}

	


	
}
