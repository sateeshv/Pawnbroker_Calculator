package sateesh.com.pawnbrokercalculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Sateesh on 16-02-2016.
 */
public class CompoundInterest extends Fragment {

    public TextView startDate, endDate, principal, interest, isMonthExclude, minDays;
    TextView errorMessage, Diff, Diff1, Diff2, resultPrincipal, interestPerMonth, totalInterest, totalAmount, resultPrincipalValue;
    String[] Month_Names = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};
    int Start_Date_Years, Start_Date_Months, Start_Date_Days, End_Date_Years, End_Date_Months, End_Date_Days;

    boolean MonthValue_boolean;
    int minDays_Number;

    Validations validations;
    ArrayList<String> values;
    ArrayList<Integer> dayValues;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static CompoundInterest newInstance(int sectionNumber) {
        CompoundInterest fragment = new CompoundInterest();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.compound_interest, container, false);
        values = MonthWiseCalculations.getValues();
        dayValues = MonthWiseCalculations.getDayValues();

        startDate = (TextView) v.findViewById(R.id.ci_startDate_value);
        endDate = (TextView) v.findViewById(R.id.ci_endtDate_value);
        principal = (TextView) v.findViewById(R.id.ci_principal_value);
        interest = (TextView) v.findViewById(R.id.ci_interest_value);
        isMonthExclude = (TextView) v.findViewById(R.id.ci_exclude_month_value);
        minDays = (TextView) v.findViewById(R.id.ci_days_value);
        Diff = (TextView) v.findViewById(R.id.ci_diff);
        Diff1 = (TextView) v.findViewById(R.id.ci_diff1);
        Diff2 = (TextView) v.findViewById(R.id.ci_diff2);

        resultPrincipalValue = (TextView) v.findViewById(R.id.ci_result_Principal_Amount);
