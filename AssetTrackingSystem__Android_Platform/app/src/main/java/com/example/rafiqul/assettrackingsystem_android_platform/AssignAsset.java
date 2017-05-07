package com.example.rafiqul.assettrackingsystem_android_platform;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AssignAsset extends Activity {

    String FIND_TAG_URL = "";
    String ASSIGN_TO_URL = "";

    String Search_Asset_Tag="";
    String Assign_User ="";
    String Assign_Location="";


    private EditText assign_tag, assign_to, assign_location;
    private Button findButton, submitButton;

    void setup() {
        assign_tag = (EditText) findViewById(R.id.Assign_Tag);
        assign_to = (EditText) findViewById(R.id.Asset_Assign_to);
        assign_location = (EditText) findViewById(R.id.Assign_To_location);
        findButton = (Button) findViewById(R.id.findButton);
        submitButton = (Button) findViewById(R.id.AssignSubmitButton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_asset);
        setup();

        //If Press Find Button
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search_Asset_Tag= assign_tag.getText().toString().trim();
                if(!Search_Asset_Tag.equals("")){
                    new HttpAsyncTaskFind().execute(FIND_TAG_URL);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Fill the Field",Toast.LENGTH_LONG).show();
                }
            }
        });

        //If Press Submit Button

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Assign_User= assign_to.getText().toString().trim();
                Assign_Location= assign_location.getText().toString().trim();

                if(!Assign_User.equals("") && !Assign_Location.equals(""))
                {
                    new HttpAsyncTaskAssign().execute(ASSIGN_TO_URL);
                    Intent i =new Intent(AssignAsset.this,Home.class);
                    startActivity(i);
                }

                else
                {
                    Toast.makeText(getApplicationContext(),"Fill the Field",Toast.LENGTH_LONG).show();
                }
            }
        });

    }




    //Background Working
    private class HttpAsyncTaskFind extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {



            return POSTFind(FIND_TAG_URL, Search_Asset_Tag);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent to the Server !", Toast.LENGTH_LONG).show();
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



    public  String POSTFind(String url,String search_Asset_Tag){
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            String jsonstring = "";
            JSONObject jsonobject = new JSONObject();
            //Build JSON object
            jsonobject.accumulate("Tag",search_Asset_Tag);
            jsonstring= jsonobject.toString();
            StringEntity stringentity = new StringEntity(jsonstring);
            httppost.setEntity(stringentity);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");
            HttpResponse httpresponse = httpclient.execute(httppost);
            String x= httpresponse.toString();
            Toast.makeText(getApplicationContext(),"response: "+x,Toast.LENGTH_LONG).show();
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



    //Background Working
    private class HttpAsyncTaskAssign extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {



            return POSTAssign(ASSIGN_TO_URL,Assign_User,Assign_Location);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent to the Server !", Toast.LENGTH_LONG).show();
        }
    }





    public  String POSTAssign(String url,String assign_User,String assign_Location){
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            String jsonstring = "";
            JSONObject jsonobject = new JSONObject();
            //Build JSON object
            jsonobject.accumulate("Assign_User",assign_User);
            jsonobject.accumulate("Assign_Location",assign_Location);

            jsonstring= jsonobject.toString();
            StringEntity stringentity = new StringEntity(jsonstring);
            httppost.setEntity(stringentity);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");
            HttpResponse httpresponse = httpclient.execute(httppost);
            String x= httpresponse.toString();
            Toast.makeText(getApplicationContext(),"response: "+x,Toast.LENGTH_LONG).show();
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