package com.triples.bf_skku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class FacilitiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_facilities);


        ListView listview2;
        final FacilitiesListViewAdapter adapter;

        // Adapter 생성
        adapter = new FacilitiesListViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        listview2 = (ListView) findViewById(R.id.listview_facilities);
        listview2.setAdapter(adapter);
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.atm_icon),
                "ATM 위치정보");
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.cafe_icon),
                "카페 위치정보");
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.shop_icon),
                "편의점 위치정보");


        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(FacilitiesActivity.this, AtmActivity.class);
                        startActivity(intent);
                        Toast.makeText(FacilitiesActivity.this, "건물별 ATM 위치정보입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Intent intent2 = new Intent(FacilitiesActivity.this, CafeActivity.class);
                        startActivity(intent2);
                        Toast.makeText(FacilitiesActivity.this, "건물별 카페 위치정보입니다.", Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        Intent intent3 = new Intent(FacilitiesActivity.this, StoreActivity.class);
                        startActivity(intent3);
                        Toast.makeText(FacilitiesActivity.this, "건물별 편의점 위치정보입니다.", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });

    }


}