//        interestPerMonth = (TextView) v.findViewById(R.id.ci_result_interest_month);
        totalInterest = (TextView) v.findViewById(R.id.ci_result_total_interest);
        totalAmount = (TextView) v.findViewById(R.id.ci_result_total_amount);

        if (values != null && dayValues != null) {
            startDate.setText(values.get(0).toString());
            endDate.setText(values.get(1).toString());
            principal.setText(values.get(2).toString());
            interest.setText(values.get(3).toString());

            MonthValue_boolean = Boolean.parseBoolean(values.get(4).toString());

            if ((values.get(4).toString()).equals("true")) {
                isMonthExclude.setText("Excluded");
            } else {
                isMonthExclude.setText("included");
            }
            minDays_Number = Integer.parseInt(values.get(5).toString());
            String minDaysText = "More than " + minDays_Number + " days considered as Month";
            minDays.setText(minDaysText);
            validations = new Validations();


        ci_calculateDuration();
        ci_calculateInterest();
    }
        return v;
    }

    public void ci_calculateDuration() {

//        isExclude = (CheckBox) findViewById(R.id.IsExcludeMonth);
//        boolean isExcludeChecked = isExclude.isChecked();

        boolean isExcludeChecked = Boolean.getBoolean(values.get(4).toString());

//        minDays = (EditText) findViewById(R.id.minDays);
//        String days = minDays.getText().toString();
//        int daysCompleted = Integer.parseInt(days);
        int daysCompleted = Integer.parseInt(values.get(5).toString());

        Start_Date_Years = dayValues.get(0).intValue();
        Start_Date_Months = dayValues.get(1).intValue();
        Start_Date_Days = dayValues.get(2).intValue();
        End_Date_Years = dayValues.get(3).intValue();
        End_Date_Months = dayValues.get(4).intValue();
        End_Date_Days = dayValues.get(5).intValue();

        Log.v("Sateesh", "*** Start Dates are " + Start_Date_Years + "-" + Start_Date_Months + "-" + Start_Date_Days);
        Log.v("Sateesh", "*** End Dates are " + End_Date_Years + "-" + End_Date_Months + "-" + End_Date_Days);


        validations.extractYear(Start_Date_Years, End_Date_Years);
        validations.extractMonth(Start_Date_Years, Start_Date_Months, Start_Date_Days, End_Date_Years, End_Date_Months, End_Date_Days);
        validations.extractDay(Start_Date_Years, Start_Date_Months, Start_Date_Days, End_Date_Years, End_Date_Months, End_Date_Days);

//        Diff = (TextView) findViewById(R.id.diff);
        Diff.setText(validations.Years + " Y " + (validations.Months) + " M " + validations.Days + " D ");


        validations.isDaysCrossed(minDays_Number);

//        Diff1 = (TextView) findViewById(R.id.diff1);
        Diff1.setText(validations.Years + " Y " + (validations.Months) + " M " + validations.Days + " D ");


        validations.isExcludeMonth(MonthValue_boolean);

//        Diff2 = (TextView) findViewById(R.id.diff2);
        if (validations.Months < 0) {
            validations.Months = 0;
            Diff2.setText(validations.Years + " Y " + (validations.Months) + " M " + validations.Days + " D ");
        } else {
            Diff2.setText(validations.Years + " Y " + (validations.Months) + " M " + validations.Days + " D ");
        }

    }

    public void ci_calculateInterest() {

        //validations = new Validations();


//        A = P (1 + r/n) ^ nt
//        r = the annual interest rate (decimal)
//        n = the number of times that interest is compounded per year
//        t = the number of years the money is invested or borrowed for

//        Number of months completed
        double totalMonths = (validations.Years * 12) + validations.Months;
        Log.i("Math months value is: ", String.valueOf(totalMonths));
//        prinicipalAmount = (EditText) findViewById(R.id.Principal_Amount);

//        t = the number of years the money is invested or borrowed for
        double t = totalMonths/12;
        Log.i("Math t value is: ", String.valueOf(t));

//        n = the number of times that interest is compounded per year
        double n = 1;

//        Prinicipal Amount
        String principalValueString = principal.getText().toString();

        double prinicipalValue = Integer.parseInt(principalValueString);

//        interest per month
//        interestRate = (EditText) findViewById(R.id.interest);
        double interestValue = Double.parseDouble(values.get(3).toString());

//        r = the annual interest rate (decimal)
        double r = (interestValue *12)/100;
        Log.i("Math R value is: ", String.valueOf(r));

//        TextView resultPrincipalValue = (TextView) findViewById(R.id.result_Principal_Amount);
        resultPrincipalValue.setText(principalValueString);

//        totalAmount = (TextView) findViewById(R.id.result_total_amount);
//       x = (1 + r/n)
//        y = nt
        double x = 1+ (double) (r/n);
        Log.i("Sateesh ", "Math X value is: " +String.valueOf(x));
        double y = n*t;
        Log.i("Sateesh ", "Math Y value is: " + String.valueOf(y));
        double totalAmountValue = prinicipalValue * Math.pow((double) x, (double) y );
        Log.i("Math POW value is: ", String.valueOf(Math.pow((double) x, (double) y )));
        String formattedTotalAmountValue = formatDouble(totalAmountValue);
        totalAmount.setText(formattedTotalAmountValue);
        //totalAmount.setText(String.valueOf(totalAmountValue));

//        totalInterest = (TextView) findViewById(R.id.result_total_interest);
        double totalInterestValue = totalAmountValue - prinicipalValue;
        String formattedTotalInterestValue = formatDouble(totalInterestValue);
        totalInterest.setText(formattedTotalInterestValue);
        //totalInterest.setText(String.valueOf(totalInterestValue));




//        interestPerMonth = (TextView) findViewById(R.id.result_interest_month);
//        double interestPerMonthValue = (interestValue * prinicipalValue) / 100;
//        interestPerMonth.setText(String.valueOf(interestPerMonthValue));

//        totalInterest = (TextView) findViewById(R.id.result_total_interest);
//        double totalInterestValue = interestPerMonthValue * totalMonths;
//        totalInterest.setText(String.valueOf(totalInterestValue));

//        totalAmount = (TextView) findViewById(R.id.result_total_amount);
//        double totalAmountValue = totalInterestValue + prinicipalValue;
//        totalAmount.setText(String.valueOf(totalAmountValue));


    }

