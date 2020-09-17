package com.example.mhealth.appointment_diary.pmtct;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

    RequestQueue queue;

    private String phone_no;

    String[] mother_child = {"Please select appointment client","Mother","Child"};
    String[] appnment = {"Please select appointment type","Re-Fill","Clinical review","Enhanced Adherence counseling","Lab investigation","VL Booking","Other"};






//    @BindView(R.id.fab_add_exposure)
//    FloatingActionButton fab_add_exposure;

    @BindView(R.id.mfl_code)
    EditText mfl_code;

    @BindView(R.id.ccc_no)
    EditText ccc_no;

    @BindView(R.id.btn_check)
    Button btn_check;

    @BindView(R.id.mother_child_layout)
    LinearLayout mother_child_layout;

    @BindView(R.id.mother_child_apt_spinner)
    Spinner mother_child_apt_spinner;

    @BindView(R.id.normal_tca_layout)
    LinearLayout normal_tca_layout;

    @BindView(R.id.appointment_date)
    EditText appointment_date;

    @BindView(R.id.appointment_type_spinner)
    Spinner appointment_type_spinner;

    @BindView(R.id.other_et)
    EditText other_et;

    @BindView(R.id.btn_submit_apt)
    Button btn_submit_apt;


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


        ArrayAdapter<String> motherChildAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mother_child);
        motherChildAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mother_child_apt_spinner.setAdapter(motherChildAdapter);

        ArrayAdapter<String> aptAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, appnment);
        aptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appointment_type_spinner.setAdapter(aptAdapter);



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




}
