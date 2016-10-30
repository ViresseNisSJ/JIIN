package com.jiin.mypage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost.OnTabChangeListener;

import com.jiin.R;

public class MypageFragment extends Fragment {
	FragmentTabHost otabHost;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view= inflater.inflate(R.layout.fragment_mypage, container, false);
		
		otabHost = (FragmentTabHost)view.findViewById(R.id.tabhost);
		otabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
		
		otabHost.addTab(otabHost.newTabSpec("기본정보").setIndicator(""), MyBasicinfoFragment.class ,null);
        otabHost.addTab(otabHost.newTabSpec("부가정보").setIndicator(""), MyOptioninfoFragment.class ,null);
        otabHost.addTab(otabHost.newTabSpec("Q&A").setIndicator(""), MyQNAFragment.class ,null);

        otabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.btn_left_on);
        otabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.btn_m_off);
        otabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.btn_qa_off);
        
        otabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				if(tabId.equals("기본정보")){
					otabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.btn_left_on);
			        otabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.btn_m_off);
			        otabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.btn_qa_off);
				}else if(tabId.equals("부가정보")){
					otabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.btn_left_off);
			        otabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.btn_m_on);
			        otabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.btn_qa_off);
				}else if(tabId.equals("Q&A")){
					otabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.btn_left_off);
			        otabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.btn_m_off);
			        otabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.btn_qa_on);
				}
			}
		});
        
		return view;
    }
   
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      

    }
}