package com.example.linerapp.app;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linerapp.app.database.SqlCommand;
import com.example.linerapp.app.model.Company;
import com.example.linerapp.app.model.ExtendedCompany;
import com.example.linerapp.app.util.JSONLoader;

import java.util.List;


public class CompanyInfoActivity extends Activity {

    public static final String EXTRA_CompanyInfoActivity = "com.example.linerapp.app.CompanyInfoActivity.EXTRA_CompanyInfoActivity";
    private boolean favorite ;

    private static class ViewHolder {
        static ImageView companyView;
        static TextView companyName;
        static TextView companyDescription;
        static TextView companyAddress;
        static ImageView favorite_btn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);

        ViewHolder.companyName = (TextView) findViewById(R.id.company_name_text);
        ViewHolder.companyDescription = (TextView) findViewById(R.id.company_description_text);
        ViewHolder.companyAddress = (TextView) findViewById(R.id.company_address_text);
        ViewHolder.favorite_btn = (ImageButton) findViewById(R.id.add_to_favorites);

        int companyId = getIntent().getExtras().getInt(EXTRA_CompanyInfoActivity);
        favorite =SqlCommand.get(getApplicationContext()).findRow(companyId);
        if (favorite){

            ViewHolder.favorite_btn.setBackground(getResources().getDrawable(R.drawable.remove_favorites));
        }

        new ExtendedCompanyJSONLoader().execute(new Integer(companyId));
    }

    public void initCompanyView(final ExtendedCompany company) {
        ViewHolder.companyName.setText(company.getName());
        ViewHolder.companyDescription.setText(company.getDescription());
        ViewHolder.companyAddress.setText(company.getAddress());
        ViewHolder.favorite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favorite){
                    Log.d("My","In DB");
                    SqlCommand.get(getApplicationContext()).deleteRow(company.getId());
                    ViewHolder.favorite_btn.setBackground(getResources().getDrawable(R.drawable.add_favorites));
                    favorite = !favorite;
                } else {
                    SqlCommand.get(getApplicationContext()).addRow(company.getId(), company.getName());
                    ViewHolder.favorite_btn.setBackground(getResources().getDrawable(R.drawable.remove_favorites));
                    Log.d("My","Not In DB");
                    favorite = !favorite;
                }
            }
        });

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
