package com.example.linerapp.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.linerapp.app.model.Category;
import com.example.linerapp.app.model.Company;
import com.example.linerapp.app.util.CompanyListAdapter;
import com.example.linerapp.app.util.JSONLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class CompanyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_layout);

        CompanyJSONLoader companyJSONLoader = new CompanyJSONLoader();
        companyJSONLoader.execute();
        CategoryJSONLoader categoryJSONLoader = new CategoryJSONLoader();
        categoryJSONLoader.execute();
    }

    public void initCategorySpinner(List<Category> categories) {
        Spinner spinner = (Spinner) findViewById(R.id.category_spinner);

        List<String> values = new ArrayList<>();
        for (Category category : categories) {
            values.add(category.getName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner, values);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);

        spinner.setAdapter(arrayAdapter);
    }

    public void initCompanyList(List<Company> companies) {
        ListView listView = (ListView) findViewById(R.id.company_listView);

        ListAdapter companyListAdapter = new CompanyListAdapter(this, companies);

        listView.setAdapter(companyListAdapter);
    }


    class CompanyJSONLoader extends AsyncTask<Void, Void, List<Company>> {

        @Override
        protected List<Company> doInBackground(Void... voids) {

            return JSONLoader.loadCompanies();
        }

        @Override
        protected void onPostExecute(List<Company> companies) {
            initCompanyList(companies);
        }
    }

    class CategoryJSONLoader extends AsyncTask<Void, Void, List<Category>> {

        @Override
        protected List<Category> doInBackground(Void... voids) {
            return JSONLoader.loadCategories();
        }

        @Override
        protected void onPostExecute(List<Category> categories) {
            initCategorySpinner(categories);
        }
    }
}
