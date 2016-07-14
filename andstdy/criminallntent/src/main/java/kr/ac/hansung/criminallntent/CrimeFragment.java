package kr.ac.hansung.criminallntent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * Created by Owner on 2016-07-12.
 */
public class CrimeFragment extends Fragment{
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText)v.findViewById(R.id.crime_title);
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
        mDateButton.setText(time);//버튼에 날짜를 넣어준다.
        mDateButton.setEnabled(false);

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override // 버튼이 눌리면 해결 속성 값을 바꿔준다.
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }
}
