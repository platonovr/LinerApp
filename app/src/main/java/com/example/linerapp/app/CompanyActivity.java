package com.example.linerapp.app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.util.List;


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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void initCompanyList(List<Company> companies) {
        if (companies == null) {
            Toast.makeText(getApplicationContext(), "Ошибка при загрузке данных, проверьте подключение к Интернету", Toast.LENGTH_SHORT).show();
            return;
        }

        final ListView listView = (ListView) findViewById(R.id.company_listView);

        final ListAdapter companyListAdapter = new CompanyListAdapter(this, companies);

        listView.setAdapter(companyListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), CompanyInfoActivity.class);
                intent.putExtra(CompanyInfoActivity.EXTRA_CompanyInfoActivity,((Company) companyListAdapter.getItem(i)).getId());
                startActivity(intent);
            }
        });
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
