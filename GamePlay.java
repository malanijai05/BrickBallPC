package com.BrickBall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import javax.swing.Timer;
public class GamePlay extends JPanel implements KeyListener, ActionListener {
    public static boolean
            play = false;
    private int score = 0;
    public int r=2;
    public int c=5;
    public int level=1;
    private int TotalBricks = r*c;
    private Timer time;
    public int delay = 12;
    private int PlayerX = 280;
    private int BallPosX = 320;
    private int BallPosY = 530;
    private int BallDirX = 0;
    private int BallDirY = -2;
    private int life=2;
    private int X;
    private MapGenerator map;
    private Audio song;
    public GamePlay(){
        map = new MapGenerator(r,c);
        song = new Audio();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        time = new Timer(delay,this);
        time.start();
        Audio.backGround();
    }

    public void paint(Graphics g){
        //BackGroud
        g.setColor(Color.black);
        g.fillRect(1,1,950,592);

        //Map
        map.draw((Graphics2D)g);

        //Borders
        g.setColor(Color.red);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,682,3);
        g.fillRect(681,0,3,592);
        //Score
        if(TotalBricks >= 0 && BallPosY < 570) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Score: " + score, 700, 30);
            g.drawString("Level: "+ level,700,80);
            g.drawString("Bricks Left: " + TotalBricks, 700, 130);
        }
        //Keys
        if(play==false){
            g.setColor(Color.WHITE);
            g.setFont(new Font("serif", Font.BOLD, 15));
            g.drawString("Press Arrow Key To Move", 700, 200);
            g.drawString("paddle.", 700, 220);
            g.setFont(new Font("serif", Font.BOLD, 15));
            g.drawString("Press \"A\" & \"D\" To Change", 700, 240);
            g.drawString("Direction", 700, 260);
        }
        //Life
        g.setColor(Color.red);
        if(life>=0) {
            g.fillOval(850, 15, 15, 15);
        }
        if(life>=1) {
            g.fillOval(870, 15, 15, 15);
        }
        if(life>=2) {
            g.fillOval(890, 15, 15, 15);
        }
        //dir
        if(play==false && life>=0 && TotalBricks!=0){
            if(BallDirX>0)
                X=-BallDirX;
            else
                X=BallDirX;
            g.setColor(Color.WHITE);
            g.fillOval(BallPosX+4*BallDirX+5,BallPosY-3*X-20,12,12);
            g.fillOval(BallPosX+6*BallDirX+BallDirX+6,BallPosY-5*X-36,10,10);
            g.fillOval(BallPosX+8*BallDirX+2*BallDirX+7,BallPosY-7*X-50,8,8);
        }

        //Paddle
        g.setColor(Color.GREEN);
        g.fillRect(PlayerX,550,100,8);

        //Ball
        g.setColor(Color.YELLOW);
        g.fillOval(BallPosX,BallPosY,20,20);

        if(TotalBricks==0){
            PlayerX = 280;
            BallPosX = 320;
            BallPosY = 530;
        }
        if(TotalBricks<=0){
            BallDirY=0;
            BallDirX=0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,40));
            g.drawString("level "+level+" Complete "+" Score: "+score,150,300);
            g.setColor(Color.GREEN);
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter For Next Level",230,350);
        }

        if(BallPosY>570 && life<=0){
            life--;
            BallDirY=0;
            BallDirX=0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over, Score: "+score,190,300);
            g.drawString("Level: "+level,190,350);
            g.setColor(Color.GREEN);
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart Game",230,400);
        }
        if(BallPosY>570 && life>=1){
            life--;
            play=false;
            BallPosY=530;
            BallPosX=PlayerX+40;
        }
        if(play==false && life>=0 && TotalBricks!=r*c && TotalBricks>0){
            g.setColor(Color.green);
            g.setFont(new Font("serif",Font.BOLD,40));
            g.drawString("Press Space to Continue",170,300);
        }
        if(TotalBricks==r*c && play==false){
            g.setColor(Color.green);
            g.setFont(new Font("serif",Font.BOLD,40));
            g.drawString("Press Enter to Start",190,300);
        }

        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        time.start();
        if(play){
            if(new Rectangle(BallPosX,BallPosY,20,20).intersects(new Rectangle(PlayerX,550,100,8))){
                BallDirY=-BallDirY;
            }
            A: for(int i=0;i<map.Map.length;i++){
                for(int j=0;j<map.Map[0].length;j++){
                    if(map.Map[i][j]>0){
                        int BrickX = j*map.BrickWidth+70;
                        int BrickY = i*map.BrickHeight+50;
                        int BrickWidth = map.BrickWidth;
                        int BrickHeight = map.BrickHeight;
                        Rectangle Rect = new Rectangle(BrickX,BrickY,BrickWidth,BrickHeight);
                        Rectangle BallRect = new Rectangle(BallPosX,BallPosY,20,20);
                        Rectangle BrickRect = Rect;

                        if(BallRect.intersects(BrickRect)){
                            map.SetBirckValue(i,j);
                            TotalBricks--;
                            score+=5;
                            if(BallPosX+19 <= BrickRect.x || BallPosX+1 >= BrickRect.x+BrickRect.width){
                                BallDirX=-BallDirX;
                            }else {
                                BallDirY = -BallDirY;
                            }
                            break A;
                        }
                    }
                }
            }
            BallPosX+=BallDirX;
            BallPosY+=BallDirY;
            if(BallPosX<0){
                BallDirX=-BallDirX;
            }
            if(BallPosY<0){
                BallDirY=-BallDirY;
            }
            if(BallPosX>670){
                BallDirX=-BallDirX;
            }
        }
        repaint();

    }
    @Override
    public void keyReleased(KeyEvent e) { }
    @Override
    public void keyTyped(KeyEvent e) { }
    @Override
    public void keyPressed(KeyEvent e) {
    if(e.getKeyCode()==KeyEvent.VK_RIGHT){
        if(PlayerX >= 580){
            PlayerX = 580;
        }
        else{
            moveRight();
        }
    }
    if(e.getKeyCode()==KeyEvent.VK_LEFT){
        if(PlayerX <= 10){
            PlayerX = 3;
        }
        else{
            moveLeft();
        }
    }
    if(e.getKeyCode()==KeyEvent.VK_ENTER){
        if(!play && TotalBricks==r*c) {
            play = true;
        }
        if(life<0 && !play){
            life = 2;
            level = 1;
            score = 0;
            r = 2;
            c = 5;
            delay = 10;
        }
        if(TotalBricks==0)
            {
                play=false;
                PlayerX = 280;
                BallPosX = 320;
                BallPosY = 530;
                BallDirX = 0;
                BallDirY = -2;
                if(TotalBricks <= 0){
                    level++;
                    if(level%3==0){
                        r++;
                    }
                    else
                        c++;
                    if(level%5==0){
                        BallDirY-=1;
                    }
                }
                TotalBricks = r*c;
                map = new MapGenerator(r,c);
            }
        if(life<0){
            play=false;
            PlayerX = 280;
            BallPosX = 320;
            BallPosY = 530;
            BallDirX = 0;
            BallDirY = -2;
            r=2;
            c=5;
            level=1;
            life = 2;
            score = 0;
            TotalBricks = r*c;
            map = new MapGenerator(r,c);
        }
        repaint();
    }
    if(e.getKeyCode()==KeyEvent.VK_SPACE){
        if(TotalBricks!=r*c) {
            play = true;
        }
    }
    if(e.getKeyCode()==KeyEvent.VK_H){
        for(int i=0;i<map.Map.length;i++)
            for(int j=0;j<map.Map[0].length;j++){
                map.Map[i][j]=0;
            }
        play=false;
        score=score+5*r*c;
        TotalBricks=0;

    }
    if(e.getKeyCode()==KeyEvent.VK_A)
    {
        BallLeft();
    }
    if(e.getKeyCode()==KeyEvent.VK_D){
        BallRight();
    }
    if(e.getKeyCode()==KeyEvent.VK_NUMPAD0){
        delay+=1;
    }
    if(e.getKeyCode()==KeyEvent.VK_NUMPAD1){
        delay-=1;
    }
    }
    public void moveRight(){
        PlayerX+=20;
        if(play==false){
            BallPosX+=20;
        }
    }
    public void moveLeft(){
        PlayerX-=20;
        if(play==false){
            BallPosX-=20;
        }
    }
    public void BallLeft(){
        if(play==false)
            if(BallDirX>=-3)
                BallDirX-=1;
    }
    public void BallRight(){
        if(play==false)
            if(BallDirX<=3)
                BallDirX+=1;

    }
}