package com.example.mhealth.appointment_diary.defaulters_diary.defaultered;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.defaulters_diary.defaultered.call.DefaulterCallFragment;
import com.example.mhealth.appointment_diary.defaulters_diary.defaultered.visit.DefaulterVisitFragment;

import co.ceryle.segmentedbutton.SegmentedButtonGroup;


/**
 * Created by abdullahi on 11/12/2017.
 */

public class DefaulterFragment extends Fragment {


    private static final String TAG = "LosttoFollowFragment";

    Button mybutton1, mybutton2;

    private static DefaulterFragment inst;


    public static DefaulterFragment instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }


    SegmentedButtonGroup sbg;
    int fragPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.defaulted_fragment, container, false);

        if (savedInstanceState == null) {
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame2, new DefaulterCallFragment())
                            .commit();
                }
            }; handler.post(runnable);



            /*getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame2, new DefaulterCallFragment())
                    .commit();*/
        }
        fragPosition=0;

        Handler handler = new Handler();
        Runnable runnable =new Runnable() {
            @Override
            public void run() {
                setDefaultFragment();
            }
        };handler.post(runnable);


        //setDefaultFragment();

        sbg = (SegmentedButtonGroup) view.findViewById(R.id.segmentedButtonGroup);

        sbg.setOnClickedButtonListener(new SegmentedButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(int position) {
                Handler handler1 = new Handler();
                Runnable runnable1 = new Runnable() {
                    @Override
                    public void run() {
                        if (position == 0) {

                            setFragment(new DefaulterCallFragment());
                            fragPosition = 0;

                        } else if (position == 1) {

                            setFragment(new DefaulterVisitFragment());
                            fragPosition = 1;

                        }
                    }
                };handler1.post(runnable1);

               /* if (position == 0) {

                    setFragment(new DefaulterCallFragment());
                    fragPosition = 0;

                } else if (position == 1) {

                    setFragment(new DefaulterVisitFragment());
                    fragPosition = 1;

                }*/
            }
        });

//        sbg.setOnClickedButtonPosition(new SegmentedButtonGroup.OnClickedButtonPosition() {
//
//            @Override
//            public void onClickedButtonPosition(int position) {
//                if (position == 0) {
//
//                    setFragment(new DefaulterCallFragment());
//                    fragPosition = 0;
//
//                } else if (position == 1) {
//
//                    setFragment(new DefaulterVisitFragment());
//                    fragPosition = 1;
//
//                }
//
//            }
//        });


        return view;
    }

    private void setDefaultFragment() {

        try {
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    setFragment(new DefaulterCallFragment());
                }
            };handler.post(runnable);

            //setFragment(new DefaulterCallFragment());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onResume() {
        super.onResume();

//        Toast.makeText(getActivity(), "active pos "+sbg.getPosition(), Toast.LENGTH_SHORT).show();

    }


    public void Dosearch(CharSequence s) {

        FragmentManager fm = getChildFragmentManager();
        if (fragPosition == 0) {

            DefaulterCallFragment fragcall = (DefaulterCallFragment) fm.findFragmentById(R.id.frame2);
            fragcall.doSearching(s);

        } else {

            DefaulterVisitFragment fragvisit = (DefaulterVisitFragment) fm.findFragmentById(R.id.frame2);
            fragvisit.doSearching(s);
        }


    }


    public void setFragment(Fragment f) {

        Handler handler =new Handler();
        Runnable runnable =new Runnable() {
            @Override
            public void run() {
                FragmentManager fmanager = getChildFragmentManager();
                FragmentTransaction ft = fmanager.beginTransaction();
                ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out); //for animation while changing fragment
                ft.replace(R.id.frame2, f);
                ft.commit();
            }
        };handler.post(runnable);

       /* FragmentManager fmanager = getChildFragmentManager();
        FragmentTransaction ft = fmanager.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out); //for animation while changing fragment
        ft.replace(R.id.frame2, f);
        ft.commit();*/
    }
}

