package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.mhealthkenya.psurvey.LastConsent;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.activities.auth.LoginActivity;
import com.mhealthkenya.psurvey.adapters.UploadDataAdapter;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.fragments.HomeFragment;
import com.mhealthkenya.psurvey.fragments.Test;
import com.mhealthkenya.psurvey.models.ActiveSurveys;
import com.mhealthkenya.psurvey.models.UploadModel;
import com.mhealthkenya.psurvey.models.UrlTable;
import com.mhealthkenya.psurvey.models.auth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.Unbinder;

public class UploadedActivity extends Fragment implements UploadDataAdapter.OnItemClickListener {

    boolean isSearchOpened = false;
    private EditText edtSearch;
    private ActionBar actionBar;
    private EditText edtxt_search1;
    JSONArray jsonArray1;

    private Context context;
   /* @BindView(R.id.fabtodays)
    FloatingActionButton fab;*/


    RelativeLayout cod;


    ListView listView;


   /* @BindView(R.id.btn_patient_consent)
    Button btn_patient_consent;

    @BindView(R.id.tv_patient_name)
    MaterialTextView tv_patient_name;*/



   //Toolbar toolbar1;

//Context context;

    String passedUname,passedPassword;
    //FloatingActionButton fab;
   // RelativeLayout cod;

     auth loggedInUser;
    TextView appCounterTxtV;
     ProgressDialog progress;
  //  Dialogs dialogs;
    JSONArray jsonarray;

    long  diffdate;
    String z, dates, phone;

     List<UploadModel> mymesslist = new ArrayList<>();
    List<UploadModel> upilist= new ArrayList<>();

    UploadDataAdapter upiErrAdapter1;
  //  ListView listView;
    ArrayAdapter arrayAdapter;
    EditText input;
    int appointmentCounter;
    int MFLcode;

    private Unbinder unbinder;
    private View root;
   ActiveSurveys activeSurveys;

    View rootView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.activity_uploaded, container, false);

        TextInputLayout textInputLayout = rootView.findViewById(R.id.searchL);
        edtxt_search1 = rootView.findViewById(R.id.edtxt_search);
        textInputLayout.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
        textInputLayout.setEndIconDrawable(R.drawable.search);
        textInputLayout.setEndIconContentDescription("Search");


        setHasOptionsMenu(true); // Important for menu creation

        // Initialize views and action bar
        actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

       // Assuming your toolbar is defined in your_fragment_layout
       /* try {
            Toolbar toolbar = rootView.findViewById(R.id.toolbar);
            ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Today Appointments");
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } catch (Exception e) {
            // Handle any exceptions here
        }*/
       /* Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);*/

        FloatingActionButton fab=rootView.findViewById(R.id.fabtodays);

        cod=rootView.findViewById(R.id.coordinate);
        listView= rootView.findViewById(R.id.messages);









      /*  Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        assert getArguments() != null;
        activeSurveys = (ActiveSurveys) getArguments().getSerializable("questionnaire");

       /* Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            activeSurveys = (ActiveSurveys) arguments.getSerializable("questionnaire");
        }*/
        MFLcode = Stash.getInt(String.valueOf(Constants.MFL_CODE));

        Log.d("activeSurveys ", String.valueOf(activeSurveys.getId()));
        Log.d("MFLCODEEE", String.valueOf(MFLcode));
       // Toast.makeText(UploadedActivity.this, "MFL CODE: "+MFLcode, Toast.LENGTH_SHORT).show();

        passedUname="";
        passedPassword="";



        postapi();
        edtxt_search1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                doSearching(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ApiCall();
                postapi();



            }
        });
        return rootView;
    }
   /* public void initialise(){

        try{



            passedUname="";
            passedPassword="";
            listView = (ListView)findViewById(R.id.messages);
            cod = findViewById(R.id.coordinate);
        }
        catch(Exception e){

        }
    }*/
   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main2, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }*/


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }*/
 /*  @Override
   public boolean onOptionsItemSelected(MenuItem item) {
       int id = item.getItemId();

       switch(id){


          /* case android.R.id.home: // Handle back button press
               if (isSearchOpened) {
                   // Clear focus from the EditText
                   if (edtSearch != null) {
                       edtSearch.clearFocus();
                   }
                   // Hide the soft keyboard
                   InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                   imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
                   isSearchOpened = false;
                   return true;
               }*/

        /*   case R.id.logout:
               Intent i = new Intent(requireActivity(), LoginActivity.class);
               i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
               i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

               startActivity(i);
               requireActivity().finish();
               return true;
       }

       return super.onOptionsItemSelected(item);
   }*/


   /* @Override
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
                Intent i = new Intent(UploadedActivity.this, LoginActivity.class);
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(i);
                //finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

   /* protected void handleMenuSearch(){
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
                    doSearching(s.toString());
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
    }*/

    protected void handleMenuSearch() {
        if (isSearchOpened) {
            actionBar.setDisplayShowCustomEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);

            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);

            isSearchOpened = false;
        } else {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(R.layout.search_bar);
            actionBar.setDisplayShowTitleEnabled(false);

            edtSearch = actionBar.getCustomView().findViewById(R.id.edtSearch);

            edtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    doSearching(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });

            edtSearch.requestFocus();

            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSearch, InputMethodManager.SHOW_IMPLICIT);

            isSearchOpened = true;
        }
    }


    public void doSearching(CharSequence s){
        //refreshSmsInbox();
        try {

            upiErrAdapter1.getFilter().filter(s.toString());
            // upiErrAdapter1.notifyDataSetChanged();

            //Toast.makeText(getApplicationContext(), "searching appointments"+s, Toast.LENGTH_SHORT).show();
            // myadapt.getFilter().filter(s.toString());
            //myadapt.filter.performFiltering(s.toString());
        }
        catch(Exception e){

            Toast.makeText(context, "unable to filter: "+e, Toast.LENGTH_SHORT).show();
        }

    }

    public  void postapi() {
        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);

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

