package com.mhealth.nishauri.Fragments.Chat;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.mhealth.nishauri.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class TreatmentSurveyFragment extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;

    @BindView(R.id.treatment_radio_group)
    RadioGroup treatment_radio_group;

    @BindView(R.id.same_day_radio)
    MaterialRadioButton same_day_radio;

    @BindView(R.id.specify_late_treatment_lyt)
    LinearLayout specify_late_treatment_lyt;

    @BindView(R.id.yes_radio)
    MaterialRadioButton yes_radio;

    @BindView(R.id.side_effects_radio_group)
    RadioGroup side_effects_radio_group;

    @BindView(R.id.specify_side_effects_lyt)
    LinearLayout specify_side_effects_lyt;

    @BindView(R.id.btn_appointments_section)
    MaterialButton btn_appointments_section;


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
        root = inflater.inflate(R.layout.fragment_treatment_survey, container, false);
        unbinder = ButterKnife.bind(this, root);

        initialise();

        btn_appointments_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(TreatmentSurveyFragment.this).navigate(R.id.nav_appointment_survey);

            }
        });

        return root;
    }

    private void initialise(){

        treatment_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
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
                    if (!checkedRadioButton.getText().equals("Same day.")){
                        specify_late_treatment_lyt.setVisibility(View.VISIBLE);
                    }
                    else{

                        specify_late_treatment_lyt.setVisibility(View.GONE);
                    }

                }
            }
        });

        side_effects_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
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
                        specify_side_effects_lyt.setVisibility(View.VISIBLE);
                    }
                    else {

                        specify_side_effects_lyt.setVisibility(View.GONE);
                    }

                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}