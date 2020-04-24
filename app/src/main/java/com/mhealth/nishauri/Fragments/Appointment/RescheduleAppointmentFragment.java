package com.mhealth.nishauri.Fragments.Appointment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mhealth.nishauri.R;

public class RescheduleAppointmentFragment extends Fragment {


    public RescheduleAppointmentFragment() {
        // Required empty public constructor
    }


    public static RescheduleAppointmentFragment newInstance(String param1, String param2) {
        RescheduleAppointmentFragment fragment = new RescheduleAppointmentFragment();

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
        return inflater.inflate(R.layout.fragment_reschedule_appointment, container, false);
    }
}
