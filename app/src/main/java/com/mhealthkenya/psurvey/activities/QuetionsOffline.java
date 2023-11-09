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
import android.widget.DatePicker;
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
import com.mhealthkenya.psurvey.activities.auth.LoginActivity;
import com.mhealthkenya.psurvey.models.Answer;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.QuestionEntity;
import com.mhealthkenya.psurvey.models.SurveyID;
import com.mhealthkenya.psurvey.models.UserResponseEntity;
import com.mhealthkenya.psurvey.models.repeat_count;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;


public class QuetionsOffline extends AppCompatActivity {
    String optionR;
    String answerR;
    int IDvalue;
    int idt1;
    AllQuestionDatabase allQuestionDatabase;
    Button btnNext;

    MaterialTextView surveyQuestion;
    Button nextButton;
    List<QuestionEntity> questions;
    int currentQuestionIndex;
    int questionnaireId;
    AnswerEntity answerEntity;


    DatePickerDialog datePickerDialog;
    String openText = "";

    int repeat_count;
    CheckBox checkBox;

    com.mhealthkenya.psurvey.models.repeat_count repeat_count1;
    repeat_count _repeat_count;
    Answer answers;
    ArrayList<AnswerEntity> answerList = new ArrayList<>();

    List<Integer> multiAnswerList = new ArrayList<>();

    int mYear, mMonth, mDay;
    int savedquestionnaireId;


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

        //set EditText type4 to accept numeric only
        numericEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

