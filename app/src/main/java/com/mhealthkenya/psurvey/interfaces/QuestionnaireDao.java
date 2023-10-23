package com.mhealthkenya.psurvey.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mhealthkenya.psurvey.models.QuestionEntity;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;

import java.util.List;

@Dao
public interface QuestionnaireDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(QuestionnaireEntity questionnaire);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<QuestionnaireEntity> questionnaires);

    @Update
    void update(QuestionnaireEntity questionnaire);

    @Delete
    void delete(QuestionnaireEntity questionnaire);

    @Query("SELECT * FROM QuestionnaireEntity WHERE id = :questionnaireId")
    QuestionnaireEntity getQuestionnaireById(int questionnaireId);

    //get questionnaire by id
   /* @Query("SELECT * FROM QuestionnaireEntity WHERE id = :questionnaireId ORDER BY id")
    List<QuestionEntity> getQuestionsOrderedByQuestionId(int questionnaireId);

    @Query("SELECT * FROM QuestionnaireEntity")
    List<QuestionnaireEntity> getAllQuestionnaires2();


    @Query("SELECT * FROM QuestionnaireEntity WHERE isPublished = 1")
    List<QuestionnaireEntity> getPublishedQuestionnaires();*/

    @Query("SELECT * FROM QuestionnaireEntity ORDER BY id")
    QuestionnaireEntity geAllQuestionnaires();

    // Add other queries as needed for your use case.






        }
