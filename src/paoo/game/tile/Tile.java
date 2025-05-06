package paoo.game.tile;

import java.awt.image.BufferedImage;

// Flyweight
public class Tile {
    protected BufferedImage image;
    protected boolean collision = false;

    public boolean isCollision() {
        return collision;
    }
}
