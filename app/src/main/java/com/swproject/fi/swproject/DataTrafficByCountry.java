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

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Calendar;

/**
 * Created by dhaejong on 28.7.2015.
 * The chart showing data traffic volume by country
 */
public class DataTrafficByCountry extends ActionBarActivity {

    private ArrayList<BarEntry> entries;
    SummeryActivity m_summery_activity = new SummeryActivity();

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.v("haejong", "chart");

        int date = m_summery_activity.getCalendar().get(Calendar.DAY_OF_MONTH);
        int month = m_summery_activity.getCalendar().get(Calendar.MONTH);


        //generating fake data
        this.entries = this.generateFakeData();

        BarDataSet dataset = new BarDataSet(this.entries, "MB of Data traffic as of " + date + "." + (month+1));
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        BarChart chart = new BarChart(this.getApplicationContext());
        setContentView(chart);
        BarData data = new BarData(this.m_summery_activity.selectedCountries, dataset);
        chart.setData(data);
        chart.setDescription("Data traffic from which sorted by nation");


    }

    private ArrayList<BarEntry> generateFakeData()
    {
        ArrayList<BarEntry> data = new ArrayList<>();
        for (int i=0; i<10; i++)
        {
            data.add(new BarEntry(m_summery_activity.dummyData.get(i), i));
        }

        return data;
    }
}
