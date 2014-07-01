package com.example.linerapp.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linerapp.app.model.Category;
import com.example.linerapp.app.model.Company;
import com.example.linerapp.app.util.CategorySpinnerAdapter;
import com.example.linerapp.app.util.CompanyListAdapter;
import com.example.linerapp.app.util.DataStorage;
import com.example.linerapp.app.util.JSONLoader;

import java.util.List;


public class CompanyActivity extends Activity {

    ProgressBar progressBar;
    Button searchOrCategory;
    boolean barState;
    Spinner categories;
    EditText search;
    FrameLayout actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_layout);

        progressBar = (ProgressBar) findViewById(R.id.company_progressBar);
        searchOrCategory = (Button) findViewById(R.id.search_or_category_button);
        barState = false;
        categories = (Spinner) findViewById(R.id.category_spinner);
        initCategorySpinner(DataStorage.getCategories());
        search = (EditText) getLayoutInflater().inflate(R.layout.search_layout, null);
        actionBar = (FrameLayout) findViewById(R.id.action_bar);
        searchOrCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionBar.removeAllViews();
                if (barState) {
                    actionBar.addView(categories);
                    reloadCompaniesByCategory();
                } else {
                    actionBar.addView(search);
                }
                barState = !barState;
            }
        });
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if ((keyEvent != null && keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)
                || keyEvent == null) {
                    Toast.makeText(getApplicationContext(), search.getText(), Toast.LENGTH_SHORT).show();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

    public void initCategorySpinner(List<Category> categories) {
        ArrayAdapter<Category> categorySpinnerAdapter = new CategorySpinnerAdapter(this, categories);

        this.categories.setAdapter(categorySpinnerAdapter);

        new CompanyJSONLoader().execute(((Category) this.categories.getSelectedItem()).getId());

        this.categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                new CompanyJSONLoader().execute(((Category) CompanyActivity.this.categories.getSelectedItem()).getId());
                clearCompanyList();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void reloadCompaniesByCategory() {
        new CompanyJSONLoader().execute(((Category) CompanyActivity.this.categories.getSelectedItem()).getId());
        clearCompanyList();
        progressBar.setVisibility(View.VISIBLE);
    }

    public void initCompanyList(List<Company> companies) {
        if (companies == null) {
            Toast.makeText(getApplicationContext(), "Ошибка при загрузке данных, проверьте подключение к Интернету", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.INVISIBLE);

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

    public void clearCompanyList() {
        final ListView listView = (ListView) findViewById(R.id.company_listView);
        listView.setAdapter(null);
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
