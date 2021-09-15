package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

//    DatePickerDialog component
    private DatePickerDialog datePickerDialog;
//    Button to activate the DatePickerDialog
    private TextView dateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatePicker();
        dateButton = findViewById(R.id.date_of_birth_input);
//        Set initial text in the date picker to be today's date
        dateButton.setHint(getTodaysDate());

//        Gender spinner
        Spinner spinner = (Spinner) findViewById(R.id.gender_spinner);
//         Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
//         Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//         Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    private String getTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(dayOfMonth, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
//                When date is set, assign it to the button as text
                dateButton.setHint(date);
            }
        };


//        Make the initial datePicker's date today's date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, dayOfMonth);

    }

    private String makeDateString(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }

//    Function to open the datePicker from onclick on button
    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

//    Function to send data to Answer activity
    public void sendInfo(View view) {
        Intent intent = new Intent(this, AnswerActivity.class);
        startActivity(intent);
    }

}