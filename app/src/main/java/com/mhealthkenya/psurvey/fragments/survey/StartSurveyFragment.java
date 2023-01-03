package com.mhealthkenya.psurvey.fragments.survey;

import static android.R.layout.simple_spinner_dropdown_item;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.models.UrlTable;
import com.mhealthkenya.psurvey.models.auth;
import com.mhealthkenya.psurvey.models.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;

import static com.mhealthkenya.psurvey.depedancies.AppController.TAG;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class StartSurveyFragment extends Fragment {



    private Unbinder unbinder;
    private View root;
    private Context context;
    public String z;

    private auth loggedInUser;
    ArrayList<String> dataList;
    ArrayList<data>  datas;
    public static int dataID;




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

        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_start_survey, container, false);
        unbinder = ButterKnife.bind(this, root);

        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);
       // getparticipant();

        btn_patient_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (dataID==1 && TextUtils.isEmpty(etxt_ccc_no.getText().toString())){
                        Snackbar.make(root.findViewById(R.id.frag_start_survey),"Enter Patient's CCC Number", Snackbar.LENGTH_SHORT).show();
                }
                else if (dataID==1 && TextUtils.isEmpty(etxt_first_name.getText().toString())){
                        Snackbar.make(root.findViewById(R.id.frag_start_survey), "Enter Patient's  First Name", Snackbar.LENGTH_LONG).show();
                }


                    else if (dataID!=1 &&dataID!=2){
                        Snackbar.make(root.findViewById(R.id.frag_start_survey), "Invalid", Snackbar.LENGTH_LONG).show();

                    }*/



                  //  else{

                        confirmConsent(etxt_ccc_no.getText().toString(), etxt_first_name.getText().toString());
                   // }


            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void confirmConsent(String ccc_no,String firstName ) {
       // private void confirmConsent() {

        JSONObject jsonObject = new JSONObject();
        try {

            //jsonObject.put("questionnaire_participant_id", dataID);
            jsonObject.put("ccc_number", ccc_no);
            jsonObject.put("first_name", firstName);



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

        AndroidNetworking.post(z+Constants.INITIAL_CONFIRMATION)
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
                                //bundle.putString("questionnaire_participant_id", String.valueOf(dataID));
                               // bundle.putInt("questionnaire_participant_id", dataID);
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

                        System.out.println("errors");


                        Snackbar.make(root.findViewById(R.id.frag_start_survey), "Error: "+error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });

    }

    private void getparticipant() {

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
                .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();

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

                            ArrayAdapter<String>  aa = new ArrayAdapter<String>(getContext(), simple_spinner_dropdown_item,dataList){
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



}