package paoo.game.entity;

import paoo.game.main.UtilityTool;
import paoo.game.panel.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

/**
 * Base class for all game entities including players, NPCs, monsters, and items.
 * Provides common functionality for movement, animation, collision detection, and rendering.
 */
public class Entity {
    /**
     * Reference to the main game panel
     */
    protected GamePanel gamePanel;

    /**
     * World X coordinate of the entity
     */
    protected int worldX;
    /**
     * World Y coordinate of the entity
     */
    protected int worldY;
    /**
     * Movement speed of the entity
     */
    protected int speed;

    /**
     * Width of the entity in pixels
     */
    protected int width;
    /**
     * Height of the entity in pixels
     */
    protected int height;

    // Movement animation sprites
    /**
     * Animation frames for moving up
     */
    protected BufferedImage up1, up2, up3, up4, up5, up6;
    /**
     * Animation frames for moving down
     */
    protected BufferedImage down1, down2, down3, down4, down5, down6;
    /**
     * Animation frames for moving left
     */
    protected BufferedImage left1, left2, left3, left4, left5, left6;
    /**
     * Animation frames for moving right
     */
    protected BufferedImage right1, right2, right3, right4, right5, right6;

    // Attack animation sprites
    /**
     * Attack animation frames for right direction
     */
    protected BufferedImage attack_right_1, attack_right_2, attack_right_3, attack_right_4;
    /**
     * Attack animation frames for left direction
     */
    protected BufferedImage attack_left_1, attack_left_2, attack_left_3, attack_left_4;
    /**
     * Attack animation frames for down direction
     */
    protected BufferedImage attack_down_1, attack_down_2, attack_down_3, attack_down_4;
    /**
     * Attack animation frames for up direction
     */
    protected BufferedImage attack_up_1, attack_up_2, attack_up_3, attack_up_4;

    // Idle animation sprites
    /**
     * Idle animation frames for up direction
     */
    protected BufferedImage upIdle1, upIdle2;
    /**
     * Idle animation frames for down direction
     */
    protected BufferedImage downIdle1, downIdle2;
    /**
     * Idle animation frames for left direction
     */
    protected BufferedImage leftIdle1, leftIdle2;
    /**
     * Idle animation frames for right direction
     */
    protected BufferedImage rightIdle1, rightIdle2;

    /**
     * General purpose image references
     */
    protected BufferedImage image1;
    protected BufferedImage image2;
    protected BufferedImage image3;

    /**
     * Name of the entity
     */
    protected String name;
    /**
     * Whether this entity has collision enabled
     */
    protected boolean collision = false;
    /**
     * Type of entity: 0 = player, 1 = npc, 2 = monster
     */
    protected int type;
    /**
     * Constant for consumable item type
     */
    protected final int TYPE_CONSUMABLE = 7;

    /**
     * Current movement direction
     */
    protected String direction = "down";
    /**
     * Counter for sprite animation timing
     */
    protected int spriteCounter = 0;
    /**
     * Counter for shot availability cooldown
     */
    protected int shotAvailableCounter = 0;
    /**
     * Counter for HP bar display duration
     */
    protected int hpBarCounter = 0;
    /**
     * Current sprite frame number
     */
    protected int spriteNumber = 1;

    /**
     * Collision detection rectangle
     */
    protected Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    /**
     * Attack range rectangle
     */
    protected Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    /**
     * Default X offset for solid area
     */
    protected int solidAreaDefaultX;
    /**
     * Default Y offset for solid area
     */
    protected int solidAreaDefaultY;
    /**
     * Whether collision is currently active
     */
    protected boolean isCollisionOn = false;

    /**
     * Counter for action timing delays
     */
    protected int actionLockCounter = 0;
    /**
     * Whether entity is currently invincible
     */
    protected boolean invincible = false;
    /**
     * Counter for invincibility duration
     */
    protected int invincibleCounter = 0;
    /**
     * Rendering priority for layered drawing
     */
    protected int renderPriority = 1;
    /**
     * Whether entity is currently attacking
     */
    protected boolean attacking = false;

    /**
     * Array of dialogue strings for NPCs
     */
    protected String[] dialogues = new String[20];
    /**
     * Current dialogue index
     */
    protected int dialogueIndex = 0;

    // Character status
    /**
     * Maximum life points
     */
    protected int maxLife;
    /**
     * Current life points
     */
    protected int life;
    /**
     * Whether HP bar should be displayed
     */
    protected boolean hpBarOn = false;

    /**
     * Whether this entity is a boss
     */
    protected boolean boss = false;

