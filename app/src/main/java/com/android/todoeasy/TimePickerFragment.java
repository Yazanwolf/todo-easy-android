package com.android.todoeasy;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.time.LocalTime;
import java.util.Calendar;

import static com.android.todoeasy.MainActivity.GIVEN_TIME;
import static com.android.todoeasy.MainActivity.TIME_HAS_BEEN_SET;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private Context context;

    public TimePickerFragment(Context context) {
        this.context = context;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String givenLocalDateAsString = String.valueOf(LocalTime.of(hourOfDay, minute));
        Intent intent = new Intent(TIME_HAS_BEEN_SET);
        intent.putExtra(GIVEN_TIME, givenLocalDateAsString);
        context.sendBroadcast(intent);
    }
}
