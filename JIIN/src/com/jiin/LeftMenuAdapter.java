package com.jiin;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class LeftMenuAdapter extends BaseAdapter {

	Context mContext;
	ArrayList<LeftMenuItem> mitem = new ArrayList<LeftMenuItem>();

	@Override
	public int getCount() {
		return mitem.size();
	}

	public Object getItem(int position) {
		return mitem.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
 
//		LeftMenuView v;
//        if(convertView == null) {
//        	v = new LeftMenuView(mContext);
//        }else{
//        	v = (LeftMenuView)convertView;
//        }

		LeftMenuView v = new LeftMenuView(parent.getContext());
		v.setItemData(mitem.get(position));
		
        return v;


    }

	public void add(LeftMenuItem item) {
		mitem.add(item);
		notifyDataSetChanged();
	}



	
}
