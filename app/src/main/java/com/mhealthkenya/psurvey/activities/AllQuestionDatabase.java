package com.mhealthkenya.psurvey.activities;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mhealthkenya.psurvey.interfaces.AnswerDao;
import com.mhealthkenya.psurvey.interfaces.QuestionDao;
import com.mhealthkenya.psurvey.interfaces.QuestionnaireDao;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.QuestionEntity;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;


@Database(entities = {QuestionnaireEntity.class,QuestionEntity.class, AnswerEntity.class}, version =2)
public abstract class AllQuestionDatabase extends RoomDatabase {

    public abstract QuestionnaireDao questionnaireDao();
    public abstract QuestionDao questionDao();
    public abstract AnswerDao answerDao();


    private static final String DATABASENAME = "surveyDB";
    public static AllQuestionDatabase INSTANCE;

    public  static AllQuestionDatabase getInstance(Context context){

        if (INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context, AllQuestionDatabase.class, DATABASENAME).
                    fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }

        return INSTANCE;
    }
    
    
    



}