        //DatePickerNone1
        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                //show dialog
                datePickerDialog = new DatePickerDialog(QuetionsOffline.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dobEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        //DatePickerNone2
        dobEditText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                //show dialog
                datePickerDialog = new DatePickerDialog(QuetionsOffline.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dobEditText2.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        //DatePickerNone3
        dobEditText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                //show dialog
                datePickerDialog = new DatePickerDialog(QuetionsOffline.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dobEditText3.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        //DatePickerNone4
        dobEditText4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                //show dialog
                datePickerDialog = new DatePickerDialog(QuetionsOffline.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dobEditText4.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        //DatePickerNone5
        dobEditText5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                //show dialog
                datePickerDialog = new DatePickerDialog(QuetionsOffline.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dobEditText5.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        //DatePickerNone6
        dobEditText6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                //show dialog
                datePickerDialog = new DatePickerDialog(QuetionsOffline.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dobEditText6.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        //  answerList = new ArrayList<>();
        // surveyQuestion = (MaterialTextView) findViewById(R.id.tv_survey_question);
        btnNext = (Button) findViewById(R.id.btn_next);
        allQuestionDatabase = AllQuestionDatabase.getInstance(this);

        /*Intent mIntent = getIntent();
        IDvalue = mIntent.getIntExtra("ID", 0);
        questionnaireId = IDvalue;/

        // Initialize your Room database
        allQuestionDatabase = AllQuestionDatabase.getInstance(this);


        /*try {
            SurveyID.deleteAll(SurveyID.class);
            SurveyID surveyID = new SurveyID(questionnaireId);
            surveyID.save();

        }catch (Exception e){
            e.printStackTrace();
        }*/

        try {

            List<SurveyID> savedID = SurveyID.findWithQuery(SurveyID.class, "SELECT *from SurveyID ORDER BY id DESC LIMIT 1");
            if (savedID.size()==1){
                for (int x=0; x<savedID.size(); x++) {
                    savedquestionnaireId = savedID.get(x).getQuetionereID();
                    Toast.makeText(QuetionsOffline.this, "surveyID" + " " +savedquestionnaireId, Toast.LENGTH_LONG).show();
                    Log.d("SURVEYIDS", String.valueOf(savedquestionnaireId));

                }


                }

        }catch (Exception e){
            e.printStackTrace();
        }

        // Retrieve questions for the specified questionnaire

        questions = allQuestionDatabase.questionDao().getQuestionsByQuestionnaireId(savedquestionnaireId);
        // displayQuestion(currentQuestionIndex);


       /* if (!questions.isEmpty()) {
            displayQuestion(currentQuestionIndex);}
        else {
            surveyQuestion.setText("No questions found for this questionnaire.");
            btnNext.setEnabled(false);
        }*/


        // Retrieve the question index from the intent
        Intent intent1 = getIntent();
        currentQuestionIndex = intent1.getIntExtra("questionIndex", 0); // Default to 0

        // Initialize your UI components, such as TextView for displaying the question
        // ...
       // Toast.makeText(QuetionsOffline.this, "index is" + currentQuestionIndex, Toast.LENGTH_LONG).show();

        // Display the question based on the index
        displayQuestion(currentQuestionIndex);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // QuestionEntity questionEntity = new QuestionEntity();
    /*            currentQuestionIndex++;
                Log.d("Debug", "currentQuestionIndex incremented to " + currentQuestionIndex); // Add this log message
                Log.d("SURVEYIDS", String.valueOf(savedquestionnaireId));

                //currentQuestionIndex++; // Increment the question index
                if (currentQuestionIndex < questions.size()) {
                    Intent intent = new Intent(QuetionsOffline.this, QuetionsOffline.class);
                    intent.putExtra("questionIndex", currentQuestionIndex); // Pass the index
                    startActivity(intent);
                    // displayQuestion(currentQuestionIndex);
                     }

                else {
                    btnNext.setEnabled(false);
                    Toast.makeText(QuetionsOffline.this, "End of quetion", Toast.LENGTH_LONG).show();
                }                                                                                               */
                    //QuestionEntity question = questions.get(currentQuestionIndex);
                    /*if(question.getQuestionType()==1 && question.isRequired()) {

                        if (openTextEtxt.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        }
                        if (currentQuestionIndex < questions.size()) {
                            displayQuestion(currentQuestionIndex);

                            SaveAnswers(questionnaireId, currentQuestionIndex, 1, openTextEtxt.getText().toString());

                        } else {
                            // Handle the end of questions
                            btnNext.setEnabled(false);
                        }
                    }*/


                    //Agreed

                    //if (currentQuestionIndex < questions.size()){

                   QuestionEntity question = questions.get(currentQuestionIndex);


                    if (question.getQuestionType() == 1 && question.isRequired()) {

                        if (openTextEtxt.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        } else {
                            SaveAnswers(savedquestionnaireId, currentQuestionIndex,  openTextEtxt.getText().toString());

                        }
                    } else if (question.getQuestionType() == 1) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, openTextEtxt.getText().toString());

                    } else if (question.getQuestionType() == 4 && question.isRequired()) {
                        if (numericEditText.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                            // }else{
                        } else {
                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, numericEditText.getText().toString());
                        }
                    } else if (question.getQuestionType() == 4) {

                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, numericEditText.getText().toString());
                    }
                    //datepicker none & not repeatable
                    else if (question.getQuestionType() == 5 && question.isRequired() && question.getDateValidation().equals("none") && !question.isRepeatable()) {
                        if (dobEditText.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                            //&& questions.getDate_validation().contentEquals("none")
                        }
                        {
                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString());
                        }

                    } else if (question.getQuestionType() == 5 && question.getDateValidation().equals("none") && !question.isRepeatable()) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString());

                    }

                    //datepicker none & not repeatable
                    else if (question.getQuestionType() == 5 && question.isRequired() && question.getDateValidation().equals("none") && question.isRepeatable() && repeat_count == 1) {
                        if (dobEditText.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                            //&& questions.getDate_validation().contentEquals("none")
                        }
                        {
                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString());
                        }

                    } else if (question.getQuestionType() == 5 && question.getDateValidation().equals("none") && question.isRepeatable() && repeat_count == 1) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString());

                    }
                    //datepicker none & repeatable count2
                    else if (question.getQuestionType() == 5 && question.isRequired() && question.getDateValidation().equals("none") && question.isRepeatable() && repeat_count == 2) {
                        if (dobEditText.getText().toString().equals("") || dobEditText2.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                            //&& questions.getDate_validation().contentEquals("none")
                        }
                        {
                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString());
                        }

                    } else if (question.getQuestionType() == 5 && question.getDateValidation().equals("none") && question.isRepeatable() && repeat_count == 2) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString());

                    }

                    // datepicker none & repeatable count3
                    else if (question.getQuestionType() == 5 && question.isRequired() && question.getDateValidation().equals("none") && question.isRepeatable() && repeat_count == 3) {
                        if (dobEditText.getText().toString().equals("") || dobEditText2.getText().toString().equals("") || dobEditText3.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                            //&& questions.getDate_validation().contentEquals("none")
                        }
                        {
                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString() + " ," + dobEditText3.getText().toString());

                        }

                    } else if (question.getQuestionType() == 5 && question.getDateValidation().equals("none") && question.isRepeatable() && repeat_count == 3) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString() + " ," + dobEditText3.getText().toString());

                    }
                    // datepicker none & repeatable count4
                    else if (question.getQuestionType() == 5 && question.isRequired() && question.getDateValidation().equals("none") && question.isRepeatable() && repeat_count == 4) {
                        if (dobEditText.getText().toString().equals("") || dobEditText2.getText().toString().equals("") || dobEditText3.getText().toString().equals("") || dobEditText4.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                            //&& questions.getDate_validation().contentEquals("none")
                        }
                        {
                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString().equals("") + " ," + dobEditText3.getText().toString().equals("") + " ," + dobEditText4.getText().toString().equals(""));

                        }

                    } else if (question.getQuestionType() == 5 && question.getDateValidation().equals("none") && question.isRepeatable() && repeat_count1.getRepeat_count() == 4) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString().equals("") + " ," + dobEditText3.getText().toString().equals("") + " ," + dobEditText4.getText().toString().equals(""));

                    }
                    // datepicker none & repeatable count5
                    else if (question.getQuestionType() == 5 && question.isRequired() && question.getDateValidation().equals("none") && question.isRepeatable() && repeat_count == 5) {
                        if (dobEditText.getText().toString().equals("") || dobEditText2.getText().toString().equals("") || dobEditText3.getText().toString().equals("") || dobEditText4.getText().toString().equals("") || dobEditText5.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                            //&& questions.getDate_validation().contentEquals("none")
                        }
                        {
                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString().equals("") + " ," + dobEditText3.getText().toString().equals("") + " ," + dobEditText4.getText().toString().equals("") + " ," + dobEditText5.getText().toString().equals(""));
                        }

                    } else if (question.getQuestionType() == 5 && question.getDateValidation().equals("none") && question.isRepeatable() && repeat_count == 5) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString().equals("") + " ," + dobEditText3.getText().toString().equals("") + " ," + dobEditText4.getText().toString().equals("") + " ," + dobEditText5.getText().toString().equals(""));


                    }

                    // datepicker none & repeatable count6
                    else if (question.getQuestionType() == 5 && question.isRequired() && question.getDateValidation().equals("none") && question.isRepeatable() && repeat_count == 6) {
                        if (dobEditText.getText().toString().equals("") || dobEditText2.getText().toString().equals("") || dobEditText3.getText().toString().equals("") || dobEditText4.getText().toString().equals("") || dobEditText4.getText().toString().equals("") || dobEditText5.getText().toString().equals("") || dobEditText6.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                            //&& questions.getDate_validation().contentEquals("none")
                        }
                        {
                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString().equals("") + " ," + dobEditText3.getText().toString().equals("") + " ," + dobEditText4.getText().toString().equals("") + " ," + dobEditText5.getText().toString().equals("") + " ," + dobEditText6.getText().toString().equals(""));
                        }

                    } else if (question.getQuestionType() == 5 && question.getDateValidation().equals("none") && question.isRepeatable() && repeat_count == 6) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditText.getText().toString() + " ," + dobEditText2.getText().toString().equals("") + " ," + dobEditText3.getText().toString().equals("") + " ," + dobEditText4.getText().toString().equals("") + " ," + dobEditText5.getText().toString().equals("") + " ," + dobEditText6.getText().toString().equals(""));

                    }

                    //datepicker restrict future notrrepeat

                    else if (question.getQuestionType() == 5 && question.isRequired() && question.getDateValidation().equals("restrict_future") && !question.isRepeatable()) {
                        if (dobEditTextfuture.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        } else {
                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString());
                        }

                    } else if (question.getQuestionType() == 5 && !question.isRequired() && question.getDateValidation().equals("restrict_future") && !question.isRepeatable()) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString());

                    }


                    //datepicker restrict future repeat count one
                    else if (question.getQuestionType() == 5 && question.isRequired() && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count == 1) {
                        if (dobEditTextfuture.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        } else {
                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString());
                        }

                    } else if (question.getQuestionType() == 5 && !question.isRequired() && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count == 1) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString());

                    }

                    //datepicker restrict future repeat count 2
                    else if (question.getQuestionType() == 5 && question.isRequired() && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count == 2) {
                        if (dobEditTextfuture.getText().toString().equals("") || dobEditTextfuture2.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        } else {
                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString());
                        }

                    } else if (question.getQuestionType() == 5 && !question.isRequired() && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count == 2) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString());

                    }
                    //datepicker restrict future repeat count 3
                    else if (question.getQuestionType() == 5 && question.isRequired() && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count == 3) {
                        if (dobEditTextfuture.getText().toString().equals("") || dobEditTextfuture2.getText().toString().equals("") || dobEditTextfuture3.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        } else {
                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString());
                        }

                    } else if (question.getQuestionType() == 5 && !question.isRequired() && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count == 3) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString());

                    }


                    //datepicker restrict future repeat count 4
                    else if (question.getQuestionType() == 5 && question.isRequired() && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count == 4) {
                        if (dobEditTextfuture.getText().toString().equals("") || dobEditTextfuture2.getText().toString().equals("") || dobEditTextfuture3.getText().toString().equals("") || dobEditTextfuture4.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        } else {
                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString() + " ," + dobEditTextfuture4.getText().toString());
                        }

                    } else if (question.getQuestionType() == 5 && !question.isRequired() && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count == 4) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString() + " ," + dobEditTextfuture4.getText().toString());

                    }

                    //datepicker restrict future repeat count 5
                    else if (question.getQuestionType() == 5 && question.isRequired() && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count == 5) {
                        if (dobEditTextfuture.getText().toString().equals("") || dobEditTextfuture2.getText().toString().equals("") || dobEditTextfuture3.getText().toString().equals("") || dobEditTextfuture4.getText().toString().equals("") || dobEditTextfuture5.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        } else {
                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString() + " ," + dobEditTextfuture4.getText().toString() + " ," + dobEditTextfuture5.getText().toString());
                        }

                    } else if (question.getQuestionType() == 5 && !question.isRequired() && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count == 5) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString() + " ," + dobEditTextfuture4.getText().toString() + " ," + dobEditTextfuture5.getText().toString());


                    }

                    //datepicker restrict future repeat count 6
                    else if (question.getQuestionType() == 5 && !question.isRequired() && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count == 6) {
                        if (dobEditTextfuture.getText().toString().equals("") || dobEditTextfuture2.getText().toString().equals("") || dobEditTextfuture3.getText().toString().equals("") || dobEditTextfuture4.getText().toString().equals("") || dobEditTextfuture5.getText().toString().equals("") || dobEditTextfuture6.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        } else {
                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString() + " ," + dobEditTextfuture4.getText().toString() + " ," + dobEditTextfuture5.getText().toString() + " ," + dobEditTextfuture6.getText().toString());

                        }

                    } else if (question.getQuestionType() == 5 && !question.isRequired() && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count == 6) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextfuture.getText().toString() + " ," + dobEditTextfuture2.getText().toString() + " ," + dobEditTextfuture3.getText().toString() + " ," + dobEditTextfuture4.getText().toString() + " ," + dobEditTextfuture5.getText().toString() + " ," + dobEditTextfuture6.getText().toString());


                    }


                    //restrict past date non repeat
                    else if (question.getQuestionType() == 5 && question.isRequired() && question.getDateValidation().equals("restrict_past") && !question.isRepeatable()) {

                        if (dobEditTextpast.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        }

                        {

                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString());

                        }

                    } else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_past") && !question.isRepeatable()) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString());


                    }
                    //restrict past date  repeat count 2
                    else if (question.getQuestionType() == 5 && question.isRepeatable() && question.getDateValidation().equals("restrict_past") && question.isRepeatable() && repeat_count == 2) {

                        if (dobEditTextpast.getText().toString().equals("") || dobEditTextpast2.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        }

                        {

                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString());

                        }

                    } else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_past") && question.isRepeatable() && repeat_count == 2) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString());


                    }

                    //restrict past date  repeat count 3
                    else if (question.getQuestionType() == 5 && question.isRequired() && question.getDateValidation().equals("restrict_past") && question.isRepeatable() && repeat_count == 3) {

                        if (dobEditTextpast.getText().toString().equals("") || dobEditTextpast2.getText().toString().equals("") || dobEditTextpast3.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        }

                        {

                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString());

                        }

                    } else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_past") && question.isRepeatable() && repeat_count == 3) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex,dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString());


                    }
                    //restrict past date  repeat count 4
                    else if (question.getQuestionType() == 5 && question.isRequired() && question.getDateValidation().equals("restrict_past") && question.isRepeatable() && repeat_count == 4) {

                        if (dobEditTextpast.getText().toString().equals("") || dobEditTextpast2.getText().toString().equals("") || dobEditTextpast3.getText().toString().equals("") || dobEditTextpast4.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        }

                        {

                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString() + " ," + dobEditTextpast4.getText().toString());
                        }

                    } else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_past") && question.isRepeatable() && repeat_count1.getRepeat_count() == 4) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString() + " ," + dobEditTextpast4.getText().toString());

                    }
                    //restrict past date  repeat count 5
                    else if (question.getQuestionType() == 5 && question.isRequired() && question.getDateValidation().equals("restrict_past") && question.isRepeatable() && repeat_count == 5) {

                        if (dobEditTextpast.getText().toString().equals("") || dobEditTextpast2.getText().toString().equals("") || dobEditTextpast3.getText().toString().equals("") || dobEditTextpast4.getText().toString().equals("") || dobEditTextpast5.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        }

                        {

                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString() + " ," + dobEditTextpast4.getText().toString() + " ," + dobEditTextpast5.getText().toString());

                        }

                    } else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_past") && question.isRepeatable() && repeat_count1.getRepeat_count() == 5) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString() + " ," + dobEditTextpast4.getText().toString() + " ," + dobEditTextpast5.getText().toString());


                    }

                    //restrict past date  repeat count 6
                    else if (question.getQuestionType() == 5 && question.isRequired() && question.getDateValidation().equals("restrict_past") && question.isRepeatable() && repeat_count == 6) {

                        if (dobEditTextpast.getText().toString().equals("") || dobEditTextpast2.getText().toString().equals("") || dobEditTextpast3.getText().toString().equals("") || dobEditTextpast4.getText().toString().equals("") || dobEditTextpast5.getText().toString().equals("") || dobEditTextpast6.getText().toString().equals("")) {
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        }

                        {

                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString() + " ," + dobEditTextpast4.getText().toString() + " ," + dobEditTextpast5.getText().toString() + " ," + dobEditTextpast6.getText().toString());

                        }
                    } else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_past") && question.isRepeatable() && repeat_count == 6) {
                        SaveAnswers(savedquestionnaireId, currentQuestionIndex, dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," + dobEditTextpast3.getText().toString() + " ," + dobEditTextpast4.getText().toString() + " ," + dobEditTextpast5.getText().toString() + " ," + dobEditTextpast6.getText().toString());


                    } else if (question.getQuestionType() == 2) {

                        int radioButtonID = singleChoiceRadioGroup.getCheckedRadioButtonId();


                        if (radioButtonID == -1) {

                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();

                        } else {
                            View radioButton = singleChoiceRadioGroup.findViewById(radioButtonID);
                            int idx = singleChoiceRadioGroup.indexOfChild(radioButton);


                         //   SaveAnswers(savedquestionnaireId, currentQuestionIndex, openText);
                            SaveAnswers(savedquestionnaireId, currentQuestionIndex, String.valueOf(answerList.get(idx).getId()));

                        }

                    } else if (question.getQuestionType() == 3) {


                        for (int i = 0; i < multipleChoiceAns.getChildCount(); i++) {
                            View nextChild = multipleChoiceAns.getChildAt(i);

                            if (nextChild instanceof CheckBox) {
                                checkBox = (CheckBox) nextChild;
                                if (checkBox.isChecked()) {
                                    multiAnswerList.add(checkBox.getId());
                                }
                            }

                        }

                        //aNSER TYPE 3

                        //SaveAnswers( questionnaireId, currentQuestionIndex, Integer.parseInt(String.valueOf(multiAnswerList)), openText);
                        SaveAnswers( questionnaireId, currentQuestionIndex, openText);
                        //startActivity(new Intent(QuetionsOffline.this, QuetionsOffline.class));

//                    Toast.makeText(context, String.valueOf(multiAnswerList), Toast.LENGTH_SHORT).show();

                    }
                    else {
                        btnNext.setEnabled(false);
                    }


                    // }


                    //List/Recyclerview

                        /*List<UserResponseEntity> response= allQuestionDatabase.userResponseDao().getUserResponsesForQuestionnaire(questionnaireId);

                        for (UserResponseEntity userResponseEntity: response){
                             answerR =userResponseEntity.getOption();

                            // UserResponseEntity userResponseEntity1 =new UserResponseEntity();


                        }
                        Toast.makeText(QuetionsOffline.this, answerR, Toast.LENGTH_LONG).show();
                        Log.d("UserResponse", answerR.toString());*/

                    //  Log.d("UserResponse", an)


                    // }

                    //}
                   /* else if (question.getQuestionType() == 1 )
                    {
                        List<AnswerEntity> answersA = allQuestionDatabase.answerDao().getAnswersForQuestion(question.getId());
                        for (AnswerEntity answers2: answersA ){
                            idt1 = answers2.getId();
                            String option=answers2.getOption();
                            String createdAt=answers2.getCreatedAt();
                            int questionI=answers2.getQuestionId();
                            int createdBy=answers2.getCreatedBy();
                        }

                            SaveAnswers( questionnaireId, currentQuestionIndex, idt1 ,openTextEtxt.getText().toString());
                       // provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), openTextEtxt.getText().toString());

                    }*/

                    //TYpe 4
                   /* else if (question.getQuestionType()==4 && question.isRequired()){
                        if (numericEditText.getText().toString().equals("")){
                            Toast.makeText(QuetionsOffline.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                            // }else{
                        }else{SaveAnswers(questionnaireId, currentQuestionIndex, 1, numericEditText.getText().toString());}
                    }

                    else if (question.getQuestionType() == 4){

                        SaveAnswers(questionnaireId, currentQuestionIndex, 1, numericEditText.getText().toString());
                    }*/


    /*            }
                else {
                    btnNext.setEnabled(false);
                }*/
            }

        });
    }
       /* else {
            surveyQuestion.setText("No questions found for this questionnaire.");
            btnNext.setEnabled(false);
        }*/

    //}

    //same as load question
    private void displayQuestion(int questionIndex) {
        //try
        // questions = allQuestionDatabase.questionDao().getQuestionsByQuestionnaireId(questionnaireId);

        if (questionIndex >= 0 && questionIndex < questions.size()) {
            // java.lang.IndexOutOfBoundsException: Index 1 out of bounds for length 0
            // questions = allQuestionDatabase.questionDao().getQuestionsByQuestionnaireId(questionnaireId);

            Log.d("Debug", "LENGTH OF INDEX" + questionIndex);

            QuestionEntity question = questions.get(questionIndex);
            Log.d("Debug", "Displaying question: " + question.getQuestion());


            surveyQuestion.setText(question.getQuestion());

            //end


            if (question.getQuestionType() == 1) {
                openTextTil.setVisibility(View.VISIBLE);

            } else if (question.getQuestionType() == 2) {
                //dateTextTil.setVisibility(View.VISIBLE);
                // openTextTil.setVisibility(View.GONE);
                singleChoiceRadioGroup.setVisibility(View.VISIBLE);


                List<AnswerEntity> answersA = allQuestionDatabase.answerDao().getAnswersForQuestion2(savedquestionnaireId, question.getId());
                // Log.d("Answer List", answersA.);


                for (AnswerEntity answers2 : answersA) {
                    // Log.d("Answer List", answers2.toString());

                    int id = answers2.getId();
                    optionR = answers2.getOption();
                    String createdAt = answers2.getCreatedAt();
                    int questionI = answers2.getQuestionId();
                    int questionnaireI = answers2.getQuestionnaireId();
                    int createdBy = answers2.getCreatedBy();

                     answerEntity = new AnswerEntity(id, optionR, createdAt, questionI, questionnaireI, createdBy);
                    answerList.add(answerEntity);

                    RadioButton rbn = new RadioButton(QuetionsOffline.this);
                    rbn.setId(View.generateViewId());
                    rbn.setText(optionR);
                    Log.d("Answer Options", optionR);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                    rbn.setLayoutParams(params);
                    singleChoiceRadioGroup.addView(rbn);


                }


            } else if (question.getQuestionType() == 3) {
                openTextTil.setVisibility(View.GONE);
                singleChoiceRadioGroup.setVisibility(View.GONE);
                multipleChoiceAns.setVisibility(View.VISIBLE);
                // List<AnswerEntity> answersB = allQuestionDatabase.answerDao().getAnswersForQuestion(question.getId());
                List<AnswerEntity> answersB = allQuestionDatabase.answerDao().getAnswersForQuestion2(savedquestionnaireId, question.getId());
                List<Integer> answersB1 = allQuestionDatabase.answerDao().getAnswerIdsForQuestion(question.getId());
                for (AnswerEntity answers2 : answersB) {
                    int id = answers2.getId();
                    String option = answers2.getOption();
                    String createdAt = answers2.getCreatedAt();
                    int questionI = answers2.getQuestionId();
                    int questionnaire2 = answers2.getQuestionnaireId();
                    int createdBy = answers2.getCreatedBy();

                    answerEntity = new AnswerEntity(id, option, createdAt, questionI, questionnaire2, createdBy);
                    answerList.add(answerEntity);

                }


                checkBox = new CheckBox(this);
                checkBox.setId(answerEntity.getId());
                checkBox.setText(answerEntity.getOption());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                checkBox.setLayoutParams(params);
                multipleChoiceAns.addView(checkBox);

                // }

            } else if (question.getQuestionType() == 4) {

                numericText.setVisibility(View.VISIBLE);
                numericEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            }
            //none
            else if (question.getQuestionType() == 5 && question.getDateValidation().equals("none") && question.isRepeatable() && repeat_count == 1) {
                dateTextTil.setVisibility(View.VISIBLE);
            }
            //none not repeat
            else if (question.getQuestionType() == 5 && question.getDateValidation().equals("none") && !question.isRepeatable()) {
                dateTextTil.setVisibility(View.VISIBLE);
            }

            //try
            // restrict future1
            else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_future") && !question.isRepeatable()) {
                // dateTextTil.setVisibility(View.VISIBLE);
                dateTextTilfuture.setVisibility(View.VISIBLE);

            }

            // restrict future1
            else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count == 1) {
                dateTextTilfuture.setVisibility(View.VISIBLE);

            }

            // restrict future2
            else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count == 2) {
                dateTextTilfuture.setVisibility(View.VISIBLE);
                dateTextTilfuture2.setVisibility(View.VISIBLE);

            }


            // restrict future3
            else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count == 3) {
                dateTextTilfuture.setVisibility(View.VISIBLE);
                dateTextTilfuture2.setVisibility(View.VISIBLE);
                dateTextTilfuture3.setVisibility(View.VISIBLE);

            }

            // restrict future4
            else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count == 4) {
                dateTextTilfuture.setVisibility(View.VISIBLE);
                dateTextTilfuture2.setVisibility(View.VISIBLE);
                dateTextTilfuture3.setVisibility(View.VISIBLE);
                dateTextTilfuture4.setVisibility(View.VISIBLE);

            }
            // restrict future5
            else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count == 5) {
                dateTextTilfuture.setVisibility(View.VISIBLE);
                dateTextTilfuture2.setVisibility(View.VISIBLE);
                dateTextTilfuture3.setVisibility(View.VISIBLE);
                dateTextTilfuture4.setVisibility(View.VISIBLE);
                dateTextTilfuture5.setVisibility(View.VISIBLE);

            }

            // restrict future6
            else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_future") && question.isRepeatable() && repeat_count == 6) {
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
            else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_past") && question.isRepeatable() && repeat_count1.getRepeat_count() == 2) {
                dateTextTilpast.setVisibility(View.VISIBLE);
                dateTextTilpast2.setVisibility(View.VISIBLE);
            }

            //restrict past3
            else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_past") && question.isRepeatable() && repeat_count1.getRepeat_count() == 3) {
                dateTextTilpast.setVisibility(View.VISIBLE);
                dateTextTilpast2.setVisibility(View.VISIBLE);
                dateTextTilpast3.setVisibility(View.VISIBLE);
            }


            //restrict past4
            else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_past") && question.isRepeatable() && repeat_count1.getRepeat_count() == 4) {
                dateTextTilpast.setVisibility(View.VISIBLE);
                dateTextTilpast2.setVisibility(View.VISIBLE);
                dateTextTilpast3.setVisibility(View.VISIBLE);
                dateTextTilpast4.setVisibility(View.VISIBLE);
            }
            //restrict past5
            else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_past") && question.isRepeatable() && repeat_count1.getRepeat_count() == 5) {
                dateTextTilpast.setVisibility(View.VISIBLE);
                dateTextTilpast2.setVisibility(View.VISIBLE);
                dateTextTilpast3.setVisibility(View.VISIBLE);
                dateTextTilpast4.setVisibility(View.VISIBLE);
                dateTextTilpast5.setVisibility(View.VISIBLE);
            }

            //restrict past6
            else if (question.getQuestionType() == 5 && question.getDateValidation().equals("restrict_past") && question.isRepeatable() && repeat_count1.getRepeat_count() == 6) {
                dateTextTilpast.setVisibility(View.VISIBLE);
                dateTextTilpast2.setVisibility(View.VISIBLE);
                dateTextTilpast3.setVisibility(View.VISIBLE);
                dateTextTilpast4.setVisibility(View.VISIBLE);
                dateTextTilpast5.setVisibility(View.VISIBLE);
                dateTextTilpast6.setVisibility(View.VISIBLE);
            }
    }
