package paoo.game.database;

import paoo.game.entity.Entity;
import paoo.game.object.*;
import paoo.game.panel.GamePanel;

import java.util.ArrayList;

public class SaveLoad {
    private GamePanel gamePanel;

    public SaveLoad(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

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

    public void save() {
        DbManager dbManager = new DbManager();
        DataStorage dataStorage = getDataStorage();

        StringBuilder inventoryBuilder = new StringBuilder();
        for (int i = 0; i < dataStorage.getItemNames().size(); i++) {
            inventoryBuilder
                    .append(dataStorage.getItemNames().get(i))
                    .append(":")
                    .append(dataStorage.getItemAmounts().get(i));

            if (i < dataStorage.getItemNames().size() - 1) {
                inventoryBuilder.append(","); // separator between items
            }
        }

        String inventoryString = inventoryBuilder.toString(); // "Sword:1,Potion:5,Key:2"
        dbManager.saveData(
                dataStorage.getLevel(),
                dataStorage.getMaxLife(),
                dataStorage.getLife(),
                dataStorage.getTime(),
                inventoryString
        );

        dbManager.closeConnection();
    }

    private DataStorage getDataStorage() {
        DataStorage dataStorage = new DataStorage();

        // Players stats
        dataStorage.setLevel(gamePanel.getKeyHandler().getCurrentLevel());
        dataStorage.setMaxLife(gamePanel.getPlayer().getMaxLife());
        dataStorage.setLife(gamePanel.getPlayer().getLife());
        dataStorage.setTime(gamePanel.getUi().getPlayTime());

        // Player inventory
        // Combine item names and amounts into a single string
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

    public void load() {
        DbManager dbManager = new DbManager();
        DataStorage dataStorage = dbManager.loadData();

        if (dataStorage != null) {
            gamePanel.getKeyHandler().setCurrentLevel(dataStorage.getLevel());
            gamePanel.getPlayer().setMaxLife(dataStorage.getMaxLife());
            gamePanel.getPlayer().setLife(dataStorage.getLife());
            gamePanel.getUi().setPlayTime(dataStorage.getTime());

            gamePanel.getPlayer().getInventory().clear();
            for (int i = 0; i < dataStorage.getItemNames().size(); ++i) {
                gamePanel.getPlayer().getInventory().add(getObject(dataStorage.getItemNames().get(i)));
                gamePanel.getPlayer().getInventory().get(i).setAmount(dataStorage.getItemAmounts().get(i));
            }
        }

        dbManager.closeConnection();
    }
}
