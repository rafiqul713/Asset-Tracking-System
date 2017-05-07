package com.example.rafiqul.assettrackingsystem_android_platform;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Login extends Activity {
    private ProgressDialog pDialog;
    String objID=null;
    String msg;
    JSONObject obj;
    String response;
    int RESPONSE_CODE;
    String LOGIN_URL="https://ats-api.scalingo.io/parse/login";
    private EditText login_email,login_pass;
    private Button Login_button,sign_up_button;
    String EMAIL="";
    String PASS="";

    void setup() {
        login_email = (EditText) findViewById(R.id.emailInput);
        login_pass =(EditText) findViewById(R.id.passwordInput);
        Login_button =(Button) findViewById(R.id.loginButton);
        sign_up_button= (Button) findViewById(R.id.signUP111);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setup();
        EMAIL="";
        PASS="";


        Login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMAIL = login_email.getText().toString().trim();
                PASS = login_pass.getText().toString().trim();
                if (!EMAIL.equals("") && !PASS.equals("")) {
                    new HttpAsyncTask().execute(LOGIN_URL);



                } else {
                    Toast.makeText(getApplicationContext(), "Fill the Field", Toast.LENGTH_LONG).show();
                }




            }
        });

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
            }
        });
    }



    //Background Working
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected String doInBackground(String... urls) {



            return POST(urls[0],EMAIL,PASS);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if (pDialog.isShowing())
                pDialog.dismiss();
            Toast.makeText(getBaseContext(), "Responese Code "+RESPONSE_CODE, Toast.LENGTH_LONG).show();
            try {
                obj = new JSONObject(response);
                objID=null;
                objID=obj.getString("objectId");
                Log.d("Object ID ",objID);
            }
            catch(Exception e){

            }
            if(RESPONSE_CODE==200 && objID!=null) {
                Intent i = new Intent(Login.this, Home.class);
                startActivity(i);
            }
            else
            {
                try {
                    msg = obj.getString("message");
                    Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();



                }
                catch (Exception e){

                }
            }
        }
    }

    //Internet Connectivity Check
    public boolean isConnectedToTheNet(){
        ConnectivityManager connectivitymanager = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivitymanager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected())
            return true;
        else
            return false;
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






    //Http Post Request
    public  String POST(String url,String EMAIL,String PASS){
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            String jsonstring = "";
            JSONObject jsonobject = new JSONObject();
            jsonobject.accumulate("username",EMAIL);
            jsonobject.accumulate("password",PASS);
            jsonstring= jsonobject.toString();
            StringEntity stringentity = new StringEntity(jsonstring);
            httppost.setEntity(stringentity);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");
            httppost.setHeader("X-Parse-Application-Id", "myAppId");
            HttpResponse httpresponse = httpclient.execute(httppost);

            RESPONSE_CODE= httpresponse.getStatusLine().getStatusCode();
            HttpEntity httpEntity = httpresponse.getEntity();
            response = EntityUtils.toString(httpEntity);
            Log.d("Response body: ",response);


            Toast.makeText(getApplicationContext(),"Response Code: "+httpresponse.getStatusLine().getStatusCode()+"Response: "+response,Toast.LENGTH_LONG).show();
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
