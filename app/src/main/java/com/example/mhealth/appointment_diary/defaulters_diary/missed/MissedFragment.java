package com.example.mhealth.appointment_diary.defaulters_diary.missed;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.defaulters_diary.defaultered.call.DefaulterCallFragment;
import com.example.mhealth.appointment_diary.defaulters_diary.missed.call.MissedCallFragment;
import com.example.mhealth.appointment_diary.defaulters_diary.missed.visit.MissedVisitFragment;

import co.ceryle.segmentedbutton.SegmentedButtonGroup;


/**
 * Created by abdullahi on 11/12/2017.
 */

public class MissedFragment extends Fragment {


    private static MissedFragment inst;


    public static MissedFragment instance() {
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
        View view = inflater.inflate(R.layout.missed_fragment,container,false);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame1, new MissedCallFragment())
                    .commit();
        }

        fragPosition=0;
        setDefaultFragment();

         sbg = (SegmentedButtonGroup)view.findViewById(R.id.segmentedButtonGroup);

        sbg.setOnClickedButtonListener(new SegmentedButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(int position) {
                if(position == 0) {
                    setFragment(new MissedCallFragment());
                    fragPosition=0;
                }
                else if(position == 1) {
                    setFragment(new MissedVisitFragment());
                    fragPosition=1;
                }
            }
        });
        /*sbg.setOnClickedButtonPosition(new SegmentedButtonGroup.OnClickedButtonPosition(){

            @Override
            public void onClickedButtonPosition(int position) {
                if(position == 0) {
                    setFragment(new MissedCallFragment());
                    fragPosition=0;
                }
                else if(position == 1) {
                    setFragment(new MissedVisitFragment());
                    fragPosition=1;
                }
            }
        });*/



        return view;
    }


    private void setDefaultFragment() {

        try {

            setFragment(new MissedCallFragment());
        } catch (Exception e) {


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

            MissedCallFragment fragcall = (MissedCallFragment) fm.findFragmentById(R.id.frame1);

            fragcall.doSearching(s);

        } else {

            MissedVisitFragment fragvisit = (MissedVisitFragment) fm.findFragmentById(R.id.frame1);
            fragvisit.doSearching(s);
        }


    }


    public void setFragment(Fragment f){

        FragmentManager fmanager = getChildFragmentManager() ;
        FragmentTransaction ft = fmanager.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out); //for animation while changing fragment
        ft.replace(R.id.frame1,f);
        ft.commit();
    }
}
