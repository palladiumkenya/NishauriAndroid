package com.mhealthkenya.psurvey.fragments.survey;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.mhealthkenya.psurvey.AQ_Repository;
import com.mhealthkenya.psurvey.AllQViewModel;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.adapters.questionnairesAdapter;
import com.mhealthkenya.psurvey.adapters.questionsAdapter;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.interfaces.APIClient;
import com.mhealthkenya.psurvey.interfaces.APIInterface;
import com.mhealthkenya.psurvey.models.Answers;
import com.mhealthkenya.psurvey.models.Questionnaires;
import com.mhealthkenya.psurvey.models.Questions;
import com.mhealthkenya.psurvey.models.Answer;
import com.mhealthkenya.psurvey.models.Question;
import com.mhealthkenya.psurvey.models.QuestionsList;
import com.mhealthkenya.psurvey.models.auth;
import com.mhealthkenya.psurvey.models.repeat_count;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mhealthkenya.psurvey.depedancies.AppController.TAG;


public class QuestionsFragment extends Fragment {
    DatePickerDialog datePickerDialog;

    private Unbinder unbinder;
    private View root;
    private Context context;

    private String openText = "";
    private String questionLink;
    private int sessionID;
    int repeat_count;

    private CheckBox checkBox;

    private auth loggedInUser;
    private Question questions;
    private repeat_count repeat_count1;
    private repeat_count  _repeat_count;
    private Answer answers;
    private ArrayList<Answer> answerList = new ArrayList<>();
    private List<Integer> multiAnswerList = new ArrayList<>();

    private int mYear, mMonth, mDay;



    @BindView(R.id.tv_survey_question)
    MaterialTextView surveyQuestion;
    //openText1
    @BindView(R.id.til_open_text)
    TextInputLayout openTextTil;
    @BindView(R.id.etxt_open_text)
    TextInputEditText openTextEtxt;

    //openText2
    @BindView(R.id.til_open_text2)
    TextInputLayout openTextTil2;
    @BindView(R.id.etxt_open_text2)
    TextInputEditText openTextEtxt2;

    //openText3
    @BindView(R.id.til_open_text3)
    TextInputLayout openTextTil3;
    @BindView(R.id.etxt_open_text3)
    TextInputEditText openTextEtxt3;

    //openText4
    @BindView(R.id.til_open_text4)
    TextInputLayout openTextTil4;
    @BindView(R.id.etxt_open_text4)
    TextInputEditText openTextEtxt4;

    //openText5
    @BindView(R.id.til_open_text5)
    TextInputLayout openTextTil5;
    @BindView(R.id.etxt_open_text5)
    TextInputEditText openTextEtxt5;

    //openText6
    @BindView(R.id.til_open_text6)
    TextInputLayout openTextTil6;
    @BindView(R.id.etxt_open_text6)
    TextInputEditText openTextEtxt6;

    //dateNone1
    @BindView(R.id.dateLayout)
    TextInputLayout dateTextTil;
    @BindView(R.id.dob)
    TextInputEditText dobEditText;
    //dateNone2
    @BindView(R.id.dateLayout2)
    TextInputLayout dateTextTil2;
    @BindView(R.id.dob2)
    TextInputEditText dobEditText2;
    //dateNone3
    @BindView(R.id.dateLayout3)
    TextInputLayout dateTextTil3;
    @BindView(R.id.dob3)
    TextInputEditText dobEditText3;
    //dateNone4
    @BindView(R.id.dateLayout4)
    TextInputLayout dateTextTil4;
    @BindView(R.id.dob4)
    TextInputEditText dobEditText4;
    //dateNone5
    @BindView(R.id.dateLayout5)
    TextInputLayout dateTextTil5;
    @BindView(R.id.dob5)
    TextInputEditText dobEditText5;
    //dateNone6
    @BindView(R.id.dateLayout6)
    TextInputLayout dateTextTil6;
    @BindView(R.id.dob6)
    TextInputEditText dobEditText6;

    //datefuture1
    @BindView(R.id.dateLayoutfuture)
    TextInputLayout dateTextTilfuture;
    @BindView(R.id.dobfuture)
    TextInputEditText dobEditTextfuture;


    //datefuture2
    @BindView(R.id.dateLayoutfuture2)
    TextInputLayout dateTextTilfuture2;
    @BindView(R.id.dobfuture2)
    TextInputEditText dobEditTextfuture2;

