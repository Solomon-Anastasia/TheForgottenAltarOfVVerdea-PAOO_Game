package paoo.game.object;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

import java.awt.*;

/**
 * Represents a Pendant object in the game.
 * The Pendant is a non-stackable consumable item described as an ancient protective charm.
 */
public class ObjPendant extends Entity {

    /**
     * Constructs a new Pendant object.
     *
     * @param gamePanel the game panel that manages this object
     */
    public ObjPendant(GamePanel gamePanel) {
        super(gamePanel);

        name = "Pendant";
        description = "[" + name + "]\nAn ancient charm said to protect\nits bearer.";
        collision = true;
        renderPriority = 0;
        type = TYPE_CONSUMABLE;
        stackable = false;

        down2 = setup("/objects/pendant");
    }

    /**
     * Draws the pendant object on the screen relative to the player's position.
     *
     * @param graphics2D the Graphics2D object used for rendering
     */
    public void draw(Graphics2D graphics2D) {
        int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSCREEN_X();
        int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSCREEN_Y();

        graphics2D.drawImage(down1, screenX, screenY, width, height, null);
    }
}