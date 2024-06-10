package com.example.tutor_point;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class viewcourse extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    EditText e1;
    SharedPreferences sh;
    String[] course,rate,duration,value,img,course_id;
    String search;

    public  static  String amt,cid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcourse);
        e1=(EditText)findViewById(R.id.search);

        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) viewcourse.this;
        String q = "/viewcourse?log_id=" +sh.getString("log_id", "")+"&cid="+viewclass.cid;
        q = q.replace(" ", "%20");
        JR.execute(q);

        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                search=e1.getText().toString();

                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) viewcourse.this;
                String q = "/Searchcourse?&search=" + search+"&cid="+viewclass.cid ;
                q = q.replace(" ", "%20");
                JR.execute(q);

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

                course= new String[ja1.length()];
                rate= new String[ja1.length()];
                duration= new String[ja1.length()];

                img= new String[ja1.length()];
                value = new String[ja1.length()];
                course_id= new String[ja1.length()];


                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {

                    course[i] = ja1.getJSONObject(i).getString("course");
                    rate[i] = ja1.getJSONObject(i).getString("rate");
                    duration[i] = ja1.getJSONObject(i).getString("duration");
                    img[i] = ja1.getJSONObject(i).getString("image");
                    course_id[i] = ja1.getJSONObject(i).getString("course_id");


                    value[i] =  "\ncourse:" + course[i] + "\nrate:" + rate[i] + "\nduration(month):" + duration[i]  ;

                }
               Custimage a = new Custimage(this,course,rate,duration,img);
                l1.setAdapter(a);
            }
            else {
                Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

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

        cid=course_id[i];
        final CharSequence[] items = {"view syllabus","Add to cart","Buy Now","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(viewcourse.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                 if (items[item].equals("view syllabus")) {
                    startActivity(new Intent(getApplicationContext(), Viewsyllabus.class));

                }
                else if (items[item].equals("Add to cart")) {

                     JsonReq JR = new JsonReq();
                     JR.json_response = (JsonResponse) viewcourse.this;
                     String q = "/addtocart?log_id=" +sh.getString("log_id", "")+"&cid="+cid+"&amt="+amt;
                     q = q.replace(" ", "%20");
                     JR.execute(q);

                     Toast.makeText(getApplicationContext(), "Successfully AddToCart", Toast.LENGTH_LONG).show();

                }

                 else if (items[item].equals("Buy Now")) {

                     JsonReq JR = new JsonReq();
                     JR.json_response = (JsonResponse) viewcourse.this;
                     String q = "/Buynowssss?log_id=" +sh.getString("log_id", "")+"&cid="+cid+"&amt="+amt;
                     q = q.replace(" ", "%20");
                     JR.execute(q);

                     Toast.makeText(getApplicationContext(), "Successfully Booked", Toast.LENGTH_LONG).show();

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
