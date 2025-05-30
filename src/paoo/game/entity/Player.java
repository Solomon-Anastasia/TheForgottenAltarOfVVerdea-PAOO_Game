package paoo.game.entity;

import paoo.game.handler.KeyHandler;
import paoo.game.object.ObjCarrot;
import paoo.game.panel.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Represents the player character in the game.
 * This class extends Entity and handles all player-specific functionality including
 * movement, attacking, inventory management, object interaction, and rendering.
 * <p>
 * The player has a fixed screen position and moves around the world by changing
 * their world coordinates. They can collect items, attack monsters, interact with
 * NPCs, and progress through different game levels.
 */
public class Player extends Entity {
    /**
     * Handler for keyboard input
     */
    KeyHandler keyHandler;

    /**
     * Fixed X coordinate where player is drawn on screen
     */
    private final int SCREEN_X;

    /**
     * Fixed Y coordinate where player is drawn on screen
     */
    private final int SCREEN_Y;

    /**
     * Number of carrots collected by the player
     */
    private int nrCarrots = 0;

    /**
     * Flag indicating if the player is currently moving
     */
    private boolean isMoving = false;

    /**
     * Flag to cancel attack animation when interacting with NPCs
     */
    private boolean attackCanceled = false;

    /**
     * Player's inventory containing collected items
     */
    private ArrayList<Entity> inventory = new ArrayList<>();

    /**
     * Maximum number of items that can be stored in inventory
     */
    private final int MAX_INVENTORY_SIZE = 20;

    /**
     * Flag indicating if player is ready to teleport to next level
     */
    private boolean teleportReady = false;

