package com.example.tutor_point;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Userhome extends AppCompatActivity {
    Button b1,b2,b3,b4,b5,b6,b7,b8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);

//        b1=(Button)findViewById(R.id.offers);
//        b2=(Button)findViewById(R.id.rules);
//        b3=(Button)findViewById(R.id.rate);
//        b4=(Button)findViewById(R.id.logout);
//        b5=(Button) findViewById(R.id.profile);






//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),viewclass.class));
//            }
//        });
//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),Addtocart.class));
//            }
//        });
//        b3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), Vieworders.class));
//            }
//        });
//
//        b4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),Login.class));
//            }
//        });
//        b5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),Updateprofile.class));
//            }
//        });


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
//                    case R.id.mihomee:
//                        startActivity(new Intent(getApplicationContext(),viewclass .class));
//                        overridePendingTransition(0, 0);
//                        return true;


                }
                return false;
            }

        });



    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Userhome.class);
        startActivity(b);
    }
    }
