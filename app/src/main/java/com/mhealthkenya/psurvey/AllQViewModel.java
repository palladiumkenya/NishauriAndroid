package com.mhealthkenya.psurvey;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mhealthkenya.psurvey.models.Answers;
import com.mhealthkenya.psurvey.models.Questions;

import java.util.ArrayList;
import java.util.List;

public class AllQViewModel extends AndroidViewModel {

    private  AQ_Repository  aq_repository;
    public LiveData<List<Questions>> getAllData;
    //get single question
    public LiveData<Questions> get1;
    //getAnswerOptions
    public  LiveData<Answers> getOpt;

    public AllQViewModel(@NonNull Application application) {
        super(application);

        aq_repository =new AQ_Repository(application);
       // getAllData =aq_repository.getAllQuestions;
        getAllData =aq_repository.getAll();
        //get 1 question
        get1 =aq_repository.getGetSingleq();
        //get options
        //getOpt=aq_repository.getGetOptions1();
    }
    public  void insert(ArrayList<Questions> quiz){
        aq_repository.inserts(quiz);
    }
    //

public LiveData<Questions> getGet1(){
        return get1;
}

/*public LiveData<Answers> getGetOpt(){
        return getOpt;
}*/



    //

    public LiveData<List<Questions>> getGetAllData() {
        return getAllData;

    }
}
