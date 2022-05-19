package com.mhealthkenya.psurvey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.fxn.stash.Stash;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.mhealthkenya.psurvey.adapters.questionsAdapter;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.models.Answer;
import com.mhealthkenya.psurvey.models.Question;
import com.mhealthkenya.psurvey.models.Questions;
import com.mhealthkenya.psurvey.models.auth;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SingleQuestions extends AppCompatActivity {

    private AllQViewModel  allQViewModel;
    private List<Questions> getqs;
    private AQ_Repository repository1;


    private Context context;

    private String openText = "";
    private String questionLink;
    private int sessionID;

    //private int quetionnaireID;

    private CheckBox checkBox;

    private auth loggedInUser;
    private Question questions;
    private Answer answers;
    private ArrayList<Answer> answerList = new ArrayList<>();
    private List<Integer> multiAnswerList = new ArrayList<>();



    @BindView(R.id.tv_survey_question)
    MaterialTextView surveyQuestion;

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

    //@BindView(R.id.recyclerView)
    //RecyclerView recyclerView;

    @BindView(R.id.no_active_survey_lyt)
    LinearLayout no_active_survey_lyt;

    @BindView(R.id.error_lyt)
    LinearLayout error_lyt;

    @BindView(R.id.btn_next)
    Button btn_next;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_questions);

        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);

        repository1 = new AQ_Repository(getApplication());
        allQViewModel=new ViewModelProvider(this).get(AllQViewModel.class);

        allQViewModel.getGet1().observe((LifecycleOwner) getApplication(), new Observer<Questions>() {
            @Override
            public void onChanged(Questions questions) {
                surveyQuestion.setText(questions.getQuestionName());

            }
        });



    }
}