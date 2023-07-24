package com.mhealth.nishauri.Fragments.Chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.Activities.ART_Activity;
import com.mhealth.nishauri.Activities.Auth.LoginPsurvey;
import com.mhealth.nishauri.Activities.ChatInterface;
import com.mhealth.nishauri.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ChatFragment extends Fragment {


    private Unbinder unbinder;
    private View root;
    private Context context;

    @BindView(R.id.faq_card)
    CardView faq_card;

    @BindView(R.id.art_card)
    CardView art_card;

    @BindView(R.id.survey_card1)
    CardView survey_card;

    @BindView(R.id.chat_card)
    CardView chat_card;

    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        this.context = ctx;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, root);




        art_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, ART_Activity.class);
                context.startActivity(intent);
            }
        });

        faq_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(ChatFragment.this).navigate(R.id.nav_faqs);

            }
        });

        survey_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // NavHostFragment.findNavController(ChatFragment.this).navigate(R.id.nav_client_survey);

                Intent intent = new Intent(context, LoginPsurvey.class);
                context.startActivity(intent);


            }
        });

        chat_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ChatFragment.this).navigate(R.id.nav_interface);

              //  Snackbar.make(root.findViewById(R.id.frag_chat), "Chat Bot Coming Soon", Snackbar.LENGTH_LONG).show();



            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
