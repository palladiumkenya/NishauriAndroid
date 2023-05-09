package com.mhealth.nishauri.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mhealth.nishauri.Models.ViralLoad;
import com.mhealth.nishauri.R;
import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.ArrowPositionRules;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.skydoves.balloon.BalloonSizeSpec;

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
        public TextView result_date, sup, unsup;
        public TextView result;
        public ImageButton bt_expand;



        public OriginalViewHolder(View v) {
            super(v);
            result_date = (TextView) v.findViewById(R.id.txt_result_date);
            result = (TextView) v.findViewById(R.id.txt_viral_load_result);
            unsup = (TextView) v.findViewById(R.id.txt_viral_load1);
            sup = (TextView) v.findViewById(R.id.txt_viral_load);
            bt_expand=(ImageButton) v.findViewById(R.id.bt_expand2);



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

            view.result_date.setText(obj.getDate());
            view.result.setText(obj.getResult());
            view.sup.setText(obj.getStatus());

           /* if (obj.getResult_content() instanceof String ){
                view.result.setTextColor(Color.parseColor("#F32013"));
            }*/
            /*if(Character.isDigit(Integer.parseInt(obj.getResult_content()))){
                view.result.setTextColor(Color.parseColor("#F32013"));
            }*/

          /*  try {
                //int x= Integer.parseInt(obj.getResult_content());
                //if (x!= String)
                String sample = obj.getResult();
                char[] chars = sample.toCharArray();
                StringBuilder sb = new StringBuilder();
                for(char c : chars){
                    if(Character.isDigit(c)){
                       // sb.append(c);
                        view.result.setTextColor(Color.parseColor("#F32013"));
                        view.unsup.setVisibility(View.VISIBLE);

                        view.bt_expand.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Toast.makeText(context, "VL", Toast.LENGTH_SHORT).show();

                                Balloon balloon = new Balloon.Builder(context).
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
                                        .setText("This could mean the beginning of treatment failure, Kindly visit your doctor/healthcare provider as soon as possible!")
                                        .setTextIsHtml(true)
                                        .setIconDrawable(ContextCompat.getDrawable(context, R.drawable.ic_profile))
                                        .setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))

                                        .setBalloonAnimation(BalloonAnimation.FADE)

                                        .setMarginLeft(14) // sets the left margin on the balloon.
                                        .setMarginRight(14)


                                        .build();
                                balloon.showAlignBottom(view);
                                balloon.show(view);
                            }
                        });
                    }
                    else{
                        view.sup.setVisibility(View.VISIBLE);
                        view.bt_expand.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                Balloon balloon = new Balloon.Builder(context).
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
                                        .setText("This means you are adhering to your treatment well, " +
                                                "Continue taking your medication as advised by your doctor/healthcare provider")
                                        .setTextIsHtml(true)
                                        .setIconDrawable(ContextCompat.getDrawable(context, R.drawable.ic_profile))
                                        .setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))

                                        .setBalloonAnimation(BalloonAnimation.FADE)

                                        .setMarginLeft(14) // sets the left margin on the balloon.
                                        .setMarginRight(14)


                                        .build();
                                balloon.showAlignBottom(view);
                                balloon.show(view);


                            }
                        });

                    }
                }


            }catch (NumberFormatException e){
                e.getStackTrace();

            }*/

        }


    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}