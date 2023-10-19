package com.mhealthkenya.psurvey.interfaces;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mhealthkenya.psurvey.models.QuestionEntity;

import java.util.List;

@Dao
public interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(QuestionEntity question);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<QuestionEntity> questions);

    @Update
    void update(QuestionEntity question);

    @Delete
    void delete(QuestionEntity question);

    @Query("SELECT * FROM QuestionEntity WHERE id = :questionId")
    QuestionEntity getQuestionById(int questionId);

    @Query("SELECT * FROM QuestionEntity WHERE questionnaireId = :questionnaireId")
    List<QuestionEntity> getQuestionsByQuestionnaireId(int questionnaireId);

    // Add other queries as needed for your use case.

}
