/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccc.nhr.test;

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
        input = scanner.nextLine().replace(",", "").replace(" ", "");
        int sum = 0;
        for (int i = 0; i < (input.length() / 2); i++) {
            sum += Integer.parseInt(input.substring(2 * i, 2 * (i + 1)), 16);
        }

        int balance = 255 - sum;
        while (balance < 0) {
            balance += 256;
        }
        System.out.println("sum = " + sum + ", balance = " + balance);
        System.out.println("checkbyte = " + Integer.toHexString(balance));
    }
}
