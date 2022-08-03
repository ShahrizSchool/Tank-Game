package tankrotationexample.game;

import java.awt.image.BufferedImage;

public class Shield extends Powerup{

    public Shield(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    public void handleCollision(Collidable with) {
        if(with instanceof Tank){
            ((Tank) with).addShield(30);
            alive = false;
        }
    }
}
