package com.mhealth.nishauri.Fragments.Appointment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mhealth.nishauri.Fragments.Dependants.AddDependantFragment;
import com.mhealth.nishauri.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ScheduleAppointmentFragment extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;

    private String SHEDULED_DATE = "";


    @BindView(R.id.txt_date_time_appointment)
    TextView txt_appointment_date_time;

    @BindView(R.id.btn_submit)
    Button btn_submit;

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

        txt_appointment_date_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getScheduledDate();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(ScheduleAppointmentFragment.this).navigate(R.id.nav_appointment);
                }

        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

                        SHEDULED_DATE = newFormat.format(new Date(date_ship_millis));
                        getScheduledTime();
                    }
                }, cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void getScheduledTime() {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                SHEDULED_DATE = "Date & Time: " + SHEDULED_DATE + " " + selectedHour + ":" + selectedMinute;

                txt_appointment_date_time.setText(SHEDULED_DATE);
//
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }
}
