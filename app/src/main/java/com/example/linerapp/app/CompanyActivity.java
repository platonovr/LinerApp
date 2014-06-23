package com.example.linerapp.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class CompanyActivity extends Activity {

    String[] values = new String[] {"Medicine", "Banks", "Hair-dress", "IT"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_layout);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> categories = new ArrayAdapter<>(this, R.layout.spinner, values);
        categories.setDropDownViewResource(R.layout.spinner_item);


        spinner.setAdapter(categories);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), values[i] + " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
