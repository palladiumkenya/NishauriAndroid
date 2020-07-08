package com.mhealth.nishauri.Fragments.Lab;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.Models.EID;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.Models.ViralLoad;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.adapters.EIDAdapter;
import com.mhealth.nishauri.adapters.ViralLoadAdapter;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mhealth.nishauri.utils.AppController.TAG;


public class EidResultsFragment extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;

    private User loggedInUser;
    private EIDAdapter mAdapter;
    private ArrayList<EID> eidArrayList;

    private ProgressDialog pDialog;



    @BindView(R.id.shimmer_my_container)
    ShimmerFrameLayout shimmer_my_container;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.request_lyt)
    LinearLayout request_lyt;

    @BindView(R.id.no_result_lyt)
    LinearLayout no_result_lyt;

    @BindView(R.id.error_lyt)
    LinearLayout error_lyt;

    @BindView(R.id.fab_request_eid)
    ExtendedFloatingActionButton fab_request_eid_results;

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
        root = inflater.inflate(R.layout.fragment_eid_results, container, false);
        unbinder = ButterKnife.bind(this, root);

        pDialog = new ProgressDialog(context);
        pDialog.setTitle("Loading...");
        pDialog.setMessage("Getting Results...");
        pDialog.setCancelable(true);


        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);


        eidArrayList = new ArrayList<>();
        mAdapter = new EIDAdapter(context, eidArrayList);


        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        recyclerView.setAdapter(mAdapter);

        fab_request_eid_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request_lyt.setVisibility(View.GONE);
                pDialog.show();
                loadEid();
            }
        });

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

    private void loadEid() {

        String auth_token = loggedInUser.getAuth_token();


        AndroidNetworking.get(Constants.ENDPOINT+Constants.EID)
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

                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.hide();
                            pDialog.cancel();
                        }

                       eidArrayList.clear();

                        if (recyclerView!=null)
                            recyclerView.setVisibility(View.VISIBLE);

                        if (shimmer_my_container!=null){
                            shimmer_my_container.stopShimmerAnimation();
                            shimmer_my_container.setVisibility(View.GONE);
                        }

                        try {

                            JSONArray myArray = response.getJSONArray("data");

                            if (myArray.length() > 0){


                                for (int i = 0; i < myArray.length(); i++) {

                                    JSONObject item = (JSONObject) myArray.get(i);


                                    int  id = item.has("id") ? item.getInt("id") : 0;
                                    String r_id = item.has("r_id") ? item.getString("r_id") : "";
                                    String result_type = item.has("result_type") ? item.getString("result_type") : "";
                                    String result_content = item.has("result_content") ? item.getString("result_content") : "";
                                    String date_collected = item.has("date_collected") ? item.getString("date_collected") : "";
                                    String lab_name = item.has("lab_name") ? item.getString("lab_name") : "";
                                    String dependant_name = item.has("dependant") ? item.getString("dependant") : "";



                                    EID newResult = new EID(id,r_id,result_type,result_content,date_collected,lab_name,dependant_name);

                                    eidArrayList.add(newResult);
                                    mAdapter.notifyDataSetChanged();

                                }

                            }else {
                                //not data found
                                if (pDialog != null && pDialog.isShowing()) {
                                    pDialog.hide();
                                    pDialog.cancel();
                                }

                                no_result_lyt.setVisibility(View.VISIBLE);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (pDialog != null && pDialog.isShowing()) {
                                pDialog.hide();
                                pDialog.cancel();
                            }
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.hide();
                            pDialog.cancel();
                        }

                        error_lyt.setVisibility(View.VISIBLE);

                        Log.e(TAG, error.getErrorBody());


                        Snackbar.make(root.findViewById(R.id.frag_eid_results), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });
    }
}
