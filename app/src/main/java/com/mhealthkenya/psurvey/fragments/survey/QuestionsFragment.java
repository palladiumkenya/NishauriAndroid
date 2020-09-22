package com.mhealthkenya.psurvey.fragments.survey;

import android.content.Context;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.models.ActiveSurveys;
import com.mhealthkenya.psurvey.models.Answer;
import com.mhealthkenya.psurvey.models.Question;
import com.mhealthkenya.psurvey.models.auth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mhealthkenya.psurvey.depedancies.AppController.TAG;


public class QuestionsFragment extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;

    private auth loggedInUser;
    private Question questions;
    private Answer answers;
    private List<Answer> answerList;

    @BindView(R.id.tv_survey_question)
    MaterialTextView surveyQuestion;

    @BindView(R.id.tv_answer_id)
    MaterialTextView answerID;

    @BindView(R.id.til_open_text)
    TextInputLayout openTextTil;

    @BindView(R.id.etxt_open_text)
    TextInputEditText openTextEtxt;

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
        questions = (Question) getArguments().getSerializable("surveyQuestion");
        answers = (Answer) getArguments().getSerializable("surveyAns");
        int sessionID=  getArguments().getInt("sessionID");

        surveyQuestion.setText(questions.getQuestion());

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

            CheckBox checkBox = new CheckBox(context);
            checkBox.setId(View.generateViewId());
            checkBox.setText(answers.getOption());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
            checkBox.setLayoutParams(params);

        }
        else {
            Toast.makeText(context, "No answers found for this question", Toast.LENGTH_SHORT).show();
        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confirmConsent(sessionID,questions.getId(),answers.getId());



            }
        });

        return root;
    }

    private void confirmConsent(int sessionID,int questionNumber,int answer) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("session", sessionID );
            jsonObject.put("question", questionNumber);
            jsonObject.put("answer", answer);
            jsonObject.put("open_text", openTextEtxt.getText().toString());


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

                            boolean  success = response.has("success") && response.getBoolean("success");
                            String  errors = response.has("error") ? response.getString("error") : "" ;
                            String  message = response.has("Message") ? response.getString("Message") : "" ;


                            if (success){


                                JSONObject question = response.getJSONObject("Question");

                                int questionId = question.has("id") ? question.getInt("id"): 0;
                                String questionName = question.has("question") ? question.getString("question") : "";
                                int questionType = question.has("question_type") ? question.getInt("question_type") : 0;
                                String createdAt = question.has("created_at") ? question.getString("created_at") : "";
                                int questionnaire = question.has("questionnaire") ? question.getInt("questionnaire") : 0;
                                int createdBy = question.has("created_by") ? question.getInt("created_by") : 0;

                                Question newQuestion = new Question(questionId,questionName,questionType,createdAt,questionnaire,createdBy);

                                Bundle bundle = new Bundle();
                                bundle.putSerializable("surveyQuestion",newQuestion);

                                JSONArray ans = response.getJSONArray("Ans");

                                if (ans.length() > 0){


                                    for (int i = 0; i < ans.length(); i++) {

                                        JSONObject item = (JSONObject) ans.get(i);


                                        int  ansID = item.has("id") ? item.getInt("id") : 0;
                                        String option = item.has("option") ? item.getString("option") : "";
                                        String created_at = item.has("created_at") ? item.getString("created_at") : "";
                                        int  questionid = item.has("question") ? item.getInt("question") : 0;
                                        int  created_by = item.has("created_by") ? item.getInt("created_by") : 0;

                                        Answer newAnswer = new Answer(ansID,option,created_at,questionid,created_by);


                                        bundle.putSerializable("surveyAns",newAnswer);

                                    }

                                }

                                int sessionId = response.has("session_id") ? response.getInt("session_id") : 0;



                                bundle.putInt("sessionID",sessionId);
                                Navigation.findNavController(root).navigate(R.id.nav_questions, bundle);

                            }
                            else if (success && response.has("Questionnaire complete, Thank You!")){

                                NavHostFragment.findNavController(QuestionsFragment.this).navigate(R.id.nav_complete_survey);
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                            else{

                                Snackbar.make(root.findViewById(R.id.frag_questions), errors, Snackbar.LENGTH_LONG).show();

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