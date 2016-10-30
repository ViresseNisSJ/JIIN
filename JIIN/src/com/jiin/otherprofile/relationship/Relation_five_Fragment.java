package com.jiin.otherprofile.relationship;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.jiin.JiinConstant;
import com.jiin.MyUserListData;
import com.jiin.R;
import com.jiin.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class Relation_five_Fragment extends Fragment {
	
	ArrayList<User> relation = new ArrayList<User>();
	
	ImageView me, friend1, friend2, friend3, friend4, you;
	View meFrame, youFrame, bar;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view= inflater.inflate(R.layout.fragment_relation_5, container, false);

		relation = (ArrayList<User>) getActivity().getIntent().getSerializableExtra("relation");
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				me = (ImageView)view.findViewById(R.id.image_me);
				ImageLoader.getInstance().displayImage(JiinConstant.SERVER_URL+MyUserListData.getInstance().getMe().mainPicture, me);
				meFrame = view.findViewById(R.id.layout_me);
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						meFrame.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.relation_popup));
						meFrame.setVisibility(View.VISIBLE);
					}
				},JiinConstant.RELATION_DELAY_TIME);
				
				friend1 = (ImageView)view.findViewById(R.id.image_friend1);
				ImageLoader.getInstance().displayImage(JiinConstant.SERVER_URL+relation.get(4).mainPicture, friend1);
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						friend1.setVisibility(View.VISIBLE);
						friend1.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.relation_popup));
					}
				}, JiinConstant.RELATION_DELAY_TIME*2);
				
				friend2 = (ImageView)view.findViewById(R.id.image_friend2);
				ImageLoader.getInstance().displayImage(JiinConstant.SERVER_URL+relation.get(3).mainPicture, friend2);
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						friend2.setVisibility(View.VISIBLE);
						friend2.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.relation_popup));
					}
				}, JiinConstant.RELATION_DELAY_TIME*3);
				
				friend3 = (ImageView)view.findViewById(R.id.image_friend3);
				ImageLoader.getInstance().displayImage(JiinConstant.SERVER_URL+relation.get(2).mainPicture, friend3);
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						friend3.setVisibility(View.VISIBLE);
						friend3.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.relation_popup));
					}
				}, JiinConstant.RELATION_DELAY_TIME*4);
				
				friend4 = (ImageView)view.findViewById(R.id.image_friend4);
				ImageLoader.getInstance().displayImage(JiinConstant.SERVER_URL+relation.get(1).mainPicture, friend4);
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						friend4.setVisibility(View.VISIBLE);
						friend4.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.relation_popup));
					}
				}, JiinConstant.RELATION_DELAY_TIME*5);
				
				you = (ImageView)view.findViewById(R.id.image_you);
				ImageLoader.getInstance().displayImage(JiinConstant.SERVER_URL+relation.get(0).mainPicture, you);
				youFrame = view.findViewById(R.id.layout_you);
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						youFrame.setVisibility(View.VISIBLE);
						youFrame.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.relation_popup));
					}
				}, JiinConstant.RELATION_DELAY_TIME*6);
				
				bar = view.findViewById(R.id.layout_bar);
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						bar.setVisibility(View.VISIBLE);
						bar.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.relation_popup));
					}
				}, JiinConstant.RELATION_DELAY_TIME*7);
			}
		}, 100);
		
		
		
		return view;
	}
}
