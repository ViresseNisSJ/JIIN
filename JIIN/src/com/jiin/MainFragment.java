package com.jiin;


import com.jiin.OtherFragment.ResultReceiver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost.OnTabChangeListener;

public class MainFragment extends Fragment implements ResultReceiver{

	public static final int MAIN_LIKEMESSAGE_REQUEST_CODE = 105;
	public static final int MAIN_PROFILE_REQUEST_CODE = 200;
	public static final int MAIN_KEEP_REQUEST_CODE = 3657;
	
	static FragmentTabHost mtabHost;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view= inflater.inflate(R.layout.fragment_main, container, false);
		
		mtabHost = (FragmentTabHost)view.findViewById(R.id.tabhost);
		mtabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
		
		mtabHost.addTab(mtabHost.newTabSpec("full list").setIndicator(""), OtherFragment.class ,null);
        mtabHost.addTab(mtabHost.newTabSpec("keep list").setIndicator(""), KeepFragment.class ,null);
		
        mtabHost.getTabWidget().getChildAt(0).setTag("full list");
        mtabHost.getTabWidget().getChildAt(1).setTag("keep list");
        mtabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_full_select);
        mtabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_keep_none);
        
        mtabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				if(tabId.equals("full list")){
					mtabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_full_select);
					mtabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_keep_none);
				}else if(tabId.equals("keep list")){
					mtabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_full_none);
					mtabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_keep_select);
				}
			}
		});
                
        
//      //인디케이터
//        UnderlinePageIndicator indicator = (UnderlinePageIndicator)view.findViewById(R.id.indicator);
//          indicator.setViewPager(viewpager);
//          indicator.setFades(false);
        
		return view;
    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		Log.i("MainFragment","onActivityResult");
		
		Log.i("requestCode",requestCode+"");
		Log.i("MainFragment.MAIN_PROFILE_REQUEST_CODE",MainFragment.MAIN_PROFILE_REQUEST_CODE+"");
		Log.i("resultCode",resultCode+"");
		Log.i("Activity.RESULT_OK",Activity.RESULT_OK+"");
		
		if(requestCode==MAIN_LIKEMESSAGE_REQUEST_CODE && resultCode==Activity.RESULT_OK){
			Log.i("MainFragment_onActivityResult","MAIN_LIKEMESSAGE_REQUEST_CODE");
			OtherFragment f = (OtherFragment)getChildFragmentManager().findFragmentByTag("full list");
			f.olAdapter.remove(f.viewPager.getCurrentItem());
		}else if(requestCode==MAIN_PROFILE_REQUEST_CODE && resultCode==Activity.RESULT_OK){
			Log.i("MainFragment_onActivityResult","MAIN_PROFILE_REQUEST_CODE");
			OtherFragment f = (OtherFragment)getChildFragmentManager().findFragmentByTag("full list");
			f.olAdapter.remove(f.viewPager.getCurrentItem());
		}
	}
	
	public static void OnTabChanged(){
		 mtabHost.setCurrentTab(1);
	}
   
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

	@Override
	public void receiveResult() {
		
	}
}