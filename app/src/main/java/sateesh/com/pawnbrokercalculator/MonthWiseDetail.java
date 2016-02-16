package sateesh.com.pawnbrokercalculator;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MonthWiseDetail extends AppCompatActivity {

    EditText startDate, endDate, minDays, prinicipalAmount, interestRate;
    TextView errorMessage, Diff, Diff1, Diff2, resultPrincipal, interestPerMonth, totalInterest, totalAmount;
    String[] Month_Names = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};
    int Start_Date_Years, Start_Date_Months, Start_Date_Days, End_Date_Years, End_Date_Months, End_Date_Days;
    Validations validations;
    CheckBox isExclude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_wise_detail);

    }


    public void Calculate(View v) {
        validations = new Validations();
        displayErrorMessage();
        if ((errorMessage.getText().toString()).matches("")) {
//            calculateDuration();
//            calculateInterest();

            prinicipalAmount = (EditText) findViewById(R.id.Principal_Amount);
            interestRate = (EditText) findViewById(R.id.interest);
            isExclude = (CheckBox) findViewById(R.id.IsExcludeMonth);
            minDays = (EditText) findViewById(R.id.minDays);


            String startDateValue = startDate.getText().toString();
            String endDateValue = endDate.getText().toString();
            String principalValue = prinicipalAmount.getText().toString();
            String interestValue = interestRate.getText().toString();
            String isExcludeCheckedValue = String.valueOf(isExclude.isChecked());
            String minDaysValue = minDays.getText().toString();

            ArrayList<String> dataValues = new ArrayList<String>();
            dataValues.add(startDateValue);
            dataValues.add(endDateValue);
            dataValues.add(principalValue);
            dataValues.add(interestValue);
            dataValues.add(isExcludeCheckedValue);
            dataValues.add(minDaysValue);

            ArrayList<Integer> dayValues = new ArrayList<Integer>();
            dayValues.add(Start_Date_Years);
            dayValues.add(Start_Date_Months);
            dayValues.add(Start_Date_Days);
            dayValues.add(End_Date_Years);
            dayValues.add(End_Date_Months);
            dayValues.add(End_Date_Days);


            Intent calculations = new Intent(this, MonthWiseCalculations.class);
            Bundle b = new Bundle();
            b.putStringArrayList("values", dataValues);
            b.putIntegerArrayList("dayValues", dayValues);

            calculations.putExtras(b);
            startActivity(calculations);

        } else {
            resetValues();
        }


    }

    public void resetValues() {
        Diff = (TextView) findViewById(R.id.diff);
        Diff.setText("");
        Diff1 = (TextView) findViewById(R.id.diff1);
        Diff1.setText("");
        Diff2 = (TextView) findViewById(R.id.diff2);
        Diff2.setText("");
    }

    public void displayErrorMessage() {


        startDate = (EditText) findViewById(R.id.startDate_EditText);
        String isStartDateEmpty = startDate.getText().toString();

        endDate = (EditText) findViewById(R.id.endDate_EditText);
        String isEndDateEmpty = endDate.getText().toString();
        errorMessage = (TextView) findViewById(R.id.ErrorMessage);

        String message = validations.errorMessages(Start_Date_Years, Start_Date_Months, Start_Date_Days, End_Date_Years, End_Date_Months, End_Date_Days);
        if ((isEndDateEmpty.matches("")) || (isStartDateEmpty.matches(""))) {
            errorMessage.setVisibility(View.VISIBLE);
            errorMessage.setText("Please choose Date(s)");
            resetValues();
        } else if (message != null) {
            errorMessage.setVisibility(View.VISIBLE);
            errorMessage.setText(message);
            resetValues();
        } else {
            errorMessage.setText("");
            errorMessage.setVisibility(View.GONE);
        }

    }

    public void calculateDuration() {

        isExclude = (CheckBox) findViewById(R.id.IsExcludeMonth);
        boolean isExcludeChecked = isExclude.isChecked();

        minDays = (EditText) findViewById(R.id.minDays);
        String days = minDays.getText().toString();
        int daysCompleted = Integer.parseInt(days);

        validations.extractYear(Start_Date_Years, End_Date_Years);
        validations.extractMonth(Start_Date_Years, Start_Date_Months, Start_Date_Days, End_Date_Years, End_Date_Months, End_Date_Days);
        validations.extractDay(Start_Date_Years, Start_Date_Months, Start_Date_Days, End_Date_Years, End_Date_Months, End_Date_Days);

        Diff = (TextView) findViewById(R.id.diff);
        Diff.setText(validations.Years + " Y " + (validations.Months) + " M " + validations.Days + " D ");


        validations.isDaysCrossed(daysCompleted);

        Diff1 = (TextView) findViewById(R.id.diff1);
        Diff1.setText(validations.Years + " Y " + (validations.Months) + " M " + validations.Days + " D ");


        validations.isExcludeMonth(isExcludeChecked);

        Diff2 = (TextView) findViewById(R.id.diff2);
        if (validations.Months < 0) {
            validations.Months = 0;
            Diff2.setText(validations.Years + " Y " + (validations.Months) + " M " + validations.Days + " D ");
        } else {
            Diff2.setText(validations.Years + " Y " + (validations.Months) + " M " + validations.Days + " D ");
        }

    }

    public void calculateInterest() {
        int totalMonths = (validations.Years * 12) + validations.Months;
        prinicipalAmount = (EditText) findViewById(R.id.Principal_Amount);

        String principalValueString = prinicipalAmount.getText().toString();

        double prinicipalValue = Double.parseDouble(principalValueString);


        interestRate = (EditText) findViewById(R.id.interest);
        double interestValue = Double.parseDouble(interestRate.getText().toString());

        TextView resultPrincipalValue = (TextView) findViewById(R.id.result_Principal_Amount);
        resultPrincipalValue.setText(principalValueString);

        interestPerMonth = (TextView) findViewById(R.id.result_interest_month);
        double interestPerMonthValue = (interestValue * prinicipalValue) / 100;
        String formattedInterestPerMonthValue = formatDouble(interestPerMonthValue);
        interestPerMonth.setText(formattedInterestPerMonthValue);

        totalInterest = (TextView) findViewById(R.id.result_total_interest);
        double totalInterestValue = interestPerMonthValue * totalMonths;
        String formattedTotalInterestValue = formatDouble(totalInterestValue);
        totalInterest.setText(formattedTotalInterestValue);

        totalAmount = (TextView) findViewById(R.id.result_total_amount);
        double totalAmountValue = totalInterestValue + prinicipalValue;
        String formattedTotalAmountValue = formatDouble(totalAmountValue);
        totalAmount.setText(formattedTotalAmountValue);


    }

    public static String formatDouble(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(value);
    }

    public void startDate_Click(View v) {
        DialogFragment newFragment = new StartDate_DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    public class StartDate_DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            //populateSetDate(year, month, day);
            startDate = (EditText) findViewById(R.id.startDate_EditText);
            String StartDate_Month_Name = Month_Names[month];
            startDate.setText(day + " - " + StartDate_Month_Name + " - " + year);
            Start_Date_Years = year;
            Start_Date_Months = month + 1;
            Start_Date_Days = day;
        }
    }

    public void endDate_Click(View v) {
        DialogFragment newFragment2 = new EndDate_DatePickerFragment();
        newFragment2.show(getSupportFragmentManager(), "datePicker");
    }

    public class EndDate_DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            //populateSetDate(year, month, day);
            endDate = (EditText) findViewById(R.id.endDate_EditText);
            endDate.clearComposingText();

            String EndDate_Month_Name = Month_Names[month];
            endDate.setText(day + " - " + EndDate_Month_Name + " - " + year);

            End_Date_Years = year;
            End_Date_Months = month + 1;
            End_Date_Days = day;
        }
    }
}
