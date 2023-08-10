package com.mhealth.nishauri.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.Models.auth;
import com.mhealth.nishauri.Models.repeat_count;
import com.mhealth.nishauri.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

public class QuestionsActivity extends AppCompatActivity {

    DatePickerDialog datePickerDialog;

    public String z;


  public String openText = "";
   // private String questionLink;
    int  questionLink;
   int sessionID;
    int repeat_count;

     CheckBox checkBox;

   //  auth loggedInUser;
    public User loggedInUser;
    Question questions;
      repeat_count repeat_count1;
    com.mhealth.nishauri.Models.repeat_count _repeat_count;
    Answer answers;
     ArrayList<Answer> answerList = new ArrayList<>();
     List<Integer> multiAnswerList = new ArrayList<>();

   int mYear, mMonth, mDay;




    MaterialTextView surveyQuestion;
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
    Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

         surveyQuestion =findViewById(R.id.tv_survey_question);
        //openText1
         openTextTil=findViewById(R.id.til_open_text);
         openTextEtxt=findViewById(R.id.etxt_open_text);
        //openText2
         openTextTil2=findViewById(R.id.til_open_text2);
        openTextEtxt2=findViewById(R.id.etxt_open_text2);
        //openText3
         openTextTil3=findViewById(R.id.til_open_text3);
         openTextEtxt3=findViewById(R.id.etxt_open_text3);
         //openText4
        openTextTil4 =findViewById(R.id.til_open_text4);
        openTextEtxt4=findViewById(R.id.etxt_open_text4);
        //openText5
        openTextTil5=findViewById(R.id.til_open_text5);
        openTextEtxt5 =findViewById(R.id.etxt_open_text5);
        //openText6
         openTextTil6 =findViewById(R.id.til_open_text6);
         openTextEtxt6=findViewById(R.id.etxt_open_text6);

        //dateNone1
        dateTextTil=findViewById(R.id.dateLayout);
        dobEditText=findViewById(R.id.dob);
        //dateNone2
        dateTextTil2=findViewById(R.id.dateLayout2);
        dobEditText2=findViewById(R.id.dob2);
        //dateNone3
        dateTextTil3=findViewById(R.id.dateLayout3);
        dobEditText3 =findViewById(R.id.dob3);
        //dateNone4
         dateTextTil4=findViewById(R.id.dateLayout4);
         dobEditText4=findViewById(R.id.dob4);
        //dateNone5
         dateTextTil5=findViewById(R.id.dateLayout5);
         dobEditText5=findViewById(R.id.dob5);
        //dateNone6
         dateTextTil6 =findViewById(R.id.dateLayout6);
         dobEditText6 =findViewById(R.id.dob6);

        //datepast1

         dateTextTilpast=findViewById(R.id.dateLayoutpast);
         dobEditTextpast=findViewById(R.id.dobpast);
         dateTextTilpast2=findViewById(R.id.dateLayoutpast2);
        dobEditTextpast2=findViewById(R.id.dobpast2);

        //datepast3
         dateTextTilpast3=findViewById(R.id.dateLayoutpast3);
        dobEditTextpast3=findViewById(R.id.dobpast3);

        //datepast4
         dateTextTilpast4=findViewById(R.id.dateLayoutpast4);
        TextInputEditText dobEditTextpast4=findViewById(R.id.dobpast4);

        //datepast5
        dateTextTilpast5=findViewById(R.id.dateLayoutpast5);
         dobEditTextpast5=findViewById(R.id.dobpast5);

        //datepast6
         dateTextTilpast6=findViewById(R.id.dateLayoutpast6);
        dobEditTextpast6=findViewById(R.id.dobpast6);

        //datefuture1
         dateTextTilfuture=findViewById(R.id.dateLayoutfuture);
        dobEditTextfuture=findViewById(R.id.dobfuture);
        //datefuture2
         dateTextTilfuture2=findViewById(R.id.dateLayoutfuture2);
         dobEditTextfuture2=findViewById(R.id.dobfuture2);

        //datefuture3
         dateTextTilfuture3=findViewById(R.id.dateLayoutfuture3);
         dobEditTextfuture3=findViewById(R.id.dobfuture3);

        //datefuture4
        dateTextTilfuture4=findViewById(R.id.dateLayoutfuture4);
        dobEditTextfuture4=findViewById(R.id.dobfuture4);

        //datefuture5
         dateTextTilfuture5=findViewById(R.id.dateLayoutfuture5);
         dobEditTextfuture5=findViewById(R.id.dobfuture5);

        //datefuture6
        dateTextTilfuture6=findViewById(R.id.dateLayoutfuture6);
        dobEditTextfuture6=findViewById(R.id.dobfuture6);


        numericText=findViewById(R.id.til_numeric_layout);
        numericEditText=findViewById(R.id.etxt_numeric_text);

         singleChoiceRadioGroup=findViewById(R.id.radio_group);
         multipleChoiceAns=findViewById(R.id.multiselect_lyt);
         coordinatorLyt=findViewById(R.id.coordinator_lyt);

        shimmer_my_container =findViewById(R.id.shimmer_my_container);
        recyclerView =findViewById(R.id.recyclerView);
        no_active_survey_lyt =findViewById(R.id.no_active_survey_lyt);
        error_lyt=findViewById(R.id.error_lyt);
         btn_next =findViewById(R.id.btn_next);




