package com.tesi.client;


//import com.tesi.client.entities.RequestBase;
import com.tesi.client.entities.RequestRead;
import com.tesi.client.entities.RequestSingleCoil;
import com.tesi.client.entities.RequestMultipleCoils;
import com.tesi.client.services.*;

import java.io.IOException;
import java.net.InetAddress;

public class Test {

    public static void main(String[] args) throws IOException {

        //args[0] --> ip address
        //args[1] --> starting address
        //args[2] --> quantity

        if (args.length < 3) {
            System.out.println("usage: The args[] will be: indirizzo_ip starting_address quantity");
            return;
        }


        RequestRead requestRead = new RequestRead(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        RequestSingleCoil requestCoil = new RequestSingleCoil(InetAddress.getByName(args[0]), Integer.parseInt(args[1]), true);
        RequestMultipleCoils requestMultipleCoils = new RequestMultipleCoils(InetAddress.getByName(args[0]), 2, new boolean[]{true, true, true});

        InetAddress address = InetAddress.getByName(args[0]);
        int startAddr = Integer.parseInt(args[1]);
        int quant = Integer.parseInt(args[2]);


        ReadCoils prova1 = new ReadCoils();
        boolean[] risposta1 = prova1.readCoils(requestRead);
        System.out.println("[READ COILS]");
        for(int i=0; i<quant;i++) {
            System.out.println("Addr 0x" + Integer.toHexString(i) + " : " +risposta1[i]);
        }
        System.out.println("**********************");

        ReadDiscreteInputs prova2 = new ReadDiscreteInputs();
        boolean[] risposta2 = prova2.readDiscreteInputs(requestRead);
        System.out.println("[READ DISCRETE INPUT]");
        for(int i=0; i<quant;i++) {
            System.out.println("Addr 0x" + Integer.toHexString(i) + " : " +risposta2[i]);
        }
        System.out.println("**********************");

        ReadHoldingRegisters prova3 = new ReadHoldingRegisters();
        int[] risposta3 = prova3.readHoldingRegisters(requestRead);
        System.out.println("[READ HOLDING REGISTER]");
        for(int i=0; i<quant;i++) {
            System.out.println("Addr 0x" + Integer.toHexString(i) + " : " +risposta3[i]);
        }
        System.out.println("**********************");

        ReadInputRegisters prova4 = new ReadInputRegisters();
        int[] risposta4 = prova4.readInputRegisters(requestRead);
        System.out.println("[READ INPUT REGISTER]");
        for(int i=0; i<quant;i++) {
            System.out.println("Addr 0x" + Integer.toHexString(i) + " : " +risposta4[i]);
        }
        System.out.println("**********************");

        WriteSingleCoil prova5 = new WriteSingleCoil();
        prova5.writeSingleCoil(requestCoil);
        System.out.println("[WRITE SINGLE COIL]");
        System.out.println("**********************");

        WriteMultipleCoils prova6 = new WriteMultipleCoils();
        prova6.writeMultipleCoils(requestMultipleCoils);
        System.out.println("[WRITE MULTIPLE COIL]");
        System.out.println("**********************");

    }
}