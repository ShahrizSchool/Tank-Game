package tankrotationexample.game;

import tankrotationexample.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Breakable extends Wall{

    private int health = 100;

    public Breakable(float x, float y, BufferedImage img) {
        super(x, y, img);

    }

    public void setLife(int life) {
        this.health = life;
    }

    public void removeWallHealth(int bulletDmg){
        if(this.health - bulletDmg <= 0){
            this.health = 0;
            deadWall = false;
        } else {
            this.health = this.health - bulletDmg;
        }
    }

    @Override
    public void handleCollision(Collidable with) {
        if (with instanceof Bullet){
            this.removeWallHealth(((Bullet) with).getBulletDmg());
        }
    }

}
