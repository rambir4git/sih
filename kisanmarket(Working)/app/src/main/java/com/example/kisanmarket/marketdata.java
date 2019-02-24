package com.example.kisanmarket;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class marketdata extends AsyncTask<String,Void,JSONArray> {

    private JSONArray errorJSON;

    {
        try {
            errorJSON = new JSONArray("[]");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONArray marketData=null;
    private String query="";
    @Override
    protected JSONArray doInBackground(String...strings) {
        HttpURLConnection urlConnection = null;
        query= strings[0];
        String mainurl = "http://api.data.gov.in/resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd000001258e7305e65b42f9567b051a7aee2fb4&format=json&limit=10000&&field=records&filters[state]=";
        String modifiedurl= mainurl+query;
        URL url = null;
        try {
            url = new URL(modifiedurl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader inReader = new BufferedReader(new InputStreamReader(in));
            String inputLine = "";
            String fullStr = "";
            while ((inputLine = inReader.readLine()) != null) {
                fullStr += inputLine;
            }

            JSONObject jsonObj = new JSONObject(fullStr);
            marketData = jsonObj.getJSONArray("records");
        } catch (IOException e) {
            e.printStackTrace();
            return errorJSON;
        } catch (JSONException e) {
            e.printStackTrace();
            return errorJSON;
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return marketData;
    }
}

