package com.example.polution;

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

public class polutionData extends AsyncTask<Object,String,String> {

    @Override
    protected String doInBackground(Object... objects) {

        String cityText = (String) objects[0];
        String finalstr="";
        String returnstr="";
        JSONArray polutionData;
        HttpURLConnection urlConnection = null;
        String mainurl = "http://api.data.gov.in/resource/3b01bcb8-0b14-4abf-b6f2-c1bfd384ba69?api-key=579b464db66ec23bdd000001258e7305e65b42f9567b051a7aee2fb4&format=json";
        URL url = null;
        try {
            url = new URL(mainurl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection1 = null;
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
            JSONArray result = jsonObj.getJSONArray("records");
            for(int i=0;i<result.length();i++)
            {
                int j=0;
                JSONObject jsonTemp = result.getJSONObject(i);
                if(jsonTemp.get("station").equals(cityText)){
                    finalstr += result.getJSONObject(i).toString();
                }
            }
             returnstr = "["+result.getJSONObject(0).toString()+","+
                    result.getJSONObject(1).toString()+","+
                    result.getJSONObject(2).toString()+","+
                    result.getJSONObject(3).toString()+","+
                    result.getJSONObject(4).toString()+","+
                    result.getJSONObject(5).toString()+","+
                    result.getJSONObject(6).toString()+"]";

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {

            urlConnection.disconnect();
        }

        return returnstr;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }
}

