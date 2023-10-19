package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuestionsOffline extends AppCompatActivity {

    private RequestQueue requestQueue;
    Button button;
    AllQuestionDatabase allQuestionDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_offline);
        allQuestionDatabase = AllQuestionDatabase.getInstance(this);

        requestQueue = Volley.newRequestQueue(this);
        button =    findViewById(R.id.get_all);

       // getAll();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Log.d("ALl", "ERRRRRRRRR");

                getAll();
            }
        });

    }

    public void getAll(){
        String url = "https://psurveyapitest.kenyahmis.org/api/questions/dep/all";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("ALl", "SUCCESS");
                Log.d("ALl", response.toString());

                for (int i=0; i<response.length(); i++ ){
                    try {
                        JSONObject jsonObject=response.getJSONObject(i);
                        // Parse the JSON data
                        int questionnaireId = jsonObject.getInt("id");
                        String questionnaireName = jsonObject.getString("name");
                        String questionnaireDescription = jsonObject.getString("description");
                        boolean questionnaireIsActive = jsonObject.getBoolean("is_active");
                        String questionnaireCreatedAt = jsonObject.getString("created_at");
                        int questionnaireNumberOfQuestions = jsonObject.getInt("number_of_questions");
                        String questionnaireActiveTill = jsonObject.getString("active_till");
                        String questionnaireTargetApp = jsonObject.getString("target_app");
                        //Log.d("ALl", questionnaireName);

                        // Create and insert the QuestionnaireEntity
                        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
                        questionnaireEntity.setId(questionnaireId);
                        questionnaireEntity.setName(questionnaireName);
                        questionnaireEntity.setDescription(questionnaireDescription);
                        questionnaireEntity.setActive(questionnaireIsActive);
                        questionnaireEntity.setCreatedAt(questionnaireCreatedAt);
                        questionnaireEntity.setNumberOfQuestions(questionnaireNumberOfQuestions);
                        questionnaireEntity.setActiveTill(questionnaireActiveTill);
                        questionnaireEntity.setTargetApp(questionnaireTargetApp);
                        questionnaireEntity.setResponsesTableName(null); // You may set this as needed
                        questionnaireEntity.setIsPublished(null); // You may set this as needed
                        questionnaireEntity.setCreatedBy(14); // You may set this as needed

                        // Insert the QuestionnaireEntity into the Room database
                        long questionnaireIdInserted = allQuestionDatabase.questionnaireDao().insert(questionnaireEntity);


                        QuestionnaireEntity retrievedQuestionnaire = allQuestionDatabase.questionnaireDao().getQuestionnaireById((int) questionnaireIdInserted);

                        if (retrievedQuestionnaire != null) {
                            // You have the retrieved questionnaire entity
                            Log.d("ALl", "data in questionnaire");
                            Log.d("ALl", retrievedQuestionnaire.getName());
                            Log.d("ALl", retrievedQuestionnaire.getDescription());


                        } else {
                            // No matching questionnaire found
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

               /* try {






                    // Parse and insert the questions and answers
                    JSONArray questionsArray = jsonResponse.getJSONArray("questions");
                    for (int i = 0; i < questionsArray.length(); i++) {
                        JSONObject questionObject = questionsArray.getJSONObject(i);

                        int questionId = questionObject.getInt("id");
                        String questionText = questionObject.getString("question");
                        int questionType = questionObject.getInt("question_type");
                        int questionOrder = questionObject.getInt("question_order");
                        boolean isRequired = questionObject.getBoolean("is_required");

                        // Create and insert the QuestionEntity
                        QuestionEntity questionEntity = new QuestionEntity();
                        questionEntity.setId(questionId);
                        questionEntity.setQuestionnaireId(questionnaireIdInserted);
                        questionEntity.setQuestion(questionText);
                        questionEntity.setQuestionType(questionType);
                        questionEntity.setQuestionOrder(questionOrder);
                        questionEntity.setIsRequired(isRequired);
                        questionEntity.setDateValidation(null); // You may set this as needed
                        questionEntity.setIsRepeatable(false); // You may set this as needed
                        questionEntity.setResponseColName(null); // You may set this as needed
                        questionEntity.setCreatedAt(questionObject.getString("created_at"));
                        questionEntity.setCreatedBy(questionObject.getInt("created_by"));

                        // Insert the QuestionEntity into the Room database
                        long questionIdInserted = appDatabase.questionDao().insert(questionEntity);

                        // Parse and insert answers
                        JSONArray answersArray = questionObject.getJSONArray("answers");
                        for (int j = 0; j < answersArray.length(); j++) {
                            JSONObject answerObject = answersArray.getJSONObject(j);

                            int answerId = answerObject.getInt("id");
                            String answerOption = answerObject.getString("option");

                            // Create and insert the AnswerEntity
                            AnswerEntity answerEntity = new AnswerEntity();
                            answerEntity.setId(answerId);
                            answerEntity.setQuestionId(questionIdInserted);
                            answerEntity.setOption(answerOption);
                            answerEntity.setCreatedAt(answerObject.getString("created_at"));
                            answerEntity.setCreatedBy(answerObject.getInt("created_by"));

                            // Insert the AnswerEntity into the Room database
                            appDatabase.answerDao().insert(answerEntity);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/





        }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ALl", error.getMessage());

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}