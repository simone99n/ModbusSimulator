package com.tesi.client.entities;

public class RequestRead {
    String ip;
    int startingAddress;
    int quantity;

    public RequestRead(String ip, int startingAddress, int quantity) {
        this.ip = ip;
        this.startingAddress = startingAddress;
        this.quantity = quantity;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getStartingAddress() {
        return startingAddress;
    }

    public void setStartingAddress(int startingAddress) {
        this.startingAddress = startingAddress;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
