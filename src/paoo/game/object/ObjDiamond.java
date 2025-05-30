package paoo.game.object;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

/**
 * Represents a Diamond object in the game.
 * The Diamond is a consumable item described as a rare gem with mystical energy.
 */
public class ObjDiamond extends Entity {

    /**
     * Constructs a new Diamond object.
     *
     * @param gamePanel the game panel that manages this object
     */
    public ObjDiamond(GamePanel gamePanel) {
        super(gamePanel);

        name = "Diamond";
        description = "[" + name + "]\nA rare gem that shimmers with\nmystical energy.";
        collision = true;
        type = TYPE_CONSUMABLE;
        renderPriority = 0;

        down2 = setup("/objects/diamond");
    }
}