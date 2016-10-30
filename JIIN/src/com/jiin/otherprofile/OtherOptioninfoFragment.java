package com.jiin.otherprofile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiin.R;
import com.jiin.User;


public class OtherOptioninfoFragment extends Fragment {
	
	User user;
	TextView heightView, bodyTypeView, drinkingView, smokeView, religionView, characterView, hobbyView;
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_other_optioninfo, container, false);
    	
    	Intent intent = getActivity().getIntent();
		user = (User)intent.getSerializableExtra("user");
		
		heightView = (TextView)view.findViewById(R.id.text_height);
		bodyTypeView = (TextView)view.findViewById(R.id.text_bodyType);
		drinkingView = (TextView)view.findViewById(R.id.text_drinking);
		smokeView = (TextView)view.findViewById(R.id.text_smoke);
		religionView = (TextView)view.findViewById(R.id.text_religion);
		characterView = (TextView)view.findViewById(R.id.text_character);
		hobbyView = (TextView)view.findViewById(R.id.text_hobby);
		
		initData();
    	
    	return view;
    }
   
    private void initData() {
		
    	String[] body = getResources().getStringArray(R.array.bodyType);
    	String[] drink = getResources().getStringArray(R.array.drinking);
    	String[] smoke = getResources().getStringArray(R.array.smoke);
    	String[] rel = getResources().getStringArray(R.array.religion);
    	String[] ch = getResources().getStringArray(R.array.character);
    	String[] hobby = getResources().getStringArray(R.array.hobby);
    	
		heightView.setText(user.height);
		bodyTypeView.setText(body[user.bodyType]);
		drinkingView.setText(drink[user.drinking]);
		smokeView.setText(smoke[user.smoke]);
		religionView.setText(rel[user.religion]);
		
		StringBuilder sb = new StringBuilder();
		for(Integer j: user.characters){
			sb.append(ch[j] + "  ");
		}
		String strText = sb.toString();
		characterView.setText(strText);
		
		StringBuilder sb2 = new StringBuilder();
		for(Integer a: user.hobbies){
			sb2.append(hobby[a]+ "  ");
		}
		String strText2 = sb2.toString();
		hobbyView.setText(strText2);
		
	}

	@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      

    }
}
