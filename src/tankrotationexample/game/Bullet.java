package tankrotationexample.game;

import tankrotationexample.GameConstants;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Bullet extends GameObject{

    private float angle;
    float scaleFactor = 1f;
    private float R = 5f;
    private int bulletDmg = 30;
    private boolean alive = true;


    Bullet(float x, float y, float angle, BufferedImage img) {
        super(x, y, img);
        this.angle = angle;
    }

    void setPosition(float x, float y){
        this.x = x;
        this.y = y;
        this.hitbox.setLocation((int)x, (int)y); //add hitbox move
    }

    void setX(float x){ this.x = x; }

    void setY(float y) { this. y = y;}
    void update() {
        moveForwards();
        scaleFactor += .1;
    }

    private void moveForwards() {
        x += Math.round(R * Math.cos(Math.toRadians(angle)));
        y += Math.round(R * Math.sin(Math.toRadians(angle)));
        checkBorder();
        this.hitbox.setLocation((int)x, (int)y);
    }

    public int getBulletDmg() {
        return bulletDmg;
    }


    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_HEIGHT - 88) {
            x = GameConstants.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_WIDTH - 80) {
            y = GameConstants.WORLD_HEIGHT - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    @Override
    void drawImage(Graphics2D g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        //rotation.scale(scaleFactor, scaleFactor); //make the bullet bigger over time
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        g2d.setColor(Color.RED);

    }

    public boolean isAlive() {
        if(!alive){
            return false;
        } else if (x>30 && x < GameConstants.WORLD_WIDTH - 88 && y>40 && y < GameConstants.WORLD_HEIGHT) {
            update();
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
        if(with instanceof Wall){
            if(with instanceof Breakable){
                with.handleCollision(this);
                ((Wall) with).getHitbox().getBounds();
            }
            this.alive = !isCollidable();

        }

        if(with instanceof Tank){
            ((Tank) with).setHealth(this.getBulletDmg());
            alive = false;
        }
    }

    @Override
    public boolean isCollidable() {
        return true;
    }
}
