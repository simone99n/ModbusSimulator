package com.tesi.client.services;

import com.tesi.client.connection.SocketTCP;
//import com.tesi.client.entities.RequestBase;
import com.tesi.client.entities.RequestSingleRegister;
import com.tesi.client.util.Util;

import java.io.IOException;
import java.net.InetAddress;

public class WriteSingleRegister {

    private byte [] transactionIdentifier = new byte[2];
    private byte [] protocolIdentifier = new byte[2];
    private byte [] length = new byte[2];
    private byte unitIdentifier = 1;
    private byte functionCode;
    private byte [] startingAddress = new byte[2];

    public void writeSingleRegister(RequestSingleRegister request) throws IOException {

        InetAddress ip = request.getIp();
        int startingAddress = request.getStartingAddress();
        int value = request.getValue();

        System.out.println("ip: " + ip + " + startAddr: " + startingAddress + " + value: " + value);

        SocketTCP connexion = new SocketTCP(ip);

        if (!connexion.isEstablishedConnexion()) {
            System.out.println("Error in TCP connection");
            throw new IllegalStateException("Error in TCP connection");
        }

        byte[] registerValue = new byte[2];
        this.transactionIdentifier = Util.toByteArray((int)0x0001);
        this.protocolIdentifier = Util.toByteArray((int)0x0000);
        this.length = Util.toByteArray((int)0x0006);
        this.functionCode = 0x06;
        this.startingAddress = Util.toByteArray(startingAddress);
        registerValue = Util.toByteArray((short)value);

        byte[] data = new byte[]{
                this.transactionIdentifier[1],
                this.transactionIdentifier[0],
                this.protocolIdentifier[1],
                this.protocolIdentifier[0],
                this.length[1],
                this.length[0],
                this.unitIdentifier,
                this.functionCode,
                this.startingAddress[1],
                this.startingAddress[0],
                registerValue[1],
                registerValue[0]
        };

        connexion.sendRequest(data);

        byte[] receiveData = connexion.receiveResponse(7+5);

        if(((receiveData[7] & 0xff) == 0x86) && (receiveData[8] == 0x01)){
            System.out.println("FunctionCodeNotSupportedException");
            throw new IllegalStateException("FunctionCodeNotSupportedException");
        }

        if(((receiveData[7] & 0xff) == 0x86) && (receiveData[8] == 0x02)){
            System.out.println("Starting adress invalid or starting adress + quantity invalid");
            throw new IllegalStateException("Starting adress invalid or starting adress + quantity invalid");
        }

        if(((receiveData[7] & 0xff) == 0x86) && (receiveData[8] == 0x03)){
            System.out.println("Quantity invalid");
            throw new IllegalStateException("Quantity invalid");
        }

        if(((receiveData[7] & 0xff) == 0x86) && (receiveData[8] == 0x04)){
            System.out.println("Error reading");
            throw new IllegalStateException("Error reading");
        }


    }
}
