package com.mhealth.nishauri.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mhealth.nishauri.Models.EID;
import com.mhealth.nishauri.R;
/*import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.ArrowPositionRules;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.skydoves.balloon.BalloonSizeSpec;*/

import java.util.ArrayList;
import java.util.List;

public class EIDAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<EID> items = new ArrayList<>();

    private Context context;
    private EIDAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(EIDAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public EIDAdapter(Context context, List<EID> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView result_date;
        public TextView result;
        public TextView dependant;
        public TextView result_guidline;
        public ImageButton bt_expand;



        public OriginalViewHolder(View v) {
            super(v);
            result_date = (TextView) v.findViewById(R.id.txt_result_date);
            result = (TextView) v.findViewById(R.id.txt_viral_load_result);
            dependant = (TextView) v.findViewById(R.id.txt_dependant);
            result_guidline = (TextView) v.findViewById(R.id.txt_result_guideline);
            bt_expand=(ImageButton) v.findViewById(R.id.bt_expand3);


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eid_results, parent, false);
        vh = new EIDAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        EID obj = items.get(position);
        if (holder instanceof EIDAdapter.OriginalViewHolder) {
            EIDAdapter.OriginalViewHolder view = (EIDAdapter.OriginalViewHolder) holder;

            view.result_date.setText(obj.getDate_collected());
            view.result.setText(obj.getResult_content());
            view.dependant.setText(obj.getDependant_name());

            if (obj.getResult_content().equals("Positive")){

                view.result.setTextColor(Color.parseColor("#F32013"));
                view.bt_expand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      /*  Balloon balloon = new Balloon.Builder(context).
                                setArrowSize(10)
                                .setArrowOrientation(ArrowOrientation.TOP)
                                .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
                                .setArrowPosition(0.5f)
                                //.setWidth(220) // sets 220dp width size.
                                .setWidth(BalloonSizeSpec.WRAP)

                                .setHeight(65)
                                .setTextSize(15f)
                                .setCornerRadius(4f)
                                .setAlpha(0.9f)
                                .setText("This means your baby needs quick clinical intervention, Kindly visit your doctor/healthcare provider as soon as possible")
                                .setTextIsHtml(true)
                                .setIconDrawable(ContextCompat.getDrawable(context, R.drawable.ic_profile))
                                .setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))

                                .setBalloonAnimation(BalloonAnimation.FADE)

                                .setMarginLeft(14) // sets the left margin on the balloon.
                                .setMarginRight(14)


                                .build();
                        balloon.showAlignBottom(view);
                        balloon.show(view);*/

                    }
                });

                //view.result_guidline.setVisibility(View.VISIBLE);

            }

        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
