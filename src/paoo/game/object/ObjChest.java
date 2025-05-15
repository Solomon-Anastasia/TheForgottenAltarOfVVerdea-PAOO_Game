package paoo.game.object;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

public class ObjChest extends Entity {

    public ObjChest(GamePanel gamePanel) {
        super(gamePanel);

        name = "Chest";
        down1 = setup("/objects/chest");
        collision = true;
        renderPriority = 0;

        // Match collision to the size of image
        setSize(48, 48);
    }
}
