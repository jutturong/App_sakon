package com.example.linux.sakon;

import android.content.Intent;
import android.net.LocalSocketAddress;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.app.Activity;
import android.widget.Toast;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import org.json.JSONArray;
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
import java.io.StringWriter;


public class MainActivity extends AppCompatActivity {

    String  url6="http://203.157.177.121/nusoap/map.php?category=store"; //ร้านค้า
    String  url7="http://203.157.177.121/nusoap/map.php?category=water"; //แหล่งน้ำดิ่ม
    String  url8="http://203.157.177.121/nusoap/map_thematic.php?scope=47&data=dhf"; //เผ้าระวัง/ภัยสุขภาพ

    public  Intent intent;
    public EditText txtUser;
    public EditText txtPass;


/*
    //private final String NAMESPACE="http://192.168.2.112/webservice/chkLogin.php";
    public  String IP="http://192.168.2.112/";
    private final String NAMESPACE=  IP +  "webservice/chkLogin.php";
    private final String METHOD_NAME="loginMember";
    private final String URL= IP  +   "webservice/chkLogin.php?wsdl";
    private final String SOAP_ACTION =   IP  +  "webservice/WebServiceServer.php/HelloWorld";
*/


    public String IP="http://203.157.177.121/";
    private final String NAMESPACE=  IP +  "nusoap/ServerSide.php";
    private final String METHOD_NAME="user_type";
   // private final String METHOD_NAME="json_authen";

    private final String URL= IP  +   "nusoap/ServerSide.php?wsdl";
    private final String SOAP_ACTION =   IP  +  "nusoap/ServerSide.php/" + METHOD_NAME ;

    //http://203.157.177.121/nusoap/testClientJSON.php?function=user_type&strDatatype=json&txtUsername=poomjit&txtPassword=123456
    //private final String NAMESPACE="http://203.157.177.121/nusoap/testClientJSON.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // setContentView(R.layout.content_main);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final EditText txtUser=(EditText)findViewById(R.id.txtUsername);
        final EditText txtPass=(EditText)findViewById(R.id.txtPassword);
        final Button btnLogin=(Button)findViewById(R.id.btnLogin);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        final ImageView imgViewI6=(ImageView) findViewById(R.id.imageViewI6); //การตรวจร้านค้า
       // imgViewI6.setImageResource(R.drawable.i6);
        imgViewI6.setClickable(true);

                /*
                Toast.makeText(v.getContext(),
                       "test",Toast.LENGTH_LONG
                        ).show();
                 */



        imgViewI6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                rendermap(url6);

            }
        });


        final ImageView imgViewI7=(ImageView) findViewById(R.id.imageViewI7);//แหล่งน้ำ
        imgViewI7.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                rendermap(url7);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




