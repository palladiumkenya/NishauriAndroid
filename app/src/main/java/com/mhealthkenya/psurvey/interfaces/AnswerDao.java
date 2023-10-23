package com.mhealthkenya.psurvey.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.QuestionEntity;

import java.util.List;

@Dao
public interface AnswerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(AnswerEntity answer);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<AnswerEntity> answers);

    @Update
    void update(AnswerEntity answer);

    @Delete
    void delete(AnswerEntity answer);

    @Query("SELECT * FROM AnswerEntity WHERE id = :answerId")
    AnswerEntity getAnswerById(int answerId);

    //@Query("SELECT * FROM AnswerEntity WHERE questionId = :questionId")
    //List<AnswerEntity> getAnswersForQuestion(int questionId);

    // Add other queries as needed for your use case.

    @Query("SELECT * FROM AnswerEntity WHERE questionId = :questionId")
    AnswerEntity getAnswersOrderedByAnswerId(int  questionId);

}
