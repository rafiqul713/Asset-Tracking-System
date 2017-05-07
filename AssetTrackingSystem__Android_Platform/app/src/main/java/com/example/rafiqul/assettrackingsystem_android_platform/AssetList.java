package com.example.rafiqul.assettrackingsystem_android_platform;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AssetList extends ListActivity {
    private ProgressDialog pDialog;
    JSONArray arr;
    // URL to get contacts JSON
    private static String url = "https://ats-api.scalingo.io/parse/assets";

    // JSON Node names
    private static final String TAG_ID = "tag";
    private static final String TAG_Type = "type";
    private static final String TAG_Assigned_To = "assigned_to";
    private static final String TAG_Location = "location";
    private static final String TAG_Update_At = "updatedAt";
    private static final String TAG_CreatedAt = "createdAt";

    ArrayList<HashMap<String, String>> assetlist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_list);

        assetlist = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();


        new GetContacts().execute();
    }
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(AssetList.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            HttpRequestHandler sh = new HttpRequestHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, HttpRequestHandler.GET);

            if (jsonStr != null) {

                try{
                    arr= new JSONArray(jsonStr);

                }
                catch (Exception e){

                }
                Log.d("Response: ", "---->" + jsonStr);
                Log.d("Length of array",arr.length()+"");

                try {
                    for(int i=0;i<arr.length();i++)
                    {
                        JSONObject obj= arr.getJSONObject(i);
                        String tag= obj.getString("tag");
                        String location= obj.getString("location");
                        String type= obj.getString("type");
                        String assigned_to= obj.getString("assigned_to");
                        String created_at= obj.getString("createdAt");
                        String update_At= obj.getString("updatedAt");
                        HashMap<String, String> Asset = new HashMap<String, String>();
                        Asset.put(TAG_ID, tag);
                        Asset.put(TAG_Type, type);
                        Asset.put(TAG_Location, location);
                        Asset.put(TAG_Assigned_To, assigned_to);
                        Asset.put(TAG_CreatedAt, created_at);
                        Asset.put(TAG_Update_At, update_At);
                        assetlist.add(Asset);

                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("HttpRequestHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    AssetList.this, assetlist,
                    R.layout.list_item, new String[] { TAG_ID, TAG_Location,
                    TAG_CreatedAt,TAG_Assigned_To,TAG_Update_At }, new int[] { R.id.taglist,
                    R.id.locationList, R.id.assetAddedOn,R.id.AssignTo,R.id.lastAssignOn });

            setListAdapter(adapter);
        }

    }
}


class HttpRequestHandler {

    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    public HttpRequestHandler() {

    }


    public String makeServiceCall(String url, int method) {
        return this.makeServiceCall(url, method, null);
    }

    public String makeServiceCall(String url, int method,
                                  List<NameValuePair> params) {
        try {
            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }

                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {
                // appending params to url
                if (params != null) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);
                httpGet.setHeader("Accept", "application/json");
                httpGet.setHeader("Content-type", "application/json");
                httpGet.setHeader("X-Parse-Application-Id", "myAppId");

                httpResponse = httpClient.execute(httpGet);

            }
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }
}
