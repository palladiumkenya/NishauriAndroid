package com.mhealth.nishauri.Fragments.Appointment;

import static com.mhealth.nishauri.utils.AppController.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.navigation.fragment.NavHostFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.NetworkResponse;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mhealth.nishauri.Activities.MainActivity;
import com.mhealth.nishauri.Fragments.HomeFragment;
import com.mhealth.nishauri.Models.UpcomingAppointment;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;

public class UpcomingAppointmentNew extends AppCompatActivity {

    private User loggedInUser;
    private UpcomingAppointment upcomingAppointment;

    private String RESCHEDULED_DATE = "";

    String message;
    boolean status;

    JSONObject JO;


    @BindView(R.id.til_reschedule_date)
    TextInputLayout til_reschedule_date;

    /*@BindView(R.id.txt_reschedule_date)
    TextView txt_reschedule_appointment;*/

   /* @BindView(R.id.txt_scheduled_date)
    TextView txt_scheduled_date;*/

    @BindView(R.id.reason_spinner)
    AppCompatSpinner reason_spinner;

    @BindView(R.id.til_specify_reason)
    TextInputLayout til_specify_reason;

    /*@BindView(R.id.specify_reason_edtxt)
    TextInputEditText specify_reason_edtxt;*/

    @BindView(R.id.animationView)
    LottieAnimationView animationView;

    /*@BindView(R.id.btn_report11)
    Button btn_continues;*/

    Button btn1;
    String X;

    TextView  txt_reschedule_appointment, txt_scheduled_dateA;
    TextInputEditText specify_reason_edtxtA;

    String dateA;
    int idA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_appointment_new);
        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);
        btn1 =(Button) findViewById(R.id.btn_reschedule1);
        txt_reschedule_appointment =(TextView) findViewById(R.id.txt_reschedule_date1);
        txt_scheduled_dateA =(TextView) findViewById(R.id.txt_scheduled_date);
        specify_reason_edtxtA =findViewById(R.id.specify_reason_edtxt11);

       // getRescheduledDate();

        txt_reschedule_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(UpcomingAppointmentNew.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        txt_reschedule_appointment.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
               // datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();

            }
        });


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                dateA = null;
                idA= Integer.parseInt(null);
            } else {
                dateA = extras.getString("date1");
                idA = extras.getInt("ID");

            }
        } else {
            dateA = (String) savedInstanceState.getSerializable("date1");
            idA = (int) savedInstanceState.getSerializable("ID");

        }

        txt_scheduled_dateA.setText(dateA);





btn1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ///Toast.makeText(UpcomingAppointmentNew.this, "CLICK.", Toast.LENGTH_SHORT).show();

        if(TextUtils.isEmpty(txt_reschedule_appointment.getText().toString()))
        {

            Toast.makeText(UpcomingAppointmentNew.this, "Please provide an new date.", Toast.LENGTH_SHORT).show();
        }
     else   if(TextUtils.isEmpty(specify_reason_edtxtA.getText().toString()))
        {
            Toast.makeText(UpcomingAppointmentNew.this, "Please Enter Reason.", Toast.LENGTH_SHORT).show();
        }else {

        rescheduleAppointment1();}

    }
});



    }
    public void rescheduleAppointment1(){


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("reschedule_date", txt_reschedule_appointment.getText().toString());
            jsonObject.put("reason",    specify_reason_edtxtA.getText().toString());
            jsonObject.put("appt_id", idA);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //String auth_token = loggedInUser.getAuth_token();


        AndroidNetworking.post("https://ushauriapi.kenyahmis.org/nishauri/reschedule")
               // .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .addJSONObjectBody(jsonObject) // posting json
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        Log.e(TAG, response.toString());




                        // animationView.setVisibility(View.GONE);


                            try {

                               // status = response.has("success") && response.getBoolean("success");
                                status =  response.getBoolean("success");
                                message = response.has("msg") ? response.getString("msg"): "";

                                if (status){

                                    // NavHostFragment.findNavController(RescheduleAppointmentFragment.this).navigate(R.id.nav_appointment);
                                   // Toast.makeText(UpcomingAppointmentNew.this, message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(UpcomingAppointmentNew.this, MainActivity.class);
                                    startActivity(intent);

                                    //Toast.makeText(UpcomingAppointmentNew.this, message, Toast.LENGTH_SHORT).show();

                                }


                           }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                      //  Toast.makeText(UpcomingAppointmentNew.this, "hhh"+message, Toast.LENGTH_SHORT).show();

                        Toast.makeText(UpcomingAppointmentNew.this, message, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("onError", error.getErrorBody());

                        try {

                            JO = new JSONObject(error.toString());
                            Toast.makeText(UpcomingAppointmentNew.this, JO.getString("msg"), Toast.LENGTH_SHORT).show();
                            Log.e("onError",  JO.getString("msg"));


                        }catch (JSONException e){

                            e.printStackTrace();
                        }



                         Toast.makeText(UpcomingAppointmentNew.this, error.getErrorBody(), Toast.LENGTH_SHORT).show();

                        //animationView.setVisibility(View.GONE);


                       // Snackbar.make(root.findViewById(R.id.frag_reschedule_appointment), "An Error occurred. Please try again later." + error.getErrorDetail(), Snackbar.LENGTH_LONG).show();
                    }
                });


    }
    private void getRescheduledDate(){
        Calendar cur_calender = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(UpcomingAppointmentNew.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        long date_ship_millis = calendar.getTimeInMillis();
                        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);

                        RESCHEDULED_DATE = newFormat.format(new Date(date_ship_millis));

                        txt_reschedule_appointment.setText(RESCHEDULED_DATE);
                    }
                }, cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }


}