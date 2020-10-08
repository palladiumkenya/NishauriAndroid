package com.mhealthkenya.psurvey.fragments.survey;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.models.auth;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mhealthkenya.psurvey.depedancies.AppController.TAG;


public class StartSurveyFragment extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;

    private auth loggedInUser;


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

        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);

        btn_patient_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(etxt_ccc_no.getText().toString())){

                    til_ccc_no.setError("Please enter a CCC Number.");

                }
                else if (TextUtils.isEmpty(etxt_first_name.getText().toString())){

                    til_first_name.setError("Please enter the first name of the patient.");

                }
                else {

                    confirmConsent(etxt_ccc_no.getText().toString(),etxt_first_name.getText().toString());

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

    private void confirmConsent(String ccc_no,String firstName) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ccc_number", ccc_no);
            jsonObject.put("first_name", firstName);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        String auth_token = loggedInUser.getAuth_token();

        AndroidNetworking.post(Constants.ENDPOINT+Constants.INITIAL_CONFIRMATION)
                .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setContentType("application.json")
                .addJSONObjectBody(jsonObject) // posting json
                .build()
                .getAsJSONObject(new JSONObjectRequestListener(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, response.toString());

                        try {

                            boolean  status = response.has("success") && response.getBoolean("success");
                            String  errors = response.has("error") ? response.getString("error") : "" ;
                            String  message = response.has("message") ? response.getString("message") : "" ;

                            if (status && message.contains("You can now start questionnaire")){

                                Bundle bundle = new Bundle();
                                bundle.putString("ccc_no", etxt_ccc_no.getText().toString());
                                bundle.putString("f_name",  etxt_first_name.getText().toString());
                                Navigation.findNavController(root).navigate(R.id.nav_select_survey, bundle);


                            }
                            else if (message.contains("client verification failed")){


                                Snackbar.make(root.findViewById(R.id.frag_start_survey), message, Snackbar.LENGTH_LONG).show();


                            }
                            else {


                                Snackbar.make(root.findViewById(R.id.frag_start_survey), errors, Snackbar.LENGTH_LONG).show();


                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, error.getErrorDetail());


                        Snackbar.make(root.findViewById(R.id.frag_start_survey), "Error: "+error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });

    }
}