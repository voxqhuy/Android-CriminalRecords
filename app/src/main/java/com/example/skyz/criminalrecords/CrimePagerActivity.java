package com.example.skyz.criminalrecords;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID = "com.example.skyz.criminalrecords.crime_id";

    private ViewPager mViewPager;
    private Button mMoveToFirstBtn;
    private Button mMoveToLastBtn;
    private List<Crime> mCrimes;

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        // The views
        mViewPager = findViewById(R.id.crime_view_pager);
        mMoveToFirstBtn = findViewById(R.id.move_to_first_btn);
        mMoveToLastBtn = findViewById(R.id.move_to_last_btn);

        CrimeLab crimeLab = CrimeLab.get(this);
        mCrimes = crimeLab.getCrimes();
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });
        mViewPager.setCurrentItem(mCrimes.indexOf(crimeLab.getCrime(crimeId)));

        mMoveToFirstBtn.setOnClickListener((View v) -> {
                mViewPager.setCurrentItem(0);
        });

        mMoveToLastBtn.setOnClickListener((View v) -> {
            mViewPager.setCurrentItem(mCrimes.size() - 1);
        });
    }
}
