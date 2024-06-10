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
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

public class viewclass extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    GridView l1;
    SharedPreferences sh;
    String[] category_id,course_category,value;

    public static String cid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewclass);
        l1=(GridView) findViewById(R.id.ltvideo);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) viewclass.this;
        String q = "/viewcoursecategory?log_id=" +sh.getString("log_id", "");
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
                category_id = new String[ja1.length()];
                course_category= new String[ja1.length()];



                value = new String[ja1.length()];



                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    category_id[i] = ja1.getJSONObject(i).getString("category_id");
                    course_category[i] = ja1.getJSONObject(i).getString("course_category");


                    value[i] =  "course category:" + course_category[i]   ;

                }
//                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);
                CustCatg ar = new CustCatg(this,course_category);
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

        cid=category_id[i];
        final CharSequence[] items = {"View Course","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(viewclass.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("View Course")) {
                    startActivity(new Intent(getApplicationContext(), viewcourse.class));
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
