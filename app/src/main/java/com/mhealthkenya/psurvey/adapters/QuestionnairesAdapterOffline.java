package com.mhealthkenya.psurvey.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.activities.ResponseData;
import com.mhealthkenya.psurvey.depedancies.Tools;
import com.mhealthkenya.psurvey.depedancies.ViewAnimation;
import com.mhealthkenya.psurvey.models.ActiveSurveys;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;

import java.util.ArrayList;
import java.util.List;

public class QuestionnairesAdapterOffline extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<QuestionnaireEntity> questionnaireEntities= new ArrayList<>();
    public Context context;

    public OnItemClickListener onItemClickListener;

    public  interface OnItemClickListener{
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener Listener){
        this. onItemClickListener = Listener;

    }

    public  QuestionnairesAdapterOffline (Context context){

        this.context = context;


    }

    public void setUser(List<QuestionnaireEntity> questionnaireEntities){
        this. questionnaireEntities =  questionnaireEntities;
        //notifyDataSetChanged();
        //notifyAll();
        notifyDataSetChanged();
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

            surveyDescription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position =getAdapterPosition();
                    if (onItemClickListener !=null && position != RecyclerView.NO_POSITION);
                   // onItemClickListener.onItemClick(questionnaireEntities.get(position));
                    onItemClickListener.onItemClick(position);
                }
            });


        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offline_questionnaire_adapter, parent, false);
        vh = new QuestionnairesAdapterOffline.OriginalViewHolder(v);
        return vh;
        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        QuestionnaireEntity obj = questionnaireEntities.get(position);
        if (holder instanceof QuestionnairesAdapterOffline.OriginalViewHolder) {
            QuestionnairesAdapterOffline.OriginalViewHolder view = (QuestionnairesAdapterOffline.OriginalViewHolder) holder;

            view.surveyTitle.setText(obj.getName());
            view.surveyDescription.setText(obj.getDescription());

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //This  is onclick implementation
                    if (onItemClickListener != null) {
                        //Toast.makeText(context,items.get(position).getLogo(), Toast.LENGTH_LONG).show();
                        onItemClickListener.onItemClick(position);
                    }
                }
            });


            view.bt_expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* boolean show = toggleLayoutExpand(!obj.expanded, v, view.lyt_expand);
                    questionnaireEntities.get(position).expanded = show;*/
                    Intent intent = new Intent(context, ResponseData.class);
                    intent.putExtra("Quetionnaire_ID", obj.getId());
                    context.startActivity(intent);
                    Toast.makeText(context, "ID is"+obj.getId(), Toast.LENGTH_LONG).show();
                }
            });


           /* if(obj.expanded){
                view.lyt_expand.setVisibility(View.VISIBLE);
            } else {
                view.lyt_expand.setVisibility(View.GONE);
            }
            Tools.toggleArrow(obj.expanded, view.bt_expand, false);


        }*/
            // }

    /*private boolean toggleLayoutExpand(boolean show, View view, View lyt_expand) {
        Tools.toggleArrow(show, view);
        if (show) {
            ViewAnimation.expand(lyt_expand);
        } else {
            ViewAnimation.collapse(lyt_expand);
        }
        return show;

    }*/
        }}

    @Override
    public int getItemCount() {
        return questionnaireEntities.size();
    }

    }