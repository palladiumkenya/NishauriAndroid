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

    @BindView(R.id.survey_card)
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

        faq_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(ChatFragment.this).navigate(R.id.nav_faqs);

            }
        });

        survey_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(ChatFragment.this).navigate(R.id.nav_client_survey);
            }
        });

        chat_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // NavHostFragment.findNavController(ChatFragment.this).navigate(R.id.chatInterface);

                /*Intent intent = new Intent(context, ChatInterface.class);
                startActivity(intent);*/

                Snackbar.make(root.findViewById(R.id.frag_chat), "Chat Bot Coming Soon", Snackbar.LENGTH_LONG).show();



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
