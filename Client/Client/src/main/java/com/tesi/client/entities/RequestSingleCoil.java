package com.tesi.client.entities;

import java.net.InetAddress;

public class RequestSingleCoil {
    InetAddress ip;
    int startingAddress;
    boolean value;

    public RequestSingleCoil(InetAddress ip, int startingAddress, boolean value) {
        this.ip = ip;
        this.startingAddress = startingAddress;
        this.value = value;
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

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
