package com.example.linerapp.app.util;

import com.example.linerapp.app.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ильнар on 25.06.2014.
 */
public class DataStorage {

    static List<Category> categories = new ArrayList<>();

    public static List<Category> getCategories() {
        return categories;
    }

    public static void setCategories(List<Category> categories) {
        DataStorage.categories = categories;
    }
}
