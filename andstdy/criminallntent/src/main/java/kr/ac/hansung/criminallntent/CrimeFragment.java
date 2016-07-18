package kr.ac.hansung.criminallntent;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
 * Created by Owner on 2016-07-12.
 */
public class CrimeFragment extends Fragment{
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);

        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
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

       /*
       String time = android.text.format.DateFormat.format("yy/MM/dd h:mmaa", System.currentTimeMillis()).toString();
       For the month of September:  For 7 minutes past the hour:
        M -> 9                      m -> 7
        MM -> 09                    mm -> 07
        MMM -> Sep                  mmm -> 007
        MMMM -> September           mmmm -> 0007

       Examples for April 6, 1970 at 3:23am:
        "MM/dd/yy h:mmaa" -> "04/06/70 3:23am"
        "MMM dd, yyyy h:mmaa" -> "Apr 6, 1970 3:23am"
        "MMMM dd, yyyy h:mmaa" -> "April 6, 1970 3:23am"
        "E, MMMM dd, yyyy h:mmaa" -> "Mon, April 6, 1970 3:23am&
        "EEEE, MMMM dd, yyyy h:mmaa" -> "Monday, April 6, 1970 3:23am"
        "'Noteworthy day: 'M/d/yy" -> "Noteworthy day: 4/6/70"
       */
        mDateButton = (Button)v.findViewById(R.id.crime_date);
        String time = android.text.format.DateFormat.format("EEEE, MMM dd, yyyy",mCrime.getDate().getTime()).toString();
        updateDate(time);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override // 버튼이 눌리면 해결 속성 값을 바꿔준다.
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_DATE){
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate(mCrime.getDate().toString());
        }
    }

    private void updateDate(String text) {
        mDateButton.setText(text);
    }
}
