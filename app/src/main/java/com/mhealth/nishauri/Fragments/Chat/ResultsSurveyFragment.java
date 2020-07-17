package com.mhealth.nishauri.Fragments.Chat;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.mhealth.nishauri.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ResultsSurveyFragment extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;

    private String AppointmentDate = "";

    @BindView(R.id.result_radio_group)
    RadioGroup result_radio_group;

    @BindView(R.id.app_date_edtxt)
    TextInputEditText app_date_edtxt;


    @BindView(R.id.specify_date_lyt)
    LinearLayout specify_date_lyt;

    @BindView(R.id.btn_complete_survey)
    MaterialButton btn_complete_survey;


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
        root = inflater.inflate(R.layout.fragment_results_survey, container, false);
        unbinder = ButterKnife.bind(this, root);

        initialise();

        btn_complete_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(ResultsSurveyFragment.this).navigate(R.id.nav_chat);
                Toast.makeText(context, "Survey Completed!", Toast.LENGTH_SHORT).show();


            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initialise(){

        app_date_edtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppointmentDate();
            }
        });

        result_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
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
                    if (checkedRadioButton.getText().equals("On my last appointment")){
                        specify_date_lyt.setVisibility(View.VISIBLE);
                    }
                    else{

                        specify_date_lyt.setVisibility(View.GONE);
                    }

                }

            }
        });

    }

    private void getAppointmentDate(){
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

                        AppointmentDate = newFormat.format(new Date(date_ship_millis));

                        app_date_edtxt.setText(AppointmentDate);
                    }
                }, cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
}