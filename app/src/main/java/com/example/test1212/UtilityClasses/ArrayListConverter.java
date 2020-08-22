package com.example.test1212.UtilityClasses;

import java.util.ArrayList;

public class ArrayListConverter {

    public ArrayList<Float> converttoarraylistfloat(float arr[])
    {
        ArrayList<Float> array_list = new ArrayList<Float>();

        for(int i=0;i<arr.length;i++)
        {
            array_list.add(arr[i]);
        }

        return array_list;
    }


    public ArrayList<String> converttoarrayliststring(String arr[])
    {
        ArrayList<String> array_list = new ArrayList<String>();

        for(int i=0;i<arr.length;i++)
        {
            array_list.add(arr[i]);
        }

        return array_list;
    }

}