//     /99/12345/


        AndroidNetworking.get(z+Constants.UPLOADEDDATA+activeSurveys.getId()+"/"+MFLcode)
                .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setPriority(Priority.LOW)

                .build().getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("SERVER RESPONSE", response.toString());

                        String test = response.toString();

                        //Toast.makeText(UPIErrorList.this, "success"+response, Toast.LENGTH_SHORT).show();
                        upilist = new ArrayList<>();

                        try {

                           jsonArray1 = response.getJSONArray("data");
                            if (jsonArray1.length()==0){
                                Toast.makeText(context, "No Data for Clients", Toast.LENGTH_SHORT).show();
                            }

                            for (int i = 0; i < jsonArray1.length(); i++) {
                                JSONObject jsonObject = jsonArray1.getJSONObject(i);

                                int id = jsonObject.getInt("id");
                                String ccc_number = jsonObject.getString("ccc_number");
                                int mfl_code = jsonObject.getInt("mfl_code");
                                boolean has_completed_survey = jsonObject.getBoolean("has_completed_survey");
                                int questionnaire = jsonObject.getInt("questionnaire");


                               upiErrAdapter1 = new UploadDataAdapter(getActivity(), upilist, UploadedActivity.this::onItemClick);


                                UploadModel uploadModel = new UploadModel(id, ccc_number, mfl_code, has_completed_survey, questionnaire);
                                //upilist=new ArrayList<>();
                                upilist.add(uploadModel);

                                listView.setAdapter(upiErrAdapter1);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                    @Override
                    public void onError(ANError anError) {

                        Toast.makeText(getActivity(), anError.getMessage(), Toast.LENGTH_LONG).show();

                        Log.d("Eror", anError.getMessage());

                    }
                });
    }

    @Override
    public void onItemClick(String position) {

        Toast.makeText(getActivity(), position, Toast.LENGTH_LONG).show();
        Bundle bundle = new Bundle();
        bundle.putSerializable("questionnaire", activeSurveys);
        bundle.putString("CCCNUMBER", position);
       // bundle.putSerializable("questionnaire", position);
        Navigation.findNavController(rootView).navigate(R.id.lastConsent2, bundle);


       /* Bundle bundle = new Bundle();
       // bundle.putString("CCCNUMBER", position);
 //       bundle.putSerializable("questionnaire1",  String.valueOf(activeSurveys.getId()));
       // set Fragmentclass Arguments
      LastConsent fragobj = new LastConsent();
     // Test fragobj = new Test();
 //       fragobj.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.testid, fragobj); // Assuming R.id.fragment_container is the ID of your fragment container in the layout
       // transaction.addToBackStack(null); // If you want to add the transaction to the back stack
        transaction.commit();*/

    }
}