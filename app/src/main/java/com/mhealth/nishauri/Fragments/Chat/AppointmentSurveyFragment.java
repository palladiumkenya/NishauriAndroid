package com.mhealth.nishauri.Fragments.Chat;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.mhealth.nishauri.Models.Facility;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mhealth.nishauri.utils.AppController.TAG;


public class AppointmentSurveyFragment extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;

    private User loggedInUser;

    private String VisitDate = "";
    private String MissedAppointmentDate = "";

    private int facilityID = 0;

    ArrayList<String> facilitiesList;
    ArrayList<Facility> facilities;

    @BindView(R.id.missed_app_radio_group)
    RadioGroup missed_app_radio_group;

    @BindView(R.id.specify_date_lyt)
    LinearLayout specify_date_lyt;

    @BindView(R.id.app_date_edtxt)
    TextInputEditText app_date_edtxt;

    @BindView(R.id.facility_change_radio_group)
    RadioGroup facility_change_radio_group;

    @BindView(R.id.facilitySpinner)
    SearchableSpinner facilitySpinner;

    @BindView(R.id.facility_change_lyt)
    LinearLayout facility_change_lyt;

    @BindView(R.id.facility_app_date_edtxt)
    TextInputEditText facility_app_date_edtxt;

    @BindView(R.id.btn_results_section)
    MaterialButton btn_results_section;

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
        root = inflater.inflate(R.layout.fragment_appointment_survey, container, false);
        unbinder = ButterKnife.bind(this, root);

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);

        initialise();

        btn_results_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(AppointmentSurveyFragment.this).navigate(R.id.nav_result_survey);


            }
        });

        return root;
    }

    private void initialise(){

        missed_app_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
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
                    if (checkedRadioButton.getText().equals("Yes")){
                        specify_date_lyt.setVisibility(View.VISIBLE);
                    }
                    else{

                        specify_date_lyt.setVisibility(View.GONE);
                    }

                }

            }
        });

        app_date_edtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMissedAppointment();
            }
        });

        facility_change_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
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
                    if (checkedRadioButton.getText().equals("Yes")){
                        facility_change_lyt.setVisibility(View.VISIBLE);
                    }
                    else{

                        facility_change_lyt.setVisibility(View.GONE);
                    }

                }

            }
        });

        facilitySpinner.setTitle("Select the facility ");
        facilitySpinner.setPositiveButton("OK");
        getFacilities();

        facility_app_date_edtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVisitDate();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getMissedAppointment(){
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
                        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);

                        MissedAppointmentDate = newFormat.format(new Date(date_ship_millis));

                        app_date_edtxt.setText(MissedAppointmentDate);
                    }
                }, cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void getVisitDate(){
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
                        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);

                        VisitDate = newFormat.format(new Date(date_ship_millis));

                        facility_app_date_edtxt.setText(VisitDate);
                    }
                }, cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void getFacilities() {

        String auth_token = loggedInUser.getAuth_token();


        AndroidNetworking.get(Constants.ENDPOINT+Constants.ALL_FACILITIES)
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

                            boolean  status = response.has("success") && response.getBoolean("success");
                            String  message = response.has("message") ? response.getString("message") : "" ;
                            String  errors = response.has("errors") ? response.getString("errors") : "" ;


                            if (status)
                            {


                                facilities = new ArrayList<Facility>();
                                facilitiesList = new ArrayList<String>();

                                facilities.clear();
                                facilitiesList.clear();

                                JSONArray jsonArray = response.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject facility = (JSONObject) jsonArray.get(i);

                                    int id = facility.has("id") ? facility.getInt("id") : 0;
                                    String mfl_code = facility.has("mfl_code") ? facility.getString("mfl_code") : "";
                                    String name = facility.has("name") ? facility.getString("name") : "";
                                    String county = facility.has("county") ? facility.getString("county") : "";
                                    String sub_county = facility.has("sub_county") ? facility.getString("sub_county") : "";

                                    Facility newFacility = new Facility(id,mfl_code,name,county,sub_county);

                                    facilities.add(newFacility);
                                    facilitiesList.add(newFacility.getName());
                                }

                                facilities.add(new Facility(0,"--select facility--","--select facility--","--select--","--select--"));
                                facilitiesList.add("--select facility--");

                                ArrayAdapter<String> aa=new ArrayAdapter<String>(context,
                                        android.R.layout.simple_spinner_dropdown_item,
                                        facilitiesList){
                                    @Override
                                    public int getCount() {
                                        return super.getCount(); // you don't display last item. It is used as hint.
                                    }
                                };

                                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                if (facilitySpinner != null){
                                    facilitySpinner.setAdapter(aa);
                                    facilitySpinner.setSelection(aa.getCount()-1);

                                    facilityID = facilities.get(aa.getCount()-1).getId();

                                    facilitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                            facilityID = facilities.get(position).getId();


                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });

                                }


                            } else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Snackbar.make(root.findViewById(R.id.frag_app_survey), e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }


                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error

                        Log.e(TAG, String.valueOf(error.getErrorCode()));

                    }
                });
    }
}