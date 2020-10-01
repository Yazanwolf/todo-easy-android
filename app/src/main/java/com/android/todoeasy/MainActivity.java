package com.android.todoeasy;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    public static String NEW_TODO_HAS_BEEN_CREATED = "com.android.todoEasy.NEW_TODO_HAS_BEEN_CREATED";
    public static String DATE_HAS_BEEN_SET = "com.android.todoEasy.DATE_HAS_BEEN_SET";
    public static String TIME_HAS_BEEN_SET = "com.android.todoEasy.TIME_HAS_BEEN_SET";
    public static String GIVEN_DATE = "com.android.todoEasy.GIVEN_DATE";
    public static String GIVEN_TIME = "com.android.todoEasy.GIVEN_TIME";
    public static String NEW_TODO = "com.android.todoEasy.NEW_TODO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initObjects();
    }

    private void initObjects() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view  -> {
            Intent intent = new Intent(MainActivity.this, CreateTodoActivity.class);
            startActivity(intent);
        });
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

}