package com.triples.bf_skku;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class AtmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm);

        ListView listview;
        final ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview_atm);
        listview.setAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.wheelchairno),
                "수선관", "5층에 위치");
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.wheelchairno),
                "법학관", "1층에 위치");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.wheelchairno),
                "인문관", "1층에 위치");
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.wheelchairok),
                "경영관", "1층에 위치");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.wheelchairok),
                "경제관", "1층에 위치");

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.wheelchairok),
                "국제관", "1층에 위치");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.wheelchairok),
                "600주년", "1층에 위치");
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
                        Toast.makeText(AtmActivity.this, "수선관은 휠체어 접근이 어렵습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(AtmActivity.this, "법학관은 휠체어 접근이 어렵습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(AtmActivity.this, "인문관은 휠체어 접근이 어렵습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(AtmActivity.this, "경영관은 휠체어 접근이 가능합니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(AtmActivity.this, "경제관은 휠체어 접근이 가능합니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(AtmActivity.this, "국제관은 휠체어 접근이 가능합니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(AtmActivity.this, "600주년기념관은 휠체어 접근이 가능합니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        Toast.makeText(AtmActivity.this, "학생회관은 휠체어 접근이 가능합니다.", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });
    }
}
