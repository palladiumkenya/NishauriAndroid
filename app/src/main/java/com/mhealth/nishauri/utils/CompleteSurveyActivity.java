package com.mhealth.nishauri.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mhealth.nishauri.Fragments.HomeFragment;
import com.mhealth.nishauri.R;

import butterknife.BindView;

public class CompleteSurveyActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_survey);
        Button btn_new_survey = findViewById(R.id.btn_new_survey);
        Button btn_done=findViewById(R.id.btn_done);


        btn_new_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //NavHostFragment.findNavController(CompleteSurveyFragment.this).navigate(R.id.nav_select_survey);


                  Intent integer = new Intent(CompleteSurveyActivity.this, SelectSurvey.class);
                startActivity(integer);


            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  NavHostFragment.findNavController(CompleteSurveyFragment.this).navigate(R.id.nav_home);

                Intent integer = new Intent(CompleteSurveyActivity.this, HomeFragment.class);
                startActivity(integer);


            }
        });


    }



}
