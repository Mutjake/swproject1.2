package com.swproject.fi.swproject;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.Date;
import java.util.Random;


public class DataByTimeActivity extends ActionBarActivity {

    private LineChart mChart;
    private RelativeLayout datatime;
    private DataSet dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_by_time);
        mChart = new LineChart(this.getApplicationContext());
        datatime = (RelativeLayout) findViewById(R.id.databytime_frame);
        datatime.addView(mChart);

        //customize line chart
        mChart.setDescription("");
        mChart.setNoDataTextDescription("No data for the moment");

        //enable value highlighting
        mChart.setHighlightEnabled(true);


        //enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);

        //enable pinch zoom to avoid scaling z and y axis separately
        mChart.setPinchZoom(true);

        mChart.setBackgroundColor(Color.LTGRAY);

        //now we work on data
        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);


        //add data to chart
        mChart.setData(data);


        //get legend object
        Legend l = mChart.getLegend();

        //customize legend
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.WHITE);
        l.setFormSize(500);

        XAxis x1 = mChart.getXAxis();
        x1.setTextColor(Color.WHITE);
        x1.setDrawGridLines(false);
        x1.setAvoidFirstLastClipping(true);

        YAxis y1 = mChart.getAxisLeft();
        y1.setTextColor(Color.WHITE);
        y1.setAxisMaxValue(450f);
        y1.setDrawGridLines(true);


        YAxis y2 = mChart.getAxisRight();
        y2.setEnabled(true);




    }

    private void addEntry(){
        LineData data = mChart.getLineData();

        if (data != null){
            LineDataSet set = data.getDataSetByIndex(0);

            if(set != null){

            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_by_time, menu);
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
