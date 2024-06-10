package com.example.tutor_point;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

public class Vieworders extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String[] course,rate,duration,value,img,date ,statu,course_id,total,ordermaster_id;
    Button b1;
    public static String tot,oid,amt,cid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vieworders);

        l1=(ListView) findViewById(R.id.ltvideo);

        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Vieworders.this;
        String q = "/Vieworders?log_id=" +sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);



        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomview);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.mihome);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.recipe:
                        startActivity(new Intent(getApplicationContext(), Updateprofile.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.mihome:
                        startActivity(new Intent(getApplicationContext(),viewclass .class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.favourite:
                        startActivity(new Intent(getApplicationContext(),Vieworders .class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.shop:
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.add:
                        startActivity(new Intent(getApplicationContext(),Addtocart .class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }

        });



    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                statu = new String[ja1.length()];
                date= new String[ja1.length()];
                course= new String[ja1.length()];
                rate= new String[ja1.length()];
                duration= new String[ja1.length()];

                img= new String[ja1.length()];
                value = new String[ja1.length()];
                course_id= new String[ja1.length()];
                total= new String[ja1.length()];
                ordermaster_id= new String[ja1.length()];


                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    statu[i] = ja1.getJSONObject(i).getString("status");
                    date[i] = ja1.getJSONObject(i).getString("date");
                    course[i] = ja1.getJSONObject(i).getString("course");
                    rate[i] = ja1.getJSONObject(i).getString("price");
                    duration[i] = ja1.getJSONObject(i).getString("duration");
                    img[i] = ja1.getJSONObject(i).getString("image");
                    course_id[i] = ja1.getJSONObject(i).getString("course_id");
                    total[i] = ja1.getJSONObject(i).getString("total");

                    ordermaster_id[i] = ja1.getJSONObject(i).getString("ordermaster_id");

                    value[i] =  "\ncourse:" + course[i] + "\nrate:" + rate[i] + "\nduration:" + duration[i] + "status:" + statu[i] + "\ndate:" + date[i] ;

                }
                Custimage1 a = new Custimage1(this,course,rate,duration,img, statu, date);
                l1.setAdapter(a);
            }else {
                Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        oid=ordermaster_id[i];
        cid=course_id[i];
        final CharSequence[] items = {"View class","Rating","Send complaint","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Vieworders.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Rating")) {
                    startActivity(new Intent(getApplicationContext(), Rating.class));

                }
                else if (items[item].equals("Send complaint")) {

                    startActivity(new Intent(getApplicationContext(), Sendcomplaints.class));
                }

                else if (items[item].equals("View class")) {


                    startActivity(new Intent(getApplicationContext(), Videos.class));
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
