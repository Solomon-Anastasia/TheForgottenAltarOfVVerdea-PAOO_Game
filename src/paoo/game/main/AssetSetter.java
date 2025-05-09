package paoo.game.main;

import paoo.game.object.ObjChest;
import paoo.game.object.ObjSeed;
import paoo.game.panel.GamePanel;

public class AssetSetter {
    private GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        // Chest
        gamePanel.getObject()[0] = new ObjChest(gamePanel);
        // Column
        gamePanel.getObject()[0].setWorldX(59 * gamePanel.getTILE_SIZE());
        // Row
        gamePanel.getObject()[0].setWorldY(39 * gamePanel.getTILE_SIZE());

        // Seed
        gamePanel.getObject()[1] = new ObjSeed(gamePanel);
        gamePanel.getObject()[1].setWorldX(46 * gamePanel.getTILE_SIZE());
        gamePanel.getObject()[1].setWorldY(26 * gamePanel.getTILE_SIZE());

    }
}
