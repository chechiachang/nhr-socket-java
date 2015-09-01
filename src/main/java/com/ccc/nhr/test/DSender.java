/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccc.nhr.test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author davidchang
 */
public class DSender {

    public static void main(String[] args) throws Exception {
        DatagramSocket ds = new DatagramSocket();
        String str = "Welcome Java";
        byte data = 0x51;
        byte[] datas = {0x41, 0x42, 0x2b, 0x09, 0x08, 0x53, 0x45, (byte) 0xd0, (byte) 0xd1, 0x0c};
        InetAddress ip = InetAddress.getByName("127.0.0.1");
        int i = 0;
        //DatagramPacket dp = new DatagramPacket(str.getBytes(), str.length(), ip, 3000);
        //System.out.println(datas.length);
        DatagramPacket dp = new DatagramPacket(datas, datas.length, ip, 10010);
        ds.send(dp);
        i++;
        ds.close();
    }
}
