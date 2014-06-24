package com.example.linerapp.app.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.linerapp.app.R;
import com.example.linerapp.app.model.Company;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Ильнар on 24.06.2014.
 */
public class CompanyListAdapter extends ArrayAdapter<Company> {

    private List<Company> companies;

    public CompanyListAdapter(Context context, List<Company> companies) {
        super(context, R.layout.company_listview_item, companies);
        this.companies = companies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        view = inflater.inflate(R.layout.company_listview_item, null);
        ((TextView) view.findViewById(R.id.company_name)).setText(companies.get(position).getName());
        ((TextView) view.findViewById(R.id.company_address)).setText(companies.get(position).getAddress());
        return view;
    }
}
