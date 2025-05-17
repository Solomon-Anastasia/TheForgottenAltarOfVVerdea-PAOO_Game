package paoo.game.monster;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

import java.util.Random;

public class MON_Goblin extends Entity {
    public MON_Goblin(GamePanel gamePanel) {
        super(gamePanel);

        type = 2;
        name = "Goblin";
        speed = 2;
        maxLife = 3;
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 16;
        solidArea.height = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        direction = "right";

        getMonsterImage();
    }

    public void getMonsterImage() {

        right1 = setup("/monster/goblin_0");
        right2 = setup("/monster/goblin_1");
        right3 = setup("/monster/goblin_2");
        right4 = setup("/monster/goblin_3");
        right5 = setup("/monster/goblin_4");
        right6 = setup("/monster/goblin_5");

        left1 = setup("/monster/goblin_8");
        left2 = setup("/monster/goblin_9");
        left3 = setup("/monster/goblin_10");
        left4 = setup("/monster/goblin_11");
        left5 = setup("/monster/goblin_12");
        left6 = setup("/monster/goblin_13");

    }

    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 20) {
            Random random = new Random();
            int i = random.nextInt(51) + 1;

            // Simple AI
            if (i <= 25) {
                direction = "left";
            }
            if (i > 25 && i <= 50) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }
}


