package com.mhealth.nishauri.Fragments.Appointment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.mhealth.nishauri.Fragments.Dependants.AddDependantFragment;
import com.mhealth.nishauri.Fragments.Profile.UpdateUserFragment;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mhealth.nishauri.utils.AppController.TAG;

public class ScheduleAppointmentFragment extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;

    private User loggedInUser;

    private String SCHEDULE_DATE = "";


    @BindView(R.id.txt_appointment_date)
    TextInputEditText txt_appointment_date;

    @BindView(R.id.reason_spinner)
    AppCompatSpinner reason_spinner;

    @BindView(R.id.lyt_specific_reason)
    LinearLayout lyt_specific_reason;

    @BindView(R.id.specify_reason_edtxt)
    TextInputEditText specify_reason_edtxt;

    @BindView(R.id.btn_submit)
    Button btn_submit;

    @BindView(R.id.animationView)
    LottieAnimationView animationView;

    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        this.context = ctx;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_schedule_appointment, container, false);
        unbinder = ButterKnife.bind(this, root);

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);

       initialise();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initialise(){

        txt_appointment_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getScheduledDate();
            }
        });

        reason_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                if (reason_spinner.getSelectedItem().toString().equals("Other")){

                    lyt_specific_reason.setVisibility(View.VISIBLE);

                }
                else {
                    lyt_specific_reason.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });



        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkNulls()){
                    scheduleAppointment();
                    animationView.setVisibility(View.VISIBLE);
                }

            }

        });

    }

    private void getScheduledDate(){
        Calendar cur_calender = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        long date_ship_millis = calendar.getTimeInMillis();
                        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);

                        SCHEDULE_DATE = newFormat.format(new Date(date_ship_millis));

                        txt_appointment_date.setText(SCHEDULE_DATE);
                    }
                }, cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private boolean checkNulls(){

        boolean valid = true;


        if(TextUtils.isEmpty(txt_appointment_date.getText().toString()))
        {
            Snackbar.make(root.findViewById(R.id.frag_schedule_appointment), "Please provide an appointment date.", Snackbar.LENGTH_LONG).show();
            valid = false;
            return valid;
        }

        //if(reason_spinner.getSelectedItem().toString().equals("Pick a reason here…"))
            if(reason_spinner.getSelectedItem().toString().equals(""))
        {
            Snackbar.make(root.findViewById(R.id.frag_schedule_appointment), "Please select a reason for the appointment.", Snackbar.LENGTH_LONG).show();
            valid = false;
            return valid;
        }
        return valid;

    }

    private void scheduleAppointment(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("app_type", reason_spinner.getSelectedItem().toString());
            jsonObject.put("comments", specify_reason_edtxt.getText().toString());
            jsonObject.put("appntmnt_date", txt_appointment_date.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        String auth_token = loggedInUser.getAuth_token();



        AndroidNetworking.post(Constants.ENDPOINT+Constants.SCHEDULE_APPOINTMENT)
                .addHeaders("Authorization","Token "+ auth_token)
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

//                        Log.e(TAG, response.toString());


                        animationView.setVisibility(View.GONE);

                        if (response.has("data")){

                            NavHostFragment.findNavController(ScheduleAppointmentFragment.this).navigate(R.id.nav_appointment);
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
//                        Log.e(TAG, error.getErrorBody());

                        animationView.setVisibility(View.GONE);


                        Snackbar.make(root.findViewById(R.id.frag_schedule_appointment), "" + error.getErrorBody(), Snackbar.LENGTH_LONG).show();
                    }
                });


    }

   /* private void getScheduledTime() {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                SCHEDULED_TIME = selectedHour + ":" + selectedMinute;

                txt_appointment_date.setText(SCHEDULE_TIME);
//
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }*/
}
