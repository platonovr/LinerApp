package com.example.linerapp.app.util;

import android.net.rtp.AudioCodec;
import android.os.AsyncTask;
import android.util.Log;

import com.example.linerapp.app.CompanyActivity;
import com.example.linerapp.app.model.Category;
import com.example.linerapp.app.model.Company;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ильнар on 23.06.2014.
 */
public class JSONLoader {

    private JSONLoader() {
    }

    public static String BASE_URL = "https://linerapp.com/api/v1/";

    /**
     * Returns categories list downloaded from server
     * @return categories list
     */
    public static ArrayList<Category> loadCategories() {
        String address = BASE_URL.concat("categories/");

        JSONArray jsonArray = null;
        try {
            jsonArray = getJSONFromURL(address);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Category> categories = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                categories.add(new Category(jsonObject.getInt("id"), jsonObject.getString("name"),
                        jsonObject.getString("description")));
            }
        } catch (JSONException e) {
            Log.e("Error", "Error parsing JSON array");
            e.printStackTrace();
        }
        return categories;
    }

    /**
     * Returns companies downloaded from server
     * @return companies list
     */
    public static ArrayList<Company> loadCompanies() {
        String address = BASE_URL.concat("companies/");

        JSONArray jsonArray = null;
        try {
            jsonArray = getJSONFromURL(address);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Company> companies = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                companies.add(new Company(jsonObject.getInt("id"), jsonObject.getString("name"),
                        jsonObject.getString("address")));
            }
        } catch (JSONException e) {
            Log.e("Error", "Error parsing JSON array");
            e.printStackTrace();
        }
        return companies;
    }

    /**
     * Returns JSON array downloaded from given URL
     * @param address url
     * @return downloaded JSON array
     * @throws IOException when downloaded data can't be parsed to JSON array
     */
    public static JSONArray getJSONFromURL(String address) throws IOException {

        URL url = new URL(address);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("X-Account-Token", "kLqqt7tR7vHTsqPZxRaT");
        connection.addRequestProperty("X-Account-Email", "api_user@linerapp.com");
        connection.connect();
        InputStream stream = connection.getInputStream();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line + "\n");
        }
        stream.close();

        try {
            JSONArray jsonArray = new JSONArray(sb.toString());
            return jsonArray;
        } catch (JSONException e) {
            Log.e("Error parsing data", "\n" + sb.toString() + "\n");
            e.printStackTrace();
        }
        return null;
    }
}
