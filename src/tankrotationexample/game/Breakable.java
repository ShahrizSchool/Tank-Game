package tankrotationexample.game;

import tankrotationexample.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Breakable extends Wall{

    private int life = 2;
    private boolean deadWall = false;
    public Breakable(float x, float y, BufferedImage img) {
        super(x, y, img);

    }

    public void setLife(int life) {
        this.life = life;
    }

    public void removeHealth(int health){
        if(this.life - health < 0){
            life = 0;
            deadWall = true;
        } else {
            life -= health;
        }
    }

    boolean isDeadWall(){
        return deadWall;
    }

    void setDeadWall(boolean deadWall){
        this.deadWall = deadWall;
    }

    void getHealth(){

    }
    @Override
    public void handleCollision(Collidable with) {
//        if (with instanceof Bullet){
//            this.takeDamage(((Bullet) with).getDmg());
//            updateImage();
//        }
    }


    //    public Breakable(int life) {
//        super(life);
//    }
}