    //datefuture3
    @BindView(R.id.dateLayoutfuture3)
    TextInputLayout dateTextTilfuture3;
    @BindView(R.id.dobfuture3)
    TextInputEditText dobEditTextfuture3;

    //datefuture4
    @BindView(R.id.dateLayoutfuture4)
    TextInputLayout dateTextTilfuture4;
    @BindView(R.id.dobfuture4)
    TextInputEditText dobEditTextfuture4;

    //datefuture5
    @BindView(R.id.dateLayoutfuture5)
    TextInputLayout dateTextTilfuture5;
    @BindView(R.id.dobfuture5)
    TextInputEditText dobEditTextfuture5;

    //datefuture6
    @BindView(R.id.dateLayoutfuture6)
    TextInputLayout dateTextTilfuture6;
    @BindView(R.id.dobfuture6)
    TextInputEditText dobEditTextfuture6;


    //datepast1
    @BindView(R.id.dateLayoutpast)
    TextInputLayout dateTextTilpast;
    @BindView(R.id.dobpast)
    TextInputEditText dobEditTextpast;

    //datepast2
    @BindView(R.id.dateLayoutpast2)
    TextInputLayout dateTextTilpast2;
    @BindView(R.id.dobpast2)
    TextInputEditText dobEditTextpast2;

    //datepast3
    @BindView(R.id.dateLayoutpast3)
    TextInputLayout dateTextTilpast3;
    @BindView(R.id.dobpast3)
    TextInputEditText dobEditTextpast3;

    //datepast4
    @BindView(R.id.dateLayoutpast4)
    TextInputLayout dateTextTilpast4;
    @BindView(R.id.dobpast4)
    TextInputEditText dobEditTextpast4;

    //datepast5
    @BindView(R.id.dateLayoutpast5)
    TextInputLayout dateTextTilpast5;
    @BindView(R.id.dobpast5)
    TextInputEditText dobEditTextpast5;

    //datepast6
    @BindView(R.id.dateLayoutpast6)
    TextInputLayout dateTextTilpast6;
    @BindView(R.id.dobpast6)
    TextInputEditText dobEditTextpast6;




    @BindView(R.id.til_numeric_layout)
    TextInputLayout numericText;

    @BindView(R.id.etxt_numeric_text)
    TextInputEditText numericEditText;





    @BindView(R.id.radio_group)
    RadioGroup singleChoiceRadioGroup;

    @BindView(R.id.multiselect_lyt)
    LinearLayout multipleChoiceAns;

    @BindView(R.id.coordinator_lyt)
    CoordinatorLayout coordinatorLyt;

