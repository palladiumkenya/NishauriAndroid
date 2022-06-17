package com.mhealth.nishauri.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhealth.nishauri.Models.MessageModel;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter {

    private ArrayList<MessageModel> messageArraylist;
    private Context context;

    public MessageAdapter(ArrayList<MessageModel> messageArraylist, Context context) {
        this.messageArraylist = messageArraylist;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
