package com.fh.util;

import java.util.Random;

public class RandomCode {
    private static final String codes ="0123456789";

    public static   String getCodes(){
        char[] chars=new char[6];
        Random random = new Random();
        for (int i = 0; i < chars.length; i++) {
            chars[i]=codes.charAt(  random.nextInt(codes.length()));
        }
        return new String(chars);
    }
}