    /**
     * Constructs a new Player instance.
     * Initializes the player's position, collision areas, and loads all sprites.
     *
     * @param gamePanel  the main game panel containing game state and utilities
     * @param keyHandler the keyboard input handler for player controls
     */
    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);

        this.keyHandler = keyHandler;

        // Calculate center screen position for player
        SCREEN_X = gamePanel.getSCREEN_WIDTH() / 2 - (gamePanel.getTILE_SIZE() / 2);
        SCREEN_Y = gamePanel.getSCREEN_HEIGHT() / 2 - (gamePanel.getTILE_SIZE() / 2);

        // Set up collision detection area
        solidArea.x = 18;
        solidArea.y = 20;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        solidArea.width = 10;
        solidArea.height = 13;

        renderPriority = 1;

        // Set up attack area
        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        setItems();
        getPlayerAttackImage();
    }

    /**
     * Gets the teleport ready status.
     *
     * @return true if player is ready to teleport, false otherwise
     */
    public boolean isTeleportReady() {
        return teleportReady;
    }

    /**
     * Gets the player's fixed screen X coordinate.
     *
     * @return the X coordinate where player is drawn on screen
     */
    public int getSCREEN_X() {
        return SCREEN_X;
    }

    /**
     * Gets the player's fixed screen Y coordinate.
     *
     * @return the Y coordinate where player is drawn on screen
     */
    public int getSCREEN_Y() {
        return SCREEN_Y;
    }

    /**
     * Gets the number of carrots collected by the player.
     *
     * @return the current carrot count
     */
    public int getNrCarrots() {
        return nrCarrots;
    }

    /**
     * Gets the player's inventory.
     *
     * @return ArrayList containing all items in player's inventory
     */
    public ArrayList<Entity> getInventory() {
        return inventory;
    }

    /**
     * Sets the attack canceled flag.
     * Used to prevent attack animation when interacting with NPCs.
     *
     * @param attackCanceled true to cancel attack, false otherwise
     */
    public void setAttackCanceled(boolean attackCanceled) {
        this.attackCanceled = attackCanceled;
    }

    /**
     * Sets the player's default starting values including position, speed, and health.
     * Player starts at tile coordinates (45, 26) facing down with full health.
     */
    public void setDefaultValues() {
        // Player position in the map
        worldX = gamePanel.getTILE_SIZE() * 45; // Start column
        worldY = gamePanel.getTILE_SIZE() * 26; // Start line

        speed = 5;
        direction = "down";

        // Player status
        maxLife = 6;
        life = maxLife;
    }

    /**
     * Sets the player's position to specific tile coordinates.
     *
     * @param tileX the X tile coordinate to move to
     * @param tileY the Y tile coordinate to move to
     */
    public void setDefaultPosition(int tileX, int tileY) {
        worldX = tileX * gamePanel.getTILE_SIZE();
        worldY = tileY * gamePanel.getTILE_SIZE();
    }

    /**
     * Sets the player's starting position based on the current game level.
     * Each level has a different starting position:
     * Level 1: (45, 26)
     * Level 2: (30, 30)
     * Level 3+: (44, 65)
     */
    public void setDefaultPositions() {
        // Player position in the map
        if (gamePanel.getKeyHandler().getCurrentLevel() == 1) {
            setDefaultPosition(45, 26);
        } else if (gamePanel.getKeyHandler().getCurrentLevel() == 2) {
            setDefaultPosition(30, 30);
        } else {
            setDefaultPosition(44, 65);
        }

        direction = "down";
    }

    /**
     * Sets the teleport ready status.
     *
     * @param teleportReady true if player should be ready to teleport, false otherwise
     */
    public void setTeleportReady(boolean teleportReady) {
        this.teleportReady = teleportReady;
    }

    /**
     * Restores the player's health to maximum and removes invincibility.
     * Typically used when respawning or healing.
     */
    public void restoreLife() {
        life = maxLife;
        invincible = false;
    }

    /**
     * Clears all items from the player's inventory.
     * Used when starting a new game or resetting player state.
     */
    public void setItems() {
        inventory.clear();
    }

    /**
     * Handles the player's attack animation and collision detection.
     * Attack is disabled on level 1. The attack has multiple frames and
     * can damage monsters within the attack area during specific frames.
     */
    public void attacking() {
        spriteCounter++;

        // No attacking on level 1
        if (keyHandler.getCurrentLevel() == 1) {
            attacking = false;
            return;
        }

        // Handle attack animation frames
        if (spriteCounter <= 5) {
            spriteNumber = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 10) {
            spriteNumber = 2;

            // Save current position and collision area
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Extend attack area in attack direction
            switch (direction) {
                case "up" -> worldY -= attackArea.height;
                case "down" -> worldY += attackArea.height;
                case "left" -> worldX -= attackArea.width;
                case "right" -> worldX += attackArea.width;
            }

            // Use attack area for collision detection
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // Check for monster collision and damage
            int monsterIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getMonster());
            damageMonster(monsterIndex);

            // Restore original position and collision area
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }

        // Continue animation frames
        if (spriteCounter <= 5) {
            spriteNumber = 1;
        } else if (spriteCounter <= 10) {
            spriteNumber = 2;
        } else if (spriteCounter <= 15) {
            spriteNumber = 3;
        } else if (spriteCounter <= 20) {
            spriteNumber = 4;
        }

        // End attack animation
        if (spriteCounter > 20) {
            spriteCounter = 0;
            attacking = false;
        }
    }

    /**
     * Handles interaction with objects in the game world.
     * Different objects have different interaction behaviors:
     * - Carrots: Can be harvested and collected
     * - Chests/Portals/Caves: Require Enter key to interact
     *
     * @param i the index of the object to interact with, or -1 if no object
     */
    public void pickUpObject(int i) {
        if (i != -1) {
            String objectName = gamePanel.getObjects()[i].getName();

            if (inventory.size() != MAX_INVENTORY_SIZE) {
                switch (objectName) {
                    case "Carrot" -> {
                        ObjCarrot carrot = (ObjCarrot) gamePanel.getObjects()[i];

                        if (canObtainItem(carrot)) {
                            if (carrot.isHarvested() && !carrot.isCollected()) {
                                // Harvest completed carrot
                                gamePanel.getUi().showMessage("You harvested a carrot!");
                                gamePanel.playSE(1);

                                nrCarrots++;
                                carrot.collect(); // Mark this carrot as collected

                                addItemToInventory(carrot);

                                System.out.println("CARROT NR. " + nrCarrots);
                            } else if (carrot.isReadyForHarvest()) {
                                // Start harvesting process
                                carrot.harvest();
                            } else {
                                gamePanel.getUi().showMessage("The carrot is not ready to be harvested yet!");
                            }
                        } else {
                            gamePanel.getUi().showMessage("You cannot harvest anymore!");
                        }
                    }
                    case "Chest", "Portal", "Cave" -> {
                        if (keyHandler.isEnterPressed()) {
                            gamePanel.getObjects()[i].interact();
                        }
                    }
                }
            } else {
                gamePanel.getUi().showMessage("You cannot carry anymore!");
            }
        }
    }

    /**
     * Handles teleportation to the next game level.
     * Plays teleport sound effect and advances to next level if not at maximum.
     * Maximum level is 3.
     */
    public void teleportToNextLevel() {
        gamePanel.playSE(5);

        int current = gamePanel.getKeyHandler().getCurrentLevel();
        if (current < 3) {
            gamePanel.getKeyHandler().setCurrentLevel(current + 1);
        }

        gamePanel.setGameState(gamePanel.getPLAY_STATE());
    }

    /**
     * Searches for a specific item in the player's inventory.
     *
     * @param itemName the name of the item to search for
     * @return the index of the item in inventory, or -1 if not found
     */
    public int searchItemInInventory(String itemName) {
        int itemIndex = -1;

        for (int i = 0; i < inventory.size(); ++i) {
            if (inventory.get(i).name.equals(itemName)) {
                itemIndex = i;
                break;
            }
        }

        return itemIndex;
    }

    /**
     * Checks if the player can obtain a specific item.
     * For stackable items, checks if item already exists in inventory.
     * For all items, checks if there's available inventory space.
     *
     * @param item the item to check
     * @return true if item can be obtained, false otherwise
     */
    public boolean canObtainItem(Entity item) {
        // Check if stackable
        if (item.stackable) {
            int index = searchItemInInventory(item.getName());

            // If item exists in inventory, it can be stacked
            if (index != -1) {
                return true;
            }
        }

        // For both stackable new items and non-stackable items,
        // check if there's room in inventory
        return inventory.size() < MAX_INVENTORY_SIZE;
    }

    /**
     * Adds an item to the player's inventory.
     * For stackable items, increments the amount if item already exists,
     * otherwise adds as new item. For non-stackable items, adds directly
     * if there's inventory space.
     *
     * @param item the item to add to inventory
     */
    public void addItemToInventory(Entity item) {
        if (item.stackable) {
            int index = searchItemInInventory(item.getName());

            // If item exists, increment amount
            if (index != -1) {
                inventory.get(index).amount++;
                System.out.println("DEBUG: Incremented " + item.getName() + " amount to " + inventory.get(index).amount);
            }
            // Otherwise add new item
            else if (inventory.size() < MAX_INVENTORY_SIZE) {
                item.amount = 1; // Set initial amount
                inventory.add(item);
                System.out.println("DEBUG: Added new " + item.getName() + " with amount 1");
            }
        }
        // Non-stackable items
        else if (inventory.size() < MAX_INVENTORY_SIZE) {
            inventory.add(item);
        }
    }

    /**
     * Handles interaction with NPCs.
     * When Enter key is pressed near an NPC, starts dialog and changes game state.
     *
     * @param i the index of the NPC to interact with, or -1 if no NPC
     */
    public void interactNpc(int i) {
        if (gamePanel.getKeyHandler().isEnterPressed()) {
            if (i != -1) {
                attackCanceled = true;
                gamePanel.setGameState(gamePanel.getDIALOG_STATE());
                gamePanel.getNpc()[i].speak();
            }
        }
    }

    /**
     * Handles contact with monsters (taking damage).
     * If player is not invincible, reduces life by 1 and grants temporary invincibility.
     *
     * @param i the index of the monster that contacted player, or -1 if no monster
     */
    public void contactMonster(int i) {
        if (i != -1) {
            if (!invincible) {
                life -= 1;
                invincible = true;
                gamePanel.playSE(6);
            }
        }
    }

    /**
     * Damages a monster during player attack.
     * If monster is not invincible, reduces its life by 1 and grants it temporary invincibility.
     * If monster's life reaches 0, removes it from the game.
     *
     * @param i the index of the monster to damage, or -1 if no monster
     */
    public void damageMonster(int i) {
        if (i != -1) {
            if (!gamePanel.getMonster()[i].invincible) {
                gamePanel.getMonster()[i].life -= 1;
                gamePanel.getMonster()[i].invincible = true;
                gamePanel.playSE(7);

                if (gamePanel.getMonster()[i].life <= 0) {
                    gamePanel.getMonster()[i] = null;
                }
            }
        }
    }

    /**
     * Updates the player's state each game frame.
     * Handles movement, collision detection, object interaction, NPC interaction,
     * monster contact, attacking, sprite animation, invincibility timer, and death.
     * This method is called once per game frame.
     */
    public void update() {
        isMoving = keyHandler.isUpPressed() || keyHandler.isDownPressed() || keyHandler.isRightPressed() || keyHandler.isLeftPressed();

        if (attacking) {
            attacking();
        } else {
            if (isMoving) {
                // Set direction based on input
                if (keyHandler.isUpPressed()) {
                    direction = "up";
                } else if (keyHandler.isDownPressed()) {
                    direction = "down";
                } else if (keyHandler.isLeftPressed()) {
                    direction = "left";
                } else if (keyHandler.isRightPressed()) {
                    direction = "right";
                }

                // Check tile collision
                isCollisionOn = false;
                gamePanel.getCollisionChecker().checkTile(this);

                // Check object collision and interact
                int objectIndex = gamePanel.getCollisionChecker().checkObject(this, true);
                pickUpObject(objectIndex);

                // Check NPC collision and interact
                int npcIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getNpc());
                interactNpc(npcIndex);

                // Check monster collision
                int monsterIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getMonster());
                contactMonster(monsterIndex);

                // Handle attack input
                if (keyHandler.isEnterPressed() && !attackCanceled) {
                    attacking = true;
                    spriteCounter = 0;
                }
                attackCanceled = false;

                gamePanel.getKeyHandler().setEnterPressed(false);

                // Move player if no collision
                if (!isCollisionOn) {
                    switch (direction) {
                        case "up" -> worldY -= speed;
                        case "down" -> worldY += speed;
                        case "left" -> worldX -= speed;
                        case "right" -> worldX += speed;
                    }
                }

                // Animate walking sprites
                spriteCounter++;
                if (spriteCounter > 5) {
                    switch (spriteNumber) {
                        case 1 -> spriteNumber = 2;
                        case 2 -> spriteNumber = 3;
                        case 3 -> spriteNumber = 4;
                        case 4 -> spriteNumber = 5;
                        case 5 -> spriteNumber = 6;
                        case 6 -> spriteNumber = 1;
                    }
                    spriteCounter = 0;
                }
            } else {
                // Continue counter for idle animation
                spriteCounter++;
            }
        }

        // Handle invincibility timer
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        // Check for death
        if (life <= 0) {
            gamePanel.setGameState(gamePanel.getGAME_OVER_STATE());
            gamePanel.playSE(3);
        }
    }

    /**
     * Renders the player on screen with appropriate sprite and effects.
     * Chooses the correct sprite based on movement state, direction, and animation frame.
     * Applies transparency effect when player is invincible.
     *
     * @param graphics2D the Graphics2D context for rendering
     */
    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;
        int tempScreenX = getSCREEN_X();
        int tempScreenY = getSCREEN_Y();

        if (isMoving) {
            switch (direction) {
                case "up" -> {
                    if (!attacking) {
                        image = getBufferedImage(spriteNumber, up1, up2, up3, up4, up5, up6);
                    }
                    if (attacking) {
                        if (spriteNumber == 1) {
                            image = attack_up_1;
                        }
                        if (spriteNumber == 2) {
                            image = attack_up_2;
                        }
                        if (spriteNumber == 3) {
                            image = attack_up_3;
                        }
                        if (spriteNumber == 4) {
                            image = attack_up_4;
                        }
                    }
                }
                case "down" -> {
                    if (!attacking) {
                        image = getBufferedImage(spriteNumber, down1, down2, down3, down4, down5, down6);
                    }
                    if (attacking) {
                        if (spriteNumber == 1) {
                            image = attack_down_1;
                        }
                        if (spriteNumber == 2) {
                            image = attack_down_2;
                        }
                        if (spriteNumber == 3) {
                            image = attack_down_3;
                        }
                        if (spriteNumber == 4) {
                            image = attack_down_4;
                        }
                    }
                }
                case "left" -> {
                    if (!attacking) {
                        image = getBufferedImage(spriteNumber, left1, left2, left3, left4, left5, left6);
                    }
                    if (attacking) {
                        if (spriteNumber == 1) {
                            image = attack_left_1;
                        }
                        if (spriteNumber == 2) {
                            image = attack_left_2;
                        }
                        if (spriteNumber == 3) {
                            image = attack_left_3;
                        }
                        if (spriteNumber == 4) {
                            image = attack_left_4;
                        }
                    }
                }
                case "right" -> {
                    if (!attacking) {
                        image = getBufferedImage(spriteNumber, right1, right2, right3, right4, right5, right6);
                    }
                    if (attacking) {
                        if (spriteNumber == 1) {
                            image = attack_right_1;
                        }
                        if (spriteNumber == 2) {
                            image = attack_right_2;
                        }
                        if (spriteNumber == 3) {
                            image = attack_right_3;
                        }
                        if (spriteNumber == 4) {
                            image = attack_right_4;
                        }
                    }
                }
            }
        } else {
            // Handle idle animation - cycles between 2 frames
            int idleFrame = (spriteCounter / 25) % 2 + 1; // Cycle every ~25 ticks

            switch (direction) {
                case "up" -> image = (idleFrame == 1) ? upIdle1 : upIdle2;
                case "down" -> image = (idleFrame == 1) ? downIdle1 : downIdle2;
                case "left" -> image = (idleFrame == 1) ? leftIdle1 : leftIdle2;
                case "right" -> image = (idleFrame == 1) ? rightIdle1 : rightIdle2;
            }
        }

        graphics2D.drawImage(image, tempScreenX, tempScreenY, null);

        // Apply transparency effect when invincible
        if (invincible) {
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        // Reset alpha
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // Draw solid area for debug purposes (commented out)
        // graphics2D.setColor(Color.RED);
        // graphics2D.drawRect(getSCREEN_X() + solidArea.x, getSCREEN_Y() + solidArea.y, solidArea.width, solidArea.height);
    }

    /**
     * Helper method to get the appropriate sprite frame from a series of animation frames.
     *
     * @param frame the current animation frame number (1-6)
     * @param down1 animation frame 1
     * @param down2 animation frame 2
     * @param down3 animation frame 3
     * @param down4 animation frame 4
     * @param down5 animation frame 5
     * @param down6 animation frame 6
     * @return the BufferedImage corresponding to the current frame
     */
    protected BufferedImage getBufferedImage(
            int frame, BufferedImage down1, BufferedImage down2,
            BufferedImage down3, BufferedImage down4,
            BufferedImage down5, BufferedImage down6
    ) {
        return switch (frame) {
            case 2 -> down2;
            case 3 -> down3;
            case 4 -> down4;
            case 5 -> down5;
            case 6 -> down6;
            default -> down1;
        };
    }

    /**
     * Loads all player movement and idle animation sprites from image files.
     * Loads 6 walking frames and 2 idle frames for each direction (up, down, left, right).
     */
    public void getPlayerImage() {
        // Walking animations
        right1 = setup("/player/player_right_1");
        right2 = setup("/player/player_right_2");
        right3 = setup("/player/player_right_3");
        right4 = setup("/player/player_right_4");
        right5 = setup("/player/player_right_5");
        right6 = setup("/player/player_right_6");

        left1 = setup("/player/player_left_1");
        left2 = setup("/player/player_left_2");
        left3 = setup("/player/player_left_3");
        left4 = setup("/player/player_left_4");
        left5 = setup("/player/player_left_5");
        left6 = setup("/player/player_left_6");

        up1 = setup("/player/player_up_1");
        up2 = setup("/player/player_up_2");
        up3 = setup("/player/player_up_3");
        up4 = setup("/player/player_up_4");
        up5 = setup("/player/player_up_5");
        up6 = setup("/player/player_up_6");

        down1 = setup("/player/player_down_1");
        down2 = setup("/player/player_down_2");
        down3 = setup("/player/player_down_3");
        down4 = setup("/player/player_down_4");
        down5 = setup("/player/player_down_5");
        down6 = setup("/player/player_down_6");

        // Idle animations
        upIdle1 = setup("/player/player_upIdle_1");
        upIdle2 = setup("/player/player_upIdle_2");

        downIdle1 = setup("/player/player_downIdle_1");
        downIdle2 = setup("/player/player_downIdle_2");

        leftIdle1 = setup("/player/player_leftIdle_1");
        leftIdle2 = setup("/player/player_leftIdle_2");

        rightIdle1 = setup("/player/player_rightIdle_1");
        rightIdle2 = setup("/player/player_rightIdle_2");
    }

    /**
     * Loads all player attack animation sprites from image files.
     * Loads 4 attack frames for each direction (up, down, left, right).
     */
    public void getPlayerAttackImage() {
        attack_right_1 = setup("/player/player_attack_right_1");
        attack_right_2 = setup("/player/player_attack_right_2");
        attack_right_3 = setup("/player/player_attack_right_3");
        attack_right_4 = setup("/player/player_attack_right_4");

        attack_left_1 = setup("/player/player_attack_left_1");
        attack_left_2 = setup("/player/player_attack_left_2");
        attack_left_3 = setup("/player/player_attack_left_3");
        attack_left_4 = setup("/player/player_attack_left_4");

        attack_up_1 = setup("/player/player_attack_up_1");
        attack_up_2 = setup("/player/player_attack_up_2");
        attack_up_3 = setup("/player/player_attack_up_3");
        attack_up_4 = setup("/player/player_attack_up_4");

        attack_down_1 = setup("/player/player_attack_down_1");
        attack_down_2 = setup("/player/player_attack_down_2");
        attack_down_3 = setup("/player/player_attack_down_3");
        attack_down_4 = setup("/player/player_attack_down_4");
    }
}