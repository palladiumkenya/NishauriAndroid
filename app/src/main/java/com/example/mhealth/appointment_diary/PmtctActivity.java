package com.example.mhealth.appointment_diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.mhealth.appointment_diary.pmtct.PmtctBookAptFragment;
import com.example.mhealth.appointment_diary.pmtct.PmtctCaregiverRegistrationFragment;
import com.example.mhealth.appointment_diary.pmtct.PmtctHeiAptFragment;
import com.example.mhealth.appointment_diary.pmtct.PmtctRegistrationFragment;
import com.example.mhealth.appointment_diary.pmtct.PmtctUnscheduledHeiAptFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class PmtctActivity extends AppCompatActivity {

    private ViewPager view_pager;
    private TabLayout tab_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmtct);


        view_pager = findViewById(R.id.view_pager);
        tab_layout = findViewById(R.id.tab_layout);


        setupViewPager(view_pager);
        tab_layout.setupWithViewPager(view_pager);


    }


    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new PmtctRegistrationFragment(), "HEI REGISTRATION");
        adapter.addFragment(new PmtctCaregiverRegistrationFragment(), "CAREGIVER REGISTRATION");
        adapter.addFragment(new PmtctBookAptFragment(), "MOTHER APT");
        adapter.addFragment(new PmtctHeiAptFragment(), "HEI APT");
        adapter.addFragment(new PmtctUnscheduledHeiAptFragment(), "UNSCHEDULED HEI APT");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



}
