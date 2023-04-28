package com.mhealth.nishauri.utils;

import static android.R.layout.simple_spinner_item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mhealth.nishauri.Activities.Auth.LoginActivity;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.Models.urlModel;
import com.mhealth.nishauri.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectUrls extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PERMS_REQUEST_CODE=12345;
    private static final String COMMAND = "su 0 setenforce 0";

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
    public void onBackPressed() {
        //super.onBackPressed();
        finishAffinity();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_urls);

        if (!haveNetworkConnection()){
            Toast.makeText(SelectUrls.this, "Check your internet", Toast.LENGTH_LONG).show();
        }else{
            geturls1();
        }
        hasPermissions();
        requestPerms();
              /* try {
                        Runtime.getRuntime().exec(COMMAND);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
        setScreen();






        btn_prcd = findViewById(R.id.login_proceed);

        spinner1 =findViewById(R.id.spCompany);
        //  geturls1();


        btn_prcd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dataId!=7 && dataId!=8){
                    Toast.makeText(SelectUrls.this, "Invalid", Toast.LENGTH_LONG).show();
                }else {
                    new GetOperation().execute();

                }
            }
        });



    }


    private void setScreen(){
        try{
            SharedPreferences preferencesS = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
            String FirstTime = preferencesS.getString("FirstTimeInstall", "");

            if (FirstTime.equals("Yes")) {
                Intent intent1 = new Intent(SelectUrls.this, LoginActivity.class);
                startActivity(intent1);

            } else {
                SharedPreferences.Editor editor = preferencesS.edit();
                editor.putString("FirstTimeInstall", "Yes");
                editor.apply();
            }
        }catch(Exception e){

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


                    JSONArray jsonArray =response.getJSONArray("NISHAURI");

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


                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SelectUrls.this, simple_spinner_item, urlModelArrayList);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinner1.setAdapter(spinnerArrayAdapter);
                    //removeSimpleProgressDialog();

                    spinner1.setSelection(spinnerArrayAdapter.getCount()-1);
                    dataId =names.get(spinnerArrayAdapter.getCount()-1).getId();


                    spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            dataId = names.get(position).getId();

                            if (dataId==7){

                                base_url = names.get(position).getUrl();

                                stage_name =names.get(position).getStage();

                            }
                            else if(dataId==8){

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
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                800000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(SelectUrls.this);
        requestQueue.add(jsonObjectRequest);
    }


    private class GetOperation extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            try {

                progressDialog = new ProgressDialog(SelectUrls.this);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Please wait...");
               /* progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMax(100);
                progressDialog.setProgress(0);*/
                //progressDialog.setCancelable(false);
                progressDialog.show();


            } catch (Exception e) {
                Toast.makeText(SelectUrls.this, "error loading progress dialog, try again", Toast.LENGTH_SHORT).show();

            }


        }

        protected String doInBackground(String... params) {
            try {
                UrlTable.deleteAll(UrlTable.class);
                UrlTable urlTable =new UrlTable(base_url, stage_name);
                urlTable.save();

                progressDialog.dismiss();

                Intent intent = new Intent(SelectUrls.this, Constants.class);
                startActivity(intent);
                // finish();


            } catch (Exception e) {
                Log.d("error saving data", "error on server saving");
            }
            return null;
        }

        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            //Toast.makeText(SelectUrls.this, "progress dialog dismissed", Toast.LENGTH_SHORT).show();

        }


    }

    //try with catch


    private  void setscreen2() {
        try {
            SharedPreferences preferencesS = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
            String FirstTime = preferencesS.getString("FirstTimeInstall", "");

            if (FirstTime.equals("Yes")) {
                Intent intent1 = new Intent(SelectUrls.this, LoginActivity.class);
                startActivity(intent1);

            } else {
                SharedPreferences.Editor editor = preferencesS.edit();
                editor.putString("FirstTimeInstall", "Yes");
                editor.apply();
            }
        }catch (Exception e){

        }


    }






    private boolean hasPermissions(){


        int res = 0;

        String[] permissions = new String[]{
                Manifest.permission.INTERNET,
//                android.Manifest.permission.READ_SMS,
//                android.Manifest.permission.RECEIVE_SMS,
//                android.Manifest.permission.CALL_PHONE


        };

        for (String perms : permissions) {
            res = checkCallingOrSelfPermission(perms);

            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }

        }
        return true;


    }


    private void requestPerms(){

        String[] permissions=new String[]{
                Manifest.permission.INTERNET,
//                android.Manifest.permission.SEND_SMS,
//                android.Manifest.permission.READ_SMS,
//                android.Manifest.permission.RECEIVE_SMS,
        };

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(permissions,PERMS_REQUEST_CODE);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    getApplicationContext());
            builder.setAutoCancel(true);

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    //test internet
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
    }
