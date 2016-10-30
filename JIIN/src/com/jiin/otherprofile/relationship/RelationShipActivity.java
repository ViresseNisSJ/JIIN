package com.jiin.otherprofile.relationship;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.jiin.R;
import com.jiin.User;

public class RelationShipActivity extends ActionBarActivity{
	
	LinearLayout layout_relation;
	int relationCount;
	ArrayList<User> user = new ArrayList<User>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_relationship);
		
		/*액션바*/
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowCustomEnabled(true);//액션바에 있는 백키 onitemselected에서 조절
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(R.layout.actionbar);
		
		user = (ArrayList<User>) getIntent().getSerializableExtra("relation");
		relationCount = user.get(0).relationCount;
		
		Log.i("relationCount", relationCount+"");
		layout_relation = (LinearLayout)findViewById(R.id.layout_relationship);
		
		if(relationCount==2){
			getSupportFragmentManager().beginTransaction().add(R.id.layout_relationship, new Relation_two_Fragment()).commit();
		}else if(relationCount==3){
			getSupportFragmentManager().beginTransaction().add(R.id.layout_relationship, new Relation_three_Fragment()).commit();
		}else if(relationCount==4){
			getSupportFragmentManager().beginTransaction().add(R.id.layout_relationship, new Relation_four_Fragment()).commit();
		}else if(relationCount==5){
			getSupportFragmentManager().beginTransaction().add(R.id.layout_relationship, new Relation_five_Fragment()).commit();
		}else if(relationCount==6){
			getSupportFragmentManager().beginTransaction().add(R.id.layout_relationship, new Relation_six_Fragment()).commit();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int id = item.getItemId();
		if(id == android.R.id.home){
			super.onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
