package com.example.mhealth.appointment_diary.appointment_diary;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.skyhope.eventcalenderlibrary.CalenderEvent;
import com.skyhope.eventcalenderlibrary.listener.CalenderDayClickListener;
import com.skyhope.eventcalenderlibrary.model.DayContainerModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppointmentCalender extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_calender);

        Button bt =findViewById(R.id.btn1);
     // CompactCalenderView calenderEvent = findViewById(R.id.calID);
        //CompactCalendarView  calenderEvents = findViewById(R.id.calIDs);

        CompactCalendarView compactCalendarView = findViewById(R.id.calIDs);
        compactCalendarView.setUseThreeLetterAbbreviation(true);


        /*Event event = new Event(1669749065, "DFGH", Color.RED );
        calenderEvent.addEvent(event);*/
        Event eventss = new Event(Color.BLUE,  1669749065000L, "Holiday");
        compactCalendarView.addEvent(eventss, true);




        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  Event event = new Event(1669749065, "DFGH", Color.RED );
                calenderEvent.addEvent(event);*/



                String startTime = "2022-02-1T09:00:00";

                //events.add(new EventDay(calendar, R.drawable.sample_icon));



                // Parsing the date and time
               SimpleDateFormat mSimpleDateFormat =new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                try {
                    Date mStartTime = mSimpleDateFormat.parse(startTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                //calenderEvent.
            }
        });

        /*CalenderEvent calenderEvent = findViewById(R.id.calID);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);*/

       /* SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date=format.format(cal.getTime());*/

        //Event event = new Event(calendar.getTimeInMillis(), "Test");
        //calenderEvent.addEvent(event);



      /*  calenderEvent.initCalderItemClickCallback(new CalenderDayClickListener() {
            @Override
            public void onGetDay(DayContainerModel dayContainerModel) {
                Log.d("", dayContainerModel.getDate());

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String dates=format.format(dayContainerModel.getTimeInMillisecond());

                String date=dayContainerModel.getDate();





                Toast.makeText(AppointmentCalender.this, dates, Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}