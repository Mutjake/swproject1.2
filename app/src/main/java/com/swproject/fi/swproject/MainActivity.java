package com.swproject.fi.swproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    private ImageViewAdapter adapter;
    private int deviceIndex = -1;
    public static List<Device> deviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        //deviceList = Collections.synchronizedList(new ArrayList<Device>());
        deviceList = new ArrayList<>();
        adapter = new ImageViewAdapter(getApplicationContext(), R.layout.grid_item, deviceList);

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(deviceClickListener);
        gridView.invalidateViews();

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        Button btnRemove = (Button) findViewById(R.id.btnRemove);

        btnAdd.setOnClickListener(this);
        btnRemove.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void addItem(String name, String ip, String mac){
        deviceList.add(new Device(R.drawable.desktop, name, ip, mac));
        adapter.notifyDataSetChanged();
        ++deviceIndex;
        onNotify();
    }

    private void onNotify(){
        SharedPreferences prefs = this.getSharedPreferences("com.swproject.fi.swproject", Context.MODE_PRIVATE);
        int notificationId = prefs.getInt("notificationId",0)+1;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("notificationId", notificationId);
        editor.commit();

        Intent intent = new Intent(this, ReportActivity.class);
        Bundle extras = new Bundle();
        String name = deviceList.get(deviceIndex).getName();
        String ip = deviceList.get(deviceIndex).getIpAddress();
        String mac = deviceList.get(deviceIndex).getMAC();
        Log.e("+++++++++", name);
        Log.e("+++++++++", "" + deviceIndex);
        extras.putString("hostName", name);
        extras.putString("ipAddress", ip);
        extras.putString("macAddress", mac);
        extras.putInt("index", deviceIndex);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("ACTION_" + notificationId);
        intent.putExtras(extras);
        intent.putExtra("notificationId",notificationId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder noti = new Notification.Builder(this)
                .setContentTitle("New device " + name)
                .setSubText("A new device has been added")
                .setSmallIcon(R.drawable.notification_icon)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                        R.drawable.notification_icon))
                .setVibrate(new long[]{100, 500, 300, 500})
                .setAutoCancel(true);
        noti.setContentIntent(pendingIntent);
        nm.notify(notificationId, noti.build());

    }

    private void removeItem(){
        if (deviceList.size() != 0){
            int last = deviceList.size() - 1;
            deviceList.remove(last);
            adapter.notifyDataSetChanged();
            deviceIndex--;
        }
    }

    private void showDeviceCountChart()
    {
        Log.v("thangld", "begin chart");
        Intent activity = new Intent(getApplicationContext(), DevicesByTimeChartActivity.class);
        startActivity(activity);
    }

    @Override
    public void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private AdapterView.OnItemClickListener deviceClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String name = deviceList.get(i).getName();
            String ip = deviceList.get(i).getIpAddress();
            String mac = deviceList.get(i).getMAC();
            Intent activity = new Intent(getApplicationContext(), ReportActivity.class);
            Bundle extras = new Bundle();
            extras.putString("hostName", name);
            extras.putString("ipAddress", ip);
            extras.putString("macAddress", mac);
            extras.putInt("index", i);
            activity.putExtras(extras);
            startActivity(activity);
        }
    };

    public void onClick(View view) {
        Log.v("thangld", String.valueOf(view.getId()));
        switch (view.getId()){
            case R.id.btnAdd:
                int index = deviceList.size() + 1;
                String name = "Device " + index;
                String ip = "192.168.0." + ++index;
                String mac = "C8:F7:33:06:64:2C";
                addItem(name, ip, mac);
                break;
            case R.id.btnRemove:
                removeItem();
                break;
        }
    }
}
