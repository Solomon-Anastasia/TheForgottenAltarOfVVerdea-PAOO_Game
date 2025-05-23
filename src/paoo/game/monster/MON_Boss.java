package paoo.game.monster;

import paoo.game.entity.Entity;
import paoo.game.panel.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MON_Boss extends Entity {
    private static final String MONSTER_NAME = "Goblin Lord";

    public MON_Boss(GamePanel gamePanel) {
        super(gamePanel);

        type = 2;
        name = MONSTER_NAME;
        speed = 2;
        // TODO: Maybe put bigger max life
        maxLife = 5;
        boss = true;
        hpBarOn = true;
        life = maxLife;

        direction = "right";

        int size = gamePanel.getTILE_SIZE() * 5;
        solidArea.x = 48;
        solidArea.y = 40;
        solidArea.width = size - 48 * 2;
        solidArea.height = size - 48;

        attackArea.width = 120;
        attackArea.height = 120;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getMonsterImage();
        getMonsterAttackImage();

        setSize(gamePanel.getTILE_SIZE() * 5, gamePanel.getTILE_SIZE() * 5);
    }

    public void getMonsterImage() {
        right1 = setup("/monster/boss_walk018");
        right2 = setup("/monster/boss_walk019");
        right3 = setup("/monster/boss_walk020");
        right4 = setup("/monster/boss_walk021");

        left1 = setup("/monster/boss_walk012");
        left2 = setup("/monster/boss_walk013");
        left3 = setup("/monster/boss_walk014");
        left4 = setup("/monster/boss_walk015");

        up1 = setup("/monster/boss_walk006");
        up2 = setup("/monster/boss_walk007");
        up3 = setup("/monster/boss_walk008");
        up4 = setup("/monster/boss_walk009");

        down1 = setup("/monster/boss_walk000");
        down2 = setup("/monster/boss_walk001");
        down3 = setup("/monster/boss_walk002");
        down4 = setup("/monster/boss_walk003");
    }

    public void getMonsterAttackImage() {
        attack_right_1 = setup("/monster/boss_attack024");
        attack_right_2 = setup("/monster/boss_attack025");
        attack_right_3 = setup("/monster/boss_attack026");
        attack_right_4 = setup("/monster/boss_attack027");

        attack_left_1 = setup("/monster/boss_attack016");
        attack_left_2 = setup("/monster/boss_attack017");
        attack_left_3 = setup("/monster/boss_attack018");
        attack_left_4 = setup("/monster/boss_attack019");

        attack_up_1 = setup("/monster/boss_attack008");
        attack_up_2 = setup("/monster/boss_attack009");
        attack_up_3 = setup("/monster/boss_attack010");
        attack_up_4 = setup("/monster/boss_attack011");

        attack_down_1 = setup("/monster/boss_attack000");
        attack_down_2 = setup("/monster/boss_attack001");
        attack_down_3 = setup("/monster/boss_attack002");
        attack_down_4 = setup("/monster/boss_attack003");
    }

    public void setAction() {
        if (getTileDistance(gamePanel.getPlayer()) < 10) {
            moveTowardPlayer(60);
        } else {
            actionLockCounter++;

            if (actionLockCounter == 80) {
                Random random = new Random();
                int i = random.nextInt(100) + 1;

                // Simple AI
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

        if (!attacking) {
            checkAttackOrNot(60, gamePanel.getTILE_SIZE() * 7, gamePanel.getTILE_SIZE() * 5);
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;
        int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSCREEN_X();
        int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSCREEN_Y();

        // Boundary (so not all map is loaded because it's not needed => save memory)
        int bossSize = gamePanel.getTILE_SIZE() * 5;
        if (worldX + bossSize > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getSCREEN_X()
                && worldX - bossSize < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSCREEN_X()
                && worldY + bossSize > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getSCREEN_Y()
                && worldY - bossSize < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSCREEN_Y()
        ) {
            int tempScreenX = screenX;
            int tempScreenY = screenY;

            switch (direction) {
                case "up" -> {
                    if (!attacking) {
                        image = getBufferedImage(spriteNumber, up1, up2, up3, up4);
                    } else {
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

            if (invincible) {
                graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }

            graphics2D.drawImage(image, tempScreenX, tempScreenY, width, height, null);
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

}
