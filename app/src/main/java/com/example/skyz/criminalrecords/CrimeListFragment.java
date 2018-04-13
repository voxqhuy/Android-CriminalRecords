package com.example.skyz.criminalrecords;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Skyz on 3/17/2018.
 */

public class CrimeListFragment extends Fragment{
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private class CrimeHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mCrimeSolvedView;
        private Button mContactPoliceBtn;

        private Crime mCrime;

        private String datePattern = "EE, MM dd, yyyy";

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent, int viewtype) {
            super(inflater.inflate(viewtype, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = itemView.findViewById(R.id.crime_title);
            mDateTextView = itemView.findViewById(R.id.crime_date);
            mCrimeSolvedView = itemView.findViewById(R.id.crime_solved);
//
//            if (viewtype == R.layout.list_item_serious_crime) {
//                mContactPoliceBtn = itemView.findViewById(R.id.contact_police_button);
//                mContactPoliceBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(getActivity(), "Calling Police for "
//                                        + mCrime.getTitle(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
        }

        // bind a new Crime each time it should be displayed in CrimeHolder
        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());

            SimpleDateFormat format = new SimpleDateFormat(datePattern, Locale.US);
            String dateString = format.format(mCrime.getDate());
            mDateTextView.setText(dateString);
            mCrimeSolvedView.setVisibility(mCrime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            // show the crime
            Intent crimeIntent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(crimeIntent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new CrimeHolder(layoutInflater, parent, viewType);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            // bind a given CrimeHolder to a particular crime each time the RecyclerView requests
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        @Override
        public int getItemViewType(int position) {
            // return serious crime View for serious ones, otherwise return normal crime View
//            return mCrimes.get(position).isRequiresPolice() ? R.layout.list_item_serious_crime
//                    : R.layout.list_item_crime;
            return R.layout.list_item_crime;
        }
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }
}
