package paoo.game.object;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

/**
 * Represents a Cup object in the game.
 * The Cup is a consumable item described as a sacred relic from royal rituals.
 */
public class ObjCup extends Entity {
    /**
     * Constructs a new Cup object.
     *
     * @param gamePanel the game panel that manages this object
     */
    public ObjCup(GamePanel gamePanel) {
        super(gamePanel);

        name = "Cup";
        description = "[" + name + "]\nA sacred relic once used in\nroyal rituals.";
        collision = true;
        type = TYPE_CONSUMABLE;
        renderPriority = 0;

        down2 = setup("/objects/cup");
    }
}