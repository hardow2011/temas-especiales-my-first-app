package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
import android.widget.Toast;

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

    private ArrayList<CheckBox> languageCheckboxes = new ArrayList<CheckBox>();

    private ColorStateList defaultHintGrayColor;
    private ColorStateList defaultBlackTextColor;
    private TextView programming_preference_radio_button_label;


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

        languageCheckboxes.add(findViewById(R.id.lenguage_java));
        languageCheckboxes.add(findViewById(R.id.lenguage_js));
        languageCheckboxes.add(findViewById(R.id.lenguage_c));
        languageCheckboxes.add(findViewById(R.id.lenguage_python));
        languageCheckboxes.add(findViewById(R.id.lenguage_go_lang));
        languageCheckboxes.add(findViewById(R.id.lenguage_c_sharp));

//        Overwrite the onCheckedChanged for the programming_preference_radio_button_group, so that the language checkboxes get enabled and disabled accordingly.
        programming_preference_radio_button_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.programming_preference_radio_button_yes) {
                    enableLanguageCheckboxes();
                } else {
                    disableLanguageCheckboxes();
                }
            }
        });

        EditText first_name = findViewById(R.id.name_input);
        EditText last_name = findViewById(R.id.last_name_input);

//        Save and reset default gray hint color
        defaultHintGrayColor = first_name.getHintTextColors();

//        Save and reset default black text color
        defaultBlackTextColor = first_name.getTextColors();

        programming_preference_radio_button_label = findViewById(R.id.programming_preference_radio_button_label);

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

    private void disableLanguageCheckboxes() {
        for (CheckBox languageCheckbox : languageCheckboxes) {
            languageCheckbox.setEnabled(false);
        }
    }

    private void enableLanguageCheckboxes() {
        for (CheckBox languageCheckbox : languageCheckboxes) {
            languageCheckbox.setEnabled(true);
        }
    }

    public void setDefaulColor(EditText editText) {
        editText.setHintTextColor(defaultHintGrayColor);
        editText.setBackgroundTintList(defaultHintGrayColor);
    }

    public void setDefaulColor(TextView textView) {
        textView.setHintTextColor(defaultHintGrayColor);
        textView.setTextColor(defaultHintGrayColor);
        textView.setBackgroundTintList(defaultHintGrayColor);
    }

    public void setRedErrorColor(EditText editText) {
        editText.setHintTextColor(Color.RED);
        editText.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
    }

    public void setRedErrorColor(TextView textView) {
        textView.setHintTextColor(Color.RED);
        textView.setTextColor(Color.RED);
        textView.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
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
        String warning_notification = "";

        setDefaulColor(first_name);
        setDefaulColor(last_name);
        setDefaulColor(programming_preference_radio_button_label);

//        .trim() to remove trailing spaces.
        if (first_name.getText().toString().trim().equals("") & last_name.getText().toString().trim().equals("")) {
            warning_notification = "El nombre y apellido no pueden estar vacíos.";
            Toast.makeText(MainActivity.this, warning_notification, Toast.LENGTH_SHORT).show();
            setRedErrorColor(first_name);
            setRedErrorColor(last_name);
            return;
        } else if (first_name.getText().toString().trim().equals("")) {
            warning_notification = "El nombre no puede estar vacío.";
            setRedErrorColor(first_name);
            Toast.makeText(MainActivity.this, warning_notification, Toast.LENGTH_SHORT).show();
            return;
        } else if (last_name.getText().toString().trim().equals("")) {
            warning_notification = "El apellido no puede estar vacío.";
            setRedErrorColor(last_name);
            Toast.makeText(MainActivity.this, warning_notification, Toast.LENGTH_SHORT).show();
            return;
        }

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

        int checkedCheckboxesArraySize = 0;

//        Remove the non-checked-checkboxes from the array
        for (CheckBox languageCheckbox : languageCheckboxes) {
            if (languageCheckbox.isChecked()) {
                checkedCheckboxesArraySize++;
            }
        }

        switch (programming_preference.getId()) {
            case R.id.programming_preference_radio_button_yes:
                programming_preference_msg = "Me gusta programar.";

                switch (checkedCheckboxesArraySize) {
                    case 0:
                        setRedErrorColor(programming_preference_radio_button_label);
                        warning_notification = "Seleccionar al menos un lenguaje. O seleccionar \"No me gusta programar\".";
                        Toast.makeText(MainActivity.this, warning_notification, Toast.LENGTH_LONG).show();
                        return;
                    default:
                        if (checkedCheckboxesArraySize == 1) {
                            selected_languages_msg = "Mi lenguaje favorito es: ";
                        }
                        else {
                            selected_languages_msg = "Mis lenguajes favoritos son: ";
                        }

                        for (CheckBox languageCheckbox : languageCheckboxes) {
                            languageCheckbox.setEnabled(true);
                        }
                        for (int i = 0; i < languageCheckboxes.size(); i++) {

                            if (languageCheckboxes.get(i).isChecked()) {
                                if (checkedCheckboxesArraySize > 1) {
                                    selected_languages_msg = selected_languages_msg + languageCheckboxes.get(i).getText().toString() + ", ";
                                } else {
                                    selected_languages_msg = selected_languages_msg + languageCheckboxes.get(i).getText().toString() + ".";
                                }
                                checkedCheckboxesArraySize--;
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