else

    {

        Toast.makeText(QuetionsOffline.this, "No answers found for this question", Toast.LENGTH_SHORT).show();
        btnNext.setEnabled(false);
        Log.d("Debug", "Question index is out of bounds.");
    }
    // btnNext.setEnabled(true);
}

    private void initialize(){
        surveyQuestion = (MaterialTextView) findViewById(R.id.tv_survey_question);
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

    public void SaveAnswers(int questionnaireId1, int questionId1, String option1){

       // List<AnswerEntity> answers = allQuestionDatabase.answerDao().getAnswersForQuestion(surveyQuestion.getId());

        UserResponseEntity userResponseEntity = new UserResponseEntity();
        userResponseEntity.setQuestionnaireId(questionnaireId1);
        userResponseEntity.setQuestionId(questionId1);
       // userResponseEntity.setAnswerId(answeid1);
        userResponseEntity.setOption(option1);

        allQuestionDatabase.userResponseDao().insertResponse(userResponseEntity);

        currentQuestionIndex++;
        Log.d("Debug", "currentQuestionIndex incremented to " + currentQuestionIndex); // Add this log message
        Log.d("SURVEYIDS", String.valueOf(savedquestionnaireId));

        //currentQuestionIndex++; // Increment the question index
        if (currentQuestionIndex < questions.size()) {
            Intent intent = new Intent(QuetionsOffline.this, QuetionsOffline.class);
            intent.putExtra("questionIndex", currentQuestionIndex); // Pass the index
            startActivity(intent);
            // displayQuestion(currentQuestionIndex);
        }else{
           // Toast.makeText(QuetionsOffline.this, "index is" + currentQuestionIndex, Toast.LENGTH_LONG).show();
            //btnNext.setEnabled(false);

            Intent intent = new Intent(QuetionsOffline.this, CompleteSurvey.class);
            startActivity(intent);

        }


        /*if (!questions.isEmpty()) {
            Intent intent = new Intent(QuetionsOffline.this, QuetionsOffline.class);
            startActivity(intent);}
        else {
            surveyQuestion.setText("No questions found for this questionnaire.");
            btnNext.setEnabled(false);
        }*/





    }

}