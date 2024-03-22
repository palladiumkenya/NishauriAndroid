package com.mhealth.nishauri.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.mhealth.nishauri.Models.ChatMessage;
import com.mhealth.nishauri.R;

import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Handler;
import android.os.Handler;

public class chatAdapter  extends BaseAdapter {

    private RequestQueue rq;
    private Context mycont;
    private List<ChatMessage> mylist;




    public chatAdapter(Context mycont, List<ChatMessage> mylist) {
        this.mycont = mycont;
        this.mylist = mylist;

    }


    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int position) {
        return mylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = View.inflate(mycont, R.layout.user_message, null);
       // View v = View.inflate(mycont, R.layout.bot_message, null);


        try {
            TextView usersms = (TextView) v.findViewById(R.id.text1);
            TextView botsms = (TextView) v.findViewById(R.id.text2);

            ChatMessage chatMessage = mylist.get(position);
            usersms.setText(mylist.get(position).getQuestion());


            if (position < mylist.size() - 1) {
                ChatMessage nextMessage = mylist.get(position + 1);
               // if (!chatMessage.isFromUser() && nextMessage.isFromUser()) {
                    String botMessage = chatMessage.getMsg();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                          //  botTextView.setText(botMessage);
                            botsms.setText(mylist.get(position).getMsg());
                        }
                    }, 10000); // 10 seconds delay
               // }
            }


          /*  // String usrmsg = mylist.get(position).getMsg();
            String btsms = mylist.get(position).getQuestion();
            String usrmsg = mylist.get(position).getMsg();



          // botsms.setText(btsms);
            botsms.setText(btsms);
            usersms.setText(usrmsg);*/






        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }



}
