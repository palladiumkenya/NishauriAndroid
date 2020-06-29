package com.mhealth.nishauri.Fragments.Dependants;

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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fxn.stash.Stash;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import com.mhealth.nishauri.Models.Dependant;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.adapters.DependantAdapter;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mhealth.nishauri.utils.AppController.TAG;

public class DependantsFragment extends Fragment {


    private Unbinder unbinder;
    private View root;
    private Context context;

    private User loggedInUser;
    private DependantAdapter mAdapter;
    private ArrayList<Dependant> dependantArrayList;



    @BindView(R.id.shimmer_my_container)
    ShimmerFrameLayout shimmer_my_container;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.fab_dependant)
    ExtendedFloatingActionButton fab_dependant;

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
        root= inflater.inflate(R.layout.fragment_dependants, container, false);
        unbinder = ButterKnife.bind(this, root);


        fab_dependant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(DependantsFragment.this).navigate(R.id.nav_add_dependant);
            }
        });

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);


        dependantArrayList = new ArrayList<>();
        mAdapter = new DependantAdapter(context, dependantArrayList);


        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        recyclerView.setAdapter(mAdapter);

        loadDependants();


        mAdapter.setOnItemClickListener(new DependantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Dependant clickedItem = dependantArrayList.get(position);

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
                                Snackbar.make(root.findViewById(R.id.frag_dependant), "No dependants found" , Snackbar.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, error.getErrorBody());

                        Snackbar.make(root.findViewById(R.id.frag_dependant), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });
    }
}
