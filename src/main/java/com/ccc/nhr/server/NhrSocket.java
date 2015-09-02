/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccc.nhr.server;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davidchang
 */
public class NhrSocket {

    public static void main(String args[]) throws IOException, SQLException, ClassNotFoundException {

        final String host = "192.168.16.146";
        final int portNumber = 10010;

        Socket socket = new Socket(host, portNumber);
        System.out.println("Creating socket to '" + host + "' on port " + portNumber);

        final NhrDataService nhrDataService = new NhrDataService(socket);
        final NhrCommandService ncs = new NhrCommandService(socket);

        ReceiverThread receiverThread = new ReceiverThread();
        receiverThread.setNhrConnection(nhrDataService);
        receiverThread.start();

        SenderThread senderThread = new SenderThread();
        senderThread.setNhrCommandService(ncs);
        senderThread.start();

    }
}

class SenderThread extends Thread {

    NhrCommandService ncs;

    public void setNhrCommandService(NhrCommandService ncs) {
        this.ncs = ncs;
    }

    @Override
    public void run() {
        try {
            ncs.sendCommand();
        } catch (IOException ex) {
            Logger.getLogger(SenderThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class ReceiverThread extends Thread {

    NhrDataService nds;

    public void setNhrConnection(NhrDataService nds) {
        this.nds = nds;
    }

    @Override
    public void run() {
        try {
            nds.getScannerRequest();
            //nds.getRequest();
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ReceiverThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
