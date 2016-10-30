package com.jiin.menu.message;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MessageAdapter extends BaseAdapter {
	
	Context mContext;
	
	ArrayList<MessageResponse> messageList = new ArrayList<MessageResponse>();
	//ArrayList<MessageContentItem> item = new ArrayList<MessageContentItem>();
	private static final int VIEW_TYPE_COUNT = 2;
	private static final int VIEW_TYPE_SEND = 0;
	private static final int VIEW_TYPE_RECEIVE = 1;
	
//	public MessageAdapter(Context context, String reqId){
//		mContext = context;
//		requestId = reqId;
//	}
	
	public void add(MessageResponse item){
		messageList.add(item);
		notifyDataSetChanged();
	}

	public void addAll(ArrayList<MessageResponse> list) {
		messageList.addAll(list);
		notifyDataSetChanged();
	}
	
	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE_COUNT;
	}
	
	@Override
	public int getItemViewType(int position) {
		MessageResponse data = messageList.get(position);
		Message mes = messageList.get(position).message;
		Log.i("receiver",data.receiver._id);
		Log.i("sender",mes.messages.get(mes.messages.size()-1).sender);
		if (data.receiver._id.equals(mes.messages.get(mes.messages.size()-1).sender)) {
			return VIEW_TYPE_RECEIVE;
		} else {
			return VIEW_TYPE_SEND;
		} 
	}

	@Override
	public int getCount() {
		return messageList.size();
	}

	@Override
	public MessageResponse getItem(int position) {
		return messageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		switch(getItemViewType(position)){
		case VIEW_TYPE_RECEIVE: {
			MessageView v;
			if (convertView == null && convertView instanceof MessageView) {
				v = (MessageView)convertView;
			} else {
				v = new MessageView(parent.getContext());
			}	
			v.setItemData((MessageResponse) messageList.get(position));
			return v;
		}
		case VIEW_TYPE_SEND:
		default: {
			MessageRigthView v;
			if (convertView == null && convertView instanceof MessageView) {
				v = (MessageRigthView)convertView;
			} else {
				v = new MessageRigthView(parent.getContext());
			}	
			v.setItemData((MessageResponse) messageList.get(position));
			return v;
		}
		}
		
	}
	
	
}
