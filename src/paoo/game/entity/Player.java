package paoo.game.entity;

import paoo.game.handler.KeyHandler;
import paoo.game.panel.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;

    // Where we draw player
    private final int SCREEN_X;
    private final int SCREEN_Y;

    // TODO: Change it after artefact is added, etc
    private int nrArtefacts = 0;
    private int nrCarrots = 0;

    private boolean isMoving = false;

    private BufferedImage upIdle1, upIdle2;
    private BufferedImage downIdle1, downIdle2;
    private BufferedImage leftIdle1, leftIdle2;
    private BufferedImage rightIdle1, rightIdle2;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        // Halfway point
        SCREEN_X = gamePanel.getSCREEN_WIDTH() / 2 - (gamePanel.getTILE_SIZE() / 2);
        SCREEN_Y = gamePanel.getSCREEN_HEIGHT() / 2 - (gamePanel.getTILE_SIZE() / 2);

        // Collision for player
        solidArea = new Rectangle();
        solidArea.x = 13;
        solidArea.y = 16;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        solidArea.width = 22;
        solidArea.height = 22;

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
            String objectName = gamePanel.getObject()[i].getName();

            // TODO: Add artefact object, etc
            switch (objectName) {
                case "Artefact" -> {
                    nrArtefacts++;

                    // Object disappear (delete)
                    gamePanel.getObject()[i] = null;
                }

                // TODO: Getting seeds from grandpa, after the dialogue
                case "Seed" -> {
                    gamePanel.getUi().showMessage("Carrot seed!");
//                    if (Check dialogue ){
//                        gamePanel.getObject()[i] = null;
//                    }
                }
                case "Carrot" -> {
                    // TODO: Add sound effects when carrot is picked up
                    // TODO: Check carrot stage first, if it is ready to pick up, then increment
                    gamePanel.getUi().showMessage("Carrot picked up!");
                    nrArtefacts++;
//                    gamePanel.playSE(number of sound);

                }
                case "Portal" -> {
                    if (nrArtefacts > 0) {
                        // TODO: Move to the next level
                        nrArtefacts--;
                    }
                }
                case "Chest" -> {
                    // TODO: Add sound for unlocking chest
//                    gamePanel.playSE(numer of sound);
                    gamePanel.getUi().showMessage("Chest will open after the task is done!");
                    if (nrCarrots >= 5) {
                        gamePanel.getUi().setGameFinished(true);
                        gamePanel.stopMusic();
//                        gamePanel.playSE(nr. of win first level);
                    }
                }
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

        graphics2D.drawImage(image, SCREEN_X, SCREEN_Y, gamePanel.getTILE_SIZE(), gamePanel.getTILE_SIZE(), null);
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
        try {
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_right_2.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_right_3.png")));
            right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_right_4.png")));
            right5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_right_5.png")));
            right6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_right_6.png")));

            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_left_2.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_left_3.png")));
            left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_left_4.png")));
            left5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_left_5.png")));
            left6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_left_6.png")));

            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_up_2.png")));
            up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_up_3.png")));
            up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_up_4.png")));
            up5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_up_5.png")));
            up6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_up_6.png")));

            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_down_2.png")));
            down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_down_3.png")));
            down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_down_4.png")));
            down5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_down_5.png")));
            down6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_down_6.png")));

            upIdle1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_upIdle_1.png")));
            upIdle2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_upIdle_2.png")));

            downIdle1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_downIdle_1.png")));
            downIdle2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_downIdle_2.png")));

            leftIdle1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_leftIdle_1.png")));
            leftIdle2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_leftIdle_2.png")));

            rightIdle1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_rightIdle_1.png")));
            rightIdle2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_rightIdle_2.png")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
