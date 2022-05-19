package com.mhealthkenya.psurvey.interfaces;

import com.mhealthkenya.psurvey.models.Questions;
import com.mhealthkenya.psurvey.models.QuestionsList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface APIInterface {

    //@GET("api/questions_all/")
   @GET("api/questions_all/1")
    Call<QuestionsList>getALlques();
            //"https://psurvey-api.mhealthkenya.co.ke/api/questions_all/1"
    //Call<ArrayList<AllQuestion>> getALlques(@QueryMap Map<String, Integer> params);
    //Call<ArrayList<Questions>> getALlques();
    //Call<List<AllQuestion>> getALlques();
    //@GET("https://psurvey-api.mhealthkenya.co.ke/api/questions_all/1")
    //Call<ArrayList<AllQuestion>> getResult();

    @GET
    Call<QuestionsList>getALlques1(@Url String url);
}
