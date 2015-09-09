/*
 * Copyright (C) 2015 University of Oulu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

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

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * The screen show summery information about a device's network traffic
 */
public class SummeryActivity extends ActionBarActivity {

    ArrayList<String> countries = new ArrayList<>();
    public static ArrayList<String> selectedCountries = new ArrayList<>();
    public static ArrayList<Integer> dummyData = new ArrayList<>();


    public static ArrayList<Entry> Data_Time = new ArrayList<Entry>();
    public static ArrayList<String> time_data = new ArrayList<String>();

    public static ArrayList<Entry>  Devices_number = new ArrayList<Entry>();
    public static ArrayList<String> Time_data = new ArrayList<String>();


    public static ArrayList<String> data = new ArrayList<>();

    //public DataByTimeActivity data_by_time = new DataByTimeActivity();

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
        Data_Time = DataTrafficTime();
        Devices_number = DevicesByTime();

        TextView Num_text = (TextView) findViewById(R.id.textView3);
        ArrayList<Integer> a = getBusyPeriod(Devices_number);
        Num_text.append("The busy periods (with 16 or more than 16 devices connected) are: ");
        for (int i = 0; i< a.size(); i++)
        {
            int time = a.get(i);
            int Time = time + 1;
            Num_text.append(time + ":00" + " to " + Time + ":00 ; " );
        }
        Num_text.append("You probably need to pay attention if any other unreliable devices break into the Network. ");
        Num_text.append("the busiest period is in " + String.valueOf(Time_data.get(getMaxData_bytime(Devices_number))) + " and the number of devices connected to the network is " + String.valueOf(Devices_number.get(getMaxData_bytime(Devices_number)).getVal()) + " ");

        TextView newtext = (TextView) findViewById(R.id.textView4);
        Button btnShowDev = (Button) findViewById(R.id.btnDevByTime);
        Button btnShowTra = (Button) findViewById(R.id.btnTraByCou);
        newtext.append("In recent weeks, in " + selectedCountries.get(getMaxValue(dummyData)) + " you spent the longest time to browse Internet, and the data used is " + String.valueOf(dummyData.get(getMaxValue(dummyData))) + " MB");
        TextView txt_datatime = (TextView) findViewById(R.id.txt_databytime);


        txt_datatime.append("During a day, in " + String.valueOf(time_data.get(getMaxData_bytime(Data_Time))) + " period you used the most data,  and its number is  " + String.valueOf(Data_Time.get(getMaxData_bytime(Data_Time)).getVal()) + " MB");

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


     Button Btn_datatime = (Button) findViewById(R.id.btn_databytime);
        Btn_datatime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DataByTimeActivity.class);
                startActivity(intent);
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

    public int getMaxValue(ArrayList<Integer> s){
        int max = 0;
        int max_index = 0;
        for(int i = 0; i < s.size(); i++){
            if(s.get(i)>max) {
                max = s.get(i);
                max_index = i;
            }
        }
        return max_index;
    }

    public int getMaxData_bytime(ArrayList<Entry> s){
        int max = 0;
        int max_index = 0;
        for(int i = 0; i < s.size(); i++){
            if(s.get(i).getVal() > max) {
                max = (int) s.get(i).getVal();
                max_index = i;
            }
        }
        return max_index;
    }


    public ArrayList<Integer> getBusyPeriod(ArrayList<Entry> s){
        int busy = 15;
        ArrayList<Integer> busy_index = new ArrayList<>();
        for(int i = 0; i < s.size(); i++){
            if(s.get(i).getVal() >= busy) {
                busy_index.add(i);
            }
        }
        return busy_index;
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

    public ArrayList<Entry> DataTrafficTime() {
        int min=300 , max=1000;
        ArrayList<Entry> temp = new ArrayList<Entry>();

        Random r = new Random();
        for (int i=0; i<25; i++) {
            temp.add(new Entry(r.nextInt(max - min + 1) + min, i));
            int a = i+1;
            if (a<25)
                time_data.add(i+":00---"+ a +":00");
            else if (a==25)
                time_data.add(i+":00---"+"0:00");
        }
        return temp;
    }


    public ArrayList<Entry> DevicesByTime() {
        int min=3 , max=20;
        ArrayList<Entry> temp = new ArrayList<Entry>();

        Random r = new Random();
        for (int i=0; i<25; i++) {
            temp.add(new Entry(r.nextInt(max - min + 1) + min, i));
            int a = i+1;
            if (a<25)
                Time_data.add(i+":00---"+ a +":00");
            else if (a==25)
                Time_data.add(i+":00---"+"0:00");
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
