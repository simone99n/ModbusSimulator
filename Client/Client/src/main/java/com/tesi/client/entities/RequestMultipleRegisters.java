package com.tesi.client.entities;

import java.net.InetAddress;

public class RequestMultipleRegisters {
    InetAddress ip;
    int startingAddress;
    int[] values;

    public RequestMultipleRegisters(InetAddress ip, int startingAddress, int[] values) {
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

    public int[] getValues() {
        return values;
    }

    public void setValues(int[] values) {
        this.values = values;
    }
}
