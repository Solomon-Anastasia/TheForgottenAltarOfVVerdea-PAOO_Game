package paoo.game.object;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

public class ObjChest extends Entity {
    private Entity loot1;
    private Entity loot2;
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
        description = "[" + name + "]\nAn old chest!";

        // Match collision to the size of image
        setSize(48, 48);
    }

    public void interact() {
        gamePanel.setGameState(gamePanel.getDIALOG_STATE());

        if (!opened) {
            if (gamePanel.getPlayer().getNrCarrots() < 10) {
                gamePanel.getUi().setCurrentDialogue("You need 10 carrots!");
            } else {
                gamePanel.playSE(5);

                StringBuilder sb = new StringBuilder();
                sb.append("You open the chest and find a ")
                        .append(loot1.getName())
                        .append(" and ")
                        .append(loot2.getName())
                        .append("!");

                if (!gamePanel.getPlayer().canObtainItem(loot1) && !gamePanel.getPlayer().canObtainItem(loot2)) {
                    sb.append("\n... But you cannot carry any more!");
                } else {
                    sb.append("\nYou obtained the ")
                            .append(loot1.getName())
                            .append(" and ")
                            .append(loot2.getName())
                            .append("!");

                    gamePanel.getPlayer().addItemToInventory(loot1);
                    gamePanel.getPlayer().addItemToInventory(loot1);

                    down1 = image2;
                    opened = true;
                }
                gamePanel.getUi().setCurrentDialogue(sb.toString());
            }
        } else {
            gamePanel.getUi().setCurrentDialogue("It's empty!");
        }
    }
}