        //loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN2, auth.class);
        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);

        Intent intent = getIntent();
        questionLink = intent.getIntExtra("questionLink",  -1);
        sessionID = intent.getIntExtra("sessionID", -1);





       /* assert getArguments() != null;
        questionLink = getArguments().getString("questionLink");

        assert getArguments() != null;
        sessionID=  getArguments().getInt("sessionID");*/


        loadQuestion();
        //set EditText type4 to accept numeric only
       // numericEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

        //DatePickerNone1
        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance ();
                mYear = calendar.get ( Calendar.YEAR );
                mMonth = calendar.get ( Calendar.MONTH );
                mDay = calendar.get ( Calendar.DAY_OF_MONTH );
                //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                //show dialog
                datePickerDialog = new DatePickerDialog (QuestionsActivity.this, new DatePickerDialog.OnDateSetListener () {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dobEditText.setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.show ();
            }
        });
        //DatePickerNone2
        dobEditText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance ();
                mYear = calendar.get ( Calendar.YEAR );
                mMonth = calendar.get ( Calendar.MONTH );
                mDay = calendar.get ( Calendar.DAY_OF_MONTH );
                //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                //show dialog
                datePickerDialog = new DatePickerDialog (QuestionsActivity.this, new DatePickerDialog.OnDateSetListener () {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dobEditText2.setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.show ();
            }
        });
        //DatePickerNone3
        dobEditText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance ();
                mYear = calendar.get ( Calendar.YEAR );
                mMonth = calendar.get ( Calendar.MONTH );
                mDay = calendar.get ( Calendar.DAY_OF_MONTH );
                //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                //show dialog
                datePickerDialog = new DatePickerDialog (QuestionsActivity.this, new DatePickerDialog.OnDateSetListener () {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dobEditText3.setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.show ();
            }
        });
        //DatePickerNone4
        dobEditText4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance ();
                mYear = calendar.get ( Calendar.YEAR );
                mMonth = calendar.get ( Calendar.MONTH );
                mDay = calendar.get ( Calendar.DAY_OF_MONTH );
                //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                //show dialog
                datePickerDialog = new DatePickerDialog (QuestionsActivity.this, new DatePickerDialog.OnDateSetListener () {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dobEditText4.setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.show ();
            }
        });
        //DatePickerNone5
        dobEditText5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance ();
                mYear = calendar.get ( Calendar.YEAR );
                mMonth = calendar.get ( Calendar.MONTH );
                mDay = calendar.get ( Calendar.DAY_OF_MONTH );
                //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                //show dialog
                datePickerDialog = new DatePickerDialog (QuestionsActivity.this, new DatePickerDialog.OnDateSetListener () {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dobEditText5.setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.show ();
            }
        });
        //DatePickerNone6
        dobEditText6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance ();
                mYear = calendar.get ( Calendar.YEAR );
                mMonth = calendar.get ( Calendar.MONTH );
                mDay = calendar.get ( Calendar.DAY_OF_MONTH );
                //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                //show dialog
                datePickerDialog = new DatePickerDialog (QuestionsActivity.this, new DatePickerDialog.OnDateSetListener () {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dobEditText6.setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.show ();
            }
        });

        //DatePickerFuture1
        dobEditTextfuture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(QuestionsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        dobEditTextfuture.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();
            }
        });

        //DatePickerFuture2
        dobEditTextfuture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(QuestionsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        dobEditTextfuture2.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();
            }
        });

        //DatePickerFuture3
        dobEditTextfuture3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(QuestionsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        dobEditTextfuture3.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();
            }
        });

        //DatePickerFuture4
        dobEditTextfuture4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(QuestionsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        dobEditTextfuture4.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();
            }
        });


        //DatePickerFuture5
        dobEditTextfuture5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(QuestionsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        dobEditTextfuture5.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();
            }
        });

        //DatePickerFuture6
        dobEditTextfuture6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(QuestionsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        dobEditTextfuture6.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();
            }
        });



        //DatePickerPast
        dobEditTextpast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(QuestionsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        dobEditTextpast.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();

            }
        });




        //DatePickerPast2
        dobEditTextpast2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(QuestionsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        dobEditTextpast2.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();

            }
        });
        //DatePickerPast3
        dobEditTextpast3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(QuestionsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        dobEditTextpast3.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();

            }
        });

        //DatePickerPast4
        dobEditTextpast4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(QuestionsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        dobEditTextpast4.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();

            }
        });


        //DatePickerPast5
        dobEditTextpast5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(QuestionsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        dobEditTextpast5.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();

            }
        });

        //DatePickerPast6
        dobEditTextpast6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(QuestionsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        dobEditTextpast6.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open Text not repeat

                if(questions.getQuestion_type()==1 && questions.isIs_required()){
                    if(openTextEtxt.getText().toString().equals("")){
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();}
                    else{
                        provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), openTextEtxt.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() == 1 )
                {
                    provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), openTextEtxt.getText().toString());

                }


                else if (questions.getQuestion_type()==4 && questions.isIs_required()){
                    if (numericEditText.getText().toString().equals("")){
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        // }else{
                    }else{provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), numericEditText.getText().toString());}
                }

                else if (questions.getQuestion_type() == 4){

                    provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), numericEditText.getText().toString());
                }

                //none
                //datepicker none & not repeatable
                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("none") && !questions.isIs_repeatable()){
                    if(dobEditText.getText().toString().equals("")) {
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        //&& questions.getDate_validation().contentEquals("none")
                    }
                    {
                        provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditText.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() ==5 && questions.getDate_validation().equals("none") && !questions.isIs_repeatable()){
                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditText.getText().toString());

                }



                //datepicker none & not repeatable
                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("none") && questions.isIs_repeatable() && repeat_count==1){
                    if(dobEditText.getText().toString().equals("")) {
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        //&& questions.getDate_validation().contentEquals("none")
                    }
                    {
                        provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditText.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() ==5 && questions.getDate_validation().equals("none") && questions.isIs_repeatable() && repeat_count==1){
                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditText.getText().toString());

                }
                //datepicker none & repeatable count2
                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("none") && questions.isIs_repeatable() && repeat_count==2){
                    if(dobEditText.getText().toString().equals("") ||dobEditText2.getText().toString().equals("")) {
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        //&& questions.getDate_validation().contentEquals("none")
                    }
                    {
                        provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditText.getText().toString()+ " ," + dobEditText2.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() ==5 &&questions.getDate_validation().equals("none") && questions.isIs_repeatable() && repeat_count==2 ){
                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditText.getText().toString()+ " ," + dobEditText2.getText().toString());

                }
                // datepicker none & repeatable count3
                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("none") && questions.isIs_repeatable() && repeat_count==3){
                    if(dobEditText.getText().toString().equals("") || dobEditText2.getText().toString().equals("") ||dobEditText3.getText().toString().equals("")) {
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        //&& questions.getDate_validation().contentEquals("none")
                    }
                    {
                        provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditText.getText().toString() +" ," + dobEditText2.getText().toString() +" ," + dobEditText3.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() ==5 &&questions.getDate_validation().equals("none") && questions.isIs_repeatable() && repeat_count==3 ){
                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditText.getText().toString()+" ," + dobEditText2.getText().toString()+" ," + dobEditText3.getText().toString());

                }
                // datepicker none & repeatable count4
                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("none") && questions.isIs_repeatable() && repeat_count==4){
                    if(dobEditText.getText().toString().equals("") ||dobEditText2.getText().toString().equals("") || dobEditText3.getText().toString().equals("") || dobEditText4.getText().toString().equals("")) {
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        //&& questions.getDate_validation().contentEquals("none")
                    }
                    {
                        provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditText.getText().toString()+ " ," +dobEditText2.getText().toString().equals("")+ " ," +dobEditText3.getText().toString().equals("")+ " ," +dobEditText4.getText().toString().equals(""));
                    }

                }
                else if (questions.getQuestion_type() ==5 &&questions.getDate_validation().equals("none") && questions.isIs_repeatable() && repeat_count1.getRepeat_count()==4 ){
                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditText.getText().toString() + " ," +dobEditText2.getText().toString().equals("")+ " ," +dobEditText3.getText().toString().equals("")+ " ," +dobEditText4.getText().toString().equals(""));

                }
                // datepicker none & repeatable count5
                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("none") && questions.isIs_repeatable() && repeat_count==5){
                    if(dobEditText.getText().toString().equals("") || dobEditText2.getText().toString().equals("") || dobEditText3.getText().toString().equals("") || dobEditText4.getText().toString().equals("") || dobEditText5.getText().toString().equals("")) {
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        //&& questions.getDate_validation().contentEquals("none")
                    }
                    {
                        provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditText.getText().toString()+ " ," +dobEditText2.getText().toString().equals("")+ " ," +dobEditText3.getText().toString().equals("")+ " ," +dobEditText4.getText().toString().equals("")+ " ," +dobEditText5.getText().toString().equals(""));
                    }

                }
                else if (questions.getQuestion_type() ==5 &&questions.getDate_validation().equals("none") && questions.isIs_repeatable() && repeat_count==5){
                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditText.getText().toString()+ " ," +dobEditText2.getText().toString().equals("")+ " ," +dobEditText3.getText().toString().equals("")+ " ," +dobEditText4.getText().toString().equals("")+ " ," +dobEditText5.getText().toString().equals(""));

                }

                // datepicker none & repeatable count6
                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("none") && questions.isIs_repeatable() && repeat_count==6){
                    if(dobEditText.getText().toString().equals("") || dobEditText2.getText().toString().equals("") || dobEditText3.getText().toString().equals("") || dobEditText4.getText().toString().equals("") || dobEditText4.getText().toString().equals("") || dobEditText5.getText().toString().equals("") || dobEditText6.getText().toString().equals("")) {
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                        //&& questions.getDate_validation().contentEquals("none")
                    }
                    {
                        provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditText.getText().toString()+ " ," +dobEditText2.getText().toString().equals("")+ " ," +dobEditText3.getText().toString().equals("")+ " ," +dobEditText4.getText().toString().equals("")+ " ," +dobEditText5.getText().toString().equals("")+ " ," +dobEditText6.getText().toString().equals(""));
                    }

                }
                else if (questions.getQuestion_type() ==5 &&questions.getDate_validation().equals("none") && questions.isIs_repeatable() && repeat_count==6){
                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditText.getText().toString() + " ," +dobEditText2.getText().toString().equals("")+ " ," +dobEditText3.getText().toString().equals("")+ " ," +dobEditText4.getText().toString().equals("")+ " ," +dobEditText5.getText().toString().equals("")+ " ," +dobEditText6.getText().toString().equals(""));

                }

                //datepicker restrict future notrrepeat

                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("restrict_future") && !questions.isIs_repeatable()){
                    if(dobEditTextfuture.getText().toString().equals("")) {
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    }else{
                        provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextfuture.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() ==5 && !questions.isIs_required()  && questions.getDate_validation().equals("restrict_future") && !questions.isIs_repeatable()){
                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextfuture.getText().toString());

                }


                //datepicker restrict future repeat count one
                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("restrict_future") && questions.isIs_repeatable() && repeat_count==1){
                    if(dobEditTextfuture.getText().toString().equals("")) {
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    }else{
                        provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextfuture.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() ==5&& !questions.isIs_required() && questions.getDate_validation().equals("restrict_future") && questions.isIs_repeatable()  && repeat_count==1){
                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextfuture.getText().toString());

                }

                //datepicker restrict future repeat count 2
                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("restrict_future") && questions.isIs_repeatable() && repeat_count==2){
                    if(dobEditTextfuture.getText().toString().equals("") || dobEditTextfuture2.getText().toString().equals("")) {
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    }else{
                        provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextfuture.getText().toString() + " ," +dobEditTextfuture2.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() ==5 && !questions.isIs_required() && questions.getDate_validation().equals("restrict_future")&& questions.isIs_repeatable() && repeat_count==2){
                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextfuture.getText().toString() + " ," +dobEditTextfuture2.getText().toString());

                }
                //datepicker restrict future repeat count 3
                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("restrict_future") && questions.isIs_repeatable() && repeat_count==3){
                    if(dobEditTextfuture.getText().toString().equals("") || dobEditTextfuture2.getText().toString().equals("") || dobEditTextfuture3.getText().toString().equals("")) {
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    }else{
                        provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextfuture.getText().toString() + " ," +dobEditTextfuture2.getText().toString()+ " ," +dobEditTextfuture3.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() ==5 && !questions.isIs_required() && questions.getDate_validation().equals("restrict_future")&& questions.isIs_repeatable() && repeat_count==3){
                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextfuture.getText().toString()+ " ," +dobEditTextfuture2.getText().toString()+ " ," +dobEditTextfuture3.getText().toString());

                }


                //datepicker restrict future repeat count 4
                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("restrict_future") && questions.isIs_repeatable() && repeat_count==4){
                    if(dobEditTextfuture.getText().toString().equals("") || dobEditTextfuture2.getText().toString().equals("") || dobEditTextfuture3.getText().toString().equals("") || dobEditTextfuture4.getText().toString().equals("") ) {
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    }else{
                        provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextfuture.getText().toString()+ " ," +dobEditTextfuture2.getText().toString()+ " ," +dobEditTextfuture3.getText().toString()+ " ," +dobEditTextfuture4.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() ==5 && !questions.isIs_required() && questions.getDate_validation().equals("restrict_future") && questions.isIs_repeatable() && repeat_count==4){
                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextfuture.getText().toString()+ " ," +dobEditTextfuture2.getText().toString()+ " ," +dobEditTextfuture3.getText().toString()+ " ," +dobEditTextfuture4.getText().toString());

                }

                //datepicker restrict future repeat count 5
                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("restrict_future") && questions.isIs_repeatable() && repeat_count==5){
                    if(dobEditTextfuture.getText().toString().equals("")||dobEditTextfuture2.getText().toString().equals("")|| dobEditTextfuture3.getText().toString().equals("")|| dobEditTextfuture4.getText().toString().equals("") || dobEditTextfuture5.getText().toString().equals("")) {
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    }else{
                        provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextfuture.getText().toString()+ " ," +dobEditTextfuture2.getText().toString()+ " ," +dobEditTextfuture3.getText().toString() + " ," +dobEditTextfuture4.getText().toString() + " ," +dobEditTextfuture5.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() ==5 && !questions.isIs_required() && questions.getDate_validation().equals("restrict_future") && questions.isIs_repeatable() && repeat_count==5){
                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextfuture.getText().toString()+ " ," +dobEditTextfuture2.getText().toString()+ " ," +dobEditTextfuture3.getText().toString()+ " ," +dobEditTextfuture4.getText().toString()+ " ," +dobEditTextfuture5.getText().toString());

                }

                //datepicker restrict future repeat count 6
                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("restrict_future") && questions.isIs_repeatable() && repeat_count==6){
                    if(dobEditTextfuture.getText().toString().equals("") ||dobEditTextfuture2.getText().toString().equals("")|| dobEditTextfuture3.getText().toString().equals("")|| dobEditTextfuture4.getText().toString().equals("") || dobEditTextfuture5.getText().toString().equals("") ||dobEditTextfuture6.getText().toString().equals("")) {
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    }else{
                        provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextfuture.getText().toString()+ " ," +dobEditTextfuture2.getText().toString()+ " ," +dobEditTextfuture3.getText().toString()+ " ," +dobEditTextfuture4.getText().toString()+ " ," +dobEditTextfuture5.getText().toString()+ " ," +dobEditTextfuture6.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() ==5 && !questions.isIs_required() && questions.getDate_validation().equals("restrict_future") && questions.isIs_repeatable() && repeat_count==6){
                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextfuture.getText().toString()+ " ," +dobEditTextfuture2.getText().toString()+ " ," +dobEditTextfuture3.getText().toString()+ " ," +dobEditTextfuture4.getText().toString()+ " ," +dobEditTextfuture5.getText().toString()+ " ," +dobEditTextfuture6.getText().toString());

                }


                //restrict past date non repeat
                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("restrict_past") && !questions.isIs_repeatable()){

                    if(dobEditTextpast.getText().toString().equals("")) {
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    }

                    {

                        provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextpast.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() ==5 && questions.getDate_validation().equals("restrict_past")&& !questions.isIs_repeatable()){
                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextpast.getText().toString());

                }
                //restrict past date  repeat count 2
                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("restrict_past") && questions.isIs_repeatable() && repeat_count==2){

                    if(dobEditTextpast.getText().toString().equals("") || dobEditTextpast2.getText().toString().equals("")) {
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    }

                    {

                        provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextpast.getText().toString()+ " ,"+ dobEditTextpast2.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() ==5 && questions.getDate_validation().equals("restrict_past")&& questions.isIs_repeatable() && repeat_count==2){
                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextpast.getText().toString()+ " ,"+ dobEditTextpast2.getText().toString());

                }

                //restrict past date  repeat count 3
                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("restrict_past") && questions.isIs_repeatable() && repeat_count==3){

                    if(dobEditTextpast.getText().toString().equals("") || dobEditTextpast2.getText().toString().equals("") || dobEditTextpast3.getText().toString().equals("")) {
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    }

                    {

                        provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextpast.getText().toString() + " ," + dobEditTextpast2.getText().toString() + " ," +dobEditTextpast3.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() ==5 && questions.getDate_validation().equals("restrict_past")&& questions.isIs_repeatable() && repeat_count==3){
                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextpast.getText().toString()+ " ," + dobEditTextpast2.getText().toString() + " ," +dobEditTextpast3.getText().toString());

                }
                //restrict past date  repeat count 4
                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("restrict_past") && questions.isIs_repeatable() && repeat_count==4){

                    if(dobEditTextpast.getText().toString().equals("") ||dobEditTextpast2.getText().toString().equals("") ||dobEditTextpast3.getText().toString().equals("") ||dobEditTextpast4.getText().toString().equals("")) {
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    }

                    {

                        provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextpast.getText().toString()+ " ,"+ dobEditTextpast2.getText().toString()+ " ,"+ dobEditTextpast3.getText().toString()+ " ,"+ dobEditTextpast4.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() ==5 && questions.getDate_validation().equals("restrict_past")&& questions.isIs_repeatable() && repeat_count1.getRepeat_count()==4){
                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextpast.getText().toString() + " ,"+ dobEditTextpast2.getText().toString()+ " ,"+ dobEditTextpast3.getText().toString()+ " ,"+ dobEditTextpast4.getText().toString());

                }
                //restrict past date  repeat count 5
                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("restrict_past") && questions.isIs_repeatable() && repeat_count==5){

                    if(dobEditTextpast.getText().toString().equals("") || dobEditTextpast2.getText().toString().equals("") ||dobEditTextpast3.getText().toString().equals("") ||dobEditTextpast4.getText().toString().equals("") ||dobEditTextpast5.getText().toString().equals("")) {
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    }

                    {

                        provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextpast.getText().toString()+ " ,"+dobEditTextpast2.getText().toString() + " ,"+dobEditTextpast3.getText().toString()+ " ,"+dobEditTextpast4.getText().toString()+ " ,"+dobEditTextpast5.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() ==5 && questions.getDate_validation().equals("restrict_past")&& questions.isIs_repeatable() && repeat_count1.getRepeat_count()==5){
                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextpast.getText().toString()+ " ,"+dobEditTextpast2.getText().toString() + " ,"+dobEditTextpast3.getText().toString()+ " ,"+dobEditTextpast4.getText().toString()+ " ,"+dobEditTextpast5.getText().toString());

                }

                //restrict past date  repeat count 6
                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("restrict_past") && questions.isIs_repeatable() && repeat_count==6){

                    if(dobEditTextpast.getText().toString().equals("") ||dobEditTextpast2.getText().toString().equals("") ||dobEditTextpast3.getText().toString().equals("")||dobEditTextpast4.getText().toString().equals("")||dobEditTextpast5.getText().toString().equals("")||dobEditTextpast6.getText().toString().equals("")) {
                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                    }

                    {

                        provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextpast.getText().toString()+ " ,"+dobEditTextpast2.getText().toString() + " ,"+dobEditTextpast3.getText().toString()+ " ,"+dobEditTextpast4.getText().toString()+ " ,"+dobEditTextpast5.getText().toString()+ " ,"+dobEditTextpast6.getText().toString());
                    }
                }
                else if (questions.getQuestion_type() ==5 && questions.getDate_validation().equals("restrict_past")&& questions.isIs_repeatable() && repeat_count==6){
                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditTextpast.getText().toString()+ " ,"+dobEditTextpast2.getText().toString() + " ,"+dobEditTextpast3.getText().toString()+ " ,"+dobEditTextpast4.getText().toString()+ " ,"+dobEditTextpast5.getText().toString()+ " ,"+dobEditTextpast6.getText().toString());

                }





                else if (questions.getQuestion_type() == 2){

                    int radioButtonID = singleChoiceRadioGroup.getCheckedRadioButtonId();


                    if (radioButtonID == -1){

                        Toast.makeText(QuestionsActivity.this, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();

                    }else {
                        View radioButton = singleChoiceRadioGroup.findViewById(radioButtonID);
                        int idx = singleChoiceRadioGroup.indexOfChild(radioButton);


                        provideAnswers(sessionID,questions.getId(),String.valueOf(answerList.get(idx).getId()), openText);
                    }

                }
                else if (questions.getQuestion_type() == 3){


                    for(int i=0; i<multipleChoiceAns.getChildCount(); i++) {
                        View nextChild = multipleChoiceAns.getChildAt(i);

                        if(nextChild instanceof CheckBox)
                        {
                            checkBox = (CheckBox) nextChild;
                            if (checkBox.isChecked()) {
                                multiAnswerList.add(checkBox.getId());
                            }
                        }

                    }

                    provideAnswers(sessionID,questions.getId(),String.valueOf(multiAnswerList), openText);

//                    Toast.makeText(context, String.valueOf(multiAnswerList), Toast.LENGTH_SHORT).show();

                }

                else {
                    Toast.makeText(QuestionsActivity.this, "Answer the question", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    private void provideAnswers(int sessionID, int questionNumber, String answer, String openText) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("session", sessionID);
            jsonObject.put("question", questionNumber);
            jsonObject.put("answer", answer);
            jsonObject.put("open_text", openText);


        } catch (JSONException e) {
            e.printStackTrace();
        }

       // String auth_token = loggedInUser.getAuth_token();

        String auth_token = loggedInUser.getAuth_token();
        String urls ="?user_id="+auth_token;


        // https://ushauriapi.kenyahmis.org/nishauri/q_answer
        //https://psurveyapitest.kenyahmis.org/api/questions/answer/
        AndroidNetworking.post("https://ushauriapi.kenyahmis.org/nishauri/q_answer")
               // .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setContentType("application.json")
                .addJSONObjectBody(jsonObject) // posting json
                .build()
                .getAsJSONObject(new JSONObjectRequestListener(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Answer", response.toString());

                        try {

                            String  message = response.has("Message") ? response.getString("Message") : "";

                            if (response.has("link")){

                                int link = response.has("link") ? response.getInt("link") : 0;
                                int sessionId = response.has("session") ? response.getInt("session") : 0;


                               /* Bundle bundle = new Bundle();
                                bundle.putString("questionLink",link);
                                bundle.putInt("sessionID",sessionId);
                                Navigation.findNavController(root).navigate(R.id.nav_questions, bundle);*/

                                Intent intent =new Intent(QuestionsActivity.this, QuestionsActivity.class);
                                intent.putExtra("questionLink",link);
                                intent.putExtra("sessionID",sessionId);
                                startActivity(intent);




                            }
                            else if (message.contains("Questionnaire complete")){
                               // NavHostFragment.findNavController(QuestionsFragment.this).navigate(R.id.nav_complete_survey);
                                Intent intent =new Intent(QuestionsActivity.this, CompleteSurveyActivity.class);
                                startActivity(intent);


                                Toast.makeText(QuestionsActivity.this, message, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("Answer Error", error.getErrorBody());
                        Toast.makeText(QuestionsActivity.this, "Error: "+error.getErrorBody(), Toast.LENGTH_SHORT).show();

                        //Snackbar.make(root.findViewById(R.id.frag_questions), "Error: "+error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });

    }

    private void loadQuestion() {

//        //int session2 = sessionID;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("next_q", questionLink);
            jsonObject.put("session", sessionID);

        } catch (JSONException e) {
            e.printStackTrace();
        }



        String auth_token = loggedInUser.getAuth_token();
        String urls ="?user_id="+auth_token;
        //https://ushauriapi.kenyahmis.org/nishauri/next_q
        AndroidNetworking.post("https://ushauriapi.kenyahmis.org/nishauri/next_q")
               // .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setContentType("application.json")
                .addJSONObjectBody(jsonObject) // posting json
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.e("Success question", response.toString());


                        try {

                            String  errors = response.has("error") ? response.getString("error") : "" ;
                            String  message = response.has("Message") ? response.getString("Message") : "" ;


                            if (response.has("Question")){

                                JSONObject question = response.getJSONObject("Question");

                                int questionId = question.has("id") ? question.getInt("id"): 0;
                                String questionName = question.has("question") ? question.getString("question") : "";
                                String date_validation = question.has("date_validation") ? question.getString("date_validation") : "";
                                int questionType = question.has("question_type") ? question.getInt("question_type") : 0;
                                String createdAt = question.has("created_at") ? question.getString("created_at") : "";
                                int questionnaire = question.has("questionnaire") ? question.getInt("questionnaire") : 0;
                                int createdBy = question.has("created_by") ? question.getInt("created_by") : 0;
                                boolean is_required = question.has("is_required") ? question.getBoolean("is_required") : Boolean.parseBoolean("");
                                boolean is_repeatable = question.has("is_repeatable") ? question.getBoolean("is_repeatable") : Boolean.parseBoolean("");


                                questions = new Question(questionId,questionName,questionType,createdAt,questionnaire,createdBy, is_required, date_validation, is_repeatable);


                                JSONArray ans = response.getJSONArray("Ans");

                                if (ans.length() > 0){


                                    for (int i = 0; i < ans.length(); i++) {

                                        JSONObject item = (JSONObject) ans.get(i);


                                        int  ansID = item.has("id") ? item.getInt("id") : 0;
                                        String option = item.has("option") ? item.getString("option") : "";
                                        String created_at = item.has("created_at") ? item.getString("created_at") : "";
                                        int  questionID = item.has("question") ? item.getInt("question") : 0;
                                        int  created_by = item.has("created_by") ? item.getInt("created_by") : 0;



                                        answers = new Answer(ansID, option, created_at, questionID, created_by);
                                        answerList.add(answers);

                                        if (response.has("repeat_count")) {
                                            repeat_count = response.getInt("repeat_count");

                                            if (questions.getQuestion_type() == 1  ){
                                                openTextTil.setVisibility(View.VISIBLE);
                                            }


                                            else if (questions.getQuestion_type() == 2) {
                                                singleChoiceRadioGroup.setVisibility(View.VISIBLE);

                                                RadioButton rbn = new RadioButton(QuestionsActivity.this);
                                                rbn.setId(View.generateViewId());
                                                rbn.setText(answers.getOption());
                                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                                                rbn.setLayoutParams(params);
                                                singleChoiceRadioGroup.addView(rbn);


                                            } else if (questions.getQuestion_type() == 3) {

                                                openTextTil.setVisibility(View.GONE);
                                                singleChoiceRadioGroup.setVisibility(View.GONE);
                                                multipleChoiceAns.setVisibility(View.VISIBLE);

                                                checkBox = new CheckBox(QuestionsActivity.this);
                                                checkBox.setId(answers.getId());
                                                checkBox.setText(answers.getOption());
                                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                                                checkBox.setLayoutParams(params);
                                                multipleChoiceAns.addView(checkBox);

                                            } else if (questions.getQuestion_type() == 4) {
                                                numericText.setVisibility(View.VISIBLE);
                                                numericEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                                            }
                                            //none
                                            else if (questions.getQuestion_type() == 5 && questions.getDate_validation().equals("none") && questions.isIs_repeatable() && repeat_count==1) {
                                                dateTextTil.setVisibility(View.VISIBLE);
                                            }
                                            //none not repeat
                                            else if (questions.getQuestion_type() == 5 && questions.getDate_validation().equals("none") && !questions.isIs_repeatable()) {
                                                dateTextTil.setVisibility(View.VISIBLE);
                                            }

                                            //try
                                            // restrict future1
                                            else if (questions.getQuestion_type() == 5 && questions.getDate_validation().equals("restrict_future") && !questions.isIs_repeatable()) {
                                                // dateTextTil.setVisibility(View.VISIBLE);
                                                dateTextTilfuture.setVisibility(View.VISIBLE);

                                            }

                                            // restrict future1
                                            else if (questions.getQuestion_type() == 5 && questions.getDate_validation().equals("restrict_future") && questions.isIs_repeatable() && repeat_count==1) {
                                                dateTextTilfuture.setVisibility(View.VISIBLE);

                                            }

                                            // restrict future2
                                            else if (questions.getQuestion_type() == 5 && questions.getDate_validation().equals("restrict_future") && questions.isIs_repeatable() && repeat_count==2) {
                                                dateTextTilfuture.setVisibility(View.VISIBLE);
                                                dateTextTilfuture2.setVisibility(View.VISIBLE);

                                            }


                                            // restrict future3
                                            else if (questions.getQuestion_type() == 5 && questions.getDate_validation().equals("restrict_future") && questions.isIs_repeatable()  && repeat_count==3) {
                                                dateTextTilfuture.setVisibility(View.VISIBLE);
                                                dateTextTilfuture2.setVisibility(View.VISIBLE);
                                                dateTextTilfuture3.setVisibility(View.VISIBLE);

                                            }

                                            // restrict future4
                                            else if (questions.getQuestion_type() == 5 && questions.getDate_validation().equals("restrict_future") && questions.isIs_repeatable() && repeat_count==4) {
                                                dateTextTilfuture.setVisibility(View.VISIBLE);
                                                dateTextTilfuture2.setVisibility(View.VISIBLE);
                                                dateTextTilfuture3.setVisibility(View.VISIBLE);
                                                dateTextTilfuture4.setVisibility(View.VISIBLE);

                                            }
                                            // restrict future5
                                            else if (questions.getQuestion_type() == 5 && questions.getDate_validation().equals("restrict_future") && questions.isIs_repeatable() && repeat_count==5) {
                                                dateTextTilfuture.setVisibility(View.VISIBLE);
                                                dateTextTilfuture2.setVisibility(View.VISIBLE);
                                                dateTextTilfuture3.setVisibility(View.VISIBLE);
                                                dateTextTilfuture4.setVisibility(View.VISIBLE);
                                                dateTextTilfuture5.setVisibility(View.VISIBLE);

                                            }

                                            // restrict future6
                                            else if (questions.getQuestion_type() == 5 && questions.getDate_validation().equals("restrict_future") && questions.isIs_repeatable()&& repeat_count==6) {
                                                dateTextTilfuture.setVisibility(View.VISIBLE);
                                                dateTextTilfuture2.setVisibility(View.VISIBLE);
                                                dateTextTilfuture3.setVisibility(View.VISIBLE);
                                                dateTextTilfuture4.setVisibility(View.VISIBLE);
                                                dateTextTilfuture5.setVisibility(View.VISIBLE);
                                                dateTextTilfuture6.setVisibility(View.VISIBLE);

                                            }





                                            //restrict past repeat none
                                            else if (questions.getQuestion_type() == 5 && questions.getDate_validation().equals("restrict_past") && !questions.isIs_repeatable()) {
                                                dateTextTilpast.setVisibility(View.VISIBLE);
                                            }
                                            //restrict past2
                                            else if (questions.getQuestion_type() == 5 && questions.getDate_validation().equals("restrict_past") && questions.isIs_repeatable() && repeat_count1.getRepeat_count()==2) {
                                                dateTextTilpast.setVisibility(View.VISIBLE);
                                                dateTextTilpast2.setVisibility(View.VISIBLE);
                                            }

                                            //restrict past3
                                            else if (questions.getQuestion_type() == 5 && questions.getDate_validation().equals("restrict_past") && questions.isIs_repeatable() && repeat_count1.getRepeat_count()==3) {
                                                dateTextTilpast.setVisibility(View.VISIBLE);
                                                dateTextTilpast2.setVisibility(View.VISIBLE);
                                                dateTextTilpast3.setVisibility(View.VISIBLE);
                                            }


                                            //restrict past4
                                            else if (questions.getQuestion_type() == 5 && questions.getDate_validation().equals("restrict_past")&& questions.isIs_repeatable() && repeat_count1.getRepeat_count()==4) {
                                                dateTextTilpast.setVisibility(View.VISIBLE);
                                                dateTextTilpast2.setVisibility(View.VISIBLE);
                                                dateTextTilpast3.setVisibility(View.VISIBLE);
                                                dateTextTilpast4.setVisibility(View.VISIBLE);
                                            }
                                            //restrict past5
                                            else if (questions.getQuestion_type() == 5 && questions.getDate_validation().equals("restrict_past") && questions.isIs_repeatable() && repeat_count1.getRepeat_count()==5) {
                                                dateTextTilpast.setVisibility(View.VISIBLE);
                                                dateTextTilpast2.setVisibility(View.VISIBLE);
                                                dateTextTilpast3.setVisibility(View.VISIBLE);
                                                dateTextTilpast4.setVisibility(View.VISIBLE);
                                                dateTextTilpast5.setVisibility(View.VISIBLE);
                                            }

                                            //restrict past6
                                            else if (questions.getQuestion_type() == 5 && questions.getDate_validation().equals("restrict_past") && questions.isIs_repeatable() && repeat_count1.getRepeat_count()==6) {
                                                dateTextTilpast.setVisibility(View.VISIBLE);
                                                dateTextTilpast2.setVisibility(View.VISIBLE);
                                                dateTextTilpast3.setVisibility(View.VISIBLE);
                                                dateTextTilpast4.setVisibility(View.VISIBLE);
                                                dateTextTilpast5.setVisibility(View.VISIBLE);
                                                dateTextTilpast6.setVisibility(View.VISIBLE);
                                            }


                                            else {
                                                Toast.makeText(QuestionsActivity.this, "No answers found for this question", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    }

                                }
                                if (!questions.isIs_required()){
                                    surveyQuestion.setText(questions.getQuestion());}
                                else if(questions.isIs_required()){
                                    surveyQuestion.setText(questions.getQuestion()+" * ");
                                }

                            }
                            else if (message.contains("Questionnaire complete")){

                                Intent intent = new Intent(QuestionsActivity.this, CompleteSurveyActivity.class);
                                startActivity(intent);

                               // NavHostFragment.findNavController(QuestionsFragment.this).navigate(R.id.nav_complete_survey);
                                Toast.makeText(QuestionsActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                            else {

                                no_active_survey_lyt.setVisibility(View.VISIBLE);
                                Toast.makeText(QuestionsActivity.this, errors, Toast.LENGTH_SHORT).show();
                              //  Snackbar.make(root.findViewById(R.id.frag_questions), errors, Snackbar.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {

                        Log.e("Error", error.getErrorBody());

                        if (error.getErrorDetail().equals("connectionError")){

                            error_lyt.setVisibility(View.VISIBLE);

                        }
                        Toast.makeText(QuestionsActivity.this, "Error: " + error.getErrorBody(), Toast.LENGTH_SHORT).show();

                       // Snackbar.make(root.findViewById(R.id.frag_questions), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });

    }
}