package com.mhealth.nishauri.Fragments.Profile;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.mhealth.nishauri.Models.Dependant;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.adapters.DependantHomeAdapter;
import com.mhealth.nishauri.adapters.EditDependantAdapter;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mhealth.nishauri.utils.AppController.TAG;


public class UpdateProfileFragment extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;


    private User loggedInUser;
    private EditDependantAdapter mAdapter;
    private ArrayList<Dependant> dependantArrayList = new ArrayList<>();
    private Dependant newDependant;

    @BindView(R.id.txt_names)
    MaterialTextView txt_names;

    @BindView(R.id.txt_ccc_number)
    MaterialTextView txt_ccc_number;

    @BindView(R.id.txt_initial_facility)
    MaterialTextView txt_initial_facility;

    @BindView(R.id.txt_current_facility)
    MaterialTextView txt_current_facility;

    @BindView(R.id.btn_update_user)
    Button btn_user;

    @BindView(R.id.shimmer_my_container)
    ShimmerFrameLayout shimmer_my_container;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.no_dependant_lyt)
    LinearLayout no_dependant_lyt;

    @BindView(R.id.error_lyt)
    LinearLayout error_lyt;

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
        root = inflater.inflate(R.layout.fragment_update_profile, container, false);
        unbinder = ButterKnife.bind(this, root);

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);

        dependantArrayList = new ArrayList<>();
        mAdapter = new EditDependantAdapter(context, dependantArrayList);


        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        recyclerView.setAdapter(mAdapter);



        loadCurrentUser();


        /*Edit user details here...*/
        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(UpdateProfileFragment.this).navigate(R.id.nav_update_user);


            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
//                        Log.e(TAG, response.toString());

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

                                    String CCCNo = item.has("CCCNo") ? item.getString("CCCNo") : "";
                                    String first_name = item.has("first_name") ? item.getString("first_name") : "";
                                    String last_name = item.has("last_name") ? item.getString("last_name") : "";
                                    String current_facility = item.has("current_facility") ? item.getString("current_facility") : "";
                                    String initial_facility = item.has("initial_facility") ? item.getString("initial_facility") : "";

                                    JSONArray dependants = item.has("dependants") ? item.getJSONArray("dependants"): null;

                                    if (dependants.length() > 0) {


                                        for (int a = 0; a < dependants.length(); a++) {

                                            JSONObject dependant = (JSONObject) dependants.get(a);


                                            String moh_upi = item.has("moh_upi") ? item.getString("moh_upi") : "";
                                            String clinic_number = item.has("clinic_number") ? item.getString("clinic_number") : "";
                                            String dependant_name = item.has("dependant_name") ? item.getString("dependant_name") : "";
                                            int  dependant_age = item.has("dependant_age") ? item.getInt("dependant_age") : 0;



                                            Dependant newDependant = new Dependant(dependant_age, moh_upi, clinic_number, dependant_name);

                                            dependantArrayList.add(newDependant);
                                            mAdapter.notifyDataSetChanged();

                                        }
                                    } else {
                                        //not data found
                                        no_dependant_lyt.setVisibility(View.VISIBLE);
                                    }



                                    txt_names.setText(first_name + " "+ last_name);
                                    txt_ccc_number.setText(CCCNo);
                                    txt_initial_facility.setText(initial_facility);
                                    txt_current_facility.setText(current_facility);

                                }

                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
//                        Log.e(TAG, error.getErrorBody());

                        error_lyt.setVisibility(View.VISIBLE);

                        Snackbar.make(root.findViewById(R.id.frag_update_profile), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });

    }

}
