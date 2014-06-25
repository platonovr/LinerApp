package com.example.linerapp.app.util;

import android.os.Bundle;

import com.example.linerapp.app.model.Company;

/**
 * Created by Stas on 25.06.2014.
 */
//метод передачи между активити компании с помощью Bundle
public class BundleHelper {

    public static final String ID = "company.id";
    public static final String NAME = "company.name";
    public static final String ADDRESS = "company.address";

    public static Bundle bundleCompany(Company company){
        Bundle bundle = new Bundle();
        bundle.putString(NAME, company.getName());
        bundle.putInt(ID, company.getId());
        bundle.putString(ADDRESS,company.getAddress());

        return bundle;
    }

    public static Company unBundleCompany(Bundle companyBundle){
        Company comp = new Company(companyBundle.getInt(ID),companyBundle.getString(NAME),companyBundle.getString(ADDRESS));

        return comp;
    }
}
