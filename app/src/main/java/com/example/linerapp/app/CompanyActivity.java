package com.example.linerapp.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.linerapp.app.model.Category;
import com.example.linerapp.app.model.Company;
import com.example.linerapp.app.util.CompanyListAdapter;
import com.example.linerapp.app.util.JSONLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class CompanyActivity extends Activity {

    public static CompanyActivity INSTANCE;
    List<Category> categoryJsonArray;
    List<Company> companyJsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        INSTANCE = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_layout);

        JSONLoader jsonLoader = new JSONLoader();
        try {
            categoryJsonArray = (List<Category>) jsonLoader.execute(Category.class).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        jsonLoader = new JSONLoader();
        try {
            companyJsonArray = (List<Company>) jsonLoader.execute(Company.class).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void initCategorySpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.category_spinner);

        List<String> values = new ArrayList<>();
        if (categoryJsonArray != null) {
            for (Category category : categoryJsonArray) {
                values.add(category.getName());
            }
        }
        ArrayAdapter<String> categories = new ArrayAdapter<>(this, R.layout.spinner, values);
        categories.setDropDownViewResource(R.layout.spinner_item);


        spinner.setAdapter(categories);
    }

    public void initCompanyList() {
        ListView listView = (ListView) findViewById(R.id.company_listView);

        ListAdapter companyListAdapter = new CompanyListAdapter(this, companyJsonArray);

        listView.setAdapter(companyListAdapter);
    }
}
