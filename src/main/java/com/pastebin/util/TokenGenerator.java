package com.pastebin.util;

import java.util.Random;

/**
 * Class for generating token. Token will be 4o char long, containing a-z, A-z and 0-9 digits
 */
public class TokenGenerator {

    private static char[] codec = "0123456789abcdefghijklmnopqrstuvwxyABCDEFGHJIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static final int defaultLength = 40;

    public static String getToken(){
        byte[] bytes = new byte[defaultLength];
        Random random = new Random();
        random.nextBytes(bytes);

        String result = "";

        for(int i = 0; i < defaultLength; i++){
            result += codec[(bytes[i] & 0XFF) % codec.length];
        }

        return result;
    }
}
