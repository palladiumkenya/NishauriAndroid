package com.mhealth.nishauri.Fragments.Lab;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fxn.stash.Stash;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.Activities.Auth.LoginActivity;
import com.mhealth.nishauri.Models.Dependant;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.Models.ViralLoad;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.adapters.DependantAdapter;
import com.mhealth.nishauri.adapters.ViralLoadAdapter;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.RECEIVER_VISIBLE_TO_INSTANT_APPS;
import static com.mhealth.nishauri.utils.AppController.TAG;


public class ViralLoadResultsFragment extends Fragment {
    private static final long INACTIVITY_THRESHOLD = 120000; // 2 minutes
    private static final long CHECK_INTERVAL = 120000; // 2 minutes
    //10000 10seconds

    private long lastInteractionTime = 0;
    private Handler inactivityHandler = new Handler();

    private Runnable inactivityRunnable = new Runnable() {
        @Override
        public void run() {

            long currentTime = System.currentTimeMillis();
            if (currentTime - lastInteractionTime >= INACTIVITY_THRESHOLD) {
                // Perform logout actions here
                //logoutUser();
                alertlogout();
            } else {
                // Schedule the next check
                inactivityHandler.postDelayed(this, CHECK_INTERVAL);
            }
            //performLogout();
            //alertlogout();


        }
    };



    private Unbinder unbinder;
    private View root;
    private View root2;
    private Context context;
    boolean x;
    JSONArray myArray;


    private User loggedInUser;
    private ViralLoadAdapter mAdapter;
    private ArrayList<ViralLoad> viralLoadArrayList;

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

    @BindView(R.id.fab_request_viral)
    ExtendedFloatingActionButton fab_request_viral_results;

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
        root = inflater.inflate(R.layout.fragment_viral_load_results, container, false);
        root2 = root.findViewById(R.id.frag_viral_load);


        unbinder = ButterKnife.bind(this, root);

        root2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                lastInteractionTime = System.currentTimeMillis();
                return false;
                //return true;
            }
        });

        pDialog = new ProgressDialog(context);
        pDialog.setTitle("Loading...");
        pDialog.setMessage("Getting Results...");
        pDialog.setCancelable(true);

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);


        viralLoadArrayList = new ArrayList<>();
        mAdapter = new ViralLoadAdapter(context, viralLoadArrayList);


        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ViralLoadAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ViralLoad clickedItem = viralLoadArrayList.get(position);

            }
        });

        fab_request_viral_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request_lyt.setVisibility(View.GONE);
               // pDialog.show();
                loadViralLoad();
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
        inactivityHandler.postDelayed(inactivityRunnable, CHECK_INTERVAL);

    }

    @Override
    public void onPause() {
        shimmer_my_container.stopShimmerAnimation();
        super.onPause();
        inactivityHandler.removeCallbacks(inactivityRunnable);
    }

    private void loadViralLoad() {

       // String auth_token = loggedInUser.getAuth_token();

        String auth_token = loggedInUser.getAuth_token();
        String urls ="?user_id="+auth_token;

        Log.d("ID", auth_token);

        //https://ushauriapi.nascop.org/nishauri/vl_results?user_id=MTAwMg==
      //  https://ushauriapi.nascop.org/nishauri/vl_results?user_id=Mg==
        //Constants.ENDPOINT+Constants.VIRALS_LOADNEW+urls


        AndroidNetworking.get(Constants.ENDPOINT+Constants.VIRALS_LOADNEW+urls)
               // .addHeaders("Authorization","Token "+ auth_token)
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
                        Log.e("success", response.toString());

                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.hide();
                            pDialog.cancel();
                        }



                        viralLoadArrayList.clear();

                        if (recyclerView!=null)
                            recyclerView.setVisibility(View.VISIBLE);

                        if (shimmer_my_container!=null){
                            shimmer_my_container.stopShimmerAnimation();
                            shimmer_my_container.setVisibility(View.GONE);
                        }

                        //myArray = response.getJSONArray("msg");


                            try {
                                 x = response.getBoolean("success");
                                 if (x) {


                                     myArray = response.getJSONArray("msg");
                                     if (myArray.length() > 0) {
                                         // JSONObject jsonObject1 = response.getJSONObject("msg");

                                         // if (myArray.length() > 0){
                                         // if (jsonObject1.length() > 0){


                                         for (int i = 0; i < myArray.length(); i++) {
                                             // for (int i = 0; i < jsonObject1.length(); i++) {

                                             //  JSONObject item = (JSONObject) myArray.get(i);
                                             JSONObject item = myArray.getJSONObject(i);
                                             //JSONObject item = (JSONObject) jsonObject1.get(String.valueOf(i));


                                             String result = item.has("result") ? item.getString("result") : "";
                                             String status = item.has("status") ? item.getString("status") : "";
                                             String date = item.has("date") ? item.getString("date") : "";
                                             int plot = item.has("plot") ? item.getInt("plot") : 0;


                                             ViralLoad newResult = new ViralLoad(result, status, date, plot);

                                             viralLoadArrayList.add(newResult);
                                             mAdapter.notifyDataSetChanged();

                                         }
                                         //}


                                     } else {
                                         Toast.makeText(context, "No VL Results Found", Toast.LENGTH_SHORT).show();
                                     }
                                 }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                if (pDialog != null && pDialog.isShowing()) {
                                    pDialog.hide();
                                    pDialog.cancel();
                                }}

                    }
                    @Override
                    public void onError(ANError error) {

                        Log.d("Errors", error.getErrorDetail());
                        // handle error

                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.hide();
                            pDialog.cancel();
                        }


                        Log.e(TAG, String.valueOf(error.getErrorCode()));
                        if (error.getErrorCode() == 0){

                            no_result_lyt.setVisibility(View.VISIBLE);
                        }
                        else{

                            error_lyt.setVisibility(View.VISIBLE);
                            Snackbar.make(root.findViewById(R.id.frag_viral_load), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                        }


                    }
                });
    }
    public void logout(){

        String endPoint = Stash.getString(Constants.AUTH_TOKEN);
        Stash.clearAll();

        Stash.put(Constants.AUTH_TOKEN, endPoint);

        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        //context.fini;
    }
    public void alertlogout(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setIcon(R.drawable.nishauri_logo);
        builder1.setTitle("Your Session Has Expired");
        // builder1.setMessage( zz);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        logout();

                        //dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

}
