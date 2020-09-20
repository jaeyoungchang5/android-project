package com.example.android_project.utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkUtils {

    // this method is specific to the countries URL
    public static URL buildAcronymsUrl(String searchTerm){
        // get string url
        String acronymUrlString = "http://www.nactem.ac.uk/software/acromine/dictionary.py?sf=" + searchTerm;
        URL acronymUrl = null;
        try{
            acronymUrl = new URL(acronymUrlString);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        Log.d("debug", "acronym url in networks util: " + acronymUrl);
        return acronymUrl;
    }

    // this method can be used with any URL object
    public static String getResponseFromUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); // getting the connection open
        Log.d("debug", "url " + urlConnection.toString());
        try{
            Log.d("debug", "url .... got here1");
            InputStream in = urlConnection.getInputStream();
            Log.d("debug", "url .... got here2");
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A"); // delimiter for end of message
            boolean hasInput = scanner.hasNext();
            if(hasInput) {
                Log.d("debug", "url .... got here3");
                return scanner.next(); // success

            }
        }catch(Exception e){
            Log.d("debug", "url .... got here5");
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> parseAcronymsJson(String acronymsResponseString){
        ArrayList<String> acronymList = new ArrayList<String>();
        try{
            JSONArray jarr = new JSONArray(acronymsResponseString);
            JSONObject jobj = (JSONObject)jarr.get(0);
            JSONArray acronyms = (JSONArray)jobj.get("lfs");
            for (int i = 0; i < acronyms.length(); i++){
                JSONObject acronym = (JSONObject)acronyms.get(i);
                String acronymStr = (String)acronym.get("lf");
                acronymList.add(acronymStr);
            }
        } catch(JSONException e){
            e.printStackTrace();
        }
        return acronymList;
    }


}
