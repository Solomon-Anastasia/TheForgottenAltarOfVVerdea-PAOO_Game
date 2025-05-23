package paoo.game.object;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

import java.util.List;

public class ObjChest extends Entity {
    private final List<Entity> loots;
    private boolean opened = false;

    public ObjChest(GamePanel gamePanel, List<Entity> loots) {
        super(gamePanel);

        down1 = setup("/objects/chest1");
        down2 = setup("/objects/chest2");
        image1 = down1;
        image2 = down2;

        this.loots = loots;

        name = "Chest";
        collision = true;
        renderPriority = 0;
        description = "[Chest]\nAn old chest!";
        setSize(48, 48);
    }

    public void interact() {
        gamePanel.getPlayer().setAttackCanceled(true);

        gamePanel.setGameState(gamePanel.getDIALOG_STATE());

        if (opened) {
            gamePanel.getUi().setCurrentDialogue("It's empty!");
            return;
        }

        int level = gamePanel.getKeyHandler().getCurrentLevel();
        int index = gamePanel.getPlayer().searchItemInInventory("Carrot");
        if (level == 1 && gamePanel.getPlayer().getInventory().get(index).getAmount() < 10) {
            gamePanel.getUi().setCurrentDialogue("You need 10 carrots!");
            return;
        }

        boolean goblinsExist = false;
        for (Entity monster : gamePanel.getMonster()) {
            if (monster != null) {
                goblinsExist = true;
                break;
            }
        }

        if (level == 2 && goblinsExist) {
            gamePanel.getUi().setCurrentDialogue("There are still goblins!");
            return;
        }

        if (level == 3 && goblinsExist) {
            gamePanel.getUi().setCurrentDialogue("Defeat the boss!");
            return;
        }

        gamePanel.playSE(5);

        StringBuilder obtainedItems = new StringBuilder("You open the chest and find:\n");
        boolean atLeastOneObtained = false;

        for (Entity loot : loots) {
            boolean canTake = gamePanel.getPlayer().canObtainItem(loot);

            if (canTake) {
                gamePanel.getPlayer().addItemToInventory(loot);
                obtainedItems.append("- ").append(loot.getName()).append("\n");
                atLeastOneObtained = true;
            } else {
                obtainedItems.append("- ").append(loot.getName()).append(" (Can't carry)\n");
            }
        }

        if (!atLeastOneObtained) {
            obtainedItems = new StringBuilder("You open the chest...\nBut you can't carry any of the items!");
        } else {
            down1 = image2;
            opened = true;

            if (level == 3 && gamePanel.getPlayer().searchItemInInventory("Diamond") != 1) {
                gamePanel.stopMusic();
                gamePanel.playSE(8);
                gamePanel.setGameState(gamePanel.getGAME_END_STATE());
            }
        }

        gamePanel.getUi().setCurrentDialogue(obtainedItems.toString().trim());
    }
}
