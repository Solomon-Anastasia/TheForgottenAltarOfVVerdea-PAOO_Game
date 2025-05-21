package paoo.game.main;

import paoo.game.entity.Grandpa;
import paoo.game.monster.MON_Goblin;
import paoo.game.object.ObjCarrot;
import paoo.game.object.ObjChest;
import paoo.game.panel.GamePanel;

public class AssetSetter {
    private GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        int i = 0;

        // TODO: Add objects for all levels
        if (gamePanel.getKeyHandler().isLevel1()) {
            // Chest
            gamePanel.getObjects()[i] = new ObjChest(gamePanel);
            gamePanel.getObjects()[i].setWorldX(59 * gamePanel.getTILE_SIZE()); // Column
            gamePanel.getObjects()[i].setWorldY(39 * gamePanel.getTILE_SIZE()); // Row
            ++i;

            // Carrot
            gamePanel.getObjects()[i] = new ObjCarrot(gamePanel);
            gamePanel.getObjects()[i].setWorldX(73 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(28 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getObjects()[i] = new ObjCarrot(gamePanel);
            gamePanel.getObjects()[i].setWorldX(40 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(35 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getObjects()[i] = new ObjCarrot(gamePanel);
            gamePanel.getObjects()[i].setWorldX(30 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(35 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getObjects()[i] = new ObjCarrot(gamePanel);
            gamePanel.getObjects()[i].setWorldX(38 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(26 * gamePanel.getTILE_SIZE());
            ++i;
        } else if (gamePanel.getKeyHandler().isLevel2()) {
        }
        else {
        }
    }

    public void setNpc() {
        if (gamePanel.getKeyHandler().isLevel1()) {
            gamePanel.getNpc()[0] = new Grandpa(gamePanel);
            gamePanel.getNpc()[0].setWorldX(gamePanel.getTILE_SIZE() * 37); // Start column
            gamePanel.getNpc()[0].setWorldY(gamePanel.getTILE_SIZE() * 25); // Start line
        }
    }

    public void setMonster() {
        if (gamePanel.getKeyHandler().isLevel1()) {
            int i = 0;
            gamePanel.getMonster()[i] = new MON_Goblin(gamePanel);
            gamePanel.getMonster()[i].setWorldX(48 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(26 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin(gamePanel);
            gamePanel.getMonster()[i].setWorldX(48 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(35 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin(gamePanel);
            gamePanel.getMonster()[i].setWorldX(60 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(28 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin(gamePanel);
            gamePanel.getMonster()[i].setWorldX(50 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(32 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin(gamePanel);
            gamePanel.getMonster()[i].setWorldX(35 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(35 * gamePanel.getTILE_SIZE());
            ++i;
        }
    }
}
