package com.example.tutor_point;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class Rating extends AppCompatActivity implements JsonResponse {
    RatingBar ratingbar;
    String rat, status, revtable,rev;
    TextView e1;
    EditText e2;
    Button B1;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ratingbar = (RatingBar) findViewById(R.id.rating);
        e1 = (TextView) findViewById(R.id.ratingText);
        e2=(EditText) findViewById(R.id.review) ;
        B1=(Button) findViewById(R.id.rate);

        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {

                e1.setText("Your Rating :\t" + rating);
                rat = rating+"";


            }
        });


        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                e1.setText("Your Rating :\t" + rat);
                rev=e2.getText().toString();


                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) Rating.this;
                String q = "/rate?rating=" + rat + "&log_id=" + sh.getString("log_id", "")+"&review="+rev +"&oid="+Vieworders.oid;
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
                Toast.makeText(getApplicationContext(), " SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Userhome.class));

            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
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