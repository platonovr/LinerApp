package com.example.linerapp.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.linerapp.app.model.Category;
import com.example.linerapp.app.util.JSONLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class CompanyActivity extends Activity {

    List<Category> jsonArray;
    public static CompanyActivity INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        INSTANCE = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_layout);

        Spinner spinner = (Spinner) findViewById(R.id.category_spinner);

        JSONLoader jsonLoader = new JSONLoader();
        try {
            jsonArray = (List<Category>) jsonLoader.execute(Category.class).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), jsonArray.get(i).getId() + " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void initCategorySpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.category_spinner);

        List<String> values = new ArrayList<>();
        if (jsonArray != null) {
            for (Category category : jsonArray) {
                values.add(category.getName());
            }
        }
        ArrayAdapter<String> categories = new ArrayAdapter<>(this, R.layout.spinner, values);
        categories.setDropDownViewResource(R.layout.spinner_item);


        spinner.setAdapter(categories);
    }
}
