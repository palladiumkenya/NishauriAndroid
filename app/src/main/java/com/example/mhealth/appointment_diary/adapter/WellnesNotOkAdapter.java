package com.example.mhealth.appointment_diary.adapter;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.models.WellnessNotOkModel;

import java.util.List;

public class WellnesNotOkAdapter extends RecyclerView.Adapter<WellnesNotOkAdapter.MyviewHolder> {

    List<WellnessNotOkModel> mylist;
    Context ctx;

    public WellnesNotOkAdapter(Context ctx, List<WellnessNotOkModel> mylist) {
        this.mylist = mylist;
        this.ctx = ctx;
    }

    @Override
    public WellnesNotOkAdapter.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_wellnes_row, parent, false);

        return new MyviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {
        try{

            WellnessNotOkModel itm = mylist.get(position);

            holder.ccnumberT.setText(itm.getCcno());
            holder.fnameT.setText(itm.getFname());
            holder.messageT.setText(itm.getMsg());
            holder.phoneT.setText(itm.getPhoneno());


        }
        catch(Exception e){


        }



    }


    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{

        public TextView ccnumberT,fnameT,phoneT,messageT;

        public CardView ccd;


        public MyviewHolder(View itemView) {
            super(itemView);

             ccnumberT=(TextView) itemView.findViewById(R.id.welnesccnumber);
            fnameT=(TextView) itemView.findViewById(R.id.welnesfname);
            phoneT=(TextView) itemView.findViewById(R.id.welnespnumber);
            messageT=(TextView) itemView.findViewById(R.id.welnesmessage);


            ccd =(CardView) itemView.findViewById(R.id.taxis_card_view);
        }
    }
}

