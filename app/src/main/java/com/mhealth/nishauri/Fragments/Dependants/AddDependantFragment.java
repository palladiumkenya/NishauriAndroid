package com.mhealth.nishauri.Fragments.Dependants;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.Activities.Auth.LoginActivity;
import com.mhealth.nishauri.Activities.MainActivity;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mhealth.nishauri.utils.AppController.TAG;


public class AddDependantFragment extends Fragment {

    @BindView(R.id.hei_number)
    EditText hei_number;

    @BindView(R.id.first_name)
    EditText first_name;

    @BindView(R.id.surname)
    EditText surname;

    @BindView(R.id.txt_dob)
    EditText txt_dob;

    @BindView(R.id.animationView)
    LottieAnimationView animationView;

    @BindView(R.id.btn_cancel)
    Button btn_cancel;

    @BindView(R.id.btn_complete_dependant)
    Button btn_complete;



    private Unbinder unbinder;
    private View root;
    private Context context;

    private User loggedInUser;



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
        root = inflater.inflate(R.layout.fragment_add_dependant, container, false);
        unbinder = ButterKnife.bind(this, root);

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);



        txt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDob();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Cancel button clicked!", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(AddDependantFragment.this).navigate(R.id.nav_dependant);
            }
        });

        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkNulls()){

                    addDependant(hei_number.getText().toString(),first_name.getText().toString(),surname.getText().toString(), txt_dob.getText().toString());

                }


            }
        });

        return root;
    }

    private void getDob() {
        Calendar cur_calender = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        long date_ship_millis = calendar.getTimeInMillis();
                        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");

                        txt_dob.setText(newFormat.format(date_ship_millis));


                    }
                }, cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public boolean checkNulls(){
        boolean valid = true;


        if(TextUtils.isEmpty(hei_number.getText().toString()))
        {
            Snackbar.make(root.findViewById(R.id.fragment_add_dependant), "Please provide a HEI Number", Snackbar.LENGTH_LONG).show();
            valid = false;
            return valid;
        }

        if(TextUtils.isEmpty(first_name.getText().toString()))
        {
            Snackbar.make(root.findViewById(R.id.fragment_add_dependant), "Please provide First name", Snackbar.LENGTH_LONG).show();
            valid = false;
            return valid;
        }

        if(TextUtils.isEmpty(surname.getText().toString()))
        {
            Snackbar.make(root.findViewById(R.id.fragment_add_dependant), "Please provide surname", Snackbar.LENGTH_LONG).show();
            valid = false;
            return valid;
        }

        if(TextUtils.isEmpty(txt_dob.getText().toString()))
        {
            Snackbar.make(root.findViewById(R.id.fragment_add_dependant), "Please provide a date of birth", Snackbar.LENGTH_LONG).show();
            valid = false;
            return valid;
        }
        return valid;
    }

    private void addDependant(String hei_number, String first_name, String surname, String txt_dob){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("heiNumber", hei_number);
            jsonObject.put("first_name", first_name);
            jsonObject.put("surname", surname);
            jsonObject.put("dob", txt_dob);
            jsonObject.put("approved", "false");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String auth_token = loggedInUser.getAuth_token();

        AndroidNetworking.post(Constants.ADD_DEPENDANT)
                .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .addJSONObjectBody(jsonObject) // posting json
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        Log.e(TAG, response.toString());


                        animationView.setVisibility(View.GONE);

                            if (response.has("data")){

                                NavHostFragment.findNavController(AddDependantFragment.this).navigate(R.id.nav_dependant);
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, error.getErrorBody());

                        animationView.setVisibility(View.GONE);


                        Snackbar.make(root.findViewById(R.id.fragment_add_dependant), "" + error.getErrorBody(), Snackbar.LENGTH_LONG).show();
                    }
                });


    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
