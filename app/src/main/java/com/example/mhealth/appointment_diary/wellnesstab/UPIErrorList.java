package com.example.mhealth.appointment_diary.wellnesstab;

import static android.R.layout.simple_spinner_item;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.Dialogs.Dialogs;
import com.example.mhealth.appointment_diary.ProcessReceivedMessage.ProcessMessage;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.adapter.UpiErrAdapter;
import com.example.mhealth.appointment_diary.appointment_diary.AppCal;
import com.example.mhealth.appointment_diary.appointment_diary.AppointmentAdapter;
import com.example.mhealth.appointment_diary.appointment_diary.AppointmentModel;
import com.example.mhealth.appointment_diary.appointment_diary.CalAdapter;
import com.example.mhealth.appointment_diary.appointment_diary.CalModel;
import com.example.mhealth.appointment_diary.appointment_diary.FetchAppointment;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.config.SelectUpdateUrls;
import com.example.mhealth.appointment_diary.loginmodule.LoginActivity;
import com.example.mhealth.appointment_diary.models.UpiErrModel;
import com.example.mhealth.appointment_diary.sendmessages.SendMessage;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.example.mhealth.appointment_diary.tables.urlModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UPIErrorList extends AppCompatActivity {

    private boolean isSearchOpened = false;
    private EditText edtSeach;
    String passedUname,passedPassword;
    FloatingActionButton fab;
    TextView appCounterTxtV;
    private ProgressDialog progress;
    Dialogs dialogs;
    JSONArray jsonarray;

    long  diffdate;
    String z, dates, phone;
    private AppointmentAdapter myadapt;
    private List<UpiErrModel> mymesslist = new ArrayList<>();
    private List<UpiErrModel> upilist= new ArrayList<>();

    UpiErrAdapter upiErrAdapter1;

    CheckInternet chkInternet;
    AccessServer acs;

    ListView listView;
    ArrayAdapter arrayAdapter;
    EditText input;
    int appointmentCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upierror_list);
        initialise();

        try{
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("UPI Client's Lists with Errors");

        }
        catch(Exception e){

        }

        //upilist= new ArrayList<>();
       // upiErrAdapter1 =new UpiErrAdapter(UPIErrorList.this, upilist);
        dialogs=new Dialogs(UPIErrorList.this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ApiCall();
                postapi();

            }
        });
    }

    public void initialise(){

        try{

            appCounterTxtV=(TextView) findViewById(R.id.appointmentcount);
            chkInternet=new CheckInternet(UPIErrorList.this);
            appointmentCounter=0;
            acs=new AccessServer(UPIErrorList.this);
            fab=(FloatingActionButton) findViewById(R.id.fabtodays);
            passedUname="";
            passedPassword="";
            listView = (ListView)findViewById(R.id.messages);
            //messages = (ListView) findViewById(R.id.messages);
            input = (EditText) findViewById(R.id.input);

        }
        catch(Exception e){

        }
    }

    public void ApiCall(){
        upilist.clear();
        List<Activelogin> al=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin limit 1");
        for(int x=0;x<al.size();x++) {
            String myuname = al.get(x).getUname();
            List<Registrationtable> myl = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", myuname);
            for (int y = 0; y < myl.size(); y++) {

                phone = myl.get(y).getPhone();

            }
        }


        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }

        //

        //Create json array for filter
        /*JSONArray array = new JSONArray();

        //Create json object
        JSONObject jsonParam = new JSONObject();

        try {
            //Add string params
            jsonParam.put("phone_no",phone);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        array.put(jsonParam);

        // String urls ="https://ushauriapi.kenyahmis.org/appnt/applist?telephone=0746537136 &start="+dates;

        String urls ="?phone_no="+phone;
        String tt ="https://ushauriapi.kenyahmis.org/mohupi/geterrorlist";
       // ?telephone=
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.POST, tt, array, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(UPIErrorList.this, "success"+response, Toast.LENGTH_SHORT).show();

                if (response.length() == 0) {
                    upilist.clear();
                    upiErrAdapter1.notifyDataSetChanged();
                    dialogs.showErrorDialog("No Clients", "Response");
                    //textView1.setVisibility(View.GONE);

                } else {


                    Log.d("resposee", response.toString());
                    for (int x = 0; x < response.length(); x++) {
                        //  Toast.makeText(AppCal.this, String.valueOf(response.length()), Toast.LENGTH_SHORT).show();
                        //textView1.setVisibility(View.VISIBLE);
                        //textView1.setText("Total appointments on"+ " "+ datex + " " +" is "+ String.valueOf(response.length()));


                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String clinic_number = jsonObject.getString("clinic_number");
                            String f_name = jsonObject.getString("f_name");
                            String m_name = jsonObject.getString("m_name");
                            String l_name = jsonObject.getString("l_name");
                            String  phone_no = jsonObject.getString(" phone_no");
                            String upi_no = jsonObject.getString("upi_no");
                            String file_no = jsonObject.getString("file_no");
                           /* String appointment_status = jsonObject.getString("appointment_status");
                            String notification = jsonObject.getString("notification");*/

                            //Toast.makeText(AppCal.this, "success", Toast.LENGTH_SHORT).show();


                           /*UpiErrModel upiErrModel = new UpiErrModel(clinic_number, f_name, m_name, l_name,  phone_no, upi_no, file_no);
                            upilist.add(upiErrModel);

                            listView.setAdapter(upiErrAdapter1);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                // JSONObject jsonObject = new JSONObject(response.get);
                //                                                                                                                                                                                                                                                                                                                                        String

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("serverErr", error.toString());
                Toast.makeText(UPIErrorList.this, "Server Connection Error", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone_no", "0718373569");
                //params.put("start", dates);*/
               /* return params;
            }

            //Important part to convert response to JSON Array Again
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                String responseString;
                JSONArray array = new JSONArray();
                if (response != null) {

                    try {
                        responseString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        JSONObject obj = new JSONObject(responseString);
                        (array).put(obj);
                    } catch (Exception ex) {
                    }
                }
                //return array;
                return Response.success(array, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        /*jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                800000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
       /* RequestQueue requestQueue = Volley.newRequestQueue(UPIErrorList.this);
        requestQueue.add(jsonArrayRequest);/*
    }

    public void setToolbar() {
        /*try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setTitle("Appointments calender");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch(Exception e){


        }*/



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch(id){
            case R.id.action_search2:
                handleMenuSearch();
                return true;

            case R.id.logout:
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(i);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
//            mSearchAction.setIcon(getResources().getDrawable(R.mipmap.search));

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            edtSeach.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //Toast.makeText(getApplicationContext(), "searching", Toast.LENGTH_SHORT).show();


                    doSearching(s);
                  //  int x = listView.getCount();
                    // textView1.setText("Total appointments"+ " "+ String.valueOf(x));
                   // upiErrAdapter1.notifyDataSetChanged();
                    //myadapt.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {
                  //  upiErrAdapter1.notifyDataSetChanged();

                   // int x = listView.getCount();
                    //textView1.setText("Total appointments"+ " "+ String.valueOf(x));

                }
            });

            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);



            //add the close icon
