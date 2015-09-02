/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccc.nhr.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author davidchang
 */
public class NhrCommandService {

    private final Socket socket;

    public NhrCommandService(Socket socket) {
        this.socket = socket;
    }

    public void sendCommand() throws IOException {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String request = scanner.next();
            String temp = "";
            switch (request) {
                case "query"://DF query the device list under coordinator
                    temp = "41 54 2b 07 08 44 46 a6";
                    break;
                case "channel"://RC read coordinator operation channel
                    temp = "41 54 2b 07 08 52 43 9b";
                    break;
                case "operationid"://RI read coordinator operation pain id
                    temp = "41 54 2b 07 08 52 49 95";
                    break;
                case "extendid"://RE read coordinator extend id
                    temp = "41 54 2b 07 08 52 45 99";
                    break;
                case "mac"://RA read coordinator IEEE address
                    temp = "41 54 2b 07 08 52 41 9d";
                    break;
                case "sirenon":
                    temp = "41 54 2B 18 12 00 12 4b 00 05 a7 bb b8 d0 b5 02 02 05 02 01 04 00 00 10 00 01 f3";
                    break;
                default:
                    break;
            }
            byte[] cmd = hexStringToByteArray(temp.replace(" ", ""));
            OutputStream socketOutputStream = socket.getOutputStream();
            socketOutputStream.write(cmd);
            socketOutputStream.flush();

            System.out.print("Client cmd : " + DatatypeConverter.printHexBinary(cmd));
            System.out.println();
        }
    }

    public byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
