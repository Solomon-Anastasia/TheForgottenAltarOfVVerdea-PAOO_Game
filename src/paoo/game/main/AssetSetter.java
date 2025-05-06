package paoo.game.main;

import paoo.game.object.ObjChest;
import paoo.game.panel.GamePanel;

public class AssetSetter {
    private GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        gamePanel.object[0] = new ObjChest();
        gamePanel.object[0].setWorldX(59 * gamePanel.getTILE_SIZE());
        gamePanel.object[0].setWorldY(39 * gamePanel.getTILE_SIZE());
    }
}
