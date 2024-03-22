package com.mhealth.nishauri.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.mhealth.nishauri.Models.ChatMessage;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import me.himanshusoni.chatmessageview.ChatMessageView;

public class ChatInterface2 extends AppCompatActivity {

    private EditText etMessage;
    private ChatMessageView chatMessageView1;
    private ChatMessageView chatMessageView;

    private EditText editTextQuestion;
    private TextView textViewChat, typing;
    private RequestQueue requestQueue;

    TextView usersms;
    TextView botsms;
   ImageButton buttonSend;

    public User loggedInUser;
   Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_interface2);

        chatMessageView1 =findViewById(R.id.chatMessageView1);


        toolbar = findViewById(R.id.toolbarMsg);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chatbot");
      //  setSupportActionBar(toolbar);
         usersms = (TextView) findViewById(R.id.text2);
         botsms = (TextView) findViewById(R.id.text1);


        requestQueue = Volley.newRequestQueue(this);
        editTextQuestion = findViewById(R.id.et_message);
       // textViewChat = findViewById(R.id.textViewChat);
        buttonSend = findViewById(R.id.btn_send);

        typing =findViewById(R.id.typing);


        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = editTextQuestion.getText().toString().trim();
                if (!question.isEmpty()) {
                    // Display the question immediately
                   // displayMessage("You: " + question);
                    usersms.setText("");
                    botsms.setText("");


                    usersms.setText(editTextQuestion.getText().toString());

                    // Send the question to the server
                   // sendQuestionToServer(question);
                   // editTextQuestion.setText("");
                    typing.setVisibility(View.VISIBLE);
                    editTextQuestion.setVisibility(View.INVISIBLE);
                    buttonSend.setVisibility(View.INVISIBLE);
                    sendMessage();
                }


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
            jsonObject.put("question", editTextQuestion.getText().toString());

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
                         botsms.setText(sms);
                         editTextQuestion.setText(" ");



                            typing.setVisibility(View.INVISIBLE);
                            chatMessageView1.setVisibility(View.VISIBLE);
                            editTextQuestion.setVisibility(View.VISIBLE);
                            buttonSend.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }



                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(ChatInterface2.this, "error"+error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                         Log.d("server error", error.getErrorDetail());
                        // handle error
                    }
                });
    }

}
