package com.tesi.client.entities;

import java.net.InetAddress;

public class RequestMultipleCoils {
    InetAddress ip;
    int startingAddress;
    boolean[] values;

    public RequestMultipleCoils(InetAddress ip, int startingAddress, boolean[] values) {
        this.ip = ip;
        this.startingAddress = startingAddress;
        this.values = values;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public int getStartingAddress() {
        return startingAddress;
    }

    public void setStartingAddress(int startingAddress) {
        this.startingAddress = startingAddress;
    }

    public boolean[] getValues() {
        return values;
    }

    public void setValues(boolean[] values) {
        this.values = values;
    }
}
