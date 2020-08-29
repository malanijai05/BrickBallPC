package com.BrickBall;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.Graphics2D;
import java.io.File;

public class MapGenerator {
    public static int[][] Map;
    public static int BrickWidth;
    public static int BrickHeight;
    public MapGenerator(int row, int col) {
        Map = new int[row][col];
        for (int i = 0; i < Map.length; i++) {
            for (int j = 0; j < Map[0].length; j++) {
                Map[i][j] = 1;
            }
        }
        BrickHeight = 150 / row;
        BrickWidth = 540 / col;
    }
    public static void draw(Graphics2D g) {
        for (int i = 0; i < Map.length; i++) {
            for (int j = 0; j < Map[0].length; j++) {
                if (Map[i][j] > 0) {
                    g.setColor(Color.white);
                    g.fillRect(j * BrickWidth+70, i * BrickHeight+50, BrickWidth, BrickHeight);
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * BrickWidth+70, i * BrickHeight+50, BrickWidth, BrickHeight);
                }
            }
        }
    }
    public static void SetBirckValue(int row, int col){
        Map[row][col]=0;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("toy.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
}


