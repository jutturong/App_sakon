package com.example.linux.sakon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by linux on 12/11/58.
 */

public class body extends AppCompatActivity {

    private Bundle savedInstanceState;
    public Intent intent;

    /*
    public String IP="http://203.157.177.121";
    private final String NAMESPACE=  IP +  "webservice/chkLogin.php";
    private final String METHOD_NAME="loginMember";
    private final String URL= IP  +   "webservice/chkLogin.php?wsdl";
    private final String SOAP_ACTION =   IP  +  "webservice/WebServiceServer.php/HelloWorld";
*/

    //http://203.157.177.121/nusoap/ServerSide.php
    //http://203.157.177.121/nusoap/ClientSide.php  for test
    public String IP="http://203.157.177.121/";
    private final String NAMESPACE=  IP +  "nusoap/ServerSide.php";
    private final String METHOD_NAME="bmi2";
    private final String URL= IP  +   "nusoap/ServerSide.php?wsdl";
    private final String SOAP_ACTION =   IP  +  "nusoap/ServerSide.php/bmi2";



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.body);
        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



     //   intent=getIntent();


        final EditText txtWeight=(EditText)findViewById(R.id.txtWeight);
        final EditText txtHeight=(EditText)findViewById(R.id.txtHeight);




        final Button btnCal=(Button)findViewById(R.id.btnCal);
        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);



                request.addProperty("strUsername", "ict");
                request.addProperty("strPassword", "skko");
                request.addProperty("strDatatype", "json");
              //  request.addProperty("txtWeight", "80");
              //  request.addProperty("txtHeight", "150");

                request.addProperty("txtWeight", txtWeight.getText().toString()); //80
                request.addProperty("txtHeight", txtHeight.getText().toString());  //150


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                String resultServer=null;
                try{
                    androidHttpTransport.call(SOAP_ACTION, envelope);
                    SoapObject result=(SoapObject) envelope.bodyIn;
                    resultServer=result.getProperty(0).toString();
                }catch (IOException e)
                {
                    e.printStackTrace();
                }catch (XmlPullParserException e)
                {
                    e.printStackTrace();
                }

                String bmi="0";
                String bmi_risk_id="";
                String min="";
                String max="";
                String level="";
                String risk="";
                String direction="";
                String bmi_result="";

                JSONObject c;
                try{
                    c=new JSONObject( resultServer);


                    /*

                      $rows["bmi_risk_id"]=$row["bmi_risk_id"];
                       $rows["bmi"]=$row["bmi"];
                        $rows["min"]=$row["min"];
                       $rows["max"]=$row["max"];
                       $rows["level"]=$row["level"];
                        $rows["risk"]=$row["risk"];
                      $rows["direction"]=$row["direction"];
                     */

                    bmi_risk_id=c.getString("bmi_risk_id");
                    bmi=c.getString("bmi");
                    min=c.getString("min");
                    max=c.getString("max");
                    level=c.getString("level");
                    risk=c.getString("risk");
                    direction=c.getString("direction");
                    bmi_result=c.getString("bmi_result");

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }


                Toast.makeText(getApplicationContext(),

                        "ดัชนีมวลกาย : "   +  bmi_result.toString()  +  "kg/m2"  +   '\n'  +
                        "อยู่ในเกณฑ์ :  "  +   bmi.toString()  +      "\n"  +
                        "ภาวะเสี่ยงต่อโรค : "  + risk.toString()  +     "\n"  +
                        "คำแนะนำ  "     +    "\n"  +   direction.toString()
                        ,
                        Toast.LENGTH_LONG).show(); //ok


            }
        });









    }


}
