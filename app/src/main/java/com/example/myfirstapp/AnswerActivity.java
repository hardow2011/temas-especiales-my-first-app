package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

//        Send intent named MSG
        Intent intent = getIntent();
        String names_msg = intent.getStringExtra("names_msg");
        String gender_msg = intent.getStringExtra("gender_msg");
        String date_of_birth_msg = intent.getStringExtra("date_of_birth_msg");
        String programming_preference_msg = intent.getStringExtra("programming_preference_msg");
        String selected_languages_msg = intent.getStringExtra("selected_languages_msg");

//        Find the view and set it text to the intent received
        TextView names_view = findViewById(R.id.names_received_text);
        TextView gender_birth_date_view = findViewById(R.id.gender_birth_date_received_text);
        TextView programming_preference_view = findViewById(R.id.programming_preference_received_text);


        String names = String.format("Hola! Mi nombre es %s.", names_msg);
        String gender_birth_date = String.format("Soy %s, y nací en fecha %s.", gender_msg, date_of_birth_msg);
        String programming_preference = String.format("%s %s", programming_preference_msg, selected_languages_msg);


        names_view.setText(names);
        gender_birth_date_view.setText(gender_birth_date);
        programming_preference_view.setText(programming_preference);

    }
}