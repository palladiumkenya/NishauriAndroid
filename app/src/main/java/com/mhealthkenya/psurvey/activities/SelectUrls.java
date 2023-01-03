package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.models.urlModel;

import java.util.ArrayList;

public class SelectUrls extends AppCompatActivity {

    urlModel url_Model;
    ArrayList<String> urlModelArrayList;
    ArrayList<urlModel> names;
    Spinner spinner1;
    public String z, zz;

    int dataId;
    public String base_url;
    public String stage_name;
    Button btn_prcd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_urls);


    }
}