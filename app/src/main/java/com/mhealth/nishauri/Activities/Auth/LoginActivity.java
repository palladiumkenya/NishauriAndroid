package com.mhealth.nishauri.Activities.Auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mhealth.nishauri.Activities.MainActivity;
import com.mhealth.nishauri.R;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private Toolbar toolbar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Sign In");
        setSupportActionBar(toolbar);

        ((TextView) findViewById(R.id.txt_sign_up)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mint = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(mint);
            }
        });


    }


    public void gotoHome(View view) {

        Intent mint = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mint);

    }
}
