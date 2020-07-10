package com.mhealth.nishauri.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mhealth.nishauri.Models.UpcomingAppointment;
import com.mhealth.nishauri.R;

import java.util.ArrayList;
import java.util.List;

public class AppointmentHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UpcomingAppointment> items = new ArrayList<>();

    private Context context;
    private AppointmentHomeAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(AppointmentHomeAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AppointmentHomeAdapter(Context context, List<UpcomingAppointment> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView appointment_date;
        public TextView appointmet_type;





        public OriginalViewHolder(View v) {
            super(v);
            appointment_date = (TextView) v.findViewById(R.id.txt_appointment_date_home);
            appointmet_type = (TextView) v.findViewById(R.id.txt_appointment_type_home);



        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment_home, parent, false);
        vh = new AppointmentHomeAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        UpcomingAppointment obj = items.get(position);
        if (holder instanceof AppointmentHomeAdapter.OriginalViewHolder) {
            AppointmentHomeAdapter.OriginalViewHolder view = (AppointmentHomeAdapter.OriginalViewHolder) holder;

            view.appointment_date.setText("Date: "+obj.getAppntmnt_date());
            view.appointmet_type.setText(obj.getApp_type());


        }
    }



    @Override
    public int getItemCount() {
        return items.size();
    }
}