package com.mhealth.nishauri.Fragments.Treatment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.Models.CurrentTreatment;
import com.mhealth.nishauri.Models.Dependant;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.adapters.CurrentTreatmentAdapter;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mhealth.nishauri.utils.AppController.TAG;


public class CurrentArtFragment extends Fragment {


    private Unbinder unbinder;
    private View root;
    private Context context;

    private User loggedInUser;
    private CurrentTreatmentAdapter mAdapter;
    private ArrayList<CurrentTreatment> currentTreatmentArrayList;



    @BindView(R.id.shimmer_my_container)
    ShimmerFrameLayout shimmer_my_container;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.no_treatment_lyt)
    LinearLayout no_treatment_lyt;

    @BindView(R.id.error_lyt)
    LinearLayout error_lyt;

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
        root =inflater.inflate(R.layout.fragment_current_art, container, false);
        unbinder = ButterKnife.bind(this, root);

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);


        currentTreatmentArrayList = new ArrayList<>();
        mAdapter = new CurrentTreatmentAdapter(context, currentTreatmentArrayList);


        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        recyclerView.setAdapter(mAdapter);

        loadCurrentTreatments();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onResume() {
        super.onResume();
        shimmer_my_container.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        shimmer_my_container.stopShimmerAnimation();
        super.onPause();
    }

    private void loadCurrentTreatments() {

        String auth_token = loggedInUser.getAuth_token();


        AndroidNetworking.get(Constants.ENDPOINT+Constants.CURENT_TREATMENTS)
                .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.e(TAG, response.toString());

                        currentTreatmentArrayList.clear();

                        if (recyclerView!=null)
                            recyclerView.setVisibility(View.VISIBLE);

                        if (shimmer_my_container!=null){
                            shimmer_my_container.stopShimmerAnimation();
                            shimmer_my_container.setVisibility(View.GONE);
                        }

                        try {

                            if (response.has("treatment")){

                                String treatment = response.has("treatment") ? response.getString("treatment") : "";

                                CurrentTreatment newTreatment = new CurrentTreatment(treatment);

                                currentTreatmentArrayList.add(newTreatment);
                                mAdapter.notifyDataSetChanged();
                            }
                            else {
                                no_treatment_lyt.setVisibility(View.VISIBLE);
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error

                        if (recyclerView!=null)
                            recyclerView.setVisibility(View.VISIBLE);

                        if (shimmer_my_container!=null){
                            shimmer_my_container.stopShimmerAnimation();
                            shimmer_my_container.setVisibility(View.GONE);
                        }

                        error_lyt.setVisibility(View.VISIBLE);

                        Log.e(TAG, error.getErrorBody());

                        Snackbar.make(root.findViewById(R.id.frag_previous_appointment), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });
    }
}
