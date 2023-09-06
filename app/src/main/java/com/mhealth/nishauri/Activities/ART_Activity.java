package com.mhealth.nishauri.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.mhealth.nishauri.Activities.Auth.LoginActivity;
import com.mhealth.nishauri.Models.ArtModel;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.adapters.artAdapter;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ART_Activity extends AppCompatActivity {
    private static final long INACTIVITY_THRESHOLD =27000000; // 30 minutes
    private static final long CHECK_INTERVAL=27000000; // 30 minutes

   // private static final long INACTIVITY_THRESHOLD = 360000; // 2 minutes
    //private static final long CHECK_INTERVAL = 360000; // 2 minutes
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



    EditText txt_facility;
    Button btn_search;
    ListView listView;

    Toolbar toolbar1;

    private List<ArtModel> mylist1;
    artAdapter artAdapter1;

    private User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art);

        View view = findViewById(R.id.art_id);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                lastInteractionTime = System.currentTimeMillis();
                return false;
            }
        });

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);

        toolbar1 =findViewById(R.id.toolbarr);
        toolbar1.setTitle("ART Sites");
        setSupportActionBar(toolbar1);

        txt_facility =findViewById(R.id.Fac_name);
        btn_search =findViewById(R.id.btn_search);
        listView =findViewById(R.id.messages);


        mylist1 =new ArrayList<>();
        artAdapter1 =new artAdapter(ART_Activity.this, mylist1);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txt_facility.getText().toString().isEmpty()){
                    Toast.makeText(ART_Activity.this, "Enter facility name", Toast.LENGTH_SHORT).show();

                }else{
                searchFacility();}
            }
        });

    }

    private void searchFacility() {

        mylist1.clear();

        String urls1 = "?search=" + txt_facility.getText().toString();

        String auth_token = loggedInUser.getAuth_token();
        String urls2 ="?user_id="+auth_token;
        Log.e("tokens", auth_token);

        AndroidNetworking.get(Constants.ENDPOINT+Constants.ART_dir+urls1+urls2)
               // .addHeaders("Authorization","Bearer" +" "+ auth_token78/=-0)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mylist1.clear();
                        // do anything with response

                        if (response.length()==0){
                            Toast.makeText(ART_Activity.this, "No Data fot the Search term", Toast.LENGTH_LONG).show();
                        }

                        Log.e("Suceess", response.toString());
                        try {

                                JSONArray jsonArray =response.getJSONArray("msg");
                            if (jsonArray.length()==0){
                                Toast.makeText(ART_Activity.this, "No Data for the Search term"+ " "+txt_facility.getText().toString(), Toast.LENGTH_LONG).show();
                            }

                                for (int a =0; a<jsonArray.length(); a++){

                                    JSONObject jsonObject =jsonArray.getJSONObject(a);

                                    String code_art =jsonObject.getString("code");
                                    String name_art =jsonObject.getString("name");
                                    String facility_type =jsonObject.getString("facility_type");
                                    String telephone =jsonObject.getString("telephone");


                                  ArtModel artModel = new ArtModel(code_art, name_art, facility_type, telephone);
                                  mylist1.add(artModel);
                                  //  urlModelArrayList.add(url_Model.getStage());
                                    listView.setAdapter(artAdapter1);

                                }


                                // existAdapter =new ActiveVAdapter(ActiveNew.this, upilist);




                            } catch (Exception e) {

                                e.printStackTrace();
                            }




                    }
                    @Override
                    public void onError(ANError error) {

                        Log.e("Errors", error.getErrorDetail());
                        Toast.makeText(ART_Activity.this, ""+error.getErrorDetail(), Toast.LENGTH_SHORT).show();


                    }
                });



    }
    public void logout(){

        String endPoint = Stash.getString(Constants.AUTH_TOKEN);
        Stash.clearAll();

        Stash.put(Constants.AUTH_TOKEN, endPoint);

        Intent intent = new Intent(ART_Activity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //context.fini;
    }

    public void alertlogout(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ART_Activity.this);
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