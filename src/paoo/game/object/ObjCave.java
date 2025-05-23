package paoo.game.object;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

public class ObjCave extends Entity {
    public ObjCave(GamePanel gamePanel) {
        super(gamePanel);

        name = "Cave";
        collision = true;
        renderPriority = 0;

        down1 = setup("/objects/cave");
        description = "A mysterious portal swirling with light...";
    }

    @Override
    public void interact() {
        gamePanel.getPlayer().setAttackCanceled(true);

        if (gamePanel.getPlayer().searchItemInInventory("Cup") != -1) {
            if (!gamePanel.getPlayer().isTeleportReady()) {
                gamePanel.getUi().setCurrentDialogue("You feel the pendant vibrating...\nA strange force surrounds you.\nPress Enter to teleport.");
                gamePanel.getPlayer().setTeleportReady(true);
            } else {
                gamePanel.getUi().setCurrentDialogue("The cave hums in response to the pendant.\nPress Enter to proceed.");
            }
            gamePanel.setGameState(gamePanel.getDIALOG_STATE());

        } else {
            gamePanel.getUi().setCurrentDialogue("A swirling force blocks the path...\nYou sense that something important is missing.");
            gamePanel.setGameState(gamePanel.getDIALOG_STATE());
        }
    }
}
