package com.mhealth.nishauri.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mhealth.nishauri.Models.ViralLoad;
import com.mhealth.nishauri.R;

import java.util.ArrayList;
import java.util.List;

public class ViralLoadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ViralLoad> items = new ArrayList<>();

    private Context context;
    private ViralLoadAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(ViralLoadAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ViralLoadAdapter(Context context, List<ViralLoad> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView result_date;
        public TextView result;



        public OriginalViewHolder(View v) {
            super(v);
            result_date = (TextView) v.findViewById(R.id.txt_result_date);
            result = (TextView) v.findViewById(R.id.txt_viral_load_result);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viral_load_results, parent, false);
        vh = new ViralLoadAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViralLoad obj = items.get(position);
        if (holder instanceof ViralLoadAdapter.OriginalViewHolder) {
            ViralLoadAdapter.OriginalViewHolder view = (ViralLoadAdapter.OriginalViewHolder) holder;

            view.result_date.setText(obj.getDate_collected());
            view.result.setText(obj.getResult_content() + " copies/mL");

        }


    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}