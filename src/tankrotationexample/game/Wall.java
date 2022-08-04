package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends GameObject{

    boolean deadWall = true;
    public Wall(float x, float y, BufferedImage img){
        super(x, y, img);

    }

    public boolean deadWall(){
        if(!deadWall){
            return false;
        } else if (x>30 && x < GameConstants.WORLD_WIDTH - 88 && y>40 && y < GameConstants.WORLD_HEIGHT) {
            return deadWall;
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
