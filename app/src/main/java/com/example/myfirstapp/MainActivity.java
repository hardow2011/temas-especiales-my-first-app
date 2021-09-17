package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

//    DatePickerDialog component
    private DatePickerDialog datePickerDialog;
//    Button to activate the DatePickerDialog
    private TextView dateButton;
    //        Gender spinner
    private Spinner genderSpinner;
    private RadioGroup programming_preference_radio_button_group;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatePicker();
        dateButton = findViewById(R.id.date_of_birth_input);
//        Set initial text in the date picker to be today's date
        dateButton.setHint(getTodaysDate());

        genderSpinner = findViewById(R.id.gender_spinner);
//         Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
//         Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//         Apply the adapter to the spinner
        genderSpinner.setAdapter(adapter);

        programming_preference_radio_button_group = findViewById(R.id.programming_preference_radio_button_group);

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

//        Get data from main layout
        EditText first_name = findViewById(R.id.name_input);
        EditText last_name = findViewById(R.id.last_name_input);
        int programming_preference_id = programming_preference_radio_button_group.getCheckedRadioButtonId();
        RadioButton programming_preference = findViewById(programming_preference_id);
        String programming_preference_msg = "";
        String selected_languages_msg = "";

        String gender_msg = "";

        switch (genderSpinner.getSelectedItemPosition()) {
            case 0:
                gender_msg = "un hombre";
                break;
            default:
                gender_msg = "una mujer";
                break;
        }

        //        Create array of the checkboxes
        ArrayList<CheckBox> checkedCheckboxesArray = new ArrayList<CheckBox>();
        checkedCheckboxesArray.add(findViewById(R.id.lenguage_java));
        checkedCheckboxesArray.add(findViewById(R.id.lenguage_js));
        checkedCheckboxesArray.add(findViewById(R.id.lenguage_c));
        checkedCheckboxesArray.add(findViewById(R.id.lenguage_python));
        checkedCheckboxesArray.add(findViewById(R.id.lenguage_go_lang));
        checkedCheckboxesArray.add(findViewById(R.id.lenguage_c_sharp));

        int checkedCheckboxesArraySize = 0;

//        Remove the non-checked-checkboxes from the array
        for (CheckBox languageCheckbox : checkedCheckboxesArray) {
            if(languageCheckbox.isChecked()) {
                checkedCheckboxesArraySize++;
            }
        }

        switch(programming_preference.getId()) {
            case R.id.programming_preference_radio_button_yes:
                programming_preference_msg = "Me gusta programar.";

                switch(checkedCheckboxesArraySize) {
                    case 1:
                        selected_languages_msg = String.format("Mi lenguaje favorito es: %s.", checkedCheckboxesArray.get(0).getText().toString());
                        break;
                    default:
                        selected_languages_msg = "Mis lenguajes favoritos son: ";
                        for (int i = 0; i < checkedCheckboxesArray.size(); i++) {

                            if(checkedCheckboxesArray.get(i).isChecked()) {
                                if(i < checkedCheckboxesArraySize) {
                                    checkedCheckboxesArraySize--;
                                    selected_languages_msg = selected_languages_msg + checkedCheckboxesArray.get(i).getText().toString() + ", ";
                                }
                                else {
                                    selected_languages_msg = selected_languages_msg + checkedCheckboxesArray.get(i).getText().toString() + ".";
                                }
                            }
                        }
                        break;
                }
                break;
            default:
                programming_preference_msg = "No me gusta programar.";
        }




//        Add data to intent
        intent.putExtra("names_msg", first_name.getText().toString() + " " + last_name.getText().toString());
        intent.putExtra("gender_msg", gender_msg);
        intent.putExtra("date_of_birth_msg", dateButton.getHint().toString());
        intent.putExtra("programming_preference_msg", programming_preference_msg);
        intent.putExtra("selected_languages_msg", selected_languages_msg);

//        start activity with intent
        startActivity(intent);
    }

    public void clearInputs(View view) {
        EditText nameInput = findViewById(R.id.name_input);
        EditText lastNameInput = findViewById(R.id.last_name_input);

//        Create array of the checkboxes
        ArrayList<CheckBox> checkboxIdArray = new ArrayList<CheckBox>();
        checkboxIdArray.add(findViewById(R.id.lenguage_java));
        checkboxIdArray.add(findViewById(R.id.lenguage_js));
        checkboxIdArray.add(findViewById(R.id.lenguage_c));
        checkboxIdArray.add(findViewById(R.id.lenguage_python));
        checkboxIdArray.add(findViewById(R.id.lenguage_go_lang));
        checkboxIdArray.add(findViewById(R.id.lenguage_c_sharp));

//        name and last name text as empty
        nameInput.setText("");
        lastNameInput.setText("");
//        Gender spinner index set to 0
        genderSpinner.setSelection(0);

//        radioGroup set at yes
        RadioButton programming_preference_radio_button_yes = findViewById(R.id.programming_preference_radio_button_yes);
        programming_preference_radio_button_group.check(programming_preference_radio_button_yes.getId());

//        Date set at today
        dateButton.setHint(getTodaysDate());

//        Iterate through checkboxes to set them as not checked
        for (CheckBox languageCheckbox : checkboxIdArray) {
            languageCheckbox.setChecked(false);
        }

    }

}