package com.mhealth.nishauri.Fragments.Profile;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
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
import com.mhealth.nishauri.Fragments.Dependants.AddDependantFragment;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mhealth.nishauri.utils.AppController.TAG;


public class UpdateUserFragment extends Fragment {
    private Unbinder unbinder;
    private View root;
    private Context context;

    private User loggedInUser;


    @BindView(R.id.card_name)
    MaterialTextView card_name;

    @BindView(R.id.card_phone)
    MaterialTextView card_phone;

    @BindView(R.id.til_first_name)
    TextInputLayout til_first_name;

    @BindView(R.id.etxt_first_name)
    TextInputEditText etxt_first_name;

    @BindView(R.id.til_surname)
    TextInputLayout til_surname;

    @BindView(R.id.etxt_surname)
    TextInputEditText etxt_surname;

    @BindView(R.id.til_phone_number)
    TextInputLayout til_phone_number;

    @BindView(R.id.etxt_phone_number)
    TextInputEditText etxt_phone_number;

    @BindView(R.id.security_question_spinner)
    AppCompatSpinner security_question_spinner;

    @BindView(R.id.til_security_answer)
    TextInputLayout til_security_answer;

    @BindView(R.id.etxt_security_answer)
    TextInputEditText etxt_security_answer;

    @BindView(R.id.btn_update_profile)
    Button btn_profile;



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
        root = inflater.inflate(R.layout.fragment_update_user, container, false);
        unbinder = ButterKnife.bind(this, root);

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);

        loadCurrentUser();

        /*Edit user details here...*/
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkNulls()){

                    updateUser();

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

    public boolean checkNulls(){
        boolean valid = true;


        if(TextUtils.isEmpty(etxt_first_name.getText().toString()))
        {
            Snackbar.make(root.findViewById(R.id.frag_update_user), "Please provide a first name.", Snackbar.LENGTH_LONG).show();
            valid = false;
            return valid;
        }

        if(TextUtils.isEmpty(etxt_surname.getText().toString()))
        {
            Snackbar.make(root.findViewById(R.id.frag_update_user), "Please provide a surname.", Snackbar.LENGTH_LONG).show();
            valid = false;
            return valid;
        }

        if(TextUtils.isEmpty(etxt_phone_number.getText().toString()))
        {
            Snackbar.make(root.findViewById(R.id.frag_update_user), "Please provide a phone number.", Snackbar.LENGTH_LONG).show();
            valid = false;
            return valid;
        }

        if (security_question_spinner.getSelectedItem().toString().equals("Select your security question.")) {
            Snackbar.make(root.findViewById(R.id.frag_update_user), "Please select a security question.", Snackbar.LENGTH_SHORT).show();
            valid = false;
            return valid;
        }

        if(TextUtils.isEmpty(etxt_security_answer.getText().toString()))
        {
            Snackbar.make(root.findViewById(R.id.frag_update_user), "Please provide a security answer.", Snackbar.LENGTH_LONG).show();
            valid = false;
            return valid;
        }
        return valid;
    }

    public  void updateUser(){


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("first_name", etxt_first_name.getText().toString());
            jsonObject.put("last_name", etxt_surname.getText().toString());
            jsonObject.put("msisdn", etxt_phone_number.getText().toString());
            jsonObject.put("securityQuestion", security_question_spinner.getSelectedItem().toString() );
            jsonObject.put("securityAnswer", etxt_security_answer.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String auth_token = loggedInUser.getAuth_token();

        AndroidNetworking.put(Constants.ENDPOINT+Constants.UPDATE_USER)
                .addHeaders("Authorization","Token "+ auth_token)
                .addJSONObjectBody(jsonObject) // posting json
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        Log.e(TAG, response.toString());



                        if (response.has("msisdn")){

                            NavHostFragment.findNavController(UpdateUserFragment.this).navigate(R.id.nav_update_profile);
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, error.getErrorBody());


                        Snackbar.make(root.findViewById(R.id.frag_update_user), "" + error.getErrorBody(), Snackbar.LENGTH_LONG).show();
                    }
                });

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
                                    String securityAnswer = item.has("securityAnswer") ? item.getString("securityAnswer") : "";


                                    card_name.setText(first_name + " " + last_name);
                                    card_phone.setText(msisdn);
                                    etxt_first_name.setText(first_name );
                                    etxt_surname.setText(last_name );
                                    etxt_phone_number.setText(msisdn);
                                    security_question_spinner.setSelection(0);
                                    etxt_security_answer.setText(securityAnswer);

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

                        Snackbar.make(root.findViewById(R.id.frag_update_user), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });

    }
}
