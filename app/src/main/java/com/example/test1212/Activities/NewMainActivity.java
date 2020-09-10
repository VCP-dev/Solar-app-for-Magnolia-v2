package com.example.test1212.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1212.BarChartOperations.hourlydetails;
import com.example.test1212.BarChartOperations.monthlydetails;
import com.example.test1212.BarChartOperations.yearlydetails;
import com.example.test1212.UtilityClasses.ArrayListConverter;
import com.example.test1212.UtilityClasses.JSONparserclass;
import com.example.test1212.R;
import com.example.test1212.RequestedValues.HourlyValues;
import com.example.test1212.RequestedValues.Interval;
import com.example.test1212.RequestedValues.SummaryOfDay;
import com.example.test1212.RequestedValues.WeeklyValues;
import com.example.test1212.SolarApi;
import com.example.test1212.StoredValues;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewMainActivity extends AppCompatActivity {


    ProgressBar loadingcircle;


    CardView hourlygraphcard;
    CardView monthlygraphcard;
    CardView yearlygraphcard;
    CardView statuscard;

    BarChart hourlygraph;
    BarChart monthlygraph;
    BarChart yearlygraph;

    BarData bardatahourly;
    BarData bardatamonthly;
    BarData bardatayearly;


    ArrayList<BarEntry> barenetryhourlygraph;
    ArrayList<BarEntry> barentrymonthlygraph;
    ArrayList<BarEntry> barentryyearlygraph;



    ImageView aboutbutton;


    float hourlytotalvalue;
    float monthlytotalvalue;
    float yearlytotalvalue;



    private static int delay_to_call = 1000;



    ArrayList<String> dates;
    Random random;
    ArrayList<BarEntry> barEntries;



    Boolean canpress;



    Boolean doubleBackToExit;




    // --------------------------------- for status card ---------------------------------

    TextView todayenergy,todayunitperkwp;
    TextView yesterdayenergy,yesterdayunitperkwp;
    TextView thismonthenergy,thismonthunitperkwp;
    TextView lastmonthenergy,lastmonthunitperkwp;
    TextView thisyearenergy,thisyearunitperkwp;
    TextView lastyearenergy,lastyearunitperkwp;
    TextView lifetimeenergy,lifetimeunitperkwp;

    // --------------------------------- for status card ---------------------------------




    // --------------------------------- for textviews under graphs ----------------------

    TextView undergraphhourlyenergyproduced,undergraphhourlyunitsperkwp;
    TextView undergraphmonthlyenergyproduced,undergraphmonthlyunitsperkwp;
    TextView undergraphyearlyenergypeoduced,undergraphyearlyunitsperkwp;

    // --------------------------------- for textviews under graphs ----------------------



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);

        loadingcircle = findViewById(R.id.loadingcircle);

        canpress=false;
        doubleBackToExit=false;

        hourlygraphcard = findViewById(R.id.todaygraphcard);
        monthlygraphcard = findViewById(R.id.thismonthgraphcard);
        yearlygraphcard = findViewById(R.id.thisyeargraphcard);
        statuscard = findViewById(R.id.statuspagecard);


        hourlygraph = findViewById(R.id.hourlygraph);
        monthlygraph = findViewById(R.id.monthlygraph);
        yearlygraph = findViewById(R.id.yearlygraph);


        // --------------------------------- for status card ---------------------------------

        todayenergy = findViewById(R.id.todayenergy);                    // today
        todayunitperkwp = findViewById(R.id.todayunitperkwp);
        yesterdayenergy = findViewById(R.id.yesterdayenergy);            // yesterday
        yesterdayunitperkwp = findViewById(R.id.yesterdayunitperkwp);
        thismonthenergy = findViewById(R.id.thismonthenergy);            // this month
        thismonthunitperkwp = findViewById(R.id.thismonthunitperkwp);
    //    lastmonthenergy = findViewById(R.id.lastmonthenergy);            // last month
    //    lastmonthunitperkwp = findViewById(R.id.lastmonthunitperkwp);
        thisyearenergy = findViewById(R.id.thisyearenergy);              // this year
        thisyearunitperkwp = findViewById(R.id.thisyearunitperkwp);
    //    lastyearenergy = findViewById(R.id.lastyearenergy);              // last year
    //    lastyearunitperkwp = findViewById(R.id.lastyearunitperkwp);
        lifetimeenergy = findViewById(R.id.lifetimeenergy);              // lifetime
        lifetimeunitperkwp = findViewById(R.id.lifetimeunitperkwp);


        // --------------------------------- for status card ---------------------------------



        // --------------------------------- for textviews under graphs ----------------------

        undergraphhourlyenergyproduced = findViewById(R.id.undergraphhourlyenergyproduced);
        undergraphhourlyunitsperkwp = findViewById(R.id.undergraphhourlyunitsperkwp);
        undergraphmonthlyenergyproduced = findViewById(R.id.undergraphmonthlyenergyproduced);
        undergraphmonthlyunitsperkwp = findViewById(R.id.undergraphmonthlyunitsperkwp);
        undergraphyearlyenergypeoduced = findViewById(R.id.undergraphyearlyenergyproduced);
        undergraphyearlyunitsperkwp = findViewById(R.id.undergraphyearlyunitsperkwp);

        // --------------------------------- for textviews under graphs ----------------------


        aboutbutton = findViewById(R.id.about_button_main);



        aboutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMainActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });


        Toast.makeText(NewMainActivity.this,"Updating, please wait...",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                createhourlygraph("Hourly",Returncurrentdate(),hourlygraph);

                //createRandomBarGraph("1-10-2020","30-10-2020",monthlygraph,"Monthly");
                //createRandomBarGraph("3-10-2020","15-10-2020",yearlygraph,"Yearly");
            }
        },delay_to_call);
        //createRandomBarGraph("10-10-2020","15-10-2020",hourlygraph,"Hourly");


        statuscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!canpress) {
                    return;
                }
                else{
                    Intent intent = new Intent(NewMainActivity.this,MainActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("fragmentname","status");
                    extras.putString("typeofenergyfragment","none");
                    extras.putString("totalvalue","none");
                    StoredValues.graphtypetobeshown = "none";
                    intent.putExtras(extras);
                    startActivity(intent);
                    return;
                }
            }
        });

        hourlygraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!canpress) {
                    return;
                }
                else{
                    Intent intent = new Intent(NewMainActivity.this,MainActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("fragmentname","energy");
                    extras.putString("typeofenergyfragment","hourly");
                    StoredValues.transferredtotalvalue = hourlytotalvalue;
                    StoredValues.graphtypetobeshown = "hourly";
                    StoredValues.transferredbardata = bardatahourly;
                    //StoredValues.transferredbarentries = barenetryhourlygraph;
                    StoredValues.transferredbarchartdescription = "Hourly analysis of "+Returncurrentdate();
                    intent.putExtras(extras);
                    startActivity(intent);
                    return;
                }
            }
        });

        monthlygraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!canpress) {
                    return;
                }
                else{
                    Calendar cal = Calendar.getInstance();
                    String month = monthname(cal.get(Calendar.MONTH));


                    Intent intent = new Intent(NewMainActivity.this,MainActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("fragmentname","energy");
                    extras.putString("typeofenergyfragment","monthly");
                    StoredValues.transferredtotalvalue = monthlytotalvalue;
                    StoredValues.graphtypetobeshown = "monthly";
                    StoredValues.transferredbardata = bardatamonthly;
                    //StoredValues.transferredbarentries = barentrymonthlygraph;
                    StoredValues.transferredbarchartdescription = "Monthly analysis of "+month+" "+cal.get(Calendar.YEAR);
                    intent.putExtras(extras);
                    startActivity(intent);
                    return;
                }
            }
        });

        yearlygraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!canpress) {
                    return;
                }
                else{
                    Calendar cal = Calendar.getInstance();


                    Intent intent = new Intent(NewMainActivity.this,MainActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("fragmentname","energy");
                    extras.putString("typeofenergyfragment","yearly");
                    StoredValues.transferredtotalvalue=yearlytotalvalue;
                    StoredValues.graphtypetobeshown = "yearly";
                    //StoredValues.transferredbarentries = barentryyearlygraph;
                    StoredValues.transferredbardata = bardatayearly;
                    StoredValues.transferredbarchartdescription = "Yearly analysis of "+cal.get(Calendar.YEAR);
                    intent.putExtras(extras);
                    startActivity(intent);
                    return;
                }
            }
        });


    }




    @Override
    public void onBackPressed(){
        if(doubleBackToExit){
            super.onBackPressed();
            return;
        }

        this.doubleBackToExit=true;
        Toast.makeText(NewMainActivity.this,"Please press back again to exit...",Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              doubleBackToExit=false;
            }
        },2000);
    }






    String monthname(int month)
    {
        String monthnum="";
        switch(month)
        {
            case 0:
                monthnum = "Jan";
                break;
            case 1:
                monthnum = "Feb";
                break;
            case 2:
                monthnum = "March";
                break;
            case 3:
                monthnum = "April";
                break;
            case 4:
                monthnum = "May";
                break;
            case 5:
                monthnum = "June";
                break;
            case 6:
                monthnum = "July";
                break;
            case 7:
                monthnum = "Aug";
                break;
            case 8:
                monthnum = "Sep";
                break;
            case 9:
                monthnum = "Oct";
                break;
            case 10:
                monthnum = "Nov";
                break;
            case 11:
                monthnum = "Dec";
                break;
        }
        return monthnum;

    }










    ///  --------------------------------  hourly bar graph functions  --------------------------------


    //@RequiresApi(api = Build.VERSION_CODES.O)
    public void createhourlygraph(String description, String date,BarChart barChart)
    {
        barChart.clear();
        ArrayList<String> selecteddayhours = hourlydetails.hoursofdayepochfivemindiff(date);
        String starttime = selecteddayhours.get(0);
        String endtime;
        endtime = selecteddayhours.get(selecteddayhours.size() - 1);
        getHourlyValues(NewMainActivity.this, description, starttime, endtime,date,barChart);
    }


    private void getHourlyValues(final Context context, String description, final String starttime, final String endtime, String date,final BarChart barChart)
    {

        JSONparserclass parser = new JSONparserclass();

        //Toast.makeText(context,"Making a request for values, please wait",Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(), "starttime: " + starttime + " || endtime: " + endtime, Toast.LENGTH_LONG).show();

        Call<HourlyValues> hourlyValues;

        String today = Returncurrentdate();
        if(date.equals(today)){
            hourlyValues = SolarApi.getService(context).getValuesOfEachHourToday(parser.returnapivalue("system_id",context),"iso8601",parser.returnapivalue("user_id",context),parser.returnapivalue("apikey",context));
        }
        else{
            hourlyValues = SolarApi.getService(context).getValuesOfEachHour(parser.returnapivalue("system_id",context),starttime,endtime,"iso8601",parser.returnapivalue("user_id",context),parser.returnapivalue("apikey",context));
        }


        //Call<HourlyValues> hourlyValues = SolarApi.getService().getValuesOfEachHour(MainActivity.returnapivalue("system_id",context),starttime,endtime,"iso8601",MainActivity.returnapivalue("user_id",context),MainActivity.returnapivalue("apikey",context));
        final String descr = description;
        hourlyValues.enqueue(new Callback<HourlyValues>() {
            @Override
            public void onResponse(Call<HourlyValues> call, Response<HourlyValues> response) {
                if(response.body() instanceof HourlyValues)
                {
                    //Toast.makeText(context,"Receiving values and creating graph.....",Toast.LENGTH_SHORT).show();

                    hourlytotalvalue = 0;

                    HourlyValues values = response.body();

                    //ArrayList<String> HourList = selecteddayhours;
                    ArrayList<String> timelist = new ArrayList<>();

                    barEntries = new ArrayList<>();

                    List<Interval> selecteddayintervals = response.body().getIntervals();

                    for(int i=0;i<selecteddayintervals.size();i++)
                    {
                        /////   display enwh divided by 1000
                        try {
                            float entryvalue = ((float) selecteddayintervals.get(i).getEnwh()) / 1000.0f;
                            hourlytotalvalue += entryvalue;
                            barEntries.add(new BarEntry(entryvalue, i));
                            timelist.add(timeofday(selecteddayintervals.get(i).getEndAt()));
                        }
                        catch(Exception e)
                        {
                            Log.println(Log.ASSERT,"Caught exception:",e.getMessage().toString());
                        }
                    }

                    barenetryhourlygraph = barEntries;

                    BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");
                    barDataSet.setDrawValues(true);

                    bardatahourly = new BarData(timelist,barDataSet);

                    BarDataSet currentbarDataSet = new BarDataSet(barEntries, "Dates");

                    currentbarDataSet.setHighLightColor(Color.TRANSPARENT);
                    currentbarDataSet.setHighLightAlpha(0);
                    currentbarDataSet.setDrawValues(false);

                    BarData barData = new BarData(timelist, currentbarDataSet);
                    barChart.setData(barData);
                    //barChart.setDescription(descr);
                    barChart.setDescription(null);
                    barChart.fitScreen();
                    barChart.setScaleEnabled(false);
                    barChart.setData(barData);
                    barChart.getLegend().setEnabled(false);
                    barChart.getAxisLeft().setGridColor(Color.parseColor("#ffffff"));
                    barChart.getAxisRight().setGridColor(Color.parseColor("#ffffff"));
                    barChart.getXAxis().setGridColor(Color.parseColor("#ffffff"));
                    barChart.getXAxis().setDrawLabels(false);
                    barChart.getAxisLeft().setDrawLabels(false);
                    barChart.getAxisRight().setDrawLabels(false);


                    //Toast.makeText(NewMainActivity.this,"Bar graph created, Please tap the display if not visible",Toast.LENGTH_SHORT).show();

                    String todaysvaluestring = ""+String.format("%.0f",hourlytotalvalue);                                           ////   energy produced today
                    float todayavgvalue = hourlytotalvalue/49.7f;//todaysvalue / 49.7f;

                    String todaysavgvaluestring = ""+String.format("%.2f",todayavgvalue);                                   ////   units per kwp today
                    StoredValues.energyproducedtoday = todaysvaluestring;
                    StoredValues.unitsperkwptoday = todaysavgvaluestring;

                    todayenergy.setText(StoredValues.energyproducedtoday);
                    todayunitperkwp.setText(StoredValues.unitsperkwptoday);

                    undergraphhourlyenergyproduced.setText(StoredValues.energyproducedtoday);
                    undergraphhourlyunitsperkwp.setText(StoredValues.unitsperkwptoday);


                    createyearlygraph("Yearly",yearlygraph);

                }
                else
                {
                    Toast.makeText(context,"invalid response: "+ new Gson().toJson(response).toString(),Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<HourlyValues> call, Throwable t) {
                Toast.makeText(context,"Error occured, Could not get hourly values",Toast.LENGTH_SHORT).show();
                //Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    String timeofday(String time)
    {
        String[] arr1 = time.split("\\+");
        String[] arr2 = arr1[0].split("T");
        return arr2[1];
    }





    ///   --------------------------------   monthly bar graph functions   --------------------------------



    public void createMonthBargraph(BarChart barChart,String description,String date)
    {
        barChart.clear();
        ArrayList<String> selectedMonth = monthlydetails.GetMonthDates(date);
        String startdate = selectedMonth.get(0);
        String enddate;
        if(selectedMonth.contains(Returncurrentdate())){
            enddate = Returncurrentdate();
        }
        else{
            enddate = selectedMonth.get(selectedMonth.size()-1);
        }

        getMonthlyValues(barChart,NewMainActivity.this,description,startdate,enddate);
        //Toast.makeText(getContext(),"Bar graph created, Please tap the display if not visible",Toast.LENGTH_SHORT).show();
    }

    public void getMonthlyValues(final BarChart barChart,final Context context, final String description, final String startdate, final String enddate)
    {
        JSONparserclass parser = new JSONparserclass();
        //Toast.makeText(context,"Making a request for values, please wait",Toast.LENGTH_SHORT).show();
        final Call<WeeklyValues> monthlyValues = SolarApi.getService(context).getValuesofWeek(parser.returnapivalue("system_id",context),startdate,enddate,parser.returnapivalue("apikey",context),parser.returnapivalue("user_id",context));
        final String descr = description;

        monthlyValues.enqueue(new Callback<WeeklyValues>() {
            @Override
            public void onResponse(Call<WeeklyValues> call, Response<WeeklyValues> response) {

                if(response.body() instanceof WeeklyValues) {

                    //Toast.makeText(context,"Receiving values and creating graph.....",Toast.LENGTH_SHORT).show();

                    monthlytotalvalue = 0;

                    WeeklyValues values = response.body();

                    ArrayList<String> daysOfMonth = null;

                    daysOfMonth = monthlydetails.GetMonthDates(startdate,enddate);

                    barEntries = new ArrayList<>();
                    List<Integer> valuesofweek = values.getProduction();
                    //if(daysOfMonth.contains(returncurrentdate())) {                    ///     checking to see if current month

                    for_this_month(barChart,context,daysOfMonth,valuesofweek,description);



                    //Toast.makeText(NewMainActivity.this,"Bar graph created, Please tap the display if not visible",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context,"Error occured, Could not get monthly values",Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<WeeklyValues> call, Throwable t) {
                Toast.makeText(context,"Error occured, Could not get monthly values",Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void for_this_month(final BarChart barChart,final Context context,final ArrayList<String> daysOfMonth,final List<Integer> valuesofweek,final String descr)
    {
        JSONparserclass parser = new JSONparserclass();
        //Snackbar.make(getView(),"Updating page with latest data....",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
        Call<SummaryOfDay> summaryOfDay = SolarApi.getService(context).getSummaryOfToday(parser.returnapivalue("system_id",context),Returncurrentdate(),parser.returnapivalue("apikey",context),parser.returnapivalue("user_id",context));
        summaryOfDay.enqueue(new Callback<SummaryOfDay>() {
            @Override
            public void onResponse(Call<SummaryOfDay> call, Response<SummaryOfDay> response) {

                SummaryOfDay today = response.body();

                Integer todaysvalue = today.getEnergyToday();
                Integer tk = todaysvalue / 1000;
                Integer tr = todaysvalue % 1000;

                int j;
                for (j = 0; j < daysOfMonth.size()-1; j++) {
                    float entryvalue = ((float) valuesofweek.get(j)) / 1000.0f;
                    monthlytotalvalue += entryvalue;
                    barEntries.add(new BarEntry(entryvalue, j));
                }

                //Toast.makeText(context, "last value="+tk, Toast.LENGTH_SHORT).show();
                monthlytotalvalue += tk;
                barEntries.add(new BarEntry(tk, j));

                /*
                String todaysvaluestring = tk + "." + tr;                                              ////   energy produced today
                float todayavgvalue = todaysvalue / 49.7f;
                int tka = (int) todayavgvalue / 1000;
                int tra = (int) todayavgvalue % 1000;
                String todaysavgvaluestring = tka + "." + tra;                                     ////   units per kwp today
                StoredValues.energyproducedtoday = todaysvaluestring;
                StoredValues.unitsperkwptoday = todaysavgvaluestring;*/


                float yesterdaysvalue = (valuesofweek.get(valuesofweek.size()-1))/1000;                      ////   energy produced yesterday
                StoredValues.energyproducedyesterday = String.format("%.0f",yesterdaysvalue);
                float yesterdaysavgvalue = (yesterdaysvalue/49.7f);                                   ////   units per kwp yesterday
                StoredValues.unitsperkwpyesterday = String.format("%.2f",yesterdaysavgvalue);

                yesterdayenergy.setText(StoredValues.energyproducedyesterday);
                yesterdayunitperkwp.setText(StoredValues.unitsperkwpyesterday);


                //String thismonthvaluevaluestring = tk + "." + tr;                                              ////   energy produced this month
                float thismonthavgvalue = monthlytotalvalue / (49.7f*(valuesofweek.size()+1));
                StoredValues.energyproducedthismonth = String.format("%.0f",monthlytotalvalue);                                      ////   units per kwp this month
                StoredValues.unitsperkwpthismonth = String.format("%.2f",thismonthavgvalue);

                thismonthenergy.setText(StoredValues.energyproducedthismonth);
                thismonthunitperkwp.setText(StoredValues.unitsperkwpthismonth);

                undergraphmonthlyenergyproduced.setText(StoredValues.energyproducedthismonth);
                undergraphmonthlyunitsperkwp.setText(StoredValues.unitsperkwpthismonth);


                barentrymonthlygraph = barEntries;


                BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");
                barDataSet.setDrawValues(true);

                bardatamonthly = new BarData(daysOfMonth, barDataSet);

                BarDataSet currentbarDataSet = new BarDataSet(barEntries, "Dates");

                currentbarDataSet.setHighLightColor(Color.TRANSPARENT);
                currentbarDataSet.setHighLightAlpha(0);
                currentbarDataSet.setDrawValues(false);

                BarData barData = new BarData(daysOfMonth, currentbarDataSet);
                barChart.setData(barData);
                //barChart.setDescription(descr);
                barChart.setDescription(null);
                barChart.fitScreen();
                barChart.setScaleEnabled(false);
                barChart.setData(barData);
                barChart.getLegend().setEnabled(false);
                barChart.getAxisLeft().setGridColor(Color.parseColor("#ffffff"));
                barChart.getAxisRight().setGridColor(Color.parseColor("#ffffff"));
                barChart.getXAxis().setGridColor(Color.parseColor("#ffffff"));
                barChart.getXAxis().setDrawLabels(false);
                barChart.getAxisLeft().setDrawLabels(false);
                barChart.getAxisRight().setDrawLabels(false);


                getlifetimevalues(context);


            }

            @Override
            public void onFailure(Call<SummaryOfDay> call, Throwable t) {
                Toast.makeText(context,"Error occured, Could not get monthly values",Toast.LENGTH_SHORT).show();
            }
        });
    }









///   --------------------------------   yearly bar graph functions   --------------------------------



    public void createyearlygraph(String description,BarChart barChart)
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        yearlygraph.clear();
        ArrayList<String> yeardates = yearlydetails.datesofyear(year);
        createrequestforyear(year,barChart,"Yearly");
        //String startdate = yeardates.get(0);
        //String endate = Returncurrentdate();
        //getyearlyvalues(NewMainActivity.this,barChart,description,startdate,endate,yeardates);
    }


    public void createrequestforyear(int year,BarChart barChart,String description)
    {
        String startdate,endate;
        JSONparserclass parser = new JSONparserclass();
        if(year == Integer.parseInt(parser.returnapivalue("system_start_year",NewMainActivity.this)))    ///    for starting year of system
        {
            ArrayList<String> yeardates = yearlydetails.datesofyear(year);

            startdate = parser.returnapivalue("system_start_date",NewMainActivity.this);
            endate = yeardates.get(yeardates.size()-2);

            int numberofdays = findnumberofdaysforyear(yeardates,startdate,endate);

            //getyearlyvalues(NewMainActivity.this,startdate,endate,yeardates,year,numberofdays);
            getyearlyvalues(NewMainActivity.this,barChart,description,startdate,endate,yeardates,numberofdays);
        }
        else if(year==Calendar.getInstance().get(Calendar.YEAR))       ///      for current year
        {
            ArrayList<String> yeardates = yearlydetails.datesofyear(year);

            startdate = yeardates.get(0);
            endate = Returncurrentdate();

            int numberofdays = findnumberofdaysforyear(yeardates,startdate,endate);

            //getyearlyvalues(NewMainActivity.this,startdate,endate,yeardates,year,numberofdays);
            getyearlyvalues(NewMainActivity.this,barChart,description,startdate,endate,yeardates,numberofdays);
        }
        else      ///     any other year
        {
            ArrayList<String> yeardates = yearlydetails.datesofyear(year);

            startdate = yeardates.get(0);
            endate = yeardates.get(yeardates.size()-2);

            int numberofdays = findnumberofdaysforyear(yeardates,startdate,endate);

            //getyearlyvalues(NewMainActivity.this,startdate,endate,yeardates,year,numberofdays);
            getyearlyvalues(NewMainActivity.this,barChart,description,startdate,endate,yeardates,numberofdays);
        }
    }


    int findnumberofdaysforyear(ArrayList<String> yeardates,String startdate,String enddate)
    {
        int startdateindex = yeardates.indexOf(startdate);
        int endindex = yeardates.indexOf(enddate);

        int numberofdays = (endindex-startdateindex)+1;

        for(int k=startdateindex;k<endindex;k++)
        {
            if(yeardates.get(k)=="---")
            {
                numberofdays-=1;
            }
        }

        return numberofdays;
    }


    public void getyearlyvalues(final Context context, final BarChart barChart, final String description, final String startdate, final String enddate, final ArrayList<String> yeardates,final int numberofdays)
    {
        //Toast.makeText(context,"Making a request for values, please wait",Toast.LENGTH_SHORT).show();
        JSONparserclass parser = new JSONparserclass();
        final Call<WeeklyValues> yearlyValues = SolarApi.getService(context).getValuesofWeek(parser.returnapivalue("system_id",context),startdate,enddate,parser.returnapivalue("apikey",context),parser.returnapivalue("user_id",context));
        final String descr = description;

        yearlyValues.enqueue(new Callback<WeeklyValues>() {
            @Override
            public void onResponse(Call<WeeklyValues> call, Response<WeeklyValues> response) {

                if(response.body() instanceof WeeklyValues) {

                    ArrayListConverter converter = new ArrayListConverter();

                    //Toast.makeText(context,"Receiving values and creating graph.....",Toast.LENGTH_SHORT).show();

                    WeeklyValues values = response.body();

                    yearlytotalvalue = 0;

                    List<Integer> valuesforgivenyear = values.getProduction();

                    String[] arrayofmonths = {"Jan","Feb","March","April","May","June","July","Aug","Sep","Oct","Nov","Dec"};

                    ArrayList<String> months = converter.converttoarrayliststring(arrayofmonths);

                    ArrayList<Float> valuesforeachmonthinyear = converter.converttoarraylistfloat(new float[]{0,0,0,0,0,0,0,0,0,0,0,0});

                    barEntries = new ArrayList<>();


                    float totalformonth=0;

                    int i=0;

                    int lastindex=0;

                    int j=startingmonth(startdate);




                    if(yeardates.contains(Returncurrentdate()))
                    {
                        int indexofthismonth=0;
                        Calendar c = Calendar.getInstance();
                        indexofthismonth = c.get(Calendar.MONTH);
                        for_this_year(barChart,indexofthismonth,yeardates,context,months,description,j,valuesforgivenyear,valuesforeachmonthinyear,numberofdays);
                    }



                    else{

                        if(j!=0)         //// for the starting year in which the preceeding months had no power generation
                        {
                            for (int k = 0; k < j; k++) {
                                barEntries.add(new BarEntry(0, k));
                            }
                        }


                        for (String date : yeardates)         // using yeardates separate the values obtained into each month dividing by ---
                        {

                            if (date == "---") {
                                try {
                                    valuesforeachmonthinyear.set(j, totalformonth);
                                    barEntries.add(new BarEntry(valuesforeachmonthinyear.get(j), j));
                                    j += 1;
                                    yearlytotalvalue += totalformonth;
                                    totalformonth = 0;
                                    lastindex=j;
                                } catch (Exception ex) {
                                    Log.println(Log.ASSERT, "value of j", "j=" + j);
                                }
                            }
                            else {
                                try {
                                    totalformonth += valuesforgivenyear.get(i) / 1000f;
                                } catch (Exception ex) {
                                    Log.println(Log.ASSERT, "value of i", "i=" + i);
                                    Log.println(Log.ASSERT, "during adding values", ex.getMessage());
                                }
                                i += 1;
                            }

                        }



                    }





                    //Toast.makeText(NewMainActivity.this,"Bar graph created, Please tap the display if not visible",Toast.LENGTH_SHORT).show();


                }
                else{

                    ///   this portion of code should technically never occur but just in case, to check if the object recieved isn't of the required type

                    Toast.makeText(context,"Error occured, Values not avalaible",Toast.LENGTH_SHORT).show();
                    return;
                }

            }

            @Override
            public void onFailure(Call<WeeklyValues> call, Throwable t) {
                Toast.makeText(context,"Error occured, Could not get yearly values",Toast.LENGTH_SHORT).show();
            }
        });
    }






    ///  in a way the call in this function isn't required since today's data should already exist. Remove the call and use that if you want


    private void for_this_year(final BarChart barChart,final int indextoaddtodays,final ArrayList<String> yeardates,final Context context, final ArrayList<String> months, final String descr, final int startindex, final List<Integer> Valuesforgivenyear, final ArrayList<Float> Valuesforeachmonth,final float numberofdays)
    {
        //Snackbar.make(getView(),"Updating page with latest data....",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
        JSONparserclass parser = new JSONparserclass();
        Call<SummaryOfDay> summaryOfDay = SolarApi.getService(context).getSummaryOfToday(parser.returnapivalue("system_id",context),Returncurrentdate(),parser.returnapivalue("apikey",context),parser.returnapivalue("user_id",context));
        summaryOfDay.enqueue(new Callback<SummaryOfDay>() {
            @Override
            public void onResponse(Call<SummaryOfDay> call, Response<SummaryOfDay> response) {

                SummaryOfDay today = response.body();

                Integer todaysvalue = today.getEnergyToday();
                Integer tk = todaysvalue / 1000;
                Integer tr = todaysvalue % 1000;

                String todaysvaluestring = tk + "." + tr;                                              ////   energy produced today
                float todayavgvalue = todaysvalue / 49.7f;
                int tka = (int) todayavgvalue / 1000;
                int tra = (int) todayavgvalue % 1000;
                String todaysavgvaluestring = tka + "." + tra;                                     ////   units per kwp today
                StoredValues.energyproducedtoday = todaysvaluestring;
                StoredValues.unitsperkwptoday = todaysavgvaluestring;

                List<Integer> valuesforgivenyear = Valuesforgivenyear;

                ArrayList<Float> valuesforeachmonthinyear = Valuesforeachmonth;

                float totalformonth=0;

                int i=0;

                if(startindex!=0)         //// for the starting year in which the preceeding months had no power generation
                {
                    for (int k = 0; k < startindex; k++) {
                        barEntries.add(new BarEntry(0, k));
                    }
                }


                int j = startindex;
                int lastindex=0;

                for (String date : yeardates)         // using yeardates separate the values obtained into each month dividing by ---
                {


                    if (date == "---") {
                        try {
                            valuesforeachmonthinyear.set(j, totalformonth);
                            barEntries.add(new BarEntry(valuesforeachmonthinyear.get(j), j));
                            j += 1;
                            yearlytotalvalue += totalformonth;
                            totalformonth = 0;
                            lastindex=j;
                        } catch (Exception ex) {
                            Log.println(Log.ASSERT, "value of j", "j=" + j);
                        }
                    }
                    else {
                        try {
                            totalformonth += valuesforgivenyear.get(i) / 1000f;
                        } catch (Exception ex) {
                            Log.println(Log.ASSERT, "value of i", "i=" + i);
                            Log.println(Log.ASSERT, "during adding values", ex.getMessage());
                        }
                        i += 1;
                    }

                }

                //Toast.makeText(context, "adding tk="+tk, Toast.LENGTH_SHORT).show();
                float lastmonthval = barEntries.get(indextoaddtodays).getVal();
                lastmonthval+=tk;
                yearlytotalvalue+=tk;
                barEntries.remove(indextoaddtodays);
                barEntries.add(new BarEntry(lastmonthval, indextoaddtodays));





                StoredValues.energyproducedthisyear = ""+String.format("%.0f",yearlytotalvalue);                   /////   for this year
                StoredValues.unitsperkwpthisyear = ""+String.format("%.2f",(yearlytotalvalue / (49.7f*numberofdays/**numberofdays*/)));

                thisyearenergy.setText(StoredValues.energyproducedthisyear);
                thisyearunitperkwp.setText(StoredValues.unitsperkwpthisyear);

                undergraphyearlyenergypeoduced.setText(StoredValues.energyproducedthisyear);
                undergraphyearlyunitsperkwp.setText(StoredValues.unitsperkwpthisyear);

                barentryyearlygraph = barEntries;


                BarDataSet barDataSet = new BarDataSet(barEntries, "Months");
                barDataSet.setDrawValues(true);

                bardatayearly = new BarData(months,barDataSet);

                BarDataSet currentbarDataSet = new BarDataSet(barEntries, "Months");

                currentbarDataSet.setHighLightColor(Color.TRANSPARENT);
                currentbarDataSet.setHighLightAlpha(0);
                currentbarDataSet.setDrawValues(false);

                BarData barData = new BarData(months, currentbarDataSet);

                barChart.setData(barData);
                //barChart.setDescription(descr);
                barChart.setDescription(null);
                barChart.fitScreen();
                barChart.setScaleEnabled(false);
                barChart.setData(barData);
                barChart.getLegend().setEnabled(false);
                barChart.getAxisLeft().setGridColor(Color.parseColor("#ffffff"));
                barChart.getAxisRight().setGridColor(Color.parseColor("#ffffff"));
                barChart.getXAxis().setGridColor(Color.parseColor("#ffffff"));
                barChart.getXAxis().setDrawLabels(false);
                barChart.getAxisLeft().setDrawLabels(false);
                barChart.getAxisRight().setDrawLabels(false);




                createMonthBargraph(monthlygraph,"Monthly",Returncurrentdate());




            }

            @Override
            public void onFailure(Call<SummaryOfDay> call, Throwable t) {
                Toast.makeText(context,"Error occured, Could not get yearly values",Toast.LENGTH_SHORT).show();
            }
        });
    }



    int startingmonth(String startingdate)
    {
        Date startdate=null;
        try {
            startdate = new SimpleDateFormat("yyyy-MM-dd").parse(startingdate);
        }
        catch (Exception ex){

        }
        Calendar givenyear = Calendar.getInstance();
        givenyear.setTime(startdate);
        return givenyear.get(Calendar.MONTH);
    }







    ///   --------------------------------   lifetime value   --------------------------------




    /// ---------------------note---------------------

    // This function isn't even needed anymore since lifetime can be found directly from the call for the summary of the system

    /// ---------------------note---------------------


    public void getlifetimevalues(final Context context)
    {
        //final Call<LifetimeValues> lifetimeValues = SolarApi.getService().getLifetimeValues(MainActivity.returnapivalue("system_id",context),MainActivity.returnapivalue("apikey",context),MainActivity.returnapivalue("user_id",context));

        final Call<WeeklyValues> lifetimeValues = SolarApi.getService(context).getValuesofWeek(MainActivity.returnapivalue("system_id",context),MainActivity.returnapivalue("system_start_date",context),Returncurrentdate(),MainActivity.returnapivalue("apikey",context),MainActivity.returnapivalue("user_id",context));

        lifetimeValues.enqueue(new Callback<WeeklyValues>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<WeeklyValues> call, Response<WeeklyValues> response) {
                WeeklyValues lifetimeValues = response.body();

                //String systemstatus = lifetimeValues.getMeta().getStatus();

                List<Integer> Values = lifetimeValues.getProduction();

                Integer totallifetimevalue = 0;

                for(int k=0;k<Values.size();k++)
                {
                    totallifetimevalue+=Values.get(k);
                }


                /*
                String systemstartdate = MainActivity.returnapivalue("system_start_date",getContext());
                String todaysdate = returncurrentdate();
                int numberofdays = numberofdaysbetweentwodates(systemstartdate,todaysdate);

                 */
                int numberofdayslifetime = Values.size();

                //Toast.makeText(context,""+numberofdayslifetime,Toast.LENGTH_SHORT).show();




                Integer lk = totallifetimevalue / 1000;
                Integer lr = totallifetimevalue % 1000;
                String lifetimevaluestring = lk+"";// + "." + lr;              ////   energy produced during lifetime
                float lifetimeavgvalue = totallifetimevalue / (49.7f*numberofdayslifetime/**numberofdays*/);
                int tmka = (int) lifetimeavgvalue / 1000;
                int tmra = (int) lifetimeavgvalue % 1000;
                String lifetimeavgvaluestring = tmka + "." + tmra;         ////   units per kwp during lifetime
                //StoredValues.energyproducedliftime = lifetimevaluestring;
                //StoredValues.unitsperkwplifetime = lifetimeavgvaluestring;


                lifetimeenergy.setText(String.format("%.0f",((float)totallifetimevalue/ 1000)));
                lifetimeunitperkwp.setText(String.format("%.2f",lifetimeavgvalue));


                canpress=true;

                loadingcircle.setVisibility(View.GONE);

                Toast.makeText(NewMainActivity.this, "Updating completed...", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<WeeklyValues> call, Throwable t) {
                Toast.makeText(context,"Error occured, Could not get lifetime values",Toast.LENGTH_SHORT).show();
            }
        });
    }
















    ////   re-usable function for returning current date

    public String Returncurrentdate()
    {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String curdate = dateFormat.format(calendar.getTime());
        return curdate;
    }


}