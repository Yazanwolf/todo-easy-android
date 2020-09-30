package com.android.todoeasy;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.android.todoeasy.parcelable.TodoParcelable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static String NEW_TODO_HAS_BEEN_CREATED = "com.android.todoEasy.NEW_TODO_HAS_BEEN_CREATED";
    public static String DATE_HAS_BEEN_SET = "com.android.todoEasy.DATE_HAS_BEEN_SET";
    public static String TIME_HAS_BEEN_SET = "com.android.todoEasy.TIME_HAS_BEEN_SET";
    public static String GIVEN_DATE = "com.android.todoEasy.GIVEN_DATE";
    public static String GIVEN_TIME = "com.android.todoEasy.GIVEN_TIME";
    public static String NEW_TODO = "com.android.todoEasy.NEW_TODO";
    private String newTodoTask;
    private String date;
    private String time;
    private LocalDateTime expiryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initObjects();
        initReceivers();
    }

    private void initReceivers() {
        DateTimeReceiver dateTimeReceiver = new DateTimeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DATE_HAS_BEEN_SET);
        filter.addAction(TIME_HAS_BEEN_SET);
        this.registerReceiver(dateTimeReceiver, filter);
    }

    private void initObjects() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startNewTodoDialog());
    }

    /** This method starts a task input dialog
     * Triggers a DatePickerFragment
     * Which in it's turn - After giving a valid date- triggers a TimePickerFragment
     * Todo: look for a better solution
     * */
    private void startNewTodoDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(R.string.todo_task);
        dialogBuilder.setMessage(R.string.enter_todo_name);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        dialogBuilder.setView(input);

        dialogBuilder.setPositiveButton(R.string.ok, (dialog, which) -> {
            newTodoTask = input.getText().toString();
            DialogFragment datePickerFragment = new DatePickerFragment(this);
            FragmentManager manager = getSupportFragmentManager();
            datePickerFragment.show(manager, "datePicker");
        });

        dialogBuilder.setNegativeButton(R.string.cancel, (dialog, which) -> clearNewTodoInputs());


        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        Log.d("lets see", "Here we go");
    }

    private void clearNewTodoInputs() {
        newTodoTask = "";
        date = "";
        time = "";
        expiryDate = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class DateTimeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (Objects.requireNonNull(intent.getAction()).contains(DATE_HAS_BEEN_SET)) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    date = Objects.requireNonNull(extras.getCharSequence(GIVEN_DATE)).toString();
                } else {
                    return;
                }
                Toast.makeText(context, date, Toast.LENGTH_LONG).show();
                DialogFragment timePickerFragment = new TimePickerFragment(context);
                FragmentManager fragmentManager = getSupportFragmentManager();
                timePickerFragment.show(fragmentManager, "timePicker");
            }

            if (intent.getAction().contains(TIME_HAS_BEEN_SET)) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    time = Objects.requireNonNull(extras.getCharSequence(GIVEN_TIME)).toString();
                    if (!isGivenDateValid(date, time)) {
                        Toast.makeText(context, R.string.invalid_date, Toast.LENGTH_LONG).show();
                        return;
                    }

                    Intent todoCreatedIntent = new Intent(NEW_TODO_HAS_BEEN_CREATED);
                    todoCreatedIntent.putExtra(NEW_TODO, new TodoParcelable(newTodoTask, date, time));
                    clearNewTodoInputs();
                    context.sendBroadcast(todoCreatedIntent);
                }
            }

        }

        //todo find out why this doesn't work
        private boolean isGivenDateValid(String date, String time) {
            LocalDate localDate = LocalDate.parse(date);
            LocalTime localTime = LocalTime.parse(time);

            ZonedDateTime givenLocalDateTime = ZonedDateTime.of(localDate, localTime, ZoneId.systemDefault());
            return givenLocalDateTime.toLocalTime().isAfter(ZonedDateTime.now(ZoneId.systemDefault()).toLocalTime());
        }
    }

}