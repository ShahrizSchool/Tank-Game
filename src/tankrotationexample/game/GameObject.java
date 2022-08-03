package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject implements Collidable{

    protected float x, y;
    protected BufferedImage img;
    protected Rectangle hitbox;
    protected boolean collided = false;

    public GameObject(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        hitbox = new Rectangle((int) x, (int) y, this.img.getWidth(), this.img.getHeight());
    }

    public Rectangle getHitbox(){
        return this.hitbox.getBounds();
    }

     void drawImage(Graphics2D buffer) {
        buffer.drawImage(img, (int)x, (int)y, null);
        buffer.setColor(Color.cyan);
        buffer.drawRect((int)x, (int)y, this.img.getWidth(),this.img.getHeight());
    }

    @Override
    public boolean isCollidable() {
        return collided;
    }
}
