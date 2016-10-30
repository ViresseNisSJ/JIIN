package com.jiin.menu.message;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MessageContentAdapter extends BaseAdapter{
	
	Context mContext;
	String requestId;

	ArrayList<MessageContentItem> items = new ArrayList<MessageContentItem>();
	private static final int VIEW_TYPE_COUNT = 2;
	private static final int VIEW_TYPE_SEND = 0;
	private static final int VIEW_TYPE_RECEIVE = 1;
		
	
	public MessageContentAdapter(Context context, String reqId){
		mContext = context;
		requestId = reqId;
	}
	
	public void add(MessageContentItem item) {
		items.add(item);
		notifyDataSetChanged();
	}
	
	public void addAll(ArrayList<MessageContentItem> item) {
		items.addAll(item);
		notifyDataSetChanged();
	}
	
	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE_COUNT;
	}
	
	@Override
	public int getItemViewType(int position) {
		MessageContentItem data = items.get(position);
		if (data.sender._id.equals(requestId)) {
			return VIEW_TYPE_RECEIVE;
		} else {
			return VIEW_TYPE_SEND;
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
		switch(getItemViewType(position)){
		case VIEW_TYPE_RECEIVE: {
			MessageReceiveView view;
			if (convertView != null && (convertView instanceof MessageReceiveView)) {
				view = (MessageReceiveView) convertView;
			} else {
				view = new MessageReceiveView(parent.getContext());
			}
			view.setData((MessageContentItem) items.get(position));
			return view;
		}
		case VIEW_TYPE_SEND: {
			MessageSendView view;
			if (convertView != null && (convertView instanceof MessageSendView)) {
				view = (MessageSendView) convertView;
			} else {
				view = new MessageSendView(parent.getContext());
			}
			view.setData((MessageContentItem) items.get(position));
			return view;
		}
		default: {
			MessageSendView view;
			if (convertView != null && (convertView instanceof MessageSendView)) {
				view = (MessageSendView) convertView;
			} else {
				view = new MessageSendView(parent.getContext());
			}
			view.setData((MessageContentItem) items.get(position));
			return view;
		}
		}
	}

}
