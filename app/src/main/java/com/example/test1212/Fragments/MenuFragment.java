package com.example.test1212.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test1212.Activities.AboutActivity;
import com.example.test1212.Activities.MainActivity;
import com.example.test1212.RequestedValues.PostData;
import com.example.test1212.R;
import com.example.test1212.SolarApi;
import com.example.test1212.StoredValues;
import com.example.test1212.RequestedValues.System;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuFragment extends Fragment {

    TextView sysname;
    TextView sysid;
    ListView menulist;
    String[] listItems = new String[6];

    String returnedsystemname,returnedsystemID,returnedsystempublicname,returnedtimezone,returnedcountry,returnedstate,returnedcity,returnedpostalcode;
    Boolean valuespresent;


    ImageView aboutbutton;


    public MenuFragment(String systemname,String systemID,String systempublicname,String timezone,String country,String state,String city,String postalcode)
    {
        if(systemname!=null && systemID!=null) {
            returnedsystemname = systemname;
            returnedsystemID = systemID;
            returnedsystempublicname = systempublicname;
            returnedtimezone = timezone;
            returnedcountry = country;
            returnedstate = state;
            returnedcity = city;
            returnedpostalcode = postalcode;
            valuespresent = true;
        }
        else
        {
            valuespresent = false;
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View v = inflater.inflate(R.layout.fragment_menu,container,false);

       sysname = v.findViewById(R.id.sysname);
       sysid = v.findViewById(R.id.sysid);
       menulist = v.findViewById(R.id.menulist);

       aboutbutton = v.findViewById(R.id.about_button);

       if(!valuespresent) {
           getdata(this.getContext());
       }
       else
       {
           sysname.setText(returnedsystemname);
           sysid.setText(returnedsystemID);
           int i=0;
           listItems[i++]=returnedsystempublicname;
           listItems[i++]=returnedtimezone;
           listItems[i++]=returnedcountry;
           listItems[i++]=returnedstate;
           listItems[i++]=returnedcity;
           listItems[i]=returnedpostalcode;
           ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,android.R.id.text1,listItems);
           menulist.setAdapter(arrayAdapter);
       }


        aboutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent about = new Intent(getContext(), AboutActivity.class);
                startActivity(about);
            }
        });


       return v;
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

                    for (System s : systemlist) {
                        datalist.add("System ID: " + s.getSystemId());
                        datalist.add(s.getSystemName());
                        int i = 0;
                        listItems[i++] = "System public name: " + s.getSystemPublicName();
                        StoredValues.systempublicname = "System public name: " + s.getSystemPublicName();
                        listItems[i++] = "Timezone: " + s.getTimezone();
                        StoredValues.timezone = "Timezone: " + s.getTimezone();
                        listItems[i++] = "Country: " + s.getCountry();
                        StoredValues.country = "Country: " + s.getCountry();
                        listItems[i++] = "State: " + s.getState();
                        StoredValues.state = "State: " + s.getState();
                        listItems[i++] = "City: " + s.getCity();
                        StoredValues.city = "City: " + s.getCity();
                        listItems[i] = "Postal Code: " + s.getPostalCode();
                        StoredValues.Postalcode = "Postal Code: " + s.getPostalCode();
                    }
                    if (getContext() != null) {
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, listItems);
                        menulist.setAdapter(arrayAdapter);
                        sysname.setText(datalist.get(1));
                        sysid.setText(datalist.get(0));
                        StoredValues.systemname = datalist.get(1);
                        StoredValues.systemID = datalist.get(0);
                    } else {
                        call.cancel();
                    }

            }

            @Override
            public void onFailure(Call<PostData> call, Throwable t) {
                if(call.isCanceled()){
                    // call cancelled
                }
                else{
                    Toast.makeText(context, "Error occured", Toast.LENGTH_SHORT).show();
                }
            }
        });



        new Callback<PostData>()
        {
            @Override
            public void onResponse(Call<PostData> call,Response<PostData> responsebody)
            {

            }

            @Override
            public void onFailure(Call<PostData> call, Throwable t)
            {
                if(call.isCanceled())
                {

                }
                else
                {
                    Toast.makeText(context, "Error occured", Toast.LENGTH_SHORT).show();
                }
            }
        };



    }



}
