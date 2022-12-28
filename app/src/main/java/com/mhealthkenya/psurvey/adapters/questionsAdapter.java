package com.mhealthkenya.psurvey.adapters;

import static com.mhealthkenya.psurvey.depedancies.AppController.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fxn.stash.Stash;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.models.Answer;
import com.mhealthkenya.psurvey.models.Answers;
import com.mhealthkenya.psurvey.models.Question;
import com.mhealthkenya.psurvey.models.Questions;
import com.mhealthkenya.psurvey.models.QuestionsList;
import com.mhealthkenya.psurvey.models.auth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import kotlin.reflect.KVisibility;
import okhttp3.OkHttpClient;

public class questionsAdapter extends RecyclerView.Adapter<questionsAdapter.QuestionsViewHolder> {

    //private Answer answers;
    //private ArrayList<com.mhealthkenya.psurvey.models.Answer> answerList = new ArrayList<>();
    //private List<Integer> multiAnswerList = new ArrayList<>();


    private Answers answers2;
    private ArrayList<Answers> answersList2 = new ArrayList<>();
    private List<Integer> multiAnswerList2 = new ArrayList<>();

    List<Questions> all = new ArrayList<>();
    List<Question> question1 = new ArrayList<>();
    Question question2;

    Context context;



    public questionsAdapter(Context context, List<Question> question1) {

        this.context = context;
        // this.all = all;

        this.question1 =question1;
    }


