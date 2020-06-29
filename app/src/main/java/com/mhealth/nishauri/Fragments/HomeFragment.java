package com.mhealth.nishauri.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.Models.Dependant;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.adapters.DependantHomeAdapter;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mhealth.nishauri.utils.AppController.TAG;


public class HomeFragment extends Fragment {

    @BindView(R.id.shimmer_my_container)
    ShimmerFrameLayout shimmer_my_container;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.btn_add_dependant)
    Button btn_add_dependant;

    @BindView(R.id.txt_name)
    TextView txt_name;

    @BindView(R.id.msisdn)
    TextView txt_msisdn;





    private Unbinder unbinder;
    private View root;
    private Context context;

    private User loggedInUser;
    private DependantHomeAdapter mAdapter;
    private ArrayList<Dependant> dependantArrayList;


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
        root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, root);



        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);

        dependantArrayList = new ArrayList<>();
        mAdapter = new DependantHomeAdapter(context, dependantArrayList);


        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        recyclerView.setAdapter(mAdapter);

        loadCurrentUser();

        loadDependants();





        btn_add_dependant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.nav_add_dependant);

            }
        });

        return root;
    }


    private void loadCurrentUser(){

        String auth_token = loggedInUser.getAuth_token();


        AndroidNetworking.get(Constants.ENDPOINT+Constants.CURRENT_USER)
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

                        try {


                            JSONArray myArray = response.getJSONArray("data");

                            if (myArray.length() > 0){


                                for (int i = 0; i < myArray.length(); i++) {

                                    JSONObject item = (JSONObject) myArray.get(i);


                                    String first_name = item.has("first_name") ? item.getString("first_name") : "";
                                    String last_name = item.has("last_name") ? item.getString("last_name") : "";
                                    String msisdn = item.has("msisdn") ? item.getString("msisdn") : "";


                                    txt_name.setText(first_name + " " + last_name);
                                    txt_msisdn.setText(msisdn);

                                }

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, error.getErrorBody());

                        Snackbar.make(root.findViewById(R.id.frag_home), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });

    }

    private void loadDependants() {

        String auth_token = loggedInUser.getAuth_token();


        AndroidNetworking.get(Constants.ENDPOINT+Constants.DEPENTANTS)
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

                        dependantArrayList.clear();

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
                                    String first_name = item.has("first_name") ? item.getString("first_name") : "";
                                    String surname = item.has("surname") ? item.getString("surname") : "";
                                    String heiNumber = item.has("heiNumber") ? item.getString("heiNumber") : "";
                                    String dob = item.has("dob") ? item.getString("dob") : "";
                                    String approved = item.has("approved") ? item.getString("approved") : "";
                                    int  user = item.has("user") ? item.getInt("user") : 0;


                                    Dependant newDependant = new Dependant(id,first_name,surname,heiNumber,dob,approved,user);

                                    dependantArrayList.add(newDependant);
                                    mAdapter.notifyDataSetChanged();

                                }

                            }else {
                                //not data found
                                Snackbar.make(root.findViewById(R.id.frag_home), "No dependants found" , Snackbar.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, error.getErrorBody());

                        Snackbar.make(root.findViewById(R.id.frag_home), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });
    }


}
