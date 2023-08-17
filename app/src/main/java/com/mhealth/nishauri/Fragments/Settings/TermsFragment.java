package com.mhealth.nishauri.Fragments.Settings;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.mhealth.nishauri.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class TermsFragment extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;

    @BindView(R.id.termsWeb)
    WebView termsWeb1;

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
        root = inflater.inflate(R.layout.fragment_terms, container, false);
        unbinder = ButterKnife.bind(this, root);

        WebSettings settings = termsWeb1.getSettings();
        settings.setJavaScriptEnabled(true);
        //Toast.makeText(InformedActivity.this, "inform", Toast.LENGTH_SHORT).show();
        termsWeb1.loadUrl("https://deployer.kenyahmis.org/KeHMIS-Policies/Terms%20and%20Conditions%20for%20Collecting%20Health%20Data.html");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}