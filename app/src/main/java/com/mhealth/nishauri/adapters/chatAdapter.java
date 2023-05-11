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



           // String usrmsg = mylist.get(position).getMsg();
            String btsms = mylist.get(position).getQuestion();
            String usrmsg = mylist.get(position).getMsg();


           // String nascopCccNumber = mylist.get(position).getNascopCccNumber();

            //usersms.setText(usrmsg);

          // botsms.setText(btsms);
            botsms.setText(btsms);
            usersms.setText(usrmsg);

          /* Handler handler =new Handler();
           handler.postDelayed(new Runnable() {
               @Override
               public void run() {
                   String usrmsg = mylist.get(position).getMsg();

                   usersms.setText(usrmsg);


               }
           }, 5000);*/








        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    /*private static final int MY_MESSAGE = 0, OTHER_MESSAGE = 1;
            //MY_IMAGE = 2, OTHER_IMAGE = 3;
    public chatAdapter(Context context, List<ChatMessage> data) {
        super(context, R.layout.user_message, data);
    }
    @Override
    public int getViewTypeCount() {
        // my message, other message, my image, other image
        return 2;
    }
    /*@Override
    public int getItemViewType(int position) {
        //ChatMessage item = getItem(position);
        return  ;
        //if (item.isMine() && !item.isImage()) return MY_MESSAGE;
        //else if (!item.isMine() && !item.isImage()) return OTHER_MESSAGE;
        //else if (item.isMine() && item.isImage()) return MY_IMAGE;
        //else return OTHER_IMAGE;
    }*/
   /* @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        if (viewType == MY_MESSAGE) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_message, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.text);
            textView.setText(getItem(position).getMsg());
        } else if (viewType == OTHER_MESSAGE) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bot_message, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.text);
            textView.setText(getItem(position).getMsg());
        }
        convertView.findViewById(R.id.chatMessageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "onClick", Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }*/
}
