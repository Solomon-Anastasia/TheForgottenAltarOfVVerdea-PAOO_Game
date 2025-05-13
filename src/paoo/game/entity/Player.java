package paoo.game.entity;

import paoo.game.handler.KeyHandler;
import paoo.game.object.ObjCarrot;
import paoo.game.panel.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    KeyHandler keyHandler;

    // Where we draw player
    private final int SCREEN_X;
    private final int SCREEN_Y;

    // TODO: Change it after artefact is added, etc
    private int nrArtefacts = 0;
    private int nrCarrots = 0;

    private boolean isMoving = false;

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

        setDefaultValues();
        getPlayerImage();
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

    public void setDefaultValues() {
        // Player position in the map
        // TODO: Change position based of map
        worldX = gamePanel.getTILE_SIZE() * 45; // Start column
        worldY = gamePanel.getTILE_SIZE() * 26; // Start line
        speed = 5;
        direction = "down";
    }

    public void setDefaultPosition(int tileX, int tileY) {
        worldX = tileX * gamePanel.getTILE_SIZE();
        worldY = tileY * gamePanel.getTILE_SIZE();
    }

    public void update() {
        isMoving = keyHandler.isUpPressed() || keyHandler.isDownPressed() || keyHandler.isRightPressed() || keyHandler.isLeftPressed();

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

    // TODO: Set specific reaction for each object
    public void pickUpObject(int i) {
        if (i != -1) {
            // TODO: Leave only necessary
            String objectName = gamePanel.getObjects()[i].getName();

            // TODO: Add artefact object, etc
            switch (objectName) {
                case "Carrot" -> {
                    ObjCarrot carrot = (ObjCarrot) gamePanel.getObjects()[i];

                    if (carrot.isHarvested() && !carrot.isCollected()) {
                        gamePanel.getUi().showMessage("You harvested a carrot!");
                        gamePanel.playSE(1);

                        nrCarrots++;
                        carrot.collect(); // Mark this carrot as collected
                        System.out.println("CARROT NR. " + nrCarrots);
                    } else if (carrot.isReadyForHarvest()) {
                        carrot.harvest();
                    } else {
                        gamePanel.getUi().showMessage("The carrot is not ready to be harvested yet.");
                    }
                }

//                case "Artefact" -> {
//                    n`rArtefacts++;
//
//                    // Object disappear (delete)
//                    gamePanel.getObject()[i] = null;
//                }
//
//                // TODO: Getting seeds from grandpa, after the dialogue
//                case "Seed" -> {
//                    gamePanel.getUi().showMessage("Carrot seed!");
////                    if (Check dialogue ){
////                        gamePanel.getObject()[i] = null;
////                    }
//                }
//                case "Portal" -> {
//                    if (nrArtefacts > 0) {
//                        // TODO: Move to the next level
//                        nrArtefacts--;
//                    }
//                }
//                case "Chest" -> {
//                    // TODO: Add sound for unlocking chest
////                    gamePanel.playSE(numer of sound);
//                    gamePanel.getUi().showMessage("Chest will open after the task is done!");
//                    if (nrCarrots >= 5) {
//                        gamePanel.getUi().setGameFinished(true);
//                        gamePanel.stopMusic();
////                        gamePanel.playSE(nr. of win first level);
//                    }
//                }
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;

        if (isMoving) {
            switch (direction) {
                case "up" -> image = getBufferedImage(spriteNumber, up1, up2, up3, up4, up5, up6);
                case "down" -> image = getBufferedImage(spriteNumber, down1, down2, down3, down4, down5, down6);
                case "left" -> image = getBufferedImage(spriteNumber, left1, left2, left3, left4, left5, left6);
                case "right" -> image = getBufferedImage(spriteNumber, right1, right2, right3, right4, right5, right6);
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

        graphics2D.drawImage(image, SCREEN_X, SCREEN_Y, null);

        // Draw solid area for debug purposes
        // TODO: Remove after game completion
//        graphics2D.setColor(Color.RED);
//        graphics2D.drawRect(getSCREEN_X() + solidArea.x, getSCREEN_Y() + solidArea.y, solidArea.width, solidArea.height);
    }

    private BufferedImage getBufferedImage(int frame, BufferedImage down1, BufferedImage down2, BufferedImage down3, BufferedImage down4, BufferedImage down5, BufferedImage down6) {
        return switch (frame) {
            case 1 -> down1;
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
}
