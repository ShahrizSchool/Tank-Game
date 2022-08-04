/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankrotationexample.game;


import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;
import tankrotationexample.Resources;
import tankrotationexample.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * @author anthony-pc
 */
public class GameWorld extends JPanel implements Runnable {

    private BufferedImage world;
    private Tank t1, t2;
    private Launcher lf;
    private long tick = 0;
    List<GameObject> Objs = new ArrayList<>();

    /**
     * @param lf
     */
    public GameWorld(Launcher lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        try {
            this.resetGame();
//            Thread t = new Thread(new Sound(Resources.getSound("music")));
//            t.start();

            while (true) {
                this.tick++;
                this.t1.update(); // update tank
                this.t2.update();

                //collision must be done after update before repaint
                collisions(); //bottom of GameWorld.java


                this.repaint();   // redraw game

                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our
                 * loop run at a fixed rate per/sec.
                 */
                Thread.sleep(1000 / 144);

                /*
                 * simulate an end game event
                 * we will do this with by ending the game when ~8 seconds has passed.
                 * This will need to be changed since the will always close after 8 seconds
                 */
//                if (this.tick >= 144 * 8) {
////                    t.isInterrupted();
//                    this.lf.setFrame("end");
//                    return;
//
//                }

            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }
    /**
     * Reset game to its initial state.
     */
    public void resetGame() {
        this.tick = 0;
        this.t1.setX(300);
        this.t1.setY(300);

        this.t2.setX(400);
        this.t2.setY(400);
    }
    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void InitializeGame() {
        Resources.loadResourcesMaps();
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);


        t1 = new Tank(300, 300, 0, 0, (short) 0, Resources.getImage("tank1"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);

        t2 = new Tank(300, 300, 0, 0, (short) 0, Resources.getImage("tank2"));
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);

        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);


        try (BufferedReader mapReader
                     = new BufferedReader(new InputStreamReader(GameWorld.class.getClassLoader().getResourceAsStream("maps/map1.txt")))) {

            String[] size = mapReader.readLine().split(",");

            for (int i = 0; mapReader.ready(); i++) {
                String[] items = mapReader.readLine().split("");
                for (int j = 0; j < items.length; j++) {
                    switch (items[j]) {
                        case "4", "9" -> {
                            Wall w = new Wall(i * 30, j * 30, Resources.getImage("unbreak"));
                            Objs.add(w);
                        }
                        case "3" -> {
                            Breakable bw1 = new Breakable(i * 30, j * 30, Resources.getImage("break1"));
                            bw1.setLife(90);
                            Objs.add(bw1);
                        }
                        case "2" -> {
                            Breakable bw2 = new Breakable(i * 30, j * 30, Resources.getImage("break2"));
                            bw2.setLife(60);
                            Objs.add(bw2);
                        }
                        case "6" -> {
                            Health hp = new Health(i * 30, j * 30, Resources.getImage("health"));
                            this.Objs.add(hp);
                        }
                        case "7" -> {
                            Shield s = new Shield(i * 30, j * 30, Resources.getImage("shield"));
                            this.Objs.add(s);
                        }
                        case "8" -> {
                            Speed sp = new Speed(i * 30, j * 30, Resources.getImage("speed"));
                            this.Objs.add(sp);
                        }
                        case "1" -> {
                            Life l = new Life(i*30, j*30, Resources.getImage("life"));
                            this.Objs.add(l);
                        }
                    }
                }
            }
        } catch (IOException err) {
            System.out.println(err);
            System.exit(-2);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        //draw background
        drawBackGround(buffer);

        //draw objects
        drawObjects(buffer);
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        g2.drawImage(world, 0, 0, null);

        //draw split screen
        //drawSplitScreen(g2, world);
        g2.drawImage(splitScreen(this.t1), 0, 0, null);
        g2.drawImage(splitScreen(this.t2), GameConstants.GAME_SCREEN_WIDTH/2+4, 0, null);

        //miniMap
        drawMiniMap(g2, world);

    }

    void drawObjects(Graphics2D buffer) {
        for (int i = 0; i < Objs.size(); i++) {
            Objs.get(i).drawImage(buffer);
        }
    }

    private BufferedImage splitScreen(Tank t1) {
        int offsetX = GameConstants.GAME_SCREEN_WIDTH/4;
        int offsetY = GameConstants.GAME_SCREEN_HEIGHT/2;
        float splitStartX = t1.getX()-offsetX;
        float splitStartY = t1.getY()-offsetY;

        if(splitStartX<0){
            splitStartX=0;
        } else if (t1.getX()+offsetX>GameConstants.WORLD_WIDTH) {
            splitStartX = GameConstants.WORLD_WIDTH-2*offsetX+2;
        }
        if(splitStartY<=0){
            splitStartY=0;
        } else if (t1.getY()+offsetY>GameConstants.WORLD_HEIGHT) {
            splitStartY=GameConstants.WORLD_HEIGHT-GameConstants.GAME_SCREEN_HEIGHT;
        }
        return world.getSubimage((int) splitStartX, (int)splitStartY, GameConstants.GAME_SCREEN_WIDTH/2-2, GameConstants.GAME_SCREEN_HEIGHT);

    }

    void drawMiniMap(Graphics2D g2, BufferedImage world) {
        BufferedImage mm = world.getSubimage(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        AffineTransform at = new AffineTransform();
        at.translate(GameConstants.GAME_SCREEN_WIDTH / 2f - (GameConstants.WORLD_WIDTH * .2) / 2f,
                GameConstants.GAME_SCREEN_HEIGHT - GameConstants.WORLD_HEIGHT * .2);
        at.scale(.2, .2);
        g2.drawImage(mm, at, null);

    }

    void drawBackGround(Graphics2D buffer) {
        for (int i = 0; i <= GameConstants.WORLD_WIDTH; i += 320) {
            for (int j = 0; j <= GameConstants.WORLD_HEIGHT; j += 240) {
                buffer.drawImage(Resources.getImage("background"), i, j, null);
            }
        }
    }

    public void collisions() { //collision method

        for (int i = 0; i < Objs.size(); i++) {
            GameObject obj = this.Objs.get(i);

            if (obj.getHitbox().intersects(this.t1.getHitbox())) {
                t1.handleCollision(obj);
            }
            if (obj.getHitbox().intersects(this.t2.getHitbox())) {
                t2.handleCollision(obj);
            }

            for (int j = 0; j < t1.ammo.size(); j++) {
                Bullet b = (Bullet) t1.ammo.get(j);
                if(b.getHitbox().intersects(obj.getHitbox())) {
                    b.handleCollision(obj);
                    t1.bulletRemover();
                }
            }

            for (int j = 0; j < t2.ammo.size(); j++) {
                Bullet b = (Bullet) t2.ammo.get(j);
                if(b.getHitbox().intersects(obj.getHitbox())) {
                    b.handleCollision(obj);
                    t2.bulletRemover();
                }
            }

            if(obj instanceof Powerup){
                if(!((Powerup)obj).isAlive()){
                    Objs.remove(obj);
                }
            }

            if(obj instanceof Breakable){
                if(!((Breakable)obj).deadWall()){
                    Objs.remove(obj);
                }
            }
        }
    }
}
