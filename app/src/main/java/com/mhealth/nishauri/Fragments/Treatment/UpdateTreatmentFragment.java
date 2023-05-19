package com.mhealth.nishauri.Fragments.Treatment;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.mhealth.nishauri.Fragments.Profile.UpdateUserFragment;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mhealth.nishauri.utils.AppController.TAG;


public class UpdateTreatmentFragment extends Fragment {


    private Unbinder unbinder;
    private View root;
    private Context context;

    private User loggedInUser;

    private boolean same_art_date ;
    private String art_start_date = "";

    @BindView(R.id.regimen_spinner)
    AppCompatSpinner regimen_spinner;

    @BindView(R.id.art_date_radio_group)
    RadioGroup art_date_radio_group;

    @BindView(R.id.same_art_radio)
    MaterialRadioButton same_art_radio;

    @BindView(R.id.pick_date_radio)
    MaterialRadioButton pick_date_radio;

    @BindView(R.id.lyt_pick_date)
    LinearLayout lyt_pick_date;

    @BindView(R.id.txt_start_art_date)
    TextInputEditText txt_start_art_date;

    @BindView(R.id.btn_submit)
    MaterialButton btn_submit;

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
        root = inflater.inflate(R.layout.fragment_update_treatment, container, false);
        unbinder = ButterKnife.bind(this, root);

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);

        initialise();

        return root;
    }

    private void initialise(){

        art_date_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                MaterialRadioButton checkedRadioButton = (MaterialRadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    // Changes the textview's text to "Checked: example radiobutton text"
                    if (checkedRadioButton.getText().equals("Pick a start date.")){
                        lyt_pick_date.setVisibility(View.VISIBLE);
                        same_art_date = false;
                        art_start_date="";
                    }
                    else{

                        lyt_pick_date.setVisibility(View.GONE);
                        same_art_date = true;
                        art_start_date="";
                    }

                }
            }
        });

        txt_start_art_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDob();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkNulls()){

                    updateTreatment();

                }

            }
        });

    }

    private boolean checkNulls(){

        boolean valid = true;


        if(regimen_spinner.getSelectedItem().toString().equals("Pick a regimen here…"))
        {
            Snackbar.make(root.findViewById(R.id.frag_update_treatment), "Please select a regimen for your treatment.", Snackbar.LENGTH_LONG).show();
            valid = false;
            return valid;
        }
        return valid;

    }

    public  void updateTreatment(){


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Regiment", regimen_spinner.getSelectedItem().toString());
            jsonObject.put("date_started", art_start_date);
            jsonObject.put("is_same_art", same_art_date);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        String auth_token = loggedInUser.getAuth_token();


        AndroidNetworking.post(Constants.ENDPOINT+Constants.UPDATE_REGIMEN)
                .addHeaders("Authorization","Token "+ auth_token)
                .addJSONObjectBody(jsonObject) // posting json
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

//                        Log.e(TAG, response.toString());

                        try {

                            boolean  status = response.has("success") && response.getBoolean("success");
                            String  message = response.has("data") ? response.getString("data") : "" ;
                            String  errors = response.has("errors") ? response.getString("errors") : "" ;

                            if (status){

                                NavHostFragment.findNavController(UpdateTreatmentFragment.this).navigate(R.id.nav_treatment);
                                Toast.makeText(context, "Treatment Saved", Toast.LENGTH_SHORT).show();

                            }
                            else{

                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
//                        Log.e(TAG, error.getErrorBody());

                        Snackbar.make(root.findViewById(R.id.frag_update_treatment), "" + error.getErrorBody(), Snackbar.LENGTH_LONG).show();
                    }
                });

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

                        art_start_date = newFormat.format(new Date(date_ship_millis));

                       txt_start_art_date.setText(art_start_date);


                    }
                }, cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}