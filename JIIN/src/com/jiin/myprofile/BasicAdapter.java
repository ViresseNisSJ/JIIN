package com.jiin.myprofile;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class BasicAdapter extends BaseAdapter{

	ArrayList<Pictures> items = new ArrayList<Pictures>();
	
	public void add(Pictures pictures){
		items.add(pictures);
		notifyDataSetChanged();
	}
	
	public void addAll(ArrayList<Pictures> pictures) {
		for(int i=2; i<pictures.size()+2; i++){
			items.remove(i);
		}
		for(int i=2; i<pictures.size()+1; i++){
			items.add(pictures.get(i));
			notifyDataSetChanged();
		}
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
		BasicView v;
		if(convertView == null){
			v = new BasicView(parent.getContext());
		}else{
			v= (BasicView)convertView;
		}
		v.setItemData(items.get(position));
		return v;
	}


	

	

}
