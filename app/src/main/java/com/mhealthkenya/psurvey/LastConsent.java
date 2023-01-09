package com.mhealthkenya.psurvey;

import static android.R.layout.simple_spinner_dropdown_item;
import static com.mhealthkenya.psurvey.depedancies.AppController.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.mhealthkenya.psurvey.activities.InformedActivity;
import com.mhealthkenya.psurvey.activities.PrivacyActivity;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.models.ActiveSurveys;
import com.mhealthkenya.psurvey.models.UrlTable;
import com.mhealthkenya.psurvey.models.auth;
import com.mhealthkenya.psurvey.models.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LastConsent extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;
    //String privacytext, informtext;

    public String z;


    ArrayList<String> dataList;
    ArrayList<data>  datas;
    public static int dataID;


    private auth loggedInUser;
    private ActiveSurveys activeSurveys;
    private boolean informb, privacyb, stateb;
    private int questionnaire_participant_id;

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

    @BindView(R.id.patient_id)
    MaterialTextView patient_id;

    @BindView(R.id.tv_survey_id)
    MaterialTextView tv_survey_id;

    @BindView(R.id.informText)
    MaterialTextView informText;

    @BindView(R.id.privacyText)
    MaterialTextView privacyText;


    @BindView(R.id.checkInform)
    CheckBox checkInform;

    @BindView(R.id.checkPrivacy)
    CheckBox checkPrivacy;

    @BindView(R.id.checkStnt)
    CheckBox checkStnt;

    //start survey

    @BindView(R.id.til_ccc_no)
    TextInputLayout til_ccc_no;

    @BindView(R.id.etxt_ccc_no)
    TextInputEditText etxt_ccc_no;

    @BindView(R.id.til_f_name)
    TextInputLayout til_first_name;

    @BindView(R.id.etxt_first_name)
    TextInputEditText etxt_first_name;

    @BindView(R.id.spinner_subjects)
    Spinner spinner_subjects;

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
       // unbinder = ButterKnife.bind(this, root);
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_last_consent, container, false);
        //unbinder = ButterKnife.bind(this, root);

        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);

        unbinder = ButterKnife.bind(this, root);

        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);
        assert getArguments() != null;
        activeSurveys = (ActiveSurveys) getArguments().getSerializable("questionnaire");
        // String ccc_no= (String) getArguments().getSerializable("ccc_no");
        //String f_name= (String) getArguments().getSerializable("f_name");
        // questionnaire_participant_id =(int) getArguments().getSerializable("questionnaire_participant_id_");

        //String description = (String) getArguments().getSerializable("description");
        //String name = (String) getArguments().getSerializable("name");
        //boolean privacy_policy = (boolean) getArguments().getSerializable("privacy_policy");
        //boolean informed_consent = (boolean) getArguments().getSerializable("informed_consent");
        //boolean interviewer_statement = (boolean) getArguments().getSerializable("interviewer_statement");


        //tv_patient_name.setText("Name: "+f_name);
        //tv_patient_number.setText("CCC Number: " + ccc_no);
        //patient_id.setText("Patient id: " + questionnaire_participant_id);
        tv_survey_id.setText("Questioonaire ID: "+String.valueOf(activeSurveys.getId()));
        getparticipant();


        informText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InformedActivity.class);
                startActivity(intent);

            }
        });

        privacyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PrivacyActivity.class);
                startActivity(intent);

            }
        });

        //checkboxes

        if(checkInform.isChecked())
        {
            informb= Boolean.parseBoolean(checkInform.getText().toString());
        }


        if(checkPrivacy.isChecked())
        {
            //description=checkPrivacy.getText().toString();
            privacyb = Boolean.parseBoolean(checkPrivacy.getText().toString());

        }

        if(checkStnt.isChecked())
        {
            stateb = Boolean.parseBoolean(checkStnt.getText().toString());
        }


        //end checkboxes

        if (activeSurveys == null){

            Snackbar.make(root.findViewById(R.id.lastConsent2), "No Surveys found.", Snackbar.LENGTH_LONG).show();


        }else {
            tv_chosen_survey_title.setText(activeSurveys.getName());
            tv_chosen_survey_introduction.setText(activeSurveys.getDescription());
        }


        btn_patient_consent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (dataID==1 && TextUtils.isEmpty(etxt_ccc_no.getText().toString())){
                    //Snackbar.make(root.findViewById(R.id.lastConsent2),"Enter Patient's CCC Number", Snackbar.LENGTH_SHORT).show();
                    Toast.makeText(context,"Enter Patient's CCC Number", Toast.LENGTH_SHORT).show();
                }
                else if (dataID==1 && TextUtils.isEmpty(etxt_first_name.getText().toString())){
                    Toast.makeText(context,"Enter Patient's  First Name", Toast.LENGTH_SHORT).show();
                    //Snackbar.make(root.findViewById(R.id.lastConsent2), "Enter Patient's  First Name", Snackbar.LENGTH_LONG).show();
                }


                else if (dataID!=1 &&dataID!=2){
                    Toast.makeText(context,"Invalid", Toast.LENGTH_SHORT).show();
                   // Snackbar.make(root.findViewById(R.id.lastConsent2), "Invalid", Snackbar.LENGTH_LONG).show();

                }*/
               if (dataID!=1 &&dataID!=2){
                   Toast.makeText(context,"Invalid", Toast.LENGTH_SHORT).show();
                   // Snackbar.make(root.findViewById(R.id.frag_patient_consent), "Invaliddfghjk", Snackbar.LENGTH_LONG).show();


                }



                // confirmConsent(activeSurveys.getId(),ccc_no,f_name, Boolean.parseBoolean(checkInform.getText().toString()), Boolean.parseBoolean(checkPrivacy.getText().toString()), Boolean.parseBoolean(checkStnt.getText().toString()));
              else if(!checkInform.isChecked() || !checkPrivacy.isChecked() || !checkStnt.isChecked())
                {
                     Toast.makeText(context, "Please consent first", Toast.LENGTH_SHORT).show();
                   // Snackbar.make(root.findViewById(R.id.lastConsent2), "Please consent first", Snackbar.LENGTH_LONG).show();


                }else {


                    confirmConsent(activeSurveys.getId(),etxt_ccc_no.getText().toString(), etxt_first_name.getText().toString(), dataID);}
            }
        });

        return root;
    }
    // private void confirmConsent(int questionnaireId, String ccc_no, String firstName,boolean informb, boolean privacyb, boolean stateb) {
    private void confirmConsent(int questionnaireId, String ccc_no, String firstName, int dataID) {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("questionnaire_id", questionnaireId );
            jsonObject.put("ccc_number", ccc_no);
            jsonObject.put("first_name", firstName);

            jsonObject.put("questionnaire_participant_id", dataID);
            jsonObject.put("interviewer_statement", stateb);
            jsonObject.put("informed_consent", informb);
            jsonObject.put("privacy_policy", privacyb);



            //"questionnaire_participant_id": 1,
            //      "informed_consent": true,
            //    "privacy_policy": true



        } catch (JSONException e) {
            e.printStackTrace();
        }



        String auth_token = loggedInUser.getAuth_token();

        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }
        AndroidNetworking.post(z+Constants.PATIENT_CONSENT)
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
                        //Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, response.toString());

                        try {

                            String  errors = response.has("error") ? response.getString("error") : "" ;
                            String  message = response.has("message") ? response.getString("message") : "" ;


                            if (response.has("link")){

                                String link = response.has("link") ? response.getString("link") : "";
                                int sessionId = response.has("session") ? response.getInt("session") : 0;

                                Bundle bundle = new Bundle();
                                bundle.putString("questionLink",link);
                                bundle.putInt("sessionID",sessionId);

                                // bundle.putInt("questionnaire_id", questionnaireId);
                                Navigation.findNavController(root).navigate(R.id.nav_questions, bundle);

                                //Intent intent = new Intent(context, SingleQuestions.class);
                                //startActivity(intent);


                            }
                            else if (message.contains("client verification failed")){

                                Navigation.findNavController(root).navigate(R.id.nav_home);
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                            else{

                               // Snackbar.make(root.findViewById(R.id.lastConsent2), errors, Snackbar.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {

                        // Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show();
                        // handle error
                        Log.e(TAG, error.getErrorDetail());


                        if (error.getErrorBody().contains("No questions")){
                            Toast.makeText(context, "No questions found for this survey!", Toast.LENGTH_SHORT).show();

                          //  Snackbar.make(root.findViewById(R.id.lastConsent2), "No questions found for this survey!", Snackbar.LENGTH_LONG).show();

                        }
                        else {
                            Toast.makeText(context, "Error: "+error.getErrorBody(), Toast.LENGTH_SHORT).show();

                           // Snackbar.make(root.findViewById(R.id.lastConsent2), "Error: "+error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                        }
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //get participant

    private void getparticipant(){

        String auth_token = loggedInUser.getAuth_token();

        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }
        AndroidNetworking.get(z+Constants.GET_PARTICIPANTS)
                //.addQueryParameter("limit", "3")
                //.addHeaders("token", "1234")

                .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Accept", "*/*")
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
                        //Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();

                        try {


                            datas = new ArrayList<>();
                            dataList = new ArrayList<>();

                            datas.clear();
                            dataList.clear();

                            JSONObject object=new JSONObject(String.valueOf(response));
                            JSONArray array=object.getJSONArray("data");

                            for (int i = 0; i < array.length(); i++) {
                                //JSONObject jsonObject = (JSONObject) postsArray.get(String.valueOf(i));
                                JSONObject jsonObject=array.getJSONObject(i);

                                int id = jsonObject.has("id") ? jsonObject.getInt("id") : 0;
                                String name = jsonObject.has("participant") ? jsonObject.getString("participant") : "";


                                data dat = new data(id, name);

                                datas.add(dat);
                                dataList.add(dat.getParticipant());
                            }
                            datas.add(new data(0,"--select participant--"));
                            dataList.add("--select participant--");

                            ArrayAdapter<String> aa = new ArrayAdapter<String>(getContext(), simple_spinner_dropdown_item,dataList){
                                @Override
                                public int getCount() {
                                    return super.getCount();
                                }
                            };

                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            spinner_subjects.setAdapter(aa);
                            spinner_subjects.setSelection(aa.getCount()-1);
                            dataID =datas.get(aa.getCount()-1).getId();

                            // dataID = datas.get(aa.getCount()-1).getId();
                            spinner_subjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                                    dataID= datas.get(position).getId();

                                    if (dataID==2){
                                        etxt_ccc_no.setText("");
                                        etxt_first_name.setText("");
                                        etxt_ccc_no.setEnabled(false);
                                        etxt_first_name.setEnabled(false);


                                        //Toast.makeText(context, "bbbb", Toast.LENGTH_SHORT).show();
                                    }else if (dataID==1){
                                        etxt_ccc_no.setText("");
                                        etxt_first_name.setText("");
                                        etxt_ccc_no.setEnabled(true);
                                        etxt_first_name.setEnabled(true);
                                    }

                                    else if (dataID==3){
                                        etxt_ccc_no.setText("");
                                        etxt_first_name.setText("");
                                        etxt_ccc_no.setEnabled(true);
                                        etxt_first_name.setEnabled(true);
                                    }



                                    //til_ccc_no.setError("Please enter a CCC Number.");*/)
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });





                        }catch(JSONException e){
                            e.printStackTrace();

                        }


                    }
                    @Override
                    public void onError(ANError error) {
                        // Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        // handle error
                    }
                });
    }

    //end participant



}
