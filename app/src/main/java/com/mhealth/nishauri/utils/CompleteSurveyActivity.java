package com.mhealth.nishauri.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.mhealth.nishauri.Activities.MainActivity;
import com.mhealth.nishauri.Fragments.Chat.ChatFragment;
import com.mhealth.nishauri.Fragments.HomeFragment;
import com.mhealth.nishauri.R;

import butterknife.BindView;

public class CompleteSurveyActivity extends AppCompatActivity {

    LinearLayout cod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_survey);
        Button btn_new_survey = findViewById(R.id.btn_new_survey);
        Button btn_done=findViewById(R.id.btn_done);
        cod =findViewById(R.id.lin2);


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

               // NavHostFragment.findNavController(CompleteSurveyActivity.this).navigate(R.id.nav_home);

                /*getSupportFragmentManager().beginTransaction().add(R.id.completeS, new HomeFragment()).commit();
                cod.setVisibility(View.GONE);*/

                Intent integer = new Intent(CompleteSurveyActivity.this, MainActivity.class);
                startActivity(integer);


            }
        });


    }



}
