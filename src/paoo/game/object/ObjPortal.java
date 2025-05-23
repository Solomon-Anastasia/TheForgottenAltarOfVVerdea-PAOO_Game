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
        down2 = setup("/objects/portal");
        image1 = down1;
        description = "[" + name + "]\nA mysterious portal swirling with light...";
    }

    @Override
    public void interact() {
        gamePanel.getPlayer().setAttackCanceled(true);

        if (gamePanel.getPlayer().searchItemInInventory("Pendant") != -1) {
            if (!gamePanel.getPlayer().isTeleportReady()) {
                gamePanel.getUi().setCurrentDialogue("As you approach, the pendant glows faintly...\nThe portal responds to its presence.\nPress Enter to teleport.");
                gamePanel.getPlayer().setTeleportReady(true);
            } else {
                gamePanel.getUi().setCurrentDialogue("The portal pulses with energy...\nPress Enter to proceed.");
            }
            gamePanel.setGameState(gamePanel.getDIALOG_STATE());

        } else {
            gamePanel.getUi().setCurrentDialogue("The portal remains dormant.\nA powerful artifact might awaken it...");
            gamePanel.setGameState(gamePanel.getDIALOG_STATE());
        }
    }
}
