package com.example.mhealth.appointment_diary.config;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mhealth.appointment_diary.R;

public class URLActivity extends AppCompatActivity {

    public static String BASE_URL= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlactivity);


        TextView x =findViewById(R.id.show);
        Button xx =findViewById(R.id.show1);

        //String z;

        Bundle bundle =getIntent().getExtras();
        BASE_URL= bundle.getString("url");

        x.setText(BASE_URL);

        xx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(URLActivity.this, TryActivity.class);
                startActivity(intent);
            }
        });






    }
}