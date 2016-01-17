package com.example.linux.sakon;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
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

    //-------- connect ประวัติแพ้ยา ---
    private final String NAMESPACE_drug=  IP +  "nusoap/ServerSide.php";
    private final String METHOD_NAME_drug="drugallergy";
    private final String URL_drug= IP  +   "nusoap/ServerSide.php?wsdl";
    private final String SOAP_ACTION_drug =   IP  +  "nusoap/ServerSide.php/" + METHOD_NAME_drug;
    //-------- connect ประวัติแพ้ยา ---

    //--------- connect โรคประจำตัว chronic--------
    private final String NAMESPACE_chronic=  IP +  "nusoap/ServerSide.php";
    private final String METHOD_NAME_chronic="chronic";
    private final String URL_chronic= IP  +   "nusoap/ServerSide.php?wsdl";
    private final String SOAP_ACTION_chronic =   IP  +  "nusoap/ServerSide.php/" + METHOD_NAME_chronic;
    //--------- connect โรคประจำตัว chronic--------

    TabHost mTabHost;

    String[] arr_picture={"เลือกภาพ"};


    private static final String[] COUNTRIES = new String[] {
            "PENICILLIN V Dry. Syrup", "PENICILLIN V 400,000 Cap.", "CLOXACILLIN 250 MG.(N)", "AUGMENTIN 625 MG.", "PENICILLIN V 250 MG (ก)"
    };



    private static final String[] ID_COUNTRIES = new String[] {
            "05444", "05444", "10710", "10710", "10710"
    };

    private static final String[] ALEVEL = new String[] {
            "ร้ายแรง - อัตรายถึงชีวิต (Life-threatening)", "ร้ายแรง - อัตรายถึงชีวิต (Life-threatening)", "ไม่ร้ายแรง (Non-serious)", "ไม่ร้ายแรง (Non-serious)", "ไม่ร้ายแรง (Non-serious)"
    };

    private static final String[] 	off_name = new String[] {
            "รพ.สต.โคกเลาะ ตำบลขมิ้น", "รพ.สต.โคกเลาะ ตำบลขมิ้น", "โรงพยาบาลสกลนคร", "โรงพยาบาลสกลนคร", "โรงพยาบาลสกลนคร"
    };

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



        final EditText txtname=(EditText)findViewById(R.id.txtname);
        final EditText txtLNAME=(EditText)findViewById(R.id.txtLNAME);
        final EditText txtBIRTH=(EditText)findViewById(R.id.txtBIRTH);
        final EditText txtABOGROUP=(EditText)findViewById(R.id.txtABOGROUP);
        final EditText txtaddress=(EditText)findViewById(R.id.txtaddress);
        final EditText txtAGE=(EditText)findViewById(R.id.txtAGE);
        final RadioButton radioM=(RadioButton)findViewById(R.id.radioM);
        final RadioButton radioF=(RadioButton)findViewById(R.id.radioF);


        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("strUsername", "ict");
        request.addProperty("strPassword", "skko");
        request.addProperty("strDatatype", "json");
        request.addProperty("strCID", "3471201545502");
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
        String name="";
        String LNAME="";
        String BIRTH="";
        String ABOGROUP="";
        String address="";
        String SEX="";
        String AGE="";


        JSONObject c;
        try{
            c=new JSONObject( resultServer);
            cid=c.getString("cid");
            name=c.getString("name");
            LNAME=c.getString("LNAME");
            BIRTH=c.getString("BIRTH");
            ABOGROUP=c.getString("ABOGROUP");
            address=c.getString("address");
            SEX=c.getString("SEX");
            AGE=c.getString("AGE");

            /*
              $rows["cid"]=$row["cid"];
                          $rows["name"]=$row["name"];
                          $rows["LNAME"]=$row["LNAME"];
                          $rows["BIRTH"]=$row["BIRTH"];
                          $rows["ABOGROUP"]=$row["ABOGROUP"];
                          $rows["address"]=$row["address"];
                          $rows["SEX"]=$row["SEX"];
                          $rows["AGE"]=$row["AGE"];
             */


        }catch (JSONException e)
        {
            e.printStackTrace();
        }


/*
        Toast.makeText(getApplicationContext(),
                cid + name + LNAME + BIRTH + ABOGROUP + address + SEX + AGE
                ,
                Toast.LENGTH_LONG).show(); //ok
*/


        txtname.setText(name.toString());
        txtLNAME.setText(LNAME.toString());
        txtBIRTH.setText(BIRTH.toString());
        txtABOGROUP.setText(ABOGROUP.toString());
        txtaddress.setText(address.toString());

        txtAGE.setText(AGE.toString());

        if(SEX.equals("ชาย"))
        {
            radioM.setChecked(true);
        }
        else
        {
            radioF.setChecked(true);
        }

        //--test  tab2---
