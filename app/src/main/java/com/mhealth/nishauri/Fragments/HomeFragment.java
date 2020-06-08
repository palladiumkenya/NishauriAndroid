package com.mhealth.nishauri.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.Fragments.Dependants.DependantsFragment;
import com.mhealth.nishauri.Models.Profile;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;
import static com.mhealth.nishauri.utils.AppController.TAG;


public class HomeFragment extends Fragment {

    @BindView(R.id.btn_add_dependant)
    Button btn_add_dependant;

    @BindView(R.id.msisdn)
    TextView txt_msisdn;

    private Unbinder unbinder;
    private View root;
    private Context context;

    private User loggedInUser;
    private Profile currentProfile;


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
        currentProfile = (Profile) Stash.getObject(Constants.PROFILE, Profile.class);

        String auth_token = loggedInUser.getAuth_token();


        AndroidNetworking.get(Constants.CURRENT_USER)
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

                            JSONObject data = response.getJSONObject("");


                            String ccc_no = data.has("CCCNo") ? data.getString("CCCNo") : "";
                            String security_question = data.has("securityQuestion") ? data.getString("securityQuestion") : "";
                            String security_answer = data.has("securityAnswer") ? data.getString("securityAnswer") : "";
                            String  terms_accepted = data.has("termAccepted") ? data.getString("termAccepted"): "";
                            String id = data.has("id") ? data.getString("id") : "";
                            String msisdn = data.has("msisdn") ? data.getString("msisdn") : "";

                            Profile newProfile = new Profile(ccc_no, security_question,security_answer,terms_accepted,id,msisdn);
                            Stash.put(Constants.PROFILE, newProfile);

                            txt_msisdn.setText(currentProfile.getMsisdn());

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



        btn_add_dependant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.nav_add_dependant);

            }
        });

        return root;
    }

}
