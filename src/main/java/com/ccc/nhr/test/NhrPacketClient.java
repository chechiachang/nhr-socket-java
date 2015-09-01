/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccc.nhr.test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author davidchang
 */
public class NhrPacketClient {

    public static void main(String[] args) {
        ReceiveThread rt = new ReceiveThread();
        rt.start();
        
        //SenderThread st = new SenderThread();
        //st.start();
        
    }
}

class ReceiveThread extends Thread {

    final String host = "192.168.16.146";
    final int portNumber = 10010;

    public void run() {

        
        try {
            DatagramSocket ds = new DatagramSocket(portNumber);
            InetAddress ip = InetAddress.getByName(host);

            byte[] buf = new byte[30];
            DatagramPacket in;

            System.out.println("Started Receiving...");
            while (true) {
                in = new DatagramPacket(buf, 30);
                ds.receive(in);
                for (byte data : in.getData()) {
                    System.out.print(Integer.toHexString(data) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {

        }
    }
}

class SenderThread extends Thread {

    final String host = "127.0.0.1";
    final int portNumber = 10010;

    public void run() {
        try {
            DatagramSocket ds = new DatagramSocket(portNumber);
            InetAddress ip = InetAddress.getByName(host);

            DatagramPacket output;
            byte[] sendData = {
                (byte) 0x41,
                (byte) 0x42,
                (byte) 0x2b,
                (byte) 0x09,
                (byte) 0x08,
                (byte) 0x53,
                (byte) 0x45,
                (byte) 0xd0,
                (byte) 0xd1,
                (byte) 0x0c
            };
            output = new DatagramPacket(sendData, sendData.length, ip, portNumber);

            ds.send(output);

            System.out.println("Data sent.");
        } catch (Exception e) {
        }
    }
}
