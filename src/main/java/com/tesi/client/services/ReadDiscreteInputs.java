package com.tesi.client.services;

import com.tesi.client.connection.SocketTCP;
import com.tesi.client.entities.RequestRead;
import com.tesi.client.util.Util;

import java.io.IOException;
import java.net.InetAddress;

public class ReadDiscreteInputs {

    private byte [] transactionIdentifier = new byte[2];
    private byte [] protocolIdentifier = new byte[2];
    private byte [] length = new byte[2];
    private byte unitIdentifier = 1;
    private byte functionCode;
    private byte [] startingAddress = new byte[2];
    private byte [] quantity = new byte[2];

    public boolean[] readDiscreteInputs(RequestRead request) throws IOException {

        InetAddress ip = InetAddress.getByName(request.getIp());
        int startingAddress = request.getStartingAddress();
        int quantity = request.getQuantity();

        SocketTCP connexion = new SocketTCP(ip);

        if (!connexion.isEstablishedConnexion()) {
            System.out.println("Error in TCP connection");
            throw new IllegalStateException("Error in TCP connection");
        }

        if (startingAddress > 65535 | quantity > 2000){
            System.out.println("Starting adress must be 0 - 65535; quantity must be 0 - 2000");
            throw new IllegalStateException("Starting adress must be 0 - 65535; quantity must be 0 - 125");
        }

        boolean[] response = new boolean[quantity];
        this.transactionIdentifier = Util.toByteArray(0x0001);
        this.protocolIdentifier = Util.toByteArray(0x0000);
        this.length = Util.toByteArray(0x0006);
        //unitIdentifier
        this.functionCode = 0x02;
        this.startingAddress = Util.toByteArray(startingAddress);
        this.quantity = Util.toByteArray(quantity);

        byte[] data = new byte[]
                {
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
                        this.quantity[1],
                        this.quantity[0]
                };

        connexion.sendRequest(data);

        byte[] receiveData = connexion.receiveResponse(7+2+quantity);

        if(((receiveData[7] & 0xff) == 0x82) && (receiveData[8] == 0x01)){
            System.out.println("FunctionCodeNotSupportedException");
            throw new IllegalStateException("FunctionCodeNotSupportedException");
        }

        if(((receiveData[7] & 0xff) == 0x82) && (receiveData[8] == 0x02)){
            System.out.println("Starting adress invalid or starting adress + quantity invalid");
            throw new IllegalStateException("Starting adress invalid or starting adress + quantity invalid");
        }

        if(((receiveData[7] & 0xff) == 0x82) && (receiveData[8] == 0x03)){
            System.out.println("Quantity invalid");
            throw new IllegalStateException("Quantity invalid");
        }

        if(((receiveData[7] & 0xff) == 0x82) && (receiveData[8] == 0x04)){
            System.out.println("Error reading");
            throw new IllegalStateException("Error reading");
        }

        for (int i = 0; i < quantity; i++) {
            int intData = (int) receiveData[9 + i/8];
            int mask = (int)Math.pow(2, (i%8));
            intData = ((intData & mask)/mask); //algoritmo che è meglio non capire
            if (intData >0)
                response[i] = true;
            else
                response[i] = false;
        }

        return (response);


    }
}
