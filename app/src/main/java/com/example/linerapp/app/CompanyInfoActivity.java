package com.example.linerapp.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linerapp.app.model.Company;
import com.example.linerapp.app.model.ExtendedCompany;
import com.example.linerapp.app.util.JSONLoader;

import java.util.List;


public class CompanyInfoActivity extends Activity {

    public static final String EXTRA_CompanyInfoActivity = "com.example.linerapp.app.CompanyInfoActivity.EXTRA_CompanyInfoActivity";

    private static class ViewHolder {
        static ImageView companyView;
        static TextView companyName;
        static TextView companyDescription;
        static TextView companyAddress;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);

        ViewHolder.companyName = (TextView) findViewById(R.id.company_name_text);
        ViewHolder.companyDescription = (TextView) findViewById(R.id.company_description_text);
        ViewHolder.companyAddress = (TextView) findViewById(R.id.company_address_text);


        Bundle extraCompany = getIntent().getExtras().getBundle(EXTRA_CompanyInfoActivity);
        int companyId = extraCompany.getInt("company.id");

        new ExtendedCompanyJSONLoader().execute(new Integer(companyId));
    }

    public void initCompanyView(ExtendedCompany company) {
        ViewHolder.companyName.setText(company.getName());
        ViewHolder.companyDescription.setText(company.getDescription());
        ViewHolder.companyAddress.setText(company.getAddress());
    }

    class ExtendedCompanyJSONLoader extends AsyncTask<Integer, Void, ExtendedCompany> {

        @Override
        protected ExtendedCompany doInBackground(Integer... integers) {
            return JSONLoader.loadCompanyById(integers[0]);
        }

        @Override
        protected void onPostExecute(ExtendedCompany company) {
            initCompanyView(company);
        }
    }
}
