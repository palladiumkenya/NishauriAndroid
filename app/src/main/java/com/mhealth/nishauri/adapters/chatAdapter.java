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
import com.mhealth.nishauri.Models.Message;
import com.mhealth.nishauri.R;

import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Handler;
import android.os.Handler;

public class chatAdapter  extends BaseAdapter {


    private Context mContext;
    private List<Message> mMessages;

    public chatAdapter(Context context, List<Message> messages) {
        mContext = context;
        mMessages = messages;
    }

    @Override
    public int getCount() {
        return mMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        Message message = mMessages.get(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (message.isSender()) {
            view = inflater.inflate(R.layout.user_message_sender, null);
            TextView textViewSenderMessage = view.findViewById(R.id.text2);
            textViewSenderMessage.setText(message.getMessage());
        } else {
            view = inflater.inflate(R.layout.user_message, null);
            TextView textViewBotMessage = view.findViewById(R.id.text1);
            textViewBotMessage.setText(message.getMessage());
        }

        return view;
    }

}
