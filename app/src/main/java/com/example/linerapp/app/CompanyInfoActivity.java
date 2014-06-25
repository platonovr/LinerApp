package com.example.linerapp.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linerapp.app.model.Company;
import com.example.linerapp.app.util.BundleHelper;


public class CompanyInfoActivity extends Activity {

    public static final String EXTRA_CompanyInfoActivity = "com.example.linerapp.app.CompanyInfoActivity.EXTRA_CompanyInfoActivity";

    private static class ViewHolder {
        static ImageView companyView;
        static TextView companyName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);

        ViewHolder.companyName = (TextView) findViewById(R.id.company_name_text);
        Bundle extraCompany = getIntent().getExtras().getBundle(EXTRA_CompanyInfoActivity);
        Company company =  BundleHelper.unBundleCompany(extraCompany);

        ViewHolder.companyName.setText(company.getName());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.company_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
