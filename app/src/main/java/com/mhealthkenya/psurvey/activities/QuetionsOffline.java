package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.models.Answer;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.QuestionEntity;

import java.util.List;

import butterknife.BindView;

public class QuetionsOffline extends AppCompatActivity {
    int IDvalue;
    AllQuestionDatabase allQuestionDatabase;
    Button btnNext;

    MaterialTextView surveyQuestion;
     Button nextButton;
     List<QuestionEntity> questions;
     int currentQuestionIndex = 0;
    int questionnaireId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quetions_offline);
        surveyQuestion = (MaterialTextView) findViewById(R.id.tv_survey_question);
        btnNext = (Button) findViewById(R.id.btn_next);
        allQuestionDatabase = AllQuestionDatabase.getInstance(this);

        Intent mIntent = getIntent();
        IDvalue = mIntent.getIntExtra("ID", 0);

        // Initialize your Room database
        allQuestionDatabase = AllQuestionDatabase.getInstance(this);
             /* AllQuestionDatabase allQuestionDatabase = Room.databaseBuilder(getApplicationContext(),
                AllQuestionDatabase.class, "all_questions_db").allowMainThreadQueries().build();*/

        // Replace '1' with your desired 'QuestionnaireId'
        questionnaireId = IDvalue;

        // Retrieve questions for the specified questionnaire
        questions = allQuestionDatabase.questionDao().getQuestionsByQuestionnaireId(questionnaireId);
        if (!questions.isEmpty()) {
            displayQuestion(currentQuestionIndex);

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentQuestionIndex++;
                    if (currentQuestionIndex < questions.size()) {
                        displayQuestion(currentQuestionIndex);
                    } else {
                        // Handle the end of questions
                        btnNext.setEnabled(false);
                    }
                }
            });
        } else {
            surveyQuestion.setText("No questions found for this questionnaire.");
            btnNext.setEnabled(false);
        }



       /* if(IDvalue!=0)
        {

            Toast.makeText(QuetionsOffline.this, String.valueOf(IDvalue), Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(QuetionsOffline.this, "nullsdfghjm", Toast.LENGTH_LONG).show();
        }
        QuestionEntity questions = allQuestionDatabase.questionDao().getQuestionsOrderedByQuestionId(IDvalue);

       btnNext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Toast.makeText(QuetionsOffline.this,  questions.getQuestion(), Toast.LENGTH_LONG).show();
           }
       });


    }*/
    }
    private void displayQuestion(int index) {
        QuestionEntity question = questions.get(index);
        surveyQuestion.setText(question.getQuestion());
        btnNext.setEnabled(true);
    }
}