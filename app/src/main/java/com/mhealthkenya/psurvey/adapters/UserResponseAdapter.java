package com.mhealthkenya.psurvey.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.activities.ResponseData;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;
import com.mhealthkenya.psurvey.models.UserResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class UserResponseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<UserResponseEntity> userResponseEntities= new ArrayList<>();
    public Context context;

    public UserResponseAdapter.OnItemClickListener onItemClickListener;

    public  interface OnItemClickListener{
        void onItemClick(int position);

    }

    public void setOnItemClickListener(UserResponseAdapter.OnItemClickListener Listener){
        this. onItemClickListener = Listener;

    }

    public  UserResponseAdapter (Context context){

        this.context = context;


    }

    public void setUser(List<UserResponseEntity> userResponseEntities){
        this.userResponseEntities = userResponseEntities;
        //notifyDataSetChanged();
        //notifyAll();
        notifyDataSetChanged();
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView surveyID;
        public TextView queryID;
        public TextView btnOpen;
        public Button bt_expand;
        public View lyt_expand;
        public View lyt_parent;



        public OriginalViewHolder(View v) {
            super(v);
            surveyID = (TextView) v.findViewById(R.id.tv_survey_id);
            queryID = (TextView) v.findViewById(R.id.tv_query_id);
            btnOpen = (TextView) v.findViewById(R.id.tv_open_id);
            bt_expand = (Button) v.findViewById(R.id.tv_btn);


            bt_expand.setOnClickListener(new View.OnClickListener() {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_response_item, parent, false);
        vh = new UserResponseAdapter.OriginalViewHolder(v);
        return vh;
       // return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UserResponseEntity obj = userResponseEntities.get(position);
        if (holder instanceof QuestionnairesAdapterOffline.OriginalViewHolder) {
            UserResponseAdapter.OriginalViewHolder view = (UserResponseAdapter.OriginalViewHolder) holder;

            view.surveyID.setText(obj.getQuestionnaireId());
            view.queryID.setText(obj.getQuestionId());
            view.btnOpen.setText(obj.getOption());

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
                   // Toast.makeText(context, "ID is" + obj.getId(), Toast.LENGTH_LONG).show();
                }
            });
}}

    @Override
    public int getItemCount() {
        return userResponseEntities.size();
    }
}
