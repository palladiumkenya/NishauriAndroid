package com.mhealth.nishauri.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.mhealth.nishauri.Activities.Auth.LoginActivity;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;


    public User loggedInUser;
    String  userID11;
    String  userExtra1;

    // Declaring handler, runnable and time in milli seconds
    private Handler mHandler;
    private Runnable mRunnable;
    private long mTime = 20000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing the handler and the runnable
       /* mHandler = new Handler(Looper.getMainLooper());
        mRunnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "User inactive for " + mTime/1000 + " secs!", Toast.LENGTH_SHORT).show();
            }
        };*/

        // Start the handler
       // startHandler();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View headerLayout = navigationView.getHeaderView(0); // 0-index header
        TextView drawer_cccno = (TextView) headerLayout.findViewById(R.id.ccc_no);
        TextView drawer_name = (TextView) headerLayout.findViewById(R.id.full_name);
        TextView drawer_facility = (TextView) headerLayout.findViewById(R.id.current_facility);



        // Passing each menu ID as a set of Ids because each
        // menu should
        // be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_lab, R.id.nav_appointment,R.id.nav_treatment,R.id.nav_dependant,R.id.nav_dashboard,R.id.nav_chat,R.id.nav_bmi, R.id.nav_settings, R.id.nav_update_profile)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);
        String auth_token = loggedInUser.getAuth_token();
        String urls ="?user_id="+auth_token;


        AndroidNetworking.get(Constants.ENDPOINT+Constants.PROFILE+urls)
                //.addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
//                        Log.e(TAG, response.toString());

                        try {

                            JSONArray myArray = response.getJSONArray("data");

                            if (myArray.length() > 0){


                                for (int i = 0; i < myArray.length(); i++) {

                                    JSONObject item = (JSONObject) myArray.get(i);




                                    String client_name = item.has("client_name") ? item.getString("client_name") : "";
                                    String facility_name = item.has("facility_name") ? item.getString("facility_name") : "";
                                    String clinic_number = item.has("clinic_number") ? item.getString("clinic_number") : "";

                                    drawer_name.setText(client_name);
                                    drawer_facility.setText(facility_name);
                                    drawer_cccno.setText(clinic_number);

                                }

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
//                        Log.e(TAG, error.getErrorBody());


                    }
                });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();


                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void logout(){

        String endPoint = Stash.getString(Constants.AUTH_TOKEN);
        Stash.clearAll();

        Stash.put(Constants.AUTH_TOKEN, endPoint);

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

  /*  @Override
    public void onStop() {
        stopHandler();
         super.onStop();

    }

    // When the screen is touched or motion is detected
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Removes the handler callbacks (if any)
       // stopHandler();

        // Runs the handler (for the specified time)
        // If any touch or motion is detected before
        // the specified time, this override function is again called
        startHandler();

        return super.onTouchEvent(event);
    }

    // start handler function
    private void startHandler() {
        mHandler.postDelayed(mRunnable, mTime);
    }

    // stop handler function
    private void stopHandler() {
        mHandler.removeCallbacks(mRunnable);
    }*/


}