//    public void calculateDuration() {
//
////        isExclude = (CheckBox) findViewById(R.id.IsExcludeMonth);
//        boolean isExcludeChecked = Boolean.getBoolean(values.get(4).toString());
//
//
//
////        minDays = (EditText) findViewById(R.id.minDays);
////        String days = minDays.getText().toString();
//        int daysCompleted = Integer.parseInt(values.get(5).toString());
//
//
//        dayValues = MonthWiseCalculations.getDayValues();
//
//        Start_Date_Years = dayValues.get(0).intValue();
//        Start_Date_Months = dayValues.get(1).intValue();
//        Start_Date_Days = dayValues.get(2).intValue();
//        End_Date_Years = dayValues.get(3).intValue();
//        End_Date_Months = dayValues.get(4).intValue();
//        End_Date_Days = dayValues.get(5).intValue();
//
//        Log.v("Sateesh", "*** Start Dates are " + Start_Date_Years + "-" + Start_Date_Months + "-" + Start_Date_Days);
//        Log.v("Sateesh", "*** End Dates are " + End_Date_Years+"-"+End_Date_Months+"-" + End_Date_Days);
//
//        if(dayValues != null) {
//            validations.extractYear(Start_Date_Years, End_Date_Years);
//            validations.extractMonth(Start_Date_Years, Start_Date_Months, Start_Date_Days, End_Date_Years, End_Date_Months, End_Date_Days);
//            validations.extractDay(Start_Date_Years, Start_Date_Months, Start_Date_Days, End_Date_Years, End_Date_Months, End_Date_Days);
//
//
////            Diff = (TextView) getView().findViewById(R.id.si_diff);
//            Diff.setText(validations.Years + " Y " + (validations.Months) + " M " + validations.Days + " D ");
//
//
//            validations.isDaysCrossed(daysCompleted);
//
//            Diff1.setText(validations.Years + " Y " + (validations.Months) + " M " + validations.Days + " D ");
//
//
//            validations.isExcludeMonth(isExcludeChecked);
//
////            Diff2 = (TextView) getView().findViewById(R.id.diff2);
//            if (validations.Months < 0) {
//                validations.Months = 0;
//                Diff2.setText(validations.Years + " Y " + (validations.Months) + " M " + validations.Days + " D ");
//            } else {
//                Diff2.setText(validations.Years + " Y " + (validations.Months) + " M " + validations.Days + " D ");
//            }
//        }else{
//            Log.v("Sateesh", "*** dayValues is NULL");
//        }
//
//    }
//
//    public void calculateInterest() {
//        int totalMonths = (validations.Years * 12) + validations.Months;
////        prinicipalAmount = (EditText) findViewById(R.id.Principal_Amount);
//
//        String principalValueString = principal.getText().toString();
//
//        double prinicipalValue = Double.parseDouble(principalValueString);
//
//
////        interestRate = (EditText) findViewById(R.id.interest);
//        double interestValue = Double.parseDouble(values.get(3).toString());
//
//
//        resultPrincipalValue.setText(principalValueString);
//
//
//
//        double interestPerMonthValue = (interestValue * prinicipalValue) / 100;
//        String formattedInterestPerMonthValue = formatDouble(interestPerMonthValue);
//        interestPerMonth.setText(formattedInterestPerMonthValue);
//
//
//        double totalInterestValue = interestPerMonthValue * totalMonths;
//        String formattedTotalInterestValue = formatDouble(totalInterestValue);
//        totalInterest.setText(formattedTotalInterestValue);
//
//
//        double totalAmountValue = totalInterestValue + prinicipalValue;
//        String formattedTotalAmountValue = formatDouble(totalAmountValue);
//        totalAmount.setText(formattedTotalAmountValue);
//
//
//    }

    public static String formatDouble(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(value);
    }

}

