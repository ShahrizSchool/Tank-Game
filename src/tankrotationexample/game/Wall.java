package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends GameObject{

    public Wall(float y, float x, BufferedImage img){
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


//        public Wall(int life){
//        this.life = life;
//    }


}
