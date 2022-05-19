package com.mhealthkenya.psurvey;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.mhealthkenya.psurvey.interfaces.AllQuestionDao;
import com.mhealthkenya.psurvey.models.Answers;
import com.mhealthkenya.psurvey.models.Questions;

import java.util.ArrayList;
import java.util.List;

public class AQ_Repository {
    public AllQuestionDao questionsDao;
    public LiveData<List<Questions>> getAllQuestions;
    private AllQuestionDatabase allQuestionDatabase;
    LiveData<Questions> getSingleq;
    //LiveData<Answers> getOptions1;



    public AQ_Repository(Application application){
        allQuestionDatabase =AllQuestionDatabase.getInstance(application);
        questionsDao=allQuestionDatabase.questionDao();
        getAllQuestions=questionsDao.getAllQuestions();
        getSingleq =questionsDao.getQuestion(3);
       // getOptions1 =questionsDao.getOptions(2);


    }

    public  void  inserts(List<Questions> quez){
        new insertAsyncTasks(questionsDao).execute((ArrayList<Questions>) quez);
        Log.d("main", String.valueOf(quez));




    }
        //get all questions
    public LiveData<List<Questions>> getAll(){
        return getAllQuestions;

    }
    //get 1 question
    public LiveData<Questions> getGetSingleq(){
        return getSingleq;
    }

    //get answerOptions
   /* public LiveData<Answers> getGetOptions1(){
        return getOptions1;
    }*/

    private static  class insertAsyncTasks extends AsyncTask<ArrayList<Questions>, Void, Void>{

        private AllQuestionDao questionsDao;

        public  insertAsyncTasks(AllQuestionDao questionsDao1){
            this.questionsDao=questionsDao1;
        }

        @Override
        protected Void doInBackground(ArrayList<Questions>... lists) {
            questionsDao.insertAllQuestions(lists[0]);
            return null;
        }
    }

}
