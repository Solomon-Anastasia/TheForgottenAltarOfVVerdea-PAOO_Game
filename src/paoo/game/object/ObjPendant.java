package paoo.game.object;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

import java.awt.*;

public class ObjPendant extends Entity {
    public ObjPendant(GamePanel gamePanel) {
        super(gamePanel);

        name = "Pendant";
        description = "[" + name + "]\nAn ancient charm said to protect\nits bearer.";
        collision = true;
        renderPriority = 0;
        type = TYPE_CONSUMABLE;
        stackable = false;

        down2 = setup("/objects/pendant");

        System.out.println(down1);
    }

    public void draw(Graphics2D graphics2D) {
        int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSCREEN_X();
        int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSCREEN_Y();


        graphics2D.drawImage(down1, screenX, screenY, width, height, null);
    }
}
