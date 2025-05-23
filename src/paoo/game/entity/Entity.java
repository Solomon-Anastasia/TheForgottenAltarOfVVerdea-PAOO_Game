package paoo.game.entity;

import paoo.game.main.UtilityTool;
import paoo.game.panel.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Entity {
    protected GamePanel gamePanel;

    protected int worldX;
    protected int worldY;
    protected int speed;

    // Default size
    protected int width;
    protected int height;

    protected BufferedImage up1, up2, up3, up4, up5, up6;
    protected BufferedImage down1, down2, down3, down4, down5, down6;
    protected BufferedImage left1, left2, left3, left4, left5, left6;
    protected BufferedImage right1, right2, right3, right4, right5, right6;
    protected BufferedImage attack_right_1, attack_right_2, attack_right_3, attack_right_4;
    protected BufferedImage attack_left_1, attack_left_2, attack_left_3, attack_left_4;
    protected BufferedImage attack_down_1, attack_down_2, attack_down_3, attack_down_4;
    protected BufferedImage attack_up_1, attack_up_2, attack_up_3, attack_up_4;

    protected BufferedImage upIdle1, upIdle2;
    protected BufferedImage downIdle1, downIdle2;
    protected BufferedImage leftIdle1, leftIdle2;
    protected BufferedImage rightIdle1, rightIdle2;

    protected BufferedImage image1;
    protected BufferedImage image2;
    protected BufferedImage image3;

    protected String name;
    protected boolean collision = false;
    protected int type; // 0 = player, 1 = npc, 2 = monster
    protected final int TYPE_CONSUMABLE = 7;

    protected String direction = "down";

    protected int spriteCounter = 0;
    protected int shotAvailableCounter = 0;
    protected int hpBarCounter = 0;
    protected int spriteNumber = 1;

    protected Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    protected Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    protected int solidAreaDefaultX;
    protected int solidAreaDefaultY;
    protected boolean isCollisionOn = false;

    protected int actionLockCounter = 0;
    protected boolean invincible = false;
    protected int invincibleCounter = 0;
    protected int renderPriority = 1;
    protected boolean attacking = false;

    protected String[] dialogues = new String[20];
    protected int dialogueIndex = 0;

    // Character status
    protected int maxLife;
    protected int life;
    protected boolean hpBarOn = false;

    protected boolean boss = false;

    // Item attribute
    protected String description = "";
    protected boolean stackable = false;
    protected int amount = 0;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        width = gamePanel.getTILE_SIZE();
        height = gamePanel.getTILE_SIZE();
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public boolean isHpBarOn() {
        return hpBarOn;
    }

    public String getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public int getLife() {
        return life;
    }

    public BufferedImage getImage1() {
        return image1;
    }

    public BufferedImage getImage2() {
        return image2;
    }

    public boolean isCollision() {
        return collision;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getImage3() {
        return image3;
    }

    public int getRenderPriority() {
        return renderPriority;
    }

    public BufferedImage getDown2() {
        return down2;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public int getHpBarCounter() {
        return hpBarCounter;
    }

    public boolean isBoss() {
        return boss;
    }

    public void setHpBarCounter(int hpBarCounter) {
        this.hpBarCounter = hpBarCounter;
    }

    public void setHpBarOn(boolean hpBarOn) {
        this.hpBarOn = hpBarOn;
    }

    public void setCollisionOn(boolean collisionOn) {
        isCollisionOn = collisionOn;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setSolidArea(int xOffset, int yOffset, int width, int height) {
        this.solidArea = new Rectangle(xOffset, yOffset, width, height);
        this.solidAreaDefaultX = xOffset;
        this.solidAreaDefaultY = yOffset;
    }

    public void setAction() {
    }

    public void checkAttackOrNot(int rate, int straight, int horizontal) {
        boolean targetInRange = false;

        int xDistance = getXDistance(gamePanel.getPlayer());
        int yDistance = getYDistance(gamePanel.getPlayer());

        switch (direction) {
            case "up" -> {
                if (gamePanel.getPlayer().getCenterY() < getCenterY() && yDistance < straight && xDistance < horizontal) {
                    targetInRange = true;
                }
            }
            case "down" -> {
                if (gamePanel.getPlayer().getCenterY() > getCenterY() && yDistance < straight && xDistance < horizontal) {
                    targetInRange = true;
                }
            }
            case "left" -> {
                if (gamePanel.getPlayer().getCenterX() < getCenterX() && yDistance < straight && xDistance < horizontal) {
                    targetInRange = true;
                }
            }
            case "right" -> {
                if (gamePanel.getPlayer().getCenterX() > getCenterX() && yDistance < straight && xDistance < horizontal) {
                    targetInRange = true;
                }
            }
        }

        if (targetInRange) {
            // Check if it initiates an attack
            int i = new Random().nextInt(rate);

            if (i == 0) {
                attacking = true;
                spriteNumber = 1;
                spriteCounter = 0;
                shotAvailableCounter = 0;
            }
        }
    }

    public void moveTowardPlayer(int interval) {
        actionLockCounter++;

        if (actionLockCounter > interval) {
            if (getXDistance(gamePanel.getPlayer()) > getYDistance(gamePanel.getPlayer())) {
                if (gamePanel.getPlayer().getCenterX() < getCenterX()) {
                    direction = "left";
                } else {
                    direction = "right";
                }
            } else if (getXDistance(gamePanel.getPlayer()) < getYDistance(gamePanel.getPlayer())) {
                if (gamePanel.getPlayer().getCenterY() < getCenterY()) {
                    direction = "up";
                } else {
                    direction = "down";
                }
            }

            actionLockCounter = 0;
        }
    }

    public double getTileDistance(Entity entity) {
        int xDistance = Math.abs(this.worldX - entity.getWorldX());
        int yDistance = Math.abs(this.worldY - entity.getWorldY());

        int xTileDistance = xDistance / gamePanel.getTILE_SIZE();
        int yTileDistance = yDistance / gamePanel.getTILE_SIZE();

        return Math.sqrt(xTileDistance * xTileDistance + yTileDistance * yTileDistance);
    }

    public int getCenterX() {
        return worldX + up1.getWidth() / 2;
    }

    public int getCenterY() {
        return worldY + up1.getHeight() / 2;
    }

    public int getXDistance(Entity entity) {
        return Math.abs(getCenterX() - entity.getCenterX());
    }

    public int getYDistance(Entity entity) {
        return Math.abs(getCenterY() - entity.getWorldY());
    }

    public void attacking() {
        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNumber = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 10) {
            spriteNumber = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case "up" -> worldY -= attackArea.height;
                case "down" -> worldY += attackArea.height;
                case "left" -> worldX -= attackArea.width;
                case "right" -> worldX += attackArea.width;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            if (type == 2) {
                if (gamePanel.getCollisionChecker().checkPlayer(this)) {
                    Entity[] target = new Entity[10];
                    target[0] = gamePanel.getPlayer();

                    int monsterIndex = gamePanel.getCollisionChecker().checkEntity(this, target);
                    gamePanel.getPlayer().contactMonster(monsterIndex);
                }
            } else {
                int monsterIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getMonster());
                gamePanel.getPlayer().damageMonster(monsterIndex);
            }

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }

        if (spriteCounter <= 5) {
            spriteNumber = 1;
        } else if (spriteCounter <= 10) {
            spriteNumber = 2;
        } else if (spriteCounter <= 15) {
            spriteNumber = 3;
        } else if (spriteCounter <= 20) {
            spriteNumber = 4;
        }

        if (spriteCounter > 20) {
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void update() {
        if (attacking) {
            attacking();
        } else {
            setAction();

            isCollisionOn = false;
            gamePanel.getCollisionChecker().checkTile(this);
            gamePanel.getCollisionChecker().checkObject(this, false);
            gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getNpc());
            gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getMonster());

            boolean contactPlayer = gamePanel.getCollisionChecker().checkPlayer(this);

            if (this.type == 2 && contactPlayer) {
                if (!gamePanel.getPlayer().invincible) {
                    // We can give damage
                    gamePanel.getPlayer().life -= 1;
                    gamePanel.getPlayer().invincible = true;
                }
            }

            spriteCounter++;
            if (spriteCounter > 4) {
                switch (spriteNumber) {
                    case 1 -> spriteNumber = 2;
                    case 2 -> spriteNumber = 3;
                    case 3 -> spriteNumber = 4;
                    case 4 -> spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        }

        // If collision is false, entity can move
        if (!isCollisionOn) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
    }

    public int getScreenX() {
        return worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSCREEN_X();
    }

    public int getScreenY() {
        return worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSCREEN_Y();
    }

    public boolean inCamera() {
        boolean inCamera = false;

        if (worldX + gamePanel.getTILE_SIZE() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getSCREEN_X()
                && worldX - gamePanel.getTILE_SIZE() < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSCREEN_X()
                && worldY + gamePanel.getTILE_SIZE() > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getSCREEN_Y()
                && worldY - gamePanel.getTILE_SIZE() < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSCREEN_Y()
        ) {
            inCamera =true;
        }

        return inCamera;
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;
        int screenX = getScreenX();
        int screenY = getScreenY();

        // Boundary (so not all map is loaded because it's not needed => save memory)
        if (inCamera()) {
            switch (direction) {
                case "up" -> image = getBufferedImage(spriteNumber, up1, up2, up3, up4);
                case "down" -> image = getBufferedImage(spriteNumber, down1, down2, down3, down4);
                case "left" -> image = getBufferedImage(spriteNumber, left1, left2, left3, left4);
                case "right" -> image = getBufferedImage(spriteNumber, right1, right2, right3, right4);
            }

            if (invincible) {
                graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }

            graphics2D.drawImage(image, screenX, screenY, width, height, null);
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gamePanel.getUi().setCurrentDialogue(dialogues[dialogueIndex]);
        dialogueIndex++;

        switch (gamePanel.getPlayer().direction) {
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }
    }

    public void interact() {
    }

    protected BufferedImage getBufferedImage(int frame, BufferedImage frame1, BufferedImage frame2, BufferedImage frame3, BufferedImage frame4) {
        return switch (frame) {
            case 2 -> frame2;
            case 3 -> frame3;
            case 4 -> frame4;
            default -> frame1;
        };
    }

    public BufferedImage setup(String imagePath) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = utilityTool.scaleImage(image, gamePanel.getTILE_SIZE(), gamePanel.getTILE_SIZE());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return image;
    }
}
