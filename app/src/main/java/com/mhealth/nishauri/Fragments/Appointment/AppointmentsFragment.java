package com.mhealth.nishauri.Fragments.Appointment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.mhealth.nishauri.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AppointmentsFragment extends Fragment {


    private Unbinder unbinder;
    private View root;
    private Context context;

    @BindView(R.id.tab_layout)
    TabLayout tab_layout;

    @BindView(R.id.view_pager)
    ViewPager view_pager;

    @BindView(R.id.fab_appointment)
    ExtendedFloatingActionButton fab_appointment;


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
        root = inflater.inflate(R.layout.fragment_appointments, container, false);
        unbinder = ButterKnife.bind(this, root);

        setupViewPager(view_pager);
        tab_layout.setupWithViewPager(view_pager);

        fab_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AppointmentsFragment.this).navigate(R.id.nav_schedule_appointment);
            }
        });


        return root;
    }

    private void setupViewPager(ViewPager viewPager) {


        AppointmentsFragment.Adapter adapter = new AppointmentsFragment.Adapter(getChildFragmentManager());
        adapter.addFragment(new UpcomingAppointmentsFragment(), "Upcoming");
       // adapter.addFragment(new PendingAppointmentsFragment(), "Pending");
        adapter.addFragment(new PreviousAppointmentsFragment(), "Previous");
//        adapter.addFragment(new ProtocolsTabFragment(), hcw == null ? "Facility" : hcw.getFacility_name());
        viewPager.setAdapter(adapter);
    }

    public static class Adapter extends FragmentPagerAdapter {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
