package com.example.test1212.UtilityClasses;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class JSONparserclass {

    public static String returnapivalue(String value, Context context){
        String returnedvalue = null;
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(context));
            switch(value)
            {
                case "apikey":
                    returnedvalue = obj.getString("apikey");
                    break;
                case "user_id":
                    returnedvalue = obj.getString("user_id");
                    break;
                case "system_id":
                    returnedvalue = obj.getString("system_id");
                    break;
                case "url":
                    returnedvalue = obj.getString("url");
                    break;
                case "system_start_date":
                    returnedvalue = obj.getString("system_start_date");
                    break;
                case "system_start_year":
                    returnedvalue = obj.getString("system_start_year");
                    break;
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        Toast.makeText(context,returnedvalue,Toast.LENGTH_SHORT);
        return returnedvalue;
    }



    public static String loadJSONFromAsset(Context context){
        String json;
        try{
            InputStream is = context.getAssets().open("apivalues.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer,"UTF-8");
        }
        catch(IOException ex){
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
