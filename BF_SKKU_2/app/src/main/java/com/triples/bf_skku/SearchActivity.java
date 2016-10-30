package com.triples.bf_skku;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity {

    EditText numClassroom;
    TextView name, type;
    LinearLayout layout1, layout2;
    ImageButton moveBuilding;

    /*엑셀*/
    private NotesDbAdapter dbAdapter;
    private static final String TAG = "NotesDbAdapter";

    String buildingEng;
    String buildingName;
    String classType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_search);

        /*엑셀*/
        this.dbAdapter = new NotesDbAdapter(this);

        name = (TextView)findViewById(R.id.txt_inform_classroom);
        type = (TextView)findViewById(R.id.txt_inform_classroom2);
        layout1 = (LinearLayout)findViewById(R.id.layout1);
        layout2 = (LinearLayout)findViewById(R.id.layout2);
        numClassroom = (EditText)findViewById(R.id.edit_search);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(numClassroom, InputMethodManager.SHOW_FORCED);

        moveBuilding = (ImageButton)findViewById(R.id.btn_move_building);
        ImageButton searchClassroom = (ImageButton)findViewById(R.id.btn_class_search);
        searchClassroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String classNum2 = numClassroom.getText().toString();
                dbAdapter.open();
                Cursor result = dbAdapter.fetchNote3(classNum2);
                result.moveToFirst();

                if(result.getCount()==0) {
                    Toast.makeText(SearchActivity.this, "없는 강의실번호 입니다", Toast.LENGTH_SHORT).show();
                    moveBuilding.setVisibility(View.INVISIBLE);
                    layout1.setVisibility(View.INVISIBLE);
                    layout2.setVisibility(View.INVISIBLE);
                }else{
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.VISIBLE);
                    moveBuilding.setVisibility(View.VISIBLE);

                    buildingEng = result.getString(1);
                    classType = result.getString(3);
                    Cursor result2 = dbAdapter.fetchNote(buildingEng);
                    result2.moveToFirst();
                    buildingName = result2.getString(2);

                    name.setText(buildingName);
                    type.setText(classType);
                }

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(numClassroom.getWindowToken(), 0);

                result.close();
                dbAdapter.close();
            }
        });

        moveBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, BuildingActivity.class);
                intent.putExtra("Building", buildingEng);
                startActivity(intent);
                finish();
            }
        });
    }

}
