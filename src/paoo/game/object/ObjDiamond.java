package paoo.game.object;

import paoo.game.entity.Entity;

/**
 * Represents a Diamond object in the game.
 * The Diamond is a consumable item described as a rare gem with mystical energy.
 */
public class ObjDiamond extends Entity {

    /**
     * Constructs a new Diamond object.
     */
    public ObjDiamond() {
        super();

        name = "Diamond";
        description = "[" + name + "]\nA rare gem that shimmers with\nmystical energy.";
        collision = true;
        type = TYPE_CONSUMABLE;
        renderPriority = 0;

        down2 = setup("/objects/diamond");
    }
}