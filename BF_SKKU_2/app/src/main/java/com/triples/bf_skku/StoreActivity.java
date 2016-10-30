package com.triples.bf_skku;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class StoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_store);


        ListView listview;
        final ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview_store);
        listview.setAdapter(adapter);


        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.wheelchairno),
                "수선관별관", "5층에 위치");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.wheelchairno),
                "학술정보관", "1층에 위치");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.wheelchairno),
                "인문관", "2층에 위치");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.wheelchairok),
                "경영관", "B3층에 위치");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.wheelchairok),
                "법학관", "B2층에 위치");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.wheelchairok),
                "학생회관", "1층에 위치");


        // 기본 생성 코드 및 ListView와 Adapter 생성 코드
        // ...

        // 위에서 생성한 listview에 클릭 이벤트 핸들러 정의.
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                switch (position) {
                    case 0:
                        Toast.makeText(StoreActivity.this, "수선관 별관은 단차로 인해 휠체어 접근이 어렵습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(StoreActivity.this, "학술정보관 매점은 휠체어 접근이 어렵습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(StoreActivity.this, "인문관 편의점 CU는 공간이 비좁아 휠체어로 이용이 어렵습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(StoreActivity.this, "경영관 편의점 CU는 휠체어 접근이 가능합니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(StoreActivity.this, "법학관 편의점은 휠체어 접근이 가능합니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(StoreActivity.this, "학생회관 매점은 휠체어 접근이 가능합니다.", Toast.LENGTH_SHORT).show();
                        break;


                }
            }
        });
    }
}
