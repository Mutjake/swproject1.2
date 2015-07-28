package com.swproject.fi.swproject;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;


public class SummeryActivity extends ActionBarActivity {

    ArrayList<String> countries = new ArrayList<>();
    public static ArrayList<String> selectedCountries = new ArrayList<>();
    public static ArrayList<Integer> dummyData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summery);

        countries.add("United State");  // index 1
        countries.add("United Kingdom"); //2
        countries.add("Korea"); //3
        countries.add("Japan"); // 4
        countries.add("China"); // 5
        countries.add("Russia"); // 6
        countries.add("Denmark"); // 7
        countries.add("Norway"); // 8
        countries.add("India"); // 9
        countries.add("Finland"); // 10
        countries.add("Hungary"); // 11
        countries.add("Germany"); // 12
        countries.add("Sweden"); // 13
        countries.add("Spain"); // 14
        countries.add("Italy"); // 15
        countries.add("Netherlands"); // 16
        countries.add("Belgium"); // 17
        countries.add("Switzerland"); // 18
        countries.add("Austria"); // 19
        countries.add("Vietnam"); // 20

        selectedCountries = getCountries();
        dummyData = dummyDataTraffic();

        TextView newtext = (TextView) findViewById(R.id.textView4);
        Button btnShowDev = (Button) findViewById(R.id.btnDevByTime);
        Button btnShowTra = (Button) findViewById(R.id.btnTraByCou);
        newtext.append("The biggest data traffic from "+selectedCountries.get(getMaxValue())+" and of "+String.valueOf(dummyData.get(getMaxValue())) + " MB");

        btnShowDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity = new Intent(getApplicationContext(), DevicesByTimeChartActivity.class);
                startActivity(activity);
            }
        });

        btnShowTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity = new Intent(getApplicationContext(), DataTrafficByCountry.class);
                startActivity(activity);
            }
        });
    }

    public ArrayList<String> getCountries(){
        Random r = new Random();
        ArrayList<String> tmp_countries = new ArrayList<>();
        ArrayList<Integer> tmp_index = new ArrayList<>();
        int i=0;

        while(i<10) {
            int t=0;
            t = r.nextInt(19 - 1 + 1) + 1;
            if(i==0) {
                tmp_index.add(t);
                tmp_countries.add(countries.get(t));
                i++;
            }
            else {
                boolean flag=false;
                int j=0;
                while(j<tmp_index.size()){
                    if(t == tmp_index.get(j)){
                        flag = true;
                        break;
                    }
                    else
                        j++;
                }
                if(flag == false){
                    tmp_index.add(t);
                    tmp_countries.add(countries.get(t));
                    i++;
                }
            }

        }

        return tmp_countries;
    }

    public int getMaxValue(){
        int max = 0;
        int max_index = 0;
        for(int i = 0; i < dummyData.size(); i++){
            if(dummyData.get(i)>max) {
                max = dummyData.get(i);
                max_index = i;
            }
        }
        return max_index;
    }

    public Calendar getCalendar(){
        Calendar c = Calendar.getInstance();
        return c;
    }

    public ArrayList<Integer> dummyDataTraffic() {
        int min=200 , max=900;
        ArrayList<Integer> temp = new ArrayList<>();

        Random r = new Random();
        for (int i=0; i<10; i++) {
            temp.add(r.nextInt(max - min + 1) + min);
        }
        return temp;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_summery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
