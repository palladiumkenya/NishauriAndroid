package com.mhealthkenya.psurvey.models;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mhealthkenya.psurvey.activities.AllQuestionDatabase;
import com.mhealthkenya.psurvey.interfaces.AnswerDao;
import com.mhealthkenya.psurvey.interfaces.QuestionDao;
import com.mhealthkenya.psurvey.interfaces.QuestionnaireDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyAsyncTask extends AsyncTask<Void, Void, Void> {

    private final Context context;
    private final String url;
    private final QuestionnaireDao questionnaireDao;
    private final QuestionDao questionDao;
    private final AnswerDao answerDao;

    QuestionnaireEntity questionnaireEntity2;
    QuestionEntity questionEntity;
    AnswerEntity answerEntity;


    JSONObject jsonObject;
    Handler mHandler;

    private RequestQueue requestQueue;
    Button button;
    AllQuestionDatabase allQuestionDatabase;
    long questionnaireIdInserted;
    long questionnaireIdInserted1;
    int questionIdInserted;
    int questionnaireId;

    public MyAsyncTask(Context context, String url, QuestionnaireDao questionnaireDao, QuestionDao questionDao, AnswerDao answerDao) {
        this.context = context;
        this.url = url;
        this.questionnaireDao = questionnaireDao;
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    @Override
    protected Void doInBackground(Void... params) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject questionnaireObject = response.getJSONObject(i);

                            // Parse the JSON data
                            questionnaireId = jsonObject.getInt("id");
                            String questionnaireName = jsonObject.getString("name");
                            String questionnaireDescription = jsonObject.getString("description");
                            boolean questionnaireIsActive = jsonObject.getBoolean("is_active");
                            String questionnaireCreatedAt = jsonObject.getString("created_at");
                            int questionnaireNumberOfQuestions = jsonObject.getInt("number_of_questions");
                            String questionnaireActiveTill = jsonObject.getString("active_till");
                            String questionnaireTargetApp = jsonObject.getString("target_app");
                            //Log.d("ALl", questionnaireName);

                            // Create and insert the QuestionnaireEntity
                            questionnaireEntity2 = new QuestionnaireEntity();
                            questionnaireEntity2.setId(questionnaireId);
                            questionnaireEntity2.setName(questionnaireName);
                            questionnaireEntity2.setDescription(questionnaireDescription);
                            questionnaireEntity2.setActive(questionnaireIsActive);
                            questionnaireEntity2.setCreatedAt(questionnaireCreatedAt);
                            questionnaireEntity2.setNumberOfQuestions(questionnaireNumberOfQuestions);
                            questionnaireEntity2.setActiveTill(questionnaireActiveTill);
                            questionnaireEntity2.setTargetApp(questionnaireTargetApp);
                            questionnaireEntity2.setResponsesTableName(null); // You may set this as needed
                            questionnaireEntity2.setIsPublished(null); // You may set this as needed
                            questionnaireEntity2.setCreatedBy(14); // You may set this as needed

                            allQuestionDatabase.questionnaireDao().insert(questionnaireEntity2);

                            //questions
                            JSONArray jsonArray = jsonObject.getJSONArray("questions");

                            for (int j = 0; j < jsonArray.length(); i++) {
                                JSONObject questionObject = jsonArray.getJSONObject(j);

                                int questionId = questionObject.getInt("id");
                                String questionText = questionObject.getString("question");
                                int questionType = questionObject.getInt("question_type");
                                int questionOrder = questionObject.getInt("question_order");
                                boolean isRequired = questionObject.getBoolean("is_required");

                                // Create and insert the QuestionEntity
                                questionEntity = new QuestionEntity();
                                questionEntity.setId(questionId);
                                questionEntity.setQuestionnaireId(questionnaireId);
                                questionEntity.setQuestion(questionText);
                                questionEntity.setQuestionType(questionType);
                                questionEntity.setQuestionOrder(questionOrder);
                                questionEntity.setRequired(isRequired);
                            questionEntity.setDateValidation(null); // You may set this as needed
                            questionEntity.setRepeatable(false); // You may set this as needed
                            questionEntity.setResponseColName(null); // You may set this as needed
                            questionEntity.setCreatedAt(questionObject.getString("created_at"));
                            questionEntity.setCreatedBy(questionObject.getInt("created_by"));

                                // Insert the QuestionEntity into the Room database
                                allQuestionDatabase.questionDao().insert(questionEntity);

                                // Parse and insert answers
                                JSONArray answersArray = questionObject.getJSONArray("answers");
                                for (int k = 0; k< answersArray.length(); k++) {
                                    JSONObject answerObject = answersArray.getJSONObject(j);

                                    int answerId = answerObject.getInt("id");
                                    String answerOption = answerObject.getString("option");

                                    // Create and insert the AnswerEntity
                                    answerEntity = new AnswerEntity();
                                    answerEntity.setId(answerId);
                                    answerEntity.setQuestionId(questionIdInserted);
                                    answerEntity.setOption(answerOption);
                                    answerEntity.setCreatedAt(answerObject.getString("created_at"));
                                    answerEntity.setCreatedBy(answerObject.getInt("created_by"));

                                    allQuestionDatabase.answerDao().insert(answerEntity);

                                    // Insert the AnswerEntity into the Room database

                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                    // Handle error
                });

        requestQueue.add(jsonArrayRequest);
        return null;
    }
}

