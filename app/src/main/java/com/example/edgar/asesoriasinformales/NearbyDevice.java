package com.example.edgar.asesoriasinformales;

import android.bluetooth.BluetoothDevice;

public class NearbyDevice implements Comparable{
    private BluetoothDevice device;
    private int signalStrength;

    public NearbyDevice(BluetoothDevice device, int signalStrength){
        this.device = device;
        this.signalStrength = signalStrength;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public void setSignalStrength(int signalStrength){
        this.signalStrength = signalStrength;
    }

    public BluetoothDevice getDevice() {
        return this.device;
    }

    public int getSignalStrength () {
        return this.signalStrength;
    }

    @Override
    public String toString() {
        return this.device.getName()+" "+this.device.getAddress()+" "+this.signalStrength+" dbm";
    }

    @Override
    public boolean equals (Object object){
        if(object instanceof NearbyDevice) {
            return this.device.getAddress().equals(((NearbyDevice) object).device.getAddress());
        }
        return false;
    }

    @Override
    public int compareTo(Object o) {
        int other = ((NearbyDevice) o).getSignalStrength();
        if(this.signalStrength > other) {
            return -1;
        }
        else if(this.signalStrength < other) {
            return 1;
        }
        return 0;
    }
}

