package com.galaxy.ankialert;

import android.app.DialogFragment;
import java.util.Calendar;
import android.widget.TimePicker;
import android.os.Bundle;
import android.app.TimePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.widget.Toast;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.view.View;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the current time as the default values for the picker
    
    final Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);

    // Create a new instance of TimePickerDialog and return it
    
    return new TimePickerDialog(getActivity(), this, hour, minute, true); //DateFormat.is24HourFormat(getActivity()));
  }

  public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    // Do something with the time chosen by the user
    
    // commit to shared preferences
    
    Context ctxt = getActivity();
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctxt.getApplicationContext());
    SharedPreferences.Editor editor = prefs.edit();
    editor.putInt("START_HOUR", hourOfDay);
    editor.putInt("START_MINUTE", minute);

    while (!editor.commit());

    TextView hourLabel = (TextView) getActivity().findViewById(R.id.start_time_hour);
    TextView minuteLabel = (TextView) getActivity().findViewById(R.id.start_time_minute);
    hourLabel.setText(Integer.toString(prefs.getInt("START_HOUR", -1)));
    minuteLabel.setText(Integer.toString(prefs.getInt("START_MINUTE", -1)));

    CharSequence text = "Set start time to " + Integer.toString(hourOfDay) + ":" + Integer.toString(minute);
    int duration = Toast.LENGTH_SHORT;

    Toast toast = Toast.makeText(getActivity(), text, duration);
    toast.show();
  }
}
