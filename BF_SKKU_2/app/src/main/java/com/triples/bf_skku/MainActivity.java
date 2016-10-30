package com.triples.bf_skku;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;


public class MainActivity extends AppCompatActivity {

    private long backKeyPressedTime = 0;

    private SlidingUpPanelLayout mLayout;
    ImageButton center, call, equal, developer, facilities, search;
    EditText editSearch;
    Button classSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);

        setSupportActionBar((Toolbar) findViewById(R.id.main_toolbar));

        /*장애지원센터*/
        center = (ImageButton) findViewById(R.id.btn_center);
        center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://support.skku.edu/"));
                startActivity(intent);
            }
        });

        /*긴급전화*/
        call = (ImageButton) findViewById(R.id.btn_call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:02-760-1092"));
                startActivity(intent);
            }
        });

        /*Equal facebook 방문*/
        equal = (ImageButton) findViewById(R.id.btn_equal);
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/skkuequal/"));
                startActivity(intent);
            }
        });


        /*개발자정보*/
        developer = (ImageButton) findViewById(R.id.btn_developer);
        developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                startActivity(intent);
            }
        });

        /*강의실검색*/

        search = (ImageButton) findViewById(R.id.btn_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        /*편의시설*/
        facilities = (ImageButton) findViewById(R.id.btn_facilities);
        facilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FacilitiesActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (mLayout != null && mLayout.getPanelState() == PanelState.EXPANDED ) {
            mLayout.setPanelState(PanelState.COLLAPSED);
        }
        else {
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis();
                Toast.makeText(MainActivity.this, "'뒤로'버튼을 한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
                return;
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                finish();
            }
        }
    }

    public void buildingClicked(View v) {
        Intent intent = new Intent(MainActivity.this, BuildingActivity.class);
        intent.putExtra("Building", this.getResources().getResourceEntryName(v.getId()));
        startActivity(intent);
    }

}