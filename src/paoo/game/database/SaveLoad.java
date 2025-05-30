package paoo.game.database;

import paoo.game.entity.Entity;
import paoo.game.object.*;
import paoo.game.panel.GamePanel;

import java.util.ArrayList;

/**
 * The {@code SaveLoad} class handles saving and loading game state data such as
 * player stats and inventory to and from a SQLite database via the {@link DbManager}.
 * <p>
 * It converts in-game objects into serializable forms and reconstructs them on load.
 */
public class SaveLoad {
    /**
     * Reference to the main game panel for accessing game state.
     */
    private GamePanel gamePanel;

    /**
     * Constructs a new {@code SaveLoad} handler.
     *
     * @param gamePanel the main game panel containing the current game state
     */
    public SaveLoad(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Converts a string-based item name into the corresponding in-game object.
     *
     * @param itemName the name of the item
     * @return an {@link Entity} object representing the item, or {@code null} if unknown
     */
    public Entity getObject(String itemName) {
        Entity obj = null;

        switch (itemName) {
            case "Carrot" -> obj = new ObjCarrot(gamePanel);
            case "Cup" -> obj = new ObjCup(gamePanel);
            case "Diamond" -> obj = new ObjDiamond(gamePanel);
            case "Pendant" -> obj = new ObjPendant(gamePanel);
            case "Pickaxe" -> obj = new ObjPickaxe(gamePanel);
            case "Sword" -> obj = new ObjSword(gamePanel);
        }

        return obj;
    }

    /**
     * Saves the current game state (level, health, play time, and inventory)
     * into the database using {@link DbManager}.
     */
    public void save() {
        DbManager dbManager = new DbManager();
        DataStorage dataStorage = getDataStorage();

        // Serialize inventory: "Sword:1,Potion:2"
        StringBuilder inventoryBuilder = new StringBuilder();
        for (int i = 0; i < dataStorage.getItemNames().size(); i++) {
            inventoryBuilder
                    .append(dataStorage.getItemNames().get(i))
                    .append(":")
                    .append(dataStorage.getItemAmounts().get(i));

            if (i < dataStorage.getItemNames().size() - 1) {
                inventoryBuilder.append(",");
            }
        }

        String inventoryString = inventoryBuilder.toString();

        dbManager.saveData(
                dataStorage.getLevel(),
                dataStorage.getMaxLife(),
                dataStorage.getLife(),
                dataStorage.getTime(),
                inventoryString,
                dataStorage.getxPosition(),
                dataStorage.getyPosition()
        );

        dbManager.closeConnection();
    }

    /**
     * Collects current game state into a {@link DataStorage} object for persistence.
     *
     * @return a {@code DataStorage} object containing player level, health, play time, and inventory
     */
    private DataStorage getDataStorage() {
        DataStorage dataStorage = new DataStorage();

        // Save player stats
        dataStorage.setLevel(gamePanel.getKeyHandler().getCurrentLevel());
        dataStorage.setMaxLife(gamePanel.getPlayer().getMaxLife());
        dataStorage.setLife(gamePanel.getPlayer().getLife());
        dataStorage.setTime(gamePanel.getUi().getPlayTime());
        dataStorage.setxPosition(gamePanel.getPlayer().getWorldX());
        dataStorage.setyPosition(gamePanel.getPlayer().getWorldY());

        // Save inventory
        ArrayList<String> itemNames = new ArrayList<>();
        ArrayList<Integer> itemAmounts = new ArrayList<>();

        for (Entity item : gamePanel.getPlayer().getInventory()) {
            itemNames.add(item.getName());
            itemAmounts.add(item.getAmount());
        }

        dataStorage.setItemNames(itemNames);
        dataStorage.setItemAmounts(itemAmounts);
        return dataStorage;
    }

    /**
     * Loads the latest saved game state from the database and updates the game accordingly.
     * It restores player stats and reconstructs the inventory.
     */
    public void load() {
        DbManager dbManager = new DbManager();
        DataStorage dataStorage = dbManager.loadData();

        if (dataStorage != null) {
            gamePanel.getKeyHandler().setCurrentLevel(dataStorage.getLevel());
            gamePanel.getPlayer().setMaxLife(dataStorage.getMaxLife());
            gamePanel.getPlayer().setLife(dataStorage.getLife());
            gamePanel.getUi().setPlayTime(dataStorage.getTime());
            gamePanel.getPlayer().setWorldX(dataStorage.getxPosition());
            gamePanel.getPlayer().setWorldY(dataStorage.getyPosition());

            gamePanel.setLoadedFromSave(true);

            // Restore inventory
            gamePanel.getPlayer().getInventory().clear();
            for (int i = 0; i < dataStorage.getItemNames().size(); ++i) {
                Entity item = getObject(dataStorage.getItemNames().get(i));
                if (item != null) {
                    item.setAmount(dataStorage.getItemAmounts().get(i));
                    gamePanel.getPlayer().getInventory().add(item);
                }
            }
        }

        dbManager.closeConnection();
    }
}
