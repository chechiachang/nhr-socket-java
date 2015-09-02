/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccc.nhr.test1;

import com.ccc.nhr.test1.NhrConnection.NhrConnectionBuilder;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;

/**
 *
 * @author davidchang
 */
public class NhrSocket {

    public static void main(String args[]) throws IOException, SQLException, ClassNotFoundException {

        final String host = "192.168.16.146";
        final int portNumber = 10010;

        System.out.println("Creating socket to '" + host + "' on port " + portNumber);
        Socket socket = new Socket(host, portNumber);

        final NhrConnection nhrConnection = new NhrConnectionBuilder(socket)
                .withInputBufferedReader(new BufferedReader(new InputStreamReader(socket.getInputStream())))
                .withDataInputStream(new DataInputStream(socket.getInputStream()))
                .withDataOutputStream(new DataOutputStream(socket.getOutputStream()))
                .build();

        ReceiverThread receiverThread = new ReceiverThread();
        receiverThread.setNhrConnection(nhrConnection);
        receiverThread.start();

        SenderThread senderThread = new SenderThread();
        senderThread.setNhrConnection(nhrConnection);
        senderThread.start();

    }
}

class SenderThread extends Thread {

    NhrConnection nhrConnection;

    public void setNhrConnection(NhrConnection nhrConnection) {
        this.nhrConnection = nhrConnection;
    }

    @Override
    public void run() {
        /*
         Timer timer = new Timer();
         timer.scheduleAtFixedRate(new TimerTask() {
         @Override
         public void run() {
         try {
         nhrConnection.sendCommand();
         } catch (Exception e) {

         }
         }
         }, 2 * 1000, 2 * 1000);
         */
        try {
            nhrConnection.sendCommand();
        } catch (Exception e) {

        }
    }
}

class ReceiverThread extends Thread {

    NhrConnection nhrConnection;

    public void setNhrConnection(NhrConnection nhrConnection) {
        this.nhrConnection = nhrConnection;
    }

    @Override
    public void run() {
        try {
            nhrConnection.getScannerRequest();
            //nhrConnection.getRequest();
        } catch (Exception e) {

        }
    }
}
