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
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AddUser extends Activity {

    String ADD_USER_URL="https://ats-api.scalingo.io/parse/users/new";
    EditText USER_NAME,USER_ID,USER_EMAIL,USER_PASS;
    Button CreateNewUser;
    Spinner TypeOfUser;
    New_User user;
    void setup(){
        USER_NAME = (EditText)findViewById(R.id.UserName);
        USER_ID = (EditText)findViewById(R.id.UserId);
        USER_EMAIL=(EditText)findViewById(R.id.Useremail);
        USER_PASS=(EditText)findViewById(R.id.Userpassword);
        CreateNewUser = (Button) findViewById(R.id.Create_NewUserButton);
        TypeOfUser =(Spinner) findViewById(R.id.SpinnerUserType);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        setup();

        CreateNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=USER_NAME.getText().toString().trim();
                String userID= USER_ID.getText().toString().trim();
                String userPass= USER_PASS.getText().toString().trim();
                String userEmail= USER_EMAIL.getText().toString().trim();
                String userType= TypeOfUser.getSelectedItem().toString().trim();
                Toast.makeText(getApplication(),userName+" "+userID+" "+userEmail+" "+userType,Toast.LENGTH_LONG).show();
                user= new New_User(userName,userID,userEmail,userPass,userType);


                if(!user.getUSER_NAME().equals("")
                    && !user.getUSER_ID().equals("")
                        && !user.getUSER_EMAIL().equals("")
                        && !user.getUSER_PASS().equals(""))
                {
                        if(PatternMatching.emailValidation(userEmail)) {
                            Toast.makeText(getApplication(), "User Type: " + userType, Toast.LENGTH_LONG).show();
                            new HttpAsyncTask().execute(ADD_USER_URL);

                        }
                    else
                        {
                            Toast.makeText(getApplicationContext(),"Invalid Email Given",Toast.LENGTH_LONG).show();

                        }


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
        getMenuInflater().inflate(R.menu.menu_add_user, menu);
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






    //Background Working
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {



            return POST(urls[0],user);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "User Added !", Toast.LENGTH_LONG).show();
            Intent i = new Intent(AddUser.this, Home.class);
            startActivity(i);
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


    //Http Post Request for Adding New User
    private  String POST(String url, New_User user){
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            String jsonstring = "";
            JSONObject jsonobject = new JSONObject();
            //Build JSON object
            jsonobject.accumulate("fullName",user.getUSER_NAME());
            jsonobject.accumulate("username",user.getUSER_ID());
            jsonobject.accumulate("email",user.getUSER_EMAIL());
            jsonobject.accumulate("password",user.getUSER_PASS());
            jsonobject.accumulate("userType",user.getUSER_TYPE());

            jsonstring= jsonobject.toString();
            StringEntity stringentity = new StringEntity(jsonstring);
            httppost.setEntity(stringentity);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");
            httppost.setHeader("X-Parse-Application-Id","myAppId");
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
