package com.mhealth.nishauri.Fragments.Profile;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mhealth.nishauri.utils.AppController.TAG;


public class UpdateDependantsFragment extends Fragment {


    private Unbinder unbinder;
    private View root;
    private Context context;

    private User loggedInUser;


    @BindView(R.id.card_dependant_name)
    MaterialTextView card_dependant_name;

    @BindView(R.id.til_dependant_firstname)
    TextInputLayout til_dependant_firstname;

    @BindView(R.id.etxt_dependant_firstname)
    TextInputEditText etxt_dependant_firstname;

    @BindView(R.id.til_dependant_surname)
    TextInputLayout til_dependant_surname;

    @BindView(R.id.etxt_dependant_surname)
    TextInputEditText etxt_dependant_surname;

    @BindView(R.id.btn_update_dependant)
    Button btn_update_dependant;

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
        root = inflater.inflate(R.layout.fragment_update_dependant, container, false);
        unbinder = ButterKnife.bind(this, root);

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);

        loadDependant();

        btn_update_dependant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkNulls()){

                    updateDependant();

                }
            }
        });

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void loadDependant(){

        String auth_token = loggedInUser.getAuth_token();


        AndroidNetworking.get(Constants.DEPENTANT)
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

                            String first_name = response.has("first_name") ? response.getString("first_name") : "";
                            String last_name = response.has("last_name") ? response.getString("last_name") : "";
                            String id = response.has("id") ? response.getString("id") : "";

                            card_dependant_name.setText(first_name + " " + last_name);
                            etxt_dependant_firstname.setText(first_name );
                            etxt_dependant_surname.setText(last_name );

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, error.getErrorBody());

                        Snackbar.make(root.findViewById(R.id.frag_update_user), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });

    }

    public boolean checkNulls(){
        boolean valid = true;


        if(TextUtils.isEmpty(etxt_dependant_firstname.getText().toString()))
        {
            Snackbar.make(root.findViewById(R.id.frag_update_dependant), "Please provide a dependants first name.", Snackbar.LENGTH_LONG).show();
            valid = false;
            return valid;
        }

        if(TextUtils.isEmpty(etxt_dependant_surname.getText().toString()))
        {
            Snackbar.make(root.findViewById(R.id.frag_update_dependant), "Please provide a dependants surname.", Snackbar.LENGTH_LONG).show();
            valid = false;
            return valid;
        }
        return valid;
    }

    public  void updateDependant(){


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("first_name", etxt_dependant_firstname.getText().toString());
            jsonObject.put("last_name", etxt_dependant_surname.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        String auth_token = loggedInUser.getAuth_token();

        AndroidNetworking.put(Constants.ENDPOINT+Constants.UPDATE_DEPENDANT)
                .addHeaders("Authorization","Token "+ auth_token)
                .addJSONObjectBody(jsonObject) // posting json
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        Log.e(TAG, response.toString());



                        if (response.has("first_name")){

                            NavHostFragment.findNavController(UpdateDependantsFragment.this).navigate(R.id.nav_update_profile);
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, error.getErrorBody());


                        Snackbar.make(root.findViewById(R.id.frag_update_dependant), "" + error.getErrorBody(), Snackbar.LENGTH_LONG).show();
                    }
                });

    }

}
