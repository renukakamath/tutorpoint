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

public class Addtocart extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String[] course,rate,duration,value,img,date ,statu,course_id,total,ordermaster_id;
    Button b1;
    public static String tot,oid,amt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtocart);
        l1=(ListView) findViewById(R.id.list);
        b1=(Button) findViewById(R.id.button2);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Addtocart.this;
        String q = "/Viewcarts?log_id=" +sh.getString("log_id", "");
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




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Makepayment.class));
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

                    total[i] = ja1.getJSONObject(i).getString("total");

                    ordermaster_id[i] = ja1.getJSONObject(i).getString("ordermaster_id");


                    b1.setText(total[i]+"Rs");
                    SharedPreferences.Editor e = sh.edit();
                    e.putString("total", total[i]);
                    e.commit();
                      e.putString("ordermaster_id", ordermaster_id[i]);
                      e.commit();

                    value[i] =  "\ncourse:" + course[i] + "\nrate:" + rate[i] + "\nduration:" + duration[i] + "status:" + statu[i] + "\ndate:" + date[i] ;

                }
                Custimage1 a = new Custimage1(this,course,rate,duration,img, statu, date);
                l1.setAdapter(a);


                   b1.setVisibility(View.VISIBLE);
            } else{
                b1.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"b1.setVisibility(View.GONE)", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        tot=total[i];
        oid=ordermaster_id[i];
        amt=rate[i];


        final CharSequence[] items = {"delete","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Addtocart.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("view syllabus")) {
                    startActivity(new Intent(getApplicationContext(), Viewsyllabus.class));

                }
                else if (items[item].equals("delete")) {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Addtocart.this;
                    String q = "/deletesss?log_id=" +sh.getString("log_id", "")+"&oid="+oid+"&total="+amt;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

                    Toast.makeText(getApplicationContext(), "Successfully delete", Toast.LENGTH_LONG).show();

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