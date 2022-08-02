package com.example.mhealth.appointment_diary.config;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
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


    /* ArrayList<urlModel> urlModelArrayList;
    ArrayList<String> names;*/
    urlModel url_Model;
    ArrayList<String> urlModelArrayList;
    ArrayList<urlModel> names;
     //ArrayList<String> names = new ArrayList<String>();
    Spinner spinner1;

    int dataId;

    String base_url;

    SharedPreferences sharedPreferences1;

    Button btn_prcd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_urls);

        btn_prcd = findViewById(R.id.login_proceed);

        btn_prcd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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
           names = new ArrayList<>();

           urlModelArrayList.clear();
           names.clear();


                for (int i=0; i<response.length(); i++){

                   // urlModel url_Model = new urlModel();
                    JSONObject jsonObject =response.getJSONObject(i);

                   /* url_Model.setUrl(jsonObject.getString("url"));
                    url_Model.setStage(jsonObject.getString("stage"));
                    url_Model.setId(jsonObject.getInt("id"));*/


                    int url_id = jsonObject.getInt("id");
                    String url_stage =jsonObject.getString("stage");
                    String main_urls =jsonObject.getString("url");

                   url_Model = new urlModel(url_id, url_stage, main_urls);
                    //urlModelArrayList.add(url_Model);

                    names.add(url_Model);
                    urlModelArrayList.add(url_Model.getStage());

                }

               names.add(new urlModel(0, "", "--select baseURL--"));
               urlModelArrayList.add("--select baseURL--");


                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SelectUrls.this, simple_spinner_item, urlModelArrayList);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner1.setAdapter(spinnerArrayAdapter);
                //removeSimpleProgressDialog();

               spinner1.setSelection(spinnerArrayAdapter.getCount()-1);
               dataId =names.get(spinnerArrayAdapter.getCount()-1).getId();

               //
              /* ArrayList<String> dataList;   //names
               ArrayList<data>  datas;      //urlModelArrayList
               public static int dataID;*/

               //


               //onSelct

               spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                   @Override
                   public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                       dataId = names.get(position).getId();

                       if (dataId==1){

                           base_url = names.get(position).getUrl();


                           Toast.makeText(SelectUrls.this, base_url, Toast.LENGTH_LONG).show();

                          // Toast.makeText(SelectUrls.this, "zero", Toast.LENGTH_LONG).show();
                       }
                       else if(dataId==2){

                         base_url = names.get(position).getUrl();
                           Toast.makeText(SelectUrls.this, base_url, Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(SelectUrls.this);
     requestQueue.add(jsonArrayRequest);
    }
}