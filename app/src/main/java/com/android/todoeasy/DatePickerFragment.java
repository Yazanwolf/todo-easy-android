package com.android.todoeasy;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;
import java.util.Calendar;

import static com.android.todoeasy.MainActivity.DATE_HAS_BEEN_SET;
import static com.android.todoeasy.MainActivity.GIVEN_DATE;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Context context;

    public DatePickerFragment(Context context) {
        this.context = context;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        // Create a new instance of TimePickerDialog and return it
        return new DatePickerDialog(context, this, year, month, day);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // todo try validating the given here as well
        String givenLocalDate = LocalDate.of(year, month, dayOfMonth).toString();
        Intent intent = new Intent(DATE_HAS_BEEN_SET);
        intent.putExtra(GIVEN_DATE, givenLocalDate);
        context.sendBroadcast(intent);
    }

}
