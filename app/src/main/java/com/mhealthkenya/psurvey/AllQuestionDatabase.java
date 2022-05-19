package com.mhealthkenya.psurvey;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mhealthkenya.psurvey.interfaces.AllQuestionDao;
import com.mhealthkenya.psurvey.models.Answers;
import com.mhealthkenya.psurvey.models.Questions;


@Database(entities = {Questions.class, Answers.class}, version =1)
public abstract class AllQuestionDatabase extends RoomDatabase {

    private static final String DATABASENAME = "ALlquestionDB";
    public abstract AllQuestionDao questionDao();
    public static AllQuestionDatabase INSTANCE;

    public  static  AllQuestionDatabase getInstance(Context context){

                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context, AllQuestionDatabase.class, DATABASENAME).
                            fallbackToDestructiveMigration().build();
                }

        return INSTANCE;
    }
//hello

   /* public static Callback callback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsyn(INSTANCE);
        }
    };

    static  class  PopulateDbAsyn extends AsyncTask<Void,Void,Void> {
        private AllQuestionDao allQuestionDao;
        public PopulateDbAsyn(AllQuestionDatabase allQuestionDatabase)
        {
           allQuestionDao=allQuestionDatabase.questionDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
          allQuestionDao.deleteAllQuestions();
            return null;
        }
    }*/
    //hello

}
