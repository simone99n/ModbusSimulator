package com.tesi.client.util;

public class Util {

    public static byte[] toByteArray(int value) {
        byte[] result = new byte[2];
        result[1] = (byte) (value >> 8); //shift di 8bit
        result[0] = (byte) (value);
        return result;
    }

}
