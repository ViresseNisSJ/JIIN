package com.triples.bf_skku;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;


public class BuildingActivity extends AppCompatActivity {

    private CustomAdapter mCustomAdapter;
    private ViewPager mViewPager;
    public static String buildingName = null;
    final String[] items = new String[]{"일반강의실", "계단식강의실", "첨단강의실"};

    /*엑셀*/
    private NotesDbAdapter dbAdapter;
    private static final String TAG = "NotesDbAdapter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        if (getIntent() != null) {
            buildingName = getIntent().getStringExtra("Building");
        }

        this.dbAdapter = new NotesDbAdapter(this);
        dbAdapter.open();
        Cursor result3 = dbAdapter.fetchNote(buildingName);
        result3.moveToFirst();
        String resultStr1 = "";
        resultStr1 = result3.getString(2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(resultStr1);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /*도면 버튼*/
        ImageButton btnBlueprint = (ImageButton)findViewById(R.id.btn_blueprint);
        btnBlueprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0, true);
                // mCustomAdapter.elevator();
            }
        });

        /*화장실버튼*/
        ImageButton btnToilet = (ImageButton)findViewById(R.id.btn_toilet);
        btnToilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAdapter.open();
                Cursor result = dbAdapter.fetchNote(buildingName);
                result.moveToFirst();
                String resultStr = "";

                String toielt = result.getString(3).replaceAll("[|]", "층, ");
                resultStr = "장애인화장실은 " + toielt + "층에 있습니다.";
                AlertDialog.Builder builder = new AlertDialog.Builder(BuildingActivity.this);
                builder.setTitle("장애인화장실");
                builder.setMessage(resultStr);
                builder.create().show();
            }
        });
//
//        /*엘베 버튼*/
//        ImageButton btnElevator = (ImageButton)findViewById(R.id.btn_elevator);
//        btnElevator.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mViewPager.setCurrentItem(0, true);
//                AlertDialog.Builder builder = new AlertDialog.Builder(BuildingActivity.this);
//                builder.setTitle("엘리베이터 위치");
//
//            }
//        });

        /*강의실 버튼*/
        ImageButton btnClassroom = (ImageButton)findViewById(R.id.btn_classroom);
        btnClassroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BuildingActivity.this);
                builder.setTitle("강의실 정보");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String[] classItems = new String[]{};
                        dbAdapter.open();
                        Cursor result3 = dbAdapter.fetchNote4(items[which], buildingName);
                        result3.moveToFirst();
                        String resultStr = "";

                        while (!result3.isAfterLast()) {
                            String classNum = result3.getString(2);
                            if(result3.getString(4).equals("1")){
                                resultStr += classNum +"(단차존재)" +"\n";
                            }else{
                                resultStr += classNum + "\n";
                            }
                            result3.moveToNext();
                        }

                        if(resultStr == "")
                        {
                            resultStr = "해당하는 강의실이 없습니다.";
                        }

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(BuildingActivity.this);
                        builder1.setTitle(items[which]);
                        builder1.setMessage(resultStr);
                        builder1.create().show();
                    }
                });
                builder.create().show();
            }
        });

        mCustomAdapter = new CustomAdapter(getLayoutInflater());

        mCustomAdapter.add(buildingName);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mCustomAdapter);


    }

}