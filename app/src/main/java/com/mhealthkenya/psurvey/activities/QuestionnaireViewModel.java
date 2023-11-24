package com.mhealthkenya.psurvey.activities;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mhealthkenya.psurvey.adapters.QuestionnairesAdapterOffline;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.QuestionEntity;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

public class QuestionnaireViewModel extends ViewModel {

    private MutableLiveData<List<QuestionnaireEntity>> questionnairesLiveData = new MutableLiveData<>();
    private AllQuestionDatabase allQuestionDatabase;
    private RequestQueue requestQueue;
    private JSONObject jsonObject;
    private QuestionnaireEntity questionnaireEntity2;
    private QuestionEntity questionEntity;
    private AnswerEntity answerEntity;
    int questionnaireId;

    //ADDED
    public ArrayList<QuestionnaireEntity> questionnaireEntities;
    public QuestionnairesAdapterOffline questionnairesAdapterOffline;



    public QuestionnaireViewModel(@NonNull Application application) {
        super((Closeable) application);
        allQuestionDatabase = AllQuestionDatabase.getInstance(application);
        requestQueue = Volley.newRequestQueue(application);
    }

    public LiveData<List<QuestionnaireEntity>> getQuestionnairesLiveData() {
        return questionnairesLiveData;
    }

    public void fetchQuestionnairesFromServer() {
        String url = "https://psurveyapitest.kenyahmis.org/api/questions/dep/all";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<QuestionnaireEntity> questionnaires = new ArrayList<>();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                jsonObject = response.getJSONObject(i);

                                // Parse the JSON data
                                questionnaireId = jsonObject.getInt("id");
                                String questionnaireName = jsonObject.getString("name");
                                String questionnaireDescription = jsonObject.getString("description");
                                boolean questionnaireIsActive = jsonObject.getBoolean("is_active");
                                String questionnaireCreatedAt = jsonObject.getString("created_at");
                                int questionnaireNumberOfQuestions = jsonObject.getInt("number_of_questions");
                                String questionnaireActiveTill = jsonObject.getString("active_till");
                                String questionnaireTargetApp = jsonObject.getString("target_app");

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
                                questionnaireEntity2.setResponsesTableName(null);
                                questionnaireEntity2.setIsPublished(null);
                                questionnaireEntity2.setCreatedBy(14);

                                allQuestionDatabase.questionnaireDao().insert(questionnaireEntity2);

                                // ... (similar logic for questions and answers)

                                // Add the questionnaire to the list
                                questionnaires.add(questionnaireEntity2);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Update the LiveData with the fetched questionnaires
                        questionnairesLiveData.postValue(questionnaires);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ALl", error.getMessage());
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    private void RetrieveQuestionnaire() {
        // Perform any additional logic after fetching and storing data if needed
        List<QuestionnaireEntity> questionnaires = allQuestionDatabase.questionnaireDao().getAllQuestionnaires();
        for (QuestionnaireEntity retrievedQuestionnaire:questionnaires) {
            Log.d("ALl", retrievedQuestionnaire.getName());
            Log.d("ALl", retrievedQuestionnaire.getDescription());

            int questionnaireId = retrievedQuestionnaire.getId();
            String questionnaireName =  retrievedQuestionnaire.getName();
            String questionnaireDescription = retrievedQuestionnaire.getDescription();
            boolean questionnaireIsActive = retrievedQuestionnaire.isActive();
            String questionnaireCreatedAt = retrievedQuestionnaire.getCreatedAt();
            int questionnaireNumberOfQuestions = retrievedQuestionnaire.getNumberOfQuestions();
            String questionnaireActiveTill = retrievedQuestionnaire.getActiveTill();
            String questionnaireTargetApp =  retrievedQuestionnaire.getTargetApp();

            QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity(questionnaireId,questionnaireName, questionnaireDescription, questionnaireCreatedAt, questionnaireNumberOfQuestions, questionnaireActiveTill, questionnaireTargetApp);

            questionnaireEntities.add(questionnaireEntity);
            questionnairesAdapterOffline.setUser(questionnaireEntities);

        }

        // ...

        // Notify observers with the updated list of questionnaires
        questionnairesLiveData.postValue(allQuestionDatabase.questionnaireDao().getAllQuestionnaires());
    }
}
