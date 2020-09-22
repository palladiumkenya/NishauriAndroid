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
import com.mhealthkenya.psurvey.models.Questionnaires;

import java.util.ArrayList;
import java.util.List;

public class questionnairesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Questionnaires> items = new ArrayList<>();

    private Context context;
    private questionnairesAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(questionnairesAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public questionnairesAdapter(Context context, List<Questionnaires> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView questionnaireTitle;
        public TextView questionnaireDescription;
        public TextView questionnaireStatus;
        public ImageButton bt_expand;
        public View lyt_expand;
        public View lyt_parent;



        public OriginalViewHolder(View v) {
            super(v);
            questionnaireTitle = (TextView) v.findViewById(R.id.tv_questionnaire_title);
            questionnaireStatus = (TextView) v.findViewById(R.id.tv_questionnaire_status);
            questionnaireDescription = (TextView) v.findViewById(R.id.tv_questionnaire_description);
            bt_expand = (ImageButton) v.findViewById(R.id.bt_expand);
            lyt_expand = (View) v.findViewById(R.id.lyt_expand);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questionnaire, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Questionnaires obj = items.get(position);
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            view.questionnaireTitle.setText(obj.getName());
            view.questionnaireDescription.setText(obj.getDescription());

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

            if (obj.getIs_active().equals("true")){
                view.questionnaireStatus.setText("Status: Active");
            }
            else {
                view.questionnaireStatus.setText("Status: Inactive");
            }


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
