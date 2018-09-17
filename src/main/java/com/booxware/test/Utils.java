package com.booxware.test;

public class Utils {

    // it is mentioned that " The encryption can be very simple, we don't put much emphasis on the
    // * encryption algorithm." so we kept it simple.
    public static byte[] hash(String password){
        byte[] bytes = password.getBytes();
        for(int i =0;i<bytes.length;i++){
            bytes[i] = (byte)(((int)bytes[i])%10);
        }
        return bytes;
    }

}
