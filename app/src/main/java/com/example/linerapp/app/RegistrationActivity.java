package com.example.linerapp.app;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.linerapp.app.model.LineField;
import com.example.linerapp.app.util.JSONLoader;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;


public class RegistrationActivity extends Activity {


    public static String EXTRA_LINE_ID = "line_id";
    static String EXTRA_COMPANY_SHORT_URL = "company_short_url";
    ArrayList<LineField> lineFields;
    ArrayList<View> values = new ArrayList<>();
    LinearLayout fields;

    String companyUrl;

    int DIALOG_DATE = 1;
    int DIALOG_TIME = 2;

    int y;
    int m;
    int d;
    int h;
    int min;
    TextView dateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Calendar calendar = Calendar.getInstance();
        y = calendar.get(Calendar.YEAR);
        m = calendar.get(Calendar.MONTH);
        d = calendar.get(Calendar.DATE);
        h = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);

        fields = (LinearLayout) findViewById(R.id.fields);

        companyUrl = getIntent().getExtras().getString(EXTRA_COMPANY_SHORT_URL);

        new LineFieldsJSONLoader().execute(getIntent().getExtras().getInt(EXTRA_LINE_ID));
    }

    public void initLineFields(ArrayList<LineField> list) {
        lineFields = list;

        for (LineField field : lineFields) {
            TextView label = new TextView(this);
            label.setTextSize(20);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 3, 0, 3);
            label.setLayoutParams(layoutParams);
            label.setText(field.getLabel());
            fields.addView(label);


            if (!field.getType().equals("select") && !field.getType().equals("datetime_picker")) {
                EditText text = new EditText(this);

                if (field.getType().equals("string")) {

                    text.setInputType(InputType.TYPE_CLASS_TEXT);
                } else if (field.getType().equals("phone")) {

                    text.setInputType(InputType.TYPE_CLASS_PHONE);
                }

                values.add(text);
                fields.addView(text);
            } else {
                if (field.getType().equals("datetime_picker")) {
                    dateTime = new TextView(this);
                    dateTime.setTextSize(20);
                    dateTime.setBackgroundResource(R.drawable.button_selector);
                    dateTime.setClickable(true);
                    dateTime.setPadding(10, 10, 10, 10);
                    dateTime.setGravity(Gravity.CENTER);
                    dateTime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showDialog(DIALOG_DATE);
                        }
                    });
                    initDateTime();
                    fields.addView(dateTime);
                    values.add(dateTime);
                } else if (field.getType().equals("select")) {
                    String[] values = field.getData().split(",");
                    Spinner spinner = new Spinner(this);
                    spinner.setBackgroundResource(R.drawable.button_selector);
                    spinner.setClickable(true);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.category_spinner, values);
                    arrayAdapter.setDropDownViewResource(R.layout.category_spinner_item);
                    spinner.setAdapter(arrayAdapter);

                    fields.addView(spinner);
                    this.values.add(spinner);
                }
            }
        }


        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < lineFields.size(); i++) {
                    LineField lineField = lineFields.get(i);
                    if (!lineField.getType().equals("select")) {
                        lineField.setValue(((TextView) values.get(i)).getText().toString());
                    } else {
                        lineField.setValue(((Spinner) values.get(i)).getSelectedItem().toString());
                    }
                }
                submitForm();
            }
        });
    }

    private void submitForm() {
        JSONObject jsonObject = new JSONObject();
        try {
            for (LineField lineField : lineFields) {
                jsonObject.put(lineField.getType(), lineField.getValue());
            }

            new SubmitJSONLoader().execute(jsonObject);
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error submitting form", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void initDateTime() {
        if (dateTime != null) {
            dateTime.setText(d + "." + (m + 1) + "." + y + " " + h + ":" + (min < 10 ? "0" : "") + min);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                            y = i;
                            m = i2;
                            d = i3;
                            showDialog(DIALOG_TIME);
                        }
                    }, y, m, d
            );
            return datePickerDialog;
        } else if (id == DIALOG_TIME) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i2) {
                            h = i;
                            min = i2;
                            initDateTime();
                        }
                    }, h, min, true
            );
            return timePickerDialog;
        }
        return super.onCreateDialog(id);
    }


    class LineFieldsJSONLoader extends AsyncTask<Integer, Void, ArrayList<LineField>> {

        @Override
        protected ArrayList<LineField> doInBackground(Integer... integers) {
            return JSONLoader.getLineFields(integers[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<LineField> list) {
            initLineFields(list);
        }
    }

    class SubmitJSONLoader extends AsyncTask<JSONObject, Void, Void> {

        @Override
        protected Void doInBackground(JSONObject... jsonObjects) {
            JSONLoader.submitForm(jsonObjects[0], companyUrl);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), "Заявка успешно оставлена", Toast.LENGTH_SHORT);
        }
    }
}
