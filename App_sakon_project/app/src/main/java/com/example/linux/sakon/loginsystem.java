package com.example.linux.sakon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

/**
 * Created by linux on 12/11/58.
 */


import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class loginsystem extends AppCompatActivity {

    private android.os.Bundle savedInstanceState;
    public Intent intent_login;

    public ImageView imageView20;  //ดัชนีมวลกาย body_weight

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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


      //  Toast.makeText(getApplicationContext(), user_type_id + " | " + us+" | " + ps  ,Toast.LENGTH_LONG).show(); //test user_type_name


/*

        //  Toast.makeText(getApplicationContext(),  user_type_name  ,Toast.LENGTH_LONG).show(); //test user_type_name
        if( user_type_name.equals("3"))
        {
            Toast.makeText(getApplicationContext(),  user_type_name  ,Toast.LENGTH_LONG).show(); //test user_type_name
        }
*/

       // imageView20.setLongClickable(false); //ดัชนีมวลกาย



        final ImageView imgViewI20=(ImageView) findViewById(R.id.imageViewI20);//ดัชนีมวลกาย
        imgViewI20.setLongClickable(true);


        imgViewI20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        /* if  ถ้รามีการ login เข้ามาแล้ว */
                if( user_type_id.equals("3"))  //สมมุติว่าผ่านการ login
                {
                    imgViewI20.setLongClickable(false);
                    // Toast.makeText(getApplicationContext(),  user_type_name  ,Toast.LENGTH_LONG).show(); //test user_type_name

               /*
            Intent intent=new Intent(MainActivity.this,loginsystem.class);
            intent.putExtra("user_type_id", "3");
            intent.setData(Uri.parse(url6));
            startActivity(intent);
            */

                    Intent  intent_login=new Intent(loginsystem.this,body.class);
                   // intent_login.putExtra("user_type_id", "3");
                    intent_login.putExtra("user_type_id",user_type_id);
                    intent_login.putExtra("us",us.toString());
                    intent_login.putExtra("ps",ps.toString());
                    startActivity(intent_login);

                }
        /* if */


            }
        });

        final ImageView imageViewI22=(ImageView)findViewById(R.id.imageViewI22);
        imageViewI22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(),  "test function"  ,Toast.LENGTH_LONG).show(); //check int

                sendMentalHealth( user_type_id, us, ps);

            }
        });


        final Button btnNextPage2=(Button)findViewById(R.id.btnNextPage2);
        btnNextPage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),  "test nextpage"  ,Toast.LENGTH_LONG).show(); //check int


                if( user_type_id.equals("3")  )
                {
                    Intent intent = new Intent(loginsystem.this, page3system.class);
                    intent.putExtra("user_type_id", user_type_id );

                            /*
                            request.addProperty("txtUsername", txtUser.getText().toString());
                            request.addProperty("txtPassword", txtPass.getText().toString());
                            */

                    intent.putExtra("us",us);
                    intent.putExtra("ps",ps);


                    startActivity(intent);
                }
                else
                {
                    // Toast.makeText(getApplicationContext(),  "Login ไม่สำเร็จ"  ,Toast.LENGTH_LONG).show(); //check int
                    Intent intent = new Intent(loginsystem.this, loginsystem.class);
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
        Intent  intent_login=new Intent(loginsystem.this,MentalHealth.class);
        // intent_login.putExtra("user_type_id", "3");
        intent_login.putExtra("user_type_id",user_type_id);
        intent_login.putExtra("us",us.toString());
        intent_login.putExtra("ps",ps.toString());
        startActivity(intent_login);
    }


}
