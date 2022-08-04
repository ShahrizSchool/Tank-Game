package tankrotationexample.game;

import tankrotationexample.Animation;
import tankrotationexample.GameConstants;
import tankrotationexample.Resources;
import tankrotationexample.Sound;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Shahriz Malek
 */
public class Tank extends GameObject{

    private float vx;
    private float vy;
    private float angle;
    int health = 10;
    int shield = 30;
    int damage = 20;
    int lives = 3;
    private float R = 5;
    private float ROTATIONSPEED = 3.0f;
    float bulletDelay = 120;
    float coolDown = 0f;
    float rateOfBullet = 1f;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;
    private float screen_x;
    private float screen_y;

    List<Bullet> ammo = new ArrayList<>();
    List<Animation> ba = new ArrayList<>();
    //Bullet b;

    Tank(float x, float y, float vx, float vy, float angle, BufferedImage img) {
        super(x, y, img);
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
//        this.health = (new Random()).nextInt(100);
//        this.lives = (new Random()).nextInt(this.lowLife, 3);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    void setPosition(float x, float y){
        this.x = x;
        this.y = y;
        this.hitbox.setLocation((int)x, (int)y); // hitbox moves as tank moves
    }

    void setX(float x){ this.x = x; }
    void setY(float y) { this. y = y;}

