package com.jiin.menu;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class NoticeAdapter extends BaseAdapter {
	
	ArrayList<NoticeGroupData> noticeList = new ArrayList<NoticeGroupData>();
	
	public void add(NoticeGroupData item){
		noticeList.add(item);
		notifyDataSetChanged();
	}

	public void addAll(ArrayList<NoticeGroupData> list) {
		noticeList.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return noticeList.size();
	}

	@Override
	public Object getItem(int position) {
		return noticeList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		NoticeGroupView v;
		if (convertView == null) {
			v = new NoticeGroupView(parent.getContext());
		} else {
			v = (NoticeGroupView)convertView;
		}	
		v.setItemData(noticeList.get(position));
		return v;
	}
	
	
}
