package com.example.mhealth.appointment_diary.DateTimePicker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class DateTimePicker {

    Context ctx;

    public DateTimePicker(Context ctx) {
        this.ctx = ctx;
    }

    public void setTimePicker(final EditText edt){

        try{

            // calender class's instance and get current date , month and year from calender
            final Calendar c = Calendar.getInstance();

            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);



            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(ctx, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                    edt.setText(selectedHour + ":" + selectedMinute);
                }
            }, hour, minute,true);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        }
        catch(Exception e){


        }
    }



    public void setDatePicker(final EditText edt){

        try{

            // calender class's instance and get current date , month and year from calender
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

            DatePickerDialog datePickerDialog;

            datePickerDialog = new DatePickerDialog(ctx,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // set day of month , month and year value in the edit text
                            String dom=String.format("%02d", dayOfMonth);
                            String moy=String.format("%02d", (monthOfYear + 1));

                            edt.setText(dom + "/"
                                    + moy + "/" + year);


                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        catch(Exception e){


        }
    }

    public void setDateTimePicker(final EditText edt){

        try{

            // calender class's instance and get current date , month and year from calender
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

            DatePickerDialog datePickerDialog;

            datePickerDialog = new DatePickerDialog(ctx,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // set day of month , month and year value in the edit text
                            String dom=String.format("%02d", dayOfMonth);
                            String moy=String.format("%02d", (monthOfYear + 1));


                            appendTimePicker(edt,dom,moy,year);



                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        catch(Exception e){


        }
    }




    public void appendTimePicker(final EditText edt,final String dom,final String moy,final int year){



        try{

            // calender class's instance and get current date , month and year from calender
            final Calendar c = Calendar.getInstance();

            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);



            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(ctx, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {


                    edt.setText(dom + "/"
                            + moy + "/" + year+" "+selectedHour + ":" + selectedMinute);
                }
            }, hour, minute,true);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        }
        catch(Exception e){


        }
    }


}
