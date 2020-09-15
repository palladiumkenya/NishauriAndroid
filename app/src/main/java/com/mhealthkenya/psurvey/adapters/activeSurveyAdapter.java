package com.mhealthkenya.psurvey.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.models.ActiveSurveys;

import java.util.ArrayList;
import java.util.List;

public class activeSurveyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ActiveSurveys> items = new ArrayList<>();

    private Context context;
    private activeSurveyAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(activeSurveyAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public activeSurveyAdapter(Context context, List<ActiveSurveys> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView surveyTitle;
        public TextView surveyDescription;



        public OriginalViewHolder(View v) {
            super(v);
            surveyTitle = (TextView) v.findViewById(R.id.tv_survey_title);
            surveyDescription = (TextView) v.findViewById(R.id.tv_survey_description);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_active_survey, parent, false);
        vh = new activeSurveyAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ActiveSurveys obj = items.get(position);
        if (holder instanceof questionnairesAdapter.OriginalViewHolder) {
            activeSurveyAdapter.OriginalViewHolder view = (activeSurveyAdapter.OriginalViewHolder) holder;

            view.surveyTitle.setText(obj.getSurveyTitle());
            view.surveyDescription.setText(obj.getSurveyDescription());


        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
