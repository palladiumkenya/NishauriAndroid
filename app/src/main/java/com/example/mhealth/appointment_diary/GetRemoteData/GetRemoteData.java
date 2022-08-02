package com.example.mhealth.appointment_diary.GetRemoteData;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.Progress.Progress;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.tables.Affiliationstable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class GetRemoteData {

    Context ctx;
    Progress pr;

    private JSONArray id_result;

    public GetRemoteData(Context ctx) {

        this.ctx = ctx;
        pr=new Progress(ctx);


    }



    public void getAffiliationData(){
//        Toast.makeText(ctx, "i am called", Toast.LENGTH_SHORT).show();

        pr.showProgress("loading, Please be patient this might take a while...");

        try{



            StringRequest stringRequest = new StringRequest(POST, Config.BASE_URL+Config.GETAFFILIATION_URL1,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {



                            if(response!=null){

                                JSONObject j = null;
                                try {
                                    j = new JSONObject(response);
                                    id_result = j.getJSONArray(Config.JSON_ARRAYAFFILIATIONS);

                                    getMyAffiliationData(id_result);



                                } catch (JSONException e) {
                                    e.printStackTrace();
//                                Toast.makeText(getActivity(), "error getting results "+e, Toast.LENGTH_SHORT).show();

                                }


                            }


                            pr.dissmissProgress();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            pr.dissmissProgress();


                            Toast.makeText(ctx, "Check your internet Connection!!!", Toast.LENGTH_SHORT).show();
                            System.out.println("*******error*** "+error);
                        }
                    })

            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
            requestQueue.add(stringRequest);


        }
        catch(Exception e){

            Toast.makeText(ctx, "error getting remote data "+e, Toast.LENGTH_SHORT).show();
        }
    }

    private void getMyAffiliationData(JSONArray j){
        System.out.println("******db data****************");
        Affiliationstable.deleteAll(Affiliationstable.class);
        int i=0;

        do{

            try {

                JSONObject json = j.getJSONObject(i);

                String affiliation_name=json.getString(Config.KEY_AFFILIATION_NAME);
                String affiliation_id=json.getString(Config.KEY_AFFILIATION_ID);


                Affiliationstable fd=new Affiliationstable();
                fd.setAffiliationid(affiliation_id);
                fd.setAffiliationname(affiliation_name);
                fd.save();
                i++;

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(ctx, "Internet connection required", Toast.LENGTH_SHORT).show();
            }


        }
        while(i<j.length());


    }
}
