package com.example.skyz.criminalrecords;

import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Vo Huy on 3/17/2018.
 */

public class CrimeLab {
    // s-prefix: static variable
    private static CrimeLab sCrimeLab;

    private Map<UUID, Crime> mCrimes;

    // A Singleton: A private constructor and a get() method
    public static CrimeLab get(Context context) {
        // singleton: can have only one object at a time
        // after the first time, a new variable will also points at the first instance created
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new LinkedHashMap<>();
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);    // Every other one
            crime.setRequiresPolice(i % 2 == 0);
            mCrimes.put(crime.getId(), crime);
        }
    }

    public List<Crime> getCrimes() {
        return new ArrayList<>(mCrimes.values());   // return an array list of crimes
    }

    public Crime getCrime(UUID id) {
        return mCrimes.get(id);
    }
}
