package com.example.test1212.Fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.test1212.Activities.AboutActivity;
import com.example.test1212.BarChartOperations.frontpagedetails;
import com.example.test1212.BarChartOperations.yearlydetails;
import com.example.test1212.Activities.MainActivity;
import com.example.test1212.RequestedValues.PostData;
import com.example.test1212.R;
import com.example.test1212.RequestedValues.WeeklyValues;
import com.example.test1212.SolarApi;
import com.example.test1212.StoredValues;
import com.example.test1212.RequestedValues.SummaryOfDay;
import com.example.test1212.RequestedValues.System;
import com.example.test1212.UtilityClasses.JSONparserclass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StatusFragment extends Fragment {


    TextView currentdate;
    TextView energyproduced;
    TextView unitsperkwp;
    TextView sysstatus;

    TextView yesterdaydate;
    TextView energyproducedyesterday;
    TextView unitsperkwpyesterday;

    TextView thismonthdate;
    TextView energyproducedthismonth;
    TextView unitsperkwpthismonth;

    TextView lastmonthdate;
    TextView energyproducedlastmonth;
    TextView unitsperkwplastmonth;

    TextView energyproducedlifetime;
    TextView unitsperkwplifetime;

    TextView energyproducedthisyear;
    TextView unitsperkwpthisyear;

    TextView energyproducedlastyear;
    TextView unitsperkwplastyear;


    Button getdetails;
    Button changedate;

    FloatingActionButton updatebutton;

    // for current date

    Calendar calendar,calender;
    DateFormat dateFormat,dateformat;
    String date;


    String returnedvaluestatus,returnedenergyproducedlastyear,returnedunitsperkwplastyear,returnedenergyproducedthisyear,returnedunitsperkwpthisyear,returnedenergyproducedlifetime,returnedunitsperkwplifetime,returnedvalueenergyproduced,returnedvalueunitsperkwp,returnedenergyproducedyesterday,returnedunitsperkwpyesterday,returnedenergyproducedthismonth,returnedunitsperkwpthismonth,returnedenergyproducedlastmonth,returnedunitsperkwplastmonth;

    Boolean valuespresent;

    ColorStateList oldColors;



    Boolean energyupdatebuttonpressed;



    Boolean limitreached;



    ImageView aboutbutton;



    public static final int REQUEST_CODE = 11;    ///  Used to identify the result

    private OnFragmentInteractionListener mListener;

    public static StatusFragment newInstance() {
        StatusFragment fragment = new StatusFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public StatusFragment()
    {

    }

    public StatusFragment(String status,String energyproducedtoday,String unitsperkwptoday,String energyproducedyesterday,String unitsperkwpyesterday,String energyproducedthismonth,String unitsperkwpthismonth,String energyproducedlastmonth,String unitsperkwplastmonth,String energyproducedlifetime,String unitsperkwplifetime,String energyproducedthisyear,String unitsperkwpthisyear,String energyproducedlastyear,String unitsperkwplastyear)
    {
        if(status!=null && energyproducedtoday!=null && unitsperkwptoday!=null && energyproducedyesterday!=null && unitsperkwpyesterday!=null && energyproducedthismonth!=null && unitsperkwpthismonth!=null && energyproducedlastmonth!=null && unitsperkwplastmonth!=null && energyproducedlifetime!=null && unitsperkwplifetime!=null && energyproducedthisyear!=null && unitsperkwpthisyear!=null && energyproducedlastyear!=null && unitsperkwplastyear!=null)
        {
            returnedvaluestatus = status;
            returnedvalueenergyproduced = energyproducedtoday;            // today
            returnedvalueunitsperkwp = unitsperkwptoday;
            returnedenergyproducedyesterday = energyproducedyesterday;    // yesterday
            returnedunitsperkwpyesterday = unitsperkwpyesterday;
            returnedenergyproducedthismonth = energyproducedthismonth;    // this month
            returnedunitsperkwpthismonth = unitsperkwpthismonth;
            returnedenergyproducedlastmonth = energyproducedlastmonth;    // last month
            returnedunitsperkwplastmonth = unitsperkwplastmonth;
            returnedenergyproducedlifetime = energyproducedlifetime;      // lifetime
            returnedunitsperkwplifetime = unitsperkwplifetime;
            returnedenergyproducedthisyear = energyproducedthisyear;      // this year
            returnedunitsperkwpthisyear = unitsperkwpthisyear;
            returnedenergyproducedlastyear = energyproducedlastyear;      // last year
            returnedunitsperkwplastyear = unitsperkwplastyear;
            valuespresent = true;
        }
        else
        {
            valuespresent = false;
        }
        /*
        if(energyproducedtoday!=null)    // today
        {
            returnedvalueenergyproduced = energyproducedtoday;
            valuespresent = true;
        }
        else
        {
            valuespresent = false;
        }
        if(unitsperkwptoday!=null)
        {
            returnedvalueunitsperkwp = unitsperkwptoday;
            valuespresent = true;
        }
        else
        {
            valuespresent = false;
        }
        if(energyproducedyesterday!=null)    // yesterday
        {
            returnedenergyproducedyesterday = energyproducedyesterday;
            valuespresent = true;
        }
        else
        {
            valuespresent = false;
        }
        if(unitsperkwpyesterday!=null)
        {
            returnedunitsperkwpyesterday = unitsperkwpyesterday;
            valuespresent = true;
        }
        else
        {
            valuespresent = false;
        }
        if(energyproducedthismonth!=null)     //  this month
        {
            returnedenergyproducedthismonth = energyproducedthismonth;
            valuespresent = true;
        }
        else
        {
            valuespresent = false;
        }
        if(unitsperkwpthismonth!=null)
        {
            returnedunitsperkwpthismonth = unitsperkwpthismonth;
            valuespresent = true;
        }
        else
        {
            valuespresent = false;
        }
        if(energyproducedlastmonth!=null)     //  last month
        {
            returnedenergyproducedlastmonth = energyproducedlastmonth;
            valuespresent = true;
        }
        else
        {
            valuespresent = false;
        }
        if(unitsperkwplastmonth!=null)
        {
            returnedunitsperkwplastmonth = unitsperkwplastmonth;
            valuespresent = true;
        }
        else
        {
            valuespresent = false;
        }
        if(energyproducedlifetime!=null)      //   lifetime
        {
            returnedenergyproducedlifetime = energyproducedlifetime;
            valuespresent = true;
        }
        else
        {
            valuespresent = false;
        }
        if(unitsperkwplifetime!=null)
        {
            returnedunitsperkwplifetime = unitsperkwplifetime;
            valuespresent=true;
        }
        else
        {
            valuespresent = false;
        }
        if(energyproducedthisyear!=null)      //   this year
        {
            returnedenergyproducedthisyear = energyproducedthisyear;
            valuespresent = true;
        }
        else
        {
            valuespresent = false;
        }
        if(unitsperkwpthisyear!=null)
        {
            returnedunitsperkwpthisyear = unitsperkwpthisyear;
            valuespresent = true;
        }
        else
        {
            valuespresent = false;
        }
        if(energyproducedlastyear!=null)      //   last year
        {
            returnedenergyproducedlastyear = energyproducedlastyear;
            valuespresent = true;
        }
        else
        {
            valuespresent = false;
        }
        if(unitsperkwplastyear!=null)
        {
            returnedunitsperkwplastyear = unitsperkwplastyear;
            valuespresent = true;
        }
        else
        {
            valuespresent = false;
        }*/
    }


    public String returncurrentdate()
    {
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String curdate = dateFormat.format(calendar.getTime());
        return curdate;
    }


    public String returntoday()
    {
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String curdate = dateFormat.format(calendar.getTime());
        return curdate;
    }


    public String returnyesterday()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String curdate = dateFormat.format(cal.getTime());
        return curdate;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check for the results
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // get date from string
            String selectedDate = data.getStringExtra("selectedDate");
            // set the values
            currentdate.setText("Details for  "+selectedDate+" :");
            sysstatus.setText("Retrieving system status....");
            energyproduced.setText("Retrieving details....");
            unitsperkwp.setText("Retrieving details....");
            sysstatus.setTextColor(oldColors);
            energyproduced.setTextColor(oldColors);
            unitsperkwp.setTextColor(oldColors);
            sysstatus.setTextSize(14);
            energyproduced.setTextSize(14);
            unitsperkwp.setTextSize(14);
            average_power_for_selected_day(getContext(),selectedDate);
            //getdata(getContext());
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } /*else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_status,container,false);

        limitreached = false;

        energyupdatebuttonpressed = false;

        currentdate = v.findViewById(R.id.currentdate);
        energyproduced = v.findViewById(R.id.energyproduced);
        unitsperkwp = v.findViewById(R.id.unitsperkwp);
        sysstatus = v.findViewById(R.id.sysstatus);

        yesterdaydate = v.findViewById(R.id.yesterdaydate);
        energyproducedyesterday = v.findViewById(R.id.energyproducedyesterday);
        unitsperkwpyesterday = v.findViewById(R.id.unitsperkwpyesterday);

        thismonthdate = v.findViewById(R.id.thismonth);
        energyproducedthismonth = v.findViewById(R.id.energyproducedthismonth);
        unitsperkwpthismonth = v.findViewById(R.id.unitsperkwpthismonth);

        lastmonthdate = v.findViewById(R.id.lastmonth);
        energyproducedlastmonth = v.findViewById(R.id.energyproducedlastmonth);
        unitsperkwplastmonth = v.findViewById(R.id.unitsperkwplastmonth);

        energyproducedlifetime = v.findViewById(R.id.energyproducedlifetime);
        unitsperkwplifetime = v.findViewById(R.id.unitsperkwplifetime);

        energyproducedthisyear = v.findViewById(R.id.energyproducedthisyear);
        unitsperkwpthisyear = v.findViewById(R.id.unitsperkwpthisyear);

        energyproducedlastyear = v.findViewById(R.id.energyproducedlastyear);
        unitsperkwplastyear = v.findViewById(R.id.unitsperkwplastyear);

        //getdetails = v.findViewById(R.id.getbutton);
        //changedate = v.findViewById(R.id.datebutton);

        updatebutton = v.findViewById(R.id.floatingupdatebutton);


        aboutbutton = v.findViewById(R.id.about_button);

        calender = Calendar.getInstance();
        dateformat = new SimpleDateFormat("dd-MM-yyyy");
        date = dateformat.format(calender.getTime());
        dateformat = new SimpleDateFormat("yyyy-dd-MM");

        //currentdate.setText("Details for  "+date+" :");
        frontpagedetails.todayandyesterday(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
        currentdate.setText("Details for "+returntoday()+":");
        yesterdaydate.setText("Details for "+returnyesterday()+":");
        StoredValues.Todaysdate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        StoredValues.Yesterdaysdate = frontpagedetails.yesterday;

        frontpagedetails.daysoftwomonths(StoredValues.Todaysdate);

        oldColors = energyproduced.getTextColors();

        //updatebutton.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5904A0")));

        updatebutton.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0BA8DA")));

        final FragmentManager fm = ((AppCompatActivity)getActivity()).getSupportFragmentManager();

        if(valuespresent)
        {
            sysstatus.setText(returnedvaluestatus);
            sysstatus.setTextColor(Color.parseColor("#2AE016"));

            energyproduced.setText(returnedvalueenergyproduced);
            energyproduced.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            energyproduced.setTextColor(Color.parseColor("#000000"));               ///    for today
            unitsperkwp.setText(returnedvalueunitsperkwp);
            //unitsperkwp.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            unitsperkwp.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            unitsperkwp.setTextColor(Color.parseColor("#000000"));


            energyproducedyesterday.setText(returnedenergyproducedyesterday);
            energyproducedyesterday.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            energyproducedyesterday.setTextColor(Color.parseColor("#000000"));               ///    for yesterday
            unitsperkwpyesterday.setText(returnedunitsperkwpyesterday);
            unitsperkwpyesterday.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            unitsperkwpyesterday.setTextColor(Color.parseColor("#000000"));


            energyproducedthismonth.setText(returnedenergyproducedthismonth);
            energyproducedthismonth.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            energyproducedthismonth.setTextColor(Color.parseColor("#000000"));               ///    for this month
            unitsperkwpthismonth.setText(returnedunitsperkwpthismonth);
            unitsperkwpthismonth.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            unitsperkwpthismonth.setTextColor(Color.parseColor("#000000"));


            energyproducedlastmonth.setText(returnedenergyproducedlastmonth);
            energyproducedlastmonth.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            energyproducedlastmonth.setTextColor(Color.parseColor("#000000"));               ///    for last month
            unitsperkwplastmonth.setText(returnedunitsperkwplastmonth);
            unitsperkwplastmonth.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            unitsperkwplastmonth.setTextColor(Color.parseColor("#000000"));


            energyproducedthisyear.setText(returnedenergyproducedthisyear);
            energyproducedthisyear.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            energyproducedthisyear.setTextColor(Color.parseColor("#000000"));                ///    this year
            unitsperkwpthisyear.setText(returnedunitsperkwpthisyear);
            unitsperkwpthisyear.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            unitsperkwpthisyear.setTextColor(Color.parseColor("#000000"));


            energyproducedlastyear.setText(returnedenergyproducedlastyear);
            energyproducedlastyear.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            energyproducedlastyear.setTextColor(Color.parseColor("#000000"));                ///    last year
            unitsperkwplastyear.setText(returnedunitsperkwplastyear);
            unitsperkwplastyear.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            unitsperkwplastyear.setTextColor(Color.parseColor("#000000"));


            energyproducedlifetime.setText(returnedenergyproducedlifetime);
            energyproducedlifetime.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            energyproducedlifetime.setTextColor(Color.parseColor("#000000"));                ///    for lifetime
            unitsperkwplifetime.setText(returnedunitsperkwplifetime);
            unitsperkwplifetime.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            unitsperkwplifetime.setTextColor(Color.parseColor("#000000"));

        }



        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(energyupdatebuttonpressed==false)
                {
                    energyupdatebuttonpressed = true;

                    sysstatus.setTextColor(oldColors);
                    sysstatus.setText("Retrieving system status....");



                    energyproduced.setText("Retrieving details....");
                    unitsperkwp.setText("Retrieving details....");
                    energyproduced.setTextColor(oldColors);                         ///  for curent day
                    unitsperkwp.setTextColor(oldColors);
                    energyproduced.setTextSize(14);
                    unitsperkwp.setTextSize(14);



                    energyproducedyesterday.setText("Retrieving details....");
                    unitsperkwpyesterday.setText("Retrieving details....");
                    energyproducedyesterday.setTextColor(oldColors);                          ///  for yesterday
                    unitsperkwpyesterday.setTextColor(oldColors);
                    energyproducedyesterday.setTextSize(14);
                    unitsperkwpyesterday.setTextSize(14);



                    energyproducedthismonth.setText("Retrieving details....");
                    unitsperkwpthismonth.setText("Retrieving details....");
                    energyproducedthismonth.setTextColor(oldColors);                          ///  for this month
                    unitsperkwpthismonth.setTextColor(oldColors);
                    energyproducedthismonth.setTextSize(14);
                    unitsperkwpthismonth.setTextSize(14);



                    energyproducedlastmonth.setText("Retrieving details....");
                    unitsperkwplastmonth.setText("Retrieving details....");
                    energyproducedlastmonth.setTextColor(oldColors);                         ///  for last month
                    unitsperkwplastmonth.setTextColor(oldColors);
                    energyproducedlastmonth.setTextSize(14);
                    unitsperkwplastmonth.setTextSize(14);


                    energyproducedthisyear.setText("Retrieving details....");
                    unitsperkwpthisyear.setText("Retrieving details....");
                    energyproducedthisyear.setTextColor(oldColors);                         ///  this year
                    unitsperkwpthisyear.setTextColor(oldColors);
                    energyproducedthisyear.setTextSize(14);
                    unitsperkwpthisyear.setTextSize(14);


                    energyproducedlastyear.setText("Retrieving details....");
                    unitsperkwplastyear.setText("Retrieving details....");
                    energyproducedlastyear.setTextColor(oldColors);                         ///  last year
                    unitsperkwplastyear.setTextColor(oldColors);
                    energyproducedlastyear.setTextSize(14);
                    unitsperkwplastyear.setTextSize(14);


                    energyproducedlifetime.setText("Retrieving details....");
                    unitsperkwplifetime.setText("Retrieving details....");
                    energyproducedlifetime.setTextColor(oldColors);                         ///  for lifetime
                    unitsperkwplifetime.setTextColor(oldColors);
                    energyproducedlifetime.setTextSize(14);
                    unitsperkwplifetime.setTextSize(14);




                    for_today(getContext());
                    //getvaluesfortwomonths(getContext(),frontpagedetails.firstmonth.get(0),StoredValues.Todaysdate);
                }

            }
        });


        aboutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent about = new Intent(getContext(), AboutActivity.class);
                startActivity(about);
            }
        });


        ///   UNCOMMENT THIS FOR THOSE TWO BUTTONS IF THEY WILL BE USED


        /*getdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentdate.setText("Details for "+returncurrentdate()+" :");
                Toast.makeText(getContext(),"Updating energy produced today....",Toast.LENGTH_SHORT).show();
                sysstatus.setText("Retrieving system status....");
                energyproduced.setText("Retrieving details....");
                unitsperkwp.setText("Retrieving details....");
                sysstatus.setTextColor(oldColors);
                energyproduced.setTextColor(oldColors);
                unitsperkwp.setTextColor(oldColors);
                //sysstatus.setTextSize(14);
                energyproduced.setTextSize(14);
                unitsperkwp.setTextSize(14);
                average_power_per_day(getContext());
                getdata(getContext());
            }
        });*/



        ///   UNCOMMENT THIS FOR THOSE TWO BUTTONS IF THEY WILL BE USED



        /*changedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDialogFragment newFragment = new DatePickerFragment();
                // set the targetFragment to receive the results, specifying the request code
                newFragment.setTargetFragment(StatusFragment.this,REQUEST_CODE);
                // show the datePicker
                newFragment.show(fm, "datePicker");
            }
        });*/

        //average_power_per_day(this.getContext());
        //getdata(this.getContext());




        return v;

    }






    /// ---------------------note---------------------

    // This function isn't even needed anymore since lifetime can be found directly from the call for the summary of the system

    /// ---------------------note---------------------

    public void getlifetimevalues(final Context context)
    {
        //final Call<LifetimeValues> lifetimeValues = SolarApi.getService().getLifetimeValues(MainActivity.returnapivalue("system_id",context),MainActivity.returnapivalue("apikey",context),MainActivity.returnapivalue("user_id",context));

        final Call<WeeklyValues> lifetimeValues = SolarApi.getService(context).getValuesofWeek(MainActivity.returnapivalue("system_id",context),MainActivity.returnapivalue("system_start_date",context),returncurrentdate(),MainActivity.returnapivalue("apikey",context),MainActivity.returnapivalue("user_id",context));

        lifetimeValues.enqueue(new Callback<WeeklyValues>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<WeeklyValues> call, Response<WeeklyValues> response) {
                WeeklyValues lifetimeValues = response.body();

                String systemstatus = lifetimeValues.getMeta().getStatus();

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
                String lifetimevaluestring = lk + "." + lr;              ////   energy produced during lifetime
                //String lifetimevaluestring = String.format("%.0f",(totallifetimevalue/1000));
                float lifetimeavgvalue = totallifetimevalue / (49.7f*numberofdayslifetime/**numberofdays*/);
                int tmka = (int) lifetimeavgvalue / 1000;
                int tmra = (int) lifetimeavgvalue % 1000;
                String lifetimeavgvaluestring = tmka + "." + tmra;         ////   units per kwp during lifetime
                //String lifetimeavgvaluestring = String.format("%.2f",(lifetimeavgvalue/1000));
                StoredValues.energyproducedliftime = lifetimevaluestring;
                StoredValues.unitsperkwplifetime = lifetimeavgvaluestring;



                if(systemstatus.contentEquals("normal"))
                {
                    sysstatus.setTextColor(Color.parseColor("#2AE016"));
                }

                sysstatus.setText("System status: "+systemstatus);
                StoredValues.SystemStatus ="System status: "+systemstatus;

                energyproducedlifetime.setText(StoredValues.energyproducedliftime);
                unitsperkwplifetime.setText(StoredValues.unitsperkwplifetime);
                energyproducedlifetime.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                energyproducedlifetime.setTextColor(Color.parseColor("#000000"));
                unitsperkwplifetime.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                unitsperkwplifetime.setTextColor(Color.parseColor("#000000"));


            }

            @Override
            public void onFailure(Call<WeeklyValues> call, Throwable t) {
                Snackbar.make(getView(),"Error in receiving values please check internet connection",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
            }
        });
    }



    public void getvaluesfortwoyears(final Context context)
    {
        int thisyear = Calendar.getInstance().get(Calendar.YEAR);
        final int lastyear = thisyear-1;

        createrequestforyear(thisyear);
        createrequestforyear(lastyear);

    }


    public void createrequestforyear(int year)
    {
        String startdate,endate;
        if(year == Integer.parseInt(MainActivity.returnapivalue("system_start_year",getContext())))    ///    for starting year of system
        {
            ArrayList<String> yeardates = yearlydetails.datesofyear(year);

            JSONparserclass parser = new JSONparserclass();

            startdate = parser.returnapivalue("system_start_date",getContext());
            endate = yeardates.get(yeardates.size()-2);

            //Toast.makeText(getContext(), "startdate:"+startdate+",endate:"+endate, Toast.LENGTH_SHORT);//.show();

            int numberofdays = findnumberofdaysforyear(yeardates,startdate,endate);

            getyearlyvalues(getContext(),startdate,endate,yeardates,year,numberofdays);
        }
        else if(year==Calendar.getInstance().get(Calendar.YEAR))       ///      for current year
        {
            ArrayList<String> yeardates = yearlydetails.datesofyear(year);

            startdate = yeardates.get(0);
            endate = returncurrentdate();

            int numberofdays = findnumberofdaysforyear(yeardates,startdate,endate);

            getyearlyvalues(getContext(),startdate,endate,yeardates,year,numberofdays);
        }
        else      ///     any other year
        {
            ArrayList<String> yeardates = yearlydetails.datesofyear(year);

            startdate = yeardates.get(0);
            endate = yeardates.get(yeardates.size()-2);

            int numberofdays = findnumberofdaysforyear(yeardates,startdate,endate);

            getyearlyvalues(getContext(),startdate,endate,yeardates,year,numberofdays);
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



    public void getyearlyvalues(final Context context, final String startdate, final String enddate, final ArrayList<String> yeardates, final int year, final int numberofdays)
    {

        if(year==Calendar.getInstance().get(Calendar.YEAR)-1 && StoredValues.energyproducedlastyear!=null && StoredValues.unitsperkwplastyear!=null)
        {
            energyproducedlastyear.setText(StoredValues.energyproducedlastyear);
            unitsperkwplastyear.setText(StoredValues.unitsperkwplastyear);
            energyproducedlastyear.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            energyproducedlastyear.setTextColor(Color.parseColor("#000000"));
            unitsperkwplastyear.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            unitsperkwplastyear.setTextColor(Color.parseColor("#000000"));
            return;
        }

        final Call<WeeklyValues> yearlyValues = SolarApi.getService(context).getValuesofWeek(MainActivity.returnapivalue("system_id",context),startdate,enddate,MainActivity.returnapivalue("apikey",context),MainActivity.returnapivalue("user_id",context));


        yearlyValues.enqueue(new Callback<WeeklyValues>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<WeeklyValues> call, Response<WeeklyValues> response) {

                //Toast.makeText(context,"year:"+year+" startdate:"+startdate+" enddate:"+enddate,Toast.LENGTH_LONG);//.show();

                if(response.body() instanceof WeeklyValues) {


                    WeeklyValues values = response.body();

                    float totalvalue = 0;

                    List<Integer> valuesforgivenyear = values.getProduction();

                    String[] arrayofmonths = {"Jan","Feb","March","April","May","June","July","Aug","Sep","Oct","Nov","Dec"};

                    ArrayList<String> months = converttoarrayliststring(arrayofmonths);

                    ArrayList<Float> valuesforeachmonthinyear = converttoarraylistfloat(new float[]{0,0,0,0,0,0,0,0,0,0,0,0});

                    float totalformonth=0;

                    int i=0;

                    int j=startingmonth(startdate);


                    for (String date : yeardates)         // using yeardates separate the values obtained into each month dividing by ---
                    {


                        if (date == "---") {
                            try {
                                valuesforeachmonthinyear.set(j, totalformonth);

                                j += 1;
                                totalvalue += totalformonth;
                                totalformonth = 0;
                            } catch (Exception ex) {
                                Log.println(Log.ASSERT, "value of j", "j=" + j);
                            }
                        } else {
                            try {
                                totalformonth += valuesforgivenyear.get(i) / 1000f;
                            } catch (Exception ex) {
                                Log.println(Log.ASSERT, "value of i", "i=" + i);
                                Log.println(Log.ASSERT, "during adding values", ex.getMessage());
                            }
                            i += 1;
                        }

                    }

                    //int numberofdays = numberofdaysbetweentwodates(startdate,enddate);


                    if(year==Calendar.getInstance().get(Calendar.YEAR))
                    {
                        /// set for current year
/*
                        Integer tyk = totalvalue / 1000;
                        Integer tyr = totalvalue % 1000;
                        String thisyearvaluestring = tyk + "." + tyr;              ////   energy produced this year
                        float thisyearavgvalue = totalvalue / 49.7f;
                        int tyka = (int) thisyearavgvalue / 1000;
                        int tyra = (int) thisyearavgvalue % 1000;
                        String thismonthavgvaluestring = tyka + "." + tyra;         ////   units per kwp this year


 */

                        float curmonthvalue = valuesforeachmonthinyear.get(--j);
                        curmonthvalue+=Float.parseFloat(energyproduced.getText().toString());
                        valuesforeachmonthinyear.set(j,curmonthvalue);
                        totalvalue+=curmonthvalue;


                        StoredValues.energyproducedthisyear =""+String.format("%.0f",totalvalue);
                        StoredValues.unitsperkwpthisyear =""+String.format("%.2f",(totalvalue / (49.7f*numberofdays/**numberofdays*/)));
                        //StoredValues.energyproducedthisyear = ""+String.format("%.0f",totalvalue);
                        //StoredValues.unitsperkwpthisyear = ""+String.format("%.2f",(totalvalue / (49.7f*numberofdays/**numberofdays*/)));


                        energyproducedthisyear.setText(StoredValues.energyproducedthisyear);
                        unitsperkwpthisyear.setText(StoredValues.unitsperkwpthisyear);
                        energyproducedthisyear.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                        energyproducedthisyear.setTextColor(Color.parseColor("#000000"));
                        unitsperkwpthisyear.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                        unitsperkwpthisyear.setTextColor(Color.parseColor("#000000"));


                    }
                    else if (year==Calendar.getInstance().get(Calendar.YEAR)-1 && StoredValues.energyproducedlastyear==null)
                    {
                        /// set for last year
/*
                        Integer lyk = totalvalue / 1000;
                        Integer lyr = totalvalue % 1000;
                        String lastyearvaluestring = lyk + "." + lyr;              ////   energy produced last year
                        float lastyearavgvalue = totalvalue / 49.7f;
                        int tyka = (int) lastyearavgvalue / 1000;
                        int tyra = (int) lastyearavgvalue % 1000;
                        String thismonthavgvaluestring = tyka + "." + tyra;         ////   units per kwp last year

 */

                        StoredValues.energyproducedlastyear =""+String.format("%.0f",totalvalue);;
                        StoredValues.unitsperkwplastyear =""+String.format("%.2f",(totalvalue / (49.7f*numberofdays/**numberofdays*/)));
                        //StoredValues.energyproducedlastyear =""+String.format("%.0f",totalvalue);
                        //StoredValues.unitsperkwplastyear =""+String.format("%.2f",(totalvalue / (49.7f*numberofdays/**numberofdays*/)));


                        energyproducedlastyear.setText(StoredValues.energyproducedlastyear);
                        unitsperkwplastyear.setText(StoredValues.unitsperkwplastyear);
                        energyproducedlastyear.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                        energyproducedlastyear.setTextColor(Color.parseColor("#000000"));
                        unitsperkwplastyear.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                        unitsperkwplastyear.setTextColor(Color.parseColor("#000000"));
                    }



                    /*
                    energyproducedthisyear.setText(StoredValues.energyproducedthisyear);
                    unitsperkwpthisyear.setText(StoredValues.unitsperkwpthisyear);
                    energyproducedthisyear.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                    energyproducedthisyear.setTextColor(Color.parseColor("#000000"));
                    unitsperkwpthisyear.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                    unitsperkwpthisyear.setTextColor(Color.parseColor("#000000"));



                    energyproducedlastyear.setText(StoredValues.energyproducedlastyear);
                    unitsperkwplastyear.setText(StoredValues.unitsperkwplastyear);
                    energyproducedlastyear.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                    energyproducedlastyear.setTextColor(Color.parseColor("#000000"));
                    unitsperkwplastyear.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                    unitsperkwplastyear.setTextColor(Color.parseColor("#000000"));


                     */


               }
               else{

                    //Toast.makeText(context,"For the year "+year+" startdate:"+startdate+" enddate:"+enddate,Toast.LENGTH_SHORT).show();

                    energyupdatebuttonpressed = false;
                    Toast.makeText(context,"Could not get yearly value. Please check your internet connection and try again",Toast.LENGTH_SHORT).show();

                   /* Dialog dialog = new Dialog(getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.noresponsedialog_design);

                    TextView noresponsetext = dialog.findViewById(R.id.noresponsetext);
                    noresponsetext.setText("Data recieved is not the right type");

                    dialog.show();*/
                    return;
                }



            }

            @Override
            public void onFailure(Call<WeeklyValues> call, Throwable t) {
                energyupdatebuttonpressed = false;
                Toast.makeText(context,"Could not get yearly values. Please check your internet connection and try again",Toast.LENGTH_SHORT).show();
            }
        });
    }



    ArrayList<Float> converttoarraylistfloat(float arr[])
    {
        ArrayList<Float> array_list = new ArrayList<Float>();

        for(int i=0;i<arr.length;i++)
        {
            array_list.add(arr[i]);
        }

        return array_list;
    }


    ArrayList<String> converttoarrayliststring(String arr[])
    {
        ArrayList<String> array_list = new ArrayList<String>();

        for(int i=0;i<arr.length;i++)
        {
            array_list.add(arr[i]);
        }

        return array_list;
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





    public void getvaluesfortwomonths(final Context context,String startdate, String enddate)
    {
        //for_today(context);


        //Snackbar.make(getView(),"Updating page with latest data....",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
        final Call<WeeklyValues> monthlyValues = SolarApi.getService(context).getValuesofWeek(MainActivity.returnapivalue("system_id",context),startdate,enddate,MainActivity.returnapivalue("apikey",context),MainActivity.returnapivalue("user_id",context));

        monthlyValues.enqueue(new Callback<WeeklyValues>() {
            @Override
            public void onResponse(Call<WeeklyValues> call, Response<WeeklyValues> response) {
                if(response.body() instanceof WeeklyValues)
                {
                    limitreached = false;
                    //float firstmonthsize = frontpagedetails.firstmonth.size();
                    int firstmonthsize = frontpagedetails.firstmonth.size();

                    WeeklyValues ValuesTM = response.body();

                    List<Integer> ValuesofTwoMonths = ValuesTM.getProduction();

                    
                    //float totalsizeoftwomonths = ValuesofTwoMonths.size();
                    int totalsizeoftwomonths = ValuesofTwoMonths.size();

                    for (int j=0;j< ValuesofTwoMonths.size();j++) {
                        Log.println(Log.ASSERT,"j="+j,""+ValuesofTwoMonths.get(j));
                    }

                    Integer sumoflastmonth = 0;
                    int i=0;

                    try {
                        for (i = 0; i < firstmonthsize; i+=1) {
                            try {
                                sumoflastmonth += (ValuesofTwoMonths.get(i));
                            }
                            catch (Exception ex)
                            {
                                Log.e("Message thrown:",ex.getMessage());
                                limitreached = true;
                                break;
                            }
                         }
                        if (limitreached)
                        {
                            Log.println(Log.INFO,"Had to decrease i","Decreased i");
                            i=i-2;
                            //sumoflastmonth=sumoflastmonth-(ValuesofTwoMonths.get(i-1)+ValuesofTwoMonths.get(i-2));
                            limitreached = false;
                        }

                        if(StoredValues.energyproducedlastmonth==null && StoredValues.unitsperkwplastmonth==null) {
                            Integer lmk = sumoflastmonth / 1000;
                            Integer lmr = sumoflastmonth % 1000;
                            //String lastmonthvaluestring = lmk + "." + lmr;              ////  energy produced last month
                            String lastmonthvaluestring = String.format("%.0f",((float)sumoflastmonth/1000));
                            float lastmonthavgvalue = sumoflastmonth / (49.7f*firstmonthsize);
                            int lmka = (int) lastmonthavgvalue / 1000;
                            int lmra = (int) lastmonthavgvalue % 1000;
                            //String lastmonthavgvaluestring = lmka + "." + lmra;         ////   units per kwp last month
                            String lastmonthavgvaluestring = String.format("%.2f",((float)lastmonthavgvalue)/1000);
                            StoredValues.energyproducedlastmonth = lastmonthvaluestring;
                            StoredValues.unitsperkwplastmonth = lastmonthavgvaluestring;
                            Log.println(Log.INFO, "First month", "First month done i=" + i);
                        }


                        Integer sumofthismonth = 0;
                        if(StoredValues.energyproducedlastmonth==null && StoredValues.unitsperkwplastmonth==null){

                            for (; i < totalsizeoftwomonths; i++) {
                                    try {
                                        sumofthismonth += (ValuesofTwoMonths.get(i));
                                    } catch (Exception ex) {
                                  Log.println(Log.ASSERT, "Caught exception:", ex.getMessage().toString());
                                   }
                                }
                        }
                        else{

                            int j = frontpagedetails.firstmonth.size();
                            Log.println(Log.INFO,"value of j","j="+j);
                            Log.println(Log.INFO,"total length","length="+totalsizeoftwomonths);
                            for (j = frontpagedetails.firstmonth.size(); j < totalsizeoftwomonths; j++) {
                                try {
                                    sumofthismonth += (ValuesofTwoMonths.get(j));
                                } catch (Exception ex) {
                                    Log.println(Log.ASSERT, "Caught exception:", ex.getMessage().toString());
                                }
                            }

                        }

                        //for (/*int i = frontpagedetails.firstmonth.size()*/; i < totalsizeoftwomonths; i++) {
                        //    try {
                        //        sumofthismonth += (ValuesofTwoMonths.get(i));
                        //    } catch (Exception ex) {
                        //   Log.println(Log.ASSERT, "Caught exception:", ex.getMessage().toString());
                        //    }
                        //}

                        sumofthismonth+=(int)(1000*Float.parseFloat(energyproduced.getText().toString()));

                        int secondmonthsize = totalsizeoftwomonths-firstmonthsize;

                        Integer tmk = sumofthismonth / 1000;
                        Integer tmr = sumofthismonth % 1000;


                        //String thismonthvaluestring = tmk + "." + tmr;              ////   energy produced this month
                        String thismonthvaluestring = String.format("%.0f",((float)sumofthismonth/1000));
                        float thismonthavgvalue = sumofthismonth / (49.7f*secondmonthsize);
                        int tmka = (int) thismonthavgvalue / 1000;
                        int tmra = (int) thismonthavgvalue % 1000;
                        //String thismonthavgvaluestring = tmka + "." + tmra;         ////   units per kwp this month
                        String thismonthavgvaluestring = String.format("%.2f",((float)thismonthavgvalue/1000));
                        StoredValues.energyproducedthismonth = thismonthvaluestring;
                        StoredValues.unitsperkwpthismonth = thismonthavgvaluestring;
                        Log.println(Log.INFO,"Second Month","Second month done i="+i);


                    //    float todaysvalue = (float) (ValuesofTwoMonths.get(ValuesofTwoMonths.size() - 2)) / 1000.0f;
                    //    Integer tk = ValuesofTwoMonths.get(ValuesofTwoMonths.size() - 2) / 1000;
                    //    Integer tr = ValuesofTwoMonths.get(ValuesofTwoMonths.size() - 2) % 1000;
                    //    String todaysvaluestring = tk + "." + tr;                                              ////   energy produced today
                    //    float todayavgvalue = ValuesofTwoMonths.get(ValuesofTwoMonths.size() - 2) / 49.7f;
                    //    int tka = (int) todayavgvalue / 1000;
                    //    int tra = (int) todayavgvalue % 1000;
                    //    String todaysavgvaluestring = tka + "." + tra;                                     ////   units per kwp today
                    //    StoredValues.energyproducedtoday = todaysvaluestring;
                    //    StoredValues.unitsperkwptoday = todaysavgvaluestring;



                        if(StoredValues.energyproducedyesterday==null && StoredValues.unitsperkwpyesterday==null) {
                            float yesterdaysvalue = (float) (ValuesofTwoMonths.get(ValuesofTwoMonths.size() - 1)) / 1000.0f;
                            Integer yk = ValuesofTwoMonths.get(ValuesofTwoMonths.size() - 1) / 1000;
                            Integer yr = ValuesofTwoMonths.get(ValuesofTwoMonths.size() - 1) % 1000;
                            //String yesterdaysvaluestring = yk + "." + yr;                                           ////   energy produced yesterday
                            String yesterdaysvaluestring = String.format("%.0f",(yesterdaysvalue/1000));
                            float yesterdayavgvalue = ValuesofTwoMonths.get(ValuesofTwoMonths.size() - 1) / 49.7f;
                            int yka = (int) yesterdayavgvalue / 1000;
                            int yra = (int) yesterdayavgvalue % 1000;
                            //String yesterdaysavgvaluestring = yka + "." + yra;                                  ////   units per kwp yesterday
                            String yesterdaysavgvaluestring = String.format("%.2f",(yesterdayavgvalue/1000));
                            StoredValues.energyproducedyesterday = yesterdaysvaluestring;
                            StoredValues.unitsperkwpyesterday = yesterdaysavgvaluestring;
                        }



                     //   energyproduced.setText(StoredValues.energyproducedtoday);
                     //   unitsperkwp.setText(StoredValues.unitsperkwptoday);
                     //   energyproduced.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                     //   energyproduced.setTextColor(Color.parseColor("#000000"));
                     //   unitsperkwp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                     //   unitsperkwp.setTextColor(Color.parseColor("#000000"));




                        energyproducedyesterday.setText(StoredValues.energyproducedyesterday);
                        unitsperkwpyesterday.setText(StoredValues.unitsperkwpyesterday);
                        energyproducedyesterday.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                        energyproducedyesterday.setTextColor(Color.parseColor("#000000"));
                        unitsperkwpyesterday.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                        unitsperkwpyesterday.setTextColor(Color.parseColor("#000000"));


                        energyproducedthismonth.setText(StoredValues.energyproducedthismonth);
                        unitsperkwpthismonth.setText(StoredValues.unitsperkwpthismonth);
                        energyproducedthismonth.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                        energyproducedthismonth.setTextColor(Color.parseColor("#000000"));
                        unitsperkwpthismonth.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                        unitsperkwpthismonth.setTextColor(Color.parseColor("#000000"));


                        energyproducedlastmonth.setText(StoredValues.energyproducedlastmonth);
                        unitsperkwplastmonth.setText(StoredValues.unitsperkwplastmonth);
                        energyproducedlastmonth.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                        energyproducedlastmonth.setTextColor(Color.parseColor("#000000"));
                        unitsperkwplastmonth.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                        unitsperkwplastmonth.setTextColor(Color.parseColor("#000000"));


                        //energyupdatebuttonpressed = false;
                    }
                    catch(Exception ex)
                    {
                        Log.e("exception thrown",ex.getMessage());
                        Snackbar.make(getView(),"An error occured. Please check your net connection and try again later...",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
                        energyupdatebuttonpressed = false;

                        sysstatus.setTextColor(oldColors);
                        sysstatus.setText("Status currently unknown....");



                        energyproduced.setText("No data....");
                        unitsperkwp.setText("No data....");
                        energyproduced.setTextColor(oldColors);                         ///  for curent day
                        unitsperkwp.setTextColor(oldColors);
                        energyproduced.setTextSize(14);
                        unitsperkwp.setTextSize(14);



                        energyproducedyesterday.setText("No data....");
                        unitsperkwpyesterday.setText("No data....");
                        energyproducedyesterday.setTextColor(oldColors);                          ///  for yesterday
                        unitsperkwpyesterday.setTextColor(oldColors);
                        energyproducedyesterday.setTextSize(14);
                        unitsperkwpyesterday.setTextSize(14);



                        energyproducedthismonth.setText("No data....");
                        unitsperkwpthismonth.setText("No data....");
                        energyproducedthismonth.setTextColor(oldColors);                          ///  for this month
                        unitsperkwpthismonth.setTextColor(oldColors);
                        energyproducedthismonth.setTextSize(14);
                        unitsperkwpthismonth.setTextSize(14);



                        energyproducedlastmonth.setText("No data....");
                        unitsperkwplastmonth.setText("No data....");
                        energyproducedlastmonth.setTextColor(oldColors);                         ///  for last month
                        unitsperkwplastmonth.setTextColor(oldColors);
                        energyproducedlastmonth.setTextSize(14);
                        unitsperkwplastmonth.setTextSize(14);
                    }

                }
                else
                {
                    energyupdatebuttonpressed = false;
                    Toast.makeText(context,"Could not get monthly value. Please check your internet connection and try again",Toast.LENGTH_SHORT).show();
                    /*Dialog dialog = new Dialog(getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.noresponsedialog_design);

                    TextView noresponsetext = dialog.findViewById(R.id.noresponsetext);
                    noresponsetext.setText("Error, the recieved data is not the right type");
                    dialog.show();*/
                    return;
                }
            }

            @Override
            public void onFailure(Call<WeeklyValues> call, Throwable t) {
                energyupdatebuttonpressed = false;
                Toast.makeText(context,"Could not get monthly value. Please check your internet connection and try again",Toast.LENGTH_SHORT).show();
            }
        });
        //getdata(context);

        //getlifetimevalues(context);
        getvaluesfortwoyears(context);
    }



    private void average_power_for_selected_day(final Context context,String date)               //////   for the selected day
    {

        Call<SummaryOfDay> summaryOfDay = SolarApi.getService(context).getSummaryOfToday(MainActivity.returnapivalue("system_id",context),date,MainActivity.returnapivalue("apikey",context),MainActivity.returnapivalue("user_id",context));
        summaryOfDay.enqueue(new Callback<SummaryOfDay>() {
            @Override
            public void onResponse(Call<SummaryOfDay> call, Response<SummaryOfDay> response) {

                if(response.body() instanceof SummaryOfDay) {

                    SummaryOfDay today = response.body();

                    ArrayList<String> datalist = new ArrayList<String>();

                    Integer k = today.getEnergyToday() / 1000;
                    Integer r = today.getEnergyToday() % 1000;
                    String value = k + "." + r + " kWh";
                    float avgval = today.getEnergyToday() / 49.7f;
                    int ka = (int) avgval / 1000;
                    int ra = (int) avgval % 1000;
                    String avgvalue = ka + "." + ra;
                    datalist.add(/* "Energy produced today is: "+*/ value);
                    datalist.add(/* "Units/kWp is: "+*/ avgvalue);

                    energyproduced.setText(datalist.get(datalist.indexOf(value)));
                    StoredValues.energyproducedtoday = datalist.get(datalist.indexOf(value));
                    unitsperkwp.setText(datalist.get(datalist.indexOf(avgvalue)));
                    StoredValues.unitsperkwptoday = datalist.get(datalist.indexOf(avgvalue));

                    energyproduced.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                    energyproduced.setTextColor(Color.parseColor("#000000"));
                    unitsperkwp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                    unitsperkwp.setTextColor(Color.parseColor("#000000"));
                }
                else
                {
                    sysstatus.setText("System status currently unknown...");
                    energyproduced.setText("No data....");
                    unitsperkwp.setText("No data....");
                    Dialog dialog = new Dialog(getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.noresponsedialog_design);

                    TextView noresponsetext = dialog.findViewById(R.id.noresponsetext);
                    noresponsetext.setText("Data does not exist for selected date.....");
                    dialog.show();
                    return;
                }
            }


            @Override
            public void onFailure(Call<SummaryOfDay> call, Throwable t) {
                Toast.makeText(context,"Error occured",Toast.LENGTH_SHORT).show();
                sysstatus.setText("System status currently unknown...");
                energyproduced.setText("No data....");
                unitsperkwp.setText("No data....");
                energyproduced.setTextSize(new Button(getContext()).getTextSize());
                unitsperkwp.setTextSize(new Button(getContext()).getTextSize());
            }


        });
    }




    private void average_power_per_day(final Context context)               //////   for the current day
    {

        Call<SummaryOfDay> summaryOfDay = SolarApi.getService(context).getSummaryOfToday(MainActivity.returnapivalue("system_id",context),returncurrentdate(),MainActivity.returnapivalue("apikey",context),MainActivity.returnapivalue("user_id",context));
        summaryOfDay.enqueue(new Callback<SummaryOfDay>() {
            @Override
            public void onResponse(Call<SummaryOfDay> call, Response<SummaryOfDay> response) {
                SummaryOfDay today = response.body();

                ArrayList<String> datalist = new ArrayList<String>();

                Integer k = today.getEnergyToday()/1000;
                Integer r = today.getEnergyToday()%1000;
                String value = k+"."+r+" kWh";
                float avgval = today.getEnergyToday()/49.7f;
                int ka = (int)avgval/1000;
                int ra = (int)avgval%1000;
                String avgvalue = ka+"."+ra;
                datalist.add(/* "Energy produced today is: "+*/ value);
                datalist.add(/* "Units/kWp is: "+*/ avgvalue);

                energyproduced.setText(datalist.get(datalist.indexOf(value)));
                StoredValues.energyproducedtoday = datalist.get(datalist.indexOf(value));
                unitsperkwp.setText(datalist.get(datalist.indexOf(avgvalue)));
                StoredValues.unitsperkwptoday = datalist.get(datalist.indexOf(avgvalue));

                energyproduced.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                energyproduced.setTextColor(Color.parseColor("#000000"));
                unitsperkwp.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                unitsperkwp.setTextColor(Color.parseColor("#000000"));

            }


            @Override
            public void onFailure(Call<SummaryOfDay> call, Throwable t) {
                Toast.makeText(context,"Error occured",Toast.LENGTH_SHORT).show();
                sysstatus.setText("System status currently unknown...");
                energyproduced.setText("No data....");
                unitsperkwp.setText("No data....");
            }


        });
    }




    private void getdata(final Context context)                  //////  for general details
    {
        Call<PostData> postData = SolarApi.getService(context).getPostData(MainActivity.returnapivalue("apikey",context),MainActivity.returnapivalue("user_id",context));
        postData.enqueue(new Callback<PostData>() {
            @Override
            public void onResponse(Call<PostData> call, Response<PostData> response) {
                PostData data = response.body();

                List<System> systemlist = data.getSystems();

                ArrayList<String> datalist = new ArrayList<String>();

                String currentstatus=null;

                for(System s:systemlist)
                {
                    currentstatus = s.getStatus();
                    datalist.add("System status: "+s.getStatus());
                }

                if(currentstatus.contentEquals("normal"))
                {
                    sysstatus.setTextColor(Color.parseColor("#2AE016"));
                }

                sysstatus.setText(datalist.get(0));
                StoredValues.SystemStatus = datalist.get(0);
            }

            @Override
            public void onFailure(Call<PostData> call, Throwable t) {
                Toast.makeText(context,"Error occured",Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void for_today(final Context context)
    {
        Snackbar.make(getView(),"Updating page with latest data....",Snackbar.LENGTH_SHORT).setAction("Action",null).show();

        Log.e("Entering today","Entered function for today and making call");

        Call<SummaryOfDay> summaryOfDay = SolarApi.getService(context).getSummaryOfToday(MainActivity.returnapivalue("system_id",context),returncurrentdate(),MainActivity.returnapivalue("apikey",context),MainActivity.returnapivalue("user_id",context));
        summaryOfDay.enqueue(new Callback<SummaryOfDay>() {
            @Override
            public void onResponse(Call<SummaryOfDay> call, Response<SummaryOfDay> response) {

                if(response.isSuccessful()){
                    Log.i("PROVARETROFIT","OK");
                    }

                    SummaryOfDay today = response.body();

                    String systemstatus = today.getStatus();

                    sysstatus.setText("System status: " + systemstatus);
                    StoredValues.SystemStatus = "System status: " + systemstatus;

                    if(systemstatus.contentEquals("normal"))
                    {
                        sysstatus.setTextColor(Color.parseColor("#2AE016"));
                    }

                    Integer todaysvalue = today.getEnergyToday();
                    Integer tk = todaysvalue / 1000;
                    Integer tr = todaysvalue % 1000;
                    //String todaysvaluestring = tk + "." + tr;                                              ////   energy produced today
                    String todaysvaluestring = String.format("%.0f", ((float)todaysvalue / 1000));
                    float todayavgvalue = todaysvalue / 49.7f;
                    int tka = (int) todayavgvalue / 1000;
                    int tra = (int) todayavgvalue % 1000;
                    //String todaysavgvaluestring = tka + "." + tra;                                     ////   units per kwp today
                    String todaysavgvaluestring = String.format("%.2f", (todayavgvalue / 1000));
                    StoredValues.energyproducedtoday = todaysvaluestring;
                    StoredValues.unitsperkwptoday = todaysavgvaluestring;


                    energyproduced.setText(StoredValues.energyproducedtoday);
                    unitsperkwp.setText(StoredValues.unitsperkwptoday);
                    energyproduced.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                    energyproduced.setTextColor(Color.parseColor("#000000"));
                    unitsperkwp.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                    unitsperkwp.setTextColor(Color.parseColor("#000000"));


                    try {

                        Integer lifetimevalue = today.getEnergyLifetime();
                        String lifetimevaluestring = String.format("%.0f", ((float)lifetimevalue / 1000));
                        String startdate = JSONparserclass.returnapivalue("system_start_date", context);
                        String todaysdate = returncurrentdate();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


                        Date firstDate = format.parse(startdate);
                        Date todaysDate = format.parse(todaysdate);
                        long difference = todaysDate.getTime() - firstDate.getTime();
                        float numberofDays = (difference / (1000 * 60 * 60 * 24));

                        float lifetimeavgvalue = lifetimevalue / (49.7f * numberofDays);
                        String lifetimeavgvaluestring = String.format("%.2f", (lifetimeavgvalue / 1000));

                        StoredValues.energyproducedliftime = lifetimevaluestring;
                        StoredValues.unitsperkwplifetime = lifetimeavgvaluestring;

                        energyproducedlifetime.setText(StoredValues.energyproducedliftime);
                        unitsperkwplifetime.setText(StoredValues.unitsperkwplifetime);
                        energyproducedlifetime.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        energyproducedlifetime.setTextColor(Color.parseColor("#000000"));
                        unitsperkwplifetime.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        unitsperkwplifetime.setTextColor(Color.parseColor("#000000"));
                    } catch (Exception ex) {
                        Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }



                    getvaluesfortwomonths(getContext(), frontpagedetails.firstmonth.get(0), StoredValues.Todaysdate);

                }

                @Override
                public void onFailure(Call<SummaryOfDay> call, Throwable t) {
                    energyupdatebuttonpressed = false;
                    Toast.makeText(context,"Could not get today's value. Please check your internet connection and try again",Toast.LENGTH_SHORT).show();
                }
            });

    }



}
