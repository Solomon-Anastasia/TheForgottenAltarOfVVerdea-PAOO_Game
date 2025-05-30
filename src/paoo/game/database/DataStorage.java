package paoo.game.database;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The {@code DataStorage} class is a serializable container used to store and retrieve
 * player-related data such as level, life, inventory, and game time.
 * <p>
 * This class can be saved to a file or database and later restored to
 * resume a player's game session.
 */
public class DataStorage implements Serializable {
    // Player status
    /**
     * The player's current level.
     */
    private int level;

    /**
     * The player's maximum life points.
     */
    private int maxLife;

    /**
     * The player's current life points.
     */
    private int life;

    /**
     * The time played or remaining in the game, depending on the game's context.
     */
    private double time;

    /**
     * The player's x position.
     */
    private int xPosition;

    /**
     * The player's y position.
     */
    private int yPosition;

    /**
     * The list of item names in the player's inventory.
     */
    private ArrayList<String> itemNames = new ArrayList<>();

    /**
     * The corresponding list of quantities for each item in the inventory.
     * The index of each amount matches the index of its respective item name in {@code itemNames}.
     */
    private ArrayList<Integer> itemAmounts = new ArrayList<>();

    /**
     * Returns the player's current level.
     *
     * @return the current level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the player's level.
     *
     * @param level the new level value
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Returns the player's maximum life points.
     *
     * @return the maximum life value
     */
    public int getMaxLife() {
        return maxLife;
    }

    /**
     * Sets the player's maximum life points.
     *
     * @param maxLife the new maximum life value
     */
    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    /**
     * Returns the player's current life points.
     *
     * @return the current life value
     */
    public int getLife() {
        return life;
    }

    /**
     * Sets the player's current life points.
     *
     * @param life the new life value
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * Returns the game time value.
     *
     * @return the time value
     */
    public double getTime() {
        return time;
    }

    /**
     * Sets the game time value.
     *
     * @param time the new time value
     */
    public void setTime(double time) {
        this.time = time;
    }

    /**
     * Returns the list of item names in the inventory.
     *
     * @return a list of item names
     */
    public ArrayList<String> getItemNames() {
        return itemNames;
    }

    /**
     * Sets the list of item names in the inventory.
     *
     * @param itemNames the new list of item names
     */
    public void setItemNames(ArrayList<String> itemNames) {
        this.itemNames = itemNames;
    }

    /**
     * Returns the list of item amounts corresponding to each item name.
     *
     * @return a list of item amounts
     */
    public ArrayList<Integer> getItemAmounts() {
        return itemAmounts;
    }

    /**
     * Sets the list of item amounts.
     *
     * @param itemAmounts the new list of item amounts
     */
    public void setItemAmounts(ArrayList<Integer> itemAmounts) {
        this.itemAmounts = itemAmounts;
    }

    /**
     * Returns the player's X coordinate on the map.
     *
     * @return the X position of the player
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * Sets the player's X coordinate on the map.
     *
     * @param xPosition the X position to set
     */
    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * Returns the player's Y coordinate on the map.
     *
     * @return the Y position of the player
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     * Sets the player's Y coordinate on the map.
     *
     * @param yPosition the Y position to set
     */
    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

}
