/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccc.nhr.test;

import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author davidchang
 */
public class TestByte {

    public static void main(String[] args) {
        String out = "";
        String in = "41542b09085345d0d10c";
        //byte[] cmd = {0x41, 0x54, 0x2b, 0x09, 0x08, 0x53, 0x46, 0xd0, 0xd1, 0x0c};
        byte[] cmd = hexStringToByteArray(in);
        /*
         for (String s : cmd) {
         int i = 0;
         out += Integer.parseInt(s, 16) + " ";
         System.out.println(out);
         }
         */
        System.out.println(Arrays.toString(cmd));

    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }
}
