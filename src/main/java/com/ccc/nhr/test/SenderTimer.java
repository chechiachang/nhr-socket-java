/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccc.nhr.test;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author davidchang
 * DONE!
 */
public class SenderTimer {

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("send!");
            }
        }, 2 * 1000, 2 * 1000);
    }

}
