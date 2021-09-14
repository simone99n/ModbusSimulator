package com.tesi.client.entities;

import java.net.InetAddress;

public class RequestSingleRegister {
    InetAddress ip;
    int startingAddress;
    int value;

    public RequestSingleRegister(InetAddress ip, int startingAddress, int value) {
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
