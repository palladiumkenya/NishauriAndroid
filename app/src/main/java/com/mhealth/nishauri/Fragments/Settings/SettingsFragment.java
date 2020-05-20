package com.mhealth.nishauri.Fragments.Settings;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mhealth.nishauri.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SettingsFragment extends Fragment {

    @BindView(R.id.faqs)
    LinearLayout faqs;

    @BindView(R.id.language)
    LinearLayout language;

    @BindView(R.id.about_app)
    LinearLayout about_app;



    private Unbinder unbinder;
    private View root;
    private Context context;

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
        root = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, root);

        faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SettingsFragment.this).navigate(R.id.nav_faqs);
            }
        });


        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SettingsFragment.this).navigate(R.id.nav_language);
            }
        });

        about_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SettingsFragment.this).navigate(R.id.nav_about_app);
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
