package com.mhealth.nishauri.Fragments.Appointment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mhealth.nishauri.R;

public class ScheduleAppointmentFragment extends Fragment {

    public ScheduleAppointmentFragment() {
        // Required empty public constructor
    }

    public static ScheduleAppointmentFragment newInstance(String param1, String param2) {
        ScheduleAppointmentFragment fragment = new ScheduleAppointmentFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_appointment, container, false);
    }
}
