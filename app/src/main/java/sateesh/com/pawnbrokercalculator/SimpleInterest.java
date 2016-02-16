package sateesh.com.pawnbrokercalculator;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sateesh on 16-02-2016.
 */
public class SimpleInterest extends Fragment {

    TextView startDate, endDate, principal, interest, isMonthExclue, minDays;
    String StartDateValue;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static SimpleInterest newInstance(int sectionNumber) {
        SimpleInterest fragment = new SimpleInterest();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        String StartDateValue = MonthWiseCalculations.getStartDate();

        ArrayList<String> values = MonthWiseCalculations.getValues();

        View v = inflater.inflate(R.layout.simple_interest, container, false);

        startDate = (TextView) v.findViewById(R.id.startDate_value);
        endDate = (TextView) v.findViewById(R.id.endtDate_value);
        principal = (TextView) v.findViewById(R.id.principal_value);
        interest = (TextView) v.findViewById(R.id.interest_value);
        isMonthExclue = (TextView) v.findViewById(R.id.exclude_month_value);
        minDays = (TextView) v.findViewById(R.id.days_value);

        startDate.setText(values.get(0).toString());
        endDate.setText(values.get(1).toString());
        principal.setText(values.get(2).toString());
        interest.setText(values.get(3).toString());

        if ((values.get(4).toString()).equals("true")) {
            isMonthExclue.setText("Excluded");
        } else {
            isMonthExclue.setText("included");
        }
        String minDaysText = "More than " + values.get(5).toString() + " days considered as Month";
        minDays.setText(minDaysText);

        return v;
    }

//    public void getValues(String Date){
//        StartDateValue = Date;
//        Log.v("Sateesh ", "*** SimpleInterest StartDateValue is: " + StartDateValue);
//
//
//    }
}