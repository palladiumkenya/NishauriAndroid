package com.mhealthkenya.psurvey.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.depedancies.Tools;
import com.mhealthkenya.psurvey.depedancies.ViewAnimation;
import com.mhealthkenya.psurvey.models.ActiveSurveys;

import java.util.ArrayList;
import java.util.List;

public class activeSurveyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ActiveSurveys> items = new ArrayList<>();

    private Context context;
    private OnItemClickListener onItemClickListener;


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
        public ImageButton bt_expand;
        public View lyt_expand;
        public View lyt_parent;



        public OriginalViewHolder(View v) {
            super(v);
            surveyTitle = (TextView) v.findViewById(R.id.tv_survey_title);
            surveyDescription = (TextView) v.findViewById(R.id.tv_survey_description);
            bt_expand = (ImageButton) v.findViewById(R.id.bt_expand);
            lyt_expand = (View) v.findViewById(R.id.lyt_expand);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);

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
        if (holder instanceof activeSurveyAdapter.OriginalViewHolder) {
            activeSurveyAdapter.OriginalViewHolder view = (activeSurveyAdapter.OriginalViewHolder) holder;

            view.surveyTitle.setText(obj.getName());
            view.surveyDescription.setText(obj.getDescription());

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        //Toast.makeText(context,items.get(position).getLogo(), Toast.LENGTH_LONG).show();
                        onItemClickListener.onItemClick(position);
                    }
                }
            });


            view.bt_expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean show = toggleLayoutExpand(!obj.expanded, v, view.lyt_expand);
                    items.get(position).expanded = show;
                }
            });


            if(obj.expanded){
                view.lyt_expand.setVisibility(View.VISIBLE);
            } else {
                view.lyt_expand.setVisibility(View.GONE);
            }
            Tools.toggleArrow(obj.expanded, view.bt_expand, false);


        }
    }

    private boolean toggleLayoutExpand(boolean show, View view, View lyt_expand) {
        Tools.toggleArrow(show, view);
        if (show) {
            ViewAnimation.expand(lyt_expand);
        } else {
            ViewAnimation.collapse(lyt_expand);
        }
        return show;
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
