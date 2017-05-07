package com.example.rafiqul.assettrackingsystem_android_platform;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Home extends Activity {
    private ProgressDialog pDialog;
    String Logout_URL="https://ats-api.scalingo.io/parse/logout";
    int LOGOUT_CODE;
    private Button Home,Add_asset,Add_User,Assign_Asset,View_History,Logout;
    private void setup(){
        Home =(Button) findViewById(R.id.homeButton);
        Add_asset =(Button) findViewById(R.id.AddAssetButton);
        Add_User =(Button) findViewById(R.id.AddUserButton);
        //Assign_Asset =(Button) findViewById(R.id.AssignAssetButton);
        View_History =(Button) findViewById(R.id.ViewHistoryButton);
        Logout=(Button)findViewById(R.id.logoutButton);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setup();

        //If Press Home Button
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,Home.class);
                startActivity(i);
            }
        });


        //If Press Add Asset Button
        Add_asset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,AddAsset.class);
                startActivity(i);

            }
        });


        ///If Press Add User Button
        Add_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,AddUser.class);
                startActivity(i);
            }
        });


        // If Press Assign Asset Button
        /*Assign_Asset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Home.this,AssignAsset.class);
                startActivity(i);
            }
        });*/


        // If Press View History Button
        View_History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent  i = new Intent(Home.this,AssetList.class);
                    startActivity(i);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpAsyncTask().execute(Logout_URL);

            }
        });

    }





    ///Adding Logout Functionality


    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Home.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if (pDialog.isShowing())
                pDialog.dismiss();
            Toast.makeText(getBaseContext(), "Log out!", Toast.LENGTH_LONG).show();
            Intent i =  new Intent(Home.this,MainActivity.class);
            startActivity(i);
        }
    }



    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet get = new HttpGet(url);
            get.setHeader("Accept", "application/json");
            get.setHeader("Content-type", "application/json");
            get.setHeader("X-Parse-Application-Id", "myAppId");


             HttpResponse httpResponse = httpclient.execute(get);
            int C=httpResponse.getStatusLine().getStatusCode();

        } catch (Exception e) {

        }

        return result;
    }





}
