package com.example.mhealth.appointment_diary.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.models.Hei;

import java.util.ArrayList;
import java.util.List;


public class AttachedHeisAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Hei> items = new ArrayList<>();

    private Context context;
    private AttachedHeisAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(AttachedHeisAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AttachedHeisAdapter(Context context, List<Hei> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView hei_no;
        public TextView dob;
        public TextView pcr_week;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            hei_no = (TextView) v.findViewById(R.id.hei_no);
            dob = (TextView) v.findViewById(R.id.dob);
            pcr_week = (TextView) v.findViewById(R.id.pcr_week);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hei, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Hei obj = items.get(position);
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            view.name.setText(obj.getHei_first_name()+" "+obj.getHei_middle_name()+" "+obj.getHei_last_name());
            view.hei_no.setText("#"+obj.getHei_no());
            view.dob.setText("DOB: "+obj.getHei_dob());
            view.pcr_week.setText("PCR WEEK 6: "+obj.getPcr_week6());

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}

