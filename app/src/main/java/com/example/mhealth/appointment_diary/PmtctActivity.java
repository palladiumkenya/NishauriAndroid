package com.example.mhealth.appointment_diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.mhealth.appointment_diary.pmtct.PmtctBookAptFragment;
import com.example.mhealth.appointment_diary.pmtct.PmtctCaregiverRegistrationFragment;
import com.example.mhealth.appointment_diary.pmtct.PmtctHeiAptFragment;
import com.example.mhealth.appointment_diary.pmtct.PmtctRegistrationFragment;
import com.example.mhealth.appointment_diary.pmtct.PmtctUnscheduledHeiAptFragment;
import com.example.mhealth.appointment_diary.pmtct.PmtctUpdateHeiFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class PmtctActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmtct);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("HEI");


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.book_mother_apt, R.id.caregiver_reg,
                R.id.hei_reg, R.id.update_hei, R.id.hei_apt, R.id.unscheduled_hei_apt,
                R.id.pcr_positive_enrollment,R.id.hei_final_outcome)
                //.setDrawerLayout(drawer)
                .build();



        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);




    }




    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
