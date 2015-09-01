/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccc.nhr.test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author davidchang
 */
public class DReceiver {

    public static void main(String[] args) throws SocketException, IOException {
        DatagramSocket ds = new DatagramSocket(10010);
        byte[] buf = new byte[40];
        DatagramPacket dp = new DatagramPacket(buf, 40);
        ds.receive(dp);

        System.out.println(DatatypeConverter.printHexBinary(dp.getData()));
        /*
        for (byte data : dp.getData()) {
            System.out.print(Integer.toHexString(data) + " ");
        }
        */
        /*
         String str = new String(dp.getData(), 0, dp.getLength());
         System.out.println(str);
         */
        ds.close();
    }
}
