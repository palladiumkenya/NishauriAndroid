package com.mhealthkenya.psurvey.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mhealthkenya.psurvey.models.UserResponseEntity;

import java.util.List;

@Dao
public interface UserResponseDao {
    @Insert
    void insertResponse(UserResponseEntity userResponse);

    @Update
    Void updateResponse1(UserResponseEntity userResponseEntity);

    @Query("SELECT * FROM user_responses WHERE questionnaireId = :questionnaireId")
    List<UserResponseEntity> getUserResponsesForQuestionnaire(int questionnaireId);

    // Add more queries and operations as needed
}
