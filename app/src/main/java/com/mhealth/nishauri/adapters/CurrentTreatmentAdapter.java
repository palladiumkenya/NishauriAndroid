package com.mhealth.nishauri.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mhealth.nishauri.Models.CurrentTreatment;
import com.mhealth.nishauri.Models.Dependant;
import com.mhealth.nishauri.R;

import java.util.ArrayList;
import java.util.List;

public class CurrentTreatmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CurrentTreatment> items = new ArrayList<>();

    private Context context;
    private CurrentTreatmentAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(CurrentTreatmentAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CurrentTreatmentAdapter(Context context, List<CurrentTreatment> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView treatment_type;



        public OriginalViewHolder(View v) {
            super(v);
            treatment_type = (TextView) v.findViewById(R.id.txt_treatment_nature);


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_active_treatment, parent, false);
        vh = new CurrentTreatmentAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CurrentTreatment obj = items.get(position);
        if (holder instanceof CurrentTreatmentAdapter.OriginalViewHolder) {
            CurrentTreatmentAdapter.OriginalViewHolder view = (CurrentTreatmentAdapter.OriginalViewHolder) holder;

            view.treatment_type.setText(obj.getTreatment());

        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
