package com.tesi.client.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SocketTCP {

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public SocketTCP(InetAddress ip) throws IOException {
        Socket socket = new Socket(ip, 5020);
        System.out.println("!!!-Client Modbus avviato-!!!");
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    public void sendRequest(byte[] request) throws IOException {
        outputStream.write(request);
        System.out.println("Send request...");
    }

    public byte[] receiveResponse(int dim) throws IOException {
        byte[] receiveBuffer = new byte[dim];
        inputStream.read(receiveBuffer);
        System.out.println("Response receive..");
        return receiveBuffer;
    }

    public void closeConnexion() throws IOException {
        socket.close();
    }

    public boolean isEstablishedConnexion() {
        return socket.isConnected();
    }

}