package tankrotationexample.game;

import java.awt.*;

public interface Collidable {
    Rectangle getHitBox();
    void handleCollision(Collidable with);
    boolean isCollidable();
}
