package com.jiin.otherprofile;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class OtherBasicinfoAdapter extends BaseAdapter{

	ArrayList<String> items = new ArrayList<String>();
	
	
	public void addAll(ArrayList<String> item) {
		items.addAll(item);
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
		OtherBasicView v;
		if(convertView == null){
			v = new OtherBasicView(parent.getContext());
		}else{
			v= (OtherBasicView)convertView;
		}
		v.setItemData(items.get(position));
		return v;
	}

	

	

}
