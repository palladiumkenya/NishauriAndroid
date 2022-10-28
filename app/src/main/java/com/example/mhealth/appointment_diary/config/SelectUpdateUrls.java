package com.example.mhealth.appointment_diary.config;

import static android.R.layout.simple_spinner_item;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.loginmodule.LoginActivity;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.example.mhealth.appointment_diary.tables.urlModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectUpdateUrls extends AppCompatActivity {

    ProgressDialog progressDialog;

    urlModel url_Model;
    ArrayList<String> urlModelArrayList;
    ArrayList<urlModel> names;
    Spinner spinner1;

    public String z, zz;

    int dataId;
    public String base_url;
    public String stage_name;
    SharedPreferences sharedPreferences1;
    Button btn_prcd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_update_urls);
        btn_prcd = findViewById(R.id.login_proceed);

        spinner1 =findViewById(R.id.spCompany);
        geturls1();


        btn_prcd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dataId!=1 && dataId!=2){
                    Toast.makeText(SelectUpdateUrls.this, "Invalid", Toast.LENGTH_LONG).show();
                }else {
                    new GetOperation().execute();

                }
            }
        });



    }


    private void setScreen(){
        SharedPreferences preferencesS =getSharedPreferences("PREFERENCE", MODE_PRIVATE);

        String FirstTime =preferencesS.getString("FirstTimeInstall", "");

        if (FirstTime.equals("Yes")){
            Intent intent1 =new Intent(SelectUpdateUrls.this, LoginActivity.class);
            startActivity(intent1);

        }else{
            SharedPreferences.Editor editor =preferencesS.edit();
            editor.putString("FirstTimeInstall", "Yes");
            editor.apply();
        }
    }

    public void geturls1(){
        String URLstring = "https://ushaurinode.mhealthkenya.co.ke/config";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLstring, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("", response.toString());

                try {
                    urlModelArrayList = new ArrayList<>();
                    names = new ArrayList<>();

                    urlModelArrayList.clear();
                    names.clear();


                    JSONArray jsonArray =response.getJSONArray("USHAURI");

                    for (int i =0; i<jsonArray.length(); i++){

                        JSONObject jsonObject =jsonArray.getJSONObject(i);
                        int url_id = jsonObject.getInt("id");
                        String url_stage =jsonObject.getString("stage");
                        String main_urls =jsonObject.getString("url");

                        url_Model = new urlModel(url_id, url_stage, main_urls);
                        names.add(url_Model);
                        urlModelArrayList.add(url_Model.getStage());

                    }



                    names.add(new urlModel(0, "", "--Select the system to connect to--"));
                    urlModelArrayList.add("--Select the system to connect to--");


                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SelectUpdateUrls.this, simple_spinner_item, urlModelArrayList);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinner1.setAdapter(spinnerArrayAdapter);
                    //removeSimpleProgressDialog();

                    spinner1.setSelection(spinnerArrayAdapter.getCount()-1);
                    dataId =names.get(spinnerArrayAdapter.getCount()-1).getId();


                    spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            dataId = names.get(position).getId();

                            if (dataId==1){

                                base_url = names.get(position).getUrl();

                                stage_name =names.get(position).getStage();

                            }
                            else if(dataId==2){

                                base_url = names.get(position).getUrl();
                                stage_name =names.get(position).getStage();
                                //Toast.makeText(SelectUrls.this, base_url, Toast.LENGTH_LONG).show();
                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(SelectUpdateUrls.this);
        requestQueue.add(jsonObjectRequest);
    }


    private class GetOperation extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            try {

                progressDialog = new ProgressDialog(SelectUpdateUrls.this);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Please wait...");
               /* progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMax(100);
                progressDialog.setProgress(0);*/
                //progressDialog.setCancelable(false);
                progressDialog.show();


            } catch (Exception e) {
                Toast.makeText(SelectUpdateUrls.this, "error loading progress dialog, try again", Toast.LENGTH_SHORT).show();

            }


        }

        protected String doInBackground(String... params) {
            try {

                UrlTable.deleteAll(UrlTable.class);
                UrlTable urlTable =new UrlTable(base_url, stage_name);
                urlTable.save();

                /*Intent intent = new Intent(SelectUpdateUrls.this, LoginActivity.class);
                startActivity(intent);
                finish();*/


            } catch (Exception e) {
                Log.d("error saving data", e.toString());
                Toast.makeText(SelectUpdateUrls.this, "error saving", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            progressDialog.dismiss();

        }



    }
}