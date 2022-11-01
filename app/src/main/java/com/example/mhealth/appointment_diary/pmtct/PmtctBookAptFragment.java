package com.example.mhealth.appointment_diary.pmtct;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.DCMActivity;
import com.example.mhealth.appointment_diary.Dialogs.ErrorMessage;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.adapter.AttachedHeisAdapter;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.config.VolleyErrors;
import com.example.mhealth.appointment_diary.models.Hei;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.google.android.material.snackbar.Snackbar;
import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;



public class PmtctBookAptFragment extends Fragment {
    private Unbinder unbinder;
    private View root;
    private Context context;
    public String  z;

    RequestQueue queue;

    private String phone_no;





    private AttachedHeisAdapter mAdapter;
    private ArrayList<Hei> heiArrayList;

//    @BindView(R.id.fab_add_exposure)
//    FloatingActionButton fab_add_exposure;

    @BindView(R.id.mfl_code)
    EditText mfl_code;

    @BindView(R.id.ccc_no)
    EditText ccc_no;

    @BindView(R.id.btn_check)
    Button btn_check;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.select_hei_txt)
    TextView select_hei_txt;



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
        root =  inflater.inflate(R.layout.pmtct_book_apt_fragment, container, false);
        unbinder = ButterKnife.bind(this, root);


        List<Activelogin> myl=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin");

        for(int x=0;x<myl.size();x++){
            String un=myl.get(x).getUname();
            List<Registrationtable> myl2=Registrationtable.findWithQuery(Registrationtable.class,"select * from Registrationtable where username=? limit 1",un);
            for(int y=0;y<myl2.size();y++){
                phone_no=myl2.get(y).getPhone();
            }
        }

        queue = Volley.newRequestQueue(context); // this = context

        heiArrayList = new ArrayList<>();
        mAdapter = new AttachedHeisAdapter(context, heiArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayout.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        recyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new AttachedHeisAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Hei hei = heiArrayList.get(position);

                HeiAptDialog bottomSheetFragment = HeiAptDialog.newInstance(mfl_code.getText().toString()+ccc_no.getText().toString(),hei, context);
                bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());

            }
        });




        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mfl_code.getText().toString())){
                    mfl_code.setError("Please enter MFL code");
                }else if (TextUtils.isEmpty(ccc_no.getText().toString())){
                    ccc_no.setError("Please enter CCC Number");
                }else {
                    getHeis();

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

    @Override
    public void onResume() {
        super.onResume();
//        shimmer_my_container.startShimmerAnimation();
    }

    @Override
    public void onPause() {
//        shimmer_my_container.stopShimmerAnimation();
        super.onPause();
    }

    private void getHeis() {
    try {
        List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
        if (_url.size()==1){
            for (int x=0; x<_url.size(); x++){
                z=_url.get(x).getBase_url1();
                //zz=_url.get(x).getStage_name1();
                // Toast.makeText(LoginActivity.this, "You are connected to" + " " +zz, Toast.LENGTH_LONG).show();
            }
        }
    }catch(Exception e){
        e.printStackTrace();
    }
       /* UrlTable _url = SugarRecord.findById(UrlTable.class, 1);
        String  z=  _url.base_url1;*/
        JSONObject payload = new JSONObject();
        try {
            payload.put("clinic_number", mfl_code.getText().toString()+ccc_no.getText().toString());
            payload.put("phone_no", phone_no);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("payload: ", payload.toString());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                z+Config.GET_ATTACHED_HEIS1, payload, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response: ", response.toString());
                try {
                    boolean success = response.has("success") && response.getBoolean("success");

                    if (success) {
                        select_hei_txt.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        heiArrayList.clear();

                        JSONArray heisArray = response.has("message") ? response.getJSONArray("message") : null;

                        assert heisArray != null;
                        if (heisArray.length() > 0){


                            for (int i = 0; i < heisArray.length(); i++) {

                                JSONObject item = (JSONObject) heisArray.get(i);


                                String hei_no = item.has("hei_no") ? item.getString("hei_no") : "";
                                String hei_dob = item.has("hei_dob") ? item.getString("hei_dob") : "";
                                String hei_first_name = item.has("hei_first_name") ? item.getString("hei_first_name") : "";
                                String hei_middle_name = item.has("hei_middle_name") ? item.getString("hei_middle_name") : "";
                                String hei_last_name = item.has("hei_last_name") ? item.getString("hei_last_name") : "";
                                String pcr_week6 = item.has("pcr_week6") ? item.getString("pcr_week6") : "";



                                Hei hei =new Hei(hei_no,hei_dob,hei_first_name,hei_middle_name,hei_last_name,pcr_week6);
                                heiArrayList.add(hei);
                                mAdapter.notifyDataSetChanged();

                            }

                        }else {

                            select_hei_txt.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.GONE);


                            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("No HEIs found","The client is not attached to any HEI at the moment",context);
                            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
                        }

                    } else {

                        select_hei_txt.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);


                        String message = response.has("message") ? response.getString("message") : "";

                        ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Invalid",message, context);
                        bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                NetworkResponse response = error.networkResponse;
                if(response != null && response.data != null){
                    String body;
                    //get status code here
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    if(error.networkResponse.data!=null) {
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");

                            JSONObject json = new JSONObject(body);
                            //                            Log.e("error response : ", json.toString());


                            String message = json.has("message") ? json.getString("message") : "";
                            String reason = json.has("reason") ? json.getString("reason") : "";

                            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance(message,reason,context);
                            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());

                        } catch (UnsupportedEncodingException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }else {

                    Log.e("VOlley error :", error.getLocalizedMessage()+" message:"+error.getMessage());
                    Toast.makeText(context, VolleyErrors.getVolleyErrorMessages(error, context),Toast.LENGTH_LONG).show();
                }

//             Log.e(TAG, "Error: " + error.getMessage());
            }
        }){
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        queue.add(jsonObjReq);
    }




}
