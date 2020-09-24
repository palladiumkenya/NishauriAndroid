package com.mhealthkenya.psurvey.fragments.survey;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.models.ActiveSurveys;
import com.mhealthkenya.psurvey.models.Answer;
import com.mhealthkenya.psurvey.models.Question;
import com.mhealthkenya.psurvey.models.auth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mhealthkenya.psurvey.depedancies.AppController.TAG;


public class PatientConsentFragment extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;


    private auth loggedInUser;
    private ActiveSurveys activeSurveys;


    @BindView(R.id.tv_chosen_survey_title)
    MaterialTextView tv_chosen_survey_title;

    @BindView(R.id.tv_chosen_survey_introduction)
    MaterialTextView tv_chosen_survey_introduction;

    @BindView(R.id.btn_patient_consent)
    Button btn_patient_consent;

    @BindView(R.id.tv_patient_name)
    MaterialTextView tv_patient_name;

    @BindView(R.id.tv_patient_number)
    MaterialTextView tv_patient_number;

    @BindView(R.id.tv_survey_id)
    MaterialTextView tv_survey_id;

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
        root = inflater.inflate(R.layout.fragment_patient_consent, container, false);
        unbinder = ButterKnife.bind(this, root);

        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);

        assert getArguments() != null;
        activeSurveys = (ActiveSurveys) getArguments().getSerializable("questionnaire");
        String ccc_no= (String) getArguments().getSerializable("ccc_no");
        String f_name= (String) getArguments().getSerializable("f_name");

        tv_patient_name.setText("Name: "+f_name);
        tv_patient_number.setText("CCC Number: " + ccc_no);
        tv_survey_id.setText("Questioonaire ID: "+String.valueOf(activeSurveys.getId()));

        if (activeSurveys == null){

            Snackbar.make(root.findViewById(R.id.frag_patient_consent), "No Surveys found.", Snackbar.LENGTH_LONG).show();


        }else {
            tv_chosen_survey_title.setText(activeSurveys.getName());
            tv_chosen_survey_introduction.setText(activeSurveys.getDescription());
        }


        btn_patient_consent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confirmConsent(activeSurveys.getId(),ccc_no,f_name);


            }
        });


        return root;
    }

    private void confirmConsent(int questionnaireId, String ccc_no, String firstName ) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("questionnaire_id", questionnaireId );
            jsonObject.put("ccc_number", ccc_no);
            jsonObject.put("first_name", firstName);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        String auth_token = loggedInUser.getAuth_token();

        AndroidNetworking.post(Constants.ENDPOINT+Constants.PATIENT_CONSENT)
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

                            String  errors = response.has("error") ? response.getString("error") : "" ;


                            if (response.has("link")){

                                String link = response.has("link") ? response.getString("link") : "";
                                int sessionId = response.has("session") ? response.getInt("session") : 0;

                                Bundle bundle = new Bundle();
                                bundle.putString("questionLink",link);
                                bundle.putInt("sessionID",sessionId);
                                Navigation.findNavController(root).navigate(R.id.nav_questions, bundle);

                            }
                            else{

                                Snackbar.make(root.findViewById(R.id.frag_patient_consent), errors, Snackbar.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, error.getErrorDetail());


                        if (error.getErrorBody().contains("No questions")){

                            Snackbar.make(root.findViewById(R.id.frag_patient_consent), "No questions found for this survey!", Snackbar.LENGTH_LONG).show();

                        }
                        else {

                            Snackbar.make(root.findViewById(R.id.frag_patient_consent), "Error: "+error.getErrorBody(), Snackbar.LENGTH_LONG).show();

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