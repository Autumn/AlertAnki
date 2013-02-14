package com.galaxy.ankialert;

import android.app.Activity;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.View;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.preference.PreferenceManager;
import android.widget.Toast;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        TextView startHour = (TextView) findViewById(R.id.start_time_hour);
        TextView startMinute = (TextView) findViewById(R.id.start_time_minute);
        EditText intervalMin = (EditText) findViewById(R.id.interval_start_edit);
        EditText intervalMax = (EditText) findViewById(R.id.interval_end_edit);
        if (prefs.getInt("INTERVAL_MIN", -1) != -1) {
          intervalMin.setText(Integer.toString(prefs.getInt("INTERVAL_MIN", -1)));
          intervalMax.setText(Integer.toString(prefs.getInt("INTERVAL_MAX", -1)));
        }
        if (prefs.getInt("START_HOUR", -1) != -1) {
          startHour.setText(Integer.toString(prefs.getInt("START_HOUR", -1)));
          startMinute.setText(Integer.toString(prefs.getInt("START_MINUTE", -1)));
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void saveSettings(View v) {
      EditText intervalMinView = (EditText) findViewById(R.id.interval_start_edit);
      EditText intervalMaxView = (EditText) findViewById(R.id.interval_end_edit);
      Integer intervalMin = 0, intervalMax = 0;

      try {
        intervalMin = Integer.parseInt(intervalMinView.getText().toString());
        intervalMax = Integer.parseInt(intervalMaxView.getText().toString());
      } catch (Exception e) {
        CharSequence text = "Invalid interval.";
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
        return;
      }

      TextView startHour = (TextView) findViewById(R.id.start_time_hour);
      TextView startMinute = (TextView) findViewById(R.id.start_time_minute);

      // if startHour and startMinute contain their default text "--", parseInt will fail and exit.
      // if it's not default text, a time has correctly been set and it is safe to proceed.
      try {
        Integer i = Integer.parseInt(startHour.getText().toString());
        i = Integer.parseInt(startMinute.getText().toString());
      } catch (Exception e) {
        CharSequence text = "Invalid start time.";
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
        return;
      }

      if (intervalMin < intervalMax) {
          Context ctxt = this;
          SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctxt.getApplicationContext());
          SharedPreferences.Editor editor = prefs.edit();
          editor.putInt("INTERVAL_MIN", intervalMin);
          editor.putInt("INTERVAL_MAX", intervalMax);
          while (!editor.commit());

    CharSequence text = "Successfully saved settings. Interval of " + prefs.getInt("INTERVAL_MIN", 0) + " to " + prefs.getInt("INTERVAL_MAX", 0) + " minutes until complete.";
    int duration = Toast.LENGTH_SHORT;

    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
    toast.show();


      } else {
        CharSequence text = "Invalid interval.";
        int duration = Toast.LENGTH_SHORT;
  
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
      }
    }
}


