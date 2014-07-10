package com.example.linerapp.app.util;

import android.util.Log;

import com.example.linerapp.app.model.Category;
import com.example.linerapp.app.model.Company;
import com.example.linerapp.app.model.ExtendedCompany;
import com.example.linerapp.app.model.Line;
import com.example.linerapp.app.model.LineField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ильнар on 23.06.2014.
 */
public class JSONLoader {

    private JSONLoader() {
    }

    public static String BASE_URL = "https://linerapp.com/api/v1/";

    /**
     * Returns list of categories downloaded from server
     *
     * @return categories list
     */
    public static ArrayList<Category> loadCategories() {
        String address = BASE_URL.concat("categories/");

        JSONArray jsonArray = null;
        try {
            jsonArray = getJSONFromURL(address);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
     * Returns list of companies downloaded from server
     *
     * @return companies list
     */
    public static ArrayList<Company> loadAllCompanies() {
        String address = BASE_URL.concat("companies/");

        JSONArray jsonArray = null;
        try {
            jsonArray = getJSONFromURL(address);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
     * Returns list of companies with given categories downloaded from server
     *
     * @param categories id of categories which must have company
     * @return companies with given categories id list
     */
    public static ArrayList<Company> loadCompaniesWithCategory(Integer[] categories) {

        Set<Company> set = new HashSet<>();

        for (Integer category : categories) {

            String address = BASE_URL.concat("categories/" + category + "/get_companies");

            JSONArray jsonArray = null;
            try {
                jsonArray = getJSONFromURL(address);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            if (jsonArray.length() == 0) continue;

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
            set.addAll(companies);
        }

        return new ArrayList<>(set);
    }

    public static ExtendedCompany loadCompanyById(int id) {

        String address = BASE_URL.concat("companies/" + id);

        JSONObject jsonObject = null;
        try {
            jsonObject = getJSONObjectFromURL(address);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        ExtendedCompany company = null;
        try {
            company = new ExtendedCompany(jsonObject.getInt("id"), jsonObject.getString("name"),
                    jsonObject.getString("address"));
            company.setDescription(jsonObject.getString("description"));
        } catch (JSONException e) {
            Log.e("Error", "Error parsing JSON array");
            e.printStackTrace();
        }
        return company;
    }

    public static ArrayList<Line> getLines(int companyId) {
        String address = BASE_URL.concat("companies/" + companyId + "/lines");

        JSONArray jsonArray = null;
        try {
            jsonArray = getJSONFromURL(address);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        ArrayList<Line> lines = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                lines.add(new Line(jsonObject.getInt("id"), jsonObject.getString("name"),
                        jsonObject.getString("description")));
            }
        } catch (JSONException e) {
            Log.e("Error", "Error parsing JSON array");
            e.printStackTrace();
        }
        return lines;
    }

    public static ArrayList<LineField> getLineFields(int lineId) {
        String address = BASE_URL.concat("companies/0/lines/" + lineId + "/line_fields");

        JSONArray jsonArray = null;
        try {
            jsonArray = getJSONFromURL(address);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        ArrayList<LineField> lineFields = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                lineFields.add(new LineField(jsonObject.getInt("id"), jsonObject.getString("custom_field_type"),
                        jsonObject.getString("name"), jsonObject.getString("label"), jsonObject.getString("data")));
            }
        } catch (JSONException e) {
            Log.e("Error", "Error parsing JSON array");
            e.printStackTrace();
        }
        return lineFields;
    }

    /**
     * Returns JSON array downloaded from given URL
     *
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
            Log.e("data", sb.toString());
            return jsonArray;
        } catch (JSONException e) {
            Log.e("Error parsing data", "\n" + sb.toString() + "\n");
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONObjectFromURL(String address) throws IOException {

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
            JSONObject jsonObject = new JSONObject(sb.toString());
            Log.e("data", sb.toString());
            return jsonObject;
        } catch (JSONException e) {
            Log.e("Error parsing data", "\n" + sb.toString() + "\n");
            e.printStackTrace();
        }
        return null;
    }
}
