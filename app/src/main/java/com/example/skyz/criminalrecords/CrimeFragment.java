package com.example.skyz.criminalrecords;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Vo Huy on 3/13/2018.
 */

public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    private EditText mTitleField;
    private Button mDateBtn;
    private Button mTimeBtn;
    private CheckBox mSolvedCheckBox;
    private Crime mCrime;

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment crimeFragment  = new CrimeFragment();
        crimeFragment.setArguments(args);
        return crimeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        // View references
        mTitleField = v.findViewById(R.id.crime_title);
        mDateBtn = v.findViewById(R.id.crime_date);
        mTimeBtn = v.findViewById(R.id.crime_time);
        mSolvedCheckBox = v.findViewById(R.id.crime_solved);

        // Title text watcher
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // display the Crime's info
        mTitleField.setText(mCrime.getTitle());
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        // Set the date to the current date
        updateDate();
        // Date Picker
        mDateBtn.setOnClickListener((View view) -> {
            FragmentManager fm = getFragmentManager();
            DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
            dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
            dialog.show(fm, DIALOG_DATE);
        });
        // Time Picker
        mTimeBtn.setOnClickListener((View view) -> {
            FragmentManager fm = getFragmentManager();
            TimePickerFragment timePicker = TimePickerFragment.newInstance(mCrime.getDate());
            timePicker.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
            timePicker.show(fm, DIALOG_TIME);
        });
        // Crime solved check listener
        mSolvedCheckBox.setOnCheckedChangeListener((CompoundButton buttonView,
                                                    boolean isChecked) -> {
                mCrime.setSolved(isChecked);
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            // get date from Intent Extras
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            // set a new date to the crime
            mCrime.setDate(date);
            updateDate();
        } else if (requestCode == REQUEST_TIME) {
            // get time from Intent Extras
            Date time = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            // set a new time to the crime
            mCrime.setTime(time);
            updateDate();
        }
    }

    private void updateDate() {
        mDateBtn.setText(Utils.formatDate(mCrime.getDate()));
        mTimeBtn.setText(Utils.formatTime(mCrime.getTime()));
    }
}
