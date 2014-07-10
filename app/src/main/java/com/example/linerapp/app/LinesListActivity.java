package com.example.linerapp.app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.linerapp.app.model.ExtendedCompany;
import com.example.linerapp.app.model.Line;
import com.example.linerapp.app.util.JSONLoader;

import java.util.ArrayList;


public class LinesListActivity extends Activity {

    ListView linesList;

    ArrayList<Line> lines;

    static String EXTRA_COMPANY_ID = "company_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lines_list);

        linesList = (ListView) findViewById(R.id.lines_list);
        new LinesJSONLoader().execute(getIntent().getExtras().getInt(EXTRA_COMPANY_ID));
    }

    public void initLinesList(final ArrayList<Line> lines) {
        this.lines = lines;
        String[] names = new String[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            names[i] = lines.get(i).getName();
        }
        linesList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names));
        linesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = lines.get(i).getId();
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                intent.putExtra(RegistrationActivity.EXTRA_LINE_ID, id);
                startActivity(intent);
            }
        });
    }

    class LinesJSONLoader extends AsyncTask<Integer, Void, ArrayList<Line>> {

        @Override
        protected ArrayList<Line> doInBackground(Integer... integers) {
            return JSONLoader.getLines(integers[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Line> lines) {
            initLinesList(lines);
        }
    }
}
