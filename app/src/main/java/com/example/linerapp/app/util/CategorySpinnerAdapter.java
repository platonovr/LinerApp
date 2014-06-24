package com.example.linerapp.app.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.linerapp.app.R;
import com.example.linerapp.app.model.Category;

import java.util.List;

/**
 * Created by Ильнар on 25.06.2014.
 */
public class CategorySpinnerAdapter extends ArrayAdapter<Category> {

    private List<Category> categories;

    public CategorySpinnerAdapter(Context context, List<Category> categories) {
        super(context, R.layout.category_spinner_item, categories);
        this.categories = categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        view = inflater.inflate(R.layout.category_spinner, null);
        ((TextView) view.findViewById(R.id.category_spinner_text)).setText(categories.get(position).getName());
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = null;
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        view = inflater.inflate(R.layout.category_spinner_item, null);
        ((TextView) view.findViewById(R.id.category_spinner_item_text)).setText(categories.get(position).getName());
        return view;
    }

    @Override
    public Category getItem(int position) {
        return categories.get(position);
    }
}
