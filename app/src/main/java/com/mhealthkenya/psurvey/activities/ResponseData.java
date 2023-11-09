package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.adapters.UserResponseAdapter;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.QuestionEntity;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;
import com.mhealthkenya.psurvey.models.UserResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class ResponseData extends AppCompatActivity {
    int IDvalue;
    AllQuestionDatabase allQuestionDatabase;

    ArrayList<UserResponseEntity> userResponseEntities;
    UserResponseEntity userResponseEntity;

    ArrayList<UserResponseEntity> answerList = new ArrayList<>();
    RecyclerView recyclerView1;
    UserResponseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_data);
        // Initialize your RecyclerView
        userResponseEntities= new ArrayList<>();
        recyclerView1 = findViewById(R.id.recyclerViewResponse);

        allQuestionDatabase = AllQuestionDatabase.getInstance(this);
        //userResponseEntities =allQuestionDatabase.userResponseDao().getUserResponsesForQuestionnaire()

         Intent mIntent = getIntent();
        IDvalue = mIntent.getIntExtra("Quetionnaire_ID", 0);

      //  Toast.makeText(ResponseData.this, "ID Is"+IDvalue, Toast.LENGTH_SHORT).show();

        recyclerView1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        recyclerView1.setHasFixedSize(true);
        // Create and set the adapter
         adapter = new UserResponseAdapter(ResponseData.this);
        recyclerView1.setAdapter(adapter);

        // Optionally, set the layout manager (e.g., LinearLayoutManager)
       // recyclerView1.setLayoutManager(new LinearLayoutManager(this));



        List<UserResponseEntity> userResponses = allQuestionDatabase.userResponseDao().getUserResponsesForQuestionnaire(IDvalue);
        //try

        for (UserResponseEntity userResponseEntity:userResponses) {
            Log.d("QuetionnaireID", String.valueOf(userResponseEntity.getQuestionnaireId()));
            Log.d("QuetionID", String.valueOf(userResponseEntity.getQuestionId()));
            Log.d("Option", String.valueOf(userResponseEntity.getOption()));

            int questionnaireId = userResponseEntity.getQuestionnaireId();
            int questionId =  userResponseEntity.getQuestionId();
            String answer = userResponseEntity.getOption();

           // QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity(questionnaireId,questionnaireName, questionnaireDescription, questionnaireCreatedAt, questionnaireNumberOfQuestions, questionnaireActiveTill, questionnaireTargetApp);
            UserResponseEntity userResponseEntity1 = new UserResponseEntity(questionnaireId, questionId, answer);
            userResponseEntities.add(userResponseEntity1);
            adapter.setUser(userResponseEntities);

        }
        if (userResponseEntities.isEmpty()){
            Toast.makeText(ResponseData.this, "No Responses for this Quetionnaire", Toast.LENGTH_SHORT).show();
        }





        //

    }
}