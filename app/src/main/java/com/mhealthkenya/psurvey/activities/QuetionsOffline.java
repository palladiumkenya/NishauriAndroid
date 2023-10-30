package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.models.Answer;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.QuestionEntity;
import com.mhealthkenya.psurvey.models.repeat_count;

import java.util.ArrayList;
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


    DatePickerDialog datePickerDialog;
    String openText = "";

    int repeat_count;
    CheckBox checkBox;

     com.mhealthkenya.psurvey.models.repeat_count repeat_count1;
     repeat_count  _repeat_count;
     Answer answers;
     ArrayList<Answer> answerList = new ArrayList<>();
     List<Integer> multiAnswerList = new ArrayList<>();

     int mYear, mMonth, mDay;


    //openText1
    TextInputLayout openTextTil;
    TextInputEditText openTextEtxt;

    //openText2
    TextInputLayout openTextTil2;
    TextInputEditText openTextEtxt2;

    //openText3
    TextInputLayout openTextTil3;
    TextInputEditText openTextEtxt3;

    //openText4
    TextInputLayout openTextTil4;
    TextInputEditText openTextEtxt4;

    //openText5
    TextInputLayout openTextTil5;
    TextInputEditText openTextEtxt5;

    //openText6
    TextInputLayout openTextTil6;
    TextInputEditText openTextEtxt6;





    //dateNone1
    TextInputLayout dateTextTil;
    TextInputEditText dobEditText;
    //dateNone2
    TextInputLayout dateTextTil2;
    TextInputEditText dobEditText2;
    //dateNone3
    TextInputLayout dateTextTil3;
    TextInputEditText dobEditText3;
    //dateNone4
    TextInputLayout dateTextTil4;
    TextInputEditText dobEditText4;
    //dateNone5
    TextInputLayout dateTextTil5;
    TextInputEditText dobEditText5;
    //dateNone6
    TextInputLayout dateTextTil6;
    TextInputEditText dobEditText6;



    //datefuture1
    TextInputLayout dateTextTilfuture;
    TextInputEditText dobEditTextfuture;

    //datefuture2
    TextInputLayout dateTextTilfuture2;
    TextInputEditText dobEditTextfuture2;

    //datefuture3
    TextInputLayout dateTextTilfuture3;
    TextInputEditText dobEditTextfuture3;

    //datefuture4
    TextInputLayout dateTextTilfuture4;
    TextInputEditText dobEditTextfuture4;

    //datefuture5
    TextInputLayout dateTextTilfuture5;
    TextInputEditText dobEditTextfuture5;

    //datefuture6
    TextInputLayout dateTextTilfuture6;
    TextInputEditText dobEditTextfuture6;


    //datepast1
    TextInputLayout dateTextTilpast;
    TextInputEditText dobEditTextpast;

    //datepast2
    TextInputLayout dateTextTilpast2;
    TextInputEditText dobEditTextpast2;

    //datepast3
    TextInputLayout dateTextTilpast3;
    TextInputEditText dobEditTextpast3;

    //datepast4
    TextInputLayout dateTextTilpast4;
    TextInputEditText dobEditTextpast4;

    //datepast5
    TextInputLayout dateTextTilpast5;
    TextInputEditText dobEditTextpast5;

    //datepast6
    TextInputLayout dateTextTilpast6;
    TextInputEditText dobEditTextpast6;




    TextInputLayout numericText;
    TextInputEditText numericEditText;


    RadioGroup singleChoiceRadioGroup;
    LinearLayout multipleChoiceAns;

    CoordinatorLayout coordinatorLyt;
    ShimmerFrameLayout shimmer_my_container;

    RecyclerView recyclerView;
    LinearLayout no_active_survey_lyt;

    LinearLayout error_lyt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quetions_offline);
        initialize();
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

        if (question.getQuestionType()==1 ){
            openTextTil.setVisibility(View.VISIBLE);
        }

        /*else if (question.getQuestionType() == 2) {
            singleChoiceRadioGroup.setVisibility(View.VISIBLE);

            RadioButton rbn = new RadioButton(this);
            rbn.setId(View.generateViewId());
            rbn.setText(answers.getOption());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
            rbn.setLayoutParams(params);
            singleChoiceRadioGroup.addView(rbn);


        } else if (question.getQuestionType() == 3) {

            openTextTil.setVisibility(View.GONE);
            singleChoiceRadioGroup.setVisibility(View.GONE);
            multipleChoiceAns.setVisibility(View.VISIBLE);

            checkBox = new CheckBox(this);
            checkBox.setId(answers.getId());
            checkBox.setText(answers.getOption());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
            checkBox.setLayoutParams(params);
            multipleChoiceAns.addView(checkBox);

        }*/

         else if (question.getQuestionType() == 4) {

            numericText.setVisibility(View.VISIBLE);
            numericEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        }
        //none
       /* else if (question.getQuestionType() == 5 && question.getDateValidation().equals("none") && question.isRepeatable() && repeat_count==1) {
            dateTextTil.setVisibility(View.VISIBLE);
        }*/
        //none not repeat
       /* else if (question.getQuestionType() == 5 && question.getDateValidation().equals("none") && !question.isRepeatable()) {
            dateTextTil.setVisibility(View.VISIBLE);
        }*/

        //try
        // restrict future1
        else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_future") && !question.isRepeatable()) {
            // dateTextTil.setVisibility(View.VISIBLE);
            dateTextTilfuture.setVisibility(View.VISIBLE);

        }

        // restrict future1
        else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count==1) {
            dateTextTilfuture.setVisibility(View.VISIBLE);

        }

        // restrict future2
        else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count==2) {
            dateTextTilfuture.setVisibility(View.VISIBLE);
            dateTextTilfuture2.setVisibility(View.VISIBLE);

        }


        // restrict future3
        else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_future") && question.isRepeatable()  && repeat_count==3) {
            dateTextTilfuture.setVisibility(View.VISIBLE);
            dateTextTilfuture2.setVisibility(View.VISIBLE);
            dateTextTilfuture3.setVisibility(View.VISIBLE);

        }

        // restrict future4
        else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count==4) {
            dateTextTilfuture.setVisibility(View.VISIBLE);
            dateTextTilfuture2.setVisibility(View.VISIBLE);
            dateTextTilfuture3.setVisibility(View.VISIBLE);
            dateTextTilfuture4.setVisibility(View.VISIBLE);

        }
        // restrict future5
        else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count==5) {
            dateTextTilfuture.setVisibility(View.VISIBLE);
            dateTextTilfuture2.setVisibility(View.VISIBLE);
            dateTextTilfuture3.setVisibility(View.VISIBLE);
            dateTextTilfuture4.setVisibility(View.VISIBLE);
            dateTextTilfuture5.setVisibility(View.VISIBLE);

        }

        // restrict future6
        else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_future") && question.isRepeatable()&& repeat_count==6) {
            dateTextTilfuture.setVisibility(View.VISIBLE);
            dateTextTilfuture2.setVisibility(View.VISIBLE);
            dateTextTilfuture3.setVisibility(View.VISIBLE);
            dateTextTilfuture4.setVisibility(View.VISIBLE);
            dateTextTilfuture5.setVisibility(View.VISIBLE);
            dateTextTilfuture6.setVisibility(View.VISIBLE);

        }





        //restrict past repeat none
        else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_past") && !question.isRepeatable()) {
            dateTextTilpast.setVisibility(View.VISIBLE);
        }
        //restrict past2
        else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_past") && question.isRepeatable() && repeat_count1.getRepeat_count()==2) {
            dateTextTilpast.setVisibility(View.VISIBLE);
            dateTextTilpast2.setVisibility(View.VISIBLE);
        }

        //restrict past3
        else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_past") && question.isRepeatable() && repeat_count1.getRepeat_count()==3) {
            dateTextTilpast.setVisibility(View.VISIBLE);
            dateTextTilpast2.setVisibility(View.VISIBLE);
            dateTextTilpast3.setVisibility(View.VISIBLE);
        }


        //restrict past4
        else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_past")&& question.isRepeatable() && repeat_count1.getRepeat_count()==4) {
            dateTextTilpast.setVisibility(View.VISIBLE);
            dateTextTilpast2.setVisibility(View.VISIBLE);
            dateTextTilpast3.setVisibility(View.VISIBLE);
            dateTextTilpast4.setVisibility(View.VISIBLE);
        }
        //restrict past5
        else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_past") && question.isRepeatable() && repeat_count1.getRepeat_count()==5) {
            dateTextTilpast.setVisibility(View.VISIBLE);
            dateTextTilpast2.setVisibility(View.VISIBLE);
            dateTextTilpast3.setVisibility(View.VISIBLE);
            dateTextTilpast4.setVisibility(View.VISIBLE);
            dateTextTilpast5.setVisibility(View.VISIBLE);
        }

        //restrict past6
        else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_past") && question.isRepeatable() && repeat_count1.getRepeat_count()==6) {
            dateTextTilpast.setVisibility(View.VISIBLE);
            dateTextTilpast2.setVisibility(View.VISIBLE);
            dateTextTilpast3.setVisibility(View.VISIBLE);
            dateTextTilpast4.setVisibility(View.VISIBLE);
            dateTextTilpast5.setVisibility(View.VISIBLE);
            dateTextTilpast6.setVisibility(View.VISIBLE);
        }


        else {
            Toast.makeText(QuetionsOffline.this, "No answers found for this question", Toast.LENGTH_SHORT).show();
        }
        btnNext.setEnabled(true);
    }

    private void initialize(){
        //openText1
         openTextTil= (TextInputLayout) findViewById(R.id.til_open_text);
         openTextEtxt =(TextInputEditText) findViewById(R.id.etxt_open_text);

        //openText2
         openTextTil2 = (TextInputLayout) findViewById(R.id.til_open_text2);
         openTextEtxt2= (TextInputEditText) findViewById(R.id.etxt_open_text2);

        //openText3
         openTextTil3=(TextInputLayout) findViewById(R.id.til_open_text3);
        openTextEtxt3= (TextInputEditText) findViewById(R.id.etxt_open_text3);

        //openText4
         openTextTil4=(TextInputLayout) findViewById(R.id.til_open_text4);
          openTextEtxt4= (TextInputEditText) findViewById(R.id.etxt_open_text4);

        //openText5
         openTextTil5=(TextInputLayout) findViewById(R.id.til_open_text5);
         openTextEtxt5= (TextInputEditText) findViewById(R.id.etxt_open_text5);

        //openText6
          openTextTil6=(TextInputLayout) findViewById(R.id.til_open_text6);
          openTextEtxt6= (TextInputEditText) findViewById(R.id.etxt_open_text6);


        //dateNone1
         dateTextTil=(TextInputLayout) findViewById(R.id.dateLayout);
         dobEditText= (TextInputEditText) findViewById(R.id.dob);
        //dateNone2
         dateTextTil2=(TextInputLayout) findViewById(R.id.dateLayout2);
         dobEditText2= (TextInputEditText) findViewById(R.id.dob2);
        //dateNone3
        dateTextTil3=(TextInputLayout) findViewById(R.id.dateLayout3);
         dobEditText3= (TextInputEditText) findViewById(R.id.dob3);
        //dateNone4
         dateTextTil4=(TextInputLayout) findViewById(R.id.dateLayout4);
         dobEditText4= (TextInputEditText) findViewById(R.id.dob4);
        //dateNone5
         dateTextTil5=(TextInputLayout) findViewById(R.id.dateLayout5);
         dobEditText5= (TextInputEditText) findViewById(R.id.dob5);
        //dateNone6
         dateTextTil6=(TextInputLayout) findViewById(R.id.dateLayout6);
         dobEditText6= (TextInputEditText) findViewById(R.id.dob6);


        //datefuture1
         dateTextTilfuture=(TextInputLayout) findViewById(R.id.dateLayoutfuture);
         dobEditTextfuture= (TextInputEditText) findViewById(R.id.dobfuture);

        //datefuture2
         dateTextTilfuture2=(TextInputLayout) findViewById(R.id.dateLayoutfuture2);
         dobEditTextfuture2= (TextInputEditText) findViewById(R.id.dobfuture2);

        //datefuture3
         dateTextTilfuture3=(TextInputLayout) findViewById(R.id.dateLayoutfuture3);
         dobEditTextfuture3= (TextInputEditText) findViewById(R.id.dobfuture3);

        //datefuture4
         dateTextTilfuture4=(TextInputLayout) findViewById(R.id.dateLayoutfuture4);
         dobEditTextfuture4= (TextInputEditText) findViewById(R.id.dobfuture4);

        //datefuture5
         dateTextTilfuture5=(TextInputLayout) findViewById(R.id.dateLayoutfuture5);
         dobEditTextfuture5= (TextInputEditText) findViewById(R.id.dobfuture5);

        //datefuture6
         dateTextTilfuture6=(TextInputLayout) findViewById(R.id.dateLayoutfuture6);
         dobEditTextfuture6= (TextInputEditText) findViewById(R.id.dobfuture6);


        //datepast1
         dateTextTilpast=(TextInputLayout) findViewById(R.id.dateLayoutpast);
         dobEditTextpast=(TextInputEditText) findViewById(R.id.dobpast);

        //datepast2
         dateTextTilpast2=(TextInputLayout) findViewById(R.id.dateLayoutpast2);
         dobEditTextpast2=(TextInputEditText) findViewById(R.id.dobpast2);

        //datepast3
         dateTextTilpast3=(TextInputLayout) findViewById(R.id.dateLayoutpast3);
         dobEditTextpast3=(TextInputEditText) findViewById(R.id.dobpast3);

        //datepast4
         dateTextTilpast4=(TextInputLayout) findViewById(R.id.dateLayoutpast4);
         dobEditTextpast4=(TextInputEditText) findViewById(R.id.dobpast4);

        //datepast5
         dateTextTilpast5=(TextInputLayout) findViewById(R.id.dateLayoutpast5);
         dobEditTextpast5=(TextInputEditText) findViewById(R.id.dobpast5);

        //datepast6
         dateTextTilpast6=(TextInputLayout) findViewById(R.id.dateLayoutpast6);
         dobEditTextpast6=(TextInputEditText) findViewById(R.id.dobpast6);


         numericText=(TextInputLayout) findViewById(R.id.til_numeric_layout);
         numericEditText=(TextInputEditText) findViewById(R.id.etxt_numeric_text);


        singleChoiceRadioGroup=(RadioGroup) findViewById(R.id.radio_group);
         multipleChoiceAns=(LinearLayout) findViewById(R.id.multiselect_lyt);

         coordinatorLyt=(CoordinatorLayout) findViewById(R.id.coordinator_lyt);
         shimmer_my_container=(ShimmerFrameLayout) findViewById(R.id.shimmer_my_container);

         recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
         no_active_survey_lyt=(LinearLayout) findViewById(R.id.no_active_survey_lyt);
         error_lyt=(LinearLayout) findViewById(R.id.error_lyt);



    }
}