package paoo.game.object;

import paoo.game.entity.Entity;

/**
 * Represents a Heart object in the game.
 * The Heart is used to display life/health status with multiple visual states.
 */
public class ObjHeart extends Entity {

    /**
     * Constructs a new Heart object.
     * Initializes the heart with three different visual states for life representation.
     */
    public ObjHeart() {
        super();

        name = "Heart";
        image1 = setup("/objects/life_1");
        image2 = setup("/objects/life_2");
        image3 = setup("/objects/life_3");
    }
}