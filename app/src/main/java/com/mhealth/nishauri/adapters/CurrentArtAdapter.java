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

public class CurrentArtAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CurrentArt> items = new ArrayList<>();

    private Context context;
    private CurrentArtAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(CurrentArtAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CurrentArtAdapter(Context context, List<CurrentArt> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView start_date;
        public TextView treatment_type;


        public OriginalViewHolder(View v) {
            super(v);
            start_date = (TextView) v.findViewById(R.id.txt_start_date);
            treatment_type = (TextView) v.findViewById(R.id.txt_regimen);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_current_art, parent, false);
        vh = new CurrentArtAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CurrentArt obj = items.get(position);
        if (holder instanceof CurrentArtAdapter.OriginalViewHolder) {
            CurrentArtAdapter.OriginalViewHolder view = (CurrentArtAdapter.OriginalViewHolder) holder;

            view.treatment_type.setText(obj.getRegiment());
            view.start_date.setText("Start date: "+obj.getDate_started());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

