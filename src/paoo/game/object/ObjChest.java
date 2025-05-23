package paoo.game.object;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

public class ObjChest extends Entity {
    private final Entity loot1;
    private final Entity loot2;
    private boolean opened = false;

    public ObjChest(GamePanel gamePanel, Entity loot1, Entity loot2) {
        super(gamePanel);

        down1 = setup("/objects/chest1");
        down2 = setup("/objects/chest2");
        image1 = down1;
        image2 = down2;

        this.loot1 = loot1;
        this.loot2 = loot2;

        name = "Chest";
        collision = true;
        renderPriority = 0;
        description = "[Chest]\nAn old chest!";
        setSize(48, 48);
    }

    public void interact() {
        gamePanel.setGameState(gamePanel.getDIALOG_STATE());

        if (opened) {
            gamePanel.getUi().setCurrentDialogue("It's empty!");
            return;
        }

        int level = gamePanel.getKeyHandler().getCurrentLevel();
        if (level == 1 && gamePanel.getPlayer().getNrCarrots() < 10) {
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

        gamePanel.playSE(5);

        boolean canTake1 = gamePanel.getPlayer().canObtainItem(loot1);
        boolean canTake2 = gamePanel.getPlayer().canObtainItem(loot2);

        StringBuilder sb = new StringBuilder("You open the chest and find a ")
                .append(loot1.getName()).append(" and ").append(loot2.getName()).append("!");

        if (!canTake1 && !canTake2) {
            sb.append("\n... But you cannot carry any more!");
        } else {
            sb.append("\nYou obtained the ")
                    .append(loot1.getName()).append(" and ")
                    .append(loot2.getName()).append("!");
            if (canTake1) gamePanel.getPlayer().addItemToInventory(loot1);
            if (canTake2) gamePanel.getPlayer().addItemToInventory(loot2);
            down1 = image2;
            opened = true;
        }

        gamePanel.getUi().setCurrentDialogue(sb.toString());
    }
}
