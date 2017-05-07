package com.example.rafiqul.assettrackingsystem_android_platform;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class AddCompany extends Activity {
    private EditText ComName,ComLoc,ComPhn,ComEmail;
    private Button Submit,Back;
    String ADD_COMPANY_URL="https://ats-api.scalingo.io/parse/companies/new";
    Company_Info company;
    void setup(){
        ComName= (EditText)findViewById(R.id.CompanyName);
        ComLoc =  (EditText)findViewById(R.id.CompanyLocation);
        ComPhn =  (EditText)findViewById(R.id.CompanyPhoneNumber);
        ComEmail=  (EditText)findViewById(R.id.AddEmail);
        Submit = (Button)findViewById(R.id.SubmitButton);
        Back= (Button)findViewById(R.id.BackButton);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);
        setup();

        //If Press Submit Button
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c_name,c_phn,c_email,c_location;
                c_name= ComName.getText().toString().trim();
                c_phn= ComPhn.getText().toString().trim();
                c_email= ComEmail.getText().toString().trim();
                c_location= ComLoc.getText().toString().trim();
                company= new Company_Info(c_name,c_location,c_phn,c_email);
                if(!company.getCompanyName().equals("") && !company.getCompanyEmail().equals("") && !company.getCompanyLocation().equals("") &&
                        !company.getCompanyPhone().equals("")){
                    if(PatternMatching.emailValidation(c_email)==true) {
                        new HttpAsyncTask().execute(ADD_COMPANY_URL);
                        Intent i = new Intent(AddCompany.this, Home.class);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Invalied Email Address Given",Toast.LENGTH_LONG).show();

                    }

                }

                else {
                    Toast.makeText(getApplicationContext(),"Fill all Field",Toast.LENGTH_LONG).show();
                }

            }
        });
    }



    //Background Working
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {



            return POST(urls[0],company);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            //Toast.makeText(getBaseContext(), "Data Sent to the Server !", Toast.LENGTH_LONG).show();
        }
    }



    //Convert Input stream to String
    private static String InputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


    //Http Post Request for Adding Company_Info
    public  String POST(String url,Company_Info company){
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            String jsonstring = "";
            JSONObject jsonobject = new JSONObject();
            //Build JSON object
            jsonobject.accumulate("companyName",company.getCompanyName());
            jsonobject.accumulate("comEmail",company.getCompanyEmail());
            jsonobject.accumulate("companyAddress", company.getCompanyLocation());
            jsonobject.accumulate("companyPhone",company.getCompanyPhone());
            //Toast.makeText(getApplicationContext(), "JSON: "+jsonobject, Toast.LENGTH_SHORT).show();
            jsonstring= jsonobject.toString();
            StringEntity stringentity = new StringEntity(jsonstring);
            httppost.setEntity(stringentity);

            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");
            httppost.setHeader("X-Parse-Application-Id","myAppId");
            HttpResponse httpresponse = httpclient.execute(httppost);
            int CODE= httpresponse.getStatusLine().getStatusCode();
            Toast.makeText(getApplicationContext(),"response code : "+CODE,Toast.LENGTH_LONG).show();
            String x= httpresponse.toString();

            inputStream = httpresponse.getEntity().getContent();
            if(inputStream != null) {
                result = InputStreamToString(inputStream);

            }
            else
                result = "Did not work!";

            Toast.makeText(getApplication(), "result: " + result, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }



}
