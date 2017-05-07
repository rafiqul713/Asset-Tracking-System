package com.example.rafiqul.assettrackingsystem_android_platform;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;





public class MainActivity extends Activity {
    private ProgressDialog pDialog;
    int CODE;
    String response;
    private EditText fullname,username,email,password;
    private Button signup,login;
    Person person;
    public String URL="";
    private String signup_URL="https://ats-api.scalingo.io/parse/";

    void setup()
    {
        fullname=(EditText)findViewById(R.id.FullName);
        username=(EditText)findViewById(R.id.UserName);
        email=(EditText) findViewById(R.id.Email);
        password=(EditText)findViewById(R.id.Password);
        signup = (Button)findViewById(R.id.signup);
        login= (Button) findViewById(R.id.login71);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();


        if(isConnectedToTheNet()==true){

        }

        else
        {
            Toast.makeText(getApplicationContext(),"Please Turn On Internet",Toast.LENGTH_LONG).show();
        }


        //If Press Signup
       signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String FULLNAME,USERNAME,EMAIL,PASSWORD;
                FULLNAME= fullname.getText().toString().trim();
                USERNAME= username.getText().toString().trim();
                EMAIL= email.getText().toString().trim();
                PASSWORD= password.getText().toString().trim();
                person = new Person(FULLNAME,USERNAME,EMAIL,PASSWORD);

                if(!person.getFULLNAME().equals("") && !person.getUSERNAME().equals("")  && !person.getEMAIL().equals("")
                        && !person.getPASSWORD().equals("")){
                    if(PatternMatching.emailValidation(EMAIL)==true){
                    new HttpAsyncTask().execute(signup_URL);

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Invalied Email Address Given",Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(),"Please Fill the Field",Toast.LENGTH_LONG).show();
                }

            }
        });


        //If Press Login

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Login.class);
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
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected String doInBackground(String... urls) {



            return POST(urls[0],person);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if (pDialog.isShowing())
                pDialog.dismiss();
            Toast.makeText(getBaseContext(), "Status Code ------"+CODE, Toast.LENGTH_LONG).show();

            if(CODE==200) {
                Toast.makeText(getBaseContext(), "Data Sent to the Server !", Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this,AddCompany.class);
                startActivity(i);
            }
            else {
                //Toast.makeText(getBaseContext(), "Already Email Address Exists", Toast.LENGTH_LONG).show();
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
    private static String InputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }






    //Http Post Request
    public  String POST(String url,Person person){
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            String jsonstring = "";
            JSONObject jsonobject = new JSONObject();
            jsonobject.accumulate("fullName",person.getFULLNAME());
            jsonobject.accumulate("username",person.getUSERNAME());
            jsonobject.accumulate("email",person.getEMAIL());
            jsonobject.accumulate("password",person.getPASSWORD());
            jsonobject.accumulate("userType","Admin");
            jsonstring= jsonobject.toString();
            StringEntity stringentity = new StringEntity(jsonstring);
            httppost.setEntity(stringentity);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");
            httppost.setHeader("X-Parse-Application-Id","myAppId");
            HttpResponse httpresponse = httpclient.execute(httppost);
            CODE= httpresponse.getStatusLine().getStatusCode();
            HttpEntity httpEntity = httpresponse.getEntity();
            response = EntityUtils.toString(httpEntity);

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
