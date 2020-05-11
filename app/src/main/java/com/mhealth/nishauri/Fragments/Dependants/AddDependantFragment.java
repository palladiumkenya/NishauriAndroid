package com.mhealth.nishauri.Fragments.Dependants;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AddDependantFragment extends Fragment {

    @BindView(R.id.first_name)
    EditText editText_first_name;

    @BindView(R.id.txt_dob)
    TextView txt_dob;

    @BindView(R.id.btn_cancel)
    Button btn_cancel;

    @BindView(R.id.btn_complete_dependant)
    Button btn_complete;



    private Unbinder unbinder;
    private View root;
    private Context context;



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
                NavHostFragment.findNavController(AddDependantFragment.this).navigate(R.id.nav_home);
            }
        });

        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkNulls()){
                    NavHostFragment.findNavController(AddDependantFragment.this).navigate(R.id.nav_dependant);
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

                        txt_dob.setText(String.format("Date of birth:  %s", newFormat.format(date_ship_millis)));


                    }
                }, cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public boolean checkNulls(){
        boolean valid = true;


        if(TextUtils.isEmpty(editText_first_name.getText().toString()))
        {
            Snackbar.make(root.findViewById(R.id.fragment_add_dependant), "Please provide First name", Snackbar.LENGTH_LONG).show();
            valid = false;
            return valid;
        }
        return valid;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
