package com.mhealthkenya.psurvey.fragments.survey;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mhealthkenya.psurvey.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class StartSurveyFragment extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;


    @BindView(R.id.til_ccc_no)
    TextInputLayout til_ccc_no;

    @BindView(R.id.etxt_ccc_no)
    TextInputEditText etxt_ccc_no;

    @BindView(R.id.til_f_name)
    TextInputLayout til_first_name;

    @BindView(R.id.etxt_first_name)
    TextInputEditText etxt_first_name;

    @BindView(R.id.btn_patient_info)
    Button btn_patient_info;

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
        root = inflater.inflate(R.layout.fragment_start_survey, container, false);
        unbinder = ButterKnife.bind(this, root);





        btn_patient_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(etxt_ccc_no.getText().toString()) && !TextUtils.isEmpty(etxt_first_name.getText().toString())){
                    Bundle bundle = new Bundle();
                    bundle.putString("ccc_no", etxt_ccc_no.getText().toString());
                    bundle.putString("f_name",  etxt_first_name.getText().toString());
                    Navigation.findNavController(view).navigate(R.id.nav_select_survey, bundle);
                }else {
                    til_ccc_no.setError("Please enter a CCC Number.");
                    til_first_name.setError("Please enter the first name of the patient.");
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
}