    // Item attributes
    /**
     * Description text for items
     */
    protected String description = "";
    /**
     * Whether item can be stacked
     */
    protected boolean stackable = false;
    /**
     * Quantity amount for stackable items
     */
    protected int amount = 0;

    /**
     * Constructs a new Entity with the specified game panel.
     * Initializes default size based on tile size.
     *
     * @param gamePanel the main game panel reference
     */
    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        width = gamePanel.getTILE_SIZE();
        height = gamePanel.getTILE_SIZE();
    }

    // Getter methods with Javadoc

    /**
     * Gets the world X coordinate of this entity.
     *
     * @return the world X coordinate
     */
    public int getWorldX() {
        return worldX;
    }

    /**
     * Gets the world Y coordinate of this entity.
     *
     * @return the world Y coordinate
     */
    public int getWorldY() {
        return worldY;
    }

    /**
     * Gets the collision detection rectangle.
     *
     * @return the solid area rectangle
     */
    public Rectangle getSolidArea() {
        return solidArea;
    }

    /**
     * Checks if the HP bar is currently displayed.
     *
     * @return true if HP bar is on, false otherwise
     */
    public boolean isHpBarOn() {
        return hpBarOn;
    }

    /**
     * Gets the current movement direction.
     *
     * @return the direction string ("up", "down", "left", "right")
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Gets the movement speed of this entity.
     *
     * @return the speed value
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Gets the default X offset for the solid area.
     *
     * @return the default X offset
     */
    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    /**
     * Gets the default Y offset for the solid area.
     *
     * @return the default Y offset
     */
    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

    /**
     * Gets the maximum life points.
     *
     * @return the maximum life value
     */
    public int getMaxLife() {
        return maxLife;
    }

    /**
     * Gets the current life points.
     *
     * @return the current life value
     */
    public int getLife() {
        return life;
    }

    /**
     * Gets the first general purpose image.
     *
     * @return the image1 BufferedImage
     */
    public BufferedImage getImage1() {
        return image1;
    }

    /**
     * Gets the second general purpose image.
     *
     * @return the image2 BufferedImage
     */
    public BufferedImage getImage2() {
        return image2;
    }

    /**
     * Checks if collision is enabled for this entity.
     *
     * @return true if collision is enabled, false otherwise
     */
    public boolean isCollision() {
        return collision;
    }

    /**
     * Gets the name of this entity.
     *
     * @return the entity name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the third general purpose image.
     *
     * @return the image3 BufferedImage
     */
    public BufferedImage getImage3() {
        return image3;
    }

    /**
     * Gets the render priority for layered drawing.
     *
     * @return the render priority value
     */
    public int getRenderPriority() {
        return renderPriority;
    }

    /**
     * Gets the second frame of the down movement animation.
     *
     * @return the down2 BufferedImage
     */
    public BufferedImage getDown2() {
        return down2;
    }

    /**
     * Gets the description text for items.
     *
     * @return the description string
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the quantity amount for stackable items.
     *
     * @return the amount value
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Gets the HP bar display counter.
     *
     * @return the HP bar counter value
     */
    public int getHpBarCounter() {
        return hpBarCounter;
    }

    /**
     * Checks if this entity is a boss.
     *
     * @return true if this is a boss entity, false otherwise
     */
    public boolean isBoss() {
        return boss;
    }

    // Setter methods with Javadoc

    /**
     * Sets the HP bar display counter.
     *
     * @param hpBarCounter the new HP bar counter value
     */
    public void setHpBarCounter(int hpBarCounter) {
        this.hpBarCounter = hpBarCounter;
    }

    /**
     * Sets whether the HP bar should be displayed.
     *
     * @param hpBarOn true to display HP bar, false to hide it
     */
    public void setHpBarOn(boolean hpBarOn) {
        this.hpBarOn = hpBarOn;
    }

    /**
     * Sets the collision state for this entity.
     *
     * @param collisionOn true to enable collision, false to disable
     */
    public void setCollisionOn(boolean collisionOn) {
        isCollisionOn = collisionOn;
    }

    /**
     * Sets the world X coordinate.
     *
     * @param worldX the new world X coordinate
     */
    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    /**
     * Sets the maximum life points.
     *
     * @param maxLife the new maximum life value
     */
    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    /**
     * Sets the world Y coordinate.
     *
     * @param worldY the new world Y coordinate
     */
    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    /**
     * Sets the current life points.
     *
     * @param life the new life value
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * Sets the size of this entity.
     *
     * @param width  the new width in pixels
     * @param height the new height in pixels
     */
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Sets the quantity amount for stackable items.
     *
     * @param amount the new amount value
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Defines the AI behavior for this entity.
     * Override this method in subclasses to implement specific behaviors.
     */
    public void setAction() {
    }

    /**
     * Checks if the entity should initiate an attack based on player proximity and direction.
     *
     * @param rate       the random rate for attack initiation (1 in rate chance)
     * @param straight   the maximum distance in the facing direction
     * @param horizontal the maximum distance perpendicular to facing direction
     */
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

    /**
     * Makes the entity move toward the player at specified intervals.
     *
     * @param interval the number of frames between direction changes
     */
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

    /**
     * Calculates the distance between this entity and another entity in tiles.
     *
     * @param entity the target entity
     * @return the distance in tiles as a double
     */
    public double getTileDistance(Entity entity) {
        int xDistance = Math.abs(this.worldX - entity.getWorldX());
        int yDistance = Math.abs(this.worldY - entity.getWorldY());

        int xTileDistance = xDistance / gamePanel.getTILE_SIZE();
        int yTileDistance = yDistance / gamePanel.getTILE_SIZE();

        return Math.sqrt(xTileDistance * xTileDistance + yTileDistance * yTileDistance);
    }

    /**
     * Gets the center X coordinate of this entity.
     *
     * @return the center X coordinate in world space
     */
    public int getCenterX() {
        return worldX + up1.getWidth() / 2;
    }

    /**
     * Gets the center Y coordinate of this entity.
     *
     * @return the center Y coordinate in world space
     */
    public int getCenterY() {
        return worldY + up1.getHeight() / 2;
    }

    /**
     * Calculates the horizontal distance between this entity and another entity.
     *
     * @param entity the target entity
     * @return the absolute horizontal distance in pixels
     */
    public int getXDistance(Entity entity) {
        return Math.abs(getCenterX() - entity.getCenterX());
    }

    /**
     * Calculates the vertical distance between this entity and another entity.
     *
     * @param entity the target entity
     * @return the absolute vertical distance in pixels
     */
    public int getYDistance(Entity entity) {
        return Math.abs(getCenterY() - entity.getWorldY());
    }

    /**
     * Handles the attack animation and collision detection.
     * Updates sprite frames and performs attack collision checks.
     */
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

    /**
     * Updates the entity's state including movement, animation, and collision detection.
     * Called once per frame during the game loop.
     */
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

    /**
     * Calculates the screen X coordinate for rendering based on camera position.
     *
     * @return the screen X coordinate
     */
    public int getScreenX() {
        return worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSCREEN_X();
    }

    /**
     * Calculates the screen Y coordinate for rendering based on camera position.
     *
     * @return the screen Y coordinate
     */
    public int getScreenY() {
        return worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSCREEN_Y();
    }

    /**
     * Checks if this entity is within the camera view bounds.
     *
     * @return true if the entity should be rendered, false otherwise
     */
    public boolean inCamera() {
        return worldX + gamePanel.getTILE_SIZE() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getSCREEN_X()
                && worldX - gamePanel.getTILE_SIZE() < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSCREEN_X()
                && worldY + gamePanel.getTILE_SIZE() > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getSCREEN_Y()
                && worldY - gamePanel.getTILE_SIZE() < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSCREEN_Y();
    }

    /**
     * Renders the entity to the screen with appropriate sprite and effects.
     *
     * @param graphics2D the Graphics2D context for rendering
     */
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

    /**
     * Handles NPC dialogue interaction.
     * Advances through dialogue array and faces the player.
     */
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

    /**
     * Handles entity interaction behavior.
     * Override this method in subclasses for specific interaction logic.
     */
    public void interact() {
    }

    /**
     * Returns the appropriate sprite frame based on the animation frame number.
     *
     * @param frame  the current frame number (1-4)
     * @param frame1 the first animation frame
     * @param frame2 the second animation frame
     * @param frame3 the third animation frame
     * @param frame4 the fourth animation frame
     * @return the appropriate BufferedImage for the current frame
     */
    protected BufferedImage getBufferedImage(int frame, BufferedImage frame1, BufferedImage frame2, BufferedImage frame3, BufferedImage frame4) {
        return switch (frame) {
            case 2 -> frame2;
            case 3 -> frame3;
            case 4 -> frame4;
            default -> frame1;
        };
    }

    /**
     * Loads and scales an image from the resources' folder.
     *
     * @param imagePath the path to the image file (without .png extension)
     * @return the loaded and scaled BufferedImage, or null if loading failed
     */
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