//            mSearchAction.setIcon(getResources().getDrawable(R.mipmap.cancel));

            isSearchOpened = true;
        }
    }

    public void doSearching(CharSequence s){
        //refreshSmsInbox();
        try {

            upiErrAdapter1.getFilter().filter(s);
           // upiErrAdapter1.notifyDataSetChanged();

            //Toast.makeText(getApplicationContext(), "searching appointments"+s, Toast.LENGTH_SHORT).show();
            // myadapt.getFilter().filter(s.toString());
            //myadapt.filter.performFiltering(s.toString());
        }
        catch(Exception e){

            Toast.makeText(getApplicationContext(), "unable to filter: "+e, Toast.LENGTH_SHORT).show();
        }

    }

    public  void postapi() {

        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("phone_no", "0718373569");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonArray.put(jsonObject);

        AndroidNetworking.post("https://ushauriapi.kenyahmis.org/mohupi/geterrorlist")
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Connection","keep-alive")
                .addHeaders("Accept", "application/json")
                .addJSONObjectBody(jsonObject) // posting json
                .setPriority(Priority.MEDIUM)
                .addJSONObjectBody(jsonObject)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("", response.toString());

                //Toast.makeText(UPIErrorList.this, "success"+response, Toast.LENGTH_SHORT).show();
                upilist= new ArrayList<>();

                try {

                    JSONArray jsonArray1 =response.getJSONArray("results");

                    for (int i =0; i<jsonArray1.length(); i++){
                        JSONObject jsonObject = jsonArray1.getJSONObject(i);
                        String  errorDescription = jsonObject.getString("errorDescription");
                        String nascopCccNumber = jsonObject.getString("nascopCccNumber");
                        String clientNumber = jsonObject.getString("clientNumber");



                        upiErrAdapter1 =new UpiErrAdapter(UPIErrorList.this, upilist);



                        UpiErrModel upiErrModel = new UpiErrModel(clientNumber, errorDescription,nascopCccNumber);
                         //upilist=new ArrayList<>();
                            upilist.add(upiErrModel);

                            listView.setAdapter(upiErrAdapter1);






                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }




            @Override
            public void onError(ANError anError) {

                Log.d("", anError.getErrorDetail());

            }
        });



    }
}