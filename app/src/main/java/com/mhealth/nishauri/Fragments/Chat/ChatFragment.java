package com.mhealth.nishauri.Fragments.Chat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.Activities.ART_Activity;
import com.mhealth.nishauri.Activities.Auth.LoginActivity;
import com.mhealth.nishauri.Activities.Auth.LoginPsurvey;
import com.mhealth.nishauri.Activities.ChatInterface;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.Models.auth;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;
import com.mhealth.nishauri.utils.HomeActivitySurvey;
import com.mhealth.nishauri.utils.ScreenLockReceiver;
import com.mhealth.nishauri.utils.SelectSurvey;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ChatFragment extends Fragment {

    //private static final long INACTIVITY_THRESHOLD = 360000; // 2 minutes
    //private static final long CHECK_INTERVAL = 360000; // 2 minutes

    private static final long INACTIVITY_THRESHOLD =27000000; // 30 minutes
    private static final long CHECK_INTERVAL=27000000; // 30 minutes

    private ScreenLockReceiver screenLockReceiver;
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

        }
    };



    private Unbinder unbinder;
    private View root;
    private View root2;
    private Context context;

    @BindView(R.id.faq_card)
    CardView faq_card;

    @BindView(R.id.art_card)
    CardView art_card;

    @BindView(R.id.survey_card1)
    CardView survey_card;

    @BindView(R.id.chat_card)
    CardView chat_card;

    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        this.context = ctx;
        // Initialize the BroadcastReceiver
        screenLockReceiver = new ScreenLockReceiver();

        // Register the BroadcastReceiver to listen for screen off events
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        context.registerReceiver(screenLockReceiver, filter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Unregister the BroadcastReceiver when the fragment is detached
        if (screenLockReceiver != null) {
            requireContext().unregisterReceiver(screenLockReceiver);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_chat, container, false);
        root2 = root.findViewById(R.id.frag_chat);

        root2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                lastInteractionTime = System.currentTimeMillis();
                return false;
                //return true;
            }
        });

        unbinder = ButterKnife.bind(this, root);




        art_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, ART_Activity.class);
                context.startActivity(intent);
            }
        });

        faq_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(ChatFragment.this).navigate(R.id.nav_faqs);

            }
        });

        survey_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // NavHostFragment.findNavController(ChatFragment.this).navigate(R.id.nav_client_survey);
               // loginRequest();

               Intent intent = new Intent(context, SelectSurvey.class);
                context.startActivity(intent);


            }
        });

        chat_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ChatFragment.this).navigate(R.id.nav_interface);

              //  Snackbar.make(root.findViewById(R.id.frag_chat), "Chat Bot Coming Soon", Snackbar.LENGTH_LONG).show();



            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }




    private  boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo =connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);

        if((wifiInfo !=null && wifiInfo.isConnected())|| (mobileInfo !=null && mobileInfo.isConnected())){
            return true;
        }
        else{
            return false;
        }

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
