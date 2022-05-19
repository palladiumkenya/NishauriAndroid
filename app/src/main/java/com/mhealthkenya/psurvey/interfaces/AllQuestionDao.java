package com.mhealthkenya.psurvey.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mhealthkenya.psurvey.models.Answers;
import com.mhealthkenya.psurvey.models.Questions;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface AllQuestionDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllQuestions(ArrayList<Questions> inserts);


    @Query("SELECT * FROM Questions")
    LiveData<List<Questions>> getAllQuestions();


     @Query("SELECT * FROM Questions WHERE Question_ID LIKE :Question_ID")
     LiveData<Questions> getQuestion(int Question_ID);

    // @Query("SELECT * FROM Answers WHERE Answer_ID LIKE :Answer_ID")
     //LiveData<Answers> getOptions(int Answer_ID);

    /*@Query("SELECT * FROM Answers WHERE Answer_ID LIKE :")
    LiveData<Answers> getOptions();*/


    /*@Query("DELETE FROM Questions")
    void deleteAllQuestions();*/
}
