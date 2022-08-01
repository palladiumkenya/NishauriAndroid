package com.example.mhealth.appointment_diary.config;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.tables.urlModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import static android.R.layout.simple_spinner_item;
public class SelectUrls extends AppCompatActivity {


     ArrayList<urlModel> urlModelArrayList;
     ArrayList<String> names = new ArrayList<String>();
    Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_urls);

         spinner1 =findViewById(R.id.spCompany);
         getUrls();



    }

    private void getUrls(){
        String URLstring = "https://ushaurinode.mhealthkenya.co.ke/config";
    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLstring, null, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {

            Log.d("", response.toString());
           // Toast.makeText(SelectUrls.this, response.toString(), Toast.LENGTH_LONG).show();

           try {
           urlModelArrayList = new ArrayList<>();
                for (int i=0; i<response.length(); i++){

                    urlModel url_Model = new urlModel();
                    JSONObject jsonObject =response.getJSONObject(i);

                    url_Model.setUrl(jsonObject.getString("url"));
                    url_Model.setStage(jsonObject.getString("stage"));

                    urlModelArrayList.add(url_Model);



                    //
                }
                for (int i = 0; i < urlModelArrayList.size(); i++){
                    names.add(urlModelArrayList.get(i).getUrl().toString());


                }
               // names.add("--Select baseURL--");
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SelectUrls.this, simple_spinner_item, names);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner1.setAdapter(spinnerArrayAdapter);
                //removeSimpleProgressDialog();


               //onSelct

               spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                   @Override
                   public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {




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
        RequestQueue requestQueue = Volley.newRequestQueue(SelectUrls.this);
     requestQueue.add(jsonArrayRequest);
    }
}