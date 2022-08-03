package tankrotationexample.game;

import java.awt.image.BufferedImage;

public class Life extends Powerup{

    public Life(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    public void handleCollision(Collidable with) {
        if(with instanceof Tank){
            ((Tank) with).gainLife(1);
            alive = false;
        }
    }
}