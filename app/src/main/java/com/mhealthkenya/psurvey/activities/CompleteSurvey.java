package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.models.Completed;
import com.orm.query.Select;

public class CompleteSurvey extends AppCompatActivity {

    Button btn;
    public int index =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_survey);
        btn =findViewById(R.id.btn_done);

        index = getLastIndex();

       // int lastIndex = getLastIndex();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                index++;
                Completed completed =new Completed(index);
                completed.save();



                Intent intent =new Intent(CompleteSurvey.this, offlineHome.class);
                startActivity(intent);
            }
        });
    }

    private int getLastIndex() {
        // Use Sugar ORM's Select to query the last value inserted for index
        Completed lastCompleted = Select.from(Completed.class).orderBy("ID DESC").first();

        if (lastCompleted != null) {
            // If a record is found, return the index value
            return lastCompleted.getComplete();
        } else {
            // Handle the case where no record is found (e.g., set a default value)
            return 0;
        }
    }
}