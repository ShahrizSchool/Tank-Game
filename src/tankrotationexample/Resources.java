package tankrotationexample;

import tankrotationexample.game.GameWorld;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class Resources {
    private static Map<String, BufferedImage> images = new HashMap<>();
    private static Map<String, Clip> sounds = new HashMap<>();
    private static Map<String, List<BufferedImage>> animations = new HashMap<>();
    public static BufferedImage getImage(String key){
        return Resources.images.get(key);
    }
    public static Clip getSound(String key){
        return Resources.sounds.get(key);
    }
    public static List<BufferedImage> getAnimation(String key){
        return Resources.animations.get(key);
    }
    public static void initImages(){ //load images to resources
        try{
            //tank
            Resources.images.put("tank1", ImageIO.read(GameWorld.class.getClassLoader().getResource("tanks/tank1.png")));
            Resources.images.put("tank2", ImageIO.read(GameWorld.class.getClassLoader().getResource("tanks/Tank2.gif")));

            //wall
            Resources.images.put("breakable1", ImageIO.read(GameWorld.class.getClassLoader().getResource("wall/breakable1.gif"))); //after break1 gets shot it is replaced with this.
            Resources.images.put("breakable2", ImageIO.read(GameWorld.class.getClassLoader().getResource("wall/breakable2.gif")));
            Resources.images.put("break1", ImageIO.read(GameWorld.class.getClassLoader().getResource("wall/break1.jpg")));
            Resources.images.put("break2", ImageIO.read(GameWorld.class.getClassLoader().getResource("wall/break2.jpg")));
            Resources.images.put("unbreak", ImageIO.read(GameWorld.class.getClassLoader().getResource("wall/unbreak.jpg")));

            //background
            Resources.images.put("background", ImageIO.read(GameWorld.class.getClassLoader().getResource("floor/Background.bmp")));

            //menu
            Resources.images.put("title", ImageIO.read(GameWorld.class.getClassLoader().getResource("menu/title.png")));

            //powerups
            Resources.images.put("health", ImageIO.read(GameWorld.class.getClassLoader().getResource("powerups/health.png")));
            Resources.images.put("shield", ImageIO.read(GameWorld.class.getClassLoader().getResource("powerups/shield.png")));
            Resources.images.put("speed", ImageIO.read(GameWorld.class.getClassLoader().getResource("powerups/speed.png")));
            Resources.images.put("life", ImageIO.read(GameWorld.class.getClassLoader().getResource("powerups/life.png")));

            //bullet
            Resources.images.put("Shell", ImageIO.read(GameWorld.class.getClassLoader().getResource("bullet/Shell.gif")));
            Resources.images.put("rocket1", ImageIO.read(GameWorld.class.getClassLoader().getResource("bullet/rocket1.png")));
            Resources.images.put("rocket2", ImageIO.read(GameWorld.class.getClassLoader().getResource("bullet/rocket2.png")));

        }
        catch(IOException err){
            err.printStackTrace();
        }
    }

    public static void initSounds(){ //load sound to resources
        try{
            AudioInputStream as;
            Clip clip;

            as = AudioSystem.getAudioInputStream(Resources.class.getClassLoader().getResource("sounds/Music.mid"));
            clip = AudioSystem.getClip();
            clip.open(as);
            Resources.sounds.put("music", clip);

            as = AudioSystem.getAudioInputStream(Resources.class.getClassLoader().getResource("sounds/bullet.wav"));
            clip = AudioSystem.getClip();
            clip.open(as);
            Resources.sounds.put("bullet", clip);

            as = AudioSystem.getAudioInputStream(Resources.class.getClassLoader().getResource("sounds/Explosion_large.wav"));
            clip = AudioSystem.getClip();
            clip.open(as);
            Resources.sounds.put("Explosion_large", clip);

            as = AudioSystem.getAudioInputStream(Resources.class.getClassLoader().getResource("sounds/Explosion_small.wav"));
            clip = AudioSystem.getClip();
            clip.open(as);
            Resources.sounds.put("Explosion_small", clip);

        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException err){
            System.out.println(err);
            err.printStackTrace();
            System.exit(-2);
        }
    }

    public static void initAnimations(){ //load animation to resources
        try {
            String baseName = "expl_08_%04d.png";
            List<BufferedImage> temp = new ArrayList<>();
            for(int i = 0; i < 32; i++) {
                String fName = String.format(baseName, i);
                String path = "animations/bullet/" + fName;
                temp.add(ImageIO.read(GameWorld.class.getClassLoader().getResource(path)));
            }
            Resources.animations.put("bullet", temp);

            baseName = "expl_01_%04d.png";
            temp = new ArrayList<>();
            for(int i = 0; i < 24; i++) {
                String fName = String.format(baseName, i);
                String path = "animations/nuke/" + fName;
                temp.add(ImageIO.read(GameWorld.class.getClassLoader().getResource(path)));
            }
            Resources.animations.put("nuke", temp);
        }
        catch (IOException err){
            err.printStackTrace();
        }
    }

    public static void loadResourcesMaps(){
        Resources.initImages();
        Resources.initSounds();
        Resources.initAnimations();
    }
}
