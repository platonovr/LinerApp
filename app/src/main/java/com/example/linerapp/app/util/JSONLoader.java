package com.example.linerapp.app.util;

import android.util.Log;

import com.example.linerapp.app.model.Category;
import com.example.linerapp.app.model.Company;
import com.example.linerapp.app.model.ExtendedCompany;
import com.example.linerapp.app.model.Line;
import com.example.linerapp.app.model.LineField;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
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
import java.util.List;
import java.util.Set;

/**
 * Created by Ильнар on 23.06.2014.
 */
public class JSONLoader {

    static ArrayList<ExtendedCompany> ecompanies = new ArrayList<>();
    static ArrayList<Company> companies = new ArrayList<>();
    static ArrayList<Line> lines = new  ArrayList<>();
    static ArrayList<Category> categories = new ArrayList<>();
    static ArrayList<LineField> lineFields = new ArrayList<>();

    static {
        ecompanies.add(new ExtendedCompany(1, "IIS", "", "Software engineering"));
        ecompanies.add(new ExtendedCompany(2, "Hospital 12", "", "Medicine service"));
        ecompanies.add(new ExtendedCompany(3, "Alfa-bank", "", "Banking service"));
        ecompanies.add(new ExtendedCompany(4, "Consult", "", "Law consulting"));
        ecompanies.add(new ExtendedCompany(5, "LTW", "", "Public services"));
        ecompanies.add(new ExtendedCompany(6, "MegaMall", "", "Super market"));
        companies.add(new ExtendedCompany(1, "IIS", ""));
        companies.add(new ExtendedCompany(2, "Hospital 12", ""));
        companies.add(new ExtendedCompany(3, "Alfa-bank", ""));
        companies.add(new ExtendedCompany(4, "Consult", ""));
        companies.add(new ExtendedCompany(5, "LTW", ""));
        companies.add(new ExtendedCompany(6, "MegaMall", ""));
        lines.add(new Line(1, "Line", "Line for reception", ""));
        lines.add(new Line(2, "Line", "Line for reception", ""));
        lines.add(new Line(3, "Line", "Line for reception", ""));
        lines.add(new Line(4, "Line", "Line for reception", ""));
        lines.add(new Line(5, "Line", "Line for reception", ""));
        lines.add(new Line(6, "Line", "Line for reception", ""));
        categories.add(new Category(1, "Software", "Software developing"));
        categories.add(new Category(2, "Medicine", "Hospitals"));
        categories.add(new Category(3, "Banking services", "Banks"));
        categories.add(new Category(4, "Legal services", "Lawyer"));
        categories.add(new Category(5, "Public services", "Public services"));
        categories.add(new Category(6, "Shopping", "Markets, shops"));
        lineFields.add(new LineField(1, "string", "name", "Your name:", null));
        lineFields.add(new LineField(2, "datetime_picker", "datetime", "Date and time of reception:", null));
        lineFields.add(new LineField(3, "phone", "phone", "Your phone number:", null));
    }

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

        /*JSONArray jsonArray = null;
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
        }*/
        return categories;
    }

    /**
     * Returns list of companies downloaded from server
     *
     * @return companies list
     */
    public static ArrayList<Company> loadAllCompanies() {
        String address = BASE_URL.concat("companies/");

        /*JSONArray jsonArray = null;
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
        }*/
        return companies;
    }

    public static ArrayList<Company> loadCompaniesByName(String name) {
        /*String address = BASE_URL.concat("companies/search?query=" + name);

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
        }*/
        List<Company> result = new ArrayList<>();
        for (Company company : companies) {
            if (company.getName().contains(name)) {
                result.add(company);
            }
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

            /*JSONArray jsonArray = null;
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
            }*/
            for (Company company : companies) {
                if (company.getId() == category) {
                    set.add(company);
                }
            }
//            set.addAll(companies);
        }

        return new ArrayList<>(set);
    }

    public static ExtendedCompany loadCompanyById(int id) {

        /*String address = BASE_URL.concat("companies/" + id);

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
            company.setShortUrl(jsonObject.getString("short_url"));
        } catch (JSONException e) {
            Log.e("Error", "Error parsing JSON array");
            e.printStackTrace();
        }*/
        for (ExtendedCompany ecompany : ecompanies) {
            if (ecompany.getId() == id) {
                return ecompany;
            }
        }
        return null;
//        return company;
    }

    public static ArrayList<Line> getLines(int companyId) {
       /* String address = BASE_URL.concat("companies/" + companyId + "/lines");

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
                        jsonObject.getString("description"), jsonObject.getString("short_url")));
            }
        } catch (JSONException e) {
            Log.e("Error", "Error parsing JSON array");
            e.printStackTrace();
        }*/
        return lines;
    }

    public static ArrayList<LineField> getLineFields(int lineId) {
        /*String address = BASE_URL.concat("companies/0/lines/" + lineId + "/line_fields");

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
        }*/
        return lineFields;
    }

    public static void submitForm(String toSubmit, String companyUrl, String lineUrl) throws Exception{
        /*String address = BASE_URL.concat(companyUrl + "/lines/" + lineUrl + "/submit");
        Log.e("URL", address);
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(address);
        StringEntity params = new StringEntity(toSubmit);
        Log.e("JSON", toSubmit);
        request.addHeader("X-Account-Token", "kLqqt7tR7vHTsqPZxRaT");
        request.addHeader("X-Account-Email", "api_user@linerapp.com");
        request.setEntity(params);
        HttpResponse response = client.execute(request);

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line;
        StringBuilder out = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        Log.e("Out", out.toString());
        if (out.toString().equals("Invalid order params")) {
            throw new Exception();
        }*/
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
