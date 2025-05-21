package paoo.game.entity;

import paoo.game.main.UtilityTool;
import paoo.game.panel.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

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

    protected String direction = "down";

    protected int spriteCounter = 0;
    protected int spriteNumber = 1;

    protected Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    protected int solidAreaDefaultX;
    protected int solidAreaDefaultY;
    protected boolean isCollisionOn = false;

    protected int actionLockCounter = 0;
    protected boolean invincible = false;
    protected int invincibleCounter = 0;
    protected int renderPriority = 1;

    protected String[] dialogues = new String[20];
    protected int dialogueIndex = 0;

    // Character status
    protected int maxLife;
    protected int life;

    // Item attribute
    protected String description = "";

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

    public void setRenderPriority(int priority) {
        this.renderPriority = priority;
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

    public void update() {
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

        // If collision is false, entity can move
        if (!isCollisionOn) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
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

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;
        int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSCREEN_X();
        int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSCREEN_Y();

        // Boundary (so not all map is loaded because it's not needed => save memory)
        if (worldX + gamePanel.getTILE_SIZE() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getSCREEN_X()
                && worldX - gamePanel.getTILE_SIZE() < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSCREEN_X()
                && worldY + gamePanel.getTILE_SIZE() > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getSCREEN_Y()
                && worldY - gamePanel.getTILE_SIZE() < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSCREEN_Y()
        ) {
            switch (direction) {
                case "up" -> image = getBufferedImage(spriteNumber, up1, up2, up3, up4);
                case "down" -> image = getBufferedImage(spriteNumber, down1, down2, down3, down4);
                case "left" -> image = getBufferedImage(spriteNumber, left1, left2, left3, left4);
                case "right" -> image = getBufferedImage(spriteNumber, right1, right2, right3, right4);
            }
            graphics2D.drawImage(image, screenX, screenY, width, height, null);
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
