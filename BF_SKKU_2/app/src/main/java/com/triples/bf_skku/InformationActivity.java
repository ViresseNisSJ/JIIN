package com.triples.bf_skku;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity {

    TextView email, email2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        email = (TextView)findViewById(R.id.txt_email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:hsj7625@hanmail.net"));
                startActivity(intent);
            }
        });

        email2 = (TextView)findViewById(R.id.txt_email2);
        email2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:skkuequal@gmail.com"));
                startActivity(intent);
            }
        });

    }
}