    @NonNull
    @Override
    public QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_questions, parent, false);
        return new QuestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsViewHolder holder, int position) {
     //   Question obj = question1.ge;
          Question obj1 =question1.get(position);
        //if (obj.getQuestion_type()==1){
        //  holder.openTextTil.setVisibility(View.VISIBLE);
        //holder.surveyQuestion.setText(obj1);


        holder.surveyQuestion.setText(obj1.getQuestion());


        // }
       /* else if(obj.getQuestionType().equals("2")){

            auth loggedInUser;
            loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);
            String auth_token = loggedInUser.getAuth_token();

            int queryID = obj.getQuestion_ID();
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(600, TimeUnit.SECONDS)
                    .readTimeout(600, TimeUnit.SECONDS)
                    . writeTimeout(600, TimeUnit.SECONDS)
                    .build();*/

            /*AndroidNetworking.get("https://psurvey-api.mhealthkenya.co.ke/api/answers_options/"+queryID)
                    .addHeaders("Authorization","Token "+ auth_token)
                    .addHeaders("Content-Type", "application.json")

                    .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setPriority(Priority.LOW)
                .setOkHttpClient(okHttpClient)
                .build().getAsJSONObject(new JSONObjectRequestListener() {*/
           /* @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, response.toString());
                try{
                    JSONArray ans = response.getJSONArray("Answers");

                    if (ans.length() > 0){

                        for (int i = 0; i < ans.length(); i++) {
                            JSONObject item = (JSONObject) ans.get(i);

                            int Answer_ID = item.has("Answer_ID") ? item.getInt("Answer_ID") : 0;
                            String AnswerName = item.has("AnswerName") ? item.getString("AnswerName") : "";

                            answers2 = new Answers(Answer_ID,AnswerName);
                            answersList2.add(answers2);

                                holder.singleChoiceRadioGroup.setVisibility(View.VISIBLE);
                                RadioButton rbn = new RadioButton(context);
                                rbn.setId(View.generateViewId());
                                rbn.setText(answers2.getAnswerName());
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                                rbn.setLayoutParams(params);
                                holder.singleChoiceRadioGroup.addView(rbn);



            holder.surveyQuestion.setText(obj.getQuestionName());



            }

             }


                }catch (JSONException e){
                    e.printStackTrace();

                }

            }

            @Override
            public void onError(ANError anError) {

                Toast.makeText(context, "API err", Toast.LENGTH_LONG).show();




            }
        });*/


        // }

       /* else if(obj.getQuestionType().equals("3")){
           // Toast.makeText(context, "option 3", Toast.LENGTH_LONG).show();
            auth loggedInUser;
            loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);
            String auth_token = loggedInUser.getAuth_token();


            int queryID = obj.getQuestion_ID();
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(600, TimeUnit.SECONDS)
                    .readTimeout(600, TimeUnit.SECONDS)
                    . writeTimeout(600, TimeUnit.SECONDS)
                    .build();

            AndroidNetworking.get("https://psurvey-api.mhealthkenya.co.ke/api/answers_options/"+queryID)
                    .addHeaders("Authorization","Token "+ auth_token)
                    .addHeaders("Content-Type", "application.json")*/

                   /* .addHeaders("Accept", "gzip, deflate, br")
                    .addHeaders("Connection","keep-alive")
                    .setPriority(Priority.LOW)
                    .setOkHttpClient(okHttpClient)
                    .build().getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e(TAG, response.toString());
                    //Toast.makeText(context, "second API", Toast.LENGTH_SHORT).show();
                    try{
                        JSONArray ans = response.getJSONArray("Answers");

                        if (ans.length() > 0){

                            for (int i = 0; i < ans.length(); i++) {
                                JSONObject item = (JSONObject) ans.get(i);

                                int Answer_ID = item.has("Answer_ID") ? item.getInt("Answer_ID") : 0;
                                String AnswerName = item.has("AnswerName") ? item.getString("AnswerName") : "";

                                answers2 = new Answers(Answer_ID,AnswerName);
                                answersList2.add(answers2);

                                holder.openTextTil.setVisibility(View.GONE);
                                holder.singleChoiceRadioGroup.setVisibility(View.GONE);
                                holder.multipleChoiceAns.setVisibility(View.VISIBLE);
                                CheckBox checkBox = new CheckBox(context);
                                checkBox.setId(answers2.getAnswer_ID());
                                checkBox.setText(answers2.getAnswerName());
                                LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                                checkBox.setLayoutParams(params3);
                                holder.multipleChoiceAns.addView(checkBox);


                               holder.surveyQuestion.setText(obj.getQuestionName());

                            }

                        }


                    }catch (JSONException e){
                        e.printStackTrace();

                    }

                }

                @Override
                public void onError(ANError anError) {

                    Toast.makeText(context, "API err", Toast.LENGTH_LONG).show();
                }
            });*/

    }
    // }


    @Override
    public int getItemCount() {
        return question1.size();

       /* int a;

        if (all != null && !all.isEmpty()) {

            a = all.size();
        } else {

            a = 0;

        }

        return a;*/
    }

    public void getAllDatass(ArrayList<Questions> all) {
        this.all = all;
    }


    public class QuestionsViewHolder extends RecyclerView.ViewHolder {
        public MaterialTextView surveyQuestion;
        public TextInputLayout openTextTil;
        public TextInputEditText openTextEtxt;
        public RadioGroup singleChoiceRadioGroup;
        public LinearLayout multipleChoiceAns;
        public View lyt_parent;
        public CoordinatorLayout coordinatorLyt;
        public ShimmerFrameLayout shimmer_my_container;
        public LinearLayout no_active_survey_lyt;
        public LinearLayout error_lyt;


        public QuestionsViewHolder(@NonNull View itemView) {
            super(itemView);
            surveyQuestion = itemView.findViewById(R.id.tv_survey_question);
            openTextTil = itemView.findViewById(R.id.til_open_text);
            openTextEtxt = itemView.findViewById(R.id.etxt_open_text);
            singleChoiceRadioGroup = itemView.findViewById(R.id.radio_group);
            multipleChoiceAns = itemView.findViewById(R.id.multiselect_lyt);
            coordinatorLyt = itemView.findViewById(R.id.coordinator_lyt);
            shimmer_my_container = itemView.findViewById(R.id.shimmer_my_container);
            no_active_survey_lyt = itemView.findViewById(R.id.no_active_survey_lyt);
            error_lyt = itemView.findViewById(R.id.error_lyt);
        }
    }
}