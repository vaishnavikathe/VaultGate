package com.vaultgate.util;

public class EncryptionUtil {

    public static String encrypt(String data, String password) {
        return data + "_encrypted";
    }

    public static String decrypt(String data, String password) {
        return data.replace("_encrypted", "");
    }
}