package com.example.linerapp.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.linerapp.app.model.Category;
import com.example.linerapp.app.model.Company;
import com.example.linerapp.app.util.CategorySpinnerAdapter;
import com.example.linerapp.app.util.CompanyListAdapter;
import com.example.linerapp.app.util.DataStorage;
import com.example.linerapp.app.util.JSONLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;


public class CompanyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_layout);

        initCategorySpinner(DataStorage.getCategories());
    }

    public void initCategorySpinner(List<Category> categories) {
        final Spinner spinner = (Spinner) findViewById(R.id.category_spinner);

        ArrayAdapter<Category> categorySpinnerAdapter = new CategorySpinnerAdapter(this, categories);

        spinner.setAdapter(categorySpinnerAdapter);

        new CompanyJSONLoader().execute(((Category) spinner.getSelectedItem()).getId());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                new CompanyJSONLoader().execute(((Category) spinner.getSelectedItem()).getId());
                Toast.makeText(getBaseContext(), ((Category) spinner.getSelectedItem()).getDescription(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void initCompanyList(List<Company> companies) {
        ListView listView = (ListView) findViewById(R.id.company_listView);

        ListAdapter companyListAdapter = new CompanyListAdapter(this, companies);

        listView.setAdapter(companyListAdapter);
    }


    class CompanyJSONLoader extends AsyncTask<Integer, Void, List<Company>> {

        @Override
        protected List<Company> doInBackground(Integer... integers) {
            if (integers.length == 0) {
                return JSONLoader.loadAllCompanies();
            } else {
                return JSONLoader.loadCompaniesWithCategory(integers);
            }
        }

        @Override
        protected void onPostExecute(List<Company> companies) {
            initCompanyList(companies);
        }
    }
}
