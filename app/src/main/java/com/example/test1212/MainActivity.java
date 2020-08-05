package com.example.test1212;

import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.test1212.Fragments.EnergyFragment;
import com.example.test1212.Fragments.MenuFragment;
import com.example.test1212.Fragments.StatusFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listview;
    ArrayList<String> datalist;

    public static Context MainActivityContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivityContext = getApplicationContext();

        BottomNavigationView bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containor,new StatusFragment(StoredValues.SystemStatus,StoredValues.energyproducedtoday,StoredValues.unitsperkwptoday,StoredValues.energyproducedyesterday,StoredValues.unitsperkwpyesterday,StoredValues.energyproducedthismonth, StoredValues.unitsperkwpthismonth,StoredValues.energyproducedlastmonth,StoredValues.unitsperkwplastmonth,StoredValues.energyproducedliftime,StoredValues.unitsperkwplifetime,StoredValues.energyproducedthisyear,StoredValues.unitsperkwpthisyear,StoredValues.energyproducedlastyear,StoredValues.unitsperkwplastyear)).commit();


    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedfragment=null;

                    switch(menuItem.getItemId())
                    {
                        case R.id.nav_status:
                            selectedfragment = new StatusFragment(StoredValues.SystemStatus,StoredValues.energyproducedtoday,StoredValues.unitsperkwptoday,StoredValues.energyproducedyesterday,StoredValues.unitsperkwpyesterday,StoredValues.energyproducedthismonth, StoredValues.unitsperkwpthismonth,StoredValues.energyproducedlastmonth,StoredValues.unitsperkwplastmonth,StoredValues.energyproducedliftime,StoredValues.unitsperkwplifetime,StoredValues.energyproducedthisyear,StoredValues.unitsperkwpthisyear,StoredValues.energyproducedlastyear,StoredValues.unitsperkwplastyear);
                            break;
                        case R.id.nav_energy:
                            selectedfragment = new EnergyFragment();
                            break;
                        case R.id.nav_menu:
                            selectedfragment = new MenuFragment(StoredValues.systemname,StoredValues.systemID,StoredValues.systempublicname,StoredValues.timezone,StoredValues.country,StoredValues.state,StoredValues.city,StoredValues.Postalcode);
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containor,selectedfragment).commit();

                    return true;
                }
            };






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
                case "system start date":
                    returnedvalue = obj.getString("system_start_date");
                    break;
                case "system start year":
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