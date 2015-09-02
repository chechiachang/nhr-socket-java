/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccc.nhr.test1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author davidchang
 */
public class NhrDataService {

    private final Socket socket;
    private final BufferedReader inputBufferedReader;
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;

    boolean isPrintout = false;

    String s = null;
    String[] scanner = {"0", "0", "0", "0"};
    String[] output = new String[33];

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/nhr?connectTimeout=3000";
    //  Database credentials
    static final String USER = "nhr";
    static final String PASS = "25ac7375c1fd64eca8dd8cf309071c0d";
    private static final long serialVersionUID = 1L;

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs;

    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getInputBufferedReader() {
        return inputBufferedReader;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public void getRequest() throws IOException {
        String str = null;
        System.out.print(new Date() + " -> ");
        while ((str = Integer.toHexString(dataInputStream.read())) != null) {
            if ("41".equals(str)) {
                System.out.println();
                System.out.print(new Date() + " -> ");
            }
            if (str.length() == 1) {
                str = "0" + str;
            }
            System.out.print(str + " ");
        }
    }

    public void getScannerRequest() throws IOException, SQLException, ClassNotFoundException {
        if (isPrintout) {
            System.out.print(new Date() + " -> ");
        }
        int dataLength = 10;   //any number larger than 5 
        int count = 0;
        boolean end = false;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            while ((s = Integer.toHexString(dataInputStream.read())) != null) {
                //
                if (isPrintout && end == true) {
                    System.out.println();
                    System.out.print(new Date() + " -> ");
                }

                //head scanner
                scanner[0] = scanner[1];
                scanner[1] = scanner[2];
                scanner[2] = scanner[3];
                scanner[3] = s;

                //refresh dataLength
                if (scanner[0].equals("41") && scanner[1].equals("54") && scanner[2].equals("2b")) {
                    dataLength = Integer.parseInt(s, 16);
                    count = 3;
                    output = new String[dataLength + 3];
                    output[0] = "41";
                    output[1] = "54";
                    output[2] = "2b";
                }

                //output
                if (s.length() == 1) {
                    s = "0" + s;
                }
                output[count] = s;
            //System.out.print(s + " ");

                /*
                 //if read head
                 if (count < 4) {

                 } else //if finished read frame type
                 if (count == 4) {

                 } else //if finished read device IEEE address
                 if (count == 12) {

                 } else //if finished read device short address
                 if (count == 14) {

                 } else //if finished read Source Endpoint
                 if (count == 15) {

                 } else //if finished read Destination Endpoint
                 if (count == 16) {

                 } else //if finished read Cluster ID
                 if (count == 18) {

                 } else //if finished read Profile ID
                 if (count == 20) {

                 } else //if finished read Recieve options
                 if (count == 21) {

                 } else //if finished read Frame Control byte
                 if (count == 22) {

                 } else //if finished read Transit sequence number
                 if (count == 23) {

                 } else //if finished read ZCL command
                 if (count == 24) {

                 } else //if finished read Attribute ID
                 if (count == 26) {

                 } else //if finished read data type
                 if (count == 27) {

                 } else //if finished read value
                 if (count == 29) {

                 } else //if finished read check byte
                 if (count == 30) {
                
                 }
                 */
                //if at the end of data
                if (end = (count == dataLength)) {
                    String out = "";
                    System.out.println(Arrays.toString(output));

                    //switch Cluster ID
                    switch (output[17]) {
                        case "00":
                            switch (output[18]) {
                                case "01":  //ZigBee Cluster Library power configuration cluster ID
                                    break;
                            }
                            break;
                        case "04":
                            switch (output[18]) {
                                case "05":  //0405 ZigBee Cluster Library relative humidity measurement cluster ID
                                    String humid = String.valueOf(Integer.parseInt(output[29] + output[28], 16));

                                    pstmt = conn.prepareStatement("INSERT INTO `data` (`mac_cluster_id`, `short_mac`, `cluster_id`, `data`) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE data=VALUES(data)");
                                    pstmt.setString(1, returnOutput(5, 12) + "0405");
                                    pstmt.setString(2, returnOutput(13, 14));
                                    pstmt.setString(3, "0405");
                                    pstmt.setString(4, humid.substring(0, 2) + "." + humid.substring(2, 4));
                                    pstmt.executeUpdate();

                                    if (isPrintout) {
                                        out = "Mac Address: " + returnOutput(5, 12) + " Device Short Mac: " + returnOutput(13, 14) + " Cluster ID: " + "0405" + " Data: " + humid.substring(0, 2) + "." + humid.substring(2, 4) + " %";
                                        System.out.println(out);
                                    }
                                    break;
                                case "02":  //ZigBee Cluster Library relative humidity measurement cluster ID 0x0405
                                    String temp = String.valueOf(Integer.parseInt(output[29] + output[28], 16));

                                    pstmt = conn.prepareStatement("INSERT INTO `data` (`mac_cluster_id`, `short_mac`, `cluster_id`, `data`) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE data=VALUES(data)");
                                    pstmt.setString(1, returnOutput(5, 12) + "0402");
                                    pstmt.setString(2, returnOutput(13, 14));
                                    pstmt.setString(3, "0402");
                                    pstmt.setString(4, temp.substring(0, 2) + "." + temp.substring(2, 4));
                                    pstmt.executeUpdate();

                                    if (isPrintout) {
                                        out = "Mac Address: " + returnOutput(5, 12) + " Device Short Mac: " + returnOutput(13, 14) + " Cluster ID: " + "0402" + " Data: " + temp.substring(0, 2) + "." + temp.substring(2, 4) + " Celcius";
                                        System.out.println(out);
                                    }
                                    break;
                            }
                            break;
                        case "05":
                            switch (output[18]) {
                                case "00":  //0500 (ZCL_CLUSTER_ID_SS_IAS_ZONE)
                                    switch (output[26]) {
                                        case "03":
                                            pstmt = conn.prepareStatement("INSERT INTO `data` (`mac_cluster_id`, `short_mac`, `cluster_id`, `data`) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE data=VALUES(data)");
                                            pstmt.setString(1, returnOutput(5, 12) + "0500");
                                            pstmt.setString(2, returnOutput(13, 14));
                                            pstmt.setString(3, "0500");
                                            pstmt.setString(4, "on");
                                            pstmt.executeUpdate();
                                            if (isPrintout) {
                                                System.out.println("Mac Address: " + returnOutput(5, 12) + " Device Short Mac: " + returnOutput(13, 14) + " Cluster ID: " + "0500" + " Data: " + "on");
                                            }
                                            break;
                                        case "00":
                                            pstmt = conn.prepareStatement("INSERT INTO `data` (`mac_cluster_id`, `short_mac`, `cluster_id`, `data`) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE data=VALUES(data)");
                                            pstmt.setString(1, returnOutput(5, 12) + "0500");
                                            pstmt.setString(2, returnOutput(13, 14));
                                            pstmt.setString(3, "0500");
                                            pstmt.setString(4, "off");
                                            pstmt.executeUpdate();
                                            if (isPrintout) {
                                                System.out.println("Mac Address: " + returnOutput(5, 12) + " Device Short Mac: " + returnOutput(13, 14) + " Cluster ID: " + "0500" + " Data: " + "off");
                                            }
                                            break;
                                        case "08":
                                            pstmt = conn.prepareStatement("INSERT INTO `data` (`mac_cluster_id`, `short_mac`, `cluster_id`, `data`) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE data=VALUES(data)");
                                            pstmt.setString(1, returnOutput(5, 12) + "0500");
                                            pstmt.setString(2, returnOutput(13, 14));
                                            pstmt.setString(3, "0500");
                                            pstmt.setString(4, "on/low");
                                            pstmt.executeUpdate();
                                            if (isPrintout) {
                                                System.out.println("Mac Address: " + returnOutput(5, 12) + " Device Short Mac: " + returnOutput(13, 14) + " Cluster ID: " + "0500" + " Data: " + "on / low battery");
                                            }
                                            break;
                                        case "0c":
                                            pstmt = conn.prepareStatement("INSERT INTO `data` (`mac_cluster_id`, `short_mac`, `cluster_id`, `data`) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE data=VALUES(data)");
                                            pstmt.setString(1, returnOutput(5, 12) + "0500");
                                            pstmt.setString(2, returnOutput(13, 14));
                                            pstmt.setString(3, "0500");
                                            pstmt.setString(4, "off/low");
                                            pstmt.executeUpdate();
                                            if (isPrintout) {
                                                System.out.println("Mac Address: " + returnOutput(5, 12) + " Device Short Mac: " + returnOutput(13, 14) + " Cluster ID: " + "0500" + " Data: " + "off / low battery");
                                            }
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                    count = 0;

                }
                //move on count
                count++;
            }
        } catch (SQLException e) {

        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException se2) {
                se2.printStackTrace();
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
    }

    String returnOutput(int startIndex, int endIndex) {
        String text = "";
        for (int i = startIndex; i < endIndex + 1; i++) {
            text = text + output[i];
        }
        return text;
    }

    public void sendCommand() throws IOException {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String request = scanner.next();
            String temp;
            byte[] cmd;
            switch (request) {
                case "sirenon":
                    temp = "41 54 2B 18 12 00 12 4b 00 05 a7 bb b8 d0 b5 02 02 05 02 01 04 00 00 10 00 01 f3";
                    cmd = hexStringToByteArray(temp.replace(" ", ""));
                    break;
                case "coordinatormac":
                    temp = "41542b07 08 524146";
                    cmd = hexStringToByteArray(temp.replace(" ", ""));
                    break;
                case "query":
                    temp = "41542b07 08 4446a6";
                    cmd = hexStringToByteArray(temp.replace(" ", ""));
                    break;
                case "coordinatorid":
                    temp = "41542b0708524943";
                    cmd = hexStringToByteArray(temp.replace(" ", ""));
                    break;
                default:
                    cmd = hexStringToByteArray("");
                    break;
            }

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

    public static class NhrConnectionBuilder {

        private final Socket socket;
        private BufferedReader inputBufferedReader;
        private DataOutputStream dataOutputStream;
        private DataInputStream dataInputStream;

        public NhrConnectionBuilder(Socket socket) {
            this.socket = socket;
        }

        public NhrConnectionBuilder withInputBufferedReader(BufferedReader inputBufferedReader) {
            this.inputBufferedReader = inputBufferedReader;
            return this;
        }

        public NhrConnectionBuilder withDataOutputStream(DataOutputStream dataOutputStream) {
            this.dataOutputStream = dataOutputStream;
            return this;
        }

        public NhrConnectionBuilder withDataInputStream(DataInputStream dataInputStream) {
            this.dataInputStream = dataInputStream;
            return this;
        }

        public NhrDataService build() {
            return new NhrDataService(this);
        }

    }

    private NhrDataService(NhrConnectionBuilder builder) {
        this.socket = builder.socket;
        this.inputBufferedReader = builder.inputBufferedReader;
        this.dataOutputStream = builder.dataOutputStream;
        this.dataInputStream = builder.dataInputStream;
    }
}
