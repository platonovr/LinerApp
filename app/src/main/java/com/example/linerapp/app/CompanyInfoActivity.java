package com.example.linerapp.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linerapp.app.customization.ExpandablePanel;
import com.example.linerapp.app.database.SqlCommand;
import com.example.linerapp.app.model.Company;
import com.example.linerapp.app.model.ExtendedCompany;
import com.example.linerapp.app.util.JSONLoader;

import java.util.List;


public class CompanyInfoActivity extends Activity {

    public static final String EXTRA_CompanyInfoActivity = "com.example.linerapp.app.CompanyInfoActivity.EXTRA_CompanyInfoActivity";
    private boolean favorite;

    private static class ViewHolder {
        static ImageView companyView;
        static TextView companyName;
        static TextView companyDescription;
        static TextView companyAddress;
        static Button fav_btn;
        static Button phone_btn;
        static ExpandablePanel ex_pnl;
        static Button reg_button;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);

        ViewHolder.companyName = (TextView) findViewById(R.id.company_name_text);
        ViewHolder.companyDescription = (TextView) findViewById(R.id.company_description_text);
        ViewHolder.companyAddress = (TextView) findViewById(R.id.company_address_text);
        ViewHolder.fav_btn = (Button) findViewById(R.id.favorites_btn);
        ViewHolder.phone_btn = (Button) findViewById(R.id.phone_btn);
        ViewHolder.ex_pnl = (ExpandablePanel) findViewById(R.id.ex_panel);
        ViewHolder.reg_button = (Button) findViewById(R.id.online_reg_button);

        final int companyId = getIntent().getExtras().getInt(EXTRA_CompanyInfoActivity);
        favorite = SqlCommand.get(getApplicationContext()).findRow(companyId);
        Log.d("My", "" + favorite);
        ViewHolder.fav_btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_navigation_accept, 0, 0, 0);
        ViewHolder.fav_btn.setText("В избранное");
        if (favorite) {
            ViewHolder.fav_btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_navigation_cancel, 0, 0, 0);
            ViewHolder.fav_btn.setText("Убрать");
        }

        ViewHolder.reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LinesListActivity.class);
                intent.putExtra(LinesListActivity.EXTRA_COMPANY_ID, companyId);
                startActivity(intent);
            }
        });

        new ExtendedCompanyJSONLoader().execute(new Integer(companyId));
    }

    public void initCompanyView(final ExtendedCompany company) {
        ViewHolder.companyName.setText(company.getName());
        ViewHolder.companyDescription.setText(company.getDescription());
        ViewHolder.companyAddress.setText(company.getAddress());
        ViewHolder.fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favorite) {
                    Log.d("My", "In DB");
                    SqlCommand.get(getApplicationContext()).deleteRow(company.getId());
                    ViewHolder.fav_btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_navigation_accept, 0, 0, 0);
                    ViewHolder.fav_btn.setText("В избранное");
                    favorite = !favorite;
                    Intent intent = new Intent();
                    intent.putExtra("name", company.getName());
                    setResult(RESULT_OK, intent);
                } else {
                    SqlCommand.get(getApplicationContext()).addRow(company.getId(), company.getName());
                    ViewHolder.fav_btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_navigation_cancel, 0, 0, 0);
                    Log.d("My", "Not In DB");
                    ViewHolder.fav_btn.setText("Убрать");
                    favorite = !favorite;
                }
            }
        });
        ViewHolder.phone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numb = "tel:" + "89600471293";
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(numb));
                startActivity(intent);
            }
        });
        ViewHolder.ex_pnl.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                Button btn = (Button) handle;
                btn.setText(R.string.show);
            }

            public void onExpand(View handle, View content) {
                Button btn = (Button) handle;
                btn.setText(R.string.hide);
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
