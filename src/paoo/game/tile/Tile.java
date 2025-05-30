package paoo.game.tile;

import java.awt.image.BufferedImage;

/**
 * Represents a tile in the game world using the Flyweight design pattern.
 *
 * <p>This class serves as a lightweight, reusable tile object that can be shared
 * across multiple locations in the game world. Each tile contains an image for
 * visual representation and collision properties that determine whether entities
 * can pass through it.</p>
 *
 * <p>The Flyweight pattern is used here to minimize memory usage when dealing
 * with large game worlds that may contain thousands of tiles. Rather than creating
 * individual tile objects for each position, tile instances are shared and their
 * position-specific data is managed externally.</p>
 *
 * <p><strong>Design Pattern:</strong> Flyweight - allows efficient sharing of
 * tile objects to reduce memory consumption in large game worlds.</p>
 */
public class Tile {

    /**
     * The visual representation of this tile.
     * This image is shared among all instances of the same tile type.
     */
    protected BufferedImage image;

    /**
     * Collision property indicating whether entities can pass through this tile.
     * If true, entities cannot move through this tile; if false, movement is allowed.
     */
    protected boolean collision = false;

    /**
     * Checks whether this tile blocks entity movement.
     *
     * <p>This method is used by the collision detection system to determine
     * if entities (such as players, NPCs, or objects) can move through this tile.
     * Tiles with collision enabled will block movement, while tiles without
     * collision allow free passage.</p>
     *
     * @return true if the tile blocks movement (has collision), false if entities can pass through
     */
    public boolean isCollision() {
        return collision;
    }
}