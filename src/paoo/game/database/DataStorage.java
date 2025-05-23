package paoo.game.database;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {
    // Player status
    private int level;
    private int maxLife;
    private int life;
    private double time;

    // Player inventory
    private ArrayList<String> itemNames = new ArrayList<>();
    private ArrayList<Integer> itemAmounts = new ArrayList<>();

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public ArrayList<String> getItemNames() {
        return itemNames;
    }

    public void setItemNames(ArrayList<String> itemNames) {
        this.itemNames = itemNames;
    }

    public ArrayList<Integer> getItemAmounts() {
        return itemAmounts;
    }

    public void setItemAmounts(ArrayList<Integer> itemAmounts) {
        this.itemAmounts = itemAmounts;
    }
}
