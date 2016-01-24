package com.example.linux.sakon;

import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by golf on 24/1/2559.
 */
public class ncdscreen extends TabActivity {

    TabHost mTabHost;


    private final String NAMESPACE=  employee.IP +  "nusoap/ServerSide.php";
    private final String METHOD_NAME="ncdscreen";
    private final String URL= employee.IP  +   "nusoap/ServerSide.php?wsdl";
    private final String SOAP_ACTION =   employee.IP  +  "nusoap/ServerSide.php/" + METHOD_NAME;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ncdscreen);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

       // Toast.makeText(getApplicationContext(), "testing คัดกรอง", Toast.LENGTH_LONG).show(); //test user_type_name


        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost = getTabHost();
        mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("ประวัติคัดกรอง").setContent(R.id.tab1));
        mTabHost.addTab(mTabHost.newTabSpec("tab_test2").setIndicator("เพิ่มประวัติคัดกรอง").setContent(R.id.tab2));
       // mTabHost.addTab(mTabHost.newTabSpec("tab_test3").setIndicator("โรคประจำตัว").setContent(R.id.tab3));

        mTabHost.setCurrentTab(0);



        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("strUsername", "ict");
        request.addProperty("strPassword", "skko");
        request.addProperty("strDatatype", "json");
        request.addProperty("strCID", "3470300302631");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        String resultServer=null;


        try{
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject result=(SoapObject) envelope.bodyIn;
            resultServer=result.getProperty(0).toString();

           // Toast.makeText(getApplicationContext(), resultServer , Toast.LENGTH_LONG).show(); //test user_type_name



        }catch (IOException e)
        {
            e.printStackTrace();
        }catch (XmlPullParserException e)
        {
            e.printStackTrace();
        }

        JSONArray ncd_arr;
        JSONObject ncd_obj;
        try{
           // ncd_arr=new JSONArray(resultServer);
           // ncd_obj=new JSONObject();

           // Toast.makeText(getApplicationContext(), ncd_obj.getString("cid") , Toast.LENGTH_LONG).show(); //test user_type_name

        }catch (Exception e)
        {
            e.printStackTrace();
        }




    }






}
