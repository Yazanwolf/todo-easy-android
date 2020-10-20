package com.android.todoeasy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.android.todoeasy.TodoFragment.NEW_TODO_CREATED;
import static com.android.todoeasy.TodoFragment.TODO_PARCEL;

public class CreateTodoActivity extends AppCompatActivity {

    public static String DATE_HAS_BEEN_SET = "com.android.todoEasy.DATE_HAS_BEEN_SET";
    public static String TIME_HAS_BEEN_SET = "com.android.todoEasy.TIME_HAS_BEEN_SET";
    public static String GIVEN_DATE = "com.android.todoEasy.GIVEN_DATE";
    public static String GIVEN_TIME = "com.android.todoEasy.GIVEN_TIME";

    private String chosenDate;
    private String chosenTime;

    private EditText todoTaskEditText;
    private EditText todoDescriptionEditText;
    private EditText dateEditText;
    private EditText timeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);
        registerReceivers();
        initObjects();
    }

    private void registerReceivers() {
        DateTimeReceiver dateTimeReceiver = new DateTimeReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DATE_HAS_BEEN_SET);
        intentFilter.addAction(TIME_HAS_BEEN_SET);
        registerReceiver(dateTimeReceiver, intentFilter);
    }

    private void initObjects() {
        todoTaskEditText = findViewById(R.id.todoTaskEditText);
        todoDescriptionEditText = findViewById(R.id.todoDescriptionEditText);

        dateEditText = findViewById(R.id.dateEditText);
        ImageButton calendarButton = findViewById(R.id.dateButton);
        calendarButton.setImageResource(R.mipmap.ic_calendar);
        calendarButton.setOnClickListener(v -> {
            // todo open calendar fragment
            DatePickerFragment datePickerFragment = new DatePickerFragment(this);
            FragmentManager manager = getSupportFragmentManager();
            datePickerFragment.show(manager, "datePicker");
        });

        timeEditText = findViewById(R.id.timeEditText);
        ImageButton timeButton = findViewById(R.id.timeButton);
        timeButton.setImageResource(R.mipmap.ic_clock);
        timeButton.setOnClickListener(v -> {
            // todo open time button
            TimePickerFragment timePickerFragment = new TimePickerFragment(this);
            FragmentManager manager = getSupportFragmentManager();
            timePickerFragment.show(manager, "timePicker");
        });

        Button createTodoButton = findViewById(R.id.createTodoButton);
        createTodoButton.setOnClickListener(v -> onCreateTodoClicked());

        Button cancelTodoButton = findViewById(R.id.cancelTodoButton);
        cancelTodoButton.setOnClickListener(v -> finish());
    }

    private void onCreateTodoClicked() {
        if (TextUtils.isEmpty(todoTaskEditText.getText())) {
            Toast.makeText(this, R.string.todo_task_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        String date = dateEditText.getText().toString();
        String time = timeEditText.getText().toString();
        try {
            LocalDate.from(LocalDate.parse(date));
            LocalTime.from(LocalTime.parse(time));
        } catch (Exception e) {
            Toast.makeText(this, R.string.given_time_date_not_valid, Toast.LENGTH_SHORT)
                    .show();

            e.printStackTrace();
            return;
        }

        Todo newTodo = Todo.builder()
                .description(todoDescriptionEditText.getText().toString())
                .expiryDate(date)
                .expiryTime(time)
                .finished(false)
                .name(todoTaskEditText.getText().toString())
                .build();

        Intent intent = new Intent(NEW_TODO_CREATED);
        intent.putExtra(TODO_PARCEL, newTodo);
        sendBroadcast(intent);
        finish();
    }

    private void refreshInputFields() {
        if (chosenDate != null) {
            dateEditText.setText(chosenDate);
        }
        if (chosenTime != null) {
            timeEditText.setText(chosenTime);
        }
    }

    private class DateTimeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction() == null || intent.getAction().equals("")) {
                return;
            }

            if (intent.getAction().equals(DATE_HAS_BEEN_SET)) {
                chosenDate = intent.getExtras().get(GIVEN_DATE).toString();
                refreshInputFields();
                return;
            }

            if (intent.getAction().equals(TIME_HAS_BEEN_SET)) {
                chosenTime = intent.getExtras().get(GIVEN_TIME).toString();
                refreshInputFields();
            }

        }
    }

}