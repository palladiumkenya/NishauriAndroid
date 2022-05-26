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

    private Unbinder unbinder;
    private View root;
    private Context context;

    private String openText = "";
    private String questionLink;
    private int sessionID;

    private CheckBox checkBox;

    private auth loggedInUser;
    private Question questions;
    private Answer answers;
    private ArrayList<Answer> answerList = new ArrayList<>();
    private List<Integer> multiAnswerList = new ArrayList<>();

    private int mYear, mMonth, mDay;



    @BindView(R.id.tv_survey_question)
    MaterialTextView surveyQuestion;

    @BindView(R.id.til_open_text)
    TextInputLayout openTextTil;

    @BindView(R.id.etxt_open_text)
    TextInputEditText openTextEtxt;

    @BindView(R.id.dateLayout)
    TextInputLayout dateTextTil;

    @BindView(R.id.dob)
    TextInputEditText dobEditText;

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

        //DatePicker
        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance ();
                mYear = calendar.get ( Calendar.YEAR );
                mMonth = calendar.get ( Calendar.MONTH );
                mDay = calendar.get ( Calendar.DAY_OF_MONTH );

                //show dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog ( context, new DatePickerDialog.OnDateSetListener () {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                       dobEditText.setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.show ();



            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (questions.getQuestion_type() == 1){


                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), openTextEtxt.getText().toString());


                }
                else if (questions.getQuestion_type() == 4){

                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), numericEditText.getText().toString());


                }else if (questions.getQuestion_type() ==5 ){

                    provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), dobEditText.getText().toString());


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
            jsonObject.put("session", sessionID );
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


        AndroidNetworking.get(questionLink)
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
                                int questionType = question.has("question_type") ? question.getInt("question_type") : 0;
                                String createdAt = question.has("created_at") ? question.getString("created_at") : "";
                                int questionnaire = question.has("questionnaire") ? question.getInt("questionnaire") : 0;
                                int createdBy = question.has("created_by") ? question.getInt("created_by") : 0;

                                questions = new Question(questionId,questionName,questionType,createdAt,questionnaire,createdBy);


                                JSONArray ans = response.getJSONArray("Ans");

                                if (ans.length() > 0){


                                    for (int i = 0; i < ans.length(); i++) {

                                        JSONObject item = (JSONObject) ans.get(i);


                                        int  ansID = item.has("id") ? item.getInt("id") : 0;
                                        String option = item.has("option") ? item.getString("option") : "";
                                        String created_at = item.has("created_at") ? item.getString("created_at") : "";
                                        int  questionID = item.has("question") ? item.getInt("question") : 0;
                                        int  created_by = item.has("created_by") ? item.getInt("created_by") : 0;


                                        answers = new Answer(ansID,option,created_at,questionID,created_by);
                                        answerList.add(answers);


                                        if (questions.getQuestion_type() == 1){
                                            openTextTil.setVisibility(View.VISIBLE);


                                        }
                                        else if (questions.getQuestion_type() == 2){
                                            singleChoiceRadioGroup.setVisibility(View.VISIBLE);

                                            RadioButton rbn = new RadioButton(context);
                                            rbn.setId(View.generateViewId());
                                            rbn.setText(answers.getOption());
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                                            rbn.setLayoutParams(params);
                                            singleChoiceRadioGroup.addView(rbn);


                                        }
                                        else if (questions.getQuestion_type() == 3){

                                            openTextTil.setVisibility(View.GONE);
                                            singleChoiceRadioGroup.setVisibility(View.GONE);
                                            multipleChoiceAns.setVisibility(View.VISIBLE);

                                            checkBox = new CheckBox(context);
                                            checkBox.setId(answers.getId());
                                            checkBox.setText(answers.getOption());
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                                            checkBox.setLayoutParams(params);
                                            multipleChoiceAns.addView(checkBox);

                                        }
                                        else if (questions.getQuestion_type()==4){
                                            numericText.setVisibility(View.VISIBLE);
                                            numericEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                                        }

                                        else if (questions.getQuestion_type()==5){
                                            dateTextTil.setVisibility(View.VISIBLE);


                                        }

                                        else {
                                            Toast.makeText(context, "No answers found for this question", Toast.LENGTH_SHORT).show();
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