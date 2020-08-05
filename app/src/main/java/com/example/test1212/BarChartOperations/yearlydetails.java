package com.example.test1212.BarChartOperations;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class yearlydetails {

    public static ArrayList<String> datesofyear(int year)
    {


        Calendar calendarStart=Calendar.getInstance();
        calendarStart.set(Calendar.YEAR,year);
        calendarStart.set(Calendar.MONTH,0);
        calendarStart.set(Calendar.DAY_OF_MONTH,1);
        // returning the first date

        Date startDate=calendarStart.getTime();

        Calendar calendarEnd=Calendar.getInstance();
        calendarEnd.set(Calendar.YEAR,year);
        calendarEnd.set(Calendar.MONTH,11);
        calendarEnd.set(Calendar.DAY_OF_MONTH,31);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        ArrayList<String> alldatesofyear = new ArrayList<>();

        do
        {
            int lastdayofmonth = calendarStart.getActualMaximum(Calendar.DAY_OF_MONTH);
            for(int i=0;i<lastdayofmonth;i++)
            {
                alldatesofyear.add(df.format(calendarStart.getTime()));
                calendarStart.add(Calendar.DAY_OF_MONTH, 1);
            }
            alldatesofyear.add("---");
        }while(calendarStart.compareTo(calendarEnd)!=1);

        return  alldatesofyear;

    }

}
