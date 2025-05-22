package paoo.game.object;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

public class ObjPortal extends Entity {

    public ObjPortal(GamePanel gamePanel) {
        super(gamePanel);

        name = "Portal";
        collision = true;
        renderPriority = 0;

        down1 = setup("/objects/portal");
        description = "A mysterious portal swirling with light...";
    }

    @Override
    public void interact() {
        if (gamePanel.getPlayer().searchItemInInventory("Pendant") != -1) {
            gamePanel.getUi().setCurrentDialogue("You feel the pendant vibrating...\nPress Enter to teleport.");
            gamePanel.setGameState(gamePanel.getDIALOG_STATE());
            gamePanel.getPlayer().setTeleportReady(true);
        } else {
            gamePanel.getUi().setCurrentDialogue("Complete the challenge first!");
            gamePanel.setGameState(gamePanel.getDIALOG_STATE());
        }
    }

}
