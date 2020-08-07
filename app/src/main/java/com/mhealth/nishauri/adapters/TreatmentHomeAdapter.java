package com.mhealth.nishauri.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mhealth.nishauri.Models.CurrentArt;
import com.mhealth.nishauri.R;

import java.util.ArrayList;
import java.util.List;

public class TreatmentHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CurrentArt> items = new ArrayList<>();

    private Context context;
    private TreatmentHomeAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(TreatmentHomeAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public TreatmentHomeAdapter(Context context, List<CurrentArt> items) {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_treatment_home, parent, false);
        vh = new TreatmentHomeAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CurrentArt obj = items.get(position);
        if (holder instanceof TreatmentHomeAdapter.OriginalViewHolder) {
            TreatmentHomeAdapter.OriginalViewHolder view = (TreatmentHomeAdapter.OriginalViewHolder) holder;

            view.treatment_type.setText(obj.getRegiment());

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}