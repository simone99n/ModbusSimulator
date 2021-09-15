package com.tesi.client.services;

import com.tesi.client.connection.SocketTCP;
import com.tesi.client.entities.RequestMultipleCoils;
import com.tesi.client.util.Util;

import java.io.IOException;
import java.net.InetAddress;

public class WriteMultipleCoils {

    private byte [] transactionIdentifier = new byte[2];
    private byte [] protocolIdentifier = new byte[2];
    private byte [] length = new byte[2];
    private byte unitIdentifier = 1;
    private byte functionCode;
    private byte [] startingAddress = new byte[2];

    public void writeMultipleCoils(RequestMultipleCoils request) throws IOException {

        InetAddress ip = request.getIp();
        int startingAddress = request.getStartingAddress();
        boolean[] values = request.getValues();

        byte byteCount = (byte)(values.length/8+1);
        if (values.length % 8 == 0)
            byteCount=(byte)(byteCount-1);
        byte[] quantityOfOutputs = Util.toByteArray((int)values.length);
        byte singleCoilValue = 0;

        SocketTCP connexion = new SocketTCP(ip);

        if (!connexion.isEstablishedConnexion()) {
            System.out.println("Error in TCP connection");
            throw new IllegalStateException("Error in TCP connection");
        }

        this.transactionIdentifier = Util.toByteArray((int)0x0001);
        this.protocolIdentifier = Util.toByteArray((int)0x0000);
        this.length = Util.toByteArray((int)(7+(values.length/8+1)));
        this.functionCode = 0x0F;
        this.startingAddress = Util.toByteArray(startingAddress);

        byte[] data = new byte[16 + byteCount-1]; //todo 16 o 13?
        data[0] = this.transactionIdentifier[1];
        data[1] = this.transactionIdentifier[0];
        data[2] = this.protocolIdentifier[1];
        data[3] = this.protocolIdentifier[0];
        data[4] = this.length[1];
        data[5] = this.length[0];
        data[6] = this.unitIdentifier;
        data[7] = this.functionCode;
        data[8] = this.startingAddress[1];
        data[9] = this.startingAddress[0];
        data[10] = quantityOfOutputs[1];
        data[11] = quantityOfOutputs[0];
        data[12] = byteCount;
        for (int i = 0; i < values.length; i++) {
            if ((i % 8) == 0)
                singleCoilValue = 0;
            byte CoilValue;
            if (values[i]) // values[i]==true
                CoilValue = 1;
            else
                CoilValue = 0;

            singleCoilValue = (byte)((int)CoilValue<<(i%8) | (int)singleCoilValue);

            data[13 + (i / 8)] = singleCoilValue;
        }

        connexion.sendRequest(data);

        byte[] receiveData = connexion.receiveResponse(7+5);

        if(((receiveData[7] & 0xff) == 0x8F) && (receiveData[8] == 0x01)){
            System.out.println("FunctionCodeNotSupportedException");
            throw new IllegalStateException("FunctionCodeNotSupportedException");
        }

        if(((receiveData[7] & 0xff) == 0x8F) && (receiveData[8] == 0x02)){
            System.out.println("Starting adress invalid or starting adress + quantity invalid");
            throw new IllegalStateException("Starting adress invalid or starting adress + quantity invalid");
        }

        if(((receiveData[7] & 0xff) == 0x8F) && (receiveData[8] == 0x03)){
            System.out.println("Quantity invalid");
            throw new IllegalStateException("Quantity invalid");
        }

        if(((receiveData[7] & 0xff) == 0x8F) && (receiveData[8] == 0x04)){
            System.out.println("Error reading");
            throw new IllegalStateException("Error reading");
        }

    }

}