/*
                // private final String NAMESPACE=  IP +  "webservice/chkLogin.php";
                // private final String METHOD_NAME="loginMember";
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("strUsername", txtUser.getText().toString());
                request.addProperty("strPassword", txtPass.getText().toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);

                envelope.setOutputSoapObject(request);

                // private final String URL= IP  +   "webservice/chkLogin.php?wsdl";
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                String resultServer=null;


                try {
                // private final String SOAP_ACTION =   IP  +  "webservice/WebServiceServer.php/HelloWorld";
                    androidHttpTransport.call(SOAP_ACTION, envelope);
                    SoapObject result=(SoapObject) envelope.bodyIn;
                    resultServer=result.getProperty(0).toString();



                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e)
                {
                    e.printStackTrace();
                }


                String strStatusID="0";
                String strMemberID="0";
                String strError="Unknow Status";

                 JSONObject c;
                try{
                    c=new JSONObject( resultServer);
                    strStatusID=c.getString("StatusID");
                    strMemberID=c.getString("MemberID");
                    strError=c.getString("Error");


                }catch (JSONException e)
                {
                    e.printStackTrace();
                }



                     if(  strStatusID.equals("1") )  //authentication login
                     {
                         Toast.makeText(getApplicationContext(),  strStatusID   ,Toast.LENGTH_LONG).show();
                         Intent newActivity=new Intent(MainActivity.this,loginsystem.class);
                         newActivity.putExtra("id_per",3);
                         startActivity(newActivity);

                     }
                else if(   strStatusID.equals("0") )
                     {
                         Toast.makeText(getApplicationContext(),  "Login false"  ,Toast.LENGTH_LONG).show();
                     }

*/


                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);



               // for test  user_type
              //  request.addProperty("txtUsername", txtUser.getText().toString());
              //  request.addProperty("txtPassword", txtPass.getText().toString());

                /*
                'strUsername'=>"ict",
                        'strPassword'=>"skko",
                        'strDatatype'=>"json",
                        'txtUsername'=>"poomjit",
                        'txtPassword'=>"123456",
                 */



                request.addProperty("strUsername", "ict"); //'strUsername'=>"ict",
                request.addProperty("strPassword", "skko"); //'strPassword'=>"skko",
                request.addProperty("strDatatype", "json"); //'strDatatype'=>"json",
                request.addProperty("txtUsername", txtUser.getText().toString());
                request.addProperty("txtPassword", txtPass.getText().toString());
               // request.addProperty("txtUsername", "poomjit");
               // request.addProperty("txtPassword", "123456");

                /*
                // for test  private final String METHOD_NAME="json_authen";
                request.addProperty("strUsername", "ict" );// 'strUsername'=>'ict',
                request.addProperty("strPassword", "skko" );//'strPassword'=>'skko'
                */


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);

                envelope.setOutputSoapObject(request);

                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                String resultServer=null;

                try {
                    // private final String SOAP_ACTION =   IP  +  "webservice/WebServiceServer.php/HelloWorld";
                    androidHttpTransport.call(SOAP_ACTION, envelope);
                    SoapObject result=(SoapObject) envelope.bodyIn;
                    resultServer=result.getProperty(0).toString();



                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e)
                {
                    e.printStackTrace();
                }

               /*
                String user_webservice_id="";
                String user_name="";
                String user_password="";
                String user_level_id="";
                */

                String user_type_id="";
                Integer  int_user_type_id=0;

              //  String row_check_user="";


                JSONObject c;

                try{
                    c=new JSONObject( resultServer);
                   // strStatusID=c.getString("StatusID");
                   // strMemberID=c.getString("MemberID");
                  //  strError=c.getString("Error");
                   // user_type_id=c.getString("user_type_id");

                   // row_check_user=c.getString("row_check_user");
                    user_type_id=c.getString("user_type_id"); //user_type_id
                    int_user_type_id=c.getInt("user_type_id");



                   // Toast.makeText(getApplicationContext(), c.toString()    ,Toast.LENGTH_LONG).show();

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }

                // Toast.makeText(getApplicationContext(),  user_type_id  ,Toast.LENGTH_LONG).show(); //ckeck string
                //  Toast.makeText(getApplicationContext(),  int_user_type_id.toString()  ,Toast.LENGTH_LONG).show(); //check int



                /*
                // send value  id=3 ประชาชนทั่วไป  จาก `user_type`
                Intent intent = new Intent(MainActivity.this, loginsystem.class);
                intent.putExtra("user_type_id", "3");
                //  intent.setData(Uri.parse(url6));
                startActivity(intent);
                */

                        if( int_user_type_id > 0 )
                        {
                            Intent intent = new Intent(MainActivity.this, loginsystem.class);
                            intent.putExtra("user_type_id", user_type_id );

                            /*
                            request.addProperty("txtUsername", txtUser.getText().toString());
                            request.addProperty("txtPassword", txtPass.getText().toString());
                            */

                            intent.putExtra("us",txtUser.getText().toString());
                            intent.putExtra("ps",txtPass.getText().toString());

                            startActivity(intent);
                        }
                      else
                        {
                           // Toast.makeText(getApplicationContext(),  "Login ไม่สำเร็จ"  ,Toast.LENGTH_LONG).show(); //check int
                            Intent intent = new Intent(MainActivity.this, loginsystem.class);
                            intent.putExtra("user_type_id", "3" );
                            // intent.putExtra("")
                            intent.putExtra("us","");
                            intent.putExtra("ps","");
                            startActivity(intent);
                        }


            }
        });


        final ImageView imgViewI8=(ImageView) findViewById(R.id.imageViewI8);//เฝ้าระวังภัย
        imgViewI8.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                rendermap(url8);
            }
        });

    }

    public void rendermap(String url) //map
    {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
