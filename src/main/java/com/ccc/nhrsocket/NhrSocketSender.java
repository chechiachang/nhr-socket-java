/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccc.nhrsocket;

import com.ccc.nhr.test1.NhrDataService;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Arrays;

/**
 *
 * @author davidchang
 */
public class NhrSocketSender {

    Socket socket;
    public static void main(String args[]) throws IOException, SQLException, ClassNotFoundException {
        final String host = "192.168.16.146";
        final int portNumber = 10010;

        System.out.println("Creating socket to '" + host + "' on port " + portNumber);

        Socket socket = new Socket(host, portNumber);

        final NhrDataService nhrConnection = new NhrDataService.NhrConnectionBuilder(socket)
                .withInputBufferedReader(new BufferedReader(new InputStreamReader(socket.getInputStream())))
                .withDataInputStream(new DataInputStream(socket.getInputStream()))
                .withDataOutputStream(new DataOutputStream(socket.getOutputStream()))
                .build();


        nhrConnection.sendCommand();
    }
}
