package com.tesi.client.services;
//import com.tesi.client.entities.RequestBase;
import com.tesi.client.entities.RequestRead;
import com.tesi.client.util.Util;
import com.tesi.client.connection.*;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;


public class ReadInputRegisters {

    private byte [] transactionIdentifier = new byte[2];
    private byte [] protocolIdentifier = new byte[2];
    private byte [] length = new byte[2];
    private byte unitIdentifier = 1;
    private byte functionCode;
    private byte [] startingAddress = new byte[2];
    private byte [] quantity = new byte[2];
    //TODO al posto di scrivere questo casino si potrebbe buttare tutto nella classe ModbusPDU

    //private List<byte[]> receiveDataChangedListener = new ArrayList<byte[]>();

    public int[] readInputRegisters(RequestRead request) throws IOException {

        InetAddress ip = InetAddress.getByName(request.getIp());
        int startingAddress = request.getStartingAddress();
        int quantity = request.getQuantity();

        SocketTCP connexion = new SocketTCP(ip);

        if (!connexion.isEstablishedConnexion()) {
            System.out.println("Error in TCP connection");
            throw new IllegalStateException("Error in TCP connection");
        }

        if (startingAddress > 65535 | quantity > 125) {
            System.out.println("Starting adress must be 0 - 65535; quantity must be 0 - 125");
            throw new IllegalStateException("Starting adress must be 0 - 65535; quantity must be 0 - 125");
        }

        int[] response = new int[quantity];

        this.transactionIdentifier = Util.toByteArray(0x0001);
        this.protocolIdentifier = Util.toByteArray(0x0000);
        this.length = Util.toByteArray(0x0006);
        //unitIdentifier
        this.functionCode = 0x04;
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

        //TODO gestire TIME-OUT

        byte[] receiveData = connexion.receiveResponse(7+2+(2*quantity));

        if(((receiveData[7] & 0xff) == 0x84) && (receiveData[8] == 0x01)){
            System.out.println("FunctionCodeNotSupportedException");
            throw new IllegalStateException("FunctionCodeNotSupportedException");
        }

        if(((receiveData[7] & 0xff) == 0x84) && (receiveData[8] == 0x02)){
            System.out.println("Starting adress invalid or starting adress + quantity invalid");
            throw new IllegalStateException("Starting adress invalid or starting adress + quantity invalid");
        }

        if(((receiveData[7] & 0xff) == 0x84) && (receiveData[8] == 0x03)){
            System.out.println("Quantity invalid");
            throw new IllegalStateException("Quantity invalid");
        }

        if(((receiveData[7] & 0xff) == 0x84) && (receiveData[8] == 0x04)){
            System.out.println("Error reading");
            throw new IllegalStateException("Error reading");
        }

        for (int i = 0; i < quantity; i++)
        {
            byte[] bytes = new byte[2];
            bytes[0] = (byte) receiveData[9+i*2];
            bytes[1] = (byte) receiveData[9+i*2+1];
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            response[i] = byteBuffer.getShort();
        }

        connexion.closeConnexion();
        return (response);
    }


}
