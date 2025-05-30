package paoo.game.monster;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

import java.util.Random;

/**
 * Represents a Goblin monster entity in the game.
 * The Goblin is a basic enemy that moves randomly left and right with simple AI behavior.
 * It extends the Entity class and provides specific characteristics for goblin monsters.
 */
public class MON_Goblin extends Entity {
    /**
     * Constructs a new Goblin monster with default attributes and behavior.
     * Initializes the goblin's properties including health, speed, collision area,
     * and loads the monster's sprite images.
     *
     * @param gamePanel the main game panel that manages game state and rendering
     */
    public MON_Goblin(GamePanel gamePanel) {
        super(gamePanel);

        type = 2;
        name = "Goblin";
        speed = 2;
        maxLife = 2;
        life = maxLife;
        hpBarOn = true;

        solidArea.x = 5;
        solidArea.y = 18;

        solidArea.width = 16;
        solidArea.height = 16;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        direction = "right";

        getMonsterImage();
    }

    /**
     * Loads and sets up all sprite images for the Goblin monster.
     * This includes animation frames for both left and right movement directions.
     * Each direction has 6 frames for smooth animation cycles.
     * <p>
     * The sprite files are expected to be located in the "/monster/" resource directory
     * with naming convention: goblin_0 through goblin_13.
     */
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

    /**
     * Implements the Goblin's artificial intelligence behavior.
     * The goblin uses a simple random movement pattern that changes direction
     * every 20 action cycles (approximately every second at 60 FPS).
     * <p>
     * Movement behavior:
     * - 50% chance to move left (random values 1-25)
     * - 50% chance to move right (random values 26-50)
     * - 2% chance to stop/continue current direction (random value 51)
     * <p>
     * This method should be called once per game update cycle.
     */
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


