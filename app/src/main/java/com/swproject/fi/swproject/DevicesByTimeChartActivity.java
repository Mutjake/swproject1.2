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
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by dluu on 27.7.2015.
 * The chart show number of devices connected to network by time
 */
public class DevicesByTimeChartActivity extends ActionBarActivity
{
    protected BarChart mChart;
    private TextView tvX, tvY;

    private ArrayList<String> labels;
    private ArrayList<BarEntry> entries;

    private int randomDeviceCount(int min, int max)
    {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

	/** Generating fake data for the chart */
    private ArrayList<BarEntry> generateFakeData()
    {
        ArrayList<BarEntry> data = new ArrayList<BarEntry>();
        for (int i=0; i<10; i++)
        {
            data.add(new BarEntry(this.randomDeviceCount(3, 9), i));
        }

        return data;
    }

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.v("thangld", "chart");
        //generating fake data
        this.entries = this.generateFakeData();
        BarDataSet dataset = new BarDataSet(this.entries, "# of Devices");

        //generate labels
        this.labels = new ArrayList<String>();
        ArrayList<String> tmp = new ArrayList<String>();
        Calendar c = Calendar.getInstance();
        int currentHour = c.get(Calendar.HOUR_OF_DAY);

        for (int i=0; i<10; i++)
        {
            int hour = currentHour - i;
            boolean isPreviousDay = false;
            if (hour == 0)
            {
                isPreviousDay = true;
            }
            if (hour < 0)
            {
                hour = 24 - (i - currentHour);
            }

            if (!isPreviousDay)
                tmp.add(String.valueOf(hour));
            else
            {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                tmp.add(dateFormat.format(c.getTime()));
            }

        }
        // reverse the array list
        for (int i=0; i<10; i++)
        {
            this.labels.add(tmp.get(10-i-1));
        }

        BarChart chart = new BarChart(this.getApplicationContext());

        setContentView(chart);

        BarData data = new BarData(labels, dataset);
        chart.setData(data);

    }
}
