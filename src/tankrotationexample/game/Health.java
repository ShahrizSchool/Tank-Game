package tankrotationexample.game;

import java.awt.image.BufferedImage;

public class Health extends Powerup{

    public Health(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void handleCollision(Collidable with) {
        if(with instanceof Tank){
            ((Tank) with).addHealth(50);
        }
    }
}
