package com.jiin.myprofile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiin.R;

public class MyprofileFragment extends Fragment{

	FragmentTabHost tabHost;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view= inflater.inflate(R.layout.fragment_myprofile, container, false);
			
//		tabHost = (FragmentTabHost)view.findViewById(R.id.tabhost);
//		tabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
//		
//		tabHost.addTab(tabHost.newTabSpec("기본정보").setIndicator("기본정보"), BasicFragment.class ,null);
//        tabHost.addTab(tabHost.newTabSpec("부가정보").setIndicator("부가정보"), OptionFragment.class ,null);
//        tabHost.addTab(tabHost.newTabSpec("Q&A").setIndicator("Q&A"), QNAFragment.class ,null);

		
		return view;
    }
   
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      

    }
}