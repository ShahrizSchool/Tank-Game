package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Powerup extends GameObject{

    boolean alive = true;
    public Powerup(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    public boolean isAlive(){
        if(!alive){
            return false;
        } else if (x>30 && x < GameConstants.WORLD_WIDTH - 88 && y>40 && y < GameConstants.WORLD_HEIGHT) {
            return alive;
        }
        return false;
    }

    @Override
    public Rectangle getHitBox() {
        return hitbox.getBounds();
    }

    @Override
    public void handleCollision(Collidable with) {

    }

    @Override
    public boolean isCollidable() {
        return true;
    }
}
