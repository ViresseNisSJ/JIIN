package com.jiin.mypage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jiin.MyData;
import com.jiin.NetworkManager;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.R;

public class MyOptioninfoFragment extends Fragment {
	
	TextView heightView, bodyTypeView, drinkingView, smokeView, religionView, characterView, hobbyView;

	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_my_optioninfo, container, false);
    	
		
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
    	
    	final String[] body = getResources().getStringArray(R.array.bodyType);
    	final String[] drink = getResources().getStringArray(R.array.drinking);
    	final String[] smoke = getResources().getStringArray(R.array.smoke);
    	final String[] rel = getResources().getStringArray(R.array.religion);
    	final String[] ch = getResources().getStringArray(R.array.character);
    	final String[] hobby = getResources().getStringArray(R.array.hobby);
		
    	NetworkManager.getInstnace().getMyInform(getActivity(), new OnResultListener<MyData>(){
			@Override
			public void onSuccess(MyData result) {
				if(result.result.equals("success")){
					
					heightView.setText(result.data.height);
					bodyTypeView.setText(body[result.data.bodyType]);
					drinkingView.setText(drink[result.data.drinking]);
					smokeView.setText(smoke[result.data.smoke]);
					religionView.setText(rel[result.data.religion]);
					
					StringBuilder sb = new StringBuilder();
					for(Integer j: result.data.characters){
						sb.append(ch[j] + "  ");
					}
					String strText = sb.toString();
					characterView.setText(strText);
					
					StringBuilder sb2 = new StringBuilder();
					for(Integer a: result.data.hobbies){
						sb2.append(hobby[a]+ "  ");
					}
					String strText2 = sb2.toString();
					hobbyView.setText(strText2);
					
				}else if(result.result.equals("fail")){
					Toast.makeText(getActivity(), result.message, Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFail(int code) {
				Toast.makeText(getActivity(), code+"", Toast.LENGTH_SHORT).show();
			}
		});
    	
		
		
	}

	@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      

    }
}