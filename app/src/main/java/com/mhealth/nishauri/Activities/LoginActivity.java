package com.mhealth.nishauri.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mhealth.nishauri.R;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Sign In");
        setSupportActionBar(toolbar);
    }


    public void gotoHome(View view) {

        Intent mint = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mint);

    }
}
