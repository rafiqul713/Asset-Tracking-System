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

public class AddAsset extends Activity {

    String ADD_ASSET_URL="https://ats-api.scalingo.io/parse/assets/new";
    Asset_Info asset_info;
    private EditText Tag,Type,AssignTo,Location;
    private Button Submit;
    void setup(){
        Tag = (EditText)findViewById(R.id.tag);
        Type = (EditText)findViewById(R.id.type);
        AssignTo = (EditText)findViewById(R.id.assign_to);
        Location= (EditText)findViewById(R.id.location);
        Submit = (Button) findViewById(R.id.submitButton);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asset);
        setup();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String tag = Tag.getText().toString().trim();
                    String type = Type.getText().toString().trim();
                    String assignTo = AssignTo.getText().toString().trim();
                    String location = Location.getText().toString().trim();
                    asset_info = new Asset_Info(tag, type, assignTo, location);

                    if(!asset_info.getAsset_TAG().equals("")&&
                            !asset_info.getAsset_Type().equals("")&&
                            !asset_info.getAsset_AssignTo().equals("")&&
                            !asset_info.getAsset_Location().equals("")){

                        new HttpAsyncTask().execute(ADD_ASSET_URL);
                        //Back to Home Page
                        Intent i = new Intent(AddAsset.this, Home.class);
                        startActivity(i);
                    }

                else {
                        Toast.makeText(getApplicationContext(),"Fill the Field",Toast.LENGTH_LONG).show();
                    }




            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_asset, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    //Background Working
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {



            return POST(urls[0],asset_info);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Asset Added!", Toast.LENGTH_LONG).show();
        }
    }

    //Http Post Request for Adding Company_Info
    public  String POST(String url,Asset_Info asset_info){
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            String jsonstring = "";
            JSONObject jsonobject = new JSONObject();
            //Build JSON object
            jsonobject.accumulate("tag",asset_info.getAsset_TAG());
            jsonobject.accumulate("type",asset_info.getAsset_Type());
            jsonobject.accumulate("assigned_to",asset_info.getAsset_AssignTo());
            jsonobject.accumulate("location",asset_info.getAsset_Location());

            jsonstring= jsonobject.toString();
            StringEntity stringentity = new StringEntity(jsonstring);
            httppost.setEntity(stringentity);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");
            httppost.setHeader("X-Parse-Application-Id","myAppId");

            HttpResponse httpresponse = httpclient.execute(httppost);
            String x= httpresponse.toString();
            Toast.makeText(getApplicationContext(), "response: " + x, Toast.LENGTH_LONG).show();
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
