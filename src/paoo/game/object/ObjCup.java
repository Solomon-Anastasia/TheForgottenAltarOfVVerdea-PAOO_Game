package paoo.game.object;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

public class ObjCup extends Entity {
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
