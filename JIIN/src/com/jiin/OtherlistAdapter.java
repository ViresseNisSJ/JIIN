package com.jiin;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class OtherlistAdapter extends FragmentStatePagerAdapter {
	
	ArrayList<User> items = new ArrayList<User>();
	
	
	public OtherlistAdapter(android.support.v4.app.FragmentManager fm) {
		super(fm);
	}
	
	
	public void add(User item){
		items.add(item);
		notifyDataSetChanged();
	}

	public void addAll(ArrayList<User> list, ArrayList<String> hide, String gender) {
		
		Log.i("list",list.size()+"");
		Log.i("hide",hide.size()+"");
		Log.i("gender",gender);
		
		for(int i=0; i<list.size(); i++){
			User user = list.get(i);
			if(user.checkPublic.equals("Y") && !user.gender.equals(gender) && user.relationCount>1 && hide.indexOf(user._id) == -1){
				items.add(user);
			}
		}
		notifyDataSetChanged();
	}
	
	public User get(int position) {
		return items.get(position);
	}

	@Override
	public Fragment getItem(int position) {
		Fragment f = null;
		f= new OtherProfileFragment();
		Bundle b = new Bundle();
		b.putSerializable("profile", items.get(position));
		f.setArguments(b);
		
		return f;
	}
	


	@Override
	public int getCount() {
		return items.size();
	}

	public View getView(int position, View convertView, ViewGroup parent){
		OtherlistView v = new OtherlistView(parent.getContext());
		//v.setItemData(items.get(position));
		return v;
	}
	
	public void remove(int position){
		items.remove(position);
		notifyDataSetChanged();
	}
	
	public int getItemPosition(Object object){
		return POSITION_NONE;
	}
	
//	@Override
//	public void destroyItem(View collection, int position, Object o) {
//	    View view = (View) o;
//	    ((ViewPager) collection).removeView(view);
//	    items.remove(position);
//	    view = null;
//	}
//	
//	@Override
//	public void notifyDataSetChanged(){
//		int key = 0;
//		for(int i = 0; i < items.size(); i++){
//			key = items.(i);
//			View view = items.get(key);
//			
//			
//		}
//		super.notifyDataSetChanged();
//	}

//	public interface OnAdapterClickListener {
//		public void onAdapterClicked(OtherlistAdapter olAdapter, OtherlistView view, OtherProfileItem data);
//
//	}
//	OnAdapterClickListener mListener;	
//	public void setOnAdapterClickListener(OnAdapterClickListener listener) {
//		mListener = listener;
//	}
//	
//	@Override
//	public void onImageClicked(OtherlistView view, OtherProfileItem data) {
//		if(mListener != null){
//			mListener.onAdapterClicked(this, view, data);
//		}
		

    
}