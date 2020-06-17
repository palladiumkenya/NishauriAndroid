package com.example.mhealth.appointment_diary.report;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.models.RegisterCounter;

import java.util.List;

/**
 * Created by abdullahi on 6/18/2018.
 */

public class Report extends AppCompatActivity {

    TextView mytexview;

    int counter=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        mytexview = (TextView) findViewById(R.id.badge_notification_4);

        List<RegisterCounter> bdy = RegisterCounter.findWithQuery(RegisterCounter.class, "Select * from Register_counter", null);


        try {

            for (int x = 0; x < bdy.size(); x++) {
                counter++;
                mytexview.setText(counter);
            }


        } catch (Exception e) {
        }
    }
}
