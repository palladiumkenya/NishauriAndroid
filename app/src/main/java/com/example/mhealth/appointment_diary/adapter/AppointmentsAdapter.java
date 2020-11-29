package com.example.mhealth.appointment_diary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.models.Appointment;
import com.example.mhealth.appointment_diary.models.Hei;

import java.util.ArrayList;
import java.util.List;


public class AppointmentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Appointment> items = new ArrayList<>();

    private Context context;
    private AppointmentsAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(AppointmentsAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AppointmentsAdapter(Context context, List<Appointment> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView apt_date;
        public TextView appointment_type;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            apt_date = (TextView) v.findViewById(R.id.apt_date);
            appointment_type = (TextView) v.findViewById(R.id.appointment_type);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Appointment obj = items.get(position);
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            view.name.setText(obj.getF_name()+" "+obj.getM_name()+" "+obj.getL_name());
            view.apt_date.setText("Date: "+obj.getAppointment_date());
            view.appointment_type.setText("Type: "+obj.getAppointment_type());

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

