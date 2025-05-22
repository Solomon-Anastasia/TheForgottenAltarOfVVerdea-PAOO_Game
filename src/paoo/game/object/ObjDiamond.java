package paoo.game.object;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

public class ObjDiamond extends Entity {
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
