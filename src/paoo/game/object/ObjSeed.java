package paoo.game.object;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

public class ObjSeed extends Entity {

    public ObjSeed(GamePanel gamePanel) {
        super(gamePanel);
        name = "Seed";
        down1 = setup("/objects/carrot_00");
        collision = true;
        setSize(16, 16);

        // Match collision to the size of image
        setSolidArea(0, 0, 16, 16);
    }
}
