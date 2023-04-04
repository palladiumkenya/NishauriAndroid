package com.mhealth.nishauri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class otpcodeActivity extends AppCompatActivity {
    Button btn_login1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpcode);
        btn_login1 =findViewById(R.id.btn_login);

        btn_login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 =new Intent(otpcodeActivity.this, NewPassword.class);
                startActivity(intent1);
            }
        });
    }
}