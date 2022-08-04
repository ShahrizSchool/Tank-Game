package tankrotationexample;

import tankrotationexample.game.GameWorld;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Animation extends Thread{
    float x,y;
    int currentFrame = 0;
    private int delay = 45;
    private boolean isRunning = true;
    List<BufferedImage> frames;

    public Animation(float x, float y, List<BufferedImage> frames){
        this.x = x;
        this.y = y;
        this.frames = frames;
    }

    public boolean isRunning(){
        return isRunning;
    }
    public void drawImage(Graphics g2){
        if(isRunning){
            g2.drawImage(this.frames.get(currentFrame), (int)x, (int)y, null );
            Resources.initAnimations();
        }
    }

    public void run(){
        try{
            while(isRunning){
                this.currentFrame = (this.currentFrame + 1) % this.frames.size();
                if(currentFrame == this.frames.size() -1){
                    this.isRunning = false;
                }
                Thread.sleep(this.delay);
            }
        }
        catch (InterruptedException err){
            err.printStackTrace();
        }
    }
}
