package paoo.game.object;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

import java.awt.*;

public class ObjPickaxe extends Entity {
    public ObjPickaxe(GamePanel gamePanel) {
        super(gamePanel);

        name = "Pickaxe";
        description = "[" + name + "]\nA sturdy tool used to break\nrocks and mine valuable ores.";
        collision = true;
        renderPriority = 0;
        type = TYPE_CONSUMABLE;
        stackable = false;

        down2 = setup("/objects/pickaxe");
    }

    public void draw(Graphics2D graphics2D) {
        int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSCREEN_X();
        int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSCREEN_Y();

        graphics2D.drawImage(down1, screenX, screenY, width, height, null);
    }
}
