package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.models.Answer;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.QuestionEntity;

import java.util.List;

public class QuetionsOffline extends AppCompatActivity {
    int IDvalue;
    AllQuestionDatabase allQuestionDatabase;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quetions_offline);
        allQuestionDatabase = AllQuestionDatabase.getInstance(this);

        Intent mIntent = getIntent();
         IDvalue = mIntent.getIntExtra("ID", 0);


        if(IDvalue!=0)
        {



            Toast.makeText(QuetionsOffline.this, String.valueOf(IDvalue), Toast.LENGTH_LONG).show();
           // String j =(String) b.get("name");
            //Textv.setText(j);
        }
        int questionnaireId = 1; // Replace with the actual questionnaireId

       QuestionEntity questions = allQuestionDatabase.questionDao().getQuestionsOrderedByQuestionId(IDvalue);

       if (questions!=null){
          // Toast.makeText(QuetionsOffline.this, "not null", Toast.LENGTH_LONG).show();

       }else{
          // Toast.makeText(QuetionsOffline.this, "null", Toast.LENGTH_LONG).show();
       }
       // Log.d("Quest", String. questions.getQuestion());

       // AnswerEntity answerEntity =allQuestionDatabase.answerDao().getAnswersOrderedByAnswerId(questions.getId());

       // Toast.makeText(QuetionsOffline.this, questions.getQuestion(), Toast.LENGTH_LONG).show();

        //Toast.makeText(QuetionsOffline.this, answerEntity.getOption(), Toast.LENGTH_LONG).show();
        //Log.d("Quest", questions.getQuestion());
        //Log.d("Answ",  answerEntity.getOption());





// Now, 'questions' will contain the questions ordered by questionId.

    }
}