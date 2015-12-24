package com.example.linux.sakon;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
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
 * Created by linux on 12/11/58.
 */

public class employee extends TabActivity {

    private Bundle savedInstanceState;
    public Intent intent_login;

    public ImageView imageView20;  //ดัชนีมวลกาย body_weight


    public String IP="http://203.157.177.121/";
    private final String NAMESPACE=  IP +  "nusoap/ServerSide.php";
    private final String METHOD_NAME="person";
    private final String URL= IP  +   "nusoap/ServerSide.php?wsdl";
    private final String SOAP_ACTION =   IP  +  "nusoap/ServerSide.php/" + METHOD_NAME;

    TabHost mTabHost;

String[] arr_picture={"เลือกภาพ"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.employee);
        //setContentView(R.layout.expandlist_employee);
        //setContentView(R.layout.scroll_horizontal);

        setContentView(R.layout.tab_employee);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

       /*
        String [ ] group = { "ข้อมูลส่วนตัว", "ประวัติแพ้ยา", "โรคประจำตัว" };
        String [ ] [ ] children =
                {
                        { "Manchester United", "Manchester City", "Liverpool" }
                        , { "Barcelona", "Real Mardrid", "Valencia" }
                        , { "AC Milan", "Inter Milan", "Lazio" }
                };

        MyExpadableAdapter adapter = new MyExpadableAdapter ( group, children );
        ExpandableListView expandableListView=(ExpandableListView) findViewById(R.id.expandablelistview_employee);
        expandableListView.setAdapter ( adapter );
        expandableListView.expandGroup(0);
        */

        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost = getTabHost();
        mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("ข้อมูลส่วนตัว").setContent(R.id.tab1));
        mTabHost.addTab(mTabHost.newTabSpec("tab_test2").setIndicator("ประวัติแพ้ยา").setContent(R.id.tab2));
        mTabHost.addTab(mTabHost.newTabSpec("tab_test3").setIndicator("โรคประจำตัว").setContent(R.id.tab3));

        mTabHost.setCurrentTab(0);


        Spinner spinner_picture1=(Spinner)findViewById(R.id.spinner_picture1);
        //spinner_picture1.setOnItemSelectedListener(this);
        ArrayAdapter<String> arrAd=new ArrayAdapter<String>(employee.this,android.R.layout.simple_spinner_item,arr_picture);
        arrAd.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner_picture1.setAdapter(arrAd);

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("strUsername", "ict");
        request.addProperty("strPassword", "skko");
        request.addProperty("strDatatype", "json");
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


        String cid="";
        JSONObject c;
        try{
            c=new JSONObject( resultServer);
            cid=c.getString("cid");




        }catch (JSONException e)
        {
            e.printStackTrace();
        }






    }

    

    protected class MyExpadableAdapter extends BaseExpandableListAdapter
    {
        private String [ ] group = null;
        private String [ ] [ ] children = null;
        public MyExpadableAdapter ( String [ ] group, String [ ] [ ] children )
        {
            this.group = group;
            this.children = children;
        }
        public Object getChild ( int groupPosition, int childPosition )
        {
            return this.children [ groupPosition ] [ childPosition ];
        }
        public long getChildId ( int groupPosition, int childPosition )
        {
            return childPosition;
        }
        public View getChildView ( int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent )
        {
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams ( ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
            TextView textView = new TextView ( employee.this );
            textView.setLayoutParams ( layoutParams );
            textView.setPadding ( 5, 5, 5, 5 );
            textView.setText ( this.children [ groupPosition ] [ childPosition ] );
            return textView;
        }
        public int getChildrenCount ( int groupPosition )
        {
            return this.children [ groupPosition ].length;
        }
        public Object getGroup ( int groupPosition )
        {
            return this.group [ groupPosition ];
        }
        public int getGroupCount ( )
        {
            return this.group.length;
        }
        public long getGroupId ( int groupPosition )
        {
            return groupPosition;
        }
        public View getGroupView ( int groupPosition, boolean isExpanded, View convertView, ViewGroup parent )
        {
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams ( ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
            TextView textView = new TextView ( employee.this );
            textView.setLayoutParams ( layoutParams );
            textView.setGravity ( Gravity.CENTER );
            textView.setPadding ( 18, 18, 18, 18 );
            textView.setText ( this.group [ groupPosition ] );
            return textView;
        }
        public boolean hasStableIds ( )
        {
            return true;
        }
        public boolean isChildSelectable ( int groupPosition, int childPosition )
        {
            return true;
        }
    }


}
