package com.example.tutor_point;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

public class Updateprofile extends AppCompatActivity implements JsonResponse {
    EditText e1,e2,e3,e4,e5;
    Button b1;
    String name,place,phone,email,age;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText) findViewById(R.id.fname);
        e2=(EditText) findViewById(R.id.place);
        e3=(EditText) findViewById(R.id.phone);
        e4=(EditText) findViewById(R.id.email);
        e5=(EditText)findViewById(R.id.age) ;
        b1=(Button) findViewById(R.id.up);


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

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Updateprofile.this;
        String q = "/viewusers?lid="+sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=e1.getText().toString();
                place=e2.getText().toString();
                phone=e3.getText().toString();
                email=e4.getText().toString();
                age=e5.getText().toString();


                JsonReq JR = new JsonReq();
                JR.json_response=(JsonResponse) Updateprofile.this;
                String q = "/updateuser?login_id="+sh.getString("log_id", "")+"&name="+name+"&place="+place+"&Phone="+phone+ "&email="+email +"&age="+age;
                q=q.replace(" ","%20");
                JR.execute(q);
            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        try {
            String method=jo.getString("method");

            if(method.equalsIgnoreCase("viewusers")) {
                String status = jo.getString("status");
                Log.d("pearl", status);

                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    e1.setText(ja1.getJSONObject(0).getString("firstname"));
                    e2.setText(ja1.getJSONObject(0).getString("lastname"));
                    e3.setText(ja1.getJSONObject(0).getString("phone"));
                    e4.setText(ja1.getJSONObject(0).getString("email"));
                    e5.setText(ja1.getJSONObject(0).getString("place"));





                    SharedPreferences.Editor e = sh.edit();
                    //e.putString("log_id", logid);
                    e.commit();
                }
            }
            else if(method.equalsIgnoreCase("updateuser"))
            {
                try {
                    String status=jo.getString("status");
                    Log.d("pearl",status);


                    if(status.equalsIgnoreCase("success")){

                        Toast.makeText(getApplicationContext(), "UPDATED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(),Updateprofile.class));

                    }
                    else
                    {
                        startActivity(new Intent(getApplicationContext(),Updateprofile.class));
                        Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }

            }
//            else {
//                Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(getApplicationContext(), Login.class));
//            }
        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),viewclass.class);
        startActivity(b);
    }
}