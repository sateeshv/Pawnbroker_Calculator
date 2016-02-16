package sateesh.com.pawnbrokercalculator;

/**
 * Created by Sateesh on 1/11/2016.
 */
public class Validations {

    int Years;
    int Months;

    int Days;

    public static String errorMessages(int sYears, int sMonths, int sDays, int eYears, int eMonths, int eDays) {
        String message = null;
        if (((sYears == eYears) && (sMonths == eMonths)) && sDays > eDays) {
            message = "Start Date - Day is greater than End Date - Day \n Please correct it ";
            return message;
        } else if ((sYears == eYears) && sMonths > eMonths) {
            message = "Start Date - Month is greater than End Date - Month \n Please correct it";
            return message;
        } else if (sYears > eYears) {
            message = "Start Date - Year is greater than End Date - Year \n Please correct it";
            return message;
        } else {
            return null;
        }

    }

    public void extractYear(int sYears, int eYears) {
        Years = (eYears - sYears);
    }

    public void extractMonth(int sYears, int sMonths, int sDays, int eYears, int eMonths, int eDays) {
        if ((sYears < eYears) && (sMonths > eMonths)) {
            Months = ((eMonths + 12) - sMonths);
            if ((eYears - sYears) == 1) {
                Years = 0;
            } else {
                Years = Years - 1;
            }
            //Years = 0;
        } else {
            Months = (eMonths - sMonths);

        }
    }

    public void extractDay(int sYears, int sMonths, int sDays, int eYears, int eMonths, int eDays) {
        if ((sMonths > eMonths) && (sDays > eDays)) {
            Days = ((eDays + 30) - sDays);
            Months = ((eMonths + 12) - sMonths) - 1;
            if ((eYears - sYears) == 1) {
                Years = 0;
            } else {
                Years = Years - 1;
            }

        } else if ((sMonths < eMonths) && (sDays > eDays)) {
            Days = ((eDays + 30) - sDays);
            Months = Months - 1;
        } else {
            Days = eDays - sDays;
        }
    }

    public void isDaysCrossed(int minDays) {
        if (Days >= minDays) {
            Months = Months + 1;
            Days = 0;
        } else {
            Months = Months;
            Days = 0;
        }
    }

    public void isExcludeMonth(boolean isExclude) {
        if (isExclude == true) {
            if(Years > 0){
                Years = Years -1;
                Months = ( Months + 12) - 1;
            }else{
                Months = Months - 1;
            }
        } else {
            Months = Months;
        }
    }

}
