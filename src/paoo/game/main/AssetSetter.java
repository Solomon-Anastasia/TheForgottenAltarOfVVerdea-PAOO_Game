package paoo.game.main;

import paoo.game.entity.Grandpa;
import paoo.game.entity.Wizard;
import paoo.game.monster.MON_Boss;
import paoo.game.monster.MON_Goblin;
import paoo.game.object.*;
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
            // TODO: Change to sword and artefact
            gamePanel.getObjects()[i] = new ObjChest(gamePanel,  new ObjCup(gamePanel), new ObjPendant(gamePanel));
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

            gamePanel.getObjects()[i] = new ObjCarrot(gamePanel);
            gamePanel.getObjects()[i].setWorldX(59 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(28 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getObjects()[i] = new ObjCarrot(gamePanel);
            gamePanel.getObjects()[i].setWorldX(24 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(25 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getObjects()[i] = new ObjCarrot(gamePanel);
            gamePanel.getObjects()[i].setWorldX(30 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(28 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getObjects()[i] = new ObjCarrot(gamePanel);
            gamePanel.getObjects()[i].setWorldX(59 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(30 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getObjects()[i] = new ObjCarrot(gamePanel);
            gamePanel.getObjects()[i].setWorldX(66 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(30 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getObjects()[i] = new ObjCarrot(gamePanel);
            gamePanel.getObjects()[i].setWorldX(64 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(22 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getObjects()[i] = new ObjPortal(gamePanel);
            gamePanel.getObjects()[i].setWorldX(74 * gamePanel.getTILE_SIZE());
            gamePanel.getObjects()[i].setWorldY(15 * gamePanel.getTILE_SIZE());
            ++i;
        } else if (gamePanel.getKeyHandler().isLevel2()) {
        }
        else {
        }
    }

    public void setNpc() {
        int i = 0;

        if (gamePanel.getKeyHandler().isLevel1()) {
            gamePanel.getNpc()[i] = new Grandpa(gamePanel);
            gamePanel.getNpc()[i].setWorldX(gamePanel.getTILE_SIZE() * 37); // Start column
            gamePanel.getNpc()[i].setWorldY(gamePanel.getTILE_SIZE() * 25); // Start line
            ++i;

            gamePanel.getNpc()[i] = new Wizard(gamePanel);
            gamePanel.getNpc()[i].setWorldX(gamePanel.getTILE_SIZE() * 63);
            gamePanel.getNpc()[i].setWorldY(gamePanel.getTILE_SIZE() * 39);
            ++i;
        } else if (gamePanel.getKeyHandler().isLevel2()) {
            gamePanel.getNpc()[i] = new Wizard(gamePanel);
            gamePanel.getNpc()[i].setWorldX(gamePanel.getTILE_SIZE() * 35);
            gamePanel.getNpc()[i].setWorldY(gamePanel.getTILE_SIZE() * 35);
            ++i;
        }
    }

    public void setMonster() {
        int i = 0;

        if (gamePanel.getKeyHandler().isLevel1()) {
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

            gamePanel.getMonster()[i] = new MON_Goblin(gamePanel);
            gamePanel.getMonster()[i].setWorldX(24 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(25 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin(gamePanel);
            gamePanel.getMonster()[i].setWorldX(59 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(30 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin(gamePanel);
            gamePanel.getMonster()[i].setWorldX(64 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(22 * gamePanel.getTILE_SIZE());
            ++i;
        } else if (gamePanel.getKeyHandler().isLevel2()) {
            gamePanel.getMonster()[i] = new MON_Goblin(gamePanel);
            gamePanel.getMonster()[i].setWorldX(28 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(23 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin(gamePanel);
            gamePanel.getMonster()[i].setWorldX(41 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(24 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin(gamePanel);
            gamePanel.getMonster()[i].setWorldX(42 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(34 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin(gamePanel);
            gamePanel.getMonster()[i].setWorldX(31 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(37 * gamePanel.getTILE_SIZE());
            ++i;

            gamePanel.getMonster()[i] = new MON_Goblin(gamePanel);
            gamePanel.getMonster()[i].setWorldX(50 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(17 * gamePanel.getTILE_SIZE());
            ++i;
        } else {
//            gamePanel.getMonster()[i] = new MON_Goblin(gamePanel);
//            gamePanel.getMonster()[i].setWorldX(40 * gamePanel.getTILE_SIZE());
//            gamePanel.getMonster()[i].setWorldY(40 * gamePanel.getTILE_SIZE());
//            ++i;

            gamePanel.getMonster()[i] = new MON_Boss(gamePanel);
            gamePanel.getMonster()[i].setWorldX(40 * gamePanel.getTILE_SIZE());
            gamePanel.getMonster()[i].setWorldY(40 * gamePanel.getTILE_SIZE());
            ++i;
        }
    }
}
