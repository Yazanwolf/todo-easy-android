package com.android.todoeasy;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CreateTodoActivity extends AppCompatActivity {

    private TextView todoTaskTitle;
    private EditText todoTaskEditText;
    private TextView todoDescriptionTitle;
    private EditText todoDescriptionEditText;
    private EditText calendarEditText;
    private ImageButton calendarButton;
    private EditText timeEditText;
    private ImageButton timeButton;
    private Button createTodoButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);
        initObjects();
    }

    private void initObjects() {
        todoTaskTitle = findViewById(R.id.todoTaskTitle);
        todoTaskEditText = findViewById(R.id.todoTaskEditText);

        todoDescriptionTitle = findViewById(R.id.todoDescriptionTitle);
        todoDescriptionEditText = findViewById(R.id.todoDescriptionEditText);

        calendarEditText = findViewById(R.id.calendarEditText);
        calendarButton = findViewById(R.id.calendarButton);
        calendarButton.setImageResource(R.mipmap.ic_calendar);

        timeEditText = findViewById(R.id.timeEditText);
        timeButton = findViewById(R.id.timeButton);
        timeButton.setImageResource(R.mipmap.ic_clock);

        createTodoButton = findViewById(R.id.createTodoButton);
    }
}