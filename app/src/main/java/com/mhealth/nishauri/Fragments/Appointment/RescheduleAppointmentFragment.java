package com.mhealth.nishauri.Fragments.Appointment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.airbnb.lottie.LottieAnimationView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.mhealth.nishauri.Models.UpcomingAppointment;
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

public class RescheduleAppointmentFragment extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;

    private User loggedInUser;
    private UpcomingAppointment upcomingAppointment;



    private String RESCHEDULED_DATE = "";


    @BindView(R.id.til_reschedule_date)
    TextInputLayout til_reschedule_date;

    @BindView(R.id.txt_reschedule_date)
    TextView txt_reschedule_appointment;

    @BindView(R.id.txt_scheduled_date)
    TextView txt_scheduled_date;

    @BindView(R.id.reason_spinner)
    AppCompatSpinner reason_spinner;

    @BindView(R.id.til_specify_reason)
    TextInputLayout til_specify_reason;

    @BindView(R.id.specify_reason_edtxt)
    TextInputEditText specify_reason_edtxt;

    @BindView(R.id.animationView)
    LottieAnimationView animationView;

    @BindView(R.id.btn_report)
    Button btn_continue;

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
        root = inflater.inflate(R.layout.fragment_reschedule_appointment, container, false);
        unbinder = ButterKnife.bind(this, root);

        assert getArguments() != null;
        upcomingAppointment = (UpcomingAppointment) getArguments().getSerializable("Appointment");

        txt_scheduled_date.setText(upcomingAppointment.getAppntmnt_date());

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);

        initialise();

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkNulls()){

                    rescheduleAppointment(upcomingAppointment.getId());
                }

            }

        });

        return root;
    }

    private void initialise(){

        txt_reschedule_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRescheduledDate();
            }
        });



    }

    private void getRescheduledDate(){
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

                        RESCHEDULED_DATE = newFormat.format(new Date(date_ship_millis));

                        txt_reschedule_appointment.setText(RESCHEDULED_DATE);
                    }
                }, cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private boolean checkNulls(){

        boolean valid = true;


        if(TextUtils.isEmpty(txt_reschedule_appointment.getText().toString()))
        {
            Snackbar.make(root.findViewById(R.id.frag_reschedule_appointment), "Please provide an new date.", Snackbar.LENGTH_LONG).show();
            valid = false;
            return valid;
        }

        if(reason_spinner.getSelectedItem().toString().equals("Pick a reason here…"))
        {
            Snackbar.make(root.findViewById(R.id.frag_reschedule_appointment), "Please select a reason for the appointment.", Snackbar.LENGTH_LONG).show();
            valid = false;
            return valid;
        }

        if(TextUtils.isEmpty(specify_reason_edtxt.getText().toString()))
        {
            Snackbar.make(root.findViewById(R.id.frag_reschedule_appointment), "Please specify a reason for rescheduling", Snackbar.LENGTH_LONG).show();
            valid = false;
            return valid;
        }
        return valid;

    }

    private void rescheduleAppointment(int appointmentId){


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("appntmnt_date", txt_reschedule_appointment.getText().toString());
            jsonObject.put("reason", reason_spinner.getSelectedItem().toString());
            jsonObject.put("comments", specify_reason_edtxt.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        String auth_token = loggedInUser.getAuth_token();




        AndroidNetworking.post(Constants.ENDPOINT+Constants.RESCHEDULE_APPOINTMENT+appointmentId)
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

                        Log.e(TAG, response.toString());


                        animationView.setVisibility(View.GONE);

                        if (response.has("data")){

                            NavHostFragment.findNavController(RescheduleAppointmentFragment.this).navigate(R.id.nav_appointment);
                        }
                        else {
                            Snackbar.make(root.findViewById(R.id.frag_schedule_appointment), "An Error occurred. Please try again later.", Snackbar.LENGTH_LONG).show();

                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, error.getErrorBody());

                        animationView.setVisibility(View.GONE);


                        Snackbar.make(root.findViewById(R.id.frag_reschedule_appointment), "An Error occurred. Please try again later." + error.getErrorDetail(), Snackbar.LENGTH_LONG).show();
                    }
                });


    }

  /*  private void getRescheduledTime() {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                RESHEDULED_DATE = selectedHour + ":" + selectedMinute;

                txt_reschedule_appointment.setText(RESHEDULED_DATE);
//
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }*/
}
