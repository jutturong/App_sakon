package com.example.linux.sakon;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
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

public class MentalHealth extends AppCompatActivity {

    private Bundle savedInstanceState;
    public Intent intent;


    public String IP="http://203.157.177.121/";
    private final String NAMESPACE=  IP +  "nusoap/ServerSide.php";
    private final String METHOD_NAME="bmi2";
    private final String URL= IP  +   "nusoap/ServerSide.php?wsdl";
    private final String SOAP_ACTION =   IP  +  "nusoap/ServerSide.php/bmi2";

   private final String[] arrSex={"รับราชการ","รัฐวิสาหกิจ","พนักงานราชการ","ลูกจ้างประจำ","ลูกจ้างชั่วคราว","เกษตรกรรม","ค้าขาย","อื่นๆ"};






    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.mentalhealth);
        setContentView(R.layout.mentalhealth_abs);
       // setContentView(R.layout.tab_test);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        Spinner spinner_career=(Spinner)findViewById(R.id.spinner_career);
        ArrayAdapter<String> arrAd=new ArrayAdapter<String>(MentalHealth.this,android.R.layout.simple_spinner_item,arrSex);
        arrAd.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner_career.setAdapter(arrAd);











    }


}
