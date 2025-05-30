package paoo.game.monster;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Represents a boss monster entity in the game - specifically the "Goblin Lord".
 * This class extends the base Entity class and implements boss-specific behavior
 * including enhanced AI, larger size, health bar display, and special attack patterns.
 *
 * <p>The boss is significantly larger than regular monsters (5x5 tiles) and has
 * more sophisticated movement AI that actively pursues the player when in range.</p>
 */
public class MON_Boss extends Entity {
    /**
     * The display name of this boss monster
     */
    private static final String MONSTER_NAME = "Goblin Lord";

    /**
     * Constructs a new boss monster with predefined characteristics.
     * Initializes the boss with enhanced stats, larger collision areas,
     * and loads all necessary sprite animations.
     *
     * @param gamePanel the main game panel that manages this entity
     */
    public MON_Boss(GamePanel gamePanel) {
        super(gamePanel);

        type = 2;
        name = MONSTER_NAME;
        speed = 2;
        maxLife = 5;
        boss = true;
        hpBarOn = true;
        life = maxLife;

        direction = "right";

        // Set up collision area for the large boss (5x5 tiles)
        int size = gamePanel.getTILE_SIZE() * 5;
        solidArea.x = 48;
        solidArea.y = 40;
        solidArea.width = size - 48 * 2;
        solidArea.height = size - 48;

        // Set up attack area
        attackArea.width = 120;
        attackArea.height = 120;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getMonsterImage();
        getMonsterAttackImage();

        setSize(gamePanel.getTILE_SIZE() * 5, gamePanel.getTILE_SIZE() * 5);
    }

    /**
     * Loads all walking animation sprites for the boss monster.
     * Sets up 4-frame animations for each direction (up, down, left, right).
     * Each direction has multiple frames to create smooth walking animations.
     */
    public void getMonsterImage() {
        // Right walking animation frames
        right1 = setup("/monster/boss_walk018");
        right2 = setup("/monster/boss_walk019");
        right3 = setup("/monster/boss_walk020");
        right4 = setup("/monster/boss_walk021");

        // Left walking animation frames
        left1 = setup("/monster/boss_walk012");
        left2 = setup("/monster/boss_walk013");
        left3 = setup("/monster/boss_walk014");
        left4 = setup("/monster/boss_walk015");

        // Up walking animation frames
        up1 = setup("/monster/boss_walk006");
        up2 = setup("/monster/boss_walk007");
        up3 = setup("/monster/boss_walk008");
        up4 = setup("/monster/boss_walk009");

        // Down walking animation frames
        down1 = setup("/monster/boss_walk000");
        down2 = setup("/monster/boss_walk001");
        down3 = setup("/monster/boss_walk002");
        down4 = setup("/monster/boss_walk003");
    }

    /**
     * Loads all attack animation sprites for the boss monster.
     * Sets up 4-frame attack animations for each direction.
     * These animations are displayed when the boss is performing an attack.
     */
    public void getMonsterAttackImage() {
        // Right attack animation frames
        attack_right_1 = setup("/monster/boss_attack024");
        attack_right_2 = setup("/monster/boss_attack025");
        attack_right_3 = setup("/monster/boss_attack026");
        attack_right_4 = setup("/monster/boss_attack027");

        // Left attack animation frames
        attack_left_1 = setup("/monster/boss_attack016");
        attack_left_2 = setup("/monster/boss_attack017");
        attack_left_3 = setup("/monster/boss_attack018");
        attack_left_4 = setup("/monster/boss_attack019");

        // Up attack animation frames
        attack_up_1 = setup("/monster/boss_attack008");
        attack_up_2 = setup("/monster/boss_attack009");
        attack_up_3 = setup("/monster/boss_attack010");
        attack_up_4 = setup("/monster/boss_attack011");

        // Down attack animation frames
        attack_down_1 = setup("/monster/boss_attack000");
        attack_down_2 = setup("/monster/boss_attack001");
        attack_down_3 = setup("/monster/boss_attack002");
        attack_down_4 = setup("/monster/boss_attack003");
    }

