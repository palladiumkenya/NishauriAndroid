package com.mhealth.nishauri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PasswordReset extends AppCompatActivity {

    EditText etxt_email1;
    Button btn_reset_password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        etxt_email1 =findViewById(R.id.etxt_email);
        btn_reset_password1=findViewById(R.id.btn_reset_password);


        btn_reset_password1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 =new Intent(PasswordReset.this, otpcodeActivity.class);
                startActivity(intent1);
            }
        });
    }
}