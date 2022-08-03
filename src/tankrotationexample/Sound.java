package tankrotationexample;

import javax.sound.sampled.Clip;

public class Sound implements Runnable{
    Clip clip;
    public Sound(Clip c){
        this.clip = c;
    }

    public void stopSound(){
        if(clip.isRunning()){
            clip.stop();
        }
    }

    public void playSound(){
        if(clip.isRunning()){
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.start();
    }

    @Override
    public void run(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        this.playSound();
        while(true){
            if(Thread.currentThread().isInterrupted()){
                clip.stop();
                return;
            }
        }
    }

//    public static void main(String[] args){
//        Thread t = new Thread(new Sound());
//        t.start();
//    }
}