    @BindView(R.id.shimmer_my_container)
    ShimmerFrameLayout shimmer_my_container;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.no_active_survey_lyt)
    LinearLayout no_active_survey_lyt;

    @BindView(R.id.error_lyt)
    LinearLayout error_lyt;

    @BindView(R.id.btn_next)
    Button btn_next;

    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        this.context = ctx;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_questions, container, false);
        unbinder = ButterKnife.bind(this, root);

        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);



        assert getArguments() != null;
        questionLink = getArguments().getString("questionLink");

        assert getArguments() != null;
        sessionID=  getArguments().getInt("sessionID");


        loadQuestion();
        //set EditText type4 to accept numeric only
        numericEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

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
                datePickerDialog = new DatePickerDialog ( context, new DatePickerDialog.OnDateSetListener () {
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
                datePickerDialog = new DatePickerDialog ( context, new DatePickerDialog.OnDateSetListener () {
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
                datePickerDialog = new DatePickerDialog ( context, new DatePickerDialog.OnDateSetListener () {
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
                datePickerDialog = new DatePickerDialog ( context, new DatePickerDialog.OnDateSetListener () {
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
                datePickerDialog = new DatePickerDialog ( context, new DatePickerDialog.OnDateSetListener () {
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
                datePickerDialog = new DatePickerDialog ( context, new DatePickerDialog.OnDateSetListener () {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dobEditText6.setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.show ();
            }
        });

        //DatePickerNone6
       /* dobEditText6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance ();
                mYear = calendar.get ( Calendar.YEAR );
                mMonth = calendar.get ( Calendar.MONTH );
                mDay = calendar.get ( Calendar.DAY_OF_MONTH );
                //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                //show dialog
                datePickerDialog = new DatePickerDialog ( context, new DatePickerDialog.OnDateSetListener () {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        dobEditText.setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.show ();
            }
        });*/



        //DatePickerFuture1
        dobEditTextfuture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
           DatePickerDialog datePicker = new DatePickerDialog(context   , new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePicker = new DatePickerDialog(context   , new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePicker = new DatePickerDialog(context   , new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePicker = new DatePickerDialog(context   , new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePicker = new DatePickerDialog(context   , new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePicker = new DatePickerDialog(context   , new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePicker = new DatePickerDialog(context   , new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePicker = new DatePickerDialog(context   , new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePicker = new DatePickerDialog(context   , new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePicker = new DatePickerDialog(context   , new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePicker = new DatePickerDialog(context   , new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePicker = new DatePickerDialog(context   , new DatePickerDialog.OnDateSetListener() {
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

                /* if (questions.getQuestion_type() == 1 )
                 {
                     provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), openTextEtxt.getText().toString());

                }*/

                //Open Text not repeat

                if(questions.getQuestion_type()==1 && questions.isIs_required()){
                    if(openTextEtxt.getText().toString().equals("")){
                        Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();}
                    else{
                        provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), openTextEtxt.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() == 1 )
                {
                    provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), openTextEtxt.getText().toString());

                }
                //Open Text repeat count 1

                /* if(questions.getQuestion_type()==1 && questions.isIs_required() && questions.isIs_repeatable() && repeat_count==1){
                    if(openTextEtxt.getText().toString().equals("")){
                     Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();}else{
                        provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), openTextEtxt.getText().toString());
                    }

                 }
                else if (questions.getQuestion_type() == 1 )
                {
                    provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), openTextEtxt.getText().toString());

                }
                //Open Text repeat count 2
                if(questions.getQuestion_type()==1 && questions.isIs_required() && questions.isIs_repeatable() && repeat_count==2){
                    if(openTextEtxt.getText().toString().equals("") || openTextEtxt2.getText().toString().equals("")){
                        Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();}
                    else{
                        provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), openTextEtxt.getText().toString()+ " ,"+openTextEtxt2.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() == 1 )
                {
                    provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), openTextEtxt.getText().toString()+ " ,"+openTextEtxt2.getText().toString());

                }
                //Open Text repeat count 3
                if(questions.getQuestion_type()==1 && questions.isIs_required() && questions.isIs_repeatable() && repeat_count==3){
                    if(openTextEtxt.getText().toString().equals("") || openTextEtxt2.getText().toString().equals("")|| openTextEtxt3.getText().toString().equals("")){
                        Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();}else{
                        provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), openTextEtxt.getText().toString()+ " ,"+openTextEtxt2.getText().toString() + " ,"+openTextEtxt3.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() == 1 )
                {
                    provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), openTextEtxt.getText().toString()+ " ,"+openTextEtxt2.getText().toString()+ " ,"+openTextEtxt3.getText().toString());

                }
                //Open Text repeat count 4
                if(questions.getQuestion_type()==1 && questions.isIs_required() && questions.isIs_repeatable() && repeat_count==4){
                    if(openTextEtxt.getText().toString().equals("") || openTextEtxt2.getText().toString().equals("")|| openTextEtxt3.getText().toString().equals("") || openTextEtxt4.getText().toString().equals("")){
                        Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();}else{
                        provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), openTextEtxt.getText().toString()+ " ,"+openTextEtxt2.getText().toString() + " ,"+openTextEtxt3.getText().toString()+ " ,"+openTextEtxt4.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() == 1 )
                {
                    provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), openTextEtxt.getText().toString()+ " ,"+openTextEtxt2.getText().toString()+ " ,"+openTextEtxt3.getText().toString()+ " ,"+openTextEtxt4.getText().toString());

                }

                //Open Text repeat count 5
                if(questions.getQuestion_type()==1 && questions.isIs_required() && questions.isIs_repeatable() && repeat_count==5){
                    if(openTextEtxt.getText().toString().equals("") || openTextEtxt2.getText().toString().equals("")|| openTextEtxt2.getText().toString().equals("")|| openTextEtxt3.getText().toString().equals("")|| openTextEtxt4.getText().toString().equals("")|| openTextEtxt5.getText().toString().equals("")){
                        Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();}else{
                        provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), openTextEtxt.getText().toString()+ " ,"+openTextEtxt2.getText().toString() + " ,"+openTextEtxt3.getText().toString()+ " ,"+openTextEtxt4.getText().toString()+ " ,"+openTextEtxt5.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() == 1 )
                {
                    provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), openTextEtxt.getText().toString()+ " ,"+openTextEtxt2.getText().toString()+ " ,"+openTextEtxt3.getText().toString()+ " ,"+openTextEtxt4.getText().toString()+ " ,"+openTextEtxt5.getText().toString());

                }

                //Open Text repeat count 6
                if(questions.getQuestion_type()==1 && questions.isIs_required() && questions.isIs_repeatable() && repeat_count==5 && questions.isIs_repeatable() && repeat_count==6){
                    if(openTextEtxt.getText().toString().equals("") || openTextEtxt2.getText().toString().equals("")|| openTextEtxt2.getText().toString().equals("")|| openTextEtxt3.getText().toString().equals("")|| openTextEtxt4.getText().toString().equals("")|| openTextEtxt5.getText().toString().equals("")|| openTextEtxt6.getText().toString().equals("")){
                        Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();}else{
                        provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), openTextEtxt.getText().toString()+ " ,"+openTextEtxt2.getText().toString() + " ,"+openTextEtxt3.getText().toString()+ " ,"+openTextEtxt4.getText().toString()+ " ,"+openTextEtxt5.getText().toString()+ " ,"+openTextEtxt6.getText().toString());
                    }

                }
                else if (questions.getQuestion_type() == 1 )
                {
                    provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), openTextEtxt.getText().toString()+ " ,"+openTextEtxt2.getText().toString()+ " ,"+openTextEtxt3.getText().toString()+ " ,"+openTextEtxt4.getText().toString()+ " ,"+openTextEtxt5.getText().toString()+ " ,"+openTextEtxt6.getText().toString());

                }*/

                /*else if (questions.getQuestion_type() == 4){

                        provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), numericEditText.getText().toString());
                }*/

                else if (questions.getQuestion_type()==4 && questions.isIs_required()){
                     if (numericEditText.getText().toString().equals("")){
                          Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
                         // }else{
                     }else{provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), numericEditText.getText().toString());}
                }

                 else if (questions.getQuestion_type() == 4){

                     provideAnswers(sessionID, questions.getId(), String.valueOf(answers.getId()), numericEditText.getText().toString());
                 }

              /* else if (questions.getQuestion_type() ==5){
                     provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditText.getText().toString());

                 }

                   if (dobEditText.getText().toString().equals("")){
                        Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();

                    }*/

                //none
                //datepicker none & not repeatable
                else if (questions.getQuestion_type()==5 && questions.isIs_required() && questions.getDate_validation().equals("none") && !questions.isIs_repeatable()){
                    if(dobEditText.getText().toString().equals("")) {
                        Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
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
                             Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
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
                         Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
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
                         Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
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
                         Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
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
                         Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
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
                         Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
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
                         Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
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
                         Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
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
                         Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
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
                         Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
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
                         Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
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
                         Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
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
                         Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
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
                         Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
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
                         Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
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
                         Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
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
                         Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
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
                         Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(context, "Answer the question", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return root;
    }

    /*public boolean checkNulls(){
        boolean valid = true;


        if(TextUtils.isEmpty(openTextEtxt.getText().toString()))
        {
            Snackbar.make(root.findViewById(R.id.frag_update_user), "Please enter your answer", Snackbar.LENGTH_LONG).show();
            valid = false;
            return valid;
        }



        return valid;
    }
*/

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

        String auth_token = loggedInUser.getAuth_token();


        AndroidNetworking.post(Constants.ENDPOINT+Constants.PROVIDE_ANSWER)
                .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setContentType("application.json")
                .addJSONObjectBody(jsonObject) // posting json
                .build()
                .getAsJSONObject(new JSONObjectRequestListener(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, response.toString());

                        try {

                            String  message = response.has("Message") ? response.getString("Message") : "";

                            if (response.has("link")){

                                String link = response.has("link") ? response.getString("link") : "";
                                int sessionId = response.has("session_id") ? response.getInt("session_id") : 0;


                                Bundle bundle = new Bundle();
                                bundle.putString("questionLink",link);
                                bundle.putInt("sessionID",sessionId);
                                Navigation.findNavController(root).navigate(R.id.nav_questions, bundle);


                            }
                            else if (message.contains("Questionnaire complete")){

                                NavHostFragment.findNavController(QuestionsFragment.this).navigate(R.id.nav_complete_survey);
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, error.getErrorBody());

                        Snackbar.make(root.findViewById(R.id.frag_questions), "Error: "+error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });

    }

    private void loadQuestion() {

        String auth_token = loggedInUser.getAuth_token();
        //int session2 = sessionID;
        AndroidNetworking.get(questionLink+ "/"+sessionID)
                .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.e(TAG, response.toString());


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

                                           /* if (questions.getQuestion_type() == 1 && !is_repeatable) {
                                                openTextTil.setVisibility(View.VISIBLE);
                                            }*/
                                           /* if (questions.getQuestion_type() == 1 && is_repeatable && repeat_count==1) {
                                                openTextTil.setVisibility(View.VISIBLE);
                                            }*/

                                          if (questions.getQuestion_type() == 1  ){
                                                openTextTil.setVisibility(View.VISIBLE);
                                            }

                                            //repeat count =2
                                           /* else if (questions.getQuestion_type()==1 && is_repeatable && repeat_count==2){
                                                openTextTil.setVisibility(View.VISIBLE);
                                                openTextTil2.setVisibility(View.VISIBLE);
                                            }
                                            //repeat count =3
                                            else if (questions.getQuestion_type()==1 && is_repeatable && repeat_count==3){
                                                openTextTil.setVisibility(View.VISIBLE);
                                                openTextTil2.setVisibility(View.VISIBLE);
                                                openTextTil3.setVisibility(View.VISIBLE);
                                            }


                                            //repeat count =4
                                            else if (questions.getQuestion_type()==1 && is_repeatable && repeat_count==4){
                                                openTextTil.setVisibility(View.VISIBLE);
                                                openTextTil2.setVisibility(View.VISIBLE);
                                                openTextTil3.setVisibility(View.VISIBLE);
                                                openTextTil4.setVisibility(View.VISIBLE);
                                            }


                                            //repeat count =5
                                            else if (questions.getQuestion_type()==1 && is_repeatable && repeat_count==5){
                                                openTextTil.setVisibility(View.VISIBLE);
                                                openTextTil2.setVisibility(View.VISIBLE);
                                                openTextTil3.setVisibility(View.VISIBLE);
                                                openTextTil4.setVisibility(View.VISIBLE);
                                                openTextTil5.setVisibility(View.VISIBLE);
                                            }


                                            //repeat count =6
                                            else if (questions.getQuestion_type()==1 && is_repeatable && repeat_count==6){
                                                openTextTil.setVisibility(View.VISIBLE);
                                                openTextTil2.setVisibility(View.VISIBLE);
                                                openTextTil3.setVisibility(View.VISIBLE);
                                                openTextTil4.setVisibility(View.VISIBLE);
                                                openTextTil5.setVisibility(View.VISIBLE);
                                                openTextTil6.setVisibility(View.VISIBLE);
                                            }*/



                                            else if (questions.getQuestion_type() == 2) {
                                                singleChoiceRadioGroup.setVisibility(View.VISIBLE);

                                                RadioButton rbn = new RadioButton(context);
                                                rbn.setId(View.generateViewId());
                                                rbn.setText(answers.getOption());
                                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                                                rbn.setLayoutParams(params);
                                                singleChoiceRadioGroup.addView(rbn);


                                            } else if (questions.getQuestion_type() == 3) {

                                                openTextTil.setVisibility(View.GONE);
                                                singleChoiceRadioGroup.setVisibility(View.GONE);
                                                multipleChoiceAns.setVisibility(View.VISIBLE);

                                                checkBox = new CheckBox(context);
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
                                                Toast.makeText(context, "No answers found for this question", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    }

                                }

                                surveyQuestion.setText(questions.getQuestion());

                            }
                            else if (message.contains("Questionnaire complete")){

                                NavHostFragment.findNavController(QuestionsFragment.this).navigate(R.id.nav_complete_survey);
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                            else {

                                no_active_survey_lyt.setVisibility(View.VISIBLE);
                                Snackbar.make(root.findViewById(R.id.frag_questions), errors, Snackbar.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {

                        Log.e(TAG, error.getErrorBody());

                        if (error.getErrorDetail().equals("connectionError")){

                            error_lyt.setVisibility(View.VISIBLE);

                        }

                        Snackbar.make(root.findViewById(R.id.frag_questions), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmer_my_container.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        shimmer_my_container.stopShimmerAnimation();
        super.onPause();
    }

}