package com.example.admin.mylog;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText e1,e2;
    Button btn;
    TextView tv;

    private final String NAMESPACE = "http://tempuri.org/ValidateUser";
    private final String URL = "http://203.124.96.117:8063/Service1.asmx?op=ValidateUser";
    private final String SOAP_ACTION = "http://tempuri.org/";
    private final String METHOD_NAME = "ValidateUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1= (EditText) findViewById(R.id.editText);
        e2= (EditText) findViewById(R.id.editText2);
        tv= (TextView) findViewById(R.id.textView);
        btn= (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LongOperation().execute();

            }
        });




    }

    class LongOperation extends AsyncTask<String, Void, String> {

        private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);


        @Override
        protected String doInBackground(String... params) {
            loginAction();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialog.setMessage("Loading...");
            Dialog.show();
        }
    }


    private void loginAction(){

        final SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


        String uname=e1.getText().toString();
        String pass=e2.getText().toString();



        PropertyInfo unameProp =new PropertyInfo();
        unameProp.setName("UserName");//Define the variable name in the web service method
        unameProp.setValue(uname);//set value for userName variable
        unameProp.setType(String.class);//Define the type of the variable
        request.addProperty(unameProp);//Pass properties to the variable

        //Pass value for Password variable of the web service
        PropertyInfo passwordProp =new PropertyInfo();
        passwordProp.setName("Password");
        passwordProp.setValue(pass);
        passwordProp.setType(String.class);
        request.addProperty(passwordProp);


        final SoapSerializationEnvelope  envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        final HttpsTransportSE androidHttpTransport = (HttpsTransportSE) new HttpTransportSE(URL);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    androidHttpTransport.call(SOAP_ACTION,envelope);
                    SoapPrimitive response= (SoapPrimitive) envelope.getResponse();
                    tv.setText(response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        });

       // runOnUiThread(new Runnable() {
         //   @Override
           // public void run() {
             //   try {

               //     androidHttpTransport.call(SOAP_ACTION, envelope);
                 //   SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                    //TextView result = (TextView) findViewById(R.id.tv_status);
                    // result.setText(response.toString());

                //} catch (Exception e) {

                }
            }


