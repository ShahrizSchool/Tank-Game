package tankrotationexample.game;

import java.awt.image.BufferedImage;

public class Speed extends Powerup{

    public Speed(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    public void handleCollision(Collidable with) {
        if(with instanceof Tank){
            ((Tank) with).giveSpeed();
            alive = false;
        }
    }
}
