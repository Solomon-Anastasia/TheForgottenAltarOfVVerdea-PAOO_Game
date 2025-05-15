package paoo.game.object;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

public class ObjHeart extends Entity {

    public ObjHeart(GamePanel gamePanel) {
        super(gamePanel);

        name = "Heart";
        image1 = setup("/objects/life_1");
        image2 = setup("/objects/life_2");
        image3 = setup("/objects/life_3");
    }
}