/*

    //-------- connect ประวัติแพ้ยา ---
    private final String NAMESPACE_drug=  IP +  "nusoap/ServerSide.php";
    private final String METHOD_NAME_drug="drugallergy";
    private final String URL_drug= IP  +   "nusoap/ServerSide.php?wsdl";
    private final String SOAP_ACTION_drug =   IP  +  "nusoap/ServerSide.php/" + METHOD_NAME_drug;
    //-------- connect ประวัติแพ้ยา ---

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("strUsername", "ict");
        request.addProperty("strPassword", "skko");
        request.addProperty("strDatatype", "json");
        request.addProperty("strCID", "3471201545502");
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



  $drugallergy_varname=array(
         'strUsername' => "ict",
         'strPassword' => "skko",
         'strDatatype'=>"json",
         'strCID'=>"3470100253904",
    );


*/
        SoapObject request_drug = new SoapObject(NAMESPACE_drug, METHOD_NAME_drug);
        request_drug.addProperty("strUsername", "ict");
        request_drug.addProperty("strPassword", "skko");
        request_drug.addProperty("strDatatype", "json");
        request_drug.addProperty("strCID", "3470100253904");

        SoapSerializationEnvelope envelope_drug = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope_drug.setOutputSoapObject(request_drug);
        HttpTransportSE androidHttpTransport_drug = new HttpTransportSE(URL_drug);
        String resultServer_drug=null;

        try{
            androidHttpTransport.call(SOAP_ACTION_drug, envelope_drug );
            SoapObject result_drug=(SoapObject) envelope_drug.bodyIn;
            resultServer_drug=result_drug.getProperty(0).toString();



        }catch (IOException e)
        {
            e.printStackTrace();
        }catch (XmlPullParserException e)
        {
            e.printStackTrace();
        }


        JSONObject c_drug;
        String HOSPCODE="";
        String  ALEVEL_obj="";
        String off_name_obj="";
        String DNAME="";
        String ALEVEL="";

        List<String>list_HOSPCODE=new ArrayList<String>();
        List<String>list_DNAME=new ArrayList<String>();
        List<String>list_ALEVEL=new ArrayList<String>();
        List<String>list_off_name=new ArrayList<String>();




        AlertDialog.Builder adb=new AlertDialog.Builder(this);
        AlertDialog ad=adb.create();
            JSONObject  c_drug2;

        try{



            c_drug=new JSONObject( resultServer_drug );
            /*
            HOSPCODE=c_drug.getString("HOSPCODE");
            ALEVEL_obj=c_drug.getString("ALEVEL");
            off_name_obj=c_drug.getString("off_name");
            DNAME=c_drug.getString("DNAME");
            */

            /*ทดสอบ messagebox */

             /*ทดสอบ messagebox */




            list_HOSPCODE.add(c_drug.getString("HOSPCODE"));
            list_DNAME.add(c_drug.getString("DNAME"));
            list_ALEVEL.add(c_drug.getString("ALEVEL"));
            list_off_name.add(c_drug.getString("off_name"));

            /*
            Toast.makeText(getApplicationContext(),
                    list1.toString()
                    ,
                    Toast.LENGTH_LONG).show(); //ok
                    */








            final ListView listView1=(ListView)findViewById(R.id.listView1);
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list_HOSPCODE );
            listView1.setAdapter(adapter);


            final ListView listView2=(ListView)findViewById(R.id.listView2);
            ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list_DNAME);
            listView2.setAdapter(adapter2);


            final ListView listView3=(ListView)findViewById(R.id.listView3);
            ArrayAdapter<String> adapter3=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_ALEVEL);
            listView3.setAdapter(adapter3);


            final ListView listView4=(ListView)findViewById(R.id.listView4);
            ArrayAdapter<String> adapter4=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_off_name);
            listView3.setAdapter(adapter4);





            /*
            Toast.makeText(getApplicationContext(),
                   count.toString()
                    ,
                    Toast.LENGTH_LONG).show(); //ok
            */





        }catch (JSONException e)
        {
            e.printStackTrace();
        }


        //--tab tab2 ok complete---

        //----  tab 3  ---
        SoapObject request_chronic = new SoapObject(NAMESPACE_chronic, METHOD_NAME_chronic);
        request_drug.addProperty("strUsername", "ict");
        request_drug.addProperty("strPassword", "skko");
        request_drug.addProperty("strDatatype", "json");
        request_drug.addProperty("strCID", "3470100391002");

        SoapSerializationEnvelope envelope_chronic = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope_chronic.setOutputSoapObject(request_chronic);
        HttpTransportSE androidHttpTransport_chronic = new HttpTransportSE(URL_chronic);
        String resultServer_chronic=null;

        try{
            androidHttpTransport.call(SOAP_ACTION_chronic, envelope_chronic );
            SoapObject result_chronic=(SoapObject) envelope_drug.bodyIn;
            resultServer_chronic=result_chronic.getProperty(0).toString();



        }catch (IOException e)
        {
            e.printStackTrace();

        }catch (XmlPullParserException e)
        {
            e.printStackTrace();
        }

JSONObject c_chronic;
        String off_name="";
        String tchronic="";

       try{
           c_chronic=new JSONObject( resultServer_chronic );
           off_name=c_chronic.getString("off_name");
        //   tchronic=c_chronic.getString("tchronic");



    // alert success
           Toast.makeText(getApplicationContext(),
                   off_name
                   ,
                   Toast.LENGTH_LONG).show(); //ok







       }
       catch (JSONException e)
       {
           e.printStackTrace();
       }

        //----  tab 3  ---



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
