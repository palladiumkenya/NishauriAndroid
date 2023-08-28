package com.mhealth.nishauri.Fragments.Appointment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.fxn.stash.Stash;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.mhealth.nishauri.Activities.Auth.LoginActivity;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AppointmentsFragment extends Fragment {

    private static final long INACTIVITY_THRESHOLD = 360000; // 2 minutes
    private static final long CHECK_INTERVAL =360000; // 2 minutes
    //10000 10seconds

    private long lastInteractionTime = 0;
    private Handler inactivityHandler = new Handler();

    private Runnable inactivityRunnable = new Runnable() {
        @Override
        public void run() {

            long currentTime = System.currentTimeMillis();
            if (currentTime - lastInteractionTime >= INACTIVITY_THRESHOLD) {
                // Perform logout actions here
                //logoutUser();
                alertlogout();
            } else {
                // Schedule the next check
                inactivityHandler.postDelayed(this, CHECK_INTERVAL);
            }
            //performLogout();
            //alertlogout();


        }
    };


    private Unbinder unbinder;
    private View root;
    private View root2;
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

        root2 = root.findViewById(R.id.main_content);
        root2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                lastInteractionTime = System.currentTimeMillis();
                return false;
                //return true;
            }
        });
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
    public void logout(){

        String endPoint = Stash.getString(Constants.AUTH_TOKEN);
        Stash.clearAll();

        Stash.put(Constants.AUTH_TOKEN, endPoint);

        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        //context.fini;
    }
    public void alertlogout(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setIcon(R.drawable.nishauri_logo);
        builder1.setTitle("Your Session Has Expired");
        // builder1.setMessage( zz);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        logout();

                        //dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

   /* @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        lastInteractionTime = System.currentTimeMillis();
    }*/

    @Override
    public void onPause() {
        super.onPause();
        // Remove any pending callbacks when the fragment is paused
        inactivityHandler.removeCallbacks(inactivityRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Resume checking for inactivity when the fragment is resumed
        inactivityHandler.postDelayed(inactivityRunnable, CHECK_INTERVAL);
    }



}
