package paoo.game.object;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

import java.awt.*;

/**
 * Represents a Sword object in the game.
 * The Sword is a non-stackable consumable weapon used to cut through enemies.
 */
public class ObjSword extends Entity {

    /**
     * Constructs a new Sword object.
     *
     * @param gamePanel the game panel that manages this object
     */
    public ObjSword(GamePanel gamePanel) {
        super(gamePanel);

        name = "Sword";
        description = "[" + name + "]\nA sharp blade forged to cut\nthrough enemies with ease.";
        collision = true;
        renderPriority = 0;
        type = TYPE_CONSUMABLE;
        stackable = false;

        down2 = setup("/objects/sword");
    }

    /**
     * Draws the sword object on the screen relative to the player's position.
     *
     * @param graphics2D the Graphics2D object used for rendering
     */
    public void draw(Graphics2D graphics2D) {
        int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSCREEN_X();
        int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSCREEN_Y();

        graphics2D.drawImage(down1, screenX, screenY, width, height, null);
    }
}