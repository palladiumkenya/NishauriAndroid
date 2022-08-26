package com.mhealth.nishauri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mhealth.nishauri.R;

public class PatientActivity extends AppCompatActivity {
   Button btn_login;
    FloatingActionButton flt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
      btn_login =findViewById(R.id.btn_login77);
        flt = findViewById(R.id.fabtodays);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PatientActivity.this, "To dashboard with client's data", Toast.LENGTH_LONG).show();
            }
        });
        flt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PatientActivity.this, "To dashboard", Toast.LENGTH_LONG).show();
            }
        });
    }
}