    void toggleUpPressed() {
        this.UpPressed = true;
    }
    void toggleDownPressed() {
        this.DownPressed = true;
    }
    void toggleRightPressed() {
        this.RightPressed = true;
    }
    void toggleLeftPressed() {
        this.LeftPressed = true;
    }
    void unToggleUpPressed() {
        this.UpPressed = false;
    }
    void unToggleDownPressed() {
        this.DownPressed = false;
    }
    void unToggleRightPressed() {
        this.RightPressed = false;
    }
    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }
    void toggleShootPressed(){
        this.shootPressed = true;
    }
    void unToggleShootPressed(){
        this.shootPressed = false;
    }

    void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }

        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }

        if (this.RightPressed) {
            this.rotateRight();
        }

        if(this.shootPressed && this.coolDown >= this.bulletDelay){
            this.coolDown = 0;
            (new Sound(Resources.getSound("bullet"))).playSound();
            //this is to charge up the bullet and make it do more dmg the more it charges
            Bullet b = new Bullet(this.setBulletStartX(), this.setBulletStartY(), angle, Resources.getImage("Shell"));
            ammo.add(b);
            animLoader();

        }
        this.coolDown += this.rateOfBullet;
        bulletRemover();

        this.ba.removeIf(a -> !a.isRunning()); //if animation is not running take it out
        center_screen();


    }
    public void center_screen(){
        this.screen_x = (int) this.getX() - GameConstants.WORLD_WIDTH / 4;
        this.screen_y = (int) this.getY() - GameConstants.WORLD_HEIGHT / 2;

        if(this.screen_x < 0) {
            this.screen_x = 0;
        }
        if(this.screen_y < 0){
            this.screen_y =0;
        }
        //keeps camera still at right border
        if(screen_x > GameConstants.WORLD_WIDTH - GameConstants.WORLD_WIDTH / 2){
            screen_x = GameConstants.WORLD_WIDTH - GameConstants.WORLD_WIDTH  / 2;
        }

        //keeps camera still at bottom border
        if(screen_y > GameConstants.WORLD_HEIGHT - GameConstants.WORLD_HEIGHT){
            screen_y = GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
        }
    }
    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }
    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }
    private int setBulletStartX() {
        float cx = 29f * (float)Math.cos(Math.toRadians(angle));
        return (int) x + this.img.getWidth()/2 + (int)cx - 4;
    }
    private int setBulletStartY() {
        float cy = 29f* (float)Math.sin(Math.toRadians(angle));
        return (int) y + this.img.getHeight()/2 + (int)cy - 4;
    }
    private void moveBackwards() {
        vx =  Math.round(R * Math.cos(Math.toRadians(angle)));
        vy =  Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
        this.hitbox.setLocation((int)x, (int)y);
    }
    private void moveForwards() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitbox.setLocation((int)x, (int)y);
    }
    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_WIDTH  - 88) {
            x = GameConstants.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 80) {
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
        Graphics2D g2d = (Graphics2D) g;
//        if(b != null){
//            b.drawImage(g2d);
//        }

        //tank hitbox and ammo stuff
        for (int i = 0; i < ammo.size(); i++) {
            this.ammo.get(i).drawImage(g2d);
        }
        g2d.drawImage(this.img, rotation, null);
        //g2d.rotate(Math.toRadians(angle), bounds.x + bounds.width/2, bounds.y + bounds.height/2);
        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight()); //draw the rect/hitbox around tank

        //Animation

        for (int i = 0; i < this.ba.size() ; i++) {
            this.ba.get(i).drawImage(g2d);
        }
        //health bar
        drawHealthBar(g2d);
        //lives
        drawLives(g2d);
        //draw shield bar
        //drawShieldbar(g2d);
    }

    void animLoader(){
        Animation a = new Animation(setBulletStartX() - 28, setBulletStartY() - 28, Resources.getAnimation("nuke"));
        a.start();
        ba.add(a);
    }

    void bulletRemover(){
        for (int i = 0; i < ammo.size(); i++) {
            Bullet b = ammo.get(i);
            if(!b.isAlive()) {
                ammo.remove(b);
            }
        }
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    void drawLives(Graphics2D g2d){
        g2d.setColor(Color.MAGENTA);
        for(int i=0; i < this.lives; i++){
            g2d.drawOval((int)x + (i*20),(int)y + 55, 15, 15);
            g2d.fillOval((int)x + (i*20),(int)y + 55, 15, 15);
        }
    }
    void drawHealthBar(Graphics2D g2d){
        g2d.setColor(Color.WHITE);
        g2d.drawRect((int)x,(int)y - 30, 100, 25);

        if(this.health >= 70) {
            g2d.setColor(Color.GREEN);
        } else if (this.health >= 50) {
            g2d.setColor(Color.YELLOW);
        } else{
            g2d.setColor(Color.RED);
        }
        g2d.fillRect((int)x,(int)y - 30, health, 25);
    }
    void drawShieldbar(Graphics2D g2d){
        g2d.setColor(Color.WHITE);
        g2d.drawRect((int)x,(int)y + 45, 100, 10);

        g2d.setColor(Color.BLUE);
        g2d.fillRect((int)x,(int)y + 45, shield, 10);
    }

    public float giveSpeed(){
        float speed = 0;
        speed =  this.R * 2;
        return speed;
    }

    public int addShield(int shield){
        shield = this.shield + shield;
        System.out.println(shield);
        return shield;
    }

    public void addHealth(int hp){
        int maxHp = 100;
        if(hp + this.health >= maxHp){
            this.health = maxHp;
            System.out.println("Max health is " + maxHp);
        } else {
            this.health += hp;
            System.out.println(hp + " health has been added to your health " + this.health);
        }
    }
    private void removeHealth(int hp){
        ammo.get(hp).getBulletDmg();
    }

    int getHealth(){
        return this.health;
    }

    public void setHealth(int health) {
        this.health -= health;
    }

    public void gainLife(int life){
        this.lives = this.lives + life;
    }

    void takeDmg(){

    }

    @Override
    public Rectangle getHitBox() {
        return hitbox.getBounds();
    }
    @Override
    public void handleCollision(Collidable with) {

        if(with instanceof Bullet){ //tank collides with bullet
            with.handleCollision(this);
        }
        if(with instanceof Tank){ //if both tanks collide with each other
            if(this.UpPressed){
                y -= vy;
                x -= vx;
            }
            if(this.DownPressed){
                y -= vy;
                x -= vx;
            }
        }
        if(with instanceof Wall){ //if tank hits wall
            if(this.UpPressed){
                y -= vy;
                x -= vx;
            }
            if(this.DownPressed){
                y += vy;
                x += vx;
            }
        }

        if(with instanceof Powerup){ // if tank hits a powerup
            with.handleCollision(this);
        }
    }

    @Override
    public boolean isCollidable() {
        return false;
    }
}
