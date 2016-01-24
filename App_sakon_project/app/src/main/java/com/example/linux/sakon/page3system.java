package com.example.linux.sakon;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by linux on 12/11/58.
 */

public class page3system extends AppCompatActivity {

    private Bundle savedInstanceState;
    public Intent intent_login;

    public ImageView imageView20;  //ดัชนีมวลกาย body_weight

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main3);
        //setContentView(R.layout.scroll_horizontal);
        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //user_type_name=3  ประชาชนทั่วไป  from  `user_type`

        intent_login=getIntent();
        final String  user_type_id=intent_login.getStringExtra("user_type_id");
        final String us=intent_login.getStringExtra("us");
        final String ps=intent_login.getStringExtra("ps");





final ImageView imageViewI30=(ImageView) findViewById(R.id.imageViewI30);


        imageViewI30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //Toast.makeText(getApplicationContext(), "testing ข้อมูลส่วนตัว", Toast.LENGTH_LONG).show(); //test user_type_name



                if( user_type_id.equals("3"))  //สมมุติว่าผ่านการ login
                {
                    Intent intent = new Intent(page3system.this, employee.class);
                    intent.putExtra("user_type_id", "3" );
                    // intent.putExtra("")
                    intent.putExtra("us","");
                    intent.putExtra("ps","");
                    startActivity(intent);

                }



            }
        });



final ImageView imageViewI32=(ImageView) findViewById(R.id.imageViewI32); //คัดกรอง ncdscreen
        imageViewI32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(), "testing คัดกรอง", Toast.LENGTH_LONG).show(); //test user_type_name

                if( user_type_id.equals("3"))  //สมมุติว่าผ่านการ login
                {


                    Intent intent = new Intent(page3system.this, ncdscreen.class);
                    intent.putExtra("user_type_id", "3" );
                    // intent.putExtra("")
                    intent.putExtra("us","");
                    intent.putExtra("ps","");
                    startActivity(intent);


                }

            }
        });




    }

    public void sendMentalHealth(String user_type_id,String us,String ps)
    {
        Intent  intent_login=new Intent(page3system.this,MentalHealth.class);
        // intent_login.putExtra("user_type_id", "3");
        intent_login.putExtra("user_type_id",user_type_id);
        intent_login.putExtra("us",us.toString());
        intent_login.putExtra("ps",ps.toString());
        startActivity(intent_login);
    }


}
