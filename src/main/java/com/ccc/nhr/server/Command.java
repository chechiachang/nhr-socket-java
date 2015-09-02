/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccc.nhr.server;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author davidchang
 */
public class Command {

    public String asciiToHex(String ascii) {
        char[] chars = ascii.toCharArray();
        String hex = "";
        for (int i = 0; i < chars.length; i++) {
            hex += " " + (Integer.toHexString((int) chars[i]));
        }
        return hex;
    }

    //Coordinator Local
    public String go(String cmd) {
        String out = "41 54 2b " //AT+
                + asciiToHex(String.valueOf(7)) //length 07
                + " 08" //frame 08
                + asciiToHex(cmd); // cmd A A
        //count checkbyte
        return out + " " +;
    }

    //A10 Siren
    public String go(String mac, String shortMac, String clusterId, String cmd, String duration) {
        return "";
    }

    /*
     public static void main(String[] args) {
     Command command = new Command();
     System.out.println(command.asciiToHex("ATRC"));
     }
     */
}
