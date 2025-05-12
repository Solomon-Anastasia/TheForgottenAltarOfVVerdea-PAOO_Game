package paoo.game.main;

import paoo.game.object.ObjCarrot;
import paoo.game.object.ObjChest;
import paoo.game.panel.GamePanel;

public class AssetSetter {
    private GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        // Chest
        gamePanel.getObjects()[0] = new ObjChest(gamePanel);
        // Column
        gamePanel.getObjects()[0].setWorldX(59 * gamePanel.getTILE_SIZE());
        // Row
        gamePanel.getObjects()[0].setWorldY(39 * gamePanel.getTILE_SIZE());

        // Carrot
        gamePanel.getObjects()[1] = new ObjCarrot(gamePanel);
        gamePanel.getObjects()[1].setWorldX(47 * gamePanel.getTILE_SIZE());
        gamePanel.getObjects()[1].setWorldY(26 * gamePanel.getTILE_SIZE());

        // TODO: Modify if needed
//        // Seed
//        gamePanel.getObject()[1] = new ObjSeed(gamePanel);
//        gamePanel.getObject()[1].setWorldX(46 * gamePanel.getTILE_SIZE());
//        gamePanel.getObject()[1].setWorldY(26 * gamePanel.getTILE_SIZE());

    }
}
