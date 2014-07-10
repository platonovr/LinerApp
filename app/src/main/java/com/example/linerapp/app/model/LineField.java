package com.example.linerapp.app.model;

/**
 * Created by Ильнар on 10.07.2014.
 */
public class LineField {

    private int id;

    private String type;

    private String name;

    private String label;

    private String data;

    private String value;

    public LineField(int id, String type, String name, String label, String data) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.label = label;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
