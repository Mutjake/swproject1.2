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

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;

/**
 * Created by alex on 27.7.2015.
 * The screen for the user to change information of one device
 */
public class ReportActivity extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_);
        Button btnReport = (Button) findViewById(R.id.btn_Report);
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SummeryActivity.class);
                startActivity(intent);
            }
        });

        LineChart chart = null;


        String name = getIntent().getExtras().getString("hostName");
        String mac = getIntent().getExtras().getString("macAddress");
        final int index = getIntent().getExtras().getInt("index");

        TextView txtHostName = (TextView) findViewById(R.id.editHostName);
        TextView txtMac = (TextView) findViewById(R.id.editMac);

        final EditText editName = (EditText) findViewById(R.id.editName);
        final InputMethodManager imm = (InputMethodManager)this.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editName, InputMethodManager.SHOW_IMPLICIT);


        Button btnSetName = (Button) findViewById(R.id.btn_setName);
        btnSetName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String newName = editName.getText().toString();
                Device device = MainActivity.deviceList.get(index);
                device.setName(newName);
                Toast.makeText(getApplicationContext(), "Name Has been Changed", Toast.LENGTH_SHORT).show();
                imm.hideSoftInputFromWindow(editName.getWindowToken(), 0);
                editName.clearFocus();
            }
        });

        editName.setText(name);
        txtHostName.setText(name);
        txtMac.setText(mac);
    }

    public void startDataActivity(){
        Intent intent = new Intent(this, Data.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report, menu);
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
