package com.mhealth.nishauri.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    private Toolbar toolbar;

   ImageButton smssend;
    EditText smstxt;
    String z;
    private User loggedInUser;


    private List<ChatMessage> smslist;
    chatAdapter chatAdapter1;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_interface);

        toolbar = findViewById(R.id.toolbarMsg);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

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
                smstxt.setText("");}

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



        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("question", smstxt.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }

        AndroidNetworking.post(z+Constants.CHAT+urls)
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


                            String  sms = response.getString("msg");
                            String  sms1 = response.getString("question");

                            Log.d("sms", sms );
                            Log.d("query", sms1 );

                                ChatMessage chatMessage = new ChatMessage(sms, sms1);
                                //upilist=new ArrayList<>();
                                smslist.add(chatMessage);

                                listView.setAdapter(chatAdapter1);

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
}