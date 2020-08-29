package com.BrickBall;

import javax.swing.*;
public class Main {

    public static void main(String[] args) {
        JFrame obj = new JFrame();
        //JFrame scr = new JFrame();
        MainScreen mainScreen = new MainScreen();
        GamePlay gamePlay = new GamePlay();
        obj.setBounds(10,10,950,600);
        obj.setTitle("BrickBall");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //scr.add(mainScreen);
        obj.add(gamePlay);
    }
}