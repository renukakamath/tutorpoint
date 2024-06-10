package com.example.tutor_point;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Rating;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class viewrequest extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String[] course,rate,date,statu,duration,value,request_id,course_id;
    public static  String rid,amt,cid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrequest);

        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) viewrequest.this;
        String q = "/viewrequest?log_id=" +sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                date = new String[ja1.length()];
                statu= new String[ja1.length()];
                course= new String[ja1.length()];
                course_id= new String[ja1.length()];
                rate= new String[ja1.length()];
                duration= new String[ja1.length()];
                request_id= new String[ja1.length()];

                value = new String[ja1.length()];



                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    date[i] = ja1.getJSONObject(i).getString("date");
                    statu[i] = ja1.getJSONObject(i).getString("status");
                    course[i] = ja1.getJSONObject(i).getString("course");

                    course_id[i] = ja1.getJSONObject(i).getString("course_id");
                    rate[i] = ja1.getJSONObject(i).getString("rate");
                    duration[i] = ja1.getJSONObject(i).getString("duration");
                    request_id[i] = ja1.getJSONObject(i).getString("request_id");




                    value[i] =  "course:" + course[i] + "\nrate:" + rate[i] + "\nduration:" + duration[i] +"\ndate:" + date[i] + "\nstatus:" + statu[i]   ;

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);

                l1.setAdapter(ar);

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

       amt=rate[i];
        rid=request_id[i];
        cid=course_id[i];
        final CharSequence[] items = {"Make Payment","view syllabus","view tutor details","view class","send complaints","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(viewrequest.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Make Payment")) {
                    startActivity(new Intent(getApplicationContext(), Makepayment.class));
                }
                else  if (items[item].equals("view syllabus")) {
                    startActivity(new Intent(getApplicationContext(), Viewsyllabus.class));

                }
                else if (items[item].equals("view tutor details")) {
                    startActivity(new Intent(getApplicationContext(), Viewtutordetails.class));
                }
                else if (items[item].equals("view class")) {
                    startActivity(new Intent(getApplicationContext(), Videos.class));
                }
                else if (items[item].equals("send complaints")) {

                    startActivity(new Intent(getApplicationContext(), Sendcomplaints.class));

                }





                else if (items[item].equals("Cancel")) {


                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),viewclass.class);
        startActivity(b);
    }
}