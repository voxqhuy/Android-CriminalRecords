package com.example.skyz.criminalrecords;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vo Huy on 4/15/2018.
 */
public class TimePickerFragment extends DialogFragment {

    private static final String ARG_TIME = "time";
    public static final String EXTRA_TIME = "com.example.skyz.criminalrecords.extra_time";
    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(Date time) {

        Bundle args = new Bundle();
        // for CrimeFragment to send crime's time along before starting TimePickerFragment
        args.putSerializable(ARG_TIME, time);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);
        // get the time from CrimeFragment
        Date date = (Date) getArguments().getSerializable(ARG_TIME);
        Calendar calendar = Calendar.getInstance();
        // convert the time to Calendar object
        calendar.setTime(date);
        // get hour, minute from Calendar object
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        mTimePicker = view.findViewById(R.id.dialog_time_picker);
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, (DialogInterface dialogInterface,
                                                         int i) -> {
                    int hour1 = mTimePicker.getCurrentHour();
                    int minute1 = mTimePicker.getCurrentMinute();
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(Calendar.HOUR_OF_DAY, hour1);
                    calendar1.set(Calendar.MINUTE, minute1);
                    Date time = calendar1.getTime();
                    sendResult(Activity.RESULT_OK, time);
                })
                .create();

    }

    private void sendResult(int resultCode, Date time) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        // add the time to the bundle and send it to crime fragment
        intent.putExtra(EXTRA_TIME, time);
        // CrimeFragment was set as a target of TimePickerFragment before it was called
        // getTargetFragment() returns CrimeFragment
        // CrimeFragment set request code before starting TimePickerFragment
        // getTargetRequestCode() returns request code for Time Picker
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