    /**
     * Defines the AI behavior for the boss monster.
     *
     * <p>The boss has two main behavioral modes:</p>
     * <ul>
     *   <li><strong>Aggressive mode:</strong> When the player is within 10 tiles,
     *       the boss actively moves toward the player with a 60-frame movement rate</li>
     *   <li><strong>Patrol mode:</strong> When the player is far away, the boss
     *       moves randomly in one of four directions, changing direction every 80 frames</li>
     * </ul>
     *
     * <p>Additionally, the boss will attempt to attack if the player is within
     * range (7 tiles horizontally, 5 tiles vertically) and the boss is not
     * currently attacking.</p>
     */
    public void setAction() {
        // Aggressive behavior when player is close (within 10 tiles)
        if (getTileDistance(gamePanel.getPlayer()) < 10) {
            moveTowardPlayer(60);
        } else {
            // Random movement pattern when player is far away
            actionLockCounter++;

            if (actionLockCounter == 80) {
                Random random = new Random();
                int i = random.nextInt(100) + 1;

                // Simple AI - 25% chance for each direction
                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75) {
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }

        // Check if boss should attack (when not already attacking)
        if (!attacking) {
            checkAttackOrNot(60, gamePanel.getTILE_SIZE() * 7, gamePanel.getTILE_SIZE() * 5);
        }
    }

    /**
     * Renders the boss monster on the screen with appropriate animations and effects.
     *
     * <p>This method handles:</p>
     * <ul>
     *   <li>Screen position calculation relative to the player's camera</li>
     *   <li>Boundary checking for performance optimization (only draws when visible)</li>
     *   <li>Animation frame selection based on current direction and state</li>
     *   <li>Attack animation positioning adjustments</li>
     *   <li>Invincibility visual effect (transparency when taking damage)</li>
     * </ul>
     *
     * @param graphics2D the Graphics2D context used for rendering
     */
    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;
        int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSCREEN_X();
        int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSCREEN_Y();

        // Boundary check for performance optimization
        // Only render if the boss is visible on screen
        int bossSize = gamePanel.getTILE_SIZE() * 5;
        if (worldX + bossSize > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getSCREEN_X()
                && worldX - bossSize < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSCREEN_X()
                && worldY + bossSize > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getSCREEN_Y()
                && worldY - bossSize < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSCREEN_Y()
        ) {
            int tempScreenX = screenX;
            int tempScreenY = screenY;

            // Select appropriate sprite based on direction and attack state
            switch (direction) {
                case "up" -> {
                    if (!attacking) {
                        image = getBufferedImage(spriteNumber, up1, up2, up3, up4);
                    } else {
                        // Adjust position for attack animation
                        tempScreenY = screenY - up1.getHeight();
                        image = getBufferedImage(spriteNumber, attack_up_1, attack_up_2, attack_up_3, attack_up_4);
                    }
                }
                case "down" -> {
                    if (!attacking) {
                        image = getBufferedImage(spriteNumber, down1, down2, down3, down4);
                    } else {
                        image = getBufferedImage(spriteNumber, attack_down_1, attack_down_2, attack_down_3, attack_down_4);
                    }
                }
                case "left" -> {
                    if (!attacking) {
                        image = getBufferedImage(spriteNumber, left1, left2, left3, left4);
                    } else {
                        // Adjust position for attack animation
                        tempScreenX = screenX - left1.getWidth();
                        image = getBufferedImage(spriteNumber, attack_left_1, attack_left_2, attack_left_3, attack_left_4);
                    }
                }
                case "right" -> {
                    if (!attacking) {
                        image = getBufferedImage(spriteNumber, right1, right2, right3, right4);
                    } else {
                        image = getBufferedImage(spriteNumber, attack_right_1, attack_right_2, attack_right_3, attack_right_4);
                    }
                }
            }

            // Apply transparency effect when boss is invincible (recently damaged)
            if (invincible) {
                graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }

            // Draw the boss sprite
            graphics2D.drawImage(image, tempScreenX, tempScreenY, width, height, null);

            // Reset transparency to normal
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
}