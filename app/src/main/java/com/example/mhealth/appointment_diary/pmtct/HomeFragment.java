package com.example.mhealth.appointment_diary.pmtct;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.example.mhealth.appointment_diary.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HomeFragment extends Fragment {


    private Unbinder unbinder;
    private View root;
    private Context context;




    @BindView(R.id.unscheduled_hei_apt)
    CardView unscheduled_hei_apt;

    @BindView(R.id.hei_apt)
    CardView hei_apt;

    @BindView(R.id.mother_apt)
    CardView mother_apt;

    @BindView(R.id.update_hei)
    CardView update_hei;

    @BindView(R.id.reg_caregiver)
    CardView reg_caregiver;

    @BindView(R.id.reg_hei)
    CardView reg_hei;

    @BindView(R.id.pcr_positive_enrollment)
    CardView pcr_positive_enrollment;

    @BindView(R.id.final_outcome)
    CardView final_outcome;





    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        this.context = ctx;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, root);



        unscheduled_hei_apt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.nav_exposures);
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.unscheduled_hei_apt);

            }
        });

        hei_apt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Navigation.findNavController(v).navigate(R.id.nav_immunizations);
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.hei_apt);

            }
        });

        mother_apt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.nav_broadcasts);
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.book_mother_apt);

            }
        });

        update_hei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.nav_resources);
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.update_hei);

            }
        });

        reg_caregiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.nav_check_in);
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.caregiver_reg);

            }
        });

        reg_hei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.nav_feedback);
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.hei_reg);

            }
        });

        pcr_positive_enrollment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.nav_feedback);
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.pcr_positive_enrollment);

            }
        });


        final_outcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.nav_feedback);
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.hei_final_outcome);

            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}