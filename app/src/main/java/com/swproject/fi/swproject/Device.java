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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alex on 7.6.2015.
 * The class model for a device
 */
public class Device {
    private Integer icon;
    private String ipAddress;
    private String macAddress;
    private String name;

    public Device(Integer icon, String name, String ipAddress, String macAddress){
        this.icon = icon;
        this.name = name;
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
    }

    /* added by Thangld on 16.6.2015
    json string should have the following format:
    {
        'name' : 'name of the device',
        'ipAdress' : 'ip of the device',
        'macAddress' : 'mac address of the device',
        'type' : 'type of the device (currently only work with laptop, phone, desktop and printer)'
    }
     */
    public Device(String jsonString)
    {
        try {
            JSONObject jsonObj = new JSONObject(jsonString);
            this.name = jsonObj.getString("name");
            this.ipAddress = jsonObj.getString("ipAddress");
            this.macAddress = jsonObj.getString("macAddress");
            switch(jsonObj.getString("type"))
            {
                case "desktop":
                    this.icon = R.drawable.desktop;
                    break;
                case "laptop":
                    this.icon = R.drawable.laptop;
                    break;
                case "phone":
                    this.icon = R.drawable.phone;
                    break;
                case "printer":
                    this.icon = R.drawable.printer;
                    break;
                default:
                    this.icon = R.drawable.desktop;
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Integer getIcon(){
        return icon;
    }

    public void setIcon(Integer icon){
        this.icon = icon;
    }

    public String getIpAddress(){
        return ipAddress;
    }

    public void setIpAddress(String ipAddress){
        this.ipAddress = ipAddress;
    }

    public String getName (){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getMAC(){
        return macAddress;
    }

    public void setMAC(String network){
        this.macAddress = network;
    }
}
