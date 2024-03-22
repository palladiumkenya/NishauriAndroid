package com.mhealth.nishauri.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.mhealth.nishauri.Activities.Auth.LoginActivity;
import com.mhealth.nishauri.Fragments.Chat.ChatFragment;
import com.mhealth.nishauri.Models.ChatMessage;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.adapters.chatAdapter;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChatInterface extends AppCompatActivity {

    //private static final long INACTIVITY_THRESHOLD = 10000; // 2 minutes
   //git  private static final long CHECK_INTERVAL = 10000; // 2 minutes
    private static final long INACTIVITY_THRESHOLD =27000000; // 30 minutes
    private static final long CHECK_INTERVAL=27000000; // 30 minutes
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


    private Toolbar toolbar;

    ImageButton smssend;
    EditText smstxt;

    public User loggedInUser;



    private List<ChatMessage> smslist;
    chatAdapter chatAdapter1;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_interface);

        toolbar = findViewById(R.id.toolbarMsg);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        View view = findViewById(R.id.chat_id);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                lastInteractionTime = System.currentTimeMillis();
                return false;
            }
        });

        smstxt = findViewById(R.id.et_message);
        smssend = findViewById(R.id.btn_send);

        listView =findViewById(R.id.listView1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chatbot");
        setSupportActionBar(toolbar);

        smslist =new ArrayList<>();

        chatAdapter1=new chatAdapter(ChatInterface.this, smslist);


        smssend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (smstxt.getText().toString().isEmpty()){
                    Toast.makeText(ChatInterface.this, "Please Enter a Question", Toast.LENGTH_LONG).show();
                }else{

                sendMessage();
                //smstxt.setText("");
                    }

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(ChatInterface.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }

    private void sendMessage(){

        // String auth_token = loggedInUser.getAuth_token();

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);
        String auth_token = loggedInUser.getAuth_token();
        String urls ="?user_id="+auth_token;

        String urlMain ="https://ushauriapi.kenyahmis.org/nishauri/chat";



        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("question", smstxt.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

     //  smstxt.setVisibility(View.VISIBLE);

        AndroidNetworking.post(urlMain)
                //.addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setMaxAgeCacheControl(300000, TimeUnit.MILLISECONDS)
                .addJSONObjectBody(jsonObject) // posting json
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(ChatInterface.this, "Your BMI is "+response, Toast.LENGTH_SHORT).show();

                        try {
                            String sms = response.getString("msg");
                            String sms1 = response.getString("question");

                            Log.d("sms", sms );
                            Log.d("query", sms1 );

                            ChatMessage chatMessage = new ChatMessage(sms1, sms);
                            smslist.add(chatMessage);
                            listView.setAdapter(chatAdapter1);

                    /*        // Delay the display of "msg" by 10 seconds
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ChatMessage delayedChatMessage = new ChatMessage(sms1, sms);
                                    smslist.add(delayedChatMessage);
                                    chatAdapter1.notifyDataSetChanged();

                                    smstxt.setVisibility(View.INVISIBLE);
                                }
                            }, 10000); // 10 seconds delay*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }



                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(ChatInterface.this, "error"+error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        // Log.d("server error", error.getErrorDetail());
                        // handle error
                    }
                });
    }

    public void logout(){

        String endPoint = Stash.getString(Constants.AUTH_TOKEN);
        Stash.clearAll();

        Stash.put(Constants.AUTH_TOKEN, endPoint);

        Intent intent = new Intent(ChatInterface.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //context.fini;
    }

    public void alertlogout(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ChatInterface.this);
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