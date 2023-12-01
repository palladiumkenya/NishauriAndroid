package com.mhealthkenya.psurvey.activities;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mhealthkenya.psurvey.interfaces.AnswerDao;
import com.mhealthkenya.psurvey.interfaces.QuestionDao;
import com.mhealthkenya.psurvey.interfaces.QuestionnaireDao;
import com.mhealthkenya.psurvey.interfaces.UserResponseDao;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.QuestionEntity;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;
import com.mhealthkenya.psurvey.models.UserResponseEntity;


@Database(entities = {QuestionnaireEntity.class,QuestionEntity.class, AnswerEntity.class, UserResponseEntity.class}, version =10)
public abstract class AllQuestionDatabase extends RoomDatabase {

    public abstract QuestionnaireDao questionnaireDao();
    public abstract QuestionDao questionDao();
    public abstract AnswerDao answerDao();
    public abstract UserResponseDao userResponseDao();


    private static final String DATABASENAME = "surveyDB";
    public static AllQuestionDatabase INSTANCE;

    public  static AllQuestionDatabase getInstance(Context context){

        if (INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context, AllQuestionDatabase.class, DATABASENAME).
                  //  .fallbackToDestructiveMigration()
                  //  .build();
            fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }

        return INSTANCE;
    }
    
    
    



}
