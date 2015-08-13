/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccc.nhrsocket;

/**
 *
 * @author davidchang
 */

import java.net.*;


public class PortMonitor {

    /**
     * JavaProgrammingForums.com
     */
    public static void main(String[] args) throws Exception {

        //Port to monitor
        final int myPort = 10010;
        //wheather to print out

        ServerSocket ssock = new ServerSocket(myPort);

        System.out.println(
                "port " + myPort + " opened");

        Socket sock = ssock.accept();

        System.out.println(
                "Someone has made socket connection");

        OneConnection client = new OneConnection(sock);
        String s = client.getScannerRequest();

    }

}
