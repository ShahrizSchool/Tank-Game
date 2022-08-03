package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Powerup extends GameObject{

    public Powerup(float x, float y, BufferedImage img) {
        super(x, y, img);
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
