package com.jiin.myprofile;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiin.NetworkManager;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.R;
import com.jiin.myprofile.qna.QNAActivity;


public class OptionFragment extends Fragment {
	
	public OptionFragment(){
		this.setHasOptionsMenu(true);
	}
	TextView text_height, text_bodyType, text_drinking, text_smoke, text_religion, text_character, text_hobby;
	LinearLayout layout1,layout2,layout3,layout4,layout5,layout6,layout7;
	String height, bodyType, drinking, smoke, religion, character, hobby;
	int height2=-1; 
	int bodyType2=-1;
	int drinking2=-1;
	int smoke2=-1;
	int religion2=-1;
	ArrayList<Integer> character2 = new ArrayList<Integer>();
	ArrayList<Integer> hobby2 = new ArrayList<Integer>();
	ImageView check1, check2, check3, check4, check5, check6, check7;
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_optioninfo, container, false);
    	
    	layout1 = (LinearLayout)view.findViewById(R.id.option_height);
    	text_height = (TextView)view.findViewById(R.id.text_height);
    	text_height.setText(height);
    	check1 = (ImageView)view.findViewById(R.id.image_check);
    	layout1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final LinearLayout layout = (LinearLayout)View.inflate(getActivity(), R.layout.dialog_layout, null);
	    		final EditText editPopup = (EditText)layout.findViewById(R.id.edit_height);
	    		editPopup.setText(text_height.getText().toString());
				
				
				new AlertDialog.Builder(getActivity())
	    		.setTitle("키")
	    		.setView(layout)
	    		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String strText = editPopup.getText().toString();
						text_height.setText(strText);
						height=strText;
						check1.setImageResource(R.drawable.ic_checkmark_after);
					}
				})
	    		.show();
				
			}
		});
    	
    	layout2 = (LinearLayout)view.findViewById(R.id.option_bodyType);
    	text_bodyType = (TextView)view.findViewById(R.id.text_bodyType);
    	text_bodyType.setText(bodyType);
    	check2 = (ImageView)view.findViewById(R.id.image_check2);
    	layout2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("체형");
				final String[] array = getResources().getStringArray(R.array.bodyType);
				builder.setItems(array, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String strText = array[which];
						text_bodyType.setText(strText);
						bodyType=strText;
						bodyType2=which;
						check2.setImageResource(R.drawable.ic_checkmark_after);
					}
				});
				builder.create().show();
				
			}
		});
    	
    	layout3 = (LinearLayout)view.findViewById(R.id.option_drinking);
    	text_drinking = (TextView)view.findViewById(R.id.text_drinking);
    	text_drinking.setText(drinking);
    	check3 = (ImageView)view.findViewById(R.id.image_check3);
    	layout3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("음주 여부");
				final String[] array = getResources().getStringArray(R.array.drinking);
				builder.setItems(array, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String strText = array[which];
						text_drinking.setText(strText);
						drinking=strText;
						drinking2=which;
						check3.setImageResource(R.drawable.ic_checkmark_after);
					}
				});
				builder.create().show();
				
			}
		});
    	
    	layout4 = (LinearLayout)view.findViewById(R.id.option_smoke);
    	text_smoke = (TextView)view.findViewById(R.id.text_smoke);
    	text_smoke.setText(smoke);
    	check4 = (ImageView)view.findViewById(R.id.image_check4);
    	layout4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("흡연 여부");
				final String[] array = getResources().getStringArray(R.array.smoke);
				builder.setItems(array, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String strText = array[which];
						text_smoke.setText(strText);
						smoke=strText;
						smoke2=which;
						check4.setImageResource(R.drawable.ic_checkmark_after);
					}
				});
				builder.create().show();
				
			}
		});
    	
    	layout5 = (LinearLayout)view.findViewById(R.id.option_religion);
    	text_religion = (TextView)view.findViewById(R.id.text_religion);
    	text_religion.setText(religion);
    	check5 = (ImageView)view.findViewById(R.id.image_check5);
    	layout5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("종교");
				final String[] array = getResources().getStringArray(R.array.religion);
				builder.setItems(array, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String strText = array[which];
						text_religion.setText(strText);
						religion=strText;
						religion2=which;
						check5.setImageResource(R.drawable.ic_checkmark_after);
					}
				});
				builder.create().show();
				
			}
		});
    	
    	layout6 = (LinearLayout)view.findViewById(R.id.option_character);
    	text_character = (TextView)view.findViewById(R.id.text_character);
    	text_character.setText(character);
    	check6 = (ImageView)view.findViewById(R.id.image_check6);
    	layout6.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {	

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("성격");
				builder.setMessage("두개 선택해 주세요");
				final String[] array = getResources().getStringArray(R.array.character);
				final boolean[] selected = {false,false, false, false, false, false, false, false, false, false, false, false, false};
				builder.setMultiChoiceItems(array, selected, new OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						selected[which] = isChecked;
						int cnt = 0;
	                    for (int i = 0; i <selected.length; i++) {
	                        if (selected[i]) {
	                            cnt++;
	                        }
	                    }
	                    
	                    if (cnt > 2) {
	                        Toast.makeText(getActivity(), "최대 2개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
	                        selected[which] = false;
	                        ((AlertDialog) dialog).getListView().setItemChecked(which, false);
	                    }
					}
				});
				builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						character2.clear();
						StringBuilder sb = new StringBuilder();
						int count = 0;
						
						for (int i = 0; i < selected.length; i++) {
							if (selected[i]) {
								sb.append(array[i]).append("  ");
								character2.add(i);
								count++;
							}
						}
						String strText = sb.toString();
						text_character.setText(strText);
						character=strText;
						check6.setImageResource(R.drawable.ic_checkmark_after);
					}
				});
				builder.create().show();
				
			}
		});
    	
    	layout7 = (LinearLayout)view.findViewById(R.id.option_hobby);
    	text_hobby = (TextView)view.findViewById(R.id.text_hobby);
    	text_hobby.setText(hobby);
    	check7 = (ImageView)view.findViewById(R.id.image_check7);
    	layout7.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("취미");
				builder.setMessage("두개 선택해 주세요");
				final String[] array = getResources().getStringArray(R.array.hobby);
				final boolean[] selected = {false,false, false, false, false, false, false, false, false, false, false, false, false};
				builder.setMultiChoiceItems(array, selected, new OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						selected[which] = isChecked;
						int cnt2 = 0;
	                    for (int i = 0; i <selected.length; i++) {
	                        if (selected[i]) {
	                            cnt2++;
	                        }
	                    }
	                    
	                    if (cnt2 > 2) {
	                        Toast.makeText(getActivity(), "최대 2개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
	                        selected[which] = false;
	                        ((AlertDialog) dialog).getListView().setItemChecked(which, false);
	                    }
					}
				});
				builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						hobby2.clear();
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < selected.length; i++) {
							if (selected[i]) {
								sb.append(array[i]).append("  ");
								hobby2.add(i);
							}
						}
						String strText = sb.toString();
						text_hobby.setText(strText);
						hobby=strText;
						check7.setImageResource(R.drawable.ic_checkmark_after);
					}
				});
				builder.create().show();
				
			}
		});
    	
    	
    	
    	return view;
    }
    
   
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      
    }
    
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.myprofile, menu);
	}
	private LayoutInflater getMenuInflater() {
		// TODO Auto-generated method stub
		return null;
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		
		switch(item.getItemId()){		
		case R.id.menu_complete:
			
			if(height.equals("") || bodyType2==-1 || drinking2==-1 || smoke2==-1 || religion2==-1){
				Toast.makeText(getActivity(), "필수항목 모두 답해주세요", Toast.LENGTH_SHORT).show();
			}else{
				if(character2.size()<2 || hobby2.size()<2){
					Toast.makeText(getActivity(), "성격과 취미 항목은 2개씩 선택해주세요", Toast.LENGTH_SHORT).show();
					return true;
				}else{
					NetworkManager.getInstnace().putMyOptionInform(getActivity(), height, bodyType2, drinking2, smoke2, religion2, character2, hobby2, new OnResultListener<OptionData>() {

						@Override
						public void onSuccess(OptionData result) {
							
							if(result.result.equals("success")){
								startActivity(new Intent(getActivity(), QNAActivity.class));
								getActivity().finish();
							}else if(result.result.equals("fail")){
								Toast.makeText(getActivity(), result.message, Toast.LENGTH_SHORT).show();
							}
							
						}

						@Override
						public void onFail(int code) {
							Toast.makeText(getActivity(), code+"", Toast.LENGTH_SHORT).show();
						}
						
					} );
					
					return true;
				}
			}
			
		}

		return super.onOptionsItemSelected(item);
	}
}
