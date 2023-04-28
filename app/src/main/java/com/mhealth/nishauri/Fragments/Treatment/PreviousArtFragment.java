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
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.Models.PreviousArt;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.adapters.PreviousArtAdapter;
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


public class PreviousArtFragment extends Fragment {


    private Unbinder unbinder;
    private View root;
    private Context context;

    String z;

    private User loggedInUser;
    private PreviousArtAdapter mAdapter;
    private ArrayList<PreviousArt> previousArtArrayList;

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
        root = inflater.inflate(R.layout.fragment_previous_art, container, false);
        unbinder = ButterKnife.bind(this, root);

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);


        previousArtArrayList = new ArrayList<>();
        mAdapter = new PreviousArtAdapter(context, previousArtArrayList);


        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        recyclerView.setAdapter(mAdapter);

        loadPreviousArt();

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

    private void loadPreviousArt() {

        String auth_token = loggedInUser.getAuth_token();

        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }


        AndroidNetworking.get(z+Constants.PREVIOUS_REGIMEN)
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

                        previousArtArrayList.clear();

                        if (recyclerView!=null)
                            recyclerView.setVisibility(View.VISIBLE);

                        if (shimmer_my_container!=null){
                            shimmer_my_container.stopShimmerAnimation();
                            shimmer_my_container.setVisibility(View.GONE);
                        }

                        try {

                            boolean  status = response.has("success") && response.getBoolean("success");
                            String  message = response.has("data") ? response.getString("data") : "" ;
                            String  errors = response.has("errors") ? response.getString("errors") : "" ;


                            if (status){

                                JSONArray myArray = response.getJSONArray("previous regiments");

                                if (myArray.length() > 0){


                                    for (int i = 0; i < myArray.length(); i++) {

                                        JSONObject item = (JSONObject) myArray.get(i);


                                        int  id = item.has("id") ? item.getInt("id") : Integer.parseInt("");
                                        String Regiment = item.has("Regiment") ? item.getString("Regiment") : "";
                                        String date_started = item.has("date_started") ? item.getString("date_started") : "";
                                        String user = item.has("user") ? item.getString("user") : "";
                                        String end_date = item.has("end_date") ? item.getString("end_date") : "";

                                        PreviousArt newPArt = new PreviousArt(id,Regiment,date_started,user,end_date);

                                        previousArtArrayList.add(newPArt);
                                        mAdapter.notifyDataSetChanged();


                                    }

                                }

                            }
                            else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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



//                        Log.e(TAG, error.getErrorBody());

                        if (error.getErrorBody().contains("No treatments found")){
                            no_treatment_lyt.setVisibility(View.VISIBLE);
                        }
                        else{

                            error_lyt.setVisibility(View.VISIBLE);
                            Snackbar.make(root.findViewById(R.id.frag_previous_appointment), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();


                        }


                    }
                });
    }
}
