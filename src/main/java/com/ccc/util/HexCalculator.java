/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccc.util;

import com.ccc.nhr.test.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author davidchang
 */
public class HexCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        while ((input = scanner.nextLine()).length() > 0) {
            String replace = input.replace(",", "").replace(" ", "");
            int sum = 0;
            for (int i = 0; i < (replace.length() / 2); i++) {
                sum += Integer.parseInt(replace.substring(2 * i, 2 * (i + 1)), 16);
            }

            int balance = 255 - sum;
            while (balance < 0) {
                balance += 256;
            }
            System.out.println("sum = " + sum + ", balance = " + balance);
            System.out.println("checkbyte = " + Integer.toHexString(balance));
        }
    }
}
