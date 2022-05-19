package com.mhealthkenya.psurvey.fragments.survey;

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
import com.mhealthkenya.psurvey.adapters.questionsAdapter;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.interfaces.APIClient;
import com.mhealthkenya.psurvey.interfaces.APIInterface;
import com.mhealthkenya.psurvey.models.Answers;
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

    private AllQViewModel  allQViewModel;
    //private List<AllQuestion> getqs;
    private List<Questions> getqs;
    //private  List<QuestionsList> getqs;
    private questionsAdapter questionsAdapter1;
   // private RecyclerView recyclerView;
    private AQ_Repository repository1;




    private Unbinder unbinder;
    private View root;
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

   // Repository repository;
    //QuestionViewModel questionViewModel;

    List<Question> quiz;
    RecyclerView recyclerView1;




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

       // repository =new Repository(getInstance());
        //quiz = new ArrayList<>();
        //questionViewModel =new QuestionViewModel(getActivity().getApplication());


        repository1=new AQ_Repository(getActivity().getApplication());
        //getqs=new ArrayList<>();
        //recyclerView=findViewById(R.id.recyclerView);

         recyclerView1 =root.findViewById(R.id.recyclerView);
         getqs= new ArrayList<>();
        questionsAdapter1= new questionsAdapter(context, getqs);
        recyclerView1.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setAdapter(questionsAdapter1);
        questionsAdapter1.notifyDataSetChanged();
        allQViewModel=new ViewModelProvider(this).get(AllQViewModel.class);

        //makeRequest();

        try {
            //loadQuestion();
           // Toast.makeText(getContext(), " dahhta", Toast.LENGTH_SHORT).show();
            //fetchAllQuestions();
            allq();

        }catch (Exception e){
            e.printStackTrace();
        }


        /*allQViewModel.getGetAllData().observe((LifecycleOwner) getContext(), new Observer<List<Questions>>() {
            @Override
            public void onChanged(List<Questions> allQuestions) {

                recyclerView1.setAdapter(questionsAdapter1);

                questionsAdapter1.getAllDatass((ArrayList<Questions>) allQuestions);
               // Toast.makeText(context, "offlineeeeeee111", Toast.LENGTH_LONG).show();
                Log.d("main", "onChanged: "+allQuestions);


            }
        });*/


        //get 1 question
        allQViewModel.getGet1().observe((LifecycleOwner) getContext(), new Observer<Questions>() {
            @Override
            public void onChanged(Questions questions) {
                surveyQuestion.setText(questions.getQuestionName());
                openTextTil.setVisibility(View.VISIBLE);

               // Toast.makeText(context,"get1"+questions.getQuestionName(), Toast.LENGTH_LONG).show();
                if (questions.getQuestionType()=="1"){
                    surveyQuestion.setText(questions.getQuestionName());
                    openTextTil.setVisibility(View.VISIBLE);

                }else if (questions.getQuestionType()=="2"){

                   /* allQViewModel.getGetOpt().observe((LifecycleOwner) getContext(), new Observer<Answers>() {
                        @Override
                        public void onChanged(Answers answers) {

                            if (questions.getQuestionType()=="2"){

                                singleChoiceRadioGroup.setVisibility(View.VISIBLE);
                                RadioButton rbn = new RadioButton(context);
                                rbn.setId(View.generateViewId());
                                rbn.setText(answers.getAnswerName());
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                                rbn.setLayoutParams(params);
                                singleChoiceRadioGroup.addView(rbn);

                            }


                        }
                    });*/
                }

            }
        });


        //assert getArguments() != null;
        //questionLink = getArguments().getString("questionLink");

        //assert getArguments() != null;
        //sessionID=  getArguments().getInt("sessionID");


       // assert getArguments()!=null;
        //quetionnaireID= getArguments().getInt("questionnaire_id");

        //assert getArguments()!=null;
       // quetionnaireID= getArguments().getInt("questionnaire");


        Handler handler  = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                try {
                    //loadQuestion();
                   // fetchAllQuestions();

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


        //loadQuestion();
        //fetchAllQuestions();



        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (questions.getQuestion_type() == 1){

                    Handler handler2 = new Handler(Looper.getMainLooper());
                    handler2.post(new Runnable() {
                        @Override
                        public void run() {
                            try {

                               // provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), openTextEtxt.getText().toString());

                            }catch(Exception e){
                                e.printStackTrace();
                            }
                            //provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), openTextEtxt.getText().toString());

                        }
                    });


                    //provideAnswers(sessionID,questions.getId(),String.valueOf(answers.getId()), openTextEtxt.getText().toString());


                }
                else if (questions.getQuestion_type() == 2){

                    int radioButtonID = singleChoiceRadioGroup.getCheckedRadioButtonId();


                    if (radioButtonID == -1){

                        Toast.makeText(context, "Please ensure you pick an answer", Toast.LENGTH_SHORT).show();

                    }



                    else {
                        View radioButton = singleChoiceRadioGroup.findViewById(radioButtonID);
                        int idx = singleChoiceRadioGroup.indexOfChild(radioButton);


                        Handler handler1 =new Handler(Looper.getMainLooper());
                        handler1.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                   // provideAnswers(sessionID,questions.getId(),String.valueOf(answerList.get(idx).getId()), openText);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });


                        //provideAnswers(sessionID,questions.getId(),String.valueOf(answerList.get(idx).getId()), openText);
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


                    Handler handler1 =new Handler(Looper.getMainLooper());
                    handler1.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                               // provideAnswers(sessionID,questions.getId(),String.valueOf(multiAnswerList), openText);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });

                    //provideAnswers(sessionID,questions.getId(),String.valueOf(multiAnswerList), openText);

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

        //add timeout
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(600, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                . writeTimeout(600, TimeUnit.SECONDS)
                .build();

        AndroidNetworking.post(Constants.ENDPOINT+Constants.PROVIDE_ANSWER)
                .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setContentType("application.json")
                .addJSONObjectBody(jsonObject) // posting json
                .setOkHttpClient(okHttpClient)
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

        //add timeout
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(600, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                . writeTimeout(600, TimeUnit.SECONDS)
                .build();


        AndroidNetworking.get(questionLink)
                .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setPriority(Priority.LOW)
                .setOkHttpClient(okHttpClient)
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
                                //repository.insert((List<Question>) questions);
                                //repository.insertOne(questions);

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

    private void fetchAllQuestions(){
        String auth_token = loggedInUser.getAuth_token();

        //OkHttpClient httpClient = new OkHttpClient();
        OkHttpClient.Builder httpClient =new OkHttpClient.Builder();
        httpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder();
                requestBuilder.header("Content-Type", "application.json");
                requestBuilder.header("Authorization","Token "+ auth_token);
                return chain.proceed(requestBuilder.build());
            }
        });
        /*httpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder();
                requestBuilder.header("Content-Type", "application/json");
                requestBuilder.header("Authorization","Token "+ auth_token);
                return chain.proceed(requestBuilder.build());
            }
        });*/
       // Retrofit retrofit = new Retrofit.Builder().baseUrl("https://psurvey-api.mhealthkenya.co.ke/").addConverterFactory(GsonConverterFactory.create()).client(httpClient).build();
       // Retrofit retrofit = new Retrofit.Builder().baseUrl("https://psurvey-api.mhealthkenya.co.ke/").addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
       // https://psurvey-api.mhealthkenya.co.ke/api/questions_all/1

        //CATapi api=retrofit.create(CATapi.class);
        //Call<List<Model>> call=api.getImgs(10);
        //APIInterface apiInterface = retrofit.create(APIInterface.class);
        APIInterface apiInterface = APIClient.getApiClient().create(APIInterface.class);
        assert getArguments()!=null;
        int quetionnaireID= getArguments().getInt("questionnaire_id");

        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("quetionnaireID", quetionnaireID);

        //Call<ArrayList<Questions>> call = apiInterface.getALlques();
        Call<QuestionsList> call1 =apiInterface.getALlques();

        call1.enqueue(new Callback<QuestionsList>() {
            @Override
            public void onResponse(Call<QuestionsList> call, Response<QuestionsList> response) {
                if (response.isSuccessful() && response.body() !=null){
                    //repository1=response.body().getQuestionsList()
                    getqs = response.body().getQuestionsList();

                    questionsAdapter1 = new questionsAdapter(  context, getqs);
                    recyclerView1.setAdapter(questionsAdapter1);
                    recyclerView1.setHasFixedSize(true);
                    questionsAdapter1.notifyDataSetChanged();
                }
                Log.d("data", response.message());
            }

            @Override
            public void onFailure(Call<QuestionsList> call, Throwable t) {

            }
        });


    }

    private void allq(){
        String auth_token = loggedInUser.getAuth_token();

        assert getArguments()!=null;
        int quetionnaireID= getArguments().getInt("questionnaire_id");


        AndroidNetworking.get("https://psurvey-api.mhealthkenya.co.ke/api/questions_all/"+quetionnaireID)
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
                        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                        // do anything with response
//                        Log.e(TAG, response.toString());

                        //questionnairesArrayList.clear();
                        getqs.clear();


                        if (recyclerView1!=null)
                            recyclerView1.setVisibility(View.VISIBLE);

                        /*if (shimmer_my_container!=null){
                            shimmer_my_container.stopShimmerAnimation();
                            shimmer_my_container.setVisibility(View.GONE);
                        }*/

                        try {

                            JSONArray myArray = response.getJSONArray("Questions");


                            if (myArray.length() > 0){


                                for (int i = 0; i < myArray.length(); i++) {

                                    JSONObject item = (JSONObject) myArray.get(i);



                                    int  Question_ID = item.has( "Question_ID") ? item.getInt( "Question_ID") : 0;
                                    String QuestionName = item.has("QuestionName") ? item.getString("QuestionName") : "";
                                    String QuestionOrder= item.has("QuestionOrder") ? item.getString("QuestionOrder") : "";
                                    String QuestionType = item.has("QuestionType") ? item.getString("QuestionType") : "";



                                    Questions allQuestion = new Questions(Question_ID, QuestionName,QuestionOrder, QuestionType);

                                    getqs.add(allQuestion);
                                    repository1.inserts(getqs);


                                    //questionsAdapter1 =new questionsAdapter(context, getqs);
                                    //recyclerView1.setAdapter(questionsAdapter1);
                                    //questionsAdapter1.notifyDataSetChanged();




                                }

                            }else {
                                //not data foundsa\zdx
                                //no_questionnaires_lyt.setVisibility(View.VISIBLE);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show();
                        // handle error
                        if (recyclerView1!=null)
                            recyclerView1.setVisibility(View.VISIBLE);

                        if (shimmer_my_container!=null){
                            shimmer_my_container.stopShimmerAnimation();
                            shimmer_my_container.setVisibility(View.GONE);
                        }

                        error_lyt.setVisibility(View.VISIBLE);

//                        Log.e(TAG, error.getErrorBody());

                        Snackbar.make(root.findViewById(R.id.frag_questions), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });
    }

}

