package com.tesi.client.services;

import com.tesi.client.connection.SocketTCP;
import com.tesi.client.entities.RequestSingleCoil;
import com.tesi.client.util.Util;

import java.io.IOException;
import java.net.InetAddress;

public class WriteSingleCoil {

    private byte [] transactionIdentifier = new byte[2];
    private byte [] protocolIdentifier = new byte[2];
    private byte [] length = new byte[2];
    private byte unitIdentifier = 1;
    private byte functionCode;
    private byte [] startingAddress = new byte[2];

    public void writeSingleCoil(RequestSingleCoil request) throws IOException { //InetAddress ip, int startingAddress, boolean value

        InetAddress ip = request.getIp();
        int startingAddress = request.getStartingAddress();
        boolean value = request.isValue();

        SocketTCP connexion = new SocketTCP(ip);

        if (!connexion.isEstablishedConnexion()) {
            System.out.println("Error in TCP connection");
            throw new IllegalStateException("Error in TCP connection");
        }

        byte[] coilValue = new byte[2];
        this.transactionIdentifier = Util.toByteArray(0x0001);
        this.protocolIdentifier = Util.toByteArray(0x0000);
        this.length = Util.toByteArray(0x0006);
        //this.unitIdentifier = 0;
        this.functionCode = 0x05;
        this.startingAddress = Util.toByteArray(startingAddress);
        if (value) {
            coilValue = Util.toByteArray((int)0xFF00);
        }
        else {
            coilValue = Util.toByteArray((int)0x0000);
        }

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
                coilValue[1],
                coilValue[0]
        };

        connexion.sendRequest(data);

        byte[] receiveData = connexion.receiveResponse(7+5);

        if(((receiveData[7] & 0xff) == 0x85) && (receiveData[8] == 0x01)){
            System.out.println("FunctionCodeNotSupportedException");
            throw new IllegalStateException("FunctionCodeNotSupportedException");
        }

        if(((receiveData[7] & 0xff) == 0x85) && (receiveData[8] == 0x02)){
            System.out.println("Starting adress invalid or starting adress + quantity invalid");
            throw new IllegalStateException("Starting adress invalid or starting adress + quantity invalid");
        }

        if(((receiveData[7] & 0xff) == 0x85) && (receiveData[8] == 0x03)){
            System.out.println("Quantity invalid");
            throw new IllegalStateException("Quantity invalid");
        }

        if(((receiveData[7] & 0xff) == 0x85) && (receiveData[8] == 0x04)){
            System.out.println("Error reading");
            throw new IllegalStateException("Error reading");
        }

    }

}
