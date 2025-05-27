package paoo.game.entity;

import paoo.game.handler.KeyHandler;
import paoo.game.object.ObjCarrot;
import paoo.game.panel.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {
    KeyHandler keyHandler;

    // Where we draw player
    private final int SCREEN_X;
    private final int SCREEN_Y;

    private int nrCarrots = 0;

    private boolean isMoving = false;
    private boolean attackCanceled = false;

    private ArrayList<Entity> inventory = new ArrayList<>();
    private final int MAX_INVENTORY_SIZE = 20;

    private boolean teleportReady = false;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);

        this.keyHandler = keyHandler;

        // Halfway point
        SCREEN_X = gamePanel.getSCREEN_WIDTH() / 2 - (gamePanel.getTILE_SIZE() / 2);
        SCREEN_Y = gamePanel.getSCREEN_HEIGHT() / 2 - (gamePanel.getTILE_SIZE() / 2);

        // Collision for player
        solidArea.x = 18;
        solidArea.y = 20;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        solidArea.width = 10;
        solidArea.height = 13;

        renderPriority = 1;

        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        setItems();
        getPlayerAttackImage();
    }

    public boolean isTeleportReady() {
        return teleportReady;
    }

    public int getSCREEN_X() {
        return SCREEN_X;
    }

    public int getSCREEN_Y() {
        return SCREEN_Y;
    }

    public int getNrCarrots() {
        return nrCarrots;
    }

    public ArrayList<Entity> getInventory() {
        return inventory;
    }

    public void setAttackCanceled(boolean attackCanceled) {
        this.attackCanceled = attackCanceled;
    }

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

    public void setDefaultPosition(int tileX, int tileY) {
        worldX = tileX * gamePanel.getTILE_SIZE();
        worldY = tileY * gamePanel.getTILE_SIZE();
    }

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

    public void setTeleportReady(boolean teleportReady) {
        this.teleportReady = teleportReady;
    }

    public void restoreLife() {
        life = maxLife;
        invincible = false;
    }

    public void setItems() {
        inventory.clear();
    }

    public void attacking() {
        spriteCounter++;

        if (keyHandler.getCurrentLevel() == 1) {
            attacking = false;
            return;
        }

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

            int monsterIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getMonster());
            damageMonster(monsterIndex);

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

    public void pickUpObject(int i) {
        if (i != -1) {
            String objectName = gamePanel.getObjects()[i].getName();

            if (inventory.size() != MAX_INVENTORY_SIZE) {
                switch (objectName) {
                    case "Carrot" -> {
                        ObjCarrot carrot = (ObjCarrot) gamePanel.getObjects()[i];

                        if (canObtainItem(carrot)) {
                            if (carrot.isHarvested() && !carrot.isCollected()) {

                                gamePanel.getUi().showMessage("You harvested a carrot!");
                                gamePanel.playSE(1);

                                nrCarrots++;
                                carrot.collect(); // Mark this carrot as collected

                                addItemToInventory(carrot);

                                System.out.println("CARROT NR. " + nrCarrots);
                            } else if (carrot.isReadyForHarvest()) {
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

    public void teleportToNextLevel() {
        gamePanel.playSE(5);

        int current = gamePanel.getKeyHandler().getCurrentLevel();
        if (current < 3) {
            gamePanel.getKeyHandler().setCurrentLevel(current + 1);
        }

        gamePanel.setGameState(gamePanel.getPLAY_STATE());
    }

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

    // This method actually adds the item to inventory
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

    public void interactNpc(int i) {
        if (gamePanel.getKeyHandler().isEnterPressed()) {
            if (i != -1) {
                attackCanceled = true;
                gamePanel.setGameState(gamePanel.getDIALOG_STATE());
                gamePanel.getNpc()[i].speak();
            }
        }
    }

    public void contactMonster(int i) {
        if (i != -1) {
            if (!invincible) {
                life -= 1;
                invincible = true;
                gamePanel.playSE(6);
            }
        }
    }

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

    public void update() {
        isMoving = keyHandler.isUpPressed() || keyHandler.isDownPressed() || keyHandler.isRightPressed() || keyHandler.isLeftPressed();

        if (attacking) {
            attacking();
        } else {
            if (isMoving) {
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

                // Check object collision
                int objectIndex = gamePanel.getCollisionChecker().checkObject(this, true);

                // Based of index, determine payer interaction with it
                pickUpObject(objectIndex);

                // Check NPC collision
                int npcIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getNpc());
                interactNpc(npcIndex);

                // Check monster collision
                int monsterIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getMonster());
                contactMonster(monsterIndex);

                if (keyHandler.isEnterPressed() && !attackCanceled) {
                    attacking = true;
                    spriteCounter = 0;
                }
                attackCanceled = false;

                gamePanel.getKeyHandler().setEnterPressed(false);

                // If collision is false, player can move
                if (!isCollisionOn) {
                    switch (direction) {
                        case "up" -> worldY -= speed;
                        case "down" -> worldY += speed;
                        case "left" -> worldX -= speed;
                        case "right" -> worldX += speed;
                    }
                }

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
                spriteCounter++;
            }
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (life <= 0) {
            gamePanel.setGameState(gamePanel.getGAME_OVER_STATE());
            gamePanel.playSE(3);
        }
    }

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
                        //tempScreenY = getSCREEN_Y() - gamePanel.getTILE_SIZE();
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
                        //tempScreenY = getSCREEN_Y() - gamePanel.getTILE_SIZE();
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
                        //tempScreenY = getSCREEN_Y() - gamePanel.getTILE_SIZE();
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
                        //tempScreenX = getSCREEN_X() - gamePanel.getTILE_SIZE();
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
            int idleFrame = (spriteCounter / 25) % 2 + 1; // Cycle every ~25 ticks

            switch (direction) {
                case "up" -> image = (idleFrame == 1) ? upIdle1 : upIdle2;
                case "down" -> image = (idleFrame == 1) ? downIdle1 : downIdle2;
                case "left" -> image = (idleFrame == 1) ? leftIdle1 : leftIdle2;
                case "right" -> image = (idleFrame == 1) ? rightIdle1 : rightIdle2;
            }
        }

        graphics2D.drawImage(image, tempScreenX, tempScreenY, null);

        if (invincible) {
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        // Reset alpha
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // Draw solid area for debug purposes
        // graphics2D.setColor(Color.RED);
        // graphics2D.drawRect(getSCREEN_X() + solidArea.x, getSCREEN_Y() + solidArea.y, solidArea.width, solidArea.height);
